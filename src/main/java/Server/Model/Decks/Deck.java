package Server.Model.Decks;

import Server.Model.Cards.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Deck class to store the order of the class
 * @author Due2
 */

public abstract class Deck {
    static final int size=12;
    private List<Card> cards;

    public Card drawOne(){
        throw new RuntimeException("not implemented method");
    }
    public void shuffle() throws UnsupportedOperationException{



    }

    /**
     * switch the card in position i with the card in position j and viceversa
     * @param i index of the first card
     * @param j index of the second card
     */
    public void cardsSwitch(int i,int j){
        Card tmp;
        tmp= cards.get(i);
        cards.set(i,cards.get(j));
        cards.set(j,tmp);

    }


}

