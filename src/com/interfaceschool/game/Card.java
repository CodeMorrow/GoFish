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
public class Card implements Comparable<Card> {
    private Rank rank;
    private Suit suit;
    
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public int compareTo(Card card) {
        int compareValue = getSuit().compareTo(card.getSuit());
        if (compareValue == 0) {
            compareValue = getRank().compareTo(card.getRank()); 
        }
        return compareValue;
    }

    /**
     * @return the rank
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * @param rank the rank to set
     */
    public void setRank(Rank rank) {
        this.rank = rank;
    }

    /**
     * @return the suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * @param suit the suit to set
     */
    public void setSuit(Suit suit) {
        this.suit = suit;
    }
    
    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }
}
