package Client;

import java.net.ServerSocket;
import Client.View.View;
import Server.Events.Event;
import Server.Events.EventTypeAdapterFactory;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.CommonGoalCardAdapter;
import Server.Network.VirtualView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EventTypeAdapterFactory())
                .create();
        System.out.println("Received message: " + json);
        MVEvent event = gson.fromJson(json, MVEvent.class);
        view.onMVEvent(event);
    }

    @Override
    public void sendSelectViewEvent(String json){
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EventTypeAdapterFactory())
                .create();
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
        json+="\n";
        try {
            out.write(json.getBytes());
            out.flush();
            System.out.println("Message sent: " + json);
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
            this.in = new Scanner(socket.getInputStream()); //message from server
            this.out = socket.getOutputStream();
            System.out.println("Connected to the socket server!");
            String welcomeMessage = in.nextLine();
            System.out.println("Ricevuto messaggio di benvenuto: " + welcomeMessage);
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EventTypeAdapterFactory())
                .create();


        while (true) {
            message = in.nextLine();
            System.out.println("Received message: " + message);


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
