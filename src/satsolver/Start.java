/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import Heuristics1.FileReaderH1;
import Heuristics1.LiteralH1;
import Heuristics1.DPRandom;
import Heuristics1.ClauseH1;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author foppe
 */
public class Start {

    private ArrayList<ClauseH1> clauses;
    private ArrayList<ClauseH1> startingClauses;
    private ArrayList<LiteralH1> statementsFinal;
    private ArrayList<LiteralH1> startingLiterals;
    //all clauses from reading the sudoku
    private ArrayList<ClauseH1> sudokuClauses;

    private int time = 0;
    private int timeReader = 0;

    //index is the line of the list with sudokus
    private int index = 9;
    //dif is if what kind of sudoku you want. 4 = 4*4, 9 = 9*9 & 16 = 16*16
    private int dif = 16;

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
        clauses = new ArrayList<ClauseH1>();
        path4 = "C:\\Users\\foppe\\Desktop\\sudoku-rules-4x4.txt";
        path9 = "C:\\Users\\foppe\\Desktop\\sudoku-rules.txt";
        path16 = "C:\\Users\\foppe\\Desktop\\sudoku-rules-16x16.txt";
        sudokuPath16 = "C:\\Users\\foppe\\Desktop\\16x16.txt";
        sudokuPath9 = "C:\\Users\\foppe\\Desktop\\Dmnhard.sdk.txt";
        sudokuPath4 = "C:\\Users\\foppe\\Desktop\\4x4.txt";

        if (heuristics == 1) {
            H1();
        }

    }

    private void H1() throws CloneNotSupportedException {
        //starts timer for reading the file
        int milis_startTimeReader = 0;
        milis_startTimeReader = (int) System.currentTimeMillis();

        if (dif == 4) {
            FileReaderH1 reader = new FileReaderH1(path4);
            statementsFinal = reader.getStatements();
            clauses = reader.main();
            FileReaderH1 readerStart = new FileReaderH1(path4);
            startingLiterals = readerStart.getStatements();
            startingClauses = readerStart.main();
            sudokuClauses = reader.SudokuReader4(sudokuPath4, index);
        } else if (dif == 9) {
            FileReaderH1 reader = new FileReaderH1(path9);
            statementsFinal = reader.getStatements();
            clauses = reader.main();
            FileReaderH1 readerStart = new FileReaderH1(path9);
            startingLiterals = readerStart.getStatements();
            startingClauses = readerStart.main();
            sudokuClauses = reader.SudokuReader9(sudokuPath9, index);
        } else {
            FileReaderH1 reader = new FileReaderH1(path16);
            statementsFinal = reader.getStatements();
            clauses = reader.main();
            FileReaderH1 readerStart = new FileReaderH1(path16);
            startingLiterals = readerStart.getStatements();
            startingClauses = readerStart.main();
            sudokuClauses = reader.SudokuReader16(sudokuPath16, index);
        }

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
        DPRandom rulesReader = new DPRandom();
        statementsFinal = rulesReader.dp(clauses, statementsFinal, startingClauses, startingLiterals);

        //Ends dp timer
        int millis_endTime = 0;
        millis_endTime = (int) System.currentTimeMillis();
        time = millis_endTime - milis_startTime;

        //prints the result
        Printer printer = new Printer(statementsFinal, timeReader, time);
        if (dif == 4) {
            printer.printStatements4();
        } else if (dif == 9) {
            printer.printStatements9();
        } else {
            printer.printStatements16();
        }

        rulesReader.printAmount();
    }

}
