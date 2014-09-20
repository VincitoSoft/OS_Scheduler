/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oss;

import java.util.*;

/**
 *
 * @author Dilan Nuwantha
 */
public class Process extends Observable {

    private boolean interrupted = false;
    private final int processId;
    private long availableTime;
    private long startTime;
    private long elapsedTime;
    private long currentTime;
    private final long fullServiceTime; // service time used for GUI
    private long fullElapsedTime = 0;// elapsed time used for GUI
    private long timeQuantum = 3000;  // set time quantum 

    public Process(int processId, long serviceTime) {
        this.processId = processId;
        this.availableTime = serviceTime;
        this.fullServiceTime = serviceTime;
    }

    public void run() {
        startTime = System.currentTimeMillis();
        interrupted = false;           // assume I/O operation finished after come to ready queue

        while (true) {
            currentTime = System.currentTimeMillis();   // set loop starting time
            elapsedTime = currentTime - startTime;      

            if (availableTime <= elapsedTime) {
                MessegeAttributes finish = new MessegeAttributes(processId, 1);
                setChanged();
                notifyObservers(finish);
                break;						// end the whole process
            } else if (isInterrupted()) {
                fullElapsedTime += System.currentTimeMillis() - currentTime;
                availableTime -= elapsedTime;
                MessegeAttributes blocked = new MessegeAttributes(processId, 3);
                setChanged();
                notifyObservers(blocked);         // I/O interrupt occurs
                break;
            } else if (timeQuantum <= elapsedTime) {
                fullElapsedTime += System.currentTimeMillis() - currentTime;
                availableTime -= timeQuantum;
                MessegeAttributes remain = new MessegeAttributes(processId, 2);
                setChanged();
                notifyObservers(remain);
                break;						//suspend process after timeout
            }

            fullElapsedTime += System.currentTimeMillis() - currentTime;  // update elapsed time for whole process
        }

    }

    public int getProcessId() {
        return processId;
    }

    private boolean isInterrupted() {
        return interrupted;
    }

    private void setInterrupted() {               // set interrupted method for use as I/O interrupt
        this.interrupted = true;
    }

    private void setTimeQuantum(long timeQuantum) {
        this.timeQuantum = timeQuantum;
    }

    private long getFullServiceTime() {
        return fullServiceTime;
    }

    private long getFullElapsedTime() {
        return fullElapsedTime;
    }

}
