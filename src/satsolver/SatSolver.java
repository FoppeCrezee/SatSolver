/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

/**
 * Starts the whole algorithm
 *
 * @author foppe
 */
public class SatSolver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CloneNotSupportedException {
        String DPH = args[0];
        int heuristics = 0;
        try {
            heuristics = Integer.parseInt(DPH.substring(2, 3));
        } catch (Exception e) {
            System.out.println("Give a valid heuristic: " + "\n" + "-S1 for random" + "\n" + "-S2 for MOM" + "\n" + "-S3 for FLSC");
            System.exit(0);
        }
        String fileName = args[1];
        System.out.println("-S" + heuristics);
        System.out.println(fileName);
        Start start = new Start(heuristics, fileName);
        start.main();

    }
}
