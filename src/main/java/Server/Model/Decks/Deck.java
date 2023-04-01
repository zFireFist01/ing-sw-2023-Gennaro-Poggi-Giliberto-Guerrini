package Server.Model.Decks;

import Server.Model.Cards.Card;

import java.util.List;

public abstract class Deck {
    private int size;
    private List<Card> cards;

    public Card drawOne(){
        throw new RuntimeException("not implemented method");
    }
    public void shuffle() throws UnsupportedOperationException{}

}

