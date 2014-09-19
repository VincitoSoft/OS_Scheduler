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
        
        startTime=System.currentTimeMillis();
        
        while(true){
            currntTime=System.currentTimeMillis();
            
            if(processAvailableTime<=currntTime-startTime){
                
                MessegeAttributes masAtr=new MessegeAttributes();//Creating messege pass object to send process info
                masAtr.setProcessId(processId);
                masAtr.setStatus(true);
                setChanged();//Mark that process has Finished
                notifyObservers(masAtr);
                break;
            }
            else if(timeQunta<=currntTime-startTime){
                notifyObservers();
                processAvailableTime=processAvailableTime-timeQunta;
                MessegeAttributes masAtr=new MessegeAttributes();//Creating messege pass object to send process info
                masAtr.setProcessId(processId);
                masAtr.setStatus(false);
               
                setChanged();//Mark that process has Finished
                notifyObservers(masAtr);
                break;                                
            }
        }
    }
            
}

