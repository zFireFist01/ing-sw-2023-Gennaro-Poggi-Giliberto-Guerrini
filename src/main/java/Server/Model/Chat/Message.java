package Server.Model.Chat;

import Server.Model.Player.Player;

import java.sql.Time;


/**
 * This class defines messages that players can send to each other
 * @author Paolo Gennaro
 */
public class Message {
    private final Player sender;
    private final String content;
    private final Time timeSent;
    private final Player receiver;

    public Message(Player sender, String content, Time timeSent, Player receiver) throws UnsupportedOperationException{
        if(sender.equals(receiver)) throw new UnsupportedOperationException("You can't text yourself!");
        this.sender = sender;
        this.content = content;
        this.timeSent = timeSent;
        this.receiver = receiver;
    }

    /**
     * This function is for printing a message
     * @param message is the message we want to print
     */
    public void printMessage(Message message){
        System.out.println("[" + this.timeSent + "]" + " " + this.sender.getPlayerNickName() + ":" + " " + this.content);
    }

    public Player getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public Time getTimeSent() {
        return timeSent;
    }

    public Player getReceiver() {
        return receiver;
    }
}
