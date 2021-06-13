package application.Controller;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;

/**
 * Hello world!
 *
 */
public class App {

    public static void main(String[] args) throws Exception {

        System.out.println(">>>PdfOrganiser v0.1<<<");
//----------------------testing things---------------------------------        
//        SwingWorkerSample task = new SwingWorkerSample();
//        task.execute();
//        try {
//            task.get();//wait
//        } catch (InterruptedException | ExecutionException ex) {
//            System.out.println("Processing thread Exception");
//        }
//----------------------testing things---------------------------------  


        Manager.LoadManager();
        Controller g = new Controller();
//-----------------------gui mode------------------------------
        //g.displayMenuGui();

        
        
//-----------------------command line mode---------------------
        Scanner keyboard = new Scanner(System.in);
        g.commandHandler(keyboard);
    }

}
