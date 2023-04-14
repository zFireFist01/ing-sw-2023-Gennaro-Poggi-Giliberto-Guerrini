package Server.Model.Chat;

import Server.Model.Player.Player;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.*;

public class PlayersChatTest {
    PlayersChat tester = new PlayersChat();



    @Test
    public void getMessages() {
        String otherName = "Other";
        int otherID = 1;
        Player other = new Player(otherID, otherName);
        String receiverName = "Receiver";
        int receiverID = 2;
        Player receiver = new Player(receiverID, receiverName);
        String senderName = "Sender";
        int senderID = 3;
        Player sender = new Player(senderID, senderName);
        Time timeSent = new Time(60);
        String content = "Test for message";
        Message message1 = new Message(sender, content, timeSent, receiver);
        Message message2 = new Message(sender, content, timeSent, null);
        Message message3 = new Message(receiver, content, timeSent, sender);
        Message message4 = new Message(receiver, content, timeSent, null);
        Message message5 = new Message(sender, content, timeSent, other);
        this.tester.addMessage(message1);
        this.tester.addMessage(message2);
        this.tester.addMessage(message3);
        this.tester.addMessage(message4);
        this.tester.addMessage(message5);

        assertTrue(this.tester.getMessages(receiver).contains(message1));
        assertTrue(this.tester.getMessages(receiver).contains(message2));
        assertTrue(this.tester.getMessages(receiver).contains(message3));
        assertTrue(this.tester.getMessages(receiver).contains(message4));
        assertFalse(this.tester.getMessages(receiver).contains(message5));
        assertTrue(this.tester.getMessages(sender).contains(message1));
        assertTrue(this.tester.getMessages(sender).contains(message2));
        assertTrue(this.tester.getMessages(sender).contains(message3));
        assertTrue(this.tester.getMessages(sender).contains(message4));
        assertTrue(this.tester.getMessages(sender).contains(message5));
        assertFalse(this.tester.getMessages(other).contains(message1));
        assertTrue(this.tester.getMessages(other).contains(message2));
        assertFalse(this.tester.getMessages(other).contains(message3));
        assertTrue(this.tester.getMessages(other).contains(message4));
        assertTrue(this.tester.getMessages(other).contains(message5));
    }
}