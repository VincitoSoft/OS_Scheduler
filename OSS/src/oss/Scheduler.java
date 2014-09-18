/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package oss;

import oss.Process;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 *
 * @author WaRLoCk
 */
public class Scheduler implements Observer{
    
    int maxProcess=0;
    Vector <Process>readyQue=new Vector<Process>();
    Vector <Process>readysusQue=new Vector<Process>();
    
    
    public Scheduler(int n){
       this.maxProcess=n;
    }
    
    public Process createProcess(){   // change "Process" constructer name, constructor already in process class
        Process p=new Process();
        p.addObserver(this);
        return p;
        
    }
    
    public void start(int n){
        
    }
    public void addProcess(Process p){
        
        
    }
    
    
    @Override
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

