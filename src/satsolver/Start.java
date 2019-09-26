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
    
    private String path4;
    private String path9;
    private String path16;
    private String sudokuPath4;
    private String sudokuPath9;
    private String sudokuPath16;

    private int heuristics;
    private String fileName;

    public Start(int heuristics, String fileName) {
        this.heuristics = heuristics;
        this.fileName = fileName;
    }

    public void main() throws CloneNotSupportedException {
        clauses = new ArrayList<Clause>();
        path4 = "C:\\Users\\foppe\\Desktop\\sudoku-rules-4x4.txt";
        path9 = "C:\\Users\\foppe\\Desktop\\sudoku-rules.txt";
        path16 = "C:\\Users\\foppe\\Desktop\\sudoku-rules-16x16.txt";
        sudokuPath16 = "C:\\Users\\foppe\\Desktop\\16x16.txt";
        sudokuPath9 = "C:\\Users\\foppe\\Desktop\\Dmnhard.sdk.txt";
        sudokuPath4 = "C:\\Users\\foppe\\Desktop\\4x4.txt";
        int index = 9;

        //starts timer for reading the file
        int milis_startTimeReader = 0;
        milis_startTimeReader = (int) System.currentTimeMillis();

        //Reads all the file
        //First one is for 9*9, second one for 16*16
        FileReader reader = new FileReader(path4);
//        FileReader reader = new FileReader(path9);
//        FileReader reader = new FileReader(path16);

        statementsFinal = reader.getStatements();
        clauses = reader.main();

        //First one is for 9*9, second one for 16*16
        FileReader readerStart = new FileReader(path4);
//        FileReader readerStart = new FileReader(path9);
//        FileReader readerStart = new FileReader(path16);

        startingLiterals = readerStart.getStatements();
        startingClauses = readerStart.main();

        //merges all new clauses from the given numbers in the sudoku
        //First one is for 9*9, second one for 16*16
        sudokuClauses = reader.SudokuReader4(sudokuPath4, index);
//        sudokuClauses = reader.SudokuReader9(sudokuPath9, index);
//        sudokuClauses = reader.SudokuReader16(sudokuPath16, index);
        

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
        RulesReader rulesReader = new RulesReader();
        statementsFinal = rulesReader.dp(clauses, statementsFinal, startingClauses, startingLiterals);

        //Ends dp timer
        int millis_endTime = 0;
        millis_endTime = (int) System.currentTimeMillis();
        time = millis_endTime - milis_startTime;

        //prints the result
        rulesReader.printAmount();
//        printStatements9();
        printStatements4();
//        printStatements16();
    }

    private void printStatements9() {

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
                        } else {
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
    
    private void printStatements4() {

        System.out.println("Reading file took: " + timeReader * 0.001 + " seconds");
        System.out.println("Calculating solution took: " + time * 0.001 + " seconds");
        System.out.println("|-----------------|");
        System.out.print("|  ");
        int j = 0;
        int k = 0;
        if (statementsFinal != null) {
            for (int i = 0; i < statementsFinal.size(); i++) {
                if (statementsFinal.get(i).getValue() == 1) {
                    System.out.print((statementsFinal.get(i).getName() % 10) + "  ");
//                System.out.println(statements.get(i).getName());
                    j++;
                    if (j % 4 == 0) {
                        System.out.println("|");

                        k++;
                        if (k % 2 == 0) {
                            System.out.println("|-----------------|");
                            System.out.print("|  ");
                        } else {
                            System.out.print("|  ");
                        }
                    } else if (j % 2 == 0) {
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

    private void printStatements16() {

        System.out.println("Reading file took: " + timeReader * 0.001 + " seconds");
        System.out.println("Calculating solution took: " + time * 0.001 + " seconds");
        System.out.println("|-----------------------------------------------------------|");
        System.out.print("|  ");
        int j = 0;
        int k = 0;
        if (statementsFinal != null) {
            for (int i = 0; i < statementsFinal.size(); i++) {
                if (statementsFinal.get(i).getValue() == 1) {
                    int number = statementsFinal.get(i).getName();
                    int r = number / 289;
                    int c = (number - (289 * r)) / 17;
                    int v = (number - (289 * r) - (17 * c));
                    if (v > 9) {
                        System.out.print(v + " ");
                    } else {
                        System.out.print(" " + v + " ");
                    }
//                System.out.println(statements.get(i).getName());
                    j++;
                    if (j % 16 == 0) {
                        System.out.println("|");

                        k++;
                        if (k % 4 == 0) {
                            System.out.println("|-----------------------------------------------------------|");
                            System.out.print("|  ");
                        } else {
                            System.out.print("|  ");
                        }
                    } else if (j % 4 == 0) {
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
