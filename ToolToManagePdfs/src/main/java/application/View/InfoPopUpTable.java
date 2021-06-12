/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.View;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

/**
 *
 * @author tamag
 */
public class InfoPopUpTable {

    JFrame frame;
    JLabel jLabel1;
    JScrollPane jScrollPane1;
    //JTable nameTable;
    JButton ok;
    List<JTextArea> nameFields;

    public InfoPopUpTable(List<String> listOfNames) {
        JPanel panel = new JPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();
        panel.setLayout(layout);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        //gbc.weightx = 50;
        //gbc.weighty = 25;
        gbc.gridx = 0;
        gbc.gridy = 0;

        nameFields = new ArrayList<JTextArea>();
        int index = 0;
        for (String name : listOfNames) {
            nameFields.add(new JTextArea(name));
            panel.add(nameFields.get(index), gbc);
            gbc.gridy++;
            index++;
        }

//init frame components----------------------------------------------------------       
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setSize(200, 300);
        jScrollPane1.setViewportView(panel);
//        jScrollPane1.setViewportView(nameTable);
        ok = new JButton("Ok");
        ok.addActionListener((java.awt.event.ActionEvent evt) -> {
            frame.setVisible(false);
        });
        ok.setSize(60, 20);
//prepare frame and add the componets to it with correct layout -----------------
        frame = new JFrame();
        frame.setTitle("Full list of Names");
        frame.setBounds(500, 250, 200, 450);
        gbc = new GridBagConstraints();
        layout = new GridBagLayout();
        frame.setLayout(layout);
        gbc.gridx = 0;
        gbc.gridy = 0;

        frame.add(jScrollPane1, gbc);
        gbc.gridy++;
        //frame.add(jLabel1);
        frame.add(ok, gbc);

    }

    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
//        for (int i = 0; i < nameTable.getRowCount(); i++) {
//            names.add(((String) nameTable.getValueAt(i, 0)));
//        }
        nameFields.forEach(nameField -> {
            names.add(nameField.getText());
        });
        return names;
    }

    public JFrame getFrame() {
        return frame;
    }
    
    public void dispose(){
        frame.dispose();
    }

}
