/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaceschool.game;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author morrow
 */
public class Player implements Comparable<Player> {
    private String name;
    private CardGroup hand = new CardGroup();
    private List<CardGroup> sets = new LinkedList<>();
    
    public Player(String name){
        this.name = name;
    }
    
    public void makeSet(CardGroup group) {
        sets.add(group);
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the hand
     */
    public CardGroup getHand() {
        return hand;
    }

    /**
     * @param hand the hand to set
     */
    public void setHand(CardGroup hand) {
        this.hand = hand;
    }

    /**
     * @return the sets
     */
    public List<CardGroup> getSets() {
        return sets;
    }

    /**
     * @param sets the sets to set
     */
    public void setSets(List<CardGroup> sets) {
        this.sets = sets;
    }

    @Override
    public int compareTo(Player otherPlayer) {
        if (sets.size() < otherPlayer.sets.size()) return 1;
        if (sets.size() > otherPlayer.sets.size()) return -1;
        return 0;
    }
}
