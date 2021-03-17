/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Model;

import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.pdmodel.PDPage;
/**
 *
 * @author tamag
 */
public class DocumentCreation {

    private PDDocument doc;
//    private final String path, name, author,creator, title, subject, creationDate, modifiedDate, keywords ;


    public DocumentCreation() {

    }

    public void makeDoc(String path, String DocName, String author, String title) throws IOException {
        this.doc = new PDDocument();

        PDDocumentInformation docProperties = doc.getDocumentInformation();
        docProperties.setAuthor(author);
        docProperties.setTitle(title);

        PDPage blankPage = new PDPage();
        doc.addPage(blankPage);

        doc.save(path + "/" + DocName);
        System.out.println("PDF created");
        doc.close();
    }

}
