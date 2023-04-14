package Server.Model.Chat;

import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.PointsTile;
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
    }

    @Test
    public void messageConstructor_NoSelfTexting_UnsupportedOperationException() {
        String senderName = "Sender";
        int senderID = 3;
        Player sender = new Player(senderID, senderName);
        Time timeSent = new Time(60);
        String content = "Test for message";
        assertThrows(UnsupportedOperationException.class, ()->this.tester = new Message(sender, content, timeSent, sender));
    }
}