package Server.Model.MatchStatus;

/**
 *match status abstract class in order to store the status of the match
 * @author martagiliberto
 */
public abstract class MatchStatus {

    /**
     * this method evolve the status of the match to a new status
     * @return MatchStatus
     * @throws UnsupportedOperationException
     */
     public abstract MatchStatus evolve() throws UnsupportedOperationException;

    public abstract void devolve() throws UnsupportedOperationException;
}
