/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.util.Span;

/**
 *
 * @author tamag
 */
public class NameRecogniser {

    public NameRecogniser() throws FileNotFoundException, IOException {
        try (InputStream modelIn = new FileInputStream("C:\\Users\\tamag\\Desktop\\Ptyxiakh\\ResearchOrganiser\\KyriakosBisiklis\\toolToManagePdfs\\src\\main\\java\\resources\\en-ner-location.bin")) {
            TokenNameFinderModel model = new TokenNameFinderModel(modelIn);
            NameFinderME nameFinder = new NameFinderME(model);

            String sentence[] = new String[]{
                "London",
                "Vinken",
                "is",
                "61",
                "old",
                "."
            };

            Span nameSpans[] = nameFinder.find(sentence);
            System.out.println("nameSpans: " + Arrays.toString(nameSpans));
        }

    }

}
