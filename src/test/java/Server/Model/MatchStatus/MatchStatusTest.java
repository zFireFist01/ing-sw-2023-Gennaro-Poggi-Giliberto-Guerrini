package Server.Model.MatchStatus;

import org.junit.Test;
import static org.junit.Assert.*;

public class MatchStatusTest {

    @Test
    public void NotRunning_test(){
        MatchStatus status = new NotRunning();
        status.evolve();
        assertSame(new WaitingForPlayers(), status);
    }

    @Test
    public void WaitingForPlayers_test(){
        MatchStatus status = new WaitingForPlayers();
        status.evolve();
    }

    @Test
    public void Running_test(){
        MatchStatus status = new Running();
        status.evolve();
        assertSame(new Closing(), status);
    }

    @Test
    public void Closing_test(){
        MatchStatus status = new Closing();
        status.evolve();
        assertSame(null, status);
    }

}