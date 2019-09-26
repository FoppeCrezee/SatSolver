/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Heuristics1;

/**
 * this is a literal
 *
 * @author foppe
 */
public class LiteralH1 implements Cloneable {

    private int name;
    private int value = 0;

    public LiteralH1(int name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        LiteralH1 clone = null;
        try {
            clone = (LiteralH1) super.clone();

            //Copy new date object to cloned method
//            clone.setValue((int) this.getValue().clone());
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}
