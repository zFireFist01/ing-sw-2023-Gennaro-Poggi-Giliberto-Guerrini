package Server.Model.MatchStatus;
/**
 * this class represents the Waiting for players status
 * @author martagiliberto
 */
public class WaitingForPlayers extends MatchStatus {
    private int numberMissingPlayers;

    /**
     * this method tries to evolve the Match status from WaitingForPlayers to Running
     * @return Running status
     * @throws UnsupportedOperationException if Match status can't evolve
     */
    @Override
    public MatchStatus evolve() throws UnsupportedOperationException{
        numberMissingPlayers--;
        if(numberMissingPlayers == 0){
            return new Running();
        }else{
            throw new UnsupportedOperationException("Still "+numberMissingPlayers+
                    "players missing");
        }
    }

    @Override
    public void devolve(){
        numberMissingPlayers++;
    }


}
