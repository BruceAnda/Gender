package zhaoliang.com.gender;

/**
 * Created by zhaoliang on 2017/2/7.
 */

public class RequstPredect {


    /**
     * status : 0
     * reason : Success
     * result : {"Claim":0.5,"Ratio":0.074829931972789,"Avg":0.5309524047619,"Stddev":0.041239306316801,"N5":0,"N4":0,"N3":0,"N2":0,"N1":0,"Zero":0.85714285714286,"P1":0.14285714285714,"P2":0,"P3":0,"P4":0,"P5":0}
     * echo : na
     */

    private int status;
    private String reason;
    private ResultBean result;
    private String echo;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public static class ResultBean {
        /**
         * Claim : 0.5
         * Ratio : 0.074829931972789
         * Avg : 0.5309524047619
         * Stddev : 0.041239306316801
         * N5 : 0
         * N4 : 0
         * N3 : 0
         * N2 : 0
         * N1 : 0
         * Zero : 0.85714285714286
         * P1 : 0.14285714285714
         * P2 : 0
         * P3 : 0
         * P4 : 0
         * P5 : 0
         */

        private double Claim;
        private double Ratio;
        private double Avg;
        private double Stddev;
        private int N5;
        private int N4;
        private int N3;
        private int N2;
        private int N1;
        private double Zero;
        private double P1;
        private int P2;
        private int P3;
        private int P4;
        private int P5;

        public double getClaim() {
            return Claim;
        }

        public void setClaim(double Claim) {
            this.Claim = Claim;
        }

        public double getRatio() {
            return Ratio;
        }

        public void setRatio(double Ratio) {
            this.Ratio = Ratio;
        }

        public double getAvg() {
            return Avg;
        }

        public void setAvg(double Avg) {
            this.Avg = Avg;
        }

        public double getStddev() {
            return Stddev;
        }

        public void setStddev(double Stddev) {
            this.Stddev = Stddev;
        }

        public int getN5() {
            return N5;
        }

        public void setN5(int N5) {
            this.N5 = N5;
        }

        public int getN4() {
            return N4;
        }

        public void setN4(int N4) {
            this.N4 = N4;
        }

        public int getN3() {
            return N3;
        }

        public void setN3(int N3) {
            this.N3 = N3;
        }

        public int getN2() {
            return N2;
        }

        public void setN2(int N2) {
            this.N2 = N2;
        }

        public int getN1() {
            return N1;
        }

        public void setN1(int N1) {
            this.N1 = N1;
        }

        public double getZero() {
            return Zero;
        }

        public void setZero(double Zero) {
            this.Zero = Zero;
        }

        public double getP1() {
            return P1;
        }

        public void setP1(double P1) {
            this.P1 = P1;
        }

        public int getP2() {
            return P2;
        }

        public void setP2(int P2) {
            this.P2 = P2;
        }

        public int getP3() {
            return P3;
        }

        public void setP3(int P3) {
            this.P3 = P3;
        }

        public int getP4() {
            return P4;
        }

        public void setP4(int P4) {
            this.P4 = P4;
        }

        public int getP5() {
            return P5;
        }

        public void setP5(int P5) {
            this.P5 = P5;
        }
    }
}
