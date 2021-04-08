/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.View;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author tamag
 */
public class InfoTable extends AbstractTableModel {

    private ArrayList<String> names;

    private final String[] columnNames = new String[]{
        "Names"
    };

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }
//    public ArrayList<String> getNames() {
//        return this.names;
//    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return names.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object row = names.get(rowIndex);
        if (0 == columnIndex) {
            return row;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        names.set(rowIndex, (String) aValue);
        fireTableCellUpdated(rowIndex, columnIndex); // informe any object about changes
    }

    public void applyChanges() {
        for (int i = 0; i < getRowCount(); i++) {
            setValueAt(names.get(i), i, 0);
        }
    }

    public InfoTable(ArrayList<String> namesList) {
        int i = 0;
        setNames(namesList);
//        for (String name : namesList) {
//            setValueAt(name, i, 0);
//            i++;
//        }
    }

}
