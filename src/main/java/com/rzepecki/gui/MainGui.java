package com.rzepecki.gui;

import com.rzepecki.XMLparser;
import com.rzepecki.variables.VariableInferface;
import com.rzepecki.variables.VariablesCells;
import com.rzepecki.variables.VariablesPrio;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.activation.ActivationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MainGui extends JFrame{

    private JPanel panelForm;
    private JButton button1;
    private JTextField textField1;
    private String[] filterTab = new String[0];
    private List<Integer> msgList = new ArrayList<Integer>();

    public String[] getFilterTab() {
        return filterTab;
    }

    public List<Integer> getMsgList() {
        return msgList;
    }

    public void setMsgList(Map<String, VariableInferface> mapPrioValues) {
        List<Integer> tempList = new ArrayList<Integer>();
        for (Map.Entry<String, VariableInferface> entry: mapPrioValues.entrySet()){
            if(!entry.getKey().equals("default")){
                VariablesPrio variablesPrio = (VariablesPrio) entry.getValue();
                tempList.addAll(variablesPrio.getMsgList());
            }
        }
        this.msgList = tempList;
    }

    private void setFilterTab(Map<String, VariablesCells> mapCells) {
        for (Map.Entry<String,VariablesCells> entry :mapCells.entrySet()){
            this.filterTab = Arrays.copyOf(this.filterTab, this.filterTab.length+1);
            this.filterTab[this.filterTab.length-1] = entry.getKey();
        }
    }

    public MainGui(){
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("CMAS segment size calculator ver. 0.2 beta");
        setSize(500, 150);
        add(panelForm);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                XMLparser xmLparser = new XMLparser();
                try {
                    JFileChooser fc = new JFileChooser();
                    fc.setDialogTitle("choose main logs catalog");
                    fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    fc.showOpenDialog(button1);
                    String path = fc.getSelectedFile().getAbsoluteFile().getPath();
                    textField1.setText(path);

                    Map<String, VariableInferface> mapPrioValues = xmLparser.getPrioMap(path);
                    Map<String, VariablesCells> mapCells = xmLparser.getCellsMap(path);
                    setFilterTab(mapCells);
                    setMsgList(mapPrioValues);

                    GUIForm guiForm = new GUIForm(mapPrioValues, mapCells, getFilterTab(), getMsgList());
                    guiForm.setVisible(true);
                    dispose();

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


}
