package oss;

import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Dilan Nuwantha
 */
public class Process extends Observable implements Observer {
		private int  processId;
	    private long serviceTime;
	    private long startTime;
	    private long elapsedTime;
	    private static final long timeQuantum=5000;  // set time quantum 
	    
	    public Process(int processId,long serviceTime){
	    	this.setProcessId(processId);
	    	this.serviceTime=serviceTime;
	    }

	    public void runTime() {
	    	   startTime=System.currentTimeMillis();
	    	while(true){
	    		elapsedTime=System.currentTimeMillis()-startTime;
	    		
	    		if(serviceTime<=elapsedTime){
	    			notifyObservers();			
	    			break;						// end the whole process
	    		}else if(timeQuantum<=elapsedTime){
	    			notifyObservers();	
	    			serviceTime-=timeQuantum;
	    			break;						//suspend process after timeout
	    		}
	    		
	    		
	    	}

	    }

		@Override
		public void update(Observable arg0, Object arg1) {
			// TODO Auto-generated method stub
			
		}

		public int getProcessId() {
			return processId;
		}

		public void setProcessId(int processId) {
			this.processId = processId;
		}
}
