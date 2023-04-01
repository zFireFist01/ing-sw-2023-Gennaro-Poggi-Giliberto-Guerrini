package Server.Model.Player;

import Server.Model.Player.PlayerStatus;

import java.sql.Time;

/**
 * Disconnected means that the player is currently not playing and it's not "online"
 */
public class Disconnected extends PlayerStatus {
    private Time disconnectionTime;
}
