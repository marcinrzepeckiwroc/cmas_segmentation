package com.rzepecki;

import com.rzepecki.variables.VariableInferface;
import com.rzepecki.variables.VariablesCells;
import com.rzepecki.variables.VariablesConstant;

import java.util.Map;

public class Calculator {

    private int maxByte;
    private int minByte;
    private int sib12NonDefSize;
    private int sib12DefSize;

    public int getMaxByte() {
        return maxByte;
    }

    public void setMaxByte(int maxByte) {
        this.maxByte = maxByte;
    }

    public int getMinByte() {
        return minByte;
    }

    public void setMinByte(int minByte) {
        this.minByte = minByte;
    }

    public int getSib12NonDefSize() {
        return sib12NonDefSize;
    }

    public void setSib12NonDefSize(int sib12NonDefSize) {
        this.sib12NonDefSize = sib12NonDefSize;
    }

    public int getSib12DefSize() {
        return sib12DefSize;
    }

    public void setSib12DefSize(int sib12DefSize) {
        this.sib12DefSize = sib12DefSize;
    }

    public Calculator(int mesgLenghtText,
                      int mesgCodingText,
                      VariablesCells cell,
                      VariableInferface variable,
                      SegmentSizeCalculator sSC){
        try {
            VariablesConstant constant = new VariablesConstant();
            //cell
            int maxCrSibDl = cell.getMaxCrSibDl();
            String dlsDciCch = cell.getDlsDciCch();
            double dlChBw = cell.getDlChBw();
            String dlMimoMode = cell.getDlMimoMode();
            int maxNrSymPdcch = cell.getMaxNrSymPdcch();
            int NumOfSymPdcch = cell.getMaxNrSymPdcch();
            //constant
            int OFDMSymbol = constant.getOFDMSymbol();
            int NumOfSymPbch = constant.getNumOfSymPbch();
            int NumOfSymSyncCh = constant.getNumOfSymSyncCh();
            int NumModBits = constant.getNumModBits();
            int NumOfCrcBits = constant.getNumOfCrcBits();

            int useMaxPRBperBand = sSC.useMaxPRBperBand(dlChBw);
            int useMinPRBperBand = sSC.useMinPRBperBand(dlChBw);
            int NumOfRefSym = sSC.numOfRefSym(maxNrSymPdcch,dlMimoMode);
            //maxTBS
            double maxtbs = sSC.maxTbsCalculator(dlChBw, useMaxPRBperBand,OFDMSymbol, NumOfRefSym,NumOfSymPdcch, NumOfSymPbch, NumOfSymSyncCh, NumModBits, maxCrSibDl, NumOfCrcBits);
            int maxBite = sSC.closeLowerTbs(dlsDciCch, maxtbs);
            //minReasonableTbs
            double minReasonableTbs = sSC.minTbsCalculator(dlChBw, dlsDciCch, useMinPRBperBand,OFDMSymbol, NumOfRefSym,NumOfSymPdcch, NumOfSymPbch, NumOfSymSyncCh, NumModBits, maxCrSibDl, NumOfCrcBits);
            int minBite = sSC.closeHigherTbs(dlsDciCch, minReasonableTbs);
            //maxTBS and minReasonableTbs converted to byte
            setMaxByte(maxBite/8);
            setMinByte(minBite/8);

            //segmentSize
            int segmentSize = getMinByte()+((getMaxByte()-getMinByte())*variable.getCmasSsf())/100;
            setSib12NonDefSize(segmentSize);

            int defSegmentSize = sSC.defaultSize(mesgLenghtText, mesgCodingText);
            int Sib12defSize = defSegmentSize + 11;
            setSib12DefSize(Sib12defSize);

            if(Sib12defSize<segmentSize){
                if(!((segmentSize-11)>=mesgLenghtText)){
                    sSC.customSize(mesgLenghtText,mesgCodingText,segmentSize);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
