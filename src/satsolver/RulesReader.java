/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Is the main Algorithm
 *
 * @author foppe
 */
public class RulesReader {

//    private ArrayList<Clause> listOfClauses;
//    private ArrayList<Literal> listOFLiterals;
//    private ArrayList<Clause> startingClauses;
//    private ArrayList<Literal> startingLiterals;
    private Random r = new Random();

    /**
     * @param list is the list with all the different clauses
     * @param statements is the list with all the variables (literals)
     */
    public RulesReader(/*ArrayList<Clause> list, ArrayList<Literal> statements, ArrayList<Clause> startingList, ArrayList<Literal> startingStatements*/) {
//        this.listOFLiterals = statements;
//        this.listOfClauses = list;
//        this.startingClauses = startingList;
//        this.startingLiterals = startingStatements;
    }

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
     */
    public ArrayList<Literal> dp(ArrayList<Clause> listOfClauses, ArrayList<Literal> listOfLiterals, ArrayList<Clause> startingList, ArrayList<Literal> startingStatements) throws CloneNotSupportedException {

        ArrayList<Clause> backupC = new ArrayList<>();

        Iterator<Clause> iterator = listOfClauses.iterator();
        while (iterator.hasNext()) {
            backupC.add((Clause) iterator.next().clone());
        }

        ArrayList<Literal> backupL = new ArrayList<Literal>();

        Iterator<Literal> iterator2 = listOfLiterals.iterator();
        while (iterator2.hasNext()) {
            backupL.add((Literal) iterator2.next().clone());
        }

//        listOfClauses.remove(0);
//        if (checkEmptyClause(listOfClauses)) {
//            System.out.println("stop");
//            return null;
//        }
        System.out.println("DP");
        if (listOfClauses.isEmpty()) {
            System.out.println("Done");
            return listOfLiterals;
        } else if (checkEmptyClause(listOfClauses)) {
            System.out.println("stop2");
            return null;
        } else {
            checkUnitClause(listOfLiterals, listOfClauses);
//            System.out.println("hiero");
//            Don't know if we need this very timeconsuming
//            checkPureLiteral(listOfClauses, listOfLiterals);
            if (!listOfClauses.isEmpty()) {
                int next = getNextUnknownLiteral(listOfLiterals);
                if (next == -1) {
                    System.out.println("exit");
                    return null;
                }
                boolean random = r.nextBoolean();
//                random = false;
                System.out.println("Choise");
                if (random) {
                    listOfLiterals.get(next).setValue(1);
                    System.out.println(listOfLiterals.get(next).getName() + " = 1");
                    removeClause(listOfLiterals.get(next).getName(), listOfClauses);
                } else {
                    listOfLiterals.get(next).setValue(-1);
                    System.out.println(listOfLiterals.get(next).getName() + " = -1");
                    removeClause(listOfLiterals.get(next).getName() * -1, listOfClauses);
                }

                System.out.println("Lijst is " + listOfClauses.size());
                listOfLiterals = this.dp(new ArrayList<Clause>(listOfClauses), new ArrayList<Literal>(listOfLiterals), new ArrayList<Clause>(listOfClauses), new ArrayList<Literal>(listOfLiterals));
                if (listOfLiterals == null) {

                    if (listOfClauses.equals(backupC)) {
                        System.out.println("Not Good");
                    }
                    listOfClauses = new ArrayList(backupC);
                    listOfLiterals = new ArrayList(backupL);

                    if (!random) {
                        listOfLiterals.get(next).setValue(1);
                        System.out.println(listOfLiterals.get(next).getName() + " = 1");
                        removeClause(listOfLiterals.get(next).getName(), listOfClauses);
                    } else {
                        listOfLiterals.get(next).setValue(-1);
                        System.out.println(listOfLiterals.get(next).getName() + " = -1");
                        removeClause(listOfLiterals.get(next).getName() * -1, listOfClauses);
                    }
                    System.out.println("Lijst is " + listOfClauses.size());

//                    return this.dp(listOfClauses, listOfLiterals, new ArrayList<Clause>(listOfClauses), new ArrayList<Literal>(listOfLiterals));
                    listOfLiterals = this.dp(new ArrayList<Clause>(listOfClauses), new ArrayList<Literal>(listOfLiterals), new ArrayList<Clause>(listOfClauses), new ArrayList<Literal>(listOfLiterals));
                    if (listOfLiterals == null) {
                        System.out.println("double exit");
                        return null;
                    } else if (listOfLiterals != null) {
                        return listOfLiterals;
//                        this.dp(listOfClauses, listOfLiterals, new ArrayList<Clause>(listOfClauses), new ArrayList<Literal>(listOfLiterals));
                    }

                } else if (listOfLiterals != null) {
                    return listOfLiterals;
//                    if (!listOfClauses.isEmpty()) {
//                        listOfLiterals = this.dp(listOfClauses, listOfLiterals, new ArrayList<Clause>(listOfClauses), new ArrayList<Literal>(listOfLiterals));
//                    } else {
//                        return listOfLiterals;
//                    }

                }
                if (listOfClauses.isEmpty()) {
                    System.out.println("Done");
                    return listOfLiterals;
                } else if (checkEmptyClause(listOfClauses)) {
                    System.out.println("stop2");
                    return null;
                }
//                if (checkEmptyClause(listOfClauses)) {
//                    System.out.println("stop");
//                    return null;
//                }

//                }
//
//                boolean split = this.pickRandom(random, next);
//                if (!split) {
//
//                    listOfClauses = new ArrayList(startingClauses);
//                    listOFLiterals = new ArrayList(startingLiterals);
//
//                    checkUnitClause();
//                    pickRandom(!random, next);
//                }
//                if (!pickRandom(random, next)) {
//
//                    //listOfClauses = listOfClauses2;
//                    //mistake was made, so we need to reset all the data.
//                    listOfClauses = new ArrayList(startingClauses);
//                    listOFLiterals = new ArrayList(startingLiterals);
//
//                    checkUnitClause();
//                    pickRandom(!random, next);
//
////                    RulesReader iteration = new RulesReader(listOfClauses, listOFLiterals);
////                    if (iteration.dp() != null || this.checkEmptyClause() == true) {
////                        System.out.println("Foutje");
////                        return listOFLiterals;
////                    }
            }
        }

//        if (!listOfClauses.isEmpty()) {
//            //RulesReader iteration = new RulesReader(listOfClauses, listOFLiterals, new ArrayList<Clause>(listOfClauses), new ArrayList<Literal>(listOFLiterals));
//            System.out.println("Teste");
//            this.dp(listOfClauses, listOfLiterals, new ArrayList<Clause>(listOfClauses), new ArrayList<Literal>(listOfLiterals));
//        }
        System.out.println("test");
        return listOfLiterals;
    }

