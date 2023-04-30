package Server.Model.MatchStatus;

import Server.Model.Match;

/**
 * this class represents the Closing status
 * @author martagiliberto
 */

public class Closing extends MatchStatus {

    private String closedBecause;

    /**
     * this method ended the match
     * @return null
     * @throws UnsupportedOperationException if MatchStatus can't evolve
     */

    public Closing(Match match) {
        super(match);
    }
    @Override
    public MatchStatus evolve() throws UnsupportedOperationException {
        return null;
    }

    public void devolve(){}

    public String getReasonClosing(){
        return closedBecause;
    }

}
