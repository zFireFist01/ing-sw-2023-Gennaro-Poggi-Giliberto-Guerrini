package Server.Model.MatchStatus;

import Server.Model.Match;

/**
 * This class represents the WaitingForPlayers status
 * @author Marta Giliberto
 */
public class WaitingForPlayers extends MatchStatus {
    private int numberMissingPlayers;

    public WaitingForPlayers(Match match){
        super(match);
        this.numberMissingPlayers = match.getNumberOfPlayers() - match.getPlayers().size();
    }

    /**
     * This method tries to evolve the Match status from WaitingForPlayers to Running
     * @return Running status
     * @throws UnsupportedOperationException if Match status can't evolve due to missing players
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

    /**
     * This method is called when one player disconnect while the match in WaitingForPlayers status.
     * @return null if the number of missing players is equal to the number of players of the match;
     *         this otherwise;
     */
    @Override
    public MatchStatus devolve(){
        numberMissingPlayers++;
        if(match.getNumberOfPlayers()==numberMissingPlayers){
          return null;
        }
        return this;
    }


}
