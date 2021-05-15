/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Model;

import static edu.stanford.nlp.util.logging.RedwoodConfiguration.Handlers.output;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author tamag
 */
public class DateRecogniser {

    String text;

//    public DateRecogniser(String text){
//        this.text = text;
//    }
//    
    public ArrayList<String> findDate() {
        return regexDate();
    }

    public ArrayList<String> regexDate() {

        ArrayList<String> output= new ArrayList<>();
        
        var p1 = Pattern.compile("[0-9]{2}/[0-9]{2}/[0-9]{4}"); //patten 2 number/2 numbers/4 numbers
        var p2 = Pattern.compile("[0-9]{2}[.][0-9]{2}[.][0-9]{4}");//patten 2 number.2 numbers.4 numbers
        var p3 = Pattern.compile("[0-9]{1,2}\\s[A-Z]{1}[a-z]{2,17}\\s[0-9]{4}");// patern date number_whitespace Month  whitespace year_number
        var p4 = Pattern.compile("[A-Z]{1}[a-z]{2,7}\\s[0-9]{1,2}\\s[0-9]{4}");

        Matcher matcher1 = p1.matcher(this.text);
        Matcher matcher2 = p2.matcher(this.text);
        Matcher matcher3 = p3.matcher(this.text);
        Matcher matcher4 = p4.matcher(this.text);
        
        boolean found = false;

        if (matcher1.find()) {
            output.add(matcher1.group(0));
            found = true;
        }
        if (matcher2.find()) {
            output.add(matcher2.group(0));
            found = true;
        }
        if (matcher3.find()) {
            output.add(matcher3.group(0));
            found = true;
        }
        if (matcher4.find()) {
            output.add(matcher4.group(0));
            found = true;
        }
//        if (!found) {
//            System.out.println("No date found.");
//        }
        
        //returns the first matches of each type.
        return output;
    }

    public void setText(String text) {
        this.text = text;
    }

}
