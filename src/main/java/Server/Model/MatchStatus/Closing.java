package Server.Model.MatchStatus;
/**
 * this class represents the Closing status
 * @author martagiliberto
 */

public class Closing extends MatchStatus {

    private String closedBecause;


    @Override
    public MatchStatus evolve() throws UnsupportedOperationException {
        return null;
    }

    public String getReasonClosing(){
        return closedBecause;
    }
}
