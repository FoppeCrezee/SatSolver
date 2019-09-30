/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import Heuristics1.FileReader;
import Heuristics1.Literal;
import Heuristics1.DPRandom;
import Heuristics1.Clause;
import java.util.ArrayList;

/**
 *
 * @author foppe
 */
public class Start {

    public static final int SUDOKU4 = 4;
    public static final int SUDOKU9 = 9;
    public static final int SUDOKU16 = 16;

    private ArrayList<Clause> clauses;
    private ArrayList<Literal> statementsFinal;
    private ArrayList<Clause> sudokuClauses;

    private int time = 0;
    private int timeReader = 0;

    //index is the line of the list with sudokus
    private int index = 2;
    //dif is if what kind of sudoku you want. 4 = 4*4, 9 = 9*9 & 16 = 16*16
    private int dif = 4;

    private String path4;
    private String path9;
    private String path16;
    private String sudokuPath4;
    private String sudokuPath9;
    private String sudokuPath16;

    private int heuristics;
    private String fileName;

    /**
     * @param heuristics number for which heuristics to use. 1 = random. 2 & 3
     * will be implemented later.
     * @param fileName name of the file with all the clauses which needs to be
     * solved.
     */
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

        H1();

//         else if(heuristics == 2){
//            H2();
//        }
    }

    private void H1() throws CloneNotSupportedException {
        //starts timer for reading the file
        int milis_startTimeReader = 0;
        milis_startTimeReader = (int) System.currentTimeMillis();

        if (dif == SUDOKU4) {
            FileReader reader = new FileReader(path4);
            statementsFinal = reader.getStatements();
            clauses = reader.main();
            sudokuClauses = reader.SudokuReader4(sudokuPath4, index);
        } else if (dif == SUDOKU9) {
            FileReader reader = new FileReader(path9);
            statementsFinal = reader.getStatements();
            clauses = reader.main();
            sudokuClauses = reader.SudokuReader9(sudokuPath9, index);
        } else {
            FileReader reader = new FileReader(path16);
            statementsFinal = reader.getStatements();
            clauses = reader.main();
            sudokuClauses = reader.SudokuReader16(sudokuPath16, index);
        }

        clauses.addAll(sudokuClauses);

        //Ends the timer for reading the file
        int millis_endTimeReader = 0;
        millis_endTimeReader = (int) System.currentTimeMillis();
        timeReader = millis_endTimeReader - milis_startTimeReader;

        //starts timer for DP
        int milis_startTime = 0;
        milis_startTime = (int) System.currentTimeMillis();

        //Starts DP algorithm
        DPRandom rulesReader = new DPRandom(heuristics);
        statementsFinal = rulesReader.dp(clauses, statementsFinal);

        //Ends dp timer
        int millis_endTime = 0;
        millis_endTime = (int) System.currentTimeMillis();
        time = millis_endTime - milis_startTime;

        //prints the result
        Printer printer = new Printer(statementsFinal, timeReader, time);
        if (dif == SUDOKU4) {
            printer.printStatements4();
        } else if (dif == SUDOKU9) {
            printer.printStatements9();
        } else {
            printer.printStatements16();
        }

        rulesReader.printAmount();
    }

}
