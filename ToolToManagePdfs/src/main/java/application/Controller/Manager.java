/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controller;

import application.Model.DateRecogniser;
import application.Model.DocumentEditor;
import application.Model.NameRecogniser;
import application.Model.Result;
import application.Model.TitleRecogniser;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingWorker;

/**
 *
 * @author tamag
 */
public class Manager {

    static List<DocumentEditor> editors = new ArrayList<DocumentEditor>();
    static NameRecogniser nameRecogniser;
    static TitleRecogniser titleRecongniser;
    static DateRecogniser dateRecogniser;
    private static List<Result> results = new ArrayList<Result>();
    static List<Result> resultsBackUp = null;
    static File[] fileList;
    static SwingWorker fileProcessTask;

    static void LoadManager() throws Exception {
        //initialise NER and TER(Title Entities Recogniser)
        System.out.println("Chopping wood carying water");
        nameRecogniser = new NameRecogniser();
        dateRecogniser = new DateRecogniser();
        System.out.println("Program is ready");

    }

    static void setFiles(File[] files) {
        fileList = files;
    }

    static SwingWorker processing = new SwingWorker() {
        @Override
        public String doInBackground() throws Exception {

            int progress = 0;
            setProgress(progress);

            for (File file : fileList) {
                DocumentEditor docEditor = new DocumentEditor(file);
                editors.add(docEditor);
                //save the file's properties
                String author = docEditor.getAuthor();
                //check if title property of file exists and if not extract it from content
                String title = docEditor.getTitle();
                if (title == null) {
                    title = TitleRecogniser.findTitle(file);
                }
                //look for date in txt
                dateRecogniser.setText(docEditor.getScannedText());
                String date = dateRecogniser.findDate().toString();//array to string will return [date1,date2,date3]
                date = date.replaceAll("\\[", "");
                date = date.replaceAll("\\]", "");
                if (date.equals("")) {//if date is an empty converted array to string
                    date = docEditor.getCreationDate().getTime().toString();//get creattion
                    //System.out.println("Creation Date:" + date);
                }

                String proposedName = date + " " + title;

                generateResults(docEditor.getScannedText(), docEditor.getFileName(),
                        title, date, author, proposedName);

                Thread.sleep(100);
                progress++;
                setProgress(100 * progress / fileList.length);
                publish(100 * progress / fileList.length);
            }

            return "Done";

        }

        @Override
        protected void process(List chunks) {
            System.out.println("Processing: " + chunks.get(chunks.size() - 1) + "%");
        }

        @Override
        public void done() {
            System.out.println("Proccessing Complete");
        }
    };

    static class BackgroundFileProcess extends Thread {

        private int workCounter = 0;
        private int progress = 0;

        public int getProgress() {
            return progress;
        }

