package zhaoliang.com.gender;

import android.Manifest;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import libsvm.svm;
import libsvm.svm_model;
import libsvm.svm_node;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import zhaoliang.com.gender.svm.svm_train;

public class MainActivity extends AppCompatActivity {

    // 1. 获取app列表
    private ListView appList;
    private PackageManager packageManager;
    private List<ApplicationInfo> applicationInfos;
    private List<String> appPackages = new ArrayList<>();

    // 2. 发送到网络
    private OkHttpClient httpClient = new OkHttpClient();
    private String jsonString;
    private String requstString;

    // 3. 写文件
    private String path;
    private String trainFilePath;
    private String modelFilePath;
    private File file;

    // 4. 显示结果
    private TextView tvRequest, tvSex;
    private double code;
    private double[] doubles;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 请求权限
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        path = Environment.getExternalStorageDirectory() + File.separator + "gender";
        trainFilePath = path + File.separator + "train";
        modelFilePath = path + File.separator + "model";

        appList = (ListView) findViewById(R.id.app_list);
        tvRequest = (TextView) findViewById(R.id.tv_requst);
        tvSex = (TextView) findViewById(R.id.tv_sex);

        doubles = new double[2];

        new AppListTask().execute();
    }

    /**
     * 获取app列表的异步任务
     */
    class AppListTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            getAppList();

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            appList.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, appPackages));

            jsonString = JSON.toJSONString(appPackages);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("applist", jsonString);
            requstString = jsonObject.toJSONString();

        }
    }

    private svm_model svmModel;

    public void requstTest(View view) {
        try {
            svmModel = svm.svm_load_model(new BufferedReader(new InputStreamReader(new FileInputStream(modelFilePath))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new RequstTest().execute();
    }

    class RequstTest extends AsyncTask<Void, Void, Void> implements Callback {

        @Override
        protected Void doInBackground(Void... params) {
            RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requstString);
            Request request = new Request.Builder()
                    .url("http://api.hannm.com/apps2prob")
                    .post(body)
                    .build();
            httpClient.newCall(request).enqueue(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
           /* if (code == 0) {
                tvSex.setText("女");
            } else {
                tvSex.setText("男");
            }*/

            tvSex.setText("女:概率" + String.format("%.2f", doubles[1] * 100) + "%\n男:概率" + String.format("%.2f", doubles[0] * 100) + "%");
        }

        @Override
        public void onFailure(Call call, IOException e) {
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            RequstPredect requstPredect = JSON.parseObject(response.body().string(), RequstPredect.class);
            RequstPredect.ResultBean result = requstPredect.getResult();

            svm_node[] svm_nodes = new svm_node[15];
            svm_node svm_node = new svm_node();
            svm_node.index = 1;
            svm_node.value = result.getClaim();
            svm_nodes[0] = svm_node;

            svm_node svm_node1 = new svm_node();
            svm_node1.index = 1;
            svm_node1.value = result.getRatio();
            svm_nodes[1] = svm_node1;

            svm_node svm_node2 = new svm_node();
            svm_node2.index = 1;
            svm_node2.value = result.getAvg();
            svm_nodes[2] = svm_node2;

            svm_node svm_node3 = new svm_node();
            svm_node3.index = 1;
            svm_node3.value = result.getStddev();
            svm_nodes[3] = svm_node3;

            svm_node svm_node4 = new svm_node();
            svm_node4.index = 1;
            svm_node4.value = result.getN5();
            svm_nodes[4] = svm_node4;

            svm_node svm_node5 = new svm_node();
            svm_node5.index = 1;
            svm_node5.value = result.getN4();
            svm_nodes[5] = svm_node5;

            svm_node svm_node6 = new svm_node();
            svm_node6.index = 1;
            svm_node6.value = result.getN3();
            svm_nodes[6] = svm_node6;

            svm_node svm_node7 = new svm_node();
            svm_node7.index = 1;
            svm_node7.value = result.getN2();
            svm_nodes[7] = svm_node7;

            svm_node svm_node8 = new svm_node();
            svm_node8.index = 1;
            svm_node8.value = result.getN1();
            svm_nodes[8] = svm_node8;

            svm_node svm_node9 = new svm_node();
            svm_node9.index = 1;
            svm_node9.value = result.getZero();
            svm_nodes[9] = svm_node9;

            svm_node svm_node10 = new svm_node();
            svm_node10.index = 1;
            svm_node10.value = result.getP1();
            svm_nodes[10] = svm_node10;

            svm_node svm_node11 = new svm_node();
            svm_node11.index = 1;
            svm_node11.value = result.getP2();
            svm_nodes[11] = svm_node11;

            svm_node svm_node12 = new svm_node();
            svm_node12.index = 1;
            svm_node12.value = result.getP3();
            svm_nodes[12] = svm_node12;

            svm_node svm_node13 = new svm_node();
            svm_node13.index = 1;
            svm_node13.value = result.getP4();
            svm_nodes[13] = svm_node13;

            svm_node svm_node14 = new svm_node();
            svm_node14.index = 1;
            svm_node14.value = result.getP5();
            svm_nodes[14] = svm_node14;


            //code = svm.svm_predict(svmModel, svm_nodes);
            code = svm.svm_predict_probability(svmModel, svm_nodes, doubles);

            //System.out.println(doubles);
        }
    }


    public void requstTrain(View view) {
        new RequstTrainTask().execute();
    }

    class RequstTrainTask extends AsyncTask<Void, Void, Void> implements Callback {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... params) {
            Request request = new Request.Builder()
                    .url("http://api.hannm.com/applist/sex")
                    .build();
            httpClient.newCall(request).enqueue(this);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            //new String[]{"-s", "0", "-c", "128.0", "-t", "2", "-g", "8.0", "-e", "0.1", scaleFile, modelFile}
            try {
                svm_train.main(new String[]{"-b", "1", trainFilePath, modelFilePath});
            } catch (IOException e) {
                e.printStackTrace();
            }
            tvRequest.setText("请求样本成功！");
        }

        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            RequstTrain requstTrain = JSON.parseObject(response.body().string(), RequstTrain.class);

            file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            RandomAccessFile randomAccessFile = new RandomAccessFile(trainFilePath, "rwd");
            List<RequstTrain.ResultBean> result = requstTrain.getResult();
            StringBuilder stringBuilder;
            Features feature;
            for (RequstTrain.ResultBean resultBean : result) {
                stringBuilder = new StringBuilder();
                if ("M".equals(resultBean.getSex())) {
                    stringBuilder.append("1");
                } else {
                    stringBuilder.append("0");
                }
                feature = JSON.parseObject(resultBean.getFeatures(), Features.class);
                stringBuilder.append(" 1:" + feature.getClaim());
                stringBuilder.append(" 2:" + feature.getRatio());
                stringBuilder.append(" 3:" + feature.getAvg());
                stringBuilder.append(" 4:" + feature.getStddev());
                stringBuilder.append(" 5:" + feature.getN5());
                stringBuilder.append(" 6:" + feature.getN4());
                stringBuilder.append(" 7:" + feature.getN3());
                stringBuilder.append(" 8:" + feature.getN2());
                stringBuilder.append(" 9:" + feature.getN1());
                stringBuilder.append(" 10:" + feature.getZero());
                stringBuilder.append(" 11:" + feature.getP1());
                stringBuilder.append(" 12:" + feature.getP2());
                stringBuilder.append(" 13:" + feature.getP3());
                stringBuilder.append(" 14:" + feature.getP4());
                stringBuilder.append(" 15:" + feature.getP5());
                stringBuilder.append("\n");
                randomAccessFile.write(stringBuilder.toString().getBytes());
            }
        }
    }

    /**
     * 获取AppList
     */
    private void getAppList() {
        packageManager = getPackageManager();
        applicationInfos = packageManager.getInstalledApplications(PackageManager.MATCH_UNINSTALLED_PACKAGES);

        for (ApplicationInfo applicationInfo : applicationInfos) {
            appPackages.add(applicationInfo.processName);
        }
    }
}
