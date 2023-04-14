package Server.Model.MatchStatus;


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
