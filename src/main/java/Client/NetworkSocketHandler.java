package Client;

import java.net.ServerSocket;
import Client.View.View;
import Server.Events.Event;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Network.VirtualView;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


/**
 * This class is used to handle the network connection with the server
 * @author Marta Giliberto & Paolo Gennaro & Patrick Poggi
 */
public class NetworkSocketHandler implements NetworkHandler{
    private View view;
    private Socket socket;
    private Scanner in;
    private OutputStream out;
    private final String host;
    private final int port;

    /**
     * This constructor is used to create a new NetworkSocketHandler and connect it to the server
     * @param host the host address
     * @param port the port of the server
     * @param view the view
     */
    public NetworkSocketHandler(String host, int port, View view) {
        this.host = host;
        this.view = view;
        this.port = port;
    }

    @Override
    public void sendMVEvent(String json) {
        Gson gson = new Gson();
        MVEvent event = gson.fromJson(json, MVEvent.class);
        view.onMVEvent(event);
    }

    @Override
    public void sendSelectViewEvent(String json){
        Gson gson = new Gson();
        SelectViewEvent event = gson.fromJson(json, SelectViewEvent.class);
        view.onSelectViewEvent(event);
    }

    @Override
    public void onVCEvent(VCEvent event, VirtualView view) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        throw new IllegalAccessError("This method should not be called");
    }

    @Override
    public void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Gson gson = new Gson();
        String json = gson.toJson(event);
        try {
            out.write(json.getBytes());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error while sending event to server");
        }
    }

    @Override
    public void run() {
        String message;
        String primaryType;
        Event event;

        try {
            this.socket = new Socket(host, port);
            this.in = new Scanner(socket.getInputStream());
            this.out = socket.getOutputStream();
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
        }


        while (true) {
            message = in.nextLine();

            Gson gson = new Gson();
            event = gson.fromJson(message, Event.class);
            primaryType = event.getPrimaryType();

            switch (primaryType) {
                case "MVEvent":
                    sendMVEvent(message);
                    break;
                case "SelectViewEvent":
                    sendSelectViewEvent(message);
                    break;
                default:
                    throw new RuntimeException("Unknown event type");
            }
        }
    }
}
