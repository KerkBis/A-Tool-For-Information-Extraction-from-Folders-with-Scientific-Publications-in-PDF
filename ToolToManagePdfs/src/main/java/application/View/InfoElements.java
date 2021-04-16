/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author tamag
 */
public class InfoElements {

    JPanel panel;
    JLabel fileLabel;
    public JTextField fileField;
    JLabel nameLabel;
    ArrayList<JTextField> nameFields = new ArrayList<>();
    InfoPopUpTable hiddenNameTable;
    JButton more;

    public InfoElements(String fileName, ArrayList<String> names) {
        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 20;
        gbc.gridx = 0;
        gbc.gridy = 0;

        ArrayList<String> listOfNames = names;
        panel = new JPanel();
        panel.setBackground(Color.lightGray);


        fileLabel = new JLabel("File");
        fileLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        //fileLabel.setSize(100, 20);
        panel.add(fileLabel, gbc);

        fileField = new JTextField(fileName);
        fileField.setFont(new Font("Arial", Font.PLAIN, 15));
        //fileField.setSize(50, 20);
        gbc.gridx++;
        gbc.gridy = 0;
        panel.add(fileField, gbc);

        nameLabel = new JLabel("Names");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        //nameLabel.setSize(100, 20);
        gbc.gridx++;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);

        int twoNamesOnly = 1;
        for (String name : listOfNames) {

            if (twoNamesOnly > 2) {
                break;
            }

            JTextField tname = new JTextField(name);
            tname.setFont(new Font("Arial", Font.PLAIN, 15));
            //tname.setSize(150, 20);
            gbc.gridx++;
            gbc.gridy = 0;
            panel.add(tname, gbc);

            nameFields.add(tname);
            twoNamesOnly++;
        }

        hiddenNameTable = null;
        //If the list of names is bigger than two they wont fit inside InfoElements
        //so it is needed to create a hidden list accessed by a button
        if (names.size() > 2) {
            //by pressing this button user will be able to see the full list of names
            //listOfNames = ; / excluding the first two that are displayed in the info form

            hiddenNameTable = new InfoPopUpTable(new ArrayList<String>(names.subList(2, names.size())));
            more = new javax.swing.JButton("more");
            gbc.gridx++;
            gbc.gridy = 0;
            panel.add(more, gbc);
            more.addActionListener((java.awt.event.ActionEvent evt) -> {
                //hiddenNameTable.getFrame();
                hiddenNameTable.getFrame().setVisible(true);
            });
        }


    }

    public ArrayList<String> getFieldContent() {
        ArrayList<String> output = new ArrayList<>();
        //every Info Panel returns it's fields in the exact order 1rst:filename,2cnd:names
        output.add(fileField.getText());
        nameFields.forEach(nameFiled -> {
            output.add(nameFiled.getText());
        });

        if (hiddenNameTable != null) {
            output.addAll(hiddenNameTable.getNames());
        }

        return output;
    }

    public JPanel getPanel() {
        return panel;
    }
}
