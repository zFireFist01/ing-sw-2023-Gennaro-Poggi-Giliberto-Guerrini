package Server.Model.Player;

import Server.Model.GameItems.Bookshelf;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    Player tester;

    @Before
    public void setUp() throws Exception {
        Bookshelf bookshelf = new Bookshelf();
        String nickName = "nickName";
        int playerID = 4;
        this.tester = new Player(playerID, bookshelf, nickName);
    }

    //maybe there is no need for this test
    @Test
    public void bookshelfNotNull(){
        assertNull(this.tester.getBookshelf());
    }

    //maybe there is no need for this test
    @Test
    public void personalGoalCardNotNull(){
        assertNull(this.tester.getPersonalGoalCard());
    }

    @Test
    public void pointsTilesStartsEmpty(){
        assertTrue(this.tester.getPointsTiles().isEmpty());
    }

    @Test
    public void atMostThreePointsTile(){
        assertTrue(this.tester.getPointsTiles().size() <= 3);
    }

    @Test
    public void noDuplicatePointsTile(){
        for(int i = 0; i<this.tester.getPointsTiles().size(); i++){
            for(int j = 0; j<this.tester.getPointsTiles().size() && i!=j; j++){
                assertEquals(this.tester.getPointsTiles().get(i), this.tester.getPointsTiles().get(j));
            }
        }
    }
}