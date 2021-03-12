/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

/**
 *
 * @author tamag
 */
public class NameRecogniser {

    private String text;
    public NameRecogniser() {

    }

    public NameRecogniser(String inputText) {
        this.text = inputText;
    }

    public ArrayList<String> findNames() throws IOException {
        return filterTextByRegex();
    }

    public String filterTextByNER(ArrayList<String> text) throws IOException {

        String[] tokens = text.toArray(new String[text.size()]);
        String output = new String();
        String currentDirectory = System.getProperty("user.dir");
        String personModelPath = currentDirectory + "\\src\\main\\java\\resources\\en-ner-person.bin";

        InputStream modelIn = new FileInputStream(personModelPath);
        TokenNameFinderModel personModel = new TokenNameFinderModel(modelIn);
        NameFinderME persoFinder = new NameFinderME(personModel);

        //Usually the first 3 names of a research papers are related to the author
        Span personSpans[] = persoFinder.find(tokens);
        int first3Names = 0;
        for (Span span : personSpans) {
            if (first3Names >= 3) {
                break;
            }
            output = output + "Position - " + span.toString() + "    Name - " + tokens[span.getStart()] + "\n";
            first3Names++;
        }

        return output;
    }

    public ArrayList<String> filterTextByRegex() {

        ArrayList<String> output = new ArrayList();
        //full names are Name Lastname so 2 CFL words seperated by whitespace are possibly a name.
        //"(\\b[A-Z]{1}[a-z]+)( )([A-Z]{1}[a-z]+\\b)" this regex targets standar names inside text
        //^([A-z\'\.-ᶜ]*(\s))+[A-z\'\.-ᶜ]*$ for names like DiMaggio St. Croix, O'Reilly butt is alone
        Pattern p = Pattern.compile("(\\b[A-Z]{1}[a-z]+)( )([A-Z]{1}[a-z]+\\b)");
        Matcher matcher = p.matcher(this.text);
        boolean found = false;

        while (matcher.find()) {
            output.add(matcher.group());
            found = true;
        }

        if (!found) {
            System.out.println("No match found.");
        }

        return output;
    }

}
