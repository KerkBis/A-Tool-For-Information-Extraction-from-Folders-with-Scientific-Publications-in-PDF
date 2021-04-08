package application.Controller;

import java.io.File;
import java.io.IOException;
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
        } catch (IOException ex) {
            output.append("File not found");
        }

        return output;
    }

    public static void main(String[] args) {

        System.out.println(">>>PdfOrganiser v0.1<<<");
//        System.out.println("enter command >>");
////
//        GUI s = new GUI();
//        s.createAndShowGUI();

        //  MyFrame f = new MyFrame();
        GraphicalManager g = new GraphicalManager();
        g.displayMenuGui();
//        g.displayInfoGui();
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new NamesPopUp().setVisible(true);
//            }
//        });


//        ArrayList<String> output = new ArrayList();
//        //full names are Name Lastname so 2 CFL words seperated by whitespace are possibly a name.
//        //"(\\b[A-Z]{1}[a-z]+)( )([A-Z]{1}[a-z]+\\b)" this regex targets standar names inside text
//        //^([A-z\'\.-ᶜ]*(\s))+[A-z\'\.-ᶜ]*$ for names like DiMaggio St. Croix, O'Reilly butt is alone
//        //Pattern p = Pattern.compile("(\\b[A-Z]{1}[a-z]+)( )([A-Z]{1}[a-z]+\\b)");
//        Pattern p = Pattern.compile("^[\\w'\\-,.]*[^_!¡?÷?¿\\/\\\\+=@#$%ˆ&*(){}|~<>;:\\]]*$");
//        Matcher matcher = p.matcher("Julien Dupraz a\n"
//                + ", Audrey Butty a\n"
//                + ", Olivier Duperrex a\n"
//                + ", Sandrine Estoppey a\n"
//                + ", Vincent Faivre a\n"
//                + ", Julien \n"
//                + "Thabard a\n"
//                + ", Claire Zuppinger a\n"
//                + ", Gilbert Greub b, c, Giuseppe Pantaleo d, e, Jérôme Pasquier a\n"
//                + ", Valentin \n"
//                + "Rousson a\n"
//                + ", Malik Egger a\n"
//                + ", Amélie Steiner-Dubuis a\n"
//                + ", Sophie Vassaux a\n"
//                + ", Eric Masserey f\n"
//                + ", Murielle \n"
//                + "Bochud* a\n"
//                + ", Semira Gonseth Nusslé* a\n"
//                + ", Valérie D’Acremont* a,g");
//        boolean found = false;
//
//        while (matcher.find()) {
//            output.add(matcher.group());
//            found = true;
//        }
//
//        if (!found) {
//            System.out.println("No match found.");
//        }
//
//        System.out.println(output);

//
//        editor e = new editor();

//        JFrame frame = new JFrame("FileChooserDemo");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(400, 400);
//        frame.setVisible(true);
//        Scanner keyboard = new Scanner(System.in);
//        commandHandler(keyboard);

//        String currentDirectory = System.getProperty("user.dir");
//        String relativePath = currentDirectory + "\\src\\main\\java\\resources\\";
//        File f = new File(relativePath + "testDoc2.pdf");
//        DocumentEditor dc = new DocumentEditor(f);
//        NameRecogniser nm = new NameRecogniser(dc.getPath(), dc.getScannedText());
//        ArrayList<String> filterTextByRegex = nm.filterTextByRegex();
//        String exportToCSV = Export.exportToCSV(filterTextByRegex);
//        System.out.println("the ouput: \n" + exportToCSV);
    }

}
