package Server.Model.MatchStatus;

/**
 *match status abstract class in order to store the status of the match
 */
public abstract class MatchStatus {

    /**
     * this method evolve the status of the match to a new status
     * @return
     * @throws UnsupportedOperationException
     */
    abstract public MatchStatus evolve() throws UnsupportedOperationException;
}
