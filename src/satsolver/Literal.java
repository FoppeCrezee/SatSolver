/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package satsolver;

/**
 * this is a literal
 * @author foppe
 */
public class Literal {

    private int name;
    private int value = 0;

    public Literal(int name) {
        this.name = name;
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
