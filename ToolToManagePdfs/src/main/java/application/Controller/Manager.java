/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controller;

import application.Model.DocumentEditor;
import application.Model.NameRecogniser;
import application.Model.Result;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tamag
 */
public class Manager {

    static List<DocumentEditor> editors = new ArrayList<DocumentEditor>();
    static List<NameRecogniser> nameRecognisers = new ArrayList<NameRecogniser>();
    final private static List<Result> results = new ArrayList<Result>();
    static List<Result> resultsBackUp;

    static int proccessing(File[] files) throws Exception {
        int i = 0;
        for (File file : files) {
            System.out.println(">>>Making editor for file:" + file.getName() + "<<<");
            editors.add(new DocumentEditor(file));
            //for every editor create a NameRecogniser to analyse it's scanned text
            String scannedText = editors.get(i).getScannedText();
            nameRecognisers.add(new NameRecogniser(scannedText));
            //for every NameRecogniser create a Result
            String fileName = editors.get(i).getFileName();
            ArrayList<String> names = nameRecognisers.get(i).findNames();
            results.add(new Result(fileName, names));

            i++;
        }
        return 0;
    }

    static void makeBackup() {
        resultsBackUp = new ArrayList<Result>();
        resultsBackUp = results;
    }

//    static void resetResults() {
//        results = resultsBackUp;
//    }

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
        nameRecognisers.clear();
        results.clear();

        return 0;
    }
}
