package Server.Model.Chat;

import Server.Model.GameItems.Bookshelf;
import Server.Model.Player.Player;
import org.junit.Before;
import org.junit.Test;

import java.awt.print.Book;
import java.sql.Time;

import static org.junit.Assert.*;

public class MessageTest {
    Message tester;

    @Before
    public void setUp() throws Exception{
        Bookshelf bookshelf1 = new Bookshelf();
        Bookshelf bookshelf2 = new Bookshelf();
        String receiverName = "Receiver";
        String senderName = "Sender";
        int receiverID = 2;
        int senderID = 3;
        Player receiver = new Player(receiverID, bookshelf1, receiverName);
        Player sender = new Player(senderID, bookshelf1, senderName);
        Time timeSent = new Time(60);
        String content = "Test for message";
        this.tester = new Message(sender, content, timeSent, receiver);
    }

    @Test
    public void noSelfTexting() {
        assertEquals(this.tester.getSender().getPlayerNickName(), this.tester.getReceiver().getPlayerNickName());
    }
}