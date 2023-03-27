package Server.Model;

public class Running extends MatchStatus{
    @Override
    protected MatchStatus evolve() throws UnsupportedOperationException {
        return new Closing();
    }
}
