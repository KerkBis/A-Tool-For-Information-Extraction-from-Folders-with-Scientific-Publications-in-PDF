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
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;

/**
 *
 * @author tamag
 */
public class GraphicalManager {

    public class MenuGui extends Menu {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle open button action.
            if (e.getSource() == open) {
                int returnVal = fc.showOpenDialog(MenuGui.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File[] files = fc.getSelectedFiles();

                    //This is where application begins proccesing the files.
                    String result = App.fileProccesing(files).toString();

                    log.append(result);
                    Manager.makeBackup();
                    displayInfoGui(Manager.getResults());
                    //Manager.closeAll();
                    try {
//                        // String
//                        String s1 = "", sl = "";
//
//                        // File reader
//                        FileReader fr = new FileReader(System.getProperty("user.dir") + "csv");
//
//                        // Buffered reader
//                        BufferedReader br = new BufferedReader(fr);
//
//                        // Initilize sl
//                        sl = br.readLine();
//
//                        // Take the input from the lfile
//                        while ((s1 = br.readLine()) != null) {
//                            sl = sl + "\n" + s1;
//                        }

                        // Set the text
                        log.setText(result);
                    } catch (Exception evt) {
                        log.append("failed to open csv file for editing");
                    }

                } else {
                    log.append("Open command cancelled by user.\n");
                }
                log.setCaretPosition(log.getDocument().getLength());
            }

        }

    }

    public class InfoGui extends ShowInfo {

        public List<InfoPanel> ipanels = new ArrayList<>();

        public void createInfoPanels(List<Result> results) {

            for (Result result : results) {
                this.ipanels.add(new InfoPanel(result.getFileName(), result.getNames()));
            }
        }

        public InfoGui(List<Result> results) {
            //super();

//            ArrayList<String> als = new ArrayList<String>();
//            als.add("Kerk");
//            als.add("Bob");
//            als.add("john");
//            InfoPanel panel = new InfoPanel("fileName", als);
//            panel.setSize(300, 50);
//            this.add(panel);
            createInfoPanels(results);

            int moveDown = 0;
            for (InfoPanel panel : ipanels) {
                panel.getPanel().setSize(500, 60);
                panel.getPanel().setLocation(50, 100 + moveDown);
                moveDown = moveDown + 70;
                add(panel.getPanel());
            }

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
                Manager.getResults().forEach(result -> {
                    System.out.println("Filename: " + result.getFileName() + " Names: " + result.getNames() + "\n");

                    JFileChooser fc = new JFileChooser();

                    int returnVal = fc.showOpenDialog(InfoGui.this);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        Export.exportToCSV(result, file);
                    }
                });

            } else if (e.getSource() == reset) {
                Manager.resetResults();
                // update infopanels
            }
        }
    }

    public void displayMenuGui() {
        MenuGui m = new MenuGui();
    }

    public void displayInfoGui(List<Result> results) {
        InfoGui i = new InfoGui(results);
    }
}
