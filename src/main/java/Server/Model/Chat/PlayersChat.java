package Server.Model.Chat;

import Server.Events.MVEvents.ModifiedChatEvent;
import Server.Model.Player.Player;
import Server.Network.VirtualView;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is for the player chat so that the players can send each other messages while playing
 * @author Paolo Gennaro
 */
public class PlayersChat {
    private final List<Message> messages;

    public PlayersChat() {
        this.messages = new ArrayList<Message>();
    }

    /**
     * We store the message every message sent in an ArrayList
     * @param message is the message we want to add in the ArrayList
     */
    public void addMessage(Message message, VirtualView... virtualViews){
        this.messages.add(message);
        for(VirtualView virtualView : virtualViews){
            virtualView.onMVEvent(new ModifiedChatEvent(message));
        }
    }

    /**
     * This method gives the player all the messages that he sends and that are sent to him
     * @param player is the one who will see all this messages
     * @return the list of messages
     */
    public List<Message> getMessages(Player player) {
        List<Message> copyMessages = new ArrayList<Message>();
        copyMessages.addAll(this.messages.stream().filter(t->(t.getReceiver() != null && t.getReceiver().equals(player))).toList());
        copyMessages.addAll(this.messages.stream().filter(t->t.getSender().equals(player)).toList());
        copyMessages.addAll(this.messages.stream().filter(t-> t.getReceiver() == null).toList());
        return copyMessages;
    }
 }

