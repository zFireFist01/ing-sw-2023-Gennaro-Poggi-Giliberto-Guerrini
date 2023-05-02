package Server.Model.MatchStatus;

import Server.Model.Match;
import Server.Model.Player.Player;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class MatchStatusTest {
    @Test
    public void NotRunning_test(){
        Match match= new Match();
        MatchStatus status = new NotRunning(match);
        status.evolve();
        assertSame(new WaitingForPlayers(match), status);
    }

    @Test
    public void WaitingForPlayers_test(){
        Match match= new Match();
        MatchStatus status = new WaitingForPlayers(match);
        status.evolve();
    }

    @Test
    public void WaitingForPlayers_test2(){
        Match match= new Match();
        MatchStatus status = new WaitingForPlayers(match);
        match.setNumberOfPlayers(3);
        Player player1= new Player(match, 2, "pluto23");
        match.addContestant(player1);
        Player player2= new Player(match,2, "pippo25");
        match.addContestant(player2);
        status.devolve();
        status.devolve();
        assertSame(null, status);
    }

    @Test
    public void Running_test(){
        Match match= new Match();
        MatchStatus status = new Running(match);
        status.evolve();
        assertSame(new Closing(match), status);
    }

    @Test
    public void Closing_test(){
        Match match= new Match();
        MatchStatus status = new Closing(match);
        status.evolve();
        assertSame(null, status);
    }

}