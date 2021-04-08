/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.View;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author tamag
 */
public class InfoPanel {

    JPanel panel;
    JLabel fileLabel;
    JTextField fileField;
    JLabel nameLabel;
    ArrayList<JTextField> nameFields = new ArrayList<>();
    PopUpTable hiddenNameTable;
    JButton more;

    public InfoPanel(String fileName, ArrayList<String> names) {
        panel = new JPanel();
        panel.setBackground(Color.lightGray);
        panel.setBounds(40, 80, 200, 200);

        fileLabel = new JLabel("File");
        fileLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        fileLabel.setSize(100, 20);
        fileLabel.setLocation(0, 100);
        panel.add(fileLabel);

        fileField = new JTextField(fileName);
        fileField.setFont(new Font("Arial", Font.PLAIN, 15));
        fileField.setSize(50, 20);
        fileField.setLocation(10, 100);
        panel.add(fileField);

        nameLabel = new JLabel("Names");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        nameLabel.setSize(100, 20);
        nameLabel.setLocation(10, 50);
        panel.add(nameLabel);

        int moveLeft = 1;//variable to move coordinated to the left depending with the amount of elements generated
        int twoNamesOnly = 1;
        for (String name : names) {

            if (twoNamesOnly > 2) {
                break; //
            }
            JTextField tname = new JTextField(name);
            tname.setFont(new Font("Arial", Font.PLAIN, 15));
            tname.setSize(150, 20);
            tname.setLocation(20 * moveLeft, 100);
            nameFields.add(tname);
            panel.add(tname);
            moveLeft++;
            twoNamesOnly++;
        }

        //If the list of names is bigger than two they wont fit inside InfoPanel
        //so it is needed to create a hidden list accessed by a button
        if (names.size() > 2) {
            //by pressing this button user will be able to see the full list of names
            hiddenNameTable = new PopUpTable(names);
            more = new javax.swing.JButton("more");
            more.setLocation(20 * moveLeft, 100);// loacted in the left most of the components
            more.addActionListener((java.awt.event.ActionEvent evt) -> {
                //hiddenNameTable.getFrame();
                hiddenNameTable.getFrame().setVisible(true);
            });
            panel.add(more);
        }

    }

    public ArrayList<String> getFieldContent() {
        ArrayList<String> output = new ArrayList<>();
        //output.add(fileField.getText());
//        for (JTextField nameFiled : nameFields) {
//            output.add(nameFiled.getText());
//        }
        return hiddenNameTable.getNames();
    }

    public JPanel getPanel() {
        return panel;
    }
}
