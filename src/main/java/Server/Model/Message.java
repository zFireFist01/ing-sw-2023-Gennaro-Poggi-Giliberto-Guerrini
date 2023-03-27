package Server.Model;

import java.sql.Time;


/**
 * This class defines messages that players can send to each other
 * @param sender the player that sends the message
 * @param content this string contains the message itself
 * @param timeSent the time the message was sent
 * @param receiver the player that receives the message
 */
public class Message {
    private Player sender;
    private String content;
    private Time timeSent;
    private Player receiver;
}
