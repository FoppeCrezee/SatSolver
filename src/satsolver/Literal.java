/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

/**
 * this is a literal
 *
 * @author foppe
 */
public class Literal implements Cloneable {

    private int name;
    private int value = 0;

    public Literal(int name) {
        this.name = name;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Literal clone = null;
        try {
            clone = (Literal) super.clone();

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
