package application.Controller;

import application.View.Graphics;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 * Hello world!
 *
 */
public class App {

    static int commandHandler(Scanner keyboard) {

        String currentDirectory = System.getProperty("user.dir");
        String relativePath = currentDirectory + "\\src\\main\\java\\resources\\";

        String inputLine;
        String[] words;

        while (keyboard.hasNext()) {
            inputLine = keyboard.nextLine();
            words = inputLine.split("\\s+");

            switch (words[0]) {
                case "findNames": {

                    File[] files = new File[words.length];
                    for (int i = 1; i < words.length; i++) {
                        files[i] = new File(relativePath + words[i]);
                        //System.out.println("read file:" + filePaths.get(i - 1));
                    }

                    System.out.println(fileProccesing(files));
                    break;
                }
                case "exit": {
                    System.out.println(">>>exiting PdfOrganiser<<<");
                    Manager.closeAllEditors();
                    return 1;
                }
                default: {
                    System.out.println(">>>unrecognised command<<<");
                }

            }

        }
        return 1;
    }

    static StringBuffer fileProccesing(File[] files) {

        StringBuffer output = new StringBuffer();
        try {
            Manager.proccessing(files);
            output.append(Manager.printResults());
//            output.append(Manager.printName());
        } catch (IOException ex) {
            output.append("File not found");
        }

        Manager.closeAllEditors();
        return output;
    }

    static class GUI extends Graphics {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle open button action.
            if (e.getSource() == openButton) {
                int returnVal = fc.showOpenDialog(GUI.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File[] files = fc.getSelectedFiles();

                    //This is where application begins proccesing the files.
                    String result = fileProccesing(files).toString();

                    log.append(result);

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
//                        // Take the input from the file
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
            } else if (e.getSource() == saveButton) {
                // Create an object of JFileChooser class
                JFileChooser j = new JFileChooser("f:");

                // Invoke the showsSaveDialog function to show the save dialog
                int r = j.showSaveDialog(null);

                if (r == JFileChooser.APPROVE_OPTION) {

                    // Set the label to the path of the selected directory
                    File fi = new File(j.getSelectedFile().getAbsolutePath());

                    try {
                        // Create a file writer
                        FileWriter wr = new FileWriter(fi, false);

                        // Create buffered writer to write
                        BufferedWriter w = new BufferedWriter(wr);

                        // Write
                        w.write(log.getText());

                        w.flush();
                        w.close();
                    } catch (Exception evt) {
                        log.append(evt.getMessage());
                    }
                } // If the user cancelled the operation
                else {
                    log.append("the user cancelled the operation");
                }
            }

        }
    }

    public static void main(String[] args) {

        System.out.println(">>>PdfOrganiser v0.1<<<");
//        System.out.println("enter command >>");
////
        GUI s = new GUI();
        s.createAndShowGUI();

//
//        editor e = new editor();

//        JFrame frame = new JFrame("FileChooserDemo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 400);
//        frame.setVisible(true);
//        Scanner keyboard = new Scanner(System.in);
//        commandHandler(keyboard);

//        String currentDirectory = System.getProperty("user.dir");
//        String relativePath = currentDirectory + "\\src\\main\\java\\resources\\";
//        File f = new File(relativePath + "testDoc2.pdf");
//        DocumentEditor dc = new DocumentEditor(f);
//        NameRecogniser nm = new NameRecogniser(dc.getPath(), dc.getScannedText());
//        ArrayList<String> filterTextByRegex = nm.filterTextByRegex();
//        String exportToCSV = Export.exportToCSV(filterTextByRegex);
//        System.out.println("the ouput: \n" + exportToCSV);
    }

}
