/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author foppe
 */
public class Start {

    private ArrayList<Clause> clauses;
    private ArrayList<Clause> startingClauses;
    private ArrayList<Literal> statementsFinal;
    private ArrayList<Literal> startingLiterals;

    private int time = 0;
    private int timeReader = 0;
    private String path = "";

    public void main() {
        clauses = new ArrayList<Clause>();

        //First UI message
        //JOptionPane.showMessageDialog(null, "Choose sudoku rules which includes the sudoku you want to solve");
        //Lets you choose the file you want to read
        //FileChooser chooser = new FileChooser();
        //path = chooser.main();
        path = "C:\\Users\\foppe\\Desktop\\sudoku-rules.txt";

        //starts timer for reading the file
        int milis_startTimeReader = 0;
        milis_startTimeReader = (int) System.currentTimeMillis();

        //Reads the file
        FileReader reader = new FileReader(path);
        statementsFinal = reader.getStatements();
        clauses = reader.main();
        FileReader readerStart = new FileReader(path);
        startingLiterals = readerStart.getStatements();
        startingClauses = readerStart.main();

//        ArrayList<Clause> clauses2 = new ArrayList<Clause>(clauses);
//        clauses2 = clauses;
        //Ends the timer for reading the file
        int millis_endTimeReader = 0;
        millis_endTimeReader = (int) System.currentTimeMillis();
        timeReader = millis_endTimeReader - milis_startTimeReader;

        //starts timer for DP
        int milis_startTime = 0;
        milis_startTime = (int) System.currentTimeMillis();

        //Starts DP algorithm
        System.out.println(clauses.size());
        RulesReader rulesReader = new RulesReader(clauses, statementsFinal, startingClauses, startingLiterals);
        statementsFinal = rulesReader.dp();
        System.out.println("Na: " + clauses.size());

        //Ends dp timer
        int millis_endTime = 0;
        millis_endTime = (int) System.currentTimeMillis();
        time = millis_endTime - milis_startTime;

        //prints the result
        printStatements();
    }

    private void printStatements() {

//        for (int i = 0; i < statements.size(); i++) {
//            if (statements.get(i).getValue() == 1) {
//                System.out.println(statements.get(i).getName());
//            }
//        }
        System.out.println("Reading file took: " + timeReader * 0.001 + " seconds");
        System.out.println("Calculating solution took: " + time * 0.001 + " seconds");
        System.out.println("|-----------------------------|");
        System.out.print("|");
        int j = 0;
        int k = 0;
        for (int i = 0; i < statementsFinal.size(); i++) {
            if (statementsFinal.get(i).getValue() == 1) {
                System.out.print((statementsFinal.get(i).getName() % 10) + "  ");
//                System.out.println(statements.get(i).getName());
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
        System.out.println("No value:");
        for (int i = 0; i < statementsFinal.size(); i++) {

            if (statementsFinal.get(i).getValue() == 0) {
                System.out.println(statementsFinal.get(i).getName());
            }
        }

        System.out.println(clauses.size());
    }
}
