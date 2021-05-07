/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author tamag
 */
public class DateRecogniser {

    String text;

    public String findDate() {
        return regexDate();
    }

    public String regexDate() {

        String output = new String();
        var p = Pattern.compile("[0-9]{2}/[0-9]{2}/[0-9]{4}");
        Matcher matcher = p.matcher(this.text);
        boolean found = false;

        if (matcher.find()) {
            output = matcher.group();
            found = true;
        }
        if (!found) {
            System.out.println("No date found.");
        }
        return output;
    }

    public void setText(String text) {
        this.text = text;
    }

}
