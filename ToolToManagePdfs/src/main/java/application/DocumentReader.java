/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author tamag
 */
public class DocumentReader {

    private PDDocument doc;
    private String path, name, author, creator, title, subject, keywords;
    private Calendar creationDate, modifiedDate;
    private String scannedText;

    public DocumentReader() {
        this.doc = null;
        this.path = null;
        this.name = null;
        this.author = null;
        this.creator = null;
        this.title = null;
        this.subject = null;
        this.creationDate = null;
        this.modifiedDate = null;
        this.keywords = null;
        this.scannedText = null;
    }

    public DocumentReader(String pathname) {

        try {
            //get the pdf file to read it
            File file = new File(pathname);
            this.doc = PDDocument.load(file);
            this.path = pathname;
            //extract the text
            PDFTextStripper pdfStripper = new PDFTextStripper();
            this.scannedText = pdfStripper.getText(doc);
            System.out.println("File read");
            //extract the document properties
            PDDocumentInformation docInformation = doc.getDocumentInformation();

            this.name = null;
            this.author = docInformation.getAuthor();
            this.title = docInformation.getTitle();
            this.creator = docInformation.getCreator();
            this.subject = docInformation.getSubject();
            this.creationDate = docInformation.getCreationDate();
            this.modifiedDate = docInformation.getModificationDate();
            this.keywords = docInformation.getKeywords();

        } catch (IOException ex) {
            Logger.getLogger(DocumentReader.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void readFile(String pathname) {
        try {
            //get the pdf file to read it
            File file = new File(pathname);
            this.doc = PDDocument.load(file);
            this.path = pathname;
            //extract the text
            PDFTextStripper pdfStripper = new PDFTextStripper();
            this.scannedText = pdfStripper.getText(doc);
            System.out.println("File read");
            //extract the document properties
            PDDocumentInformation docInformation = doc.getDocumentInformation();

            this.name = null;
            this.author = docInformation.getAuthor();
            this.title = docInformation.getTitle();
            this.creator = docInformation.getCreator();
            this.subject = docInformation.getSubject();
            this.creationDate = docInformation.getCreationDate();
            this.modifiedDate = docInformation.getModificationDate();
            this.keywords = docInformation.getKeywords();

        } catch (IOException ex) {
            Logger.getLogger(DocumentReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List getPropertiesList() {

        List properiesList = new LinkedList<>();

        properiesList.add(name);
        properiesList.add(author);
        properiesList.add(creator);
        properiesList.add(title);
        properiesList.add(subject);
        properiesList.add(creationDate);
        properiesList.add(modifiedDate);
        properiesList.add(keywords);

        return properiesList;
    }

    public PDDocument getDoc() {
        return doc;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreator() {
        return creator;
    }

    public String getTitle() {
        return title;
    }

    public String getSubject() {
        return subject;
    }

    public String getKeywords() {
        return keywords;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public Calendar getModifiedDate() {
        return modifiedDate;
    }

    public String getScannedText() {
        return scannedText;
    }

}
