/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template lfile, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controller;

import application.Model.CSVeditor;
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

    static class PdfFileFilter implements FilenameFilter {

        @Override
        public boolean accept(File dir, String name) {
            String lowercaseName = name.toLowerCase();
            if (lowercaseName.endsWith(".pdf")) {
                return true;
            } else {
                return false;
            }
        }

    }

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
        boolean unrecognisedCmd;
        File inFolder = null;
        File outFile = null;

        while (keyboard.hasNext()) {

            extraction = false;
            inputedFile = false;
            outputedFile = false;
            batchRename = false;
            unrecognisedCmd = false;

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
                    unrecognisedCmd = true;
                    System.out.println("unrecognised command");
                }
            }
            if (executeCommand(extraction, inputedFile, outputedFile, batchRename,
                    unrecognisedCmd, inFolder, outFile) == 0) {
                System.out.println("System Error execution of command failed...exiting");
                return 0;
            }

        }
        return 1;
    }

    static int executeCommand(boolean extraction, boolean inputedFile, boolean outputedFile,
            boolean batchRename, boolean unrecognisedCmd,
            File inFolder, File outFile) {

        if (extraction && inputedFile && outputedFile) {
            Manager.closeAll();
            File fileList[] = inFolder.listFiles(new PdfFileFilter());
            handleProccessing(fileList);
            CSVeditor.exportToCSVv2(Manager.getResults(), outFile);
            return 1;
        } else if (batchRename && inputedFile) {
            handleBatchRename(inFolder);
            return 1;
        } else if (unrecognisedCmd) {
            return 1;
        }
        return 0;
    }

    static StringBuffer handleProccessing(File[] files) {

        StringBuffer output = new StringBuffer();
        try {
            Manager.proccessing(files);
            output.append(Manager.printResults());
//            output.append(Manager.printName());
        } catch (Exception ex) {
            output.append("File not found");
        }
        System.out.println("pdfExtracted!!!");
        return output;
    }

    static void handleBatchRename(File inFile) {

        List<String> listOfInfo;
        try {
            Manager.closeAll();
            listOfInfo = CSVeditor.readFromCSV(inFile);
            File parent = inFile.getParentFile();

            File fileList[] = parent.listFiles(new PdfFileFilter());

            int index = 0;
            for (File file : fileList) {
                String oldName = file.getName();
                String newName = listOfInfo.get(index); // first entry of csv line containing the file name
                String pathName = parent + "\\" + newName;

                File newFileName = new File(pathName);

                if (!oldName.equals(newName)) {

                    if (file.renameTo(newFileName)) {
                        System.out.println("Renaming: " + file.getName() + " to: " + newFileName.getName());
                    } else {
                        System.out.println("Renaming failed");
                    }
                }
                index++;
            }
        } catch (Exception ex) {
            System.out.println("Read csv file failed, please provide a valid csv file");
        }
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
                handleProccessing(files);

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
                    CSVeditor.exportToCSV(Manager.getResults(), file);
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
