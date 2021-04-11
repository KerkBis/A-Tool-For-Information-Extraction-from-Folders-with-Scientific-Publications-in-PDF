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
import java.io.StringWriter;
import java.util.List;

/**
 *
 * @author tamag
 */
public class Export {

    public static String exportToCSV(List<Result> results, File file) {
        // first create file object for file placed at location 
        // specified by filepath
        String s = "Not initialised";
        //File file = new File(System.getProperty("user.dir") + "csv");
        try {
            // create FileWriter object with file as parameter 
            FileWriter fw = new FileWriter(file + ".csv");

            StringWriter sw = new StringWriter();

            // create CSVWriter object filewriter object as parameter 
            CSVWriter writer = new CSVWriter(fw);

            // adding header to csv 
            String[] header = {"File", "First Name", "Last Name"};
            writer.writeNext(header);

            // add data to csv
            for (Result result : results) {
                String fileName = result.getFileName();
                String[] tokens = result.getNames().toArray(new String[result.getNames().size()]);
                for (String itterator : tokens) {
                    //tokens elements are in form: token[i] = "Firstname Lastname"
                    //so we need to break them apart
                    String fileNameTemp = fileName;

                    String[] newMap = fileNameTemp.concat(" " + itterator).split(" ");

                    writer.writeNext(newMap);
                }
            }

            System.out.println("Exported to directory: " + file.getPath());
            // closing writer connection 
            writer.close();
            s = sw.toString();
            sw.close();
            fw.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block 
            System.out.println("Failed to export in csv");
        }
        return s;
    }

}
