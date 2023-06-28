package Server.Model.MatchStatus;

import Server.Model.Match;

/**
 * This class represents the Closing status
 * @author Marta Giliberto
 */
public class Closing extends MatchStatus {

    public Closing(Match match) {
        super(match);
    }

    /**
     * This method set the MatchStatus to null
     * @return null
     * @throws UnsupportedOperationException should never be thrown
     */
    @Override
    public MatchStatus evolve() throws UnsupportedOperationException {
        return null;
    }

    public MatchStatus devolve(){return this;}
}
