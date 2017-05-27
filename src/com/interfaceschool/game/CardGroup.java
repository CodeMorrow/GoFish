/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaceschool.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author morrow
 */
public class CardGroup {
    private List<Card> cards = new ArrayList<>();
    
    public void addCard(Card card) {
        cards.add(card);
    }
    
    public Card removeLastCard() {
        if (cards.size() > 0) {
            return cards.remove(cards.size()-1);
        }
        return null;
    }
    
    // Go Fish Specific....
    public CardGroup removeCards(Rank rank) {
        CardGroup matchedCards = new CardGroup();
        Iterator<Card> cardIterator = cards.iterator();
        while (cardIterator.hasNext()) {
            Card card = cardIterator.next();
            if (rank.equals(card.getRank())) {
                cardIterator.remove();
                matchedCards.addCard(card);
            }
        }
        return matchedCards;
    }
    
    public void shuffleCards() {
        Random generator = new Random(System.currentTimeMillis());
        for (int i=0; i<cards.size(); i++) {
            int j = generator.nextInt(cards.size());
            Card iCard = cards.get(i);
            Card jCard = cards.get(j);
            cards.set(i, jCard);
            cards.set(j, iCard);
        }
    }
    
    /**
     * @return the cards
     */
    public List<Card> getCards() {
        return cards;
    }

    /**
     * @param cards the cards to set
     */
    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
    
    @Override
    public String toString() {
        StringBuilder deckText = new StringBuilder(200);
        for (Card card:cards) {
           String cardText = card.toString();
           deckText.append(cardText).append("\n");
        }
        return deckText.toString();
    }
}
