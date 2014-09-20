/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oss;

//import oss.Process;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 *
 * @author WaRLoCk
 */
public class Scheduler implements Observer {
    private int presentProcess=0;
    private int processId = 0;
    private int maxProcess = 5;
    private int maxProcessBlocked =3;
    Vector<Process> readyQue = new Vector<Process>();
    Vector<Process> readySusQue = new Vector<Process>();
    Vector<Process> blockQue = new Vector<Process>();
    Vector<Process> blockSusQue = new Vector<Process>();
    Process intPro;//To keep intermidiate processes

    public Scheduler(int numProcess) {

        int i,j;//Initially add to the Scheduler
        for (i = 0; i < numProcess; i++) {
                
                Process p=createProcess(1000 +i*1000);//call to create process with service time
                addtoReadyqueue(p, i);//Then add to ready Queue
        }
        for(j=0;j<numProcess;j++){
             Process p=createProcess(1000 +i*1000);//call to create process with service time
             addtoBlockedQueue(p, j);
        }
        startTask();

    }
    public void startTask(){//Run the next processes
        int i;
        for(i=0;i<readyQue.size();i++){
            System.out.print(" "+readyQue.elementAt(i).getProcessId());
            
        }
        System.out.println("");
        if(readyQue.isEmpty()){//Because no other processes to proceed
            System.exit(0);
        }
        else{
            intPro=readyQue.remove(0);//Hold to the intermidiate object and run the process
            intPro.run();
        }
        
    }
    public void sheduleProcess(MessegeAttributes ms){
        if (ms.getStatus()==1) {//Finished
            if(!readySusQue.isEmpty()){//& Suspend queue is not empty so put to the ready queue
                addToreadyBysus();
                    addtoreadySusbyBlocked();
                        addToblockedBysus();
            }
            else if(!blockQue.isEmpty()){//if ready sus queue is empty get process from blocked queue
                addblockedToReady();
                    addToblockedBysus();
                        addtoreadySusbyBlocked();
                              addToblockedBysus();
                    
            }
        } else if (ms.getStatus()==2){//if not finished add to the ready que again
            if(readySusQue.isEmpty()){
                 readyQue.add(intPro);
            }
            else{
                addToreadyBysus();
                    readySusQue.add(intPro);
            }
        }
        else{
            addtoBlockedQueue(intPro,blockQue.size());
        }
        
        
        startTask();
        
    }
    
    public void addtoReadyqueue(Process p,int i){
         if (i > maxProcess) {//If no space in main memory put to secondary memory
                readySusQue.add(p);
            } else {//if Main memory is available put to the redy queue
                readyQue.add(p);
            }
    }
    public void addtoBlockedQueue(Process p,int i){
         if (i > maxProcessBlocked) {//If no space in main memory put to secondary memory
                blockSusQue.add(p);
            } else {//if Main memory is available put to the redy queue
               blockQue.add(p);
            }
    
    }
    public void addToreadyBysus(){//remove from ready suspend and add to ready queue
        if(!readySusQue.isEmpty()){
            Process p=readySusQue.remove(0);
            addtoReadyqueue(p,readyQue.size());
        }
    }
    public void addToblockedBysus(){//remove from blocked suspend and add to block queue
        if(!blockSusQue.isEmpty()){
            Process p=blockSusQue.remove(0);
            addtoBlockedQueue(p,blockQue.size());
        }
    }
    public void addtoreadySusbyBlocked(){
        if(!blockQue.isEmpty()){
            Process p=blockQue.remove(0);
            readySusQue.add(p);
        }
    }
    public void addblockedToReady(){
        if(!blockQue.isEmpty()){
            Process p=blockQue.remove(0);
            addtoReadyqueue(p,readyQue.size());
        }
    }
   

    public Process createProcess(long servTime) {
        processId++;//Increment the process Id and Add to the list of Observer
        Process p = new Process(processId, servTime);
        p.addObserver(this);
            return p;
    }

  

    @Override
    public void update(Observable o, Object messegeObject) {
        MessegeAttributes ms = (MessegeAttributes) messegeObject;
        sheduleProcess(ms);

    }
}
