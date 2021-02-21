/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controler;

import application.Model.DocumentEditor;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tamag
 */
public class Manager {

    static List<DocumentEditor> editors = new ArrayList<>();

    static int createEditors(int numberOfEditors, List<String> pathNames) {
        for (int i = 0; i < numberOfEditors; i++) {
            System.out.println("Making editor");
            editors.add(new DocumentEditor(pathNames.get(i)));
        }
        return 0;
    }

    static int closeAllEditors() {
        editors.clear();
        return 0;
    }
}
