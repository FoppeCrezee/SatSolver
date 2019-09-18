/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import java.io.*;
import java.util.ArrayList;

/**
 * Reads in the file
 *
 * @author foppe
 */
public class FileReader {

    String line = "";
    ArrayList<Clause> list = new ArrayList();
    ArrayList<Literal> statements = new ArrayList<Literal>();
    String path;
    //int i = 0;

    public FileReader(String path) {
        this.path = path;
    }

    public ArrayList<Clause> main() {
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            int count = fileInformation(br.readLine());
            makeStatementList(count);
            while ((line = br.readLine()) != null) {
                String[] gegeven = line.split("0");
                String test = gegeven[0];
                Clause rule = new Clause(test);
                if (!rule.checkTaut()) {
                    list.add(rule);
                }
            }
        } catch (Exception e) {
        }

        return list;
    }

    /**
     * Reads in the sudokufile and converts every given number to a clause
     * @param path is the path to the sudokufile
     * @param l is the index of the line which we want to read
     */
    public ArrayList<Clause> SudokuReader(String path, int l) {
        ArrayList<Clause> sudokuClauses = new ArrayList<>();
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            int k = l+1;
            for(int f = 0; f < l; f++){
                line = br.readLine();
            }
            
            while (((line = br.readLine()) != null) && l != k) {
                String sudoku = line;

                for (int i = 9; i < 90; i++) {
                    String number = "";
                    number = number + i / 9;
                    number = number + ((i % 9) + 1);
                    if (!sudoku.substring(i - 9, i - 8).equals(".")) {
                        number = number + sudoku.substring(i - 9, i - 8);
                        Clause clause = new Clause(number + " ");
                        sudokuClauses.add(clause);
                    } else {
                    }
                }
                l++;
            }

        } catch (Exception e) {
        }
        return sudokuClauses;
    }

    private int fileInformation(String information) {
        String testje = information.substring(6, information.length());
        //int count;
        String lijn = "";
        for (int i = 0; i < testje.length(); i++) {
            //System.out.println(testje.substring(i, i + 1));
            if (!testje.substring(i, i + 1).equals(" ")) {
                lijn = lijn + testje.substring(i, i + 1);
            } else {

                return Integer.parseInt(lijn);
            }
        }
        return 0;

    }

    private void makeStatementList(int count) {
        for (int i = 1; i < count - 110; i++) {
            if (((i + 110) % 10 != 0) && ((((i + 110) / 10) % 10) != 0)) {
                statements.add(new Literal(i + 110));
            }
        }
    }

    public ArrayList<Literal> getStatements() {
        return statements;
    }

}
