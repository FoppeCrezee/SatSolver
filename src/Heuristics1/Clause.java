/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Heuristics1;

import java.util.ArrayList;

/**
 * This is a clause
 *
 * @author foppe
 */
public class Clause implements Cloneable {

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
            }
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Clause clone = null;
        try {
            clone = (Clause) super.clone();

            //Copy new date object to cloned method
            clone.setRules((ArrayList<Integer>) this.getRules().clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }

    public boolean checkTaut() {
        for (int i = 0; i < statements.size(); i++) {
            int getal = statements.get(i);
            if (statements.size() != 0) {
                for (int j = 0; i < statements.size(); i++) {
                    if (statements.get(j) == getal * -1) {
                        System.out.println("TAUt");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void setRules(ArrayList<Integer> list){
        statements = list;
    }
    
    
    public boolean getTrue() {
        return isTrue;
    }

    public ArrayList<Integer> getRules() {
        return statements;
    }
    
    public int getSize(){
        return statements.size();
    }
    
    public boolean containsLiteral(int variable){
        for(Integer number : statements){
            if(number == variable){
                return true;
            }
        }
        return false;
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
