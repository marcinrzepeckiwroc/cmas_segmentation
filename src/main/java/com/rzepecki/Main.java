package com.rzepecki;



import com.rzepecki.gui.GUIForm;

import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        GUIForm guiForm = new GUIForm();
        guiForm.setVisible(true);

        SegmentSizeCalculator segmentSizeCalculator = new SegmentSizeCalculator();
        double dlChBw = segmentSizeCalculator.dlChBw;
        String dlMimoMode = segmentSizeCalculator.dlMimoMode;
        int maxNrSymPdcch = segmentSizeCalculator.maxNrSymPdcch;
        int useMaxPRBperBand = segmentSizeCalculator.useMaxPRBperBand(dlChBw);
        int useMinPRBperBand = segmentSizeCalculator.useMinPRBperBand(dlChBw);
        int NumOfRefSym = segmentSizeCalculator.numOfRefSym(maxNrSymPdcch,dlMimoMode);
        int OFDMSymbol = segmentSizeCalculator.OFDMSymbol;
        int NumOfSymPdcch = segmentSizeCalculator.NumOfSymPdcch;
        int NumOfSymPbch = segmentSizeCalculator.NumOfSymPbch;
        int NumOfSymSyncCh = segmentSizeCalculator.NumOfSymSyncCh;
        int NumModBits = segmentSizeCalculator.NumModBits;
        int NumOfCrcBits = segmentSizeCalculator.NumOfCrcBits;
        int maxCrSibDl = segmentSizeCalculator.maxCrSibDl;
        String ddl = segmentSizeCalculator.dlsDciCch;
        int cmasDefaultSsf = segmentSizeCalculator.cmasDefaultSsf;
        int cmasSsf = segmentSizeCalculator.cmasSsf;


        //zmienne
        int mesg = 7001;
        int msgSize = 4000;
        int dataCodingScheme = 1;

        List<Integer> msgIdPrioList = segmentSizeCalculator.prioList();

        double maxtbs = segmentSizeCalculator.maxTbsCalculator(dlChBw, useMaxPRBperBand,OFDMSymbol, NumOfRefSym,NumOfSymPdcch, NumOfSymPbch, NumOfSymSyncCh, NumModBits, maxCrSibDl, NumOfCrcBits);
        int maxBite = segmentSizeCalculator.closeLowerTbs(ddl, maxtbs);

        double minReasonableTbs = segmentSizeCalculator.minTbsCalculator(dlChBw, ddl, useMinPRBperBand,OFDMSymbol, NumOfRefSym,NumOfSymPdcch, NumOfSymPbch, NumOfSymSyncCh, NumModBits, maxCrSibDl, NumOfCrcBits);
        int minBite = segmentSizeCalculator.closeHigherTbs(ddl, minReasonableTbs);

        int maxByte = maxBite/8;
        int minByte = minBite/8;

        System.out.println(maxByte+" "+minByte);
        int segmentationSizeFactor = 0;
        if(msgIdPrioList.contains(mesg)) {
            System.out.println("NON DEFAULT cmasSsf" + cmasSsf);
            segmentationSizeFactor = cmasSsf;
        }else {
            System.out.println("DEFAULT cmasDefaultSsf " + cmasDefaultSsf);
            segmentationSizeFactor = cmasDefaultSsf;
        }
        int segmentSize = minByte+((maxByte-minByte)*segmentationSizeFactor)/100;
        int defSegmentSize = segmentSizeCalculator.defaultSize(msgSize, dataCodingScheme);
        int Sib12defSize = defSegmentSize + 11;
        System.out.println("SIB12 "+ Sib12defSize);
        System.out.println(segmentSize);
        if(Sib12defSize<segmentSize){
            System.out.println("Default SIB12 size "+Sib12defSize+" New SIB12 size "+segmentSize+" | Default SIB12 size override by new segmentSize");
            if((segmentSize-11)>=msgSize){
                System.out.println("bedzie jeden Segment !!!!!");
            }else{
                segmentSizeCalculator.customSize(msgSize,dataCodingScheme,segmentSize);
            }
        }else{
            System.out.println("wiekszy zostaje default");
        }
    }
}
