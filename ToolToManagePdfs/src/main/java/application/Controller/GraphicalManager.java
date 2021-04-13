/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template lfile, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controller;

import application.Model.Export;
import application.Model.Result;
import application.View.InfoPanel;
import application.View.Menu;
import application.View.ShowInfo;
import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author tamag
 */
public class GraphicalManager {

    MenuGui m;
    InfoGui i;

    public class MenuGui extends Menu {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle open button action.
            if (e.getSource() == open) {
                int returnVal = fc.showOpenDialog(MenuGui.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File[] files = fc.getSelectedFiles();

                    //This is where application begins proccesing the files.
//                    String result =
                    App.fileProccesing(files);
//                                    .toString();

//                    log.append(result);
                    // Manager.makeBackup();
                    displayInfoGui(Manager.getResults());
                    setVisible(false);
                    dispose();

//                    try {
//                        log.setText(result);
//                    } catch (Exception evt) {
//                        log.append("failed to open csv file for editing");
//                    }
                } //else {
//                    log.append("Open command cancelled by user.\n");
//                }
//                log.setCaretPosition(log.getDocument().getLength());
            }

        }

    }

    public class InfoGui extends ShowInfo {

        public List<InfoPanel> ipanels = new ArrayList<>();
        JScrollPane jScrollPane1;

        public void createInfoPanels(List<Result> results) {

            results.forEach(result -> {
                this.ipanels.add(new InfoPanel(result.getFileName(), result.getNames()));
            });
        }

        public InfoGui(List<Result> results) {
            createInfoPanels(results);
            JPanel contentPanel = new JPanel();
//            new BoxLayout(contentPanel, BoxLayout.Y_AXIS)

            jScrollPane1 = new JScrollPane();
            jScrollPane1.setSize(800, 300);
            jScrollPane1.setLocation(50, 100);
            //jScrollPane1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            GridBagLayout layout = new GridBagLayout();
            GridBagConstraints gbc = new GridBagConstraints();
            contentPanel.setLayout(layout);
            contentPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.ipady = 5;
            gbc.ipadx = 10;
            gbc.weightx = 1.0;
//            gbc.weighty = 1.0;
            for (InfoPanel panel : ipanels) {
                gbc.fill = GridBagConstraints.HORIZONTAL;
                contentPanel.add(panel.getPanel(), gbc);
                gbc.gridy++;
            }

            jScrollPane1.setViewportView(contentPanel);
            add(jScrollPane1);
            setLayout(null);
            this.getContentPane();
            this.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submit) {
                int i = 0;
                for (InfoPanel panel : ipanels) {
                    String fileName = panel.getFieldContent().get(0);
                    ArrayList<String> listOfNames = new ArrayList<>();
                    listOfNames.addAll(panel.getFieldContent().subList(1, panel.getFieldContent().size()));
                    Manager.modifyResults(new Result(fileName, listOfNames), i);
                    System.out.println();
                    i++;
                }
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showSaveDialog(InfoGui.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    Export.exportToCSV(Manager.getResults(), file);
                }
                dispose();
                displayMenuGui();

            } else if (e.getSource() == reset) {
                dispose();
                displayInfoGui(Manager.getResults());// update infopanels

            } else if (e.getSource() == back) {
                Manager.closeAll();
                displayMenuGui();
                setVisible(false);
                dispose();

            }
        }
    }

    public void displayMenuGui() {
        m = new MenuGui();
    }

    public void displayInfoGui(List<Result> results) {
        i = new InfoGui(results);
    }

//    public GraphicalManager() {
//        displayMenuGui();
//    }
}
