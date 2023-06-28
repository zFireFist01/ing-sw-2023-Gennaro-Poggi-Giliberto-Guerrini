package Server.Model.MatchStatus;

import Server.Model.GameItems.TileType;
import Server.Model.Match;

import Server.Model.Player.Player;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class MatchStatusTest {
    @Test
    public void NotRunning_test(){
        Match match= new Match();
        assertTrue(match.getMatchStatus() instanceof NotRunning);
    }

    @Test
    public void WaitingForPlayers_test(){
        Match match= new Match();
        Random random = new Random();
        match.setNumberOfPlayers(random.nextInt(5));
        Player player1= new Player(match, 1, "pluto23");
        match.addContestant(player1);
        assertTrue(match.getMatchStatus() instanceof WaitingForPlayers);
    }

    @Test
    public void WaitingForPlayers_test2(){
        Match match= new Match();
        match.setNumberOfPlayers(3);
        Player player1= new Player(match, 1, "pluto23");
        match.addContestant(player1);
        Player player2= new Player(match,2, "pippo25");
        match.addContestant(player2);
        match.removeContestant(player2);
        assertTrue(match.getMatchStatus() instanceof WaitingForPlayers);

    }

    @Test
    public void WaitingForPlayers_test3(){
        Match match= new Match();
        match.setNumberOfPlayers(3);
        Player player1= new Player(match, 2, "pluto23");
        match.addContestant(player1);
        match.removeContestant(player1);
        assertTrue(match.getMatchStatus() == null);;
    }


    @Test
    public void Running_test1(){
        Match match= new Match();
        match.setNumberOfPlayers(2);
        Player player1= new Player(match, 1, "pluto23");
        match.addContestant(player1);
        Player player2= new Player(match,2, "pippo25");
        match.addContestant(player2);
        assertTrue(match.getMatchStatus() instanceof Running);
    }

    @Test
    public void Running_test2(){
        Match match= new Match();
        match.setNumberOfPlayers(3);
        Player player1= new Player(match, 1, "pluto23");
        match.addContestant(player1);
        Player player2= new Player(match,2, "pippo25");
        match.addContestant(player2);
        Player player3= new Player(match, 3, "paperino23");
        match.addContestant(player3);

        assertTrue(match.getMatchStatus() instanceof Running);
    }

    @Test
    public void Running_test3(){
        Match match= new Match();
        match.setNumberOfPlayers(4);
        Player player1= new Player(match, 1, "pluto23");
        match.addContestant(player1);
        Player player2= new Player(match,2, "pippo25");
        match.addContestant(player2);
        Player player3= new Player(match, 3, "paperino23");
        match.addContestant(player3);
        Player player4= new Player(match,4, "minnie25");
        match.addContestant(player4);
        assertTrue(match.getMatchStatus() instanceof Running);
    }


    @Test
    public void Closing_test1(){
        Match match= new Match();
        match.setNumberOfPlayers(2);
        Player player1= new Player(match, 1, "pluto23");
        match.addContestant(player1);
        Player player2= new Player(match,2, "pippo25");
        match.addContestant(player2);

        Player firstPlayer=match.getFirstPlayer();
        Player player=firstPlayer.getNextPlayer();

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
        player.getBookshelf().insertTile(2, TileType.PLANTS);

        player.getBookshelf().insertTile(3, TileType.CATS);
        player.getBookshelf().insertTile(3, TileType.CATS);
        player.getBookshelf().insertTile(3, TileType.GAMES);
        player.getBookshelf().insertTile(3, TileType.PLANTS);
        player.getBookshelf().insertTile(3, TileType.PLANTS);
        player.getBookshelf().insertTile(3, TileType.PLANTS);

        player.getBookshelf().insertTile(4, TileType.CATS);
        player.getBookshelf().insertTile(4, TileType.CATS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);


        if(match.checkIfBookshelfIsFull(player)) {
            match.calculateFinalScores();

        }
        assertTrue(match.getMatchStatus() instanceof Closing);


    }

    @Test
    public void Closing_test2(){
        Match match= new Match();
        match.setNumberOfPlayers(3);
        Player player1= new Player(match, 1, "pluto23");
        match.addContestant(player1);
        Player player2= new Player(match,2, "pippo25");
        match.addContestant(player2);
        Player player3= new Player(match,3, "paperina25");
        match.addContestant(player3);

        Player firstPlayer=match.getFirstPlayer();
        Player player=firstPlayer.getNextPlayer().getNextPlayer();

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
        player.getBookshelf().insertTile(2, TileType.PLANTS);

        player.getBookshelf().insertTile(3, TileType.CATS);
        player.getBookshelf().insertTile(3, TileType.CATS);
        player.getBookshelf().insertTile(3, TileType.GAMES);
        player.getBookshelf().insertTile(3, TileType.PLANTS);
        player.getBookshelf().insertTile(3, TileType.PLANTS);
        player.getBookshelf().insertTile(3, TileType.PLANTS);

        player.getBookshelf().insertTile(4, TileType.CATS);
        player.getBookshelf().insertTile(4, TileType.CATS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);


        if(match.checkIfBookshelfIsFull(player)) {
            match.calculateFinalScores();

        }
        assertTrue(match.getMatchStatus() instanceof Closing);

    }

    @Test
    public void Closing_test3(){
        Match match= new Match();
        match.setNumberOfPlayers(4);
        Player player1= new Player(match, 1, "pluto23");
        match.addContestant(player1);
        Player player2= new Player(match,2, "pippo25");
        match.addContestant(player2);
        Player player3= new Player(match,3, "paperina25");
        match.addContestant(player3);
        Player player4= new Player(match,4, "topolino25");
        match.addContestant(player4);

        Player firstPlayer=match.getFirstPlayer();
        Player player= firstPlayer.getNextPlayer().getNextPlayer().getNextPlayer();

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
        player.getBookshelf().insertTile(2, TileType.PLANTS);

        player.getBookshelf().insertTile(3, TileType.CATS);
        player.getBookshelf().insertTile(3, TileType.CATS);
        player.getBookshelf().insertTile(3, TileType.GAMES);
        player.getBookshelf().insertTile(3, TileType.PLANTS);
        player.getBookshelf().insertTile(3, TileType.PLANTS);
        player.getBookshelf().insertTile(3, TileType.PLANTS);

        player.getBookshelf().insertTile(4, TileType.CATS);
        player.getBookshelf().insertTile(4, TileType.CATS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);
        player.getBookshelf().insertTile(4, TileType.BOOKS);


        if(match.checkIfBookshelfIsFull(player)) {
            match.calculateFinalScores();

        }
        assertTrue(match.getMatchStatus() instanceof Closing);

    }




}