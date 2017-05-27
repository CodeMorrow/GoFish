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
public class StandardDeck extends CardGroup {
    public static final String[] SUITS = {"\u2663", "\u2666", "\u2764", "\u2660"}; // Club, Diamond, Heart, Spade
    public static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    
    public StandardDeck() {
        for (int suitValue=0; suitValue < 4; suitValue++) {
            Suit suit = new Suit(suitValue, SUITS[suitValue]);
            for (int rankValue=0; rankValue < 13; rankValue++) {
                Rank rank = new Rank(rankValue+2, RANKS[rankValue]);
                Card card = new Card(rank, suit);
                addCard(card);
            }
        }
    }
}
