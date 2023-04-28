package Server.Model.MatchStatus;

/**
 * this class represents the Running status
 * @author martagiliberto
 */
public class Running extends MatchStatus {

    /**
     * this method evolves the match status from running to closing
     * @return match
     * @throws UnsupportedOperationException if match status can't evolve
     */
    @Override
    public MatchStatus evolve() throws UnsupportedOperationException {
        return new Closing();
    }

    public void devolve(){

    }

}
