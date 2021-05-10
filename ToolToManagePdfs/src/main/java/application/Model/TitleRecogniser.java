/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

/**
 *
 * @author tamag
 */
public class TitleRecogniser extends PDFTextStripper {

    class LineInfo {

        String text;
        float fontSize;

        public LineInfo(String line, float fontSize) {
            this.text = line;
            this.fontSize = fontSize;
        }
    }

    static float maxFontSize;
    static int lineOfText;
    static List<String> bigWords = new ArrayList<>();
    static List<LineInfo> lines = new ArrayList<>();

    public TitleRecogniser() throws IOException {

    }

    @Override
    protected void writeString(String string, List<TextPosition> textPositions) throws IOException {

        if (!textPositions.isEmpty());
        TextPosition text = textPositions.get(0);
        //usually the title of a paper is on the first 10 lines of the text
        //many times the paper has hidden text that is extra file or paper information
        //we ignore those lines that usually have character length smallar than 3
        if (lineOfText <= 10 && string.length() > 3) {

            if (text.getFontSize() > maxFontSize) {
                maxFontSize = text.getFontSize();
            }
            lines.add(new LineInfo(string, text.getFontSize()));
            lineOfText++;
        }

    }

    public static void extractText(InputStream inputStream) {
        PDDocument pdd = null;

        try {

//             PDDocument fd = new PDDocument();
//        fd.addPage(this.doc.getPage(0));
//        PDFTextStripper pdfStripper = new PDFTextStripper();
//        pdfStripper.getLineSeparator();
//        fd.close();
            pdd = PDDocument.load(inputStream);
            TitleRecogniser stripper = new TitleRecogniser();
            // stripper.setSortByPosition(true);
            stripper.setStartPage(0);
            stripper.setEndPage(1);
            stripper.getLineSeparator();

            Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
            stripper.writeText(pdd, dummy);
            dummy.close();

        } catch (IOException e) {
            // throw error
        } finally {
            if (pdd != null) {
                try {
                    pdd.close();
                } catch (IOException e) {

                }
            }
        }
    }

    public static String findTitle(File file) throws IOException {
//        File f = new File("C:\\Users\\tamag\\Documents\\project_test_files\\fontTesting.pdf");
//        File f = new File("C:\\Users\\tamag\\Desktop\\Ptyxiakh\\ResearchOrganiser"
//                + "\\KyriakosBisiklis\\toolToManagePdfs\\src\\main\\java\\resources\\Oxford1.pdf");

        FileInputStream fis = null;
        maxFontSize = 0;
        lineOfText = 1;

        try {
            fis = new FileInputStream(file);
            extractText(fis);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        int numberOfLines = 1;
        for (LineInfo il : lines) {
            if (il.fontSize == maxFontSize && numberOfLines <= 3) {
                bigWords.add(il.text);
                numberOfLines++;
            }
        }
//        System.out.println("fs= " + maxFontSize);
        String output = new String();
        for (String word : bigWords) {
            output = output.concat(word);
        }
        fis.close();
        bigWords.clear();
        lines.clear();
        return output;
    }
}
