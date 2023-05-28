package Server.Controller;

import Server.Model.Match;
import Server.Model.Player.Player;
import Server.Network.Server;
import Server.Network.VirtualSocketView;
import Server.Network.VirtualView;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

public class ControllerTest {
    @Before
    public void setUp() throws Exception {
        Socket socket = new Socket();
        VirtualView virtualView = new VirtualSocketView(socket, false);
        Match match = new Match();
        Player player = new Player(match, 23, "pippo");
        Server server = new Server();
        Controller controller = new Controller(match, server);
    }


    @Test
    public void onLoginEvent_test1() throws IOException {

    }
}