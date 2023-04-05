package Server.Model.Player;


/**
 * Connected means that the player is currently playing or waiting for is turn to play
 * @author Paolo Gennaro
 */
public class Connected extends PlayerStatus {
    private final long turnDuration = System.currentTimeMillis();

    /**
     * This function is used to take count of how many seconds the player is playing is Turn
     * @return the seconds of connection
     */
    public int getTimeInSeconds(){
        long nowMillis = System.currentTimeMillis();
        return (int)((nowMillis - this.turnDuration) / 1000);
    }
}
