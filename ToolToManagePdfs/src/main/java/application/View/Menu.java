/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.View;

import edu.stanford.nlp.io.ExtensionFileFilter;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author tamag
 *
 */
public abstract class Menu extends JFrame
        implements ActionListener {

    // Components of the Form
    private JLabel title;
    public JFileChooser fc;

    public JTextArea log;

    public JButton open;
    public JButton batchRename;

    // constructor, to initialize the components
    // with default values.
    public void initFileChooser() {
        //Create file chooser
        fc = new JFileChooser();
        //dfferent modes
        fc.setMultiSelectionEnabled(true);
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        FileFilter pdfType = new ExtensionFileFilter("pdf", rootPaneCheckingEnabled);
        fc.setFileFilter(pdfType);
        fc.setAcceptAllFileFilterUsed(false);

        //---> fc.addChoosableFileFilter(new FileNameExtensionFilter("*.pdf", "pdf"));
        //fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
    }

    public Menu() {

        setTitle("PDF Name Finder");
        setBounds(300, 90, 900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.gray);

        title = new JLabel("Select files");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(50, 50);
        add(title);

        initFileChooser();

        open = new JButton("Open a File...");
        open.setFont(new Font("Arial", Font.PLAIN, 15));
        open.setSize(200, 30);
        open.setLocation(50, 100);
        open.addActionListener(this);
        add(open);

        batchRename = new JButton("automatic rename");
        batchRename.setFont(new Font("Arial", Font.PLAIN, 15));
        batchRename.setSize(200, 30);
        batchRename.setLocation(260, 100);
        batchRename.addActionListener(this);
        add(batchRename);

        
        setLayout(null);
        getContentPane();
        setVisible(true);
    }
}
