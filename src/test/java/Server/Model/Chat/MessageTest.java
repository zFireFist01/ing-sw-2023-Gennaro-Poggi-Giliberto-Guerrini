package Server.Model.Chat;

import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.PointsTile;
import Server.Model.Match;
import Server.Model.Player.Player;
import org.junit.Before;
import org.junit.Test;

import java.awt.print.Book;
import java.sql.Time;
import java.time.LocalTime;

import static org.junit.Assert.*;

public class MessageTest {
    Message tester;

    @Before
    public void setUp() throws Exception{
    }

    @Test
    public void messageConstructor_NoSelfTexting_UnsupportedOperationException() {
        Match match = new Match();
        String senderName = "Sender";
        int senderID = 3;
        Player sender = new Player(match, senderID, senderName);
        LocalTime timeSent = LocalTime.now();
        String content = "Test for message";
        assertThrows(UnsupportedOperationException.class, ()->this.tester = new Message(sender, content, timeSent, sender));
    }
}