package Server.Model.MatchStatus;

import Server.Model.Match;

public class NotRunning extends MatchStatus{

    public NotRunning(Match match){
        super(match);
    }

    @Override
    public MatchStatus evolve() throws UnsupportedOperationException {
        return new WaitingForPlayers(match);
    }

    public void devolve(){}
}
