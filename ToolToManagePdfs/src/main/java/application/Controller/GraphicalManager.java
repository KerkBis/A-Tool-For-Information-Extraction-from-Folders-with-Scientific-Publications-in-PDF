/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template lfile, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controller;

import application.Model.Export;
import application.Model.Result;
import application.View.InfoElements;
import application.View.InfoPanel;
import application.View.Menu;
import application.View.ShowInfo;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;

/**
 *
 * @author tamag
 */
public class GraphicalManager {

    MenuGui m;
    InfoGui i;

    public class MenuGui extends Menu {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Handle open button action.
            if (e.getSource() == open) {

            }
            int returnVal = fc.showOpenDialog(MenuGui.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File[] files = fc.getSelectedFiles();
                //This is where application begins proccesing the files.
                App.fileProccesing(files);

                Manager.makeBackup();
                displayInfoGui(Manager.getResults());
                setVisible(false);
                dispose();

            }

        }

    }

    public class InfoGui extends ShowInfo {

        public List<InfoElements> ipanels = new ArrayList<>();
        JScrollPane jScrollPane1;
        InfoPanel panel;

        public InfoGui(List<Result> results) {

            jScrollPane1 = new JScrollPane();
            jScrollPane1.setSize(800, 300);
            jScrollPane1.setLocation(50, 100);

            panel = new InfoPanel(results);
            jScrollPane1.setViewportView(panel);
            add(jScrollPane1);

            setLayout(null);
            this.getContentPane();
            this.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == submit) {

                int i = 0;
                for (Result result : panel.getFieldContent()) {
                    Manager.modifyResults(result, i);
                    i++;
                };

                int returnVal = fc.showSaveDialog(InfoGui.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    Export.exportToCSV(Manager.getResults(), file);
                }
                Manager.closeAll();
                displayMenuGui();
                setVisible(false);
                dispose();

            } else if (e.getSource() == reset) {
                dispose();
                displayInfoGui(Manager.getResults());// update infopanels

            } else if (e.getSource() == back) {
                Manager.closeAll();
                displayMenuGui();
                setVisible(false);
                dispose();

            }
        }
    }

    public void displayMenuGui() {
        m = new MenuGui();
    }

    public void displayInfoGui(List<Result> results) {
        i = new InfoGui(results);
    }

//    public GraphicalManager() {
//        displayMenuGui();
//    }
}
