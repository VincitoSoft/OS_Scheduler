package oss;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Dilan Nuwantha
 */
public class Process extends Observable{
		private boolean interrupted=false;
		private int  processId;
	    private long serviceTime;
	    private long startTime;
	    private long elapsedTime;
	    private long fullServiceTime; // service time used for GUI
	    private long fullElapsedTime=0;// elapsed time used for GUI
	    private long timeQuantum=5000;  // set time quantum 
	    
	    public Process(int processId,long serviceTime){
	    	this.processId=processId;
	    	this.serviceTime=serviceTime;
	    	this.fullServiceTime=serviceTime;
	    }

	    public void runTime() {
	    	   startTime=System.currentTimeMillis();
	    	   setInterrupted(false);           // I/O operation finished after come to ready queue
	    	   
	    	while(true){
	    		elapsedTime=System.currentTimeMillis()-startTime;
	    		fullElapsedTime+=elapsedTime;
	    		
	    		if(serviceTime<=elapsedTime){
	    			setChanged();
	    			notifyObservers();			
	    			break;						// end the whole process
	    		}else if(isInterrupted()){
	    			setChanged();
	    			notifyObservers();         // I/O interrupt occurs
	    			
	    		}
	    		else if(timeQuantum<=elapsedTime){
	    			setChanged();
	    			notifyObservers();	
	    			serviceTime-=timeQuantum;
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

		private void setInterrupted(boolean interrupted) {
			this.interrupted = interrupted;
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
