package Server.Model.MatchStatus;

/**
 *match status abstract class in order to store the status of the match
 * @author martagiliberto
 */
public abstract class MatchStatus {

    /**
     * this method evolve the status of the match to a new status
     * @return the new MatchStatus of the match
     * @throws UnsupportedOperationException if MatchStatus can't evolve
     * @author martagiliberto
     */
     public abstract MatchStatus evolve() throws UnsupportedOperationException;

    /**
     * @author martagiliberto
     */
    public abstract void devolve();
}
