/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Model;

import edu.stanford.nlp.simple.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author tamag
 */
public class NameRecogniser {

    private String text;
    private String fileName;

    public NameRecogniser() {

    }

    public NameRecogniser(String inputText) {
        this.text = inputText;

    }

    public ArrayList<String> findNames() throws IOException {
        //return filterTextByRegex();
        //return filterTextByNER();
        return filterTextByStandfordNer();
    }

    public ArrayList<String> filterTextByStandfordNer() throws IOException {
        ArrayList<String> output = new ArrayList<String>();

//        // set up pipeline properties
//        Properties props = new Properties();
//        // set the list of annotators to run
//        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
//        // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
//        props.setProperty("coref.algorithm", "neural");
//        // build pipeline
//        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
//        // create a document object
//        CoreDocument document = new CoreDocument("yoooo halooo Kerk ");
//        // annnotate the document
//        pipeline.annotate(document);
        Sentence sent = new Sentence(this.text);
        List<String> nerTags = sent.nerTags();  // [PERSON, O, O, O, O, O, O, O]
        String firstPOSTag = sent.posTag(0);   // NNP
        int i = -0;
        for (String itterator : nerTags) {
            if (itterator.equals("PERSON")) {
                output.add(sent.word(i));
            }
            i++;
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
