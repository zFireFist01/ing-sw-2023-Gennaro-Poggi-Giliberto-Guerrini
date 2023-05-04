package Server.Model.Player;

import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.PointsTile;
import Server.Model.Match;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class PlayerTest {
    Player tester;

    @Before
    public void setUp() throws Exception {
        Match m = new Match();
        String nickName = "nickName";
        int playerID = 4;
        this.tester = new Player(m, playerID, nickName);
    }


    @Test
    public void assignPointTile_GotTheMatchEndTile_UnsupportedOperationException(){
        this.tester.assignPointTile(PointsTile.MATCH_ENDED);
        assertThrows(UnsupportedOperationException.class, ()->this.tester.assignPointTile(PointsTile.randomPointsTile()));
    }

    @Test
    public void assignPointTile_AlreadyATileOfThisCommonGoal_UnsupportedOperationException(){
        this.tester.assignPointTile(PointsTile.TWO_1);
        assertThrows(UnsupportedOperationException.class, ()->this.tester.assignPointTile(PointsTile.EIGHT_1));
    }
}