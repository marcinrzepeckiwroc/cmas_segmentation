package com.rzepecki.variables;

public class VariablesDefault implements VariableInferface{

    private int cmasDefaultPrio;
    private int cmasDefaultSsf;

    public int getCmasPrio() {
        return cmasDefaultPrio;
    }

    public int getCmasSsf() {
        return cmasDefaultSsf;
    }

    public static class Builder {
        private int cmasDefaultPrio = 0;
        private int cmasDefaultSsf = 0;

        public Builder cmasDefaultPrio(int cmasDefaultPrio) {
            this.cmasDefaultPrio = cmasDefaultPrio;
            return this;
        }

        public Builder cmasDefaultSsf(int cmasDefaultSsf) {
            this.cmasDefaultSsf = cmasDefaultSsf;
            return this;
        }

        public VariablesDefault build() {
            return new VariablesDefault(this);
        }
    }

    public VariablesDefault(Builder builder){
        this.cmasDefaultPrio = builder.cmasDefaultPrio;
        this.cmasDefaultSsf = builder.cmasDefaultSsf;
    }

    @Override
    public String toString() {
        return "VariablesDefault{" +
                "cmasDefaultPrio=" + cmasDefaultPrio +
                ", cmasDefaultSsf=" + cmasDefaultSsf +
                '}';
    }
}
