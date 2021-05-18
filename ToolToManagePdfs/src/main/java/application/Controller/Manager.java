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
    static List<Result> resultsBackUp;
    static File[] fileList;

    static SwingWorker processing = new SwingWorker() {
        @Override
        public String doInBackground() throws Exception {
            // define what thread will do here
            int progress = 0;
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
            }

            String res = "Finished Execution";
            return res;
        }
    };

    static void setFiles(File[] files) {
        fileList = files;
    }

    static void LoadManager() throws Exception {
        //initialise NER and TER(Title Entities Recogniser)
        System.out.println("Loading please wait");
        nameRecogniser = new NameRecogniser();
        dateRecogniser = new DateRecogniser();
    }

    static int proccessing(File[] files) throws Exception {
//        File f = new File(files[0].getPath() + "scandText.txt");
//        FileWriter fw = new FileWriter(f);

        for (File file : files) {

            //System.out.println(">>>Making editor for file:" + file.getName() + "<<<");
            DocumentEditor docEditor = new DocumentEditor(file);
//            fw.append(docEditor.getScannedText());
//            fw.append("\n" + "--------------------------------------------------------------------" + " \n");
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

            //System.out.print("Progress: " +(int)getProgress()+"%"+"\r");
        }
//        fw.close();
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
        results = resultsBackUp;
    }

    static void modifyResults(Result element, int elementIndex) {
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

    static List<Result> getResults() {
        return results;
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

        return 0;
    }
}
