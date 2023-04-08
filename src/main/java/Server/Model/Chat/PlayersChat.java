package Server.Model.Chat;

import Server.Model.Player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for the player chat so that the players can send each other messages while playing
 * @author Paolo Gennaro
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

    /**
     * This method gives the player all the messages that he sends and that are sent to him
     * @param player is the one who will see all this messages
     * @return the list of messages
     */
    public List<Message> getMessages(Player player) {
        List<Message> copyMessages = new ArrayList<Message>(this.messages.stream().filter(t->t.getReceiver().equals(player)).toList());
        copyMessages.addAll(this.messages.stream().filter(t->t.getSender().equals(player)).toList());
        copyMessages.addAll(this.messages.stream().filter(t-> t.getReceiver() == null).toList());
        return copyMessages;
    }
 }

