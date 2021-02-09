/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
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

    interface commandInterface {

        void apply(String x);

    }

    private Map<String, commandInterface> commands;

    private PDDocument doc;
    private String path, name, author, creator, title, subject, keywords;
    private Calendar creationDate, modifiedDate;
    private String scannedText;

    public DocumentEditor(String pathname) {
        initialiseCommandMap();
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

    private void initialiseCommandMap() {
        commands = new HashMap<>();
        commands.put("set path", (String value) -> this.path = value);
        commands.put("set name", (String value) -> setName(value));
        commands.put("set author", (String value) -> setAuthor(value));
        commands.put("set creator", (String value) -> setCreator(value));
        commands.put("set title", (String value) -> setTitle(value));
        commands.put("set subject", (String value) -> setSubject(value));
        commands.put("set keywords", (String value) -> setKeywords(value));
        commands.put("set scanned text", (String value) -> setScannedText(value));
//        commands.put("get path", (String x) -> getPath());
//        commands.put("get name", (String x) -> getName());
//        commands.put("get author", (String x) -> getAuthor());
//        commands.put("get creator", (String x) -> getCreator());
//        commands.put("get title", (String x) -> getTitle());
//        commands.put("get subject", (String x) -> getSubject());
//        commands.put("get keywords", (String x) -> getKeywords());
//        commands.put("get scanned text", (String x) -> getScannedText());
    }

    public void editProperties(String commandKey, String value) {
        commands.get(commandKey).apply(value);
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
