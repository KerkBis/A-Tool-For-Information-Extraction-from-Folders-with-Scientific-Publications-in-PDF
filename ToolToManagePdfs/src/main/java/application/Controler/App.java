package application.Controler;

import application.Model.NameRecogniser;
import application.Model.DocumentEditor;
import java.io.IOException;
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
        while (keyboard.hasNext()) {
            switch (keyboard.next()) {
                case "findNames": {
                    DocumentEditor editor = new DocumentEditor(relativePath + keyboard.next());
                    nameFinder(editor.getScannedText());
                }
                case "exit": {
                    System.out.println(">>>exiting PdfOrganiser<<<");
                    return 0;
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


        commandHandler();

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
