/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oss;
import java.util.*;
/**
 *
 * @author Thilina Piyadasun
 */
public class Process extends Observable{
    
    private int processId;
    private long processAvailableTime;
    private long startTime=0;
    private long currntTime=0;
    private static final long timeQunta=5000;

    public Process(int processId,long serviceTime) {
        this.processId = processId;
        this.processAvailableTime=serviceTime;
    }
    
    public void run(){
        
        currntTime=System.currentTimeMillis();
        
        while(true){
            currntTime=System.currentTimeMillis();
            
            if(processAvailableTime<=currntTime-startTime){
                notifyObservers();                      //end of the process
                break;
            }
            else if(timeQunta<=currntTime-startTime){
                notifyObservers();
                processAvailableTime=processAvailableTime-timeQunta;
                break;                                 //process go to suspended state
            }
        }
    }
            
}

