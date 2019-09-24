/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author foppe
 */
public class Start {

    private ArrayList<Clause> clauses;
    private ArrayList<Clause> startingClauses;
    private ArrayList<Literal> statementsFinal;
    private ArrayList<Literal> startingLiterals;
    //all clauses from reading the sudoku
    private ArrayList<Clause> sudokuClauses;

    private int time = 0;
    private int timeReader = 0;
    private String path;
    private String sudokuPath;
    
    private int heuristics;
    private String fileName;

    public Start(int heuristics, String fileName){
        this.heuristics = heuristics;
        this.fileName = fileName;
    }
    
    public void main() throws CloneNotSupportedException {
        clauses = new ArrayList<Clause>();

//        First UI message
//        JOptionPane.showMessageDialog(null, "Choose sudoku rules which includes the sudoku you want to solve");
////        Lets you choose the file you want to read
//        FileChooser chooser = new FileChooser();
//        path = chooser.main();
//        JOptionPane.showMessageDialog(null, "Now choose the file with all the sudokus");
//        sudokuPath = chooser.main();
//        JOptionPane.showMessageDialog(null, "Which line in the file do you want to solve?");
//        int index = Integer.parseInt(JOptionPane.showInputDialog(null, "Which line in the file do you want to solve?"));
        path = "C:\\Users\\foppe\\Desktop\\sudoku-rules.txt";
        sudokuPath = "C:\\Users\\foppe\\Desktop\\1000_sudokus.txt";
        int index = 2;

        //starts timer for reading the file
        int milis_startTimeReader = 0;
        milis_startTimeReader = (int) System.currentTimeMillis();

        //Reads all the file
        FileReader reader = new FileReader(path);
        statementsFinal = reader.getStatements();
        clauses = reader.main();
        FileReader readerStart = new FileReader(path);
        startingLiterals = readerStart.getStatements();
        startingClauses = readerStart.main();

        //merges all new clauses from the given numbers in the sudoku
        sudokuClauses = reader.SudokuReader(sudokuPath, index);
        clauses.addAll(sudokuClauses);
        startingClauses.addAll(sudokuClauses);

        //Ends the timer for reading the file
        int millis_endTimeReader = 0;
        millis_endTimeReader = (int) System.currentTimeMillis();
        timeReader = millis_endTimeReader - milis_startTimeReader;

        //starts timer for DP
        int milis_startTime = 0;
        milis_startTime = (int) System.currentTimeMillis();

        //Starts DP algorithm
//        System.out.println(clauses.size());
        RulesReader rulesReader = new RulesReader();
        statementsFinal = rulesReader.dp(clauses, statementsFinal, startingClauses, startingLiterals);
//        System.out.println("Na: " + clauses.size());

        //Ends dp timer
        int millis_endTime = 0;
        millis_endTime = (int) System.currentTimeMillis();
        time = millis_endTime - milis_startTime;

        //prints the result
        printStatements();
    }

    private void printStatements() {

        System.out.println("Reading file took: " + timeReader * 0.001 + " seconds");
        System.out.println("Calculating solution took: " + time * 0.001 + " seconds");
        System.out.println("|-----------------------------------|");
        System.out.print("|  ");
        int j = 0;
        int k = 0;
        if (statementsFinal != null) {
            for (int i = 0; i < statementsFinal.size(); i++) {
                if (statementsFinal.get(i).getValue() == 1) {
                    System.out.print((statementsFinal.get(i).getName() % 10) + "  ");
//                System.out.println(statements.get(i).getName());
                    j++;
                    if (j % 9 == 0) {
                        System.out.println("|");
                        
                        k++;
                        if (k % 3 == 0) {
                            System.out.println("|-----------------------------------|");
                            System.out.print("|  ");
                        }
                        else{
                            System.out.print("|  ");
                        }
                    } else if (j % 3 == 0) {
                        System.out.print("|  ");
                    }

                }

            }

            System.out.println("No value:");
            for (int i = 0; i < statementsFinal.size(); i++) {

                if (statementsFinal.get(i).getValue() == 0) {
                    System.out.println(statementsFinal.get(i).getName());
                }
            }
        } else {
            System.out.println("Not solvable");
        }

    }
}
