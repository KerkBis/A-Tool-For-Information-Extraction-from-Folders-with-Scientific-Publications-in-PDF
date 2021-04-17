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

//        String serializedClassifier = "src/main/java/resources/english.all.3class.distsim.crf.ser.gz";
//        if (args.length > 0) {
//            serializedClassifier = args[0];
//        }
//        AbstractSequenceClassifier<CoreLabel> classifier = CRFClassifier.getClassifier(serializedClassifier);
//
//        if (args.length > 1) {
//            String fileContents = IOUtils.slurpFile(args[1]);
//            List<List<CoreLabel>> out = classifier.classify(fileContents);
//            for (List<CoreLabel> sentence : out) {
//                for (CoreLabel word : sentence) {
//                    System.out.print(word.word() + '/' + word.get(CoreAnnotations.AnswerAnnotation.class) + ' ');
//                }
//                System.out.println();
//            }
//
//            System.out.println("---");
//            out = classifier.classifyFile(args[1]);
//            for (List<CoreLabel> sentence : out) {
//                for (CoreLabel word : sentence) {
//                    System.out.print(word.word() + '/' + word.get(CoreAnnotations.AnswerAnnotation.class) + ' ');
//                }
//                System.out.println();
//            }
//
//            System.out.println("---");
//            List<Triple<String, Integer, Integer>> list = classifier.classifyToCharacterOffsets(fileContents);
//            for (Triple<String, Integer, Integer> item : list) {
//                System.out.println(item.first() + ": " + fileContents.substring(item.second(), item.third()));
//            }
//            System.out.println("---");
//            System.out.println("Ten best entity labelings");
//            DocumentReaderAndWriter<CoreLabel> readerAndWriter = classifier.makePlainTextReaderAndWriter();
//            classifier.classifyAndWriteAnswersKBest(args[1], 10, readerAndWriter);
//
//            System.out.println("---");
//            System.out.println("Per-token marginalized probabilities");
//            classifier.printProbs(args[1], readerAndWriter);
//        } else {
//            String[] example = {"Good afternoon Rajat Raina, how are you today?",
//                "I go to school at Stanford University, which is located in California."};
//            for (String str : example) {
//                // This one is best for dealing with the output as a TSV (tab-separated column) file.
//                // The first column gives entities, the second their classes, and the third the remaining text in a document
//                System.out.print(classifier.classifyToString(str, "tabbedEntities", false));
//            }
//            System.out.println("---");
//
//            for (String str : example) {
//                System.out.println(classifier.classifyWithInlineXML(str));
//            }
//            System.out.println("---");
            // This gets out entities with character offsets
//            int j = 0;
//            for (String str : example) {
//                j++;
//                List<Triple<String, Integer, Integer>> triples = classifier.classifyToCharacterOffsets(str);
//                for (Triple<String, Integer, Integer> trip : triples) {
//                    System.out.printf("%s over character offsets [%d, %d) in sentence %d.%n",
//                            trip.first(), trip.second(), trip.third, j);
//                }
//            }
//            System.out.println("---");
//            // This prints out all the details of what is stored for each token
//            int i = 0;
//            for (String str : example) {
//                for (List<CoreLabel> lcl : classifier.classify(str)) {
//                    for (CoreLabel cl : lcl) {
//                        System.out.print(i++ + ": ");
//                        System.out.println(cl.toShorterString());
//                    }
//                }
//            }
//
//            System.out.println("---");
//        }

    }

}
