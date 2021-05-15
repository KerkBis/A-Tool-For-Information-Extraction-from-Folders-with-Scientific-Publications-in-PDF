/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.Controller;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

/**
 *
 * @author tamag
 */
public class swingWorkerSample {

    private static JLabel statusLabel;
    private static JFrame mainFrame;
    static File files[];
    SwingWorker sw1;

   
      
    public swingWorkerSample(File[] files) 
    {
        mainFrame = new JFrame("Swing Worker");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(2,1));
          
        mainFrame.addWindowListener(new WindowAdapter() 
        {
  
            @Override
            public void windowClosing(WindowEvent e) 
            {
                System.exit(0);
            }
              
        });
          
        statusLabel = new JLabel("Not Completed", JLabel.CENTER);
        mainFrame.add(statusLabel);
        
        mainFrame.setVisible(true);
        files = files;
         startThread();
        
    }
      
    private  void startThread() 
    {
  
         sw1 = new SwingWorker() 
        {
  
            @Override
            protected String doInBackground() throws Exception 
            {
                // define what thread will do here
                
                    Controller.handleProccessing(files);
                    publish((int) Manager.getProgress());
                
                  
                String res = "Finished Execution";
                return res;
            }
  
            @Override
            protected void process(List chunks)
            {
                // define what the event dispatch thread 
                // will do with the intermediate results received
                // while the thread is executing
                int val = (int) chunks.get(chunks.size()-1);
                  
                statusLabel.setText(String.valueOf(val));
            }
  
            @Override
            protected void done() 
            {
                // this method is called when the background 
                // thread finishes execution
                try 
                {
                    String statusMsg = (String) get();
                    System.out.println("Inside done function");
                    statusLabel.setText(statusMsg);
                      
                } 
                catch (InterruptedException e) 
                {
                    e.printStackTrace();
                } 
                catch (ExecutionException e) 
                {
                    e.printStackTrace();
                }
            }
        };
          
        // executes the swingworker on worker thread
        sw1.execute(); 
    }
      
   
}