    /**
     * @param random is the random boolean value which the next empty variable
     * will be made
     * @return false if there is an empty clause, which means there was made a
     * mistake. It returns true if no empty clauses were found.
     */
//    private boolean pickRandom(boolean random, int next) {
//        //if no new variable is found return false
//        if (next == -1) {
//            System.out.println("exit2");
//            return false;
//        }
//        //if the boolean is true
//        if (random) {
//            listOFLiterals.get(next).setValue(1);
//            removeClause(listOFLiterals.get(next).getName(), listOfClauses);
//            if (listOfClauses.isEmpty()) {
//                return true;
//            } else {
//                if (checkEmptyClause(listOfClauses)) {
//                    System.out.println("stop");
//                    return false;
//                }
//                if (!listOfClauses.isEmpty()) {
//                    //starts a new iteration of recursion.
//                    System.out.println("start");
////                    RulesReader iteration = new RulesReader(listOfClauses, listOFLiterals, new ArrayList<Clause>(listOfClauses), new ArrayList<Literal>(listOFLiterals));
////                    listOFLiterals = iteration.dp();
//                }
//                if (checkEmptyClause(listOfClauses)) {
//                    System.out.println("stop");
//                    return false;
//                }
//            }
//
//            //if the boolean is false
//        } else {
//            listOFLiterals.get(next).setValue(-1);
//            removeClause(listOFLiterals.get(next).getName() * -1, listOfClauses);
//            if (listOfClauses.isEmpty()) {
//                return true;
//            } else {
//                if (checkEmptyClause(listOfClauses)) {
//                    System.out.println("stop");
//                    return false;
//                }
//                if (!listOfClauses.isEmpty()) {
//                    //starts a new iteration of recursion.
//                    System.out.println("start");
////                    RulesReader iteration = new RulesReader(listOfClauses, listOFLiterals, new ArrayList<>(listOfClauses), new ArrayList<>(listOFLiterals));
////                    listOFLiterals = iteration.dp();
//                }
//                if (checkEmptyClause(listOfClauses)) {
//                    System.out.println("stop");
//                    return false;
//                }
//            }
//
//        }
//        return true;
//    }
    /**
     * @return returns true if there is an empty clause in the list. Returns
     * false if no empty list has been found.
     */
    private boolean checkEmptyClause(ArrayList<Clause> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRules().isEmpty()) {
//                System.out.println("Empty!");
                return true;
            }
        }
//        System.out.println("not Empty");
        return false;
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
//                            System.out.println("Set " + variable + " 1");
                            list.get(j).setValue(1);
                            if (list.get(j).getName() == 716) {
                                System.out.println(list.get(j).getName());
                            }
                            removeClause(variable, clauses);
                            i = 0;
                        } else {
//                            System.out.println("Set " + variable + " -1");
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

    private ArrayList<Literal> checkPureLiteral(ArrayList<Clause> listOfClauses, ArrayList<Literal> listOfLiterals) {
        for (int i = 0; i < listOfLiterals.size(); i++) {
            if (listOfLiterals.get(i).getValue() == 0) {
                if (checkNumber(listOfLiterals.get(i).getName(), listOfClauses) == 1) {
                    listOfLiterals.get(i).setValue(1);
                    removeClause(listOfLiterals.get(i).getName(), listOfClauses);
                    System.out.println("Een pure");
                } else if (checkNumber(listOfLiterals.get(i).getName(), listOfClauses) == -1) {
                    listOfLiterals.get(i).setValue(-1);
                    removeClause(listOfLiterals.get(i).getName() * -1, listOfClauses);
                    System.out.println("Een pure");
                } else {
                }
            }
        }
        return null;
    }

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
     * Removes a literal from a clause if the negation from that literal is
     * found. Removes a clause from the list when the clause contains that
     * literal
     *
     * @param number is the literal which we want to remove.
     */
    private void removeClause(int number, ArrayList<Clause> clauses) {
//         System.out.println("verwijder: " + number);

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
