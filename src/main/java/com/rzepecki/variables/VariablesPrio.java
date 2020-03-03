package com.rzepecki.variables;

import java.util.ArrayList;
import java.util.List;

public class VariablesPrio implements VariableInferface{

    //prio
    private int msgIdFirst;
    private int msgIdLast;
    private int cmasPrio;
    private int cmasSsf;
    private List<Integer> msgList;

    public VariablesPrio(int msgIdFirst, int msgIdLast, int cmasPrio, int cmasSsf) {
        this.msgIdFirst = msgIdFirst;
        this.msgIdLast = msgIdLast;
        this.cmasPrio = cmasPrio;
        this.cmasSsf = cmasSsf;
        setMsgList(msgIdFirst, msgIdLast);
    }

    private void setMsgList(int msgIdFirst, int msgIdLast) {
        List<Integer> list = new ArrayList<Integer>();
        for(int i = msgIdFirst; i<=msgIdLast; i++){
            list.add(i);
        }
        this.msgList = list;
    }

    public int getMsgIdFirst() {
        return msgIdFirst;
    }

    public int getMsgIdLast() {
        return msgIdLast;
    }

    public int getCmasPrio() {
        return cmasPrio;
    }

    public int getCmasSsf() {
        return cmasSsf;
    }

    public List<Integer> getMsgList() {
        return msgList;
    }

    @Override
    public String toString() {
        return "VariablesPrio{" +
                "msgIdFirst=" + msgIdFirst +
                ", msgIdLast=" + msgIdLast +
                ", cmasPrio=" + cmasPrio +
                ", cmasSsf=" + cmasSsf +
                ", msgList=" + msgList +
                '}';
    }
}
