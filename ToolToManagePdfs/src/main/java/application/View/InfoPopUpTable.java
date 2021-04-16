/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.View;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author tamag
 */
public class InfoPopUpTable {

    JFrame frame;
    JLabel jLabel1;
    JScrollPane jScrollPane1;
    JTable nameTable;
    JButton ok;

    public InfoPopUpTable(List<String> listOfNames) {
        frame = new JFrame();
        frame.setTitle("Full list of Names");
        frame.setBounds(500, 250, 200, 450);
        frame.setLayout(null);

//        jLabel1 = new JLabel("Names");
//        jLabel1.setFont(new Font("Arial", Font.PLAIN, 11));
//        jLabel1.setSize(300, 30);
//        jLabel1.setLocation(10, 10);

        // Initializing the JTable
        NameTable model = new NameTable(listOfNames);
        nameTable = new JTable(model);
        nameTable.setSize(200, 300);
        nameTable.setLocation(10, 10);

        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setViewportView(nameTable);
        jScrollPane1.setSize(200, 300);
        jScrollPane1.setLocation(0, 50);

        ok = new JButton("Ok");
        ok.addActionListener((java.awt.event.ActionEvent evt) -> {
            frame.setVisible(false);
        });
        ok.setSize(60, 20);
        ok.setLocation(10, 350);

        //frame.add(jLabel1);

        frame.add(jScrollPane1);

        frame.add(ok);

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
