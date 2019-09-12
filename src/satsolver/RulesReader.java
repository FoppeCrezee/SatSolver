/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author foppe
 */
public class RulesReader {

    private ArrayList<Rule> list;
    private ArrayList<Statement> statements;
    private Random r = new Random();

    public ArrayList<Statement> dp(ArrayList<Rule> list, ArrayList<Statement> statements) {
        this.statements = statements;
        this.list = list;
        if (list.size() == 0) {
            return statements;
        } else {

            if (checkUnitClause() != null) {
                return statements;
            }
            if (checkPureLiteral() != null) {
                return statements;
            }

            if (list.size() != 0) {
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

    private boolean pickRandom(boolean random) {
        int next = getNextEmptyStatement();
        if (next == -1) {
            return false;
        }
        if (random) {
            statements.get(next).setValue(1);
            //System.out.println(statements.get(next).getName() + " wordt " + 1 + " want Random");
            removeClause(statements.get(next).getName());
            if (list.size() == 0) {
                return true;
            } else {
                dp(list, statements);
            }
            if (checkEmptyClause()) {
                return false;
            }
        } else {
            statements.get(next).setValue(-1);
            removeClause(statements.get(next).getName() * -1);
            //System.out.println(statements.get(next).getName() + " wordt " + -1 + " want Random");
            if (list.size() == 0) {
                return true;
            } else {
                dp(list, statements);
            }
            if (checkEmptyClause()) {
                return false;
            }
        }
        return true;
    }

    private boolean checkEmptyClause() {
        checkUnitClause();
        checkPureLiteral();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRules().size() == 0) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<Statement> checkUnitClause() {
        //System.out.println("test");
        int variable = 0;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getRules().size() == 1) {
                variable = list.get(i).getRules().get(0);
                //int variable komt maar 1 keer voor
                for (int j = 0; j < statements.size(); j++) {
                    if ((statements.get(j).getName() == variable) || (statements.get(j).getName() == variable * -1)) {

                        if (variable > 0) {
                            statements.get(j).setValue(1);
                            //System.out.println(statements.get(j).getName() + " wordt " + 1 + " want UnitClause");
                            removeClause(variable);
                            if (dp(list, statements) != null) {
                                return statements;
                            }
                        } else {
                            statements.get(j).setValue(-1);
                            //System.out.println(statements.get(j).getName() + " wordt " + -1 + " want UnitClause");
                            removeClause(variable);
                            if (dp(list, statements) != null) {
                                return statements;
                            }
                        }

                    }

                }
                //System.out.println(variable);
            }
        }

        if (list.size() == 0) {
            return statements;
        }
        return null;
    }

    private ArrayList<Statement> checkPureLiteral() {
        for (int i = 0; i < statements.size(); i++) {
            if (statements.get(i).getValue() == 0) {
                if (checkNumber(statements.get(i).getName()) == 1) {
                    //System.out.println(statements.get(i).getName() + " is alleen maar positief");
                    statements.get(i).setValue(1);
                    //System.out.println(statements.get(i).getName() + " wordt " + 1 + " want puur");
                    removeClause(statements.get(i).getName());
                    if (dp(list, statements) != null) {
                        return statements;
                    }
                } else if (checkNumber(statements.get(i).getName()) == -1) {
                    //System.out.println(statements.get(i).getName() + " is alleen maar negatief");
                    statements.get(i).setValue(-1);
                    //System.out.println(statements.get(i).getName() + " wordt " + -1 + " want puur");
                    removeClause(statements.get(i).getName() * -1);
                    if (dp(list, statements) != null) {
                        return statements;
                    }
                } else {
                    //System.out.println(statements.get(i).getName() + " We weten het nog niet");
                }
            }
        }
        if (list.size() == 0) {
            dp(list, statements);
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
            //System.out.println("Zie je wel");
            return -1;
        }
        return -1;
    }

    private int checkNumber(int number) {
        int positive = 0;
        int negative = 0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).checkRuleNegative(number)) {
                negative++;
            }
            if (list.get(i).checkRulePositive(number)) {
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
            // System.out.println(number);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).checkRulePositive(number)) {
                    list.remove(i);
                    removeClause(number);
                    //System.out.println("Deze clause gaat weg " + number);
                } else if (list.get(i).checkRuleNegative(number)) {
                    list.get(i).removeNumber(number * -1);
                    //System.out.println("Deze gaat weg " + number);
                }
            }
        } else if (number < 0) {
            //  System.out.println(number + "jahf;aug" );
            for (int i = 0; i < list.size(); i++) {
                //System.out.println(number);
                if (list.get(i).checkRulePositive(number)) {
                    list.remove(i);
                    removeClause(number);
                    //System.out.println("Deze clause gaat weg " + number);
                } else if (list.get(i).checkRulePositive(number * -1)) {
                    list.get(i).removeNumber(number * -1);
                    //System.out.println("Deze gaat weg " + number);
                }
            }
        }

    }

}
