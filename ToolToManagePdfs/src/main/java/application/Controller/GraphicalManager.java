/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template lfile, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controller;

import application.Model.Result;
import application.View.Menu;
import application.View.ShowInfo;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author tamag
 */
public class GraphicalManager {

    public class MenuGui extends Menu {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle open button action.
            if (e.getSource() == open) {
                int returnVal = fc.showOpenDialog(MenuGui.this);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File[] files = fc.getSelectedFiles();

                    //This is where application begins proccesing the files.
                    String result = App.fileProccesing(files).toString();

                    log.append(result);
                    ArrayList<String> fileNames = new ArrayList<String>();
                    ArrayList<ArrayList<String>> nanames = new ArrayList<ArrayList<String>>();

//                    for (Result r : Manager.getResults()) {
////                        fileNames.add(r.getFileName());
////                        nanames.add(r.getNames());
//                        ShowInfo.createInfoPanels(r.getFileName(), r.getNames());
//                    }
                    displayInfoGui(Manager.getResults());
                    Manager.closeAll();
                    try {
//                        // String
//                        String s1 = "", sl = "";
//
//                        // File reader
//                        FileReader fr = new FileReader(System.getProperty("user.dir") + "csv");
//
//                        // Buffered reader
//                        BufferedReader br = new BufferedReader(fr);
//
//                        // Initilize sl
//                        sl = br.readLine();
//
//                        // Take the input from the lfile
//                        while ((s1 = br.readLine()) != null) {
//                            sl = sl + "\n" + s1;
//                        }

                        // Set the text
                        log.setText(result);
                    } catch (Exception evt) {
                        log.append("failed to open csv file for editing");
                    }

                } else {
                    log.append("Open command cancelled by user.\n");
                }
                log.setCaretPosition(log.getDocument().getLength());
            }

        }

    }

    public class InfoGui extends ShowInfo {

        public List<InfoPanel> ipanels = new ArrayList<>();

        public class InfoPanel extends JPanel {

            JLabel lfile;
            JTextField tfile;
            JLabel lname;
            ArrayList<JTextField> tnames = new ArrayList<>();

            public InfoPanel(String fileName, ArrayList<String> names) {
                this.setBackground(Color.lightGray);
                this.setBounds(40, 80, 200, 200);
                this.setSize(600, 50);

                lfile = new JLabel("File");
                lfile.setFont(new Font("Arial", Font.PLAIN, 20));
                lfile.setSize(100, 20);
                lfile.setLocation(10, 100);
                this.add(lfile);

                tfile = new JTextField(fileName);
                tfile.setFont(new Font("Arial", Font.PLAIN, 15));
                tfile.setSize(50, 20);
                tfile.setLocation(20, 100);
                this.add(tfile);

                lname = new JLabel("Names");
                lname.setFont(new Font("Arial", Font.PLAIN, 20));
                lname.setSize(100, 20);
                lname.setLocation(10, 50);
                this.add(lname);

                int moveLeft = 1;
                for (String name : names) {

                    JTextField tname = new JTextField(name);
                    tname.setFont(new Font("Arial", Font.PLAIN, 15));
                    tname.setSize(150, 20);
                    tname.setLocation(20 * moveLeft, 100);
                    tnames.add(tname);
                    this.add(tname);
                    moveLeft++;

                }
            }
        }

        public void createInfoPanels(List<Result> results) {
            int moveDown = 0;
            for (Result result : results) {
                InfoPanel p = new InfoPanel(result.getFileName(), result.getNames());
                p.setLocation(100, 100 + moveDown);
                this.ipanels.add(p);
                moveDown += 50;
            }
        }

        public InfoGui(List<Result> results) {
            //super();

//            ArrayList<String> als = new ArrayList<String>();
//            als.add("Kerk");
//            als.add("Bob");
//            als.add("john");
//            InfoPanel panel = new InfoPanel("fileName", als);
//            panel.setSize(300, 50);
//            this.add(panel);
            createInfoPanels(results);

            for (InfoPanel p : ipanels) {
                add(p);
            }

            setLayout(null);
            this.getContentPane();
            this.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submit) {

            } else if (e.getSource() == reset) {
                String def = "";
//                tname.setText(def);
//                res.setText(def);
            }
        }
    }

    public void displayMenuGui() {
        MenuGui m = new MenuGui();
    }

    public void displayInfoGui(List<Result> results) {
        InfoGui i = new InfoGui(results);
    }
}
