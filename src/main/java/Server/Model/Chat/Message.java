package Server.Model.Chat;

import Server.Model.Player.Player;
import com.google.gson.annotations.Expose;

import java.sql.Time;


/**
 * This class defines messages that players can send to each other
 * @author Paolo Gennaro
 */
public class Message {
    @Expose
    private final Player sender;
    @Expose
    private final String content;
    @Expose
    private final Time timeSent;
    @Expose
    private final Player receiver;

    /**
     * This constructor is for creating a message between sender and receiver
     * @param sender is the one who sends the message
     * @param content is the content of the message
     * @param timeSent is the time when the message is sent
     * @param receiver is the one who receives the message
     * @throws UnsupportedOperationException when the sender and the receiver are the same
     */
    public Message(Player sender, String content, Time timeSent, Player receiver) throws UnsupportedOperationException{
        if(sender.equals(receiver)) throw new UnsupportedOperationException("You can't text yourself!");
        this.sender = sender;
        this.content = content;
        this.timeSent = timeSent;
        this.receiver = receiver;
    }

    /**
     * This constructor is for creating a message between sender and all the other players
     * @param sender is the one who sends the message
     * @param content is the content of the message
     * @param timeSent is the time when the message is sent
     */
    public Message(Player sender, String content, Time timeSent){
        this.sender = sender;
        this.content = content;
        this.timeSent = timeSent;
        this.receiver = null;
    }

    /**
     * This function is for printing a message
     */
    public void printMessage(){
        if(receiver != null) {
            System.out.println("[" + this.timeSent + "]" + " " + this.sender.getPlayerNickName() + ":" + " " + this.content);
        }else{
            System.out.println("[" + this.timeSent + "]" + " " + this.sender.getPlayerNickName() + " to @All:" + " " + this.content);
        }
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