        @Override
        public void run() {
            workCounter = 0;
            for (File file : fileList) {
                try {
                    DocumentEditor docEditor = new DocumentEditor(file);
                    editors.add(docEditor);
                    //save the file's properties
                    String author = docEditor.getAuthor();
                    //check if title property of file exists and if not extract it from content
                    String title = docEditor.getTitle();
                    if (title == null) {
                        title = TitleRecogniser.findTitle(file);
                    }
                    //look for date in txt
                    dateRecogniser.setText(docEditor.getScannedText());
                    String date = dateRecogniser.findDate().toString();//array to string will return [date1,date2,date3]
                    date = date.replaceAll("\\[", "");
                    date = date.replaceAll("\\]", "");
                    if (date.equals("")) {//if date is an empty converted array to string
                        date = docEditor.getCreationDate().getTime().toString();//get creattion
                        //System.out.println("Creation Date:" + date);
                    }

                    String proposedName = date + " " + title;

                    generateResults(docEditor.getScannedText(), docEditor.getFileName(),
                            title, date, author, proposedName);

                    Thread.sleep(100);
                    workCounter++;
                    progress = 100 * workCounter / fileList.length;
                } catch (Exception ex) {
                    Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    static SwingWorker startBackgroundProcessing(PropertyChangeListener listener) {
        fileProcessTask = new SwingWorker() {
            @Override
            protected Object doInBackground() throws Exception {
                BackgroundFileProcess bg = new BackgroundFileProcess();
                bg.start();
                while (bg.isAlive()) {
                    setProgress(bg.getProgress());
                }
                bg.join();
                return bg;
            }

            @Override
            protected void done() {
                try {
                    super.get();
                    System.out.println("Processing complete ");
                    //can call other gui update code here
                } catch (Throwable t) {
                    //do something with the exception
                    System.out.println("Background proccess failed");
                }
            }
        };
        fileProcessTask.addPropertyChangeListener(listener);
        return fileProcessTask;
    }

    public static SwingWorker getFileProcessTask() {
        return fileProcessTask;
    }

    static int processing() throws Exception {
        int workCounter = 0;
        int progress = 0;

        for (File file : fileList) {
            try {
                DocumentEditor docEditor = new DocumentEditor(file);
                editors.add(docEditor);
                //save the file's properties
                String author = docEditor.getAuthor();
                //check if title property of file exists and if not extract it from content
                String title = docEditor.getTitle();
                if (title == null) {
                    title = TitleRecogniser.findTitle(file);
                }
                //look for date in txt
                dateRecogniser.setText(docEditor.getScannedText());
                String date = dateRecogniser.findDate().toString();//array to string will return [date1,date2,date3]
                date = date.replaceAll("\\[", "");
                date = date.replaceAll("\\]", "");
                if (date.equals("")) {//if date is an empty converted array to string
                    date = docEditor.getCreationDate().getTime().toString();//get creattion
                    //System.out.println("Creation Date:" + date);
                }

                String proposedName = date + " " + title;

                generateResults(docEditor.getScannedText(), docEditor.getFileName(),
                        title, date, author, proposedName);

                Thread.sleep(100);
                workCounter++;
                progress = 100 * workCounter / fileList.length;
            } catch (Exception ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    public static void generateResults(String scannedText, String fileName,
            String title, String publicationDate, String author, String proposedName) throws Exception {
        //Analyse text with NER 
        nameRecogniser.setText(scannedText);
        nameRecogniser.findNames();
        //and create list of Results
        ArrayList<String> names = nameRecogniser.findNames();
        if (author != null) {
            names.add(author);
        }
        results.add(new Result(fileName, title, publicationDate, names, proposedName));
    }

    static void makeBackup() {
        resultsBackUp = new ArrayList<>();
        resultsBackUp = results;
    }

    static void resetResults() {
        if (resultsBackUp != null) {
            results = resultsBackUp;
        } else {
            System.out.println("ResetResults failed: no back up exists");
        }

    }

    static void modifyResults(int elementIndex, Result element) {
        results.set(elementIndex, element);
    }

    public static String printResults() {
        String output = new String();
        for (Result result : results) {
//            output = output + "For file: " + result.getFileName() + " Names found: " + result.getNames() + "\n";
            output = output + "For file: " + result.getFileName() + " Names found: " + result.getNames() + "\n";
        }
        return output + "--------------------------------";
    }

    static String printText() {
        String output = new String();
        for (DocumentEditor editor : editors) {
            output = output + "For file: " + editor.getFileName() + " Scanned text: " + editor.getScannedText() + "\n";
        }
        return output;
    }

    static String printName() {
        String output = new String();
        for (DocumentEditor editor : editors) {
            output = output + "For file: " + editor.getFileName() + " Authors are: " + editor.getAuthor() + "\n";
        }
        return output;
    }

    static ArrayList<Result> getResults() {
        return new ArrayList<Result>(results);
    }

    static int closeAll() {
        editors.forEach(editor -> {
            try {
                editor.close();

            } catch (IOException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        editors.clear();
        results.clear();
        fileList = null;

        return 0;
    }
}
