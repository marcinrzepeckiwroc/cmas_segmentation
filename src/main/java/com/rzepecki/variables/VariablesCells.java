package com.rzepecki.variables;

public class VariablesCells {


    //zmienne NOKLTE:LNCEL_FDD
    private double dlChBw;
    private String dlMimoMode;
    private int maxNrSymPdcch;
    private int NumOfSymPdcch;
    private int maxCrSibDl;
    private String dlsDciCch;

    public VariablesCells() {
    }

    public VariablesCells(double dlChBw, String dlMimoMode, int maxNrSymPdcch, int numOfSymPdcch, int maxCrSibDl, String dlsDciCch) {

        this.dlChBw = dlChBw;
        this.dlMimoMode = dlMimoMode;
        this.maxNrSymPdcch = maxNrSymPdcch;
        NumOfSymPdcch = numOfSymPdcch;
        this.maxCrSibDl = maxCrSibDl;
        this.dlsDciCch = dlsDciCch;
    }

    public VariablesCells(Builder builder){
        this.dlChBw = builder.dlChBw;
        this.dlMimoMode = builder.dlMimoMode;
        this.maxNrSymPdcch = builder.maxNrSymPdcch;
        this.NumOfSymPdcch = builder.maxNrSymPdcch;
        this.maxCrSibDl = builder.maxCrSibDl;
        this.dlsDciCch = builder.dlsDciCch;
    }

    public double getDlChBw() {
        return dlChBw;
    }

    public String getDlMimoMode() {
        return dlMimoMode;
    }

    public int getMaxNrSymPdcch() {
        return maxNrSymPdcch;
    }

    public int getNumOfSymPdcch() {
        return NumOfSymPdcch;
    }

    public int getMaxCrSibDl() {
        return maxCrSibDl;
    }

    public String getDlsDciCch() {
        return dlsDciCch;
    }

    public void setDlChBw(double dlChBw) {
        this.dlChBw = dlChBw;
    }

    public void setDlMimoMode(String dlMimoMode) {
        this.dlMimoMode = dlMimoMode;
    }

    public void setMaxNrSymPdcch(int maxNrSymPdcch) {
        setNumOfSymPdcch(maxNrSymPdcch);
        this.maxNrSymPdcch = maxNrSymPdcch;
    }

    public void setNumOfSymPdcch(int numOfSymPdcch) {
        NumOfSymPdcch = numOfSymPdcch;
    }

    public void setMaxCrSibDl(int maxCrSibDl) {
        this.maxCrSibDl = maxCrSibDl;
    }

    public void setDlsDciCch(String dlsDciCch) {
        this.dlsDciCch = dlsDciCch;
    }

    public static class Builder{
        private double dlChBw = 0;
        private String dlMimoMode = null;
        private int maxNrSymPdcch = 0;
        private int NumOfSymPdcch = 0;
        private int maxCrSibDl = 0;
        private String dlsDciCch = null;

        public Builder dlChBw(double dlChBw) {
            this.dlChBw = dlChBw;
            return this;
        }

        public Builder dlMimoMode(String dlMimoMode) {
            this.dlMimoMode = dlMimoMode;
            return this;
        }

        public Builder maxNrSymPdcch(int maxNrSymPdcch) {
            this.NumOfSymPdcch = maxNrSymPdcch;
            this.maxNrSymPdcch = maxNrSymPdcch;
            return this;
        }

        public Builder maxCrSibDl(int maxCrSibDl) {
            this.maxCrSibDl = maxCrSibDl;
            return this;
        }

        public Builder dlsDciCch(String dlsDciCch) {
            this.dlsDciCch = dlsDciCch;
            return this;
        }

        public VariablesCells build(){
            return new VariablesCells(this);
        }
    }

    @Override
    public String toString() {
        return "VariablesClass{" +
                "dlChBw=" + dlChBw +
                ", dlMimoMode='" + dlMimoMode + '\'' +
                ", maxNrSymPdcch=" + maxNrSymPdcch +
                ", NumOfSymPdcch=" + NumOfSymPdcch +
                ", maxCrSibDl=" + maxCrSibDl +
                ", dlsDciCch='" + dlsDciCch + '\'' +
                '}';
    }
}
