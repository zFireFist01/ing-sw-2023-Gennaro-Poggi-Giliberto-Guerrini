package Server.Model.MatchStatus;

import Server.Model.Match;

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

    public Running(Match match){
        super(match);
        match.setup();
        //MVEvent matchsatarted
    }


    @Override
    public MatchStatus evolve() throws UnsupportedOperationException {
        return new Closing(match);
    }

    public void devolve(){}

}
