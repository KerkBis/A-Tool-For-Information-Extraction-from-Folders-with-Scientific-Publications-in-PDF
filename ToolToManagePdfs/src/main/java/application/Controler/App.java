package application.Controler;

import application.Model.NameRecogniser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {

    static int commandHandler() {

        Scanner keyboard = new Scanner(System.in);

        String currentDirectory = System.getProperty("user.dir");
        String relativePath = currentDirectory + "\\src\\main\\java\\resources\\";

        // System.out.println(editor.getScannedText());
        String inputLine = keyboard.nextLine();
        String[] words = inputLine.split("\\s+");

        switch (words[0]) {
            case "findNames": {
                List<String> filePaths = new ArrayList<String>();
                int counter = 0;
                for (int i = 1; i < words.length; i++) {
                    filePaths.add(relativePath + words[i]);
                    counter++;
                    System.out.println("read file:" + filePaths.get(i - 1));
                }

                Manager.createEditors(counter, filePaths);
                Manager.editors.forEach(editor -> {
                    System.out.println(">>>In file: " + editor.getPath());
                    nameFinder(editor.getScannedText());
                });
                }
                case "exit": {
                    System.out.println(">>>exiting PdfOrganiser<<<");
                    return 0;
                }
                default: {
                    System.out.println(">>>unrecognised command<<<");
                }

        }


        return 1;
    }

    public static void main(String[] args) {

        System.out.println(">>>PdfOrganiser v0.1<<<");
        System.out.println("enter command >>");

        commandHandler();
        System.out.println("Yoooo11");

    }

    static private int nameFinder(String text) {
        try {
            NameRecogniser nr = new NameRecogniser(text);
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
        return 1;
    }
}
