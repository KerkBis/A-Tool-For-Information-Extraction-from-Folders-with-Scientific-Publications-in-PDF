package application;

import java.awt.event.ActionEvent;
import java.io.File;
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
            Manager.createEditors(files);
            Manager.editors.forEach(editor -> {
                output.append(">>>In file: " + editor.getPath() + "<<<\n");
                NameRecogniser nr = new NameRecogniser(editor.getScannedText());
                try {
                    output.append(nr.findNames().toString());
                    Result.exportToCSV(nr.findNames());
                } catch (IOException ex) {
                    output.append("AI model failed to load");
                }
            });
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
                    log.append(fileProccesing(files).toString());

                } else {
                    log.append("Open command cancelled by user.\n");
                }
                log.setCaretPosition(log.getDocument().getLength());
            }
//            else if (saveButton == e.getSource()) {
//                int returnVal = fc.showOpenDialog(GUI.this);
//
//                if (returnVal == JFileChooser.APPROVE_OPTION) {
//                    File file = fc.getSelectedFile();
//                    //This is where a real application would open the file.
//                    log.append("Saving: " + file.getName() + "." + newline);
//                } else {
//                    log.append("Save command cancelled by user." + newline);
//                }
//            }//

        }
    }

    public static void main(String[] args) {

        System.out.println(">>>PdfOrganiser v0.1<<<");
//        System.out.println("enter command >>");
//
        GUI s = new GUI();
        s.createAndShowGUI();

//        JFrame frame = new JFrame("FileChooserDemo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 400);
//        frame.setVisible(true);
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

}
