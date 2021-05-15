/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;

public class LoadingBar extends JFrame {

    JProgressBar progressBar;

    public LoadingBar() {
        this.setTitle("Progress Bar");
        this.setBounds(100, 100, 407, 119);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().setLayout(null);

        progressBar = new JProgressBar();
        progressBar.setBounds(10, 45, 371, 22);
        this.getContentPane().add(progressBar);

        JLabel label = new JLabel("Loading please wait");

        this.getContentPane().add(label);
        this.getContentPane().add(progressBar);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void upProgressBar(int count) {
//        if (count == 100) {
//            this.setVisible(false);
//            this.dispose();
//        }
        this.progressBar.setValue(count);
    }

}
