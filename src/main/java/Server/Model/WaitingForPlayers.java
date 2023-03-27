package Server.Model;

public class WaitingForPlayers extends MatchStatus{
    private int numberMissingPlayers;

    @Override
    protected MatchStatus evolve() throws UnsupportedOperationException{
        numberMissingPlayers++;
        if(numberMissingPlayers == 0){
            return new Running();
        }else{
            throw new UnsupportedOperationException("Still "+numberMissingPlayers+
                    "players missing");
        }
    }
}
