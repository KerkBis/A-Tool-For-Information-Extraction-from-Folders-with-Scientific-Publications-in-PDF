/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Model;

import com.opencsv.CSVWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author tamag
 */
public class Result {

    public static void exportToCSV(ArrayList<String> input) {
        // first create file object for file placed at location 
        // specified by filepath 
        File file = new File(System.getProperty("user.dir") + "csv");
        try {
            // create FileWriter object with file as parameter 
            FileWriter outputfile = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter 
            CSVWriter writer = new CSVWriter(outputfile);

            // adding header to csv 
            String[] header = {"First Name", "Last Name"};
            writer.writeNext(header);


            // add data to csv
            //turn Array list to String[]
            String[] tokens = input.toArray(new String[input.size()]);
            for (String itterator : tokens) {
                //tokens elements are of form: token[i] = "Firstname Lastname"
                //so we need to break them apart
                writer.writeNext(itterator.split(" "));
            }
            // writer.writeNext(input.toArray(new String[input.size()]));

            System.out.println("Exported to directory: " + file.getPath());
            // closing writer connection 
            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block 
            System.out.println("Failed to export in csv");
        }
    }

}
