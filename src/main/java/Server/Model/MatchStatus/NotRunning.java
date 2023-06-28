package Server.Model.MatchStatus;

import Server.Model.Match;

/**
 * This class represents the NotRunning status
 * @author Marta Giliberto
 */
public class NotRunning extends MatchStatus {

    public NotRunning(Match match) {
        super(match);
    }

    /**
     * This method evolve the status from NotRunning to WaitingForPlayers
     * @return the WaitingForPlayers status
     * @throws UnsupportedOperationException Should never be thrown
     */
    @Override
    public MatchStatus evolve() throws UnsupportedOperationException {
        return new WaitingForPlayers(match);
    }

    public MatchStatus devolve() {
        return this;
    }

}
