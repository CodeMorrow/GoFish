/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interfaceschool.game.gofish;

import com.interfaceschool.game.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 *
 * @author morrow
 */
public class GoFish {

    protected List<Player> players = new LinkedList<>();
    protected StandardDeck deck;
    BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        GoFish game = new GoFish();
        String[] playerNames = {"Kent", "Dan", "David", "John"};
        game.setup(playerNames);
        for (Player player : game.players) {
            game.layDownSets(player);
        }
        int currentPlayerIndex = 0;
        Player currentPlayer;
        do {
            currentPlayer = game.players.get(currentPlayerIndex);
            game.takeTurn(currentPlayer);
            if (game.cardList(currentPlayer).size() > 0 || game.deck.getCards().size() > 0) {
                if (++currentPlayerIndex == game.players.size()) {
                    currentPlayerIndex = 0;
                }
            }
        } while (game.cardList(currentPlayer).size() > 0 && game.deck.getCards().size() > 0);
        if (game.cardList(currentPlayer).size() > 0) {
            game.calculateSetsForVictory();
        }
    }

    private void calculateSetsForVictory() {
        Player[] playerArray = new Player[players.size()];
        for (int i = 0; i < players.size(); i++) {
            playerArray[i] = players.get(i);
        }
        Arrays.sort(playerArray);
        for (Player player : playerArray) {
            if (player.getSets().size() == playerArray[0].getSets().size()) {
                // you win!
                System.out.println(player.getName() + " wins with " + playerArray[0].getSets().size() + " mathces!");
            }
        }
    }

    public void takeTurn(Player currentPlayer) throws IOException {
        cardList(currentPlayer).sort(new SortByRank());
        displaySets();
        promptTurn(currentPlayer);
        
        String[] playerRequest = console.readLine().split(" ");
        String nameText = playerRequest[0];
        String rankText = playerRequest[1];
        Player foundPlayer = findPlayer(nameText);
        
        if (isValidMove(playerRequest, foundPlayer, rankText, currentPlayer)) {
            int rankValue = findRank(rankText);
            Rank rank = new Rank(rankValue, rankText);
            List<Card> foundCards = foundPlayer.getHand().removeCards(rank).getCards();
            if (foundCards.size() > 0) {
                int numberOfCards = foundCards.size();
                String name = nameText.substring(0, 1).toUpperCase() + nameText.substring(1);
                String guessedRank = rankText.toUpperCase();
                if (numberOfCards == 1) {
                    System.out.println(name + " had " + numberOfCards + " " + guessedRank);
                } else {
                    System.out.println(name + " had " + numberOfCards + " " + guessedRank + "s");
                }
                foundCards.forEach((card) -> {
                    currentPlayer.getHand().addCard(card);
                });
                layDownSets(currentPlayer);
                if (cardList(currentPlayer).size() > 0) {
                    takeTurn(currentPlayer);
                } else {
                    System.out.println(currentPlayer.getName() + " wins by getting rid of all their cards!");
                }
            } else {
                // Go fish
                goFishForCard(currentPlayer, rankValue);
            }
        } else {
            System.out.println("Couldn't match to a valid format.\n Using valid opponent names and valid card ranks, please request in this format \"<name> <rank>\".");
            takeTurn(currentPlayer);
        }
    }
    
    private List<Card> cardList(Player player){
        return player.getHand().getCards();
    }
    
    private void promptTurn(Player currentPlayer){
        System.out.println(currentPlayer.getName() + "'s Turn");
        System.out.println(currentPlayer.getHand().toString());
        System.out.println("Enter player name and card rank:");
    }
    
    private boolean isValidMove(String[] playerRequest, Player foundPlayer, String rankText, Player currentPlayer){
        return (playerRequest.length == 2 
                && foundPlayer != null 
                && playerHasRankCard(rankText, currentPlayer) 
                && !currentPlayer.getName().equalsIgnoreCase(playerRequest[0]));
    }

    private void goFishForCard(Player player, int rankValue) throws IOException {
        System.out.println("Go fish!");
        Card card = deck.removeLastCard();
        if (card != null) {
            player.getHand().addCard(card);
            layDownSets(player);
            if (cardList(player).size() > 0) {
                if (rankValue == card.getRank().getValue()) {
                    System.out.println("You got lucky and drew a " + card + "!");
                    takeTurn(player);
                } else {
                    System.out.println("You drew a " + card);
                }
            } else {
                System.out.println("You Win!");
            }
        } else {
            System.out.println("There are no more cards to draw.");
        }
    }

    private void displaySets() {
        for (Player player : players) {
            if (player.getSets().size() == 1) {
                System.out.println(player.getName() + " has " + player.getSets().size() + " set");
            } else {
                System.out.println(player.getName() + " has " + player.getSets().size() + " sets");
            }
        }
    }

    private boolean playerHasRankCard(String rankText, Player player) {
        for (Card card : cardList(player)) {
            if (card.getRank().getText().equalsIgnoreCase(rankText)) {
                return true;
            }
        }
        return false;
    }

    private void layDownSets(Player player) {
        if (cardList(player).size() < 4) {
            return;
        }
        cardList(player).sort(new SortByRank());
        int numberOfCards = cardList(player).size();
        int consectutiveRankCount = 1;
        for (int i = 0; i < numberOfCards - 1; i++) {
            Card card = cardList(player).get(i);
            Card card2 = cardList(player).get(i + 1);
            if (card.getRank().getValue() == card2.getRank().getValue()) {
                consectutiveRankCount++;
                if (consectutiveRankCount == 4) {
                    CardGroup matchedCards = player.getHand().removeCards(card.getRank());
                    player.makeSet(matchedCards);
                    numberOfCards = cardList(player).size();
                    System.out.println("You just made a set!");
                }
            } else {
                consectutiveRankCount = 1;
            }
        }
    }

    private Player findPlayer(String name) {
        Player foundPlayer = null;
        for (Player player : players) {
            if (player.getName().equalsIgnoreCase(name)) {
                foundPlayer = player;
                break;
            }
        }
        return foundPlayer;
    }

    private int findRank(String rank) {
        int foundRank = -1;
        for (int i = 0; i < StandardDeck.RANKS.length; i++) {
            String rankName = StandardDeck.RANKS[i];
            if (rank.equalsIgnoreCase(rankName)) {
                foundRank = i + 2;
                break;
            }
        }
        return foundRank;
    }

    private void setup(String[] playerNames) {
        createPlayers(playerNames);
        deck = new StandardDeck();
        deck.shuffleCards();
        dealHands();
    }

    private void createPlayers(String[] playerNames) {
        for (String name : playerNames) {
            Player player = new Player(name);
            players.add(player);
        }
    }

    private void dealHands() {
        for (int cardCount = 0; cardCount < 7; cardCount++) {
            for (int playerNum = 0; playerNum < players.size(); playerNum++) {
                Card card = deck.removeLastCard();
                players.get(playerNum).getHand().addCard(card);
            }
        }
    }
}

class SortByRank implements Comparator<Card> {

    @Override
    public int compare(Card card1, Card card2) {
        if (card1.getRank().getValue() < card2.getRank().getValue()) {
            return -1;
        }
        if (card1.getRank().getValue() > card2.getRank().getValue()) {
            return 1;
        }
        return 0;
    }

}
