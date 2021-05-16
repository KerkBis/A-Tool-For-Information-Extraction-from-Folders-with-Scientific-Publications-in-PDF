/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controller;

import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author tamag
 */
class SwingWorkerSample extends SwingWorker<String, Integer> {

    final JProgressBar progressBar;
    private static JLabel statusLabel;
    private static JFrame mainFrame;
    static File[] fileList;

    public SwingWorkerSample(File[] files) {
        fileList = files;

        mainFrame = new JFrame("Swing Worker");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(2, 1));

        mainFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }

        });

        statusLabel = new JLabel("Not Completed", JLabel.CENTER);
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        mainFrame.add(statusLabel);
        mainFrame.add(progressBar);

//        JButton btn = new JButton("Start counter");
//        btn.setPreferredSize(new Dimension(5, 5));
//
//        btn.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println("Button clicked, thread started");
//                startThread();
//            }
//
//        });
//        mainFrame.add(btn);
        mainFrame.setVisible(true);
        
        this.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            if ("progress".equals(evt.getPropertyName())) {
                this.progressBar.setValue((Integer) evt.getNewValue());
            }
        });
        
         
    }

    @Override
    public String doInBackground() throws Exception {
        // define what thread will do here
        for (int i = 0; i <= 30; i++) {
            Thread.sleep(100);
            System.out.println("Value in thread : " + i);
            publish(i);
            setProgress(100 * i/30);
        }
       
       
        
        

        String res = "Finished Execution";
        return res;
    }

    @Override
    protected void process(List<Integer> chunks) {
        // define what the event dispatch thread 
        // will do with the intermediate results received
        // while the thread is executing
        for (int number : chunks) {
            statusLabel.setText(String.valueOf(number));
            //progressBar.setValue(number);
        }

    }

//    @Override
//    protected void done() {
//        // this method is called when the background 
//        // thread finishes execution
//        try {
//            String statusMsg = (String) get();
//            System.out.println("Inside done function");
//            statusLabel.setText(statusMsg);
//           
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//    }
    
  

//     executes the swingworker on worker thread
//    public static void main(String[] args) {
//
//        SwingWorkerSample task = new SwingWorkerSample();
//        task.execute();
//        
//        while (task.getState() != StateValue.DONE){
//            //wait
//        }
//
//    }
}
