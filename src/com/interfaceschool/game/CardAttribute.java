/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaceschool.game;

/**
 *
 * @author morrow
 */
public class CardAttribute implements Comparable<CardAttribute> {
    private int value;
    private String text;
    
    protected CardAttribute(int value, String text) {
        this.value = value;
        this.text = text;
    }
    
    @Override
    public boolean equals(Object obj) {
        CardAttribute attr = (CardAttribute)obj;
        return value == attr.value;
    }

    @Override
    public int compareTo(CardAttribute attr) {
        if (getValue() < attr.getValue()) return -1;
        if (getValue() > attr.getValue()) return 1;
        return 0;
    }

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }
    
    public String toString() {
        return text;
    }
}