/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template lfile, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controller;

import application.Model.CSVeditor;
import application.Model.Result;
import application.View.InfoPanel;
import application.View.Menu;
import application.View.ShowInfo;
import edu.stanford.nlp.io.ExtensionFileFilter;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

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
                    outFile = new File(filename);
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
//            ExecThread ect = new ExecThread(extraction, inputedFile, outputedFile,
//                    batchRename, unrecognisedCmd, inFolder, outFile);
//            ect.start();
//            while (ect.isAlive()) {
//
//            }
            if (executeCommands(extraction, inputedFile, outputedFile, batchRename,
                    unrecognisedCmd, inFolder, outFile) == 0) {
                System.out.println("System Error execution of command failed...exiting");
                return 0;
            }

        }
        return 1;
    }

    static int executeCommands(boolean extraction, boolean inputedFile, boolean outputedFile,
            boolean batchRename, boolean unrecognisedCmd,
            File inFolder, File outFile) {

        if (extraction && inputedFile && outputedFile) {
            Manager.closeAll();
            try {
                File fileList[] = inFolder.listFiles(new PdfFileFilter());
                if (fileList.length == 0) {
                    System.out.println("folder has no pdf files");
                    return 1;
//                        state = 1;
                }
                handleProccessing(fileList);

                CSVeditor.exportToCSVv2(Manager.getResults(), outFile);
            } catch (NullPointerException ne) {
                System.out.println("File not found");
            }
            return 1;
//                state = 1;
        } else if (batchRename && inputedFile && outputedFile) {
            try {
                if (outFile.isDirectory()) {
                    handleBatchRename(inFolder, outFile);
                } else {
                    System.out.println("No directory or does not exist");
                }
                return 1;
//                    state = 1;
            } catch (NullPointerException ne) {
                System.out.println("File not found");
            }

        } else if (unrecognisedCmd) {
            return 1;
//                state = 1;
        }
        return 0;
//            state = 0;
    }

    static int handleProccessing(File[] files) {

        StringBuffer output = new StringBuffer();
        int done = 0;
        try {
            Manager.closeAll();
            Manager.setFiles(files);
//-------Using Swing Worker-----------------------------------------------------------------

            PropertyChangeListener progress = new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (evt.getPropertyName().equals("progress")) {
                        System.out.print("Proccessing: " + (int) evt.getNewValue() + "%\r");
                    }
                }
            };
            Manager.startBackgroundProcessing(progress).execute();
            Manager.getFileProcessTask().get();
//-------Using Thread-----------------------------------------------------------------
//            Manager.BackgroundFileProcess bg = new Manager.BackgroundFileProcess();
//            bg.start();
//            int curProgress = bg.getProgress();
//            int newProgress = curProgress;
//            System.out.print("Proccessing: " + curProgress + "%\r");
//            while (bg.isAlive()) {
//                if (curProgress != newProgress) {
//                    curProgress = newProgress;
//                    System.out.print("Proccessing: " + curProgress + "%\r");
//                }
//                newProgress = bg.getProgress();
//            }
//--------------------------------------------------------------------------------------

            Manager.makeBackup();

        } catch (Exception ex) {
            System.out.println("File not found");
        }
        return 1;
    }

    static int handleBatchRename(File inFile, File outFile) {

        List<String> changedFileNames = null;

        Manager.closeAll();
        try {
            changedFileNames = CSVeditor.readFirstElemFromCSV(inFile);
        } catch (Exception ex) {
            System.out.println("Read csv file failed, invalid csv file or target folder ");
            return 0;
        }

        File fileList[] = outFile.listFiles(new PdfFileFilter());

        if (fileList.length != changedFileNames.size()) {
            System.out.println("BatchRename can not be applied to this folder");
            return 2;
        }

        int index = 0;
        for (File file : fileList) {
            String oldName = file.getName();
            String newName = changedFileNames.get(index); // first entry of csv line containing the file name
            String pathName = outFile + "\\" + newName;

            File newFileName = new File(pathName);

            if (!oldName.equals(newName)) {
                if (file.renameTo(newFileName)) {
                    System.out.println("Renamed " + file.getName() + " to: " + newFileName.getName());
                } else {
                    System.out.println("Renaming failed");
                }
            }
            index++;
        }

        return 1;

    }

    public class MenuGui extends Menu {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle open button action.
            int returnVal;
            if (e.getSource() == open) {
                initFileChooserPdf();
                returnVal = fc.showOpenDialog(MenuGui.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File[] files = fc.getSelectedFiles();
                    //This is where application begins proccesing the files.
                    if (files.length == 1 && files[0].isDirectory()) {
                        files = files[0].listFiles(new PdfFileFilter());
                        if (files.length == 0) {
                            JOptionPane.showMessageDialog(null, "folder has no pdf files");
                        }
                    }
                    setVisible(false);
                    JFrame loadingFrame = new JFrame("Loading");
                    loadingFrame.setBounds(300, 90, 500, 100);
                    loadingFrame.setLocationRelativeTo(null);
                    JProgressBar progressBar = new JProgressBar(0, 100);
                    progressBar.setStringPainted(true);
                    loadingFrame.add(progressBar);
                    loadingFrame.setVisible(true);

                    PropertyChangeListener listener = new PropertyChangeListener() {
                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if (evt.getPropertyName().equals("progress")) {
                                progressBar.setValue((int) evt.getNewValue());
                            }
                            if (evt.getPropertyName().equals("state") && evt.getNewValue() == SwingWorker.StateValue.DONE) {

                                Manager.makeBackup();
                                displayInfoGui(Manager.getResults());
                                loadingFrame.setVisible(false);
                                loadingFrame.dispose();
                            }
                        }
                    };

                    Manager.setFiles(files);
                    Manager.startBackgroundProcessing(listener).execute();

//With Thread-------------------------------------------------------------------------------------
//                    int curProgress = bg.getProgress();
//                    int newProgress = curProgress;
//                    progressBar.setValue(curProgress);
//                    while (bg.isAlive()) {
//                        if (curProgress != newProgress) {
//                            curProgress = newProgress;
//                            progressBar.setValue(curProgress);
//                        }
//                        newProgress = bg.getProgress();
//                    }
//
//                    System.out.println("Task finished");
//                    Manager.makeBackup();
//                    displayInfoGui(Manager.getResults());
//                    loadingFrame.setVisible(false);
//                    loadingFrame.dispose();
//-------------------------------------------------------------------------------------------
                    dispose();
                }
            } else if (e.getSource() == batchRename) {

                initFileChooserCsv();
                returnVal = fc.showOpenDialog(MenuGui.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File inFile = fc.getSelectedFile();

                    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    returnVal = fc.showOpenDialog(MenuGui.this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File outFile = fc.getSelectedFile();
                        switch (handleBatchRename(inFile, outFile)) {
                            case 0:
                                JOptionPane.showMessageDialog(null, "Read csv file failed, invalid csv file or target folder");
                                break;
                            case 2:
                                JOptionPane.showMessageDialog(null, "BatchRename can not be applied to this folder");
                                break;
                            default:
                                JOptionPane.showMessageDialog(null, "BatchRename successfull");
                        }
                    }
                    
                }

            }
        }
    }

    public class InfoGui extends ShowInfo {

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
                    Manager.modifyResults(i, result);
                    i++;
                };

                int returnVal = fc.showSaveDialog(InfoGui.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    CSVeditor.exportToCSVv2(Manager.getResults(), file);
                }
                Manager.closeAll();
                displayMenuGui();
                setVisible(false);
                panel.disposeEntries();
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
