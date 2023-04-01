package Server.Model.MatchStatus;

public class Running extends MatchStatus {
    @Override
    protected MatchStatus evolve() throws UnsupportedOperationException {
        return new Closing();
    }
}
