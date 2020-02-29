package com.rzepecki.variables;

import java.util.ArrayList;
import java.util.List;

public class VariablesGlobal {

    //constant value
    private static final int OFDMSymbol = 12*14;
    private static final int NumOfSymPbch = 6*12*4;
    private static final int NumOfSymSyncCh = 6*12*2;
    private static final int NumModBits = 2; //LTE_CP_35259
    private static final int NumOfCrcBits = 24; //LTE_CP_35258

    List<Integer> msgIdPrioList = new ArrayList<Integer>();

    //globalne
    private boolean actCMAS = true;
    private boolean actCmasConfigurablePrio = true;
    private int cmasDefaultPrio = 10;
    private int cmasDefaultSsf = 0;
    //prio
    int msgIdFirst = 5000;
    int msgIdLast = 5002;
    int cmasPrio = 10;
    int cmasSsf = 100;

    public List<Integer> prioList(int msgIdFirst, int msgIdLast){
        msgIdPrioList.add(7000);
        msgIdPrioList.add(7001);
        return msgIdPrioList;
    }
}
