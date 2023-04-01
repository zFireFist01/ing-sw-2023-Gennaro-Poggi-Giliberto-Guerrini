package Server.Model.MatchStatus;

import Server.Model.MatchStatus.MatchStatus;

public class Closing extends MatchStatus {
    @Override
    protected MatchStatus evolve() throws UnsupportedOperationException {
        return null;
    }
}
