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

    protected final int playersNum;
    protected final int[] order = new int[size];

    /**
     * constructor of the class Deck
     * @param playersNum the number of players in the game
     */
    public Deck(int playersNum){
        for(int i=1;i<=size;i++){
            order[i-1]=i;
        }
        shuffle();
        this.playersNum=playersNum;
    }

    public Card drawOne(){
        return null;// this method will be implemented in the subclasses
    }

    /**
     * This method shuffles the order[] array
     */
    public void shuffle(){
        for(int i=0;i<size;i++){
            int random = (int) (Math.random() * size);
            int temp = order[i];
            order[i] = order[random];
            order[random] = temp;
        }
    }





}

