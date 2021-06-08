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
                    System.out.println("Folder is not a directory");
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
                    if (evt.getPropertyName().equals("state") && evt.getNewValue() == SwingWorker.StateValue.DONE) {
                        System.out.println("\n Complete");

                    }
                }
            };
            //Manager.processing.addPropertyChangeListener(progress);
//            Manager.processing.execute();
//            Manager.processing.get();//wait for processing to finish
            // Manager.processing.removePropertyChangeListener(progress);
//-------Using Thread-----------------------------------------------------------------
            Manager.BackgroundProcessing bg = new Manager.BackgroundProcessing();
            bg.start();
            
            int curProgress =  bg.getProgress();
            int newProgress = curProgress;
             System.out.print("Proccessing: " + curProgress + "%\r");
            while (bg.isAlive()) {
                if (curProgress != newProgress) {
                    curProgress = newProgress;
                    System.out.print("Proccessing: " + curProgress + "%\r");
                }
                newProgress = bg.getProgress();
            }

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
                return 1;
            }

            int index = 0;
            for (File file : fileList) {
                String oldName = file.getName();
                String newName = changedFileNames.get(index); // first entry of csv line containing the file name
                String pathName = outFile + "\\" + newName;

                File newFileName = new File(pathName);

                if (!oldName.equals(newName)) {
                    System.out.println("trying to rename: " + file.getName() + " to: " + newFileName.getName());
                    if (file.renameTo(newFileName)) {
                        System.out.println("Rename Success");
                    } else {
                        System.out.println("Renaming failed");
                    }
                }
                index++;
            }
        

        return 1;

    }

    public void executePro(File[] files) {
//        BackgroundProcessing task = new BackgroundProcessing(files);
//        task.execute();
//        task.addPropertyChangeListener((PropertyChangeEvent evt) -> {
//            if (evt.getPropertyName().equals("state") && evt.getNewValue() == SwingWorker.StateValue.DONE) {
//                System.out.println("Task finished");
//                Manager.makeBackup();
//                displayInfoGui(Manager.getResults());
//            }
//        });

    }

    public class MenuGui extends Menu {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle open button action.
            int returnVal;
            if (e.getSource() == open) {
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

                    Manager.setFiles(files);
                    Manager.processing.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                        if (evt.getPropertyName().equals("progress")) {
                            progressBar.setValue((int) evt.getNewValue());
                        }
                    });
                    Manager.processing.execute();
                    Manager.processing.addPropertyChangeListener((PropertyChangeEvent evt) -> {
                        if (evt.getPropertyName().equals("state") && evt.getNewValue() == SwingWorker.StateValue.DONE) {
                            System.out.println("Task finished");
                            Manager.makeBackup();
                            displayInfoGui(Manager.getResults());
                            loadingFrame.setVisible(false);
                            loadingFrame.dispose();
                        }
                    });

                    dispose();
                }
            } else if (e.getSource() == batchRename) {

                fc.setMultiSelectionEnabled(false);
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                FileFilter csvType = new ExtensionFileFilter("csv", rootPaneCheckingEnabled);
                fc.setFileFilter(csvType);
                fc.setAcceptAllFileFilterUsed(false);

                returnVal = fc.showOpenDialog(MenuGui.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File inFile = fc.getSelectedFile();

                    fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    returnVal = fc.showOpenDialog(MenuGui.this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File outFile = fc.getSelectedFile();
                        if (outFile.isDirectory()) {
                            handleBatchRename(inFile, outFile);
                        } else {
                            JOptionPane.showMessageDialog(null, "Folder is not a directory");
                        }
                    }
                }

            }
        }
    }

    public class InfoGui extends ShowInfo {

        public List<InfoElements> ipanels = new ArrayList<>();
        JButton batchRename;
        JScrollPane jScrollPane1;
        InfoPanel panel;

        public InfoGui(List<Result> results) {

            batchRename = new JButton("automatic raname");
            batchRename.setFont(new Font("Arial", Font.PLAIN, 15));
            batchRename.setSize(40, 30);
            batchRename.setLocation(50, 10);
            batchRename.addActionListener(this);
            this.add(batchRename);

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
                    CSVeditor.exportToCSVv2(Manager.getResults(), file);
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
