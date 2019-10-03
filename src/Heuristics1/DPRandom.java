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
    private int heuristics;
//    private int h = 1;

    public DPRandom(int heuristics) {
        this.heuristics = heuristics;
    }

    /**
     * Is the main algorithm which consist of checking if there is a unit
     * clause, a pure literal or making a split.
     *
     * @param listOfClauses
     * @param listOfLiterals
     *
     * @return This returns an ArrayList with all the statements
     * @throws java.lang.CloneNotSupportedException
     */
    public ArrayList<Literal> dp(ArrayList<Clause> listOfClauses, ArrayList<Literal> listOfLiterals) throws CloneNotSupportedException {

        if (listOfClauses.isEmpty()) {
            return listOfLiterals;
        } else if (checkEmptyClause(listOfClauses)) {
            return null;
        }

        //copy of list
        ArrayList<Clause> backupC = new ArrayList<>();

        Iterator<Clause> iterator = listOfClauses.iterator();
        while (iterator.hasNext()) {
            backupC.add((Clause) iterator.next().clone());
        }
        //copy of list
        ArrayList<Literal> backupL = new ArrayList<Literal>();

        Iterator<Literal> iterator2 = listOfLiterals.iterator();
        while (iterator2.hasNext()) {
            backupL.add((Literal) iterator2.next().clone());
        }

        checkUnitClause(listOfLiterals, listOfClauses);
//            Don't know if we need this very timeconsuming
//            checkPureLiteral(listOfClauses, listOfLiterals);
        if (!listOfClauses.isEmpty()) {
            int next = -1;
            boolean random = r.nextBoolean();
            if (heuristics == 0) {
                random = r.nextBoolean();
                next = getNextUnknownLiteral(listOfLiterals);
            } else if (heuristics == 1) {
//                if (listOfClauses.size() > 1500) {
//                    System.out.println("More then 1500");
                next = MOM(listOfLiterals, listOfClauses);
                if (next > 0) {
                    random = true;
                    next = this.getIndex(listOfLiterals, next);
                } else {
                    random = false;
                    next = this.getIndex(listOfLiterals, next);
                }
//                } else {
//                    System.out.println("Less then 1500");
//                    random = r.nextBoolean();
//                    next = getNextUnknownLiteral(listOfLiterals);
//                }

            }
            if (next == -1) {
                return null;
            }
            choicesAmount++;
            if (random) {
                listOfLiterals.get(next).setValue(1);
//                System.out.println(listOfLiterals.get(next).getName() + " : " + 1);
                removeClause(listOfLiterals.get(next).getName(), listOfClauses);
            } else {
                listOfLiterals.get(next).setValue(-1);
//                System.out.println(listOfLiterals.get(next).getName() + " : " + -1);
                removeClause(listOfLiterals.get(next).getName() * -1, listOfClauses);
            }

            listOfLiterals = this.dp(new ArrayList<Clause>(listOfClauses), new ArrayList<Literal>(listOfLiterals));
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

                listOfLiterals = this.dp(new ArrayList<Clause>(listOfClauses), new ArrayList<Literal>(listOfLiterals));
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
    private boolean checkEmptyClause(ArrayList<Clause> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRules().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private int getIndex(ArrayList<Literal> listOfLiterals, int next) {
        if (next > 0) {
            for (int i = 0; i < listOfLiterals.size(); i++) {
                if (listOfLiterals.get(i).getName() == next) {
                    return i;
                }
            }
        } else {
            next = next * -1;
            for (int i = 0; i < listOfLiterals.size(); i++) {
                if (listOfLiterals.get(i).getName() == next) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * @return Returns an ArrayList with all the statements after checking if
     * there is a unit clause
     */
    private ArrayList<Literal> checkUnitClause(ArrayList<Literal> list, ArrayList<Clause> clauses) {
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

    /**
     * @deprecated
     */
    private ArrayList<Literal> checkPureLiteral(ArrayList<Clause> listOfClauses, ArrayList<Literal> listOfLiterals) {
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

    /**
     * Gives you the next Literal in the list which has no value
     *
     * @param listOfLiterals is the list with all the literals
     * @return the index of the literal in the array. returns -1 if no unknown
     * literal is found
     */
    private int getNextUnknownLiteral(ArrayList<Literal> listOfLiterals) {
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

    private int checkNumber(int number, ArrayList<Clause> listOfCLauses) {
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

    /**
     * prints the amount of choices made in the algorithm
     */
    public void printAmount() {
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
    private void removeClause(int number, ArrayList<Clause> clauses) {

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

    private ArrayList<Integer> getAllUnknownLiterals(ArrayList<Literal> listOfLiterals) {
        ArrayList<Integer> unknownLiterals = new ArrayList<>();
        for (Literal literal : listOfLiterals) {
            if (literal.getValue() == 0) {
                unknownLiterals.add(literal.getName());
//                unknownLiterals.add(literal.getName() * -1);
            }
        }
        return unknownLiterals;
    }

    private int getMinimumClauseSize(ArrayList<Clause> listOfClauses) {
        int minimum = 1000;
        for (Clause clause : listOfClauses) {
            if (clause.getSize() < minimum) {
                minimum = clause.getSize();
            }
        }
        if (minimum == 1000) {
            System.out.println("STOP");
            return -1;
        }else{
            return minimum;
        }
    }

    private int MOM(ArrayList<Literal> listOfLiterals, ArrayList<Clause> listOfClauses) {
        ArrayList<Integer> list = getAllUnknownLiterals(listOfLiterals);
        double highestRatio = 0;
        int highestLiteral = -1;
        int minimum = this.getMinimumClauseSize(listOfClauses);

//        if(this.getNextUnknownLiteral(listOfLiterals) == -1){
//            return highestLiteral;
//        }
        for (Integer variable : list) {
            int countPos = 0;
            int countNeg = 0;
            for (Clause clause : listOfClauses) {
                if (clause.containsLiteral(variable) && clause.getSize() == minimum) {
                    countPos++;
                } else if (clause.containsLiteral(variable * -1) && clause.getSize() == minimum) {
                    countNeg++;
                }
            }
            double ratio = (countPos + countNeg) * Math.pow(2, 2) + countPos * countNeg;
//            System.out.println(variable + " : " + count);
            if (ratio >= highestRatio) {
                highestRatio = ratio;
                if (countPos >= countNeg) {
                    highestLiteral = variable;
                } else {
                    highestLiteral = variable * -1;
                }
            }
        }
        System.out.println(highestLiteral + " : " + highestRatio);
        return highestLiteral;
    }
}
