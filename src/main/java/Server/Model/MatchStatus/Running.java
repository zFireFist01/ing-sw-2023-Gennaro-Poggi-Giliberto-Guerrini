package Server.Model.MatchStatus;

import Server.Model.Match;

/**
 * this class represents the Running status
 * @author martagiliberto
 */
public class Running extends MatchStatus {

    public Running(Match match){
        super(match);
        match.setup();
        //MVEvent matchsatarted
    }
    /**
     * this method evolves the match status from running to closing
     * @return match
     * @throws UnsupportedOperationException if match status can't evolve
     */
    @Override
    public MatchStatus evolve() throws UnsupportedOperationException {
        return new Closing(match);
    }

    public MatchStatus devolve(){return this;}

}
