/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.View;

import java.awt.BorderLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author tamag
 */
public abstract class Graphics extends JPanel
        implements ActionListener {

    public static final String newline = "\n";
    public JButton openButton, saveButton;
    public JTextArea log;
    public JFileChooser fc;
    JScrollPane logScrollPane;
    JPanel buttonPanel;

    public void initLog() {
        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(20, 30);
        log.setMargin(new Insets(5, 5, 5, 5));
        log.setEditable(false);
        logScrollPane = new JScrollPane(log);
    }

    public void initFileChooser() {
        //Create file choosr
        fc = new JFileChooser();
        //dfferent modes
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }

    public void initButtons() {
        //Create the open button
        openButton = new JButton("Open a File...");
        openButton.addActionListener(this);

        //Create the save button
        saveButton = new JButton("Save a File...");
        saveButton.addActionListener(this);
    }

    public void initButtonPanel() {
        //For layout purposes, put the buttons in a separate panel
        buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
    }

    public Graphics() {
//        super(new BorderLayout());

        //Add the buttons and the log to this panel.
    }

//    public void actionPerformed(ActionEvent e) {
//
//    }

    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Init Graphics elements buttonPanel and logScrollPane
        initLog();
        initFileChooser();
        initButtons();
        initButtonPanel();

        //Add content to the window.
        frame.add(buttonPanel, BorderLayout.PAGE_START);
        frame.add(logScrollPane, BorderLayout.CENTER);
        //frame.add(new Graphics());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}
