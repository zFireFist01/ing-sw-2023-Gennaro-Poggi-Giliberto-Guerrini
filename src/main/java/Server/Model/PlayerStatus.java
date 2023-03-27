package Server.Model;

import java.sql.Time;


/**
 * PlayerStatus defines the status of a player that joined the actual match
 */
public abstract class PlayerStatus {
}

/**
 * Connected means that the player is currently playing or waiting for is turn to play
 * @param turnDuration it's just a chronometer
 */
public class Connected extends PlayerStatus{
    private Time turnDuration;
}


/**
 * Disconnected means that the player is currently not playing and it's not "online"
 * @param disconnectionTime it's a chronometer for how long the player is being disconnected
 */
public class Disconnected extends PlayerStatus{
    private Time disconnectionTime;
}
