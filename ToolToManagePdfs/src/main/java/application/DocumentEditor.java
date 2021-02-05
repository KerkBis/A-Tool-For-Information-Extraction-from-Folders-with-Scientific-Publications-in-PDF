/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author tamag
 */
public class DocumentEditor {

    private PDDocument doc;
    private String path, name, author, creator, title, subject, keywords;
    private Calendar creationDate, modifiedDate;
    private String scannedText;

    public DocumentEditor(String pathname) {

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
            Logger.getLogger(DocumentEditor.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(DocumentEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editProperties(String property, String value) {
        switch (property) {
            case "name":
                setName(value);
                break;
            case "author":
                setAuthor(value);
                break;
            case "title":
                setTitle(value);
        }
    }

    public DocumentReader getReader() {
        return reader;
    }

    public void setReader(DocumentReader reader) {
        this.reader = reader;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Calendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Calendar creationDate) {
        this.creationDate = creationDate;
    }

    public Calendar getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Calendar modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getScannedText() {
        return scannedText;
    }

    public void setScannedText(String scannedText) {
        this.scannedText = scannedText;
    }

}
