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
 *
 * @author foppe
 */
public class RulesReader {

    private ArrayList<Clause> listOfClauses;
    private ArrayList<Literal> listOFLiterals;
    private ArrayList<Clause> startingClauses;
    private ArrayList<Literal> startingLiterals;
    private Random r = new Random();

    /**
     * @param list is the list with all the different clauses
     * @param statements is the list with all the variables (literals)
     */
    public RulesReader(ArrayList<Clause> list, ArrayList<Literal> statements, ArrayList<Clause> startingList, ArrayList<Literal> startingStatements) {
        this.listOFLiterals = statements;
        this.listOfClauses = list;
        this.startingClauses = startingList;
        this.startingLiterals = startingStatements;
    }

    /**
     * Is the main algorithm which consist of checking if there is a unit
     * clause, a pure literal or making a split.
     *
     * @return This returns an ArrayList with all the statements
     */
    public ArrayList<Literal> dp() {

        if (listOfClauses.isEmpty()) {
            return listOFLiterals;
        } else {

            checkUnitClause();

//            Don't know if we need this very timeconsuming
//            checkPureLiteral();

            if (!listOfClauses.isEmpty()) {
                int next = getNextUnknownLiteral();
                boolean random = r.nextBoolean();
                random = false;
                if (!pickRandom(random, next)) {

                    //listOfClauses = listOfClauses2;

                    //mistake was made, so we need to reset all the data.
                    listOfClauses = startingClauses;
                    listOFLiterals = startingLiterals;
                    
                    checkUnitClause();
                    
                    pickRandom(!random, next);

//                    RulesReader iteration = new RulesReader(listOfClauses, listOFLiterals);
//                    if (iteration.dp() != null || this.checkEmptyClause() == true) {
//                        System.out.println("Foutje");
//                        return listOFLiterals;
//                    }
                } else {
//                    if (!listOfClauses.isEmpty()) {
//                        RulesReader iteration = new RulesReader(listOfClauses, listOFLiterals);
//                        listOFLiterals = iteration.dp();
//                        return listOFLiterals;
//                    }

                }
            }
        }

//        if (!listOfClauses.isEmpty() || this.checkEmptyClause()) {
//            return null;
//        }
        return listOFLiterals;
    }

    /**
     * @param random is the random boolean value which the next empty variable
     * will be made
     * @return false if there is an empty clause, which means there was made a
     * mistake. It returns true if no empty clauses were found.
     */
    private boolean pickRandom(boolean random, int next) {
        //if no new variable is found return false
        if (next == -1) {
            return false;
        }
        //if the boolean is true
        if (random) {
            listOFLiterals.get(next).setValue(1);
            removeClause(listOFLiterals.get(next).getName());
            if (listOfClauses.isEmpty()) {
                return true;
            } else {
                if (checkEmptyClause()) {
                    System.out.println("and wrong");
                    return false;
                }
                if (!listOfClauses.isEmpty()) {
                    //starts a new iteration of recursion.
                    RulesReader iteration = new RulesReader(listOfClauses, listOFLiterals, new ArrayList<Clause>(startingClauses), new ArrayList<Literal>(startingLiterals));
                    listOFLiterals = iteration.dp();
                }
                if (checkEmptyClause()) {
                    System.out.println("and wrong");
                    return false;
                }
            }

            //if the boolean is false
        } else {
            listOFLiterals.get(next).setValue(-1);
            removeClause(listOFLiterals.get(next).getName() * -1);
            if (listOfClauses.isEmpty()) {
                return true;
            } else {
                if (checkEmptyClause()) {
                    System.out.println("and wrong");
                    return false;
                }
                if (!listOfClauses.isEmpty()) {
                    //starts a new iteration of recursion.
                    RulesReader iteration = new RulesReader(listOfClauses, listOFLiterals, new ArrayList<Clause>(startingClauses), new ArrayList<Literal>(startingLiterals));
                    listOFLiterals = iteration.dp();
                }
                if (checkEmptyClause()) {
                    System.out.println("and wrong");
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * @return returns true if there is an empty clause in the list. Returns
     * false if no empty list has been found.
     */
    private boolean checkEmptyClause() {
        for (int i = 0; i < listOfClauses.size(); i++) {
            if (listOfClauses.get(i).getRules().isEmpty()) {
                System.out.println("Empty!");
                return true;
            }
        }
        System.out.println("not Empty");
        return false;
    }

    /**
     * @return Returns an ArrayList with all the statements after checking if
     * there is a unit clause
     */
    private ArrayList<Literal> checkUnitClause() {
        int variable = 0;

        for (int i = 0; i < listOfClauses.size(); i++) {
            if (listOfClauses.get(i).getRules().size() == 1) {
                variable = listOfClauses.get(i).getRules().get(0);
                //int variable komt maar 1 keer voor
                for (int j = 0; j < listOFLiterals.size(); j++) {
                    if ((listOFLiterals.get(j).getName() == variable) || (listOFLiterals.get(j).getName() == variable * -1)) {

                        if (variable > 0) {
//                            System.out.println("Set " + variable + " 1");
                            listOFLiterals.get(j).setValue(1);
                            if (listOFLiterals.get(j).getName() == 716) {
                                System.out.println(listOFLiterals.get(j).getName());
                            }
                            removeClause(variable);
                            i = 0;
                        } else {
//                            System.out.println("Set " + variable + " -1");
                            listOFLiterals.get(j).setValue(-1);
                            removeClause(variable);
                            i = 0;
                        }

                    }

                }
            }
        }
        if (listOfClauses.size() == 0) {
            return listOFLiterals;
        } else {
            return null;
        }
    }

    private ArrayList<Literal> checkPureLiteral() {
        for (int i = 0; i < listOFLiterals.size(); i++) {
            if (listOFLiterals.get(i).getValue() == 0) {
                if (checkNumber(listOFLiterals.get(i).getName()) == 1) {
                    listOFLiterals.get(i).setValue(1);
                    removeClause(listOFLiterals.get(i).getName());
                    System.out.println("Een pure");
                } else if (checkNumber(listOFLiterals.get(i).getName()) == -1) {
                    listOFLiterals.get(i).setValue(-1);
                    removeClause(listOFLiterals.get(i).getName() * -1);
                    System.out.println("Een pure");
                } else {
                }
            }
        }
        return null;
    }

    private int getNextUnknownLiteral() {
        try {
            for (int i = 0; i < listOFLiterals.size(); i++) {
                if (listOFLiterals.get(i).getValue() == 0) {
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

    /**
     * Removes a literal from a clause if the negation from that literal is found.
     * Removes a clause from the list when the clause contains that literal
     * @param number is the literal which we want to remove.
     */
    private void removeClause(int number) {
        // System.out.println("verwijder: " + number);
        if (number > 0) {
            for (int i = 0; i < listOfClauses.size(); i++) {
                if (listOfClauses.get(i).checkRulePositive(number)) {
                    listOfClauses.remove(i);
                    i--;
                } else if (listOfClauses.get(i).checkRuleNegative(number)) {
                    listOfClauses.get(i).removeNumber(number * -1);
                    i--;
                }
            }
        } else if (number < 0) {
            for (int i = 0; i < listOfClauses.size(); i++) {
                if (listOfClauses.get(i).checkRulePositive(number)) {
                    listOfClauses.remove(i);
                    i--;
                } else if (listOfClauses.get(i).checkRulePositive(number * -1)) {
                    listOfClauses.get(i).removeNumber(number * -1);
                    i--;
                }
            }
        }

    }

}
