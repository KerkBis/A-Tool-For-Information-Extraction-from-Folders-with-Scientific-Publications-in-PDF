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
        DocumentReader reader = new DocumentReader("C:/Users/tamag/Desktop/sample.pdf");

        System.out.println("Some of the properties are: " + reader.getAuthor());
        System.out.println(reader.getScannedText());
    }
}
