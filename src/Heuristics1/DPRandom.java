/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Heuristics1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Is the main Algorithm
 *
 * @author foppe
 */
public class DPRandom {

    private Random r = new Random();
    private int choicesAmount = 0;
    private int wrongChoices = 0;

    /**
     * Is the main algorithm which consist of checking if there is a unit
     * clause, a pure literal or making a split.
     *
     * @param listOfClauses
     * @param listOfLiterals
     * @param startingList
     * @param startingStatements
     *
     * @return This returns an ArrayList with all the statements
     * @throws java.lang.CloneNotSupportedException
     */
    public ArrayList<LiteralH1> dp(ArrayList<ClauseH1> listOfClauses, ArrayList<LiteralH1> listOfLiterals, ArrayList<ClauseH1> startingList, ArrayList<LiteralH1> startingStatements) throws CloneNotSupportedException {

        if (listOfClauses.isEmpty()) {
            return listOfLiterals;
        } else if (checkEmptyClause(listOfClauses)) {
            return null;
        }

        //copy of list
        ArrayList<ClauseH1> backupC = new ArrayList<>();

        Iterator<ClauseH1> iterator = listOfClauses.iterator();
        while (iterator.hasNext()) {
            backupC.add((ClauseH1) iterator.next().clone());
        }
        //copy of list
        ArrayList<LiteralH1> backupL = new ArrayList<LiteralH1>();

        Iterator<LiteralH1> iterator2 = listOfLiterals.iterator();
        while (iterator2.hasNext()) {
            backupL.add((LiteralH1) iterator2.next().clone());
        }

        checkUnitClause(listOfLiterals, listOfClauses);
//            Don't know if we need this very timeconsuming
//            checkPureLiteral(listOfClauses, listOfLiterals);
        if (!listOfClauses.isEmpty()) {
            int next = getNextUnknownLiteral(listOfLiterals);
            if (next == -1) {
                return null;
            }
            choicesAmount++;
            boolean random = r.nextBoolean();
            if (random) {
                listOfLiterals.get(next).setValue(1);
                removeClause(listOfLiterals.get(next).getName(), listOfClauses);
            } else {
                listOfLiterals.get(next).setValue(-1);
                removeClause(listOfLiterals.get(next).getName() * -1, listOfClauses);
            }
            
            listOfLiterals = this.dp(new ArrayList<ClauseH1>(listOfClauses), new ArrayList<LiteralH1>(listOfLiterals), new ArrayList<ClauseH1>(listOfClauses), new ArrayList<LiteralH1>(listOfLiterals));
            if (listOfLiterals == null) {
                wrongChoices++;
                if (listOfClauses.equals(backupC)) {
                }
                listOfClauses = new ArrayList(backupC);
                listOfLiterals = new ArrayList(backupL);

                if (!random) {
                    listOfLiterals.get(next).setValue(1);
                    removeClause(listOfLiterals.get(next).getName(), listOfClauses);
                } else {
                    listOfLiterals.get(next).setValue(-1);
                    removeClause(listOfLiterals.get(next).getName() * -1, listOfClauses);
                }

                listOfLiterals = this.dp(new ArrayList<ClauseH1>(listOfClauses), new ArrayList<LiteralH1>(listOfLiterals), new ArrayList<ClauseH1>(listOfClauses), new ArrayList<LiteralH1>(listOfLiterals));
                if (listOfLiterals == null) {
                    return null;
                } else if (listOfLiterals != null) {
                    return listOfLiterals;
                }

            } else if (listOfLiterals != null) {
                return listOfLiterals;

            }
            if (listOfClauses.isEmpty()) {
                return listOfLiterals;
            } else if (checkEmptyClause(listOfClauses)) {
                return null;
            }
        }
        return listOfLiterals;
    }

    /**
     * @return returns true if there is an empty clause in the list. Returns
     * false if no empty list has been found.
     */
    private boolean checkEmptyClause(ArrayList<ClauseH1> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRules().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return Returns an ArrayList with all the statements after checking if
     * there is a unit clause
     */
    private ArrayList<LiteralH1> checkUnitClause(ArrayList<LiteralH1> list, ArrayList<ClauseH1> clauses) {
        int variable = 0;

        for (int i = 0; i < clauses.size(); i++) {
            if (clauses.get(i).getRules().size() == 1) {
                variable = clauses.get(i).getRules().get(0);
                //int variable komt maar 1 keer voor
                for (int j = 0; j < list.size(); j++) {
                    if ((list.get(j).getName() == variable) || (list.get(j).getName() == variable * -1)) {

                        if (variable > 0) {
                            list.get(j).setValue(1);
                            if (list.get(j).getName() == 716) {
                            }
                            removeClause(variable, clauses);
                            i = 0;
                        } else {
                            list.get(j).setValue(-1);
                            removeClause(variable, clauses);
                            i = 0;
                        }

                    }

                }
            }
        }
        if (list.size() == 0) {
            return list;
        } else {
            return null;
        }
    }

    private ArrayList<LiteralH1> checkPureLiteral(ArrayList<ClauseH1> listOfClauses, ArrayList<LiteralH1> listOfLiterals) {
        for (int i = 0; i < listOfLiterals.size(); i++) {
            if (listOfLiterals.get(i).getValue() == 0) {
                if (checkNumber(listOfLiterals.get(i).getName(), listOfClauses) == 1) {
                    listOfLiterals.get(i).setValue(1);
                    removeClause(listOfLiterals.get(i).getName(), listOfClauses);
                } else if (checkNumber(listOfLiterals.get(i).getName(), listOfClauses) == -1) {
                    listOfLiterals.get(i).setValue(-1);
                    removeClause(listOfLiterals.get(i).getName() * -1, listOfClauses);
                } else {
                }
            }
        }
        return null;
    }

    private int getNextUnknownLiteral(ArrayList<LiteralH1> listOfLiterals) {
        try {
            for (int i = 0; i < listOfLiterals.size(); i++) {
                if (listOfLiterals.get(i).getValue() == 0) {
                    return i;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            return -1;
        }
        return -1;
    }

    private int checkNumber(int number, ArrayList<ClauseH1> listOfCLauses) {
        int positive = 0;
        int negative = 0;
        for (int i = 0; i < listOfCLauses.size(); i++) {
            if (listOfCLauses.get(i).checkRuleNegative(number)) {
                negative++;
            }
            if (listOfCLauses.get(i).checkRulePositive(number)) {
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
    
    public void printAmount(){
        System.out.println("Amount of Choices: " + choicesAmount);
        System.out.println("Wrong coices: " + wrongChoices);
    }

    /**
     * Removes a literal from a clause if the negation from that literal is
     * found. Removes a clause from the list when the clause contains that
     * literal
     *
     * @param number is the literal which we want to remove.
     */
    private void removeClause(int number, ArrayList<ClauseH1> clauses) {

        if (number > 0) {
            for (int i = 0; i < clauses.size(); i++) {
                if (clauses.get(i).checkRulePositive(number)) {
                    clauses.remove(i);
                    i--;
                } else if (clauses.get(i).checkRuleNegative(number)) {
                    clauses.get(i).removeNumber(number * -1);
                    i--;
                }
            }
        } else if (number < 0) {
            for (int i = 0; i < clauses.size(); i++) {
                if (clauses.get(i).checkRuleNegative(number * -1)) {
                    clauses.remove(i);
                    i--;

                } else if (clauses.get(i).checkRulePositive(number * -1)) {
                    clauses.get(i).removeNumber(number * -1);
                    i--;
                }
            }
        }

    }

}
