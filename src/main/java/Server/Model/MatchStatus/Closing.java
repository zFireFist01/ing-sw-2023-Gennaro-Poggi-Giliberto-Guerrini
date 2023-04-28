package Server.Model.MatchStatus;
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
    @Override
    public MatchStatus evolve() throws UnsupportedOperationException {
        return null;
    }

    public void devolve(){}

    public String getReasonClosing(){
        return closedBecause;
    }

}
