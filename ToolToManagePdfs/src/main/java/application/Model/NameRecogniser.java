/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Model;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreEntityMention;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public ArrayList<String> findNames() throws Exception {
//        return regexNer();
        //return filterTextByNER();
        return regexNerVerification(regexNer());//RegexNER -> StandfordNER verifycation -> output
        //return StandfordNer();
    }

    public ArrayList<String> StandfordNer() {
        ArrayList<String> output = new ArrayList<String>();
        // set up pipeline properties
        Properties props = new Properties();
        // set the list of annotators to run
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner");
        // set a property for an annotator, in this case the coref annotator is being set to use the neural algorithm
        props.setProperty("coref.algorithm", "neural");
        // build pipeline
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        // create a document object
        CoreDocument document = new CoreDocument(this.text);
        // annnotate the document
        pipeline.annotate(document);

        // view results
        System.out.println("---");
        System.out.println("entities found");
        for (CoreEntityMention em : document.entityMentions()) {
            if (em.entityType().equals("PERSON")) {
                output.add(em.text());
            }
        }

//        Sentence sent = new Sentence(this.text);
//        List<String> nerTags = sent.nerTags();  // [PERSON, O, O, O, O, O, O, O]
//        String firstPOSTag = sent.posTag(0);   // NNP
//        sent.asCoreMap(functions)
//        int i = -0;
//        for (String itterator : nerTags) {
//            if (itterator.equals("PERSON")) {
//                output.add(sent.word(i));
//            }
//            i++;
//        }
        return output;
    }

    public ArrayList<String> regexNerVerification(ArrayList<String> regexDetectedNames) throws Exception {
        ArrayList<String> output = new ArrayList<String>();

        String currentDirectory = System.getProperty("user.dir");
        String relativePath = currentDirectory + "\\src\\main\\java\\resources\\";
        String serializedClassifier = relativePath + "english.all.3class.distsim.crf.ser.gz";

        AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);

        for (String pname : regexDetectedNames) {
//            Sentence pnameSent = new Sentence(pname);
//            List<String> nerTags = pnameSent.nerTags();  // [PERSON, O, O, O, O, O, O, O]
//            if (nerTags.contains("PERSON") && !nerTags.contains("LOCATION")) {
//                output.add(pname);
//            }
            String name = classifier.classifyToString(pname);
            if (name.contains("/PERSON")) {
                name = name.replace("/PERSON", "");
                name = name.replace("/O", "");
                output.add(name);
            }
        }
        //discard duplicate Names
        List<String> original = output;
        List<String> result = new ArrayList<>();
        result = original.stream()
                .distinct()
                .collect(Collectors.toList());
        output.clear();
        output.addAll(result.subList(0, result.size()));
        return output;
    }

    public ArrayList<String> regexNer() {

        ArrayList<String> output = new ArrayList();
        String specialChars = "àáâäãåąčćęèéêëėįìíîïłńòóôöõøùúûüųūÿýżźñçčšžÀÁÂÄÃÅĄĆČĖĘÈÉÊËÌÍÎÏĮŁŃÒÓÔÖÕØÙÚÛÜŲŪŸÝŻŹÑßÇŒÆČŠŽ∂ð’,.'-";
        var p = Pattern.compile(
                "(\\b[A-Z’]{1,}" //recognizes a word starting with at least one capital letter
                + "[a-z" //followed by words from simple alphabet
                + specialChars + "]+)" //and special characters from foreign languages
                + "\\s+" //a whitespace or sperator
                + "(\\b[A-Z’]{1,}" //recognizes a word starting with at least one capital letter
                + "[a-z" //followed by words from simple alphabet
                + specialChars + "]+\\b)" //and special characters from foreign languages
        );
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
