package Server.Model.Decks;
import Server.Model.Cards.Card;
import Server.Model.Cards.CommonGoalCards.CommonGoalCard1;
import Server.Model.Decks.Deck;
import Server.Model.Decks.PersonalGoalCardsDeck;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DeckTest {
    @Test
    void cardsSwitchWork(){
        Deck d = new PersonalGoalCardsDeck();

        ArrayList<Card> emma = new ArrayList<>();

        CommonGoalCard1 c1 = new CommonGoalCard1();
        CommonGoalCard1 c2 = new CommonGoalCard1();

        emma.add(c1);
        emma.add(c2);

        ArrayList<Card> emmaSwitched = new ArrayList<>();
        emmaSwitched.add(c2);
        emmaSwitched.add(c1);
        d.cardsSwitch(0,1);

        assertEquals(emmaSwitched.get(0),emma.get(0));
        assertEquals(emmaSwitched.get(1),emma.get(1));


    }
}
