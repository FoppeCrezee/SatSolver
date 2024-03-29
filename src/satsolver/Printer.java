/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import Heuristics1.Literal;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author foppe
 */
public class Printer {

    private ArrayList<Literal> statementsFinal;
    private int time;
    private int timeReader;

    public Printer(ArrayList<Literal> statementsFinal, int timeReader, int time) {
        this.statementsFinal = statementsFinal;
        this.time = time;
        this.timeReader = timeReader;
    }

    public void nomralPrint() {
        int amount = 0;
        int highest = 0;
        for (Literal literal : statementsFinal) {
            if(literal.getValue() > 0){
                amount++;
                if(literal.getName() > highest){
                    highest = literal.getName();
                }
            }
        }
        
        System.out.println("Reading file took: " + timeReader * 0.001 + " seconds");
        System.out.println("Calculating solution took: " + time * 0.001 + " seconds");
        
        PrintWriter writer;
        
        try {
            writer = new PrintWriter("answerset.txt", "UTF-8");
            writer.println("p cnf " + highest + " " +  amount);
            for (Literal literal : statementsFinal) {
                if (literal.getValue() > 0) {
                    writer.println(literal.getName() + " 0");
                }
            }
            writer.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Printer.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Answerset can be found in: SAT/answerset.txt");

    }

    public void printStatements4() {

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

    public void printStatements9() {

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

    public void printStatements16() {

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
