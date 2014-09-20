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
		private boolean interrupted=false;
            private int  processId;
	    private long availableTime;
	    private long startTime;
	    private long elapsedTime;
	    private long fullServiceTime; // service time used for GUI
	    private long fullElapsedTime=0;// elapsed time used for GUI
	    private long timeQuantum=3000;  // set time quantum 
	    
	    public Process(int processId,long serviceTime){
	    	this.processId=processId;
	    	this.availableTime=serviceTime;
	    	this.fullServiceTime=serviceTime;
	    }

	    public void run() {
	    	   startTime=System.currentTimeMillis();
	    	   interrupted=false;           // I/O operation finished after come to ready queue
	    	   
	    	while(true){
	    		elapsedTime=System.currentTimeMillis()-startTime;
	    		
	    		
	    		if(availableTime<=elapsedTime){
	    			MessegeAttributes finish=new MessegeAttributes(processId,1);
	    			setChanged();
	    			notifyObservers(finish);
	    			break;						// end the whole process
	    		}else if(isInterrupted()){
	    			fullElapsedTime+=elapsedTime;
                                availableTime-=elapsedTime;
                                MessegeAttributes blocked=new MessegeAttributes(processId,3);
                                setChanged();
	    			notifyObservers(blocked);         // I/O interrupt occurs
                                break;
	    		}
	    		else if(timeQuantum<=elapsedTime){
                                fullElapsedTime+=elapsedTime;
	    			availableTime-=timeQuantum;
                                MessegeAttributes remain=new MessegeAttributes(processId,2);
                                setChanged();
	    			notifyObservers(remain);
	    			break;						//suspend process after timeout
	    		}
	    		
	    	
	    	}

	    }

		public int getProcessId() {
			return processId;
		}

		private boolean isInterrupted() {
			return interrupted;
		}

		private void setInterrupted() {
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
