package com.rzepecki;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.rmi.activation.ActivationException;
import java.util.HashMap;
import java.util.Map;

import com.rzepecki.variables.*;

public class XMLparser {

    public NodeList xmlParser(String path) throws ParserConfigurationException, IOException, SAXException {
        File inputFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        return doc.getElementsByTagName("managedObject");
    }


    public Map<String, VariablesCells> getCellsMap(String path) throws ParserConfigurationException, IOException, SAXException {
        NodeList nList = xmlParser(path);
        Map<String, VariablesCells> map = new HashMap<String, VariablesCells>();
        try {
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    VariablesCells variablesCells = new VariablesCells();
                    if (eElement.getAttribute("class").equalsIgnoreCase("NOKLTE:LNCEL")) {
                        String[] tabName = eElement.getAttribute("distName").split("/");
                        String cellName = tabName[2];
                        //System.out.println(cellName);
                        for (Map.Entry<String, VariablesCells> entry :map.entrySet()) {
                            if(entry.getKey().equals(cellName)){
                                variablesCells = entry.getValue();
                                break;
                            }
                        }
                        NodeList paragrafList = eElement.getElementsByTagName("p");
                        for (int i = 0; i < paragrafList.getLength(); i++) {
                            Node pNode = paragrafList.item(i);
                            if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element pElement = (Element) pNode;
                                if(pElement.getAttribute("name").equalsIgnoreCase("maxCrSibDl")) {
                                    int maxCrSibDl = Integer.parseInt(pElement.getTextContent());
                                    variablesCells.setMaxCrSibDl(maxCrSibDl);
                                    //System.out.println(maxCrSibDl);
                                }else if(pElement.getAttribute("name").equalsIgnoreCase("dlsDciCch")) {
                                    String dlsDciCch = pElement.getTextContent();
                                    variablesCells.setDlsDciCch(dlsDciCch);
                                    //System.out.println(dlsDciCch);
                                }
                            }
                        }
                        map.put(cellName, variablesCells);
                    }
                    if (eElement.getAttribute("class").equalsIgnoreCase("NOKLTE:LNCEL_FDD")) {
                        String[] tabName = eElement.getAttribute("distName").split("/");
                        String cellName = tabName[2];
                        //System.out.println(cellName);
                        for (Map.Entry<String, VariablesCells> entry :map.entrySet()) {
                            if(entry.getKey().equals(cellName)){
                                //System.out.println(entry.getValue().toString());
                                variablesCells = entry.getValue();
                                break;
                            }
                        }
                        NodeList paragrafList = eElement.getElementsByTagName("p");
                        for (int i = 0; i < paragrafList.getLength(); i++) {
                            Node pNode = paragrafList.item(i);
                            if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element pElement = (Element) pNode;
                                if (pElement.getAttribute("name").equalsIgnoreCase("dlChBw")) {
                                    String[] tab = pElement.getTextContent().split(" ");
                                    double dlChBw = Double.parseDouble(tab[0]);
                                    variablesCells.setDlChBw(dlChBw);
                                }else if(pElement.getAttribute("name").equalsIgnoreCase("dlMimoMode")) {
                                    String dlMimoMode = pElement.getTextContent();
                                    variablesCells.setDlMimoMode(dlMimoMode);
                                }else if(pElement.getAttribute("name").equalsIgnoreCase("maxNrSymPdcch")) {
                                    int maxNrSymPdcch = Integer.parseInt(pElement.getTextContent());
                                    variablesCells.setMaxNrSymPdcch(maxNrSymPdcch);
                                }
                            }
                        }
                        //System.out.println(variablesClass.toString());
                        map.put(cellName, variablesCells);
                    }
                }
            }
        } catch (Exception e){
                    e.printStackTrace();
                }
        return map;
    }


    public Map<String, VariableInferface> getPrioMap(String path) throws IOException, SAXException, ParserConfigurationException, ActivationException {
        NodeList nList = xmlParser(path);
        Map<String, VariableInferface> map = new HashMap<String, VariableInferface>();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    VariablesCells variablesCells = new VariablesCells();
                    if (eElement.getAttribute("class").equalsIgnoreCase("NOKLTE:LNBTS")) {
                        NodeList paragrafList = eElement.getElementsByTagName("p");
                        for (int i = 0; i < paragrafList.getLength(); i++) {
                            Node pNode = paragrafList.item(i);
                            if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element pElement = (Element) pNode;
                                if (pElement.getAttribute("name").equalsIgnoreCase("actCMAS")) {
                                    if (!pElement.getTextContent().equals("true")) {
                                        throw new ActivationException("actCMAS not activated");
                                    }
                                }
                                if (pElement.getAttribute("name").equalsIgnoreCase("actCmasConfigurablePrio")) {
                                    if (!pElement.getTextContent().equals("true")) {
                                        throw new ActivationException("actCmasConfigurablePrio not activated");
                                    }
                                }
                            }
                        }
                    }
                    if (eElement.getAttribute("class").equalsIgnoreCase("NOKLTE:CMASPRIO")) {
                        String[] tabName = eElement.getAttribute("distName").split("/");
                        String cellName = tabName[2];
                        System.out.println(cellName);
                        int cmasDefaultPrio = 0;
                        int cmasDefaultSsf = 0;
                        int cmasPrio = 0;
                        int cmasSsf = 0;
                        int msgIdFirst = 0;
                        int msgIdLast = 0;
                        int index = 0;

                        NodeList paragrafList = eElement.getElementsByTagName("p");
                        for (int i = 0; i < paragrafList.getLength(); i++) {
                            Node pNode = paragrafList.item(i);
                            if (pNode.getNodeType() == Node.ELEMENT_NODE) {
                                Element pElement = (Element) pNode;

                                if (pElement.getAttribute("name").equalsIgnoreCase("cmasDefaultPrio")) {
                                    cmasDefaultPrio = Integer.parseInt(pElement.getTextContent());
                                }
                                else if (pElement.getAttribute("name").equalsIgnoreCase("cmasDefaultSsf")) {
                                    cmasDefaultSsf = Integer.parseInt(pElement.getTextContent());

                                    VariablesDefault variablesDefault = new VariablesDefault.Builder().cmasDefaultPrio(cmasDefaultPrio).cmasDefaultSsf(cmasDefaultSsf).build();
                                    map.put("default", variablesDefault);
                                }

                                else if (pElement.getAttribute("name").equalsIgnoreCase("cmasPrio")) {
                                    cmasPrio = Integer.parseInt(pElement.getTextContent());
                                }
                                else if (pElement.getAttribute("name").equalsIgnoreCase("cmasSsf")) {
                                    cmasSsf = Integer.parseInt(pElement.getTextContent());
                                }
                                else if (pElement.getAttribute("name").equalsIgnoreCase("msgIdFirst")) {
                                    msgIdFirst = Integer.parseInt(pElement.getTextContent());
                                }
                                else if (pElement.getAttribute("name").equalsIgnoreCase("msgIdLast")) {
                                    msgIdLast = Integer.parseInt(pElement.getTextContent());
                                    VariablesPrio variablesPrio = new VariablesPrio(msgIdFirst, msgIdLast, cmasPrio, cmasSsf);
                                    map.put(String.valueOf(index++), variablesPrio);
                                }
                            }
                        }
                    }
                }
            }
        return map;
    }

    //policz ilosc celek
    //utwórz tyle obiektów ile celek i załaduj do nich parametry
    //pobierz globalne parametry ale wczesniej sprawdz czy feature jest aktywowany

}
