package Server.Model.MatchStatus;

import Server.Model.Match;

/**
 * this class represents the Waiting for players status
 * @author martagiliberto
 */
public class WaitingForPlayers extends MatchStatus {
    private int numberMissingPlayers;

    public WaitingForPlayers(Match match){
        super(match);
        this.numberMissingPlayers = match.getNumberOfPlayers() - match.getPlayers().size();
    }
    /**
     * this method tries to evolve the Match status from WaitingForPlayers to Running
     * @return Running status
     * @throws UnsupportedOperationException if Match status can't evolve
     */

    @Override
    public MatchStatus evolve() throws UnsupportedOperationException{
        numberMissingPlayers--;
        if(numberMissingPlayers == 0){
            return new Running(match);
        }else{
            throw new UnsupportedOperationException("Still "+numberMissingPlayers+
                    "players missing");
        }
    }

    @Override
    public MatchStatus devolve(){
        numberMissingPlayers++;
        if(match.getNumberOfPlayers()==numberMissingPlayers){
          return null;
        }
        return this;
    }


}
