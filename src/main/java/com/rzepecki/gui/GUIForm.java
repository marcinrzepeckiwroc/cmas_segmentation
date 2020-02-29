package com.rzepecki.gui;

import com.rzepecki.XMLparser;
import com.rzepecki.variables.VariablesClass;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class GUIForm extends JFrame{
    private JButton button1;
    private JTextField textField1;
    private JTextField textField2;
    private JButton startButton;
    private JPanel panelForm;
    private JButton stopButton;
    private JLabel status;
    private JScrollPane scroll;
    private JTextArea textArea;
    private String[] regex = new String[0];
    private String dir;
    private boolean exit;


    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public String getDir() {
        return dir;
    }

    private void setDir(String dir) {
        this.dir = dir;
    }

    public String[] getRegex() {
        return regex;
    }

    private void setRegex(String regex) {
        String[] regextemp = regex.trim().split(",");
        this.regex = new String[regextemp.length];
        int count =0;
        for (String text: regextemp) {
            this.regex[count++] = text.trim();
        }
    }

    public void setTextArea(String text) {

        this.textArea.append(text);
    }

    public GUIForm(){
        panelForm.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Log parser ver. 0.2 beta");
        setSize(500, 500);
        add(panelForm);
        textArea.setEditable(false);

        JMenuBar mb=new JMenuBar();
        JMenu jMenu = new JMenu("Help");
        JMenuItem menu1=new JMenuItem("Report problem");
        JMenuItem menu2=new JMenuItem("About");

        jMenu.add(menu1);
        jMenu.add(menu2);
        mb.add(jMenu);
        setJMenuBar(mb);


        menu1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("report problem");
            }
        });

        menu2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { System.out.println("About");
            }
        });


        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                //fc.setCurrentDirectory(new java.io.File("C:/Users/rzepecki/Desktop/Java/11/src/src"));
                fc.setDialogTitle("choose main logs catalog");
                fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fc.showOpenDialog(button1);
                String path = fc.getSelectedFile().getAbsoluteFile().getPath();
                //System.out.println(path);
                textField1.setText(path);
                try {
                    parsXML(path);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (SAXException ex) {
                    ex.printStackTrace();
                } catch (ParserConfigurationException ex) {
                    ex.printStackTrace();
                }
            }

        });




        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

//                Thread thread = new Thread(()->{
//                    try {
//                        setRegex(textField2.getText());
//                        setDir(textField1.getText()); //ustawia ścieżkę
////                        startParsing();
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
//                });



            }
        });



    }

    private void parsXML(String path) throws IOException, SAXException, ParserConfigurationException {
        System.out.println("-----------------");
        XMLparser xmLparser = new XMLparser();
        Map<String, VariablesClass> map = xmLparser.xmlParser(path);
        System.out.println(map.size());
        for (Map.Entry<String,VariablesClass> entry :map.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue().toString());
        }

    }


}


