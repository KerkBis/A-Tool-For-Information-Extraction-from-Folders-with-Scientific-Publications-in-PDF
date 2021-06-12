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
    ArrayList<Entry> entries;
    JButton more;

    public class Entry {

        JTextField fileField;
        JTextField titleField;
        JTextField publicationTimeField;
        JTextField proposedNameField;
        //ArrayList<JTextField> nameFields = new ArrayList<>();
        InfoPopUpTable hiddenNamesTable = null;

        public Entry(String fileName, String title, String publicationTime, List<String> names, String proposedName) {
            this.fileField = new JTextField(fileName);
            this.titleField = new JTextField(title);
            this.publicationTimeField = new JTextField(publicationTime);
            this.proposedNameField = new JTextField(proposedName);
            this.hiddenNamesTable = new InfoPopUpTable(names);

        }

        public boolean hiddenNamesExists() {
            return hiddenNamesTable != null;
        }
    }

    public void createEntries(List<Result> results) {
        entries = new ArrayList<>();
        results.forEach(result -> {
            entries.add(new Entry(result.getFileName(), result.getTitle(), result.getPublicationDate(),
                    result.getNames(), result.getProposedName()));
        });
    }

    public void disposeEntries() {
        entries.forEach(entry -> {
            entry.hiddenNamesTable.dispose();
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

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new Label("FILE"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        this.add(new Label("TITLE"), gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        this.add(new Label("PUBLICATION DATE"), gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        this.add(new Label("PROPOSED NAME"), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        entries.forEach(entry -> {

            this.add(entry.fileField, gbc);
            gbc.gridx++;

            this.add(entry.titleField, gbc);
            gbc.gridx++;

            this.add(entry.publicationTimeField, gbc);
            gbc.gridx++;

            this.add(entry.proposedNameField, gbc);
            gbc.gridx++;

            if (entry.hiddenNamesExists()) {
                JButton button = new JButton("Names");
                button.addActionListener((e) -> {
                    entry.hiddenNamesTable.getFrame().setVisible(true);
                });
                this.add(button, gbc);
            }
            gbc.gridx = 0;
            gbc.gridy++;
        });

        setSize(800, 300);
        setVisible(true);
    }

    public List<Result> getFieldContent() {
        List<Result> results = new ArrayList<>();

        entries.forEach(entry -> {
            String fileName = entry.fileField.getText();
            String title = entry.titleField.getText();
            String publicationTime = entry.publicationTimeField.getText();
            String proposedName = entry.proposedNameField.getText();
            ArrayList<String> names = new ArrayList<>();
            if (entry.hiddenNamesExists()) {
                names.addAll(entry.hiddenNamesTable.getNames());
            }
            results.add(new Result(fileName, title, publicationTime, names, proposedName));

        });

        return results;
    }
}
