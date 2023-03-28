package Server.Model;

import java.sql.Time;


/**
 * This class defines messages that players can send to each other
 */
public class Message {
    private Player sender;
    private String content;
    private Time timeSent;
    private Player receiver;
}
