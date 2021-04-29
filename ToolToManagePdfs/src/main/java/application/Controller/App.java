package application.Controller;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws Exception {

        System.out.println(">>>PdfOrganiser v0.1<<<");
        Manager.LoadManager();
        Controller g = new Controller();
//        g.displayMenuGui();
        Scanner keyboard = new Scanner(System.in);
        g.commandHandler(keyboard);

    }

}
