/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Model;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author tamag
 */
public class Result {

    String fileName;
    String title;
    String publicationDate;
    ArrayList<String> names;

    public Result(String fileName, ArrayList<String> names) {
        this.fileName = fileName;
        this.names = names;
    }

    public Result(String fileName, String title, String publicationDate, ArrayList<String> names) {
        this.fileName = fileName;
        this.title = title;
        this.publicationDate = publicationDate;
        this.names = names;

    }

    public String getFileName() {
        return fileName;
    }

    public String getTitle() {
        return title;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public ArrayList<String> getNames() {
        return names;
    }
}
