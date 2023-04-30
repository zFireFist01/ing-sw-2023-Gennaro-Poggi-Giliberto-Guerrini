package Server.Model.MatchStatus;

import Server.Model.Match;

/**
 * this class represents the Closing status
 * @author Marta Giliberto
 */

public class Closing extends MatchStatus {

    private String closedBecause;
    public Closing(Match match) {
        super(match);
    }
    /**
     * this method ended the match
     * @return null
     * @throws UnsupportedOperationException if MatchStatus can't evolve
     * @author Marta Giliberto
     */

    @Override
    public MatchStatus evolve() throws UnsupportedOperationException {
        return null;
    }

    public MatchStatus devolve(){return this;}

    public String getReasonClosing(){
        return closedBecause;
    }

}
