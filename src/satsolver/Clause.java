/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import java.util.ArrayList;

/**
 * This is a clause
 * @author foppe
 */
public class Clause {

    private boolean isTrue = false;
    private ArrayList<Integer> statements = new ArrayList<Integer>();

    String lijn = "";

    public Clause(String list) {
        for (int i = 0; i < list.length(); i++) {
            if (!list.substring(i, i + 1).equals(" ")) {
                lijn = lijn + list.substring(i, i + 1);
            } else {
                int getal = Integer.parseInt(lijn);
                statements.add(getal);
                lijn = "";
                //checkTaut(getal);
            }
        }
        //printRule();

    }

    public boolean checkTaut() {
        for (int i = 0; i < statements.size(); i++) {
            int getal = statements.get(i);
            if (statements.size() != 0) {
                for (int j = 0; i < statements.size(); i++) {
                    if (statements.get(j) == getal * -1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    //TODO if statement is negative and positive in same statement delete

    public boolean getTrue() {
        return isTrue;
    }

    public ArrayList<Integer> getRules() {
        return statements;
    }

    public boolean checkRulePositive(int number) {
        for (int i = 0; i < statements.size(); i++) {
            if (statements.get(i) == number) {
                return true;
            }
        }
        return false;
    }

    public void removeNumber(int number) {
        for (int i = 0; i < statements.size(); i++) {
            if (statements.get(i) == number) {
                statements.remove(i);
                //TODO wat als leeg
            }
        }
    }

    public boolean checkRuleNegative(int number) {
        for (int i = 0; i < statements.size(); i++) {
            if (statements.get(i) == number * -1) {
                return true;
            }
        }
        return false;
    }

    public boolean checkRule(int number) {
        for (int i = 0; i < statements.size(); i++) {
            if ((statements.get(i) == number * -1) || statements.get(i) == number) {
                return true;
            }
        }
        return false;
    }

    private void printRule() {
        for (int i = 0; i < statements.size(); i++) {
            System.out.println(statements.get(i));
        }
    }

}
