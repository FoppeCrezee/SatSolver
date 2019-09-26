/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import java.util.ArrayList;
import javax.swing.JOptionPane;

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
        int heuristics = Integer.parseInt(DPH.substring(2, 3));
        String fileName = args[1];
        System.out.println(heuristics);
        System.out.println(fileName);
        Start start = new Start(heuristics, fileName);
        start.main();

    }
}
