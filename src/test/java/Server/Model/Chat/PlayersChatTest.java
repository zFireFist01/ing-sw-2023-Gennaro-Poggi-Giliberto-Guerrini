package Server.Model.Chat;

import Server.Model.Match;
import Server.Model.Player.Player;
import org.junit.Before;
import org.junit.Test;

import java.sql.Time;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class PlayersChatTest {
    PlayersChat tester;



    @Test
    public void getMessages() {
        Match m = new Match();
        tester = new PlayersChat();
        String otherName = "Other";
        int otherID = 1;
        Player other = new Player(m, otherID, otherName);
        String receiverName = "Receiver";
        int receiverID = 2;
        Player receiver = new Player(m, receiverID, receiverName);
        String senderName = "Sender";
        int senderID = 3;
        Player sender = new Player(m, senderID, senderName);
        Time timeSent = new Time(60);
        String content = "Test for message";
        Message message1 = new Message(sender, content, new Time(60), receiver);
        Message message2 = new Message(sender, content, new Time(60));
        Message message3 = new Message(receiver, content, new Time(60), sender);
        Message message4 = new Message(receiver, content, new Time(60));
        Message message5 = new Message(sender, content, new Time(60), other);
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