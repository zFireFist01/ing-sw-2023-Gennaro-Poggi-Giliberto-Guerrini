package Server.Model.MatchStatus;

import Server.Model.Match;
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