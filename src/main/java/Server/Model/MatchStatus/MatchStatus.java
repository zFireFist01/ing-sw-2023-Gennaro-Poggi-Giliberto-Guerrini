package Server.Model.MatchStatus;

import Server.Model.Match;

/**
 * This abstract class is the status of the match
 * @author Marta Giliberto
 */
public abstract class MatchStatus {

    Match match;

    public MatchStatus(Match match){
        this.match = match;

    }

    /**
     * This method evolve the status of the match to a new status
     * @return the new MatchStatus of the match
     * @throws UnsupportedOperationException if MatchStatus can't evolve
     */
    public abstract MatchStatus evolve() throws UnsupportedOperationException;

    /**
     * This method devolve the status of the match to a previous status
     * @return In every case, except for WaitingForPlayers, this method returns this
     */
    public abstract MatchStatus devolve();
}
