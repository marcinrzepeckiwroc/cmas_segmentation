package com.rzepecki;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SegmentSizeCalculator {

    //constant value
    public static final int OFDMSymbol = 12*14;
    public static final int NumOfSymPbch = 6*12*4;
    public static final int NumOfSymSyncCh = 6*12*2;
    public static final int NumModBits = 2; //LTE_CP_35259
    public static final int NumOfCrcBits = 24; //LTE_CP_35258
    List<Integer> msgIdPrioList = new ArrayList<Integer>();


    boolean actCMAS = true;
    boolean actCmasConfigurablePrio = true;

    int cmasDefaultPrio = 10;
    int cmasDefaultSsf = 0;

    //prio
    int msgIdFirst = 5000;
    int msgIdLast = 5002;
    int cmasPrio = 10;
    int cmasSsf = 100;

    public List<Integer> prioList(){
        msgIdPrioList.add(7000);
        msgIdPrioList.add(7001);
        return msgIdPrioList;
    }

    //zmienne NOKLTE:LNCEL_FDD
    double dlChBw = 5;
    String dlMimoMode = "Closed Loop Mimo"; //dla kazdej celki
    int maxNrSymPdcch = 3; //dla kazdej celki
    int NumOfSymPdcch = maxNrSymPdcch;
    int maxCrSibDl = 26; //dla kazdej celki
    String dlsDciCch = "DCI format 1C";  //dla kazdej celki

    public int defaultSize(int msgSize, int dataCodingScheme){

        int messageSize = msgSize+dataCodingScheme;
        if(messageSize%64==0){
            return messageSize/64;
        }else {
            return (messageSize/64)+1;
        }
    }

    public int customSize(int msgSize, int dataCodingScheme, int segmentSize){
        int bytesSegment = segmentSize-11;
        int messageSize = msgSize+dataCodingScheme;
        int segments = messageSize/bytesSegment;
        int mod = messageSize%bytesSegment;
        System.out.println("byte "+bytesSegment+"segmentow "+segments+" ostatni "+mod);
        return 0;
    }

    //base on LTE_CP_100203
    public double minTbsCalculator(double bw, String dlsDciCch, int useMinPRBperBand, int OFDMSymbol, int NumOfRefSym, int NumOfSymPdcch,
                                   int NumOfSymPbch, int NumOfSymSyncCh, int NumModBits, int maxCrSibDl, int NumOfCrcBits){
        if(dlsDciCch.equalsIgnoreCase("DCI format 1A")){
            useMinPRBperBand = 1;
        }
        return (useMinPRBperBand*(OFDMSymbol-NumOfRefSym-12*NumOfSymPdcch))*NumModBits*(maxCrSibDl/100.00)-NumOfCrcBits;
    }

    //base on LTE_CP_78161
    public double maxTbsCalculator(double bw, int useMaxPRBperBand, int OFDMSymbol, int NumOfRefSym, int NumOfSymPdcch,
                                 int NumOfSymPbch, int NumOfSymSyncCh, int NumModBits, int maxCrSibDl, int NumOfCrcBits){
        double maxTbs = 0;
        if(bw==1.4 || bw ==3){
            maxTbs = (useMaxPRBperBand*(OFDMSymbol-NumOfRefSym-12*NumOfSymPdcch))*NumModBits*(maxCrSibDl/100.00)-NumOfCrcBits;
        }else {
            maxTbs = ((useMaxPRBperBand*(OFDMSymbol-NumOfRefSym-12*NumOfSymPdcch))-NumOfSymPbch-NumOfSymSyncCh)*NumModBits*(maxCrSibDl/100.00)-NumOfCrcBits;
        }
        return maxTbs;
    }

    public int useMaxPRBperBand(double bandwidth){
        int prb = returnMaxPRB().get(bandwidth);
        return prb;
    }

    public int useMinPRBperBand(double bandwidth){
        int prb = returnMinPRB().get(bandwidth);
        return prb;
    }

    private Map<Double, Integer> returnMinPRB(){
        Map<Double, Integer> prbMap = new HashMap<Double, Integer>();
        //base on (LTE_CP_100198)
        prbMap.put(1.4, 2);
        prbMap.put(3.0, 2);
        prbMap.put(5.0, 2);
        prbMap.put(10.0, 4);
        prbMap.put(15.0, 4);
        prbMap.put(20.0, 4);
        return prbMap;
    }

    private Map<Double, Integer> returnMaxPRB(){
        Map<Double, Integer> prbMap = new HashMap<Double, Integer>();
        //base on (LTE_CP_102572)
        prbMap.put(1.4, 6);
        prbMap.put(3.0, 14);
        prbMap.put(5.0, 24);
        prbMap.put(10.0, 44);
        prbMap.put(15.0, 64);
        prbMap.put(20.0, 96);
        return prbMap;
    }

    public int numOfRefSym(int maxNrSymPdcch, String dlMimoMode) {
        int numOfRefSym = 0;
        if (dlMimoMode.equalsIgnoreCase("SingleTX")) {
            return numOfRefSym = 6;
        } else if (dlMimoMode.equalsIgnoreCase("TXDiv")) {
            return numOfRefSym = 6 + 6;
        } else if (dlMimoMode.equalsIgnoreCase("4-way TXDiv")) {
            if (maxNrSymPdcch == 1) {
                return numOfRefSym = 6 + 6 + 4 + 4;
            } else if (maxNrSymPdcch > 1) {
                return numOfRefSym = 6 + 6 + 2 + 2;
            }
        } else if (dlMimoMode.equalsIgnoreCase("Static Open Loop MIMO")) {
            return numOfRefSym = 6 + 6;
        } else if (dlMimoMode.equalsIgnoreCase("Dynamic Open Loop MIMO")) {
            return numOfRefSym = 6 + 6;
        } else if (dlMimoMode.equalsIgnoreCase("Closed Loop Mimo")) {
            return numOfRefSym = 6 + 6;
        } else if (dlMimoMode.equalsIgnoreCase("Closed Loop MIMO (4x2)")) {
            if (maxNrSymPdcch == 1) {
                return numOfRefSym = 6 + 6 + 4 + 4;
            } else if (maxNrSymPdcch > 1) {
                return numOfRefSym = 6 + 6 + 2 + 2;
            }
        }
        return numOfRefSym; //default value
    }

    //base on LTE_CP_35259
    private Map<Integer, Integer> returnTRBFormat1A(){
        Map<Integer, Integer> trbMap = new HashMap<Integer, Integer>();
        //base on (LTE_CP_35259)
        trbMap.put(0, 56);
        trbMap.put(1, 88);
        trbMap.put(2, 144);
        trbMap.put(3, 176);
        trbMap.put(4, 208);
        trbMap.put(5, 224);
        trbMap.put(6, 256);
        trbMap.put(7, 328);
        trbMap.put(8, 392);
        trbMap.put(9, 456);
        trbMap.put(10, 504);
        trbMap.put(11, 584);
        trbMap.put(12, 680);
        trbMap.put(13, 744);
        trbMap.put(14, 840);
        trbMap.put(15, 904);
        trbMap.put(16, 968);
        trbMap.put(17, 1064);
        trbMap.put(18, 1160);
        trbMap.put(19, 1288);
        trbMap.put(20, 1384);
        trbMap.put(21, 1480);
        trbMap.put(22, 1608);
        trbMap.put(23, 1736);
        trbMap.put(24, 1800);
        trbMap.put(25, 1864);
        trbMap.put(26, 2216);

        return trbMap;
    }

    //base on LTE_CP_35259
    private Map<Integer, Integer> returnTRBFormat1C(){
        Map<Integer, Integer> trbMap = new HashMap<Integer, Integer>();
        //base on (LTE_CP_35259)
        trbMap.put(0, 40);
        trbMap.put(1, 56);
        trbMap.put(2, 72);
        trbMap.put(3, 120);
        trbMap.put(4, 136);
        trbMap.put(5, 144);
        trbMap.put(6, 176);
        trbMap.put(7, 208);
        trbMap.put(8, 224);
        trbMap.put(9, 256);
        trbMap.put(10, 280);
        trbMap.put(11, 296);
        trbMap.put(12, 328);
        trbMap.put(13, 336);
        trbMap.put(14, 392);
        trbMap.put(15, 488);
        trbMap.put(16, 552);
        trbMap.put(17, 600);
        trbMap.put(18, 632);
        trbMap.put(19, 696);
        trbMap.put(20, 776);
        trbMap.put(21, 840);
        trbMap.put(22, 904);
        trbMap.put(23, 1000);
        trbMap.put(24, 1064);
        trbMap.put(25, 1128);
        trbMap.put(26, 1224);
        trbMap.put(27, 1288);
        trbMap.put(28, 1384);
        trbMap.put(29, 1480);
        trbMap.put(30, 1608);
        trbMap.put(31, 1736);

        return trbMap;
    }

    //base on LTE_CP_35259
    public int closeLowerTbs(String dlsDciCch, double maxTbs) throws Exception {
        Map<Integer, Integer> tbsTable;
        if (dlsDciCch.equalsIgnoreCase("DCI format 1C")){
            tbsTable=returnTRBFormat1C();
        }else if(dlsDciCch.equalsIgnoreCase("DCI format 1A")){
            tbsTable=returnTRBFormat1A();
        }else {
            throw new Exception("Nieznany dlsDciCch format");
        }
        int tempValue = tbsTable.get(0);
        for (Map.Entry<Integer, Integer> entry: tbsTable.entrySet()) {
            if (entry.getValue()<=maxTbs){
                tempValue = entry.getValue();
            }else {
                break;
            }
        }
        return tempValue;
    }

    public int closeHigherTbs(String dlsDciCch, double minTbs) throws Exception {
        Map<Integer, Integer> tbsTable;
        if (dlsDciCch.equalsIgnoreCase("DCI format 1C")){
            tbsTable=returnTRBFormat1C();
        }else if(dlsDciCch.equalsIgnoreCase("DCI format 1A")){
            tbsTable=returnTRBFormat1A();
        }else {
            throw new Exception("Nieznany dlsDciCch format");
        }
        int tempValue = tbsTable.get(0);
        for (Map.Entry<Integer, Integer> entry: tbsTable.entrySet()) {
            if (entry.getValue()==minTbs){
                tempValue = entry.getValue();
                break;
            }else if (tempValue<minTbs && minTbs<entry.getValue()) {
                tempValue = entry.getValue();
                break;
            }
        }
        return tempValue;
    }


}
