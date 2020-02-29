package com.rzepecki;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rzepecki.variables.*;

public class XMLparser {


    public Map<String, VariablesClass> xmlParser(String path) throws ParserConfigurationException, IOException, SAXException {
        Map<String, VariablesClass> map = new HashMap<String, VariablesClass>();
        try {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("managedObject");
            //System.out.println("----------------------------"+nList.getLength());

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    VariablesClass variablesClass = new VariablesClass();
                    if (eElement.getAttribute("class").equalsIgnoreCase("NOKLTE:LNCEL")) {
                        String[] tabName = eElement.getAttribute("distName").split("/");
                        String cellName = tabName[2];
                        //System.out.println(cellName);
                        for (Map.Entry<String,VariablesClass> entry :map.entrySet()) {
                            if(entry.getKey().equals(cellName)){
                                variablesClass = entry.getValue();
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
                                    variablesClass.setMaxCrSibDl(maxCrSibDl);
                                    //System.out.println(maxCrSibDl);
                                }else if(pElement.getAttribute("name").equalsIgnoreCase("dlsDciCch")) {
                                    String dlsDciCch = pElement.getTextContent();
                                    variablesClass.setDlsDciCch(dlsDciCch);
                                    //System.out.println(dlsDciCch);
                                }
                            }
                        }
                        map.put(cellName, variablesClass);
                    }
                    if (eElement.getAttribute("class").equalsIgnoreCase("NOKLTE:LNCEL_FDD")) {
                        String[] tabName = eElement.getAttribute("distName").split("/");
                        String cellName = tabName[2];
                        //System.out.println(cellName);
                        for (Map.Entry<String,VariablesClass> entry :map.entrySet()) {
                            if(entry.getKey().equals(cellName)){
                                //System.out.println(entry.getValue().toString());
                                variablesClass = entry.getValue();
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
                                    variablesClass.setDlChBw(dlChBw);
                                }else if(pElement.getAttribute("name").equalsIgnoreCase("dlMimoMode")) {
                                    String dlMimoMode = pElement.getTextContent();
                                    variablesClass.setDlMimoMode(dlMimoMode);
                                }else if(pElement.getAttribute("name").equalsIgnoreCase("maxNrSymPdcch")) {
                                    int maxNrSymPdcch = Integer.parseInt(pElement.getTextContent());
                                    variablesClass.setMaxNrSymPdcch(maxNrSymPdcch);
                                }
                            }
                        }
                        //System.out.println(variablesClass.toString());
                        map.put(cellName, variablesClass);
                    }
                }
            }
        } catch (Exception e){
                    e.printStackTrace();
                }
        return map;
    }

    //policz ilosc celek
    //utwórz tyle obiektów ile celek i załaduj do nich parametry
    //pobierz globalne parametry ale wczesniej sprawdz czy feature jest aktywowany

}
