/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.View;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author tamag
 */
public abstract class Graphics extends JPanel
        implements ActionListener {

    private Container c;
    public JButton openButton, saveButton;
    public JLabel file, name;
    public JTextArea log;
    public JTextField tfile, tname;
    public JFileChooser fc;
    JScrollPane logScrollPane;
    JPanel buttonPanel;
    JPanel infoPanel;

//    public void initLog() {
//        //Create the log first, because the action listeners
//        //need to refer to it.
//        log = new JTextArea(20, 40);
//        log.setMargin(new Insets(5, 5, 5, 5));
//        log.setEditable(false);
//        logScrollPane = new JScrollPane(log);
//
//    }

    public void initInfo() {

        c.setLayout(null);

        file = new JLabel("File");
        file.setFont(new Font("Arial", Font.PLAIN, 20));
        file.setSize(100, 20);
        file.setLocation(100, 100);
//
//        tfile = new JTextField();
//        tfile.setFont(new Font("Arial", Font.PLAIN, 15));
//        tfile.setSize(190, 20);
//        tfile.setLocation(200, 100);

        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(200, 100);

//        tname = new JTextField();
//        tname.setFont(new Font("Arial", Font.PLAIN, 15));
//        tname.setSize(190, 20);
//        tname.setLocation(250, 150);

//        infoPanel = new JPanel();
//        infoPanel.add(name);
//        //infoPanel.add(tname);
//        infoPanel.add(file);
//        //infoPanel.add(tfile);
        c.add(name);
        c.add(file);


    }

    public void initFileChooser() {
        //Create file chooser
        fc = new JFileChooser();
        //dfferent modes
        fc.setMultiSelectionEnabled(true);
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }

    public void initButtons() {
        //Create the open button
        openButton = new JButton("Open a File...");
        openButton.addActionListener(this);

//        Create the save button
        saveButton = new JButton("Save file...");
        saveButton.addActionListener(this);

        buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
    }


    public void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("FileChooserDemo");
        frame.setSize(3000, 1000);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Init Graphics elements buttonPanel and logScrollPane
       // initLog();
        initFileChooser();
        initButtons();
        initInfo();
        //Add content to the window.
        frame.add(buttonPanel, BorderLayout.PAGE_START);
        // frame.add(infoPanel, BorderLayout.CENTER);
        frame.add(c, BorderLayout.CENTER);
        //frame.add(logScrollPane, BorderLayout.CENTER);


        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

}
