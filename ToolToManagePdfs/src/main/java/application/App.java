package application;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) {
        try {
            //create dummy doc
            //DocumentCreation creator = new DocumentCreation();
            //creator.makeDoc("C:/Users/tamag/Desktop", "my_doc.pdf", "Tamag", "DocumentCreationTest");

            //test DocEditor edit and Read
//        DocumentEditor editor = new DocumentEditor("C:/Users/tamag/Desktop/sample.pdf");
//        editor.editProperties("set subject", "Subject");
//        System.out.println("Some of the properties are: " + editor.getSubject());
//        System.out.println(editor.getScannedText());
//test Apache Opnlp
            NameRecogniser nr = new NameRecogniser();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
