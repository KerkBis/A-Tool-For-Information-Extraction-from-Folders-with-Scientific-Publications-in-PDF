package application;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        //DocumentCreation creator = new DocumentCreation();
        //creator.makeDoc("C:/Users/tamag/Desktop", "my_doc.pdf", "Tamag", "DocumentCreationTest");
        DocumentEditor editor = new DocumentEditor("C:/Users/tamag/Desktop/sample.pdf");

        System.out.println("Some of the properties are: " + editor.getAuthor());
        System.out.println(editor.getScannedText());
    }
}
