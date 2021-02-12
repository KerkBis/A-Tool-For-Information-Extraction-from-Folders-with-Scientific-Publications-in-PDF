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
        String currentDirectory = System.getProperty("user.dir");
            String relativePath = currentDirectory + "\\src\\main\\java\\application\\testDoc.pdf";

            DocumentEditor editor = new DocumentEditor(relativePath);

//        editor.editProperties("set subject", "Subject");
//        System.out.println("Some of the properties are: " + editor.getSubject());
        System.out.println(editor.getScannedText());
//test Apache Opnlp
            NameRecogniser nr = new NameRecogniser("The text below contains german words and loactions and name in english for the models to \n"
                    + "find.\n"
                    + "Dies John ist cm digitalcs Exemplar Greg eines Buches, das seit  London Generationen in den \n"
                    + "R^alen der Bibliotheken Greece aufbewahrt wurde, bevor Africa es von Google im");
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
