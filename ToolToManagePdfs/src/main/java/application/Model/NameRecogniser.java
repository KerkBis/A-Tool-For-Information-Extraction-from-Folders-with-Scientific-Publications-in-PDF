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
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

/**
 *
 * @author tamag
 */
public class NameRecogniser {

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

}
