/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Model;

import java.util.ArrayList;

/**
 *
 * @author tamag
 */
public class Result {

    String fileName;
    ArrayList<String> names;

    public Result(String fileName, ArrayList<String> names) {
        this.fileName = fileName;
        this.names = names;
    }

    public String getFileName() {
        return fileName;
    }

    public ArrayList<String> getNames() {
        return names;
    }
}
