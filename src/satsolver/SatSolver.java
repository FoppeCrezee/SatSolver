/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import java.util.ArrayList;

/**
 *
 * @author foppe
 */
public class SatSolver {

    private static ArrayList<Rule> list = new ArrayList();
    private static ArrayList<Statement> statements = new ArrayList<Statement>();
    private static int time = 0;
    private static int timeReader = 0;
    private static String path = "";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        FileChooser chooser = new FileChooser();
        path = chooser.main();
        
        
        int milis_startTime = 0;
        milis_startTime = (int) System.currentTimeMillis();
        int milis_startTimeReader = 0;
        milis_startTimeReader = (int) System.currentTimeMillis();

        FileReader reader = new FileReader(path);
        statements = reader.getStatements();
        list = reader.main();
        
        int millis_endTimeReader = 0;
        millis_endTimeReader = (int) System.currentTimeMillis();
        timeReader = millis_endTimeReader - milis_startTimeReader;
        
        
        RulesReader rulesReader = new RulesReader();
        statements = rulesReader.dp(list, statements);
        
        
        int millis_endTime = 0;
        millis_endTime = (int) System.currentTimeMillis();
        time = millis_endTime - milis_startTime;
        printStatements();
        // TODO code application logic here
    }

    private static void printStatements() {
        System.out.println("Reading file took: " + timeReader * 0.001 + " seconds");
        System.out.println("Calculating solution took: " + time * 0.001 + " seconds");
        System.out.println("|-----------------------------|");
        System.out.print("|");
        int j = 0;
        int k = 0;
        for (int i = 0; i < statements.size(); i++) {
            if (statements.get(i).getValue() == 1) {
                System.out.print((statements.get(i).getName() % 10) + "  ");
                j++;
                if (j % 9 == 0) {
                    System.out.println("|");
                    System.out.print("|");
                    k++;
                    if (k % 3 == 0) {
                        System.out.println("-----------------------------|");
                        System.out.print("|");
                    }
                } else if (j % 3 == 0) {
                    System.out.print("|");
                }

            }

        }
    }

}
