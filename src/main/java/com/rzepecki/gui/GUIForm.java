package com.rzepecki.gui;

import com.rzepecki.Calculator;
import com.rzepecki.SegmentSizeCalculator;
import com.rzepecki.XMLparser;
import com.rzepecki.variables.VariableInferface;
import com.rzepecki.variables.VariablesCells;
import com.rzepecki.variables.VariablesDefault;
import com.rzepecki.variables.VariablesPrio;
import org.xml.sax.SAXException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;


public class GUIForm extends JFrame{
    private JPanel panelForm;
    private JPanel input;
    private JPanel output;
    private JTextArea textArea;



    public GUIForm(final Map<String, VariableInferface> mapPrioValues,
                   final Map<String, VariablesCells> mapCells,
                   String[] filterTab,
                   final List<Integer> getMsgList){
        final JComboBox comboBox1 = new JComboBox(filterTab);
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelForm.setLayout(new GridLayout(2,1));
        input.setLayout(new BoxLayout(input, BoxLayout.PAGE_AXIS));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CMAS segment size calculator ver. 0.2 beta");
        setSize(500, 500);
        JLabel label1 = new JLabel("Cell");
        JLabel label2 = new JLabel("Message ID");
        JLabel label3 = new JLabel("Message Lenght");
        JLabel label4 = new JLabel("Data Coding Scheme");
        final JTextField mesgId = new JTextField();
        final JTextField mesgLenght = new JTextField();
        final JTextField mesgCoding = new JTextField();
        JButton button = new JButton();
        button.setText("start");

        add(panelForm);
        panelForm.add(input);
        panelForm.add(output);
        input.add(label1);
        input.add(comboBox1);
        input.add(label2);
        input.add(mesgId);
        input.add(label3);
        input.add(mesgLenght);
        input.add(label4);
        input.add(mesgCoding);
        input.add(button);

        textArea.setEditable(false);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText(null);

                Integer mesgIdText = Integer.parseInt(mesgId.getText());
                Integer mesgLenghtText = Integer.parseInt(mesgLenght.getText());
                Integer mesgCodingText = Integer.parseInt(mesgCoding.getText());

                String comboText = (String)comboBox1.getSelectedItem();

                VariablesCells cell = getCell(comboText, mapCells);
                VariableInferface variable = getVariable(mesgIdText, getMsgList, mapPrioValues);

                SegmentSizeCalculator sSC = new SegmentSizeCalculator();
                Calculator calculator = new Calculator(mesgLenghtText, mesgCodingText, cell, variable, sSC);
                textArea.append("Cell: "+comboText+"\n");
                textArea.append("dlChBw: "+cell.getDlChBw()+" MHz\n");
                textArea.append("dlMimoMode: "+cell.getDlMimoMode()+"\n");
                textArea.append("maxNrSymPdcch: "+cell.getMaxNrSymPdcch()+"\n");
                textArea.append("maxCrSibDl: "+cell.getMaxCrSibDl()+"\n");
                textArea.append("dlsDciCch: "+cell.getDlsDciCch()+"\n");
                textArea.append("-------------------------------------\n");
                textArea.append("maxTBS "+calculator.getMaxByte()+" bytes"+"\n"+"minReasonableTbs "+calculator.getMinByte()+" bytes"+"\n");
                textArea.append("cmasSsf "+variable.getCmasSsf()+"\n");
                textArea.append("SIB12 Segment size NON default "+calculator.getSib12NonDefSize()+" bytes"+"\n");
                textArea.append("SIB12 Segment size default "+calculator.getSib12DefSize()+" bytes (64 segments)"+"\n");
                if(calculator.getSib12DefSize()<calculator.getSib12NonDefSize()){
                    textArea.append("Default SIB12 size "+calculator.getSib12DefSize()+" New SIB12 size "+calculator.getSib12NonDefSize()+" | Default SIB12 size override by new segmentSize \n");
                    textArea.append("--------RESULT------------\n");
                    if((calculator.getSib12NonDefSize()-11)>=mesgLenghtText){
                        textArea.append("1 segment "+calculator.getSib12NonDefSize()+" bytes\n");
                    }else{
                        textArea.append(sSC.getSegments()+" segments of "+sSC.getNotLastSegmentSize()+" bytes "+" / last segments "+sSC.getLastSegmentSize()+" bytes");
                    }
                }else{
                    textArea.append("Default segment size will be use "+calculator.getSib12DefSize()+" bytes/segment (64 segments)");
                }
            }
        });
    }

    private VariablesCells getCell(String comboText, Map<String, VariablesCells> mapCells) {
        for(Map.Entry<String, VariablesCells> entry: mapCells.entrySet()){
            if(entry.getKey().equals(comboText)){
                return entry.getValue();
            }
        }
        return null;
    }

    private VariableInferface getVariable(Integer mesgIdText, List<Integer> getMsgList, Map<String, VariableInferface> mapPrioValues) {
        VariableInferface variable = null;
        if(getMsgList.contains(mesgIdText)){
            for(Map.Entry<String, VariableInferface> entry: mapPrioValues.entrySet()){
                if(!entry.getKey().equals("default")){
                    VariablesPrio variablesPrio = (VariablesPrio) entry.getValue();
                    if(variablesPrio.getMsgList().contains(mesgIdText)){
                        return entry.getValue();
                    }
                }
            }
        }else{
            VariablesDefault variablesDefault = (VariablesDefault) mapPrioValues.get("default");
            return mapPrioValues.get("default");
        }
        return variable;
    }
}


