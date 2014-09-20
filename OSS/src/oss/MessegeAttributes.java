/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package oss;

/**
 *
 * @author Malaka
 */
public class MessegeAttributes {
    private int processId;
    private int status;

    public MessegeAttributes(int processId,int status){
    	this.processId=processId;
    	this.status=status;
    }
    public int getProcessId() {
        return processId;
    }

    public int getStatus() {
        return status;
    }


}
