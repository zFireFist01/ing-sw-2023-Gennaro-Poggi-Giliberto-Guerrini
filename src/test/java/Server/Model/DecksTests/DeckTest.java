package Server.Model.DecksTests;
import Server.Model.Cards.Card;
import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.CommonGoalCards.CommonGoalCard1;
import Server.Model.Decks.CommonGoalCardsDeck;
import Server.Model.Decks.Deck;
import Server.Model.Decks.PersonalGoalCardsDeck;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import javax.swing.plaf.basic.BasicComboBoxUI;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Deck Methods Tester.
 */
public class DeckTest {


    /**
     * Method: drawOne() test in CommonGoalCardsDeck
     */
    @Test
    public void CommonGoalCardsDeckTest() {


        Deck deck = new CommonGoalCardsDeck(4);
        //test with 4 players
        boolean IDs[] = {false, false, false, false, false, false, false, false, false, false, false, false};
        deck = new CommonGoalCardsDeck(4);
        for (int i = 0; i < 12; i++) {
            IDs[deck.drawOne().getCardID() - 1] = true;
        }
        assertArrayEquals(IDs, new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true});
        //test with 3 players
        IDs = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false};
        deck = new CommonGoalCardsDeck(3);
        for (int i = 0; i < 12; i++) {
            IDs[deck.drawOne().getCardID() - 1] = true;
        }
        assertArrayEquals(IDs, new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true});
        //test with 2 players
        IDs = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false};
        deck = new CommonGoalCardsDeck(2);
        for (int i = 0; i < 12; i++) {
            IDs[deck.drawOne().getCardID() - 1] = true;
        }
        assertArrayEquals(IDs, new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true});


    }

    /**
     * Method: drawOne() test in personalCardsDeck
     */
    @Test
    public void PersonalGoalCardsDeckTest(){
        Deck deck = new PersonalGoalCardsDeck(4);
        //test with 4 players
        boolean IDs[] = {false, false, false, false, false, false, false, false, false, false, false, false};
        deck = new PersonalGoalCardsDeck(4);
        for (int i = 0; i < 12; i++) {
            IDs[deck.drawOne().getCardID() - 1] = true;
        }
        assertArrayEquals(IDs, new boolean[]{true, true, true, true, true, true, true, true, true, true, true, true});


    }
}