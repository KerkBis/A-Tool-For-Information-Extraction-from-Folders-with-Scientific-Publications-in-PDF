/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.View;

import application.Model.Result;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author tamag
 */
public class InfoPanel extends JPanel {

    JPanel panel;
    JLabel fileLabel;
    JLabel nameLabel;
    ArrayList<JTextField> nameFields = new ArrayList<>();
    ArrayList<Entry> entries = new ArrayList<>();
    JButton more;

    public class Entry {

        JTextField fileField;
        ArrayList<JTextField> nameFields = new ArrayList<>();
        InfoPopUpTable hiddenNamesTable = null;

        public Entry(String fileName, List<String> names) {
            this.fileField = new JTextField(fileName);

            if (names.size() > 2) {
                this.nameFields.add(new JTextField(names.get(0)));
                this.nameFields.add(new JTextField(names.get(1)));
                this.hiddenNamesTable = new InfoPopUpTable(names.subList(2, names.size()));
            } else {
                for (String name : names) {
                    this.nameFields.add(new JTextField(name));
                }
            }
        }

        public boolean hiddenNamesExists() {
            return hiddenNamesTable != null;
        }
    }

    public void createEntries(List<Result> results) {
        results.forEach(result -> {
            entries.add(new Entry(result.getFileName(), result.getNames()));
        });
    }

    public InfoPanel(List<Result> results) {

        GridBagConstraints gbc = new GridBagConstraints();
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 50;
        //gbc.weighty = 25;
        gbc.gridx = 0;
        gbc.gridy = 0;

        createEntries(results);

        entries.forEach(entry -> {
            this.add(new Label("FILE"), gbc);
            gbc.gridx++;

            this.add(entry.fileField, gbc);
            gbc.gridx++;

            this.add(new Label("NAMES"), gbc);
            gbc.gridx++;

            entry.nameFields.forEach((nameField) -> {
                this.add(nameField, gbc);
                gbc.gridx++;
            });
            if (entry.hiddenNamesExists()) {
                JButton b = new JButton("more");
                b.addActionListener((e) -> {
                    entry.hiddenNamesTable.getFrame().setVisible(true);
                });
                this.add(b, gbc);
            }
            gbc.gridx = 0;
            gbc.gridy++;
        });

//        for (Result result : results) {
//            this.add(new Label("FILE"), gbc);
//
//            gbc.gridx++;
//            JTextField tfile = new JTextField(result.getFileName());
//            this.add(tfile, gbc);
//            gbc.gridx++;
//            this.add(new Label("NAMES"), gbc);
//            gbc.gridx++;
//            int counter = 1;
//            for (String name : result.getNames()) {
//
//                JTextField tname = new JTextField(name);
//                this.add(tname, gbc);
//                nameFields.add(tname);
//                gbc.gridx++;
//
//                if (counter > 2) {
//                    InfoPopUpTable hiddenNameTable = new InfoPopUpTable(new ArrayList<String>(
//                            result.getNames().subList(
//                                    2, result.getNames().size()
//                            )
//                    )
//                    );
//                    JButton b = new JButton("more");
//                    b.addActionListener((e) -> {
//                        hiddenNameTable.getFrame().setVisible(true);
//                    });
//                    this.add(b, gbc);
//                    entries.add(new Entry(tfile, nameFields, hiddenNameTable));
//                    nameFields.clear();
//                    break;
//                }
//                counter++;
//            }
//
//            gbc.gridx = 0;
//            gbc.gridy++;
//        }
        setSize(800, 300);
//        setPreferredSize(getSize());
        setVisible(true);
    }

    public List<Result> getFieldContent() {
        List<Result> results = new ArrayList<>();
        entries.forEach(entry -> {
            String fileName = entry.fileField.getText();
            ArrayList<String> names = new ArrayList<>();
            entry.nameFields.forEach((nameField) -> {
                if (!nameField.getText().equals(""))
                names.add(nameField.getText());
            });
            if (entry.hiddenNamesExists()) {
                names.addAll(entry.hiddenNamesTable.getNames());
            }

            results.add(new Result(fileName, names));
        });

        return results;
    }
}
