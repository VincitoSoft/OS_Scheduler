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
import javax.swing.JOptionPane;

/**
 *
 * @author WaRLoCk
 */
public class Scheduler implements Observer {

    private int processId = 0;
    private int maxProcess = 5;
    private int maxProcessBlocked = 3;
    //Vector q=new Vector();
    Vector<Process> readyQue = new Vector<Process>();
    Vector<Process> readySusQue = new Vector<Process>();
    Vector<Process> blockQue = new Vector<Process>();
    Vector<Process> blockSusQue = new Vector<Process>();
    Vector TempreadyQue = new Vector();
    Vector TempreadySusQue = new Vector();
    Vector TempblockQue = new Vector();
    Vector TempblockSusQue = new Vector();
    Process intPro;//To keep intermidiate processes
    GUI1 gui;

    public Scheduler(int numProcess, long[] serviceTime, boolean[] blockProcesses, GUI1 gui) {

        this.gui = gui;
        long timeQuanta = calculateTimeQuantum(serviceTime);//Calculate average of service times as Time Quanta
        int i, j;//Initially add to the Scheduler
        for (i = 0; i < numProcess; i++) {

            Process p = createProcess(serviceTime[i], timeQuanta, blockProcesses[i]);//call to create process with service time
            addtoReadyqueue(p, i);//Then add to ready Queue
        }

        startTask();

    }

    public long calculateTimeQuantum(long[] array) {//To get average of processes service times
        int i;
        long timeQunta = 0;
        for (i = 0; i < array.length; i++) {
            timeQunta += array[i];
        }
        return timeQunta / array.length;//Return average time of processes
    }

    public void startTask() {//Run the next processes

        //showQueues();

        if (readyQue.isEmpty()) {//Because no other processes to proceed
           
            JOptionPane.showMessageDialog(null, "Execution Done..!");
        } else {
            intPro = readyQue.remove(0);//Hold to the intermidiate object and run the process
            intPro.run();
        }

    }

    public void sheduleProcess(MessegeAttributes ms) {
        if (ms.getStatus() == 1) {//execution Finished
            if (!readySusQue.isEmpty()) {//& Suspend queue is not empty so put to the ready queue
                addToreadyBysus();
                   
            } else if (!blockQue.isEmpty()) {//if ready sus queue is empty get process from blocked queue
                addblockedToReady();
                    addToblockedBysus();
                        

            }
        } else if (ms.getStatus() == 2) {//if not finished add to the ready que again
            if (readySusQue.isEmpty()) {
                
                readyQue.add(intPro);
            } else {
                addToreadyBysus();
                readySusQue.add(intPro);
            }
        } else {
            if (!readySusQue.isEmpty()) {//& Suspend queue is not empty so put to the ready queue
                addToreadyBysus();
                
            } else if (!blockQue.isEmpty()) {//if ready sus queue is empty get process from blocked queue
                addblockedToReady();
                addToblockedBysus();
                

            }

            addtoBlockedQueue(intPro, blockQue.size());
        }
        
        gui.updateGui(intPro, this, readyQue);//pass the 

        //startTask();

    }

    public void addtoReadyqueue(Process p, int i) {//I means number of processes already in the queue
        if (i >= maxProcess) {//If no space in main memory put to secondary memory
            readySusQue.add(p);
        } else {//if Main memory is available put to the redy queue
            readyQue.add(p);
        }
    }

    public void addtoBlockedQueue(Process p, int i) {
        if (i >= maxProcessBlocked) {//If no space in main memory put to secondary memory
            blockSusQue.add(p);
        } else {//if Main memory is available put to the redy queue
            blockQue.add(p);
        }

    }

    public void addToreadyBysus() {//remove from ready suspend and add to ready queue
        if (!readySusQue.isEmpty()) {
            Process p = readySusQue.remove(0);
            addtoReadyqueue(p, readyQue.size());
        }
    }

    public void addToblockedBysus() {//remove from blocked suspend and add to block queue
        if (!blockSusQue.isEmpty()) {
            Process p = blockSusQue.remove(0);
            addtoBlockedQueue(p, blockQue.size());
        }
    }

    public void addtoreadySusbyBlocked() {
        if (!blockQue.isEmpty()) {
            Process p = blockQue.remove(0);
            readySusQue.add(p);
        }
    }

    public void addblockedToReady() {
        if (!blockQue.isEmpty()) {
            Process p = blockQue.remove(0);
            addtoReadyqueue(p, readyQue.size());
        }
    }

    public Process createProcess(long servTime, long timeQuanta, boolean isInterrupted) {
        //processId++;//Increment the process Id and Add to the list of Observer
        Process p = new Process(processId, servTime, timeQuanta, isInterrupted, gui);
        p.addObserver(this);
        processId++;//Increment the process Id and Add to the list of Observer
        return p;
    }

    public void showQueues() {
        int i;
        TempreadyQue = new Vector();
        TempreadySusQue = new Vector();
        TempblockQue = new Vector();
        TempblockSusQue = new Vector();
        //System.out.println("Ready Queue");
        for (i = 0; i < readyQue.size(); i++) {
           
            TempreadyQue.add(i,String.format("Process %d",readyQue.get(i).getProcessId()+1));
        }
        gui.setReady(TempreadyQue);
       
        for (i = 0; i < readySusQue.size(); i++) {
            
            TempreadySusQue.add(i,String.format("Process %d",readySusQue.get(i).getProcessId()+1));
        }
        gui.setReadySus(TempreadySusQue);
       
        for (i = 0; i < blockQue.size(); i++) {
            
            TempblockQue.add(i,String.format("Process %d",blockQue.get(i).getProcessId()+1));
        }
        gui.setBlocked(TempblockQue);
        
        for (i = 0; i < blockSusQue.size(); i++) {
           
            TempblockSusQue.add(i,String.format("Process %d",blockSusQue.get(i).getProcessId()+1));
        }
        gui.setBlockSus(TempblockSusQue);

        //System.out.println("------------------------------------------------");
    }

    @Override
    public void update(Observable o, Object messegeObject) {
        MessegeAttributes ms = (MessegeAttributes) messegeObject;
        sheduleProcess(ms);

    }
}
