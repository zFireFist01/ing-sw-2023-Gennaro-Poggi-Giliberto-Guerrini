package Server.Model.MatchStatus;

import Server.Model.Match;

/**
 * This class represents the Running status
 * @author Marta Giliberto
 */
public class Running extends MatchStatus {

    public Running(Match match){
        super(match);
        match.setup();
        //MVEvent match started
    }
    /**
     * This method evolves the match status from Running to Closing
     * @return the Closing status
     * @throws UnsupportedOperationException should never be thrown
     */
    @Override
    public MatchStatus evolve() throws UnsupportedOperationException {
        return new Closing(match);
    }

    public MatchStatus devolve(){return this;}

}
