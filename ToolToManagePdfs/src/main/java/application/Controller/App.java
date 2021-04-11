package application.Controller;

import java.io.File;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    static int commandHandler(Scanner keyboard) {

        String currentDirectory = System.getProperty("user.dir");
        String relativePath = currentDirectory + "\\src\\main\\java\\resources\\";

        String inputLine;
        String[] words;

        while (keyboard.hasNext()) {
            inputLine = keyboard.nextLine();
            words = inputLine.split("\\s+");

            switch (words[0]) {
                case "findNames": {

                    File[] files = new File[words.length];
                    for (int i = 1; i < words.length; i++) {
                        files[i] = new File(relativePath + words[i]);
                        //System.out.println("read file:" + filePaths.get(i - 1));
                    }

                    System.out.println(fileProccesing(files));
                    break;
                }
                case "exit": {
                    System.out.println(">>>exiting PdfOrganiser<<<");
                    Manager.closeAll();
                    return 1;
                }
                default: {
                    System.out.println(">>>unrecognised command<<<");
                }

            }

        }
        return 1;
    }

    static StringBuffer fileProccesing(File[] files) {

        StringBuffer output = new StringBuffer();
        try {
            Manager.proccessing(files);
            output.append(Manager.printResults());
//            output.append(Manager.printName());
        } catch (Exception ex) {
            output.append("File not found");
        }

        return output;
    }

    public static void main(String[] args) throws Exception {

        System.out.println(">>>PdfOrganiser v0.1<<<");
//        System.out.println("enter command >>");
        GraphicalManager g = new GraphicalManager();
        g.displayMenuGui();

//        String currentDirectory = System.getProperty("user.dir");
//        String relativePath = currentDirectory + "\\src\\main\\java\\resources\\";
//        String serializedClassifier = relativePath + "english.all.3class.distsim.crf.ser.gz";
//
//        if (args.length > 0) {
//            serializedClassifier = args[0];
//        }
//
//        AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
//
//        /* For either a file to annotate or for the hardcoded text example, this
//       demo file shows several ways to process the input, for teaching purposes.
//         */
//        String[] example = {"Good afternoon Rajat Raina, how are you today?",
//            "I go to school at Stanford University, which is located in California."};

//        for (String str : example) {
//            System.out.println(classifier.classifyToString(str));
//        }
//        System.out.println("---");
//        for (String str : example) {
//            // This one puts in spaces and newlines between tokens, so just print not println.
//            System.out.print(classifier.classifyToString(str, "slashTags", false));
//        }
//        System.out.println("---");
//
//        for (String str : example) {
//            // This one is best for dealing with the output as a TSV (tab-separated column) file.
//            // The first column gives entities, the second their classes, and the third the remaining text in a document
//            System.out.print(classifier.classifyToString(str, "tabbedEntities", false));
//        }
//        System.out.println("---");
//        for (String str : example) {
//            System.out.println(classifier.classifyWithInlineXML(str));
//        }
//        System.out.println("---");
////
//        for (String str : example) {
//            System.out.println(classifier.classifyToString(str, "xml", true));
//        }
//        System.out.println("---");
////
//        for (String str : example) {
//            System.out.print(classifier.classifyToString(str, "tsv", false));
//        }
//        System.out.println("---");
//
        // This gets out entities with character offsets
//        int j = 0;
//        for (String str : example) {
//            j++;
//            List<Triple<String, Integer, Integer>> triples = classifier.classifyToCharacterOffsets(str);
//            for (Triple<String, Integer, Integer> trip : triples) {
//                System.out.printf("%s over character offsets [%d, %d) in sentence %d.%n",
//                        trip.first(), trip.second(), trip.third, j);
//            }
//        }
//        System.out.println("---");
////
//        // This prints out all the details of what is stored for each token
//        int i = 0;
//        for (String str : example) {
//            for (List<CoreLabel> lcl : classifier.classify(str)) {
//                for (CoreLabel cl : lcl) {
//                    System.out.print(i++ + ": ");
//                    System.out.println(cl.toShorterString());
//                }
//            }
//        }
//
//        System.out.println("---");
    }

}
