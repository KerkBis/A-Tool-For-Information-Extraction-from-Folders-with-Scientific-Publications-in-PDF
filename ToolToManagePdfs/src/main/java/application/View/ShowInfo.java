/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template lfile, choose Tools | Templates
 * and submit the template in the editor.
 */
package application.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author tamag
 */
abstract public class ShowInfo extends JFrame
        implements ActionListener {

    // Components of the Form
    private JLabel title;
    public JFileChooser fc;
    public JTextArea log;

    public JButton submit;
    public JButton reset;

    public JLabel lfile;
    public JTextField tfile;
    public JLabel name;
    public JTextField tname;


    public ShowInfo() {

        setTitle("PDF Name Finder");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setBackground(Color.gray);

        title = new JLabel("File Information");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(50, 50);
        this.add(title);


//        ArrayList<String> als = new ArrayList<String>();
//        als.add("Kerk");
//        als.add("Bob");
//        als.add("john");
//        InfoPanel panel = new InfoPanel("fileName", als);
//        panel.setSize(300, 50);
//        this.add(panel);
        submit = new JButton("Submit");
        submit.setFont(new Font("Arial", Font.PLAIN, 15));
        submit.setSize(100, 30);
        submit.setLocation(50, 300);
        submit.addActionListener(this);
        this.add(submit);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 30);
        reset.setLocation(150, 300);
        reset.addActionListener(this);
        this.add(reset);

    }
}
