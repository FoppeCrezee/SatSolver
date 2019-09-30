/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Heuristics1;

import java.io.*;
import java.util.ArrayList;

/**
 * Reads in the file
 *
 * @author foppe
 */
public class FileReader {

    private String line = "";
    private ArrayList<Clause> list = new ArrayList();
//    private ArrayList<Literal> statements = new ArrayList<Literal>();
    private ArrayList<Literal> listOfLiterals = new ArrayList<>();
    private String path;
    //int i = 0;

    public FileReader(String path) {
        this.path = path;
    }

    public ArrayList<Clause> main() {
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            int count = fileInformation(br.readLine());
            
//            makeStatementList(count);
            while ((line = br.readLine()) != null) {
                String[] gegeven = line.split(" 0");
                String test = gegeven[0];
//                System.out.println(test);
                Clause rule = new Clause(test + " ");
                if (!rule.checkTaut()) {
                    list.add(rule);
                }
                addLiterals(test + " ");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    /**
     * Reads in the sudokufile and converts every given number to a clause
     *
     * @param path is the path to the sudokufile
     * @param l is the index of the line which we want to read
     */
    public ArrayList<Clause> SudokuReader9(String path, int l) {
        ArrayList<Clause> sudokuClauses = new ArrayList<>();
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            int k = l + 1;
            for (int f = 0; f < l; f++) {
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
    
        public ArrayList<Clause> SudokuReader4(String path, int l) {
        ArrayList<Clause> sudokuClauses = new ArrayList<>();
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            int k = l + 1;
            for (int f = 0; f < l; f++) {
                line = br.readLine();
            }

            while (((line = br.readLine()) != null) && l != k) {
                String sudoku = line;

                for (int i = 4; i < 17; i++) {
                    String number = "";
                    number = number + i / 4;
                    number = number + ((i % 4) + 1);
                    if (!sudoku.substring(i - 4, i - 3).equals(".")) {
                        number = number + sudoku.substring(i - 4, i - 3);
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
    
    

    public ArrayList<Clause> SudokuReader16(String path, int l) {
        ArrayList<Clause> sudokuClauses = new ArrayList<>();
        try {
            File file = new File(path);
            BufferedReader br = new BufferedReader(new java.io.FileReader(file));
            int k = l + 1;
            for (int f = 0; f < l; f++) {
                line = br.readLine();
            }

            while (((line = br.readLine()) != null) && l != k) {
                String sudoku = line;
                int r = 1;
                int c = 1;

                for (int i = 1; i < 257; i++) {
                    String number = "";
                    if (c == 17) {
                        r++;
                        c = 1;
                    }

                    if (!sudoku.substring(i - 1, i).equals(".")) {                 
                        int v = hexToDec(sudoku.substring(i - 1, i));
                        int encode = 289 * r + 17 * c + v;
                        number = Integer.toString(encode);
//                        number = number + sudoku.substring(i - 9, i - 8);
                        Clause clause = new Clause(number + " ");
                        sudokuClauses.add(clause);
                    } else {
                    }
                    c++;
                }
                l++;
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return sudokuClauses;
    }

    private int hexToDec(String hex) {
        try {
            int number = Integer.parseInt(hex);
            return number;
        } catch (java.lang.NumberFormatException e) {
            switch (hex) {
                case "A":
                    return 10;
                case "B":
                    return 11;
                case "C":
                    return 12;
                case "D":
                    return 13;
                case "E":
                    return 14;
                case "F":
                    return 15;
                default:
                    return 16;
            }
        }

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

    private void addLiterals(String list) {
        String lijn = "";
        for (int i = 0; i < list.length(); i++) {
            if (!list.substring(i, i + 1).equals(" ")) {
                lijn = lijn + list.substring(i, i + 1);
            } else {
                int getal = Integer.parseInt(lijn);
                if (getal < 0) {
                    getal = getal * -1;
                }
                if (!inList(getal)) {
                    listOfLiterals.add(new Literal(getal));
                }
                lijn = "";
            }
        }
    }

    private boolean inList(int number) {
        for (int i = 0; i < listOfLiterals.size(); i++) {
            if (listOfLiterals.get(i).getName() == number) {
                return true;
            }
        }
        return false;
    }

//    private void makeStatementList(int count) {
//        for (int i = 1; i < count - 109; i++) {
//            if (((i + 110) % 10 != 0) && ((((i + 110) / 10) % 10) != 0)) {
//                statements.add(new Literal(i + 110));
//            }
//        }
//    }
    public ArrayList<Literal> getStatements() {
        return listOfLiterals;
    }

}
