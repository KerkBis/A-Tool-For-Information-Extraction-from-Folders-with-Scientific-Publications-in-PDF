/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.View;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author tamag
 */
public class MyFrame extends JFrame
        implements ActionListener {

    // Components of the Form
    private Container c;
    private JLabel title;
    private JLabel file;
    private JTextField tfile;
    private JLabel name;
    private JTextField tname;

    private JButton sub;
    private JButton reset;

    // constructor, to initialize the components
    // with default values.
    public MyFrame() {
        setTitle("PDF Name Finder");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        title = new JLabel("Extracted Information");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        file = new JLabel("File");
        file.setFont(new Font("Arial", Font.PLAIN, 20));
        file.setSize(100, 20);
        file.setLocation(100, 100);
        c.add(file);

        tfile = new JTextField();
        tfile.setFont(new Font("Arial", Font.PLAIN, 15));
        tfile.setSize(150, 20);
        tfile.setLocation(200, 100);
        c.add(tfile);

        name = new JLabel("Name");
        name.setFont(new Font("Arial", Font.PLAIN, 20));
        name.setSize(100, 20);
        name.setLocation(100, 150);
        c.add(name);

        tname = new JTextField();
        tname.setFont(new Font("Arial", Font.PLAIN, 15));
        tname.setSize(150, 20);
        tname.setLocation(200, 150);
        c.add(tname);


        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 20);
        sub.setLocation(150, 450);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 20);
        reset.setLocation(270, 450);
        reset.addActionListener(this);
        c.add(reset);

//        tout = new JTextArea();
//        tout.setFont(new Font("Arial", Font.PLAIN, 15));
//        tout.setSize(300, 400);
//        tout.setLocation(500, 100);
//        tout.setLineWrap(true);
//        tout.setEditable(false);
//        c.add(tout);
//
//        res = new JLabel("");
//        res.setFont(new Font("Arial", Font.PLAIN, 20));
//        res.setSize(500, 25);
//        res.setLocation(100, 500);
//        c.add(res);

//        resadd = new JTextArea();
//        resadd.setFont(new Font("Arial", Font.PLAIN, 15));
//        resadd.setSize(200, 75);
//        resadd.setLocation(580, 175);
//        resadd.setLineWrap(true);
//        c.add(resadd);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

//        if filechooser
//                manager.results -> for eache result
//
//                                                tfile.setText(result.fileName)
//                                            for(ittr : result.names){
//                                                make name form
//                                                tname.setText(ittr)
//                                            }
    }
}
