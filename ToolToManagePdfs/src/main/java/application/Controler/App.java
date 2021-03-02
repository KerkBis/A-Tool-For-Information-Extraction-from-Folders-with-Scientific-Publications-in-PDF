package application.Controler;

import application.Model.DocumentEditor;
import application.Model.NameRecogniser;
import application.View.Graphics;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
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
                    List<String> filePaths = new ArrayList<String>();
                    int counter = 0;
                    for (int i = 1; i < words.length; i++) {
                        filePaths.add(relativePath + words[i]);
                        counter++;
//                        System.out.println("read file:" + filePaths.get(i - 1));
                    }

                    try {
                        Manager.createEditors(counter, filePaths);
                    } catch (IOException ex) {
                        System.out.println("File given not found please try again");
                        break;
                    }
                    Manager.editors.forEach(editor -> {
                        System.out.println(">>>Names recognised for file: " + editor.getPath() + "<<<");
                        NameRecogniser nr = new NameRecogniser();
                        nr.filterTextByRegex(editor.getScannedText());
                    });
                    Manager.closeAllEditors();
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

    public static void main(String[] args) {

        System.out.println(">>>PdfOrganiser v0.1<<<");
        System.out.println("enter command >>");

        GUI s = new GUI();
        s.createAndShowGUI();


//        Scanner keyboard = new Scanner(System.in);
//        commandHandler(keyboard);
//        String currentDirectory = System.getProperty("user.dir");
//        String relativePath = currentDirectory + "\\src\\main\\java\\resources\\";
//        try {
//            DocumentEditor dc = new DocumentEditor(relativePath + "testDoc3.pdf");
//            dc.filterTextByRegex("fgcgvjh Kerk Bob hgvbjbj John Doe hjbj.Hello");
//        } catch (IOException ex) {
//            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
//        }

    }

    static class GUI extends Graphics {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle open button action.
            if (e.getSource() == openButton) {
                int returnVal = fc.showOpenDialog(GUI.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    log.append("Opening: " + file.getName() + "." + newline);
                    DocumentEditor editor;
                    try {
                        editor = new DocumentEditor(file.getPath());
                        NameRecogniser nr = new NameRecogniser();
                        log.append(nr.filterTextByRegex(editor.getScannedText()));
                    } catch (IOException ex) {
                        Logger.getLogger(Graphics.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    log.append("Open command cancelled by user." + newline);
                }
                log.setCaretPosition(log.getDocument().getLength());
            } else if (saveButton == e.getSource()) {
                int returnVal = fc.showOpenDialog(GUI.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    log.append("Saving: " + file.getName() + "." + newline);
                } else {
                    log.append("Save command cancelled by user." + newline);
                }
            }//

        }
    }

}
