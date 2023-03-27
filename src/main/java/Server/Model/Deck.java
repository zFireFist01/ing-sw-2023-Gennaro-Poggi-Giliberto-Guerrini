package Server.Model;

import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public abstract class Deck {
    private int size;
    private List<Card> cards;

    public Card drawOne(){
        throw new RuntimeException("not implemented method");
    }
    public void shuffle() throws UnsupportedOperationException{}

}

