package Server.Model.Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for the player chat so that the players can send each other messages while playing
 */
public class PlayersChat {
    private List<Message> messages;

    /**
     * We store the message every message sent in an ArrayList
     * @param message is the message we want to add in the ArrayList
     */
    public void addMessage(Message message){
        this.messages = new ArrayList<Message>();
        this.messages.add(message);
    }
    public List<Message> getMessages() {
        return this.messages;
    }
 }
