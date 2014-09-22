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
    private long tempTime;
    private int processId;
    private int  status;//if 1 finished if 2 not finished if 3 blocked

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
      public long getTempTime() {
        return tempTime;
    }

    /**
     * @param tempTime the tempTime to set
     */
    public void setTempTime(long tempTime) {
        this.tempTime = tempTime;
    }

}
