package Server.Model.MatchStatus;

import Server.Model.Match;

/**
 *match status abstract class in order to store the status of the match
 * @author martagiliberto
 */
public abstract class MatchStatus {

    Match match;

    /**
     * this method evolve the status of the match to a new status
     * @return the new MatchStatus of the match
     * @throws UnsupportedOperationException if MatchStatus can't evolve
     * @author martagiliberto
     */

    public MatchStatus(Match match){
        this.match = match;

    }
     public abstract MatchStatus evolve() throws UnsupportedOperationException;

    /**
     * @author martagiliberto
     */
    public abstract void devolve();
}
