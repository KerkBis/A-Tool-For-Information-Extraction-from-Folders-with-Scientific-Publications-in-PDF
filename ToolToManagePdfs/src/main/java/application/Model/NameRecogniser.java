/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

    public NameRecogniser() {

    }
    public NameRecogniser(String inputText) throws FileNotFoundException, IOException {

        String[] tokens = inputText.split(" ");

        String currentDirectory = System.getProperty("user.dir");
        String locationModelPath = currentDirectory + "\\src\\main\\java\\resources\\en-ner-location.bin";
        String personModelPath = currentDirectory + "\\src\\main\\java\\resources\\en-ner-person.bin";

        InputStream modelIn = new FileInputStream(locationModelPath);
        TokenNameFinderModel locationModel = new TokenNameFinderModel(modelIn);
        modelIn = new FileInputStream(personModelPath);
        TokenNameFinderModel personModel = new TokenNameFinderModel(modelIn);
        NameFinderME locationFinder = new NameFinderME(locationModel);
        NameFinderME persoFinder = new NameFinderME(personModel);

        Span locationSpans[] = locationFinder.find(tokens);
        Span personSpans[] = persoFinder.find(tokens);
//        for (Span span : locationSpans) {
//            System.out.println("Position - " + span.toString() + "    Entity - " + tokens[span.getStart()] + "    Type - " + span.getType());
//        }
        int first3Names = 0;
        //Usually the first 3 names of a research papers are related to the author

        for (Span span : personSpans) {
            if (first3Names >= 3) {
                break;
            }
            System.out.println("Position - " + span.toString() + "    Name - " + tokens[span.getStart()]);
            first3Names++;
        }

    }

    public String filterTextByRegex(String text) {
        String output = new String();
        //full names are Name Lastname so 2 CFL words seperated by whitespace are possibly a name.
        //"(\\b[A-Z]{1}[a-z]+)( )([A-Z]{1}[a-z]+\\b)" this regex targets standar names inside text
        //^([A-z\'\.-ᶜ]*(\s))+[A-z\'\.-ᶜ]*$ for names like DiMaggio St. Croix, O'Reilly butt is alone
        Pattern p = Pattern.compile("(\\b[A-Z]{1}[a-z]+)( )([A-Z]{1}[a-z]+\\b)");
        Matcher matcher = p.matcher(text);
        boolean found = false;
        while (matcher.find()) {

            output = output + "Possible name: " + matcher.group() + " Position: "
                    + matcher.start() + "," + matcher.end() + "\n";
            found = true;
        }
        if (!found) {
            System.out.println("No match found.");
        }

        return output;
    }

}
