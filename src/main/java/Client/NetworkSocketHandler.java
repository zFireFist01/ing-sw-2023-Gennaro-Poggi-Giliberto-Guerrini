package Client;

import Client.View.View;
import Server.Events.Event;
import Server.Events.EventTypeAdapterFactory;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Network.VirtualView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketException;
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
    private boolean isReconnecting;

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
        this.isReconnecting = view.isReconnecting();
    }

    @Override
    public void receiveMVEvent(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EventTypeAdapterFactory())
                .create();
        //System.out.println("Received message: " + json);
        MVEvent event = gson.fromJson(json, MVEvent.class);
        view.onMVEvent(event);
    }

    @Override
    public void receiveSelectViewEvent(String json){
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
            //System.out.println("Message sent: " + json);
        } catch (IOException e) {
            throw new RuntimeException("Error while sending event to server");
        }
    }

    @Override
    public void run()  {
        String message;
        String primaryType;
        Event event;

        try {
            this.socket = new Socket(host, port);
            this.in = new Scanner(socket.getInputStream()); //message from server
            this.out = socket.getOutputStream();
            if(isReconnecting){
                out.write("Reconnecting\n".getBytes());
                out.flush();
                out.write(
                        (new Gson().toJson(view.getConnectionInfo())+"\n").getBytes()
                );
                out.flush();
                //In queso caso non devo aspettare il messaggio di benvenuto
                System.out.println("Reconnecting to the socket server!: connection info sent:\n"+new Gson().toJson(view.getConnectionInfo())+"\n");
            } else{
                out.write("Connecting\n".getBytes());
                out.flush();
                out.write(
                        (new Gson().toJson(view.getConnectionInfo())+"\n").getBytes()
                );
                out.flush();
                String welcomeMessage = in.nextLine(); //Devo prima aspettare il messaggio di benvenuto
            }
            System.out.println("Connected to the socket server!");
            //System.out.println("Ricevuto messaggio di benvenuto: " + welcomeMessage);
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
            throw new RuntimeException("Error while connecting to server");
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EventTypeAdapterFactory())
                .create();


        while (true) {
            message = in.nextLine();
            //System.out.println("Received message: " + message);
            if(message.equals("ping")){
                //System.out.println("Received ping message");
                //message = message.replace("ping", "");
                pong();
            }else{
                event = gson.fromJson(message, Event.class);
                primaryType = event.getPrimaryType();

                switch (primaryType) {
                    case "MVEvent":
                        receiveMVEvent(message);
                        break;
                    case "SelectViewEvent":
                        receiveSelectViewEvent(message);

                        break;
                    default:
                        throw new RuntimeException("Unknown event type");
                }
            }
        }
    }

    private void pong(){
        String pongMessage = "pong\n";
        try {
            out.write(pongMessage.getBytes());
            out.flush();
            //System.out.println("Message sent: " + pongMessage);
        }catch (SocketException e){
            System.out.println("Lost connection with server");
            //We don't do anything (is it wrong? We'll see...)
        }catch (IOException e) {
            throw new RuntimeException("Error while sending pong message to server");
        }

    }

}
