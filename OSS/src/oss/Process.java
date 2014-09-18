package oss;

import java.util.Observable;
/**
 *
 * @author Thilina Piyadasun
 */
public class Process extends Observable{
    
    private int processId;
    private long serviceTime;
    private long startTime;
    private long currntTime=0;
    private static final long timeQuntm=5000;
    
    public Process(int processId,long SrvTime){ 
        this.processId=processId;
        this.serviceTime=SrvTime;
    }
    public void run(){
            startTime = System.currentTimeMillis();
            while(true){
              
                currntTime=System.currentTimeMillis();
                if(serviceTime<=currntTime-startTime)
                {
                    notifyObservers();                  //end of the process
                    break;
                }
                else if(timeQuntm<=currntTime-startTime){
                    
                    notifyObservers();
                    serviceTime=serviceTime-timeQuntm;
                    break;                             // process go to suspend state
                }
  
            }
            
        }



}
