package com.rzepecki.variables;

import java.util.ArrayList;
import java.util.List;

public class VariablesConstant {

    //constant value
    private final int OFDMSymbol = 12*14;
    private final int NumOfSymPbch = 6*12*4;
    private final int NumOfSymSyncCh = 6*12*2;
    private final int NumModBits = 2; //LTE_CP_35259
    private final int NumOfCrcBits = 24; //LTE_CP_35258

    public int getOFDMSymbol() {
        return OFDMSymbol;
    }

    public int getNumOfSymPbch() {
        return NumOfSymPbch;
    }

    public int getNumOfSymSyncCh() {
        return NumOfSymSyncCh;
    }

    public int getNumModBits() {
        return NumModBits;
    }

    public int getNumOfCrcBits() {
        return NumOfCrcBits;
    }
}
