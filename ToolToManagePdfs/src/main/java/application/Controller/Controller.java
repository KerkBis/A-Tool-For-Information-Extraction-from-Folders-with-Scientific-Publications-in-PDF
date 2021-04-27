/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template lfile, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controller;

import application.Model.Export;
import application.Model.Result;
import application.View.InfoElements;
import application.View.InfoPanel;
import application.View.Menu;
import application.View.ShowInfo;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;

/**
 *
 * @author tamag
 */
public class Controller {

    MenuGui m;
    InfoGui i;

    static int commandHandler(Scanner keyboard) {

        String currentDirectory = System.getProperty("user.dir");
        String relativePath = currentDirectory + "\\src\\main\\java\\resources\\";
        System.out.println(currentDirectory);

        String inputLine;
        String[] tokens;
        boolean extraction;
        boolean inputedFile;
        boolean outputedFile;
        boolean batchRename;
        File inFolder = null;
        File outFile = null;

        while (keyboard.hasNext()) {

            extraction = false;
            inputedFile = false;
            outputedFile = false;
            batchRename = false;

            inputLine = keyboard.nextLine();
            tokens = inputLine.split(" -");

            for (String s : tokens) {

                if (s.equals("pdfExtract")) {
                    extraction = true;
//                    break;
                } else if (s.contains("if")) {
                    String folderName = s.replaceAll("if ", "");
                    inFolder = new File(folderName);
                    inputedFile = true;
//                    break;
                } else if (s.contains("of")) {
                    String filename = s.replaceAll("of ", "");
                    outFile = new File(inFolder, filename);
                    outputedFile = true;
//                    break;
                } else if (s.contains("pdfBatchRename")) {
                    batchRename = true;
                } else if (s.equals("exit")) {
                    System.out.println("System exiting");
                    return 1;
                } else {
                    System.out.println("unrecognised command");
                }
            }
            if (extraction && inputedFile && outputedFile) {
                executeCommand(inFolder, outFile);
            } else if (batchRename && inputedFile) {
                List<String> ls = Export.readFromCSV(inFolder);

                File parent = inFolder.getParentFile();
                System.out.println("PFile: " + parent.getName());
                FilenameFilter pdfFilefilter = (File dir, String name) -> {
                    String lowercaseName = name.toLowerCase();
                    if (lowercaseName.endsWith(".pdf")) {
                        return true;
                    } else {
                        return false;
                    }
                };
                File fileList[] = parent.listFiles(pdfFilefilter);
                int i = 0;
                for (File file : fileList) {

                    File newName = new File(parent + "\\" + ls.get(i));
                    System.out.println("Renaming: " + file.getName() + " to: " + newName.getName());
                    if (file.renameTo(newName)) {
                        System.out.println("renamed");
                    } else {
                        System.out.println("Error");
                    }

                    i++;
                }

            }

        }
        return 1;
    }

    static int executeCommand(File inFolder, File outFile) {
        FilenameFilter pdfFilefilter = (File dir, String name) -> {
            String lowercaseName = name.toLowerCase();
            if (lowercaseName.endsWith(".pdf")) {
                return true;
            } else {
                return false;
            }
        };
        File fileList[] = inFolder.listFiles(pdfFilefilter);
        try {
            Manager.proccessing(fileList);
        } catch (Exception ex) {
            System.out.println("File not found");
            return 0;
        }
        System.out.println("pdfExtracted!!!");

        Export.exportToCSVv2(Manager.getResults(), outFile);

        return 1;
    }

    static StringBuffer fileProccesing(File[] files) {

        StringBuffer output = new StringBuffer();
        try {
            Manager.proccessing(files);
            output.append(Manager.printResults());
//            output.append(Manager.printName());
        } catch (Exception ex) {
            output.append("File not found");
        }

        return output;
    }

    public class MenuGui extends Menu {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle open button action.
            if (e.getSource() == open) {

            }
            int returnVal = fc.showOpenDialog(MenuGui.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();
                //This is where application begins proccesing the files.
                fileProccesing(files);

                Manager.makeBackup();
                displayInfoGui(Manager.getResults());
                setVisible(false);
                dispose();

            }

        }

    }

    public class InfoGui extends ShowInfo {

        public List<InfoElements> ipanels = new ArrayList<>();
        JScrollPane jScrollPane1;
        InfoPanel panel;

        public InfoGui(List<Result> results) {

            jScrollPane1 = new JScrollPane();
            jScrollPane1.setSize(800, 300);
            jScrollPane1.setLocation(50, 100);

            panel = new InfoPanel(results);
            jScrollPane1.setViewportView(panel);
            add(jScrollPane1);

            setLayout(null);
            this.getContentPane();
            this.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submit) {

                int i = 0;
                for (Result result : panel.getFieldContent()) {
                    Manager.modifyResults(result, i);
                    i++;
                };

                int returnVal = fc.showSaveDialog(InfoGui.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    Export.exportToCSV(Manager.getResults(), file);
                }
                Manager.closeAll();
                displayMenuGui();
                setVisible(false);
                dispose();

            } else if (e.getSource() == reset) {
                displayInfoGui(Manager.getResults());// update infopanels
                setVisible(false);
                dispose();
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

}
