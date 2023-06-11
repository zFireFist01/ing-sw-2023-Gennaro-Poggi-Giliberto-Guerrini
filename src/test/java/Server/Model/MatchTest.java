package Server.Model;

import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.TileType;
import Server.Model.MatchStatus.Running;
import Server.Model.Player.Player;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class represents the tests of Match
 * @author Marta Giliberto
 */

public class MatchTest {

    //addContestant tests
    /**
     * addContestant test1 checks if when number of missing players is equal to 0, evolves the match status
     * in Running
     *  @author Marta Giliberto
     */
    @Test
    public void addContestant_test1(){
        Match match= new Match();
        Player player1= new Player(match, 2, "pluto23");
        match.addContestant(player1);
        match.setNumberOfPlayers(2);
        Player player2= new Player(match,2, "pippo25");
        match.addContestant(player2);
        assertTrue(match.getMatchStatus() instanceof Running);
    }

    /**
     * addContestant test2 checks if when number of missing players is greater than 0, it throws
     * UnsupportedOperationException
     *  @author Marta Giliberto
     */
    @Test
    public void addContestant_test2(){
        Match match= new Match();
        Player player= new Player(match, 2, "pippo25");
        match.setNumberOfPlayers(2);
        assertThrows(UnsupportedOperationException.class,()->  match.addContestant(player));
    }

    /**
     * addContestant test3 checks if when addContestant want to add a player who has the same ID of one player
     * who is already in the match,it throws UnsupportedOperationException
     *  @author Marta Giliberto
     */
    @Test
    public void addContestant_test3(){
        Match match= new Match();
        Player player1= new Player(match, 2, "pippo25");
        match.setNumberOfPlayers(2);
        match.addContestant(player1);
        Player player2= new Player(match,2, "pippo25");
        assertThrows(UnsupportedOperationException.class,()->  match.addContestant(player2));
    }

    //checkIfBookshelfIsFull test

    /**
     * checkIfBookshelfIsFull test1 checks if a full bookshelf results full
     *  @author Marta Giliberto
     */
    @Test
    public void checkIfBookshelfIsFull_test1(){
        Match match = new Match();
        Player player= new Player(match,1, "pluto23");
        match.setNumberOfPlayers(2);
        match.addContestant(player);
        Player player2= new Player(match,2, "pippo25");
        match.addContestant(player2);

        Bookshelf bookshelf;
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
     *  @author Marta Giliberto
     */
    @Test
    public void checkIfBookshelfIsFull_test2(){
        Match match = new Match();
        Player player= new Player(match,1, "pluto23");
        match.setNumberOfPlayers(2);
        match.addContestant(player);
        Player player2= new Player(match,2, "pippo25");
        match.addContestant(player2);

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
     *  @author Marta Giliberto
     */
    @Test
    public void checkIfBookshelfIsFull_test3(){
        Match match = new Match();
        Player player= new Player(match,1, "pluto23");
        match.setNumberOfPlayers(2);
        match.addContestant(player);
        Player player2= new Player(match,2, "pippo25");
        match.addContestant(player2);

        assertFalse(match.checkIfBookshelfIsFull(player));
    }

    /**
     * checkAdjacentTiles test verifies if calculates right points in a bookshelf
     *  @author Marta Giliberto
     */
    @Test
    public void checkAdjacentTiles_test(){
        Match match = new Match();
        Player player= new Player(match,1, "pluto23");
        match.setNumberOfPlayers(2);
        match.addContestant(player);
        Player player2= new Player(match,2, "pippo25");
        match.addContestant(player2);

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



