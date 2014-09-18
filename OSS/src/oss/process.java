/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package oss;

/**
 *
 * @author Malaka
 */
public class process {

    public static void main(String[] args) {
        new Process1().runTime();
    }
}

class Process1 {

    private long serviceTime;
    private long startTime;
    private long endTime;

    public void runTime() {
        boolean i = true;
        startTime = System.currentTimeMillis();
         System.out.println("" + startTime);
        while (i) {
            endTime = System.currentTimeMillis();
            if (endTime >= 5000+ startTime) {
                i = false;
                System.out.println("" + endTime);

            }
        }

    }
}
