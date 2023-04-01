package Server.Model.Player;

import Server.Model.Player.PlayerStatus;

import java.sql.Time;

/**
 * Connected means that the player is currently playing or waiting for is turn to play
 */
public class Connected extends PlayerStatus {
    private Time turnDuration;
}
