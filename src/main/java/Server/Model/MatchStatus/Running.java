package Server.Model.MatchStatus;

public class Running extends MatchStatus {

    /**
     * this method evolves the match status from running to closing
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    public MatchStatus evolve() throws UnsupportedOperationException {
        return new Closing();
    }
}
