/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controler;

import application.Model.DocumentEditor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tamag
 */
public class Manager {

    static List<DocumentEditor> editors = new ArrayList<>();

    static int createEditors(int numberOfEditors, List<String> pathNames) throws IOException {
        for (int i = 0; i < numberOfEditors; i++) {
            System.out.println(">>>Making editor<<<");
            editors.add(new DocumentEditor(pathNames.get(i)));
        }
        return 0;
    }

    static int closeAllEditors() {
        editors.forEach(editor -> {
            try {
                editor.close();
            } catch (IOException ex) {
                Logger.getLogger(Manager.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        editors.clear();
        return 0;
    }
}
