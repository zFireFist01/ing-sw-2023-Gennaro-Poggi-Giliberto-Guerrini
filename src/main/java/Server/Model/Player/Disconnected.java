package Server.Model.Player;

/**
 * Disconnected means that the player is currently not playing and "not online"
 * @author Paolo Gennaro
 */
public class Disconnected extends PlayerStatus {
    private final long disconnectionTime = System.currentTimeMillis();

    /**
     * This function is used to take count of how many seconds the player has been disconnected
     * @return the seconds of disconnection
     */
    public int getTimeInSeconds(){
        long nowMillis = System.currentTimeMillis();
        return (int)((nowMillis - this.disconnectionTime) / 1000);
    }
}
