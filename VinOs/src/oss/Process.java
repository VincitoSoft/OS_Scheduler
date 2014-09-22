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
public class Process extends Observable {
    
    private String state;
    private long fullElapsedtime;//to keep executed time upto now
    private int processId;//to keep process Id
    private boolean isInterupted;

    private long processAvailableTime;//How much time left to run
    //private long fullExecutionTime;//To keep total executed time
    private long localExecutionTime;//To keep time elapsed in processo
    private final long serviceTime;

    private long startTime;//time which execution Begin at processor
    private long currntTime;//Updating time
    private long timeQunta = 0;

    GUI1 gui;

    public Process(int processId, long serviceTime, long timeQuanta, boolean isInterupted, GUI1 gui) {//When process created execution is 0
        this.gui = gui;
        
        this.fullElapsedtime=0;//at start executed time is 0
        this.isInterupted = isInterupted;//I/O operation or not
        //this.fullExecutionTime=0;
        this.localExecutionTime = 0;

        this.processId = processId;//Set id of process
        this.processAvailableTime = serviceTime;//set Service time

        this.serviceTime = serviceTime;
        //this.executedPrecentage=0;
        this.timeQunta = timeQuanta;//set time Qunta(Time out)
    }

    public void run() {

        startTime = System.currentTimeMillis();//Process start execution in processor at this time

        while (true) {//Start the execution

            currntTime = System.currentTimeMillis();//This is the updating time
            localExecutionTime = currntTime - startTime;//How much time process has been in processor
            //gui.setLable(getProcessId(), "Running...");

            if (localExecutionTime >= processAvailableTime) {//Process finished
                
                fullElapsedtime+=localExecutionTime;
                MessegeAttributes masAtr = new MessegeAttributes();//Creating messege pass object to send process info
                masAtr.setProcessId(getProcessId());
                masAtr.setStatus(1);
                masAtr.setTempTime(getFullElapsedtime());
                setState("Finished");
                
                setChanged();//Mark that process has Finished
                notifyObservers(masAtr);
                break;
            } else if (localExecutionTime >= timeQunta) {

                processAvailableTime = processAvailableTime - timeQunta;//from the available time timequanta has deducted
                fullElapsedtime+=localExecutionTime;
                MessegeAttributes masAtr = new MessegeAttributes();//Creating messege pass object to send process info
                masAtr.setProcessId(getProcessId());
                masAtr.setStatus(2);
                masAtr.setTempTime(getFullElapsedtime());
                setState("Pause");
                setChanged();//Mark that process has Finished
                notifyObservers(masAtr);
                break;
            } else if (isInterupted) {
                System.out.println(" " + getProcessId() + "Moved to Blocked Queue");
                fullElapsedtime+=localExecutionTime;
                MessegeAttributes masAtr = new MessegeAttributes();//Creating messege pass object to send process info
                masAtr.setProcessId(getProcessId());
                masAtr.setStatus(3);
                
                isInterupted = false;//Interuption is removed
                masAtr.setTempTime(getFullElapsedtime());
                setState("Interupted");
                setChanged();//Mark that process has Finished
                notifyObservers(masAtr);
                //System.out.println("HOHOHO");
                break;
            }
        }
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the fullElapsedtime
     */
    public long getFullElapsedtime() {
        return fullElapsedtime;
    }

    /**
     * @return the serviceTime
     */
    public long getServiceTime() {
        return serviceTime;
    }

}
