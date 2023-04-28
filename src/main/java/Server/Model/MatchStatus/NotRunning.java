package Server.Model.MatchStatus;

public class NotRunning extends MatchStatus{

    @Override
    public MatchStatus evolve() throws UnsupportedOperationException {
        return new WaitingForPlayers();
    }

    public void devolve(){}
}
