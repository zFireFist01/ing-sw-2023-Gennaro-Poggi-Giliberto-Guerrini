package Server.Model;

public abstract class Deck {
    private int size;
    private List<Card> cards;

    public Card drawOne(){}
    public void shuffle() throws UnsupportedOperationException{}

}

public class CommongGoalCardsDeck extends Deck{}
public class PersonalGoalCardsDeck extends Deck{}
