package Server.Model;

import Server.Model.Cards.PersonalGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.TileType;
import Server.Model.MatchStatus.Running;
import Server.Model.Player.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class represents the tests of Match
 * @author martagiliberto
 */

public class MatchTest {

    @Before
    public void setup() {
        Match match = new Match(2, new Player(1, "pluto23"));
        match.addContestant(new Player(2, "pippo24"));
    }

    //addContestant tests
    /**
     * addContestant test1 checks if when number of missing players is equal to 0, evolves the match status
     * in Running
     */
    @Test
    public void addContestant_test1(){
        Match match= new Match(2, new Player(1, "pluto23"));
        Player player= new Player(2, "pippo25");

        match.addContestant(player);
        assertTrue(match.getMatchStatus() instanceof Running);
    }

    /**
     * addContestant test2 checks if when number of missing players is greater than 0, it throws
     * UnsupportedOperationException
     */
    @Test
    public void addContestant_test2(){
        Match match= new Match(4, new Player(1, "pluto23"));
        Player player= new Player(2, "pippo25");

        assertThrows(UnsupportedOperationException.class,()->  match.addContestant(player));
    }

    /**
     * addContestant test3 checks if when addContestant want to add a player who has the same ID of one player
     * who is already in the match,it throws UnsupportedOperationException
     */
    @Test
    public void addContestant_test3(){
        Match match= new Match(2, new Player(1, "pluto23"));
        Player player= new Player(1, "pippo25");
        assertThrows(UnsupportedOperationException.class,()->  match.addContestant(player));
    }

    //checkIfBookshelfIsFull test

    /**
     * checkIfBookshelfIsFull test1 checks if a full bookshelf results full
     */
    @Test
    public void checkIfBookshelfIsFull_test1(){
        Player player= new Player(1, "pluto23");
        Match match = new Match(2, player);

        Bookshelf bookshelf= new Bookshelf();
        bookshelf=player.getBookshelf();

        for(int j=0; j<5; j++){
            for(int i=5; i>=0; i--){
                bookshelf.insertTile(j, TileType.CATS);
            }
        }

        assertTrue(match.checkIfBookshelfIsFull(player));
    }

    /**
     * checkIfBookshelfIsFull test2 checks if a bookshelf with just one spot free results empty
     */

    @Test
    public void checkIfBookshelfIsFull_test2(){
        Player player= new Player(1, "pluto23");
        Match match = new Match(2, player);

        for(int j=0; j<4; j++){
            for(int i=5; i>=0; i--){
                player.getBookshelf().insertTile(j, TileType.CATS);
            }
        }
            int j=4;
            for(int i=5; i>0; i--){
                player.getBookshelf().insertTile(j, TileType.CATS);
            }


        assertFalse(match.checkIfBookshelfIsFull(player));
    }

    /**
     * checkIfBookshelfIsFull test3 checks if an empty bookshelf  results empty
     */

    @Test
    public void checkIfBookshelfIsFull_test3(){
        Player player= new Player(1, "pluto23");
        Match match = new Match(2, player);

        assertFalse(match.checkIfBookshelfIsFull(player));
    }

    /**
     * checkAdjacentTiles test verifies if calculates right points in a bookshelf
     */
    @Test
    public void checkAdjacentTiles_test(){
        Player player= new Player(1, "pluto23");
        Match match = new Match(2, player);

        player.getBookshelf().insertTile(0, TileType.TROPHIES);
        player.getBookshelf().insertTile(0, TileType.FRAMES);
        player.getBookshelf().insertTile(0, TileType.FRAMES);
        player.getBookshelf().insertTile(0, TileType.FRAMES);
        player.getBookshelf().insertTile(0, TileType.PLANTS);
        player.getBookshelf().insertTile(0, TileType.PLANTS);

        player.getBookshelf().insertTile(1, TileType.TROPHIES);
        player.getBookshelf().insertTile(1, TileType.TROPHIES);
        player.getBookshelf().insertTile(1, TileType.GAMES);
        player.getBookshelf().insertTile(1, TileType.FRAMES);
        player.getBookshelf().insertTile(1, TileType.PLANTS);
        player.getBookshelf().insertTile(1, TileType.PLANTS);

        player.getBookshelf().insertTile(2, TileType.TROPHIES);
        player.getBookshelf().insertTile(2, TileType.CATS);
        player.getBookshelf().insertTile(2, TileType.GAMES);
        player.getBookshelf().insertTile(2, TileType.PLANTS);
        player.getBookshelf().insertTile(2, TileType.PLANTS);

        player.getBookshelf().insertTile(3, TileType.CATS);
        player.getBookshelf().insertTile(3, TileType.CATS);
        player.getBookshelf().insertTile(3, TileType.GAMES);
        player.getBookshelf().insertTile(3, TileType.PLANTS);
        player.getBookshelf().insertTile(3, TileType.PLANTS);

        player.getBookshelf().insertTile(4, TileType.CATS);
        player.getBookshelf().insertTile(4, TileType.CATS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);

        assertTrue(match.checkAdjacentTiles(player)==19);
    }















}



