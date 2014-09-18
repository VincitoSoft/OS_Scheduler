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

    /**
     * @return the timeQunta
     */
    public static long getTimeQunta() {
        return timeQunta;
    }

    /**
     * @param aTimeQunta the timeQunta to set
     */
    public static void setTimeQunta(long aTimeQunta) {
        timeQunta = aTimeQunta;
    }
    
    private int processId;
    private long serviceTime=0;
    private long availableTime=0;
    private long startTime=0;
    private long currntTime=0;
    private static long timeQunta=5000;
    private boolean firstRound=true;
    private int timefactor;
    public Process(int processId,long serviceTime) {
        this.processId = processId;
        this.serviceTime=serviceTime;
        availableTime=serviceTime;
    }
    
    public void run(){
        
        setCurrntTime(System.currentTimeMillis());
        
        while(true){
            setCurrntTime(System.currentTimeMillis());
            
            setTimefactor((int) ((getAvailableTime() / getServiceTime()) * 100));
            
            if(getAvailableTime()<=getCurrntTime()-getStartTime()){
                message msg=new message(this.getProcessId(), this.getTimefactor());
                notifyObservers(msg);                      //end of the process
                break;
            }
            else if(getTimeQunta()<=getCurrntTime()-getStartTime()){
                message msg=new message(this.getProcessId(), this.getTimefactor());
                notifyObservers(msg);
                setAvailableTime(getAvailableTime() - getTimeQunta());
                break;                                 //process go to suspended state
            }
        }
    }

    /**
     * @return the processId
     */
    public int getProcessId() {
        return processId;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(int processId) {
        this.processId = processId;
    }

    /**
     * @return the serviceTime
     */
    public long getServiceTime() {
        return serviceTime;
    }

    /**
     * @param serviceTime the serviceTime to set
     */
    public void setServiceTime(long serviceTime) {
        this.serviceTime = serviceTime;
    }

    /**
     * @return the availableTime
     */
    public long getAvailableTime() {
        return availableTime;
    }

    /**
     * @param availableTime the availableTime to set
     */
    public void setAvailableTime(long availableTime) {
        this.availableTime = availableTime;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the currntTime
     */
    public long getCurrntTime() {
        return currntTime;
    }

    /**
     * @param currntTime the currntTime to set
     */
    public void setCurrntTime(long currntTime) {
        this.currntTime = currntTime;
    }

    /**
     * @return the firstRound
     */
    public boolean isFirstRound() {
        return firstRound;
    }

    /**
     * @param firstRound the firstRound to set
     */
    public void setFirstRound(boolean firstRound) {
        this.firstRound = firstRound;
    }

    /**
     * @return the timefactor
     */
    public int getTimefactor() {
        return timefactor;
    }

    /**
     * @param timefactor the timefactor to set
     */
    public void setTimefactor(int timefactor) {
        this.timefactor = timefactor;
    }
            
}

class message{
    int id;
   // int quePosition;
    int timefactor;
    
    public message(int id,int t){
        this.id=id;
       // this.quePosition=pos;
       this.timefactor=t;
    }
    
    public int getId(){
        return this.id;
    }
    
    public int getTimeFactor(){
        return timefactor;
    }
}
    
    
    
    


