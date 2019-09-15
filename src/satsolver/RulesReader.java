/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import java.util.ArrayList;
import java.util.Random;

/**
 * Is the main Algorithm
 * @author foppe
 */
public class RulesReader {

    private ArrayList<Clause> listOfClauses;
    private ArrayList<Literal> statements;
    private Random r = new Random();

    
    /**
     *  Is the main algorithm which consist of checking if there is a unit clause, a pure literal or making a split.
     *  @param list is the list with all the different clauses
     *  @param statements is the list with all the variables (literals)
     *  @return This returns an ArrayList with all the statements
     */
    public ArrayList<Literal> dp(ArrayList<Clause> list, ArrayList<Literal> statements) {
        this.statements = statements;
        this.listOfClauses = list;
        if (list.isEmpty()) {
            return statements;
        } else {

            if (checkUnitClause() != null) {
                return statements;
            }
            
            //Don't know if we need this very timeconsuming
            if (checkPureLiteral() != null) {
                return statements;
            }
            
            if (!list.isEmpty()) {
                boolean random = r.nextBoolean();
                if (!pickRandom(random)) {
                    pickRandom(!random);
                    //System.out.println("Foutje");
//                    if (dp(list, statements) != null) {
//                        System.out.println("Foutje");
//                        return statements;
//                    }
                } else {
                    dp(list, statements);
                }
            }

        }
        return statements;
    }

    /**
     * @param random is the random boolean value which the next empty variable will be made
     * @return false if there is an empty clause, which means there was made a mistake. It returns true if no empty clauses were found.
     */
    private boolean pickRandom(boolean random) {
        int next = getNextEmptyStatement();
        if (next == -1) {
            return false;
        }
        //if the boolean is true
        if (random) {
            statements.get(next).setValue(1);
            removeClause(statements.get(next).getName());
            if (listOfClauses.isEmpty()) {
                return true;
            } else {
                dp(listOfClauses, statements);
            }
            if (checkEmptyClause()) {
                return false;
            }
        //if the boolean is false
        } else {
            statements.get(next).setValue(-1);
            removeClause(statements.get(next).getName() * -1);
            if (listOfClauses.isEmpty()) {
                return true;
            } else {
                dp(listOfClauses, statements);
            }
            if (checkEmptyClause()) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return returns true if there is an empty clause in the list. Returns false if no empty list has been found.
     */
    private boolean checkEmptyClause() {
        checkUnitClause();
        checkPureLiteral();

        for (int i = 0; i < listOfClauses.size(); i++) {
            if (listOfClauses.get(i).getRules().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    
    /**
     * @return Returns an ArrayList with all the statements after checking if there is a unit clause
     */
    private ArrayList<Literal> checkUnitClause() {
        int variable = 0;

        for (int i = 0; i < listOfClauses.size(); i++) {
            if (listOfClauses.get(i).getRules().size() == 1) {
                variable = listOfClauses.get(i).getRules().get(0);
                //int variable komt maar 1 keer voor
                for (int j = 0; j < statements.size(); j++) {
                    if ((statements.get(j).getName() == variable) || (statements.get(j).getName() == variable * -1)) {

                        if (variable > 0) {
                            statements.get(j).setValue(1);
                            removeClause(variable);
                            if (dp(listOfClauses, statements) != null) {
                                return statements;
                            }
                        } else {
                            statements.get(j).setValue(-1);
                            removeClause(variable);
                            if (dp(listOfClauses, statements) != null) {
                                return statements;
                            }
                        }

                    }

                }
            }
        }

        if (listOfClauses.size() == 0) {
            return statements;
        }
        return null;
    }

    private ArrayList<Literal> checkPureLiteral() {
        for (int i = 0; i < statements.size(); i++) {
            if (statements.get(i).getValue() == 0) {
                if (checkNumber(statements.get(i).getName()) == 1) {
                    statements.get(i).setValue(1);
                    removeClause(statements.get(i).getName());
                    System.out.println("Een pure");
                    if (dp(listOfClauses, statements) != null) {
                        return statements;
                    }
                } else if (checkNumber(statements.get(i).getName()) == -1) {
                    statements.get(i).setValue(-1);
                    removeClause(statements.get(i).getName() * -1);
                    System.out.println("Een pure");
                    if (dp(listOfClauses, statements) != null) {
                        return statements;
                    }
                } else {
                }
            }
        }
        if (listOfClauses.size() == 0) {
            dp(listOfClauses, statements);
        }
        return null;
    }

    private int getNextEmptyStatement() {
        try {
            for (int i = 0; i < statements.size(); i++) {
                if (statements.get(i).getValue() == 0) {
                    return i;
                }
            }
        } catch (Exception e) {
            return -1;
        }
        return -1;
    }

    private int checkNumber(int number) {
        int positive = 0;
        int negative = 0;
        for (int i = 0; i < listOfClauses.size(); i++) {
            if (listOfClauses.get(i).checkRuleNegative(number)) {
                negative++;
            }
            if (listOfClauses.get(i).checkRulePositive(number)) {
                positive++;
            }
        }
        if (positive != 0 && negative == 0) {
            return 1;
        } else if (negative != 0 && positive == 0) {
            return -1;
        } else {
            return 0;
        }
    }

    private void removeClause(int number) {
        if (number > 0) {
            for (int i = 0; i < listOfClauses.size(); i++) {
                if (listOfClauses.get(i).checkRulePositive(number)) {
                    listOfClauses.remove(i);
                    removeClause(number);
                } else if (listOfClauses.get(i).checkRuleNegative(number)) {
                    listOfClauses.get(i).removeNumber(number * -1);
                }
            }
        } else if (number < 0) {
            for (int i = 0; i < listOfClauses.size(); i++) {
                if (listOfClauses.get(i).checkRulePositive(number)) {
                    listOfClauses.remove(i);
                    removeClause(number);
                } else if (listOfClauses.get(i).checkRulePositive(number * -1)) {
                    listOfClauses.get(i).removeNumber(number * -1);
                }
            }
        }

    }

}
