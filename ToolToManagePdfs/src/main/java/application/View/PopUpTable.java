/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.View;

import java.awt.Font;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author tamag
 */
public class PopUpTable {

    JFrame frame;
    JLabel jLabel1;
    JScrollPane jScrollPane1;
    JTable nameTable;
    JButton ok;


    public PopUpTable(ArrayList<String> listOfNames) {
        frame = new JFrame();
        frame.setTitle("Full list of Names");
        frame.setBounds(500, 200, 200, 400);

        jLabel1 = new JLabel("Names");
        jLabel1.setFont(new Font("Arial", Font.PLAIN, 30));
        jLabel1.setSize(300, 30);
        jLabel1.setLocation(10, 10);

        // Initializing the JTable
        InfoTable model = new InfoTable(listOfNames);
        nameTable = new JTable(model);
        nameTable.setBounds(30, 40, 200, 300);

        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setViewportView(nameTable);
        jScrollPane1.setSize(200, 300);
        jScrollPane1.setLocation(0, 50);

        ok = new JButton("Ok");
        ok.addActionListener((java.awt.event.ActionEvent evt) -> {
            frame.setVisible(false);
        });
        ok.setSize(
                60, 20);
        ok.setLocation(
                10, 350);

        frame.add(jLabel1);

        frame.add(jScrollPane1);

        frame.add(ok);

        frame.setLayout(
                null);
    }

    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<>();
        for (int i = 0; i < nameTable.getRowCount(); i++) {
            names.add(((String) nameTable.getValueAt(i, 0)));
        }
        return names;
    }


    public JFrame getFrame() {
        return frame;
    }

}
