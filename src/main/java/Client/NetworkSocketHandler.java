package Client;

import Client.View.CLI.ANSIParameters;
import Client.View.View;
import Server.Events.Event;
import Server.Events.EventTypeAdapterFactory;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Network.VirtualView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketException;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * This class implements the NetworkHandler interface and is responsible for handling socket communications.
 * This class communicates with the server via sockets, and uses Gson to serialize/deserialize JSON objects to/from strings.
 *
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
     * Constructs a NetworkSocketHandler with the specified host, port, and View.
     *
     * @param host The host to connect to.
     * @param port The port to connect to.
     * @param view The View instance associated with this NetworkSocketHandler.
     */
    public NetworkSocketHandler(String host, int port, View view) {
        this.host = host;
        this.view = view;
        this.port = port;
        this.isReconnecting = view.isReconnecting();
    }

    /**
     * Receives and processes a MVEvent from the server.
     *
     * @param json The JSON representation of the MVEvent.
     */
    @Override
    public void receiveMVEvent(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EventTypeAdapterFactory())
                .create();
        //System.out.println("Received message: " + json);
        MVEvent event = gson.fromJson(json, MVEvent.class);
        view.onMVEvent(event);
    }

    /**
     * Receives and processes a SelectViewEvent from the server.
     *
     * @param json The JSON representation of the SelectViewEvent.
     */
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

    /**
     * This method serializes a VCEvent object into a JSON string and writes it to the output stream for the socket connection.
     * This allows sending VCEvent objects to the server. In case of an IOException, it will throw a RuntimeException.
     * If any other exception occurs, it prints an error message and attempts to reset the connection with the server.
     *
     * @param event The VCEvent to be sent to the server.
     * @throws NoSuchMethodException If a method requested to invoke does not exist.
     * @throws InvocationTargetException If an exception is thrown by the method invoked.
     * @throws IllegalAccessException If this Method object is enforcing Java language access control and the underlying method is inaccessible.
     */
    @Override
    public void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Gson gson = new Gson();
        String json = gson.toJson(event);
        json+="\n";
        try {
            out.write(json.getBytes());
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException("Error while sending event to server");
        } catch (Exception e){
            System.out.println(ANSIParameters.CLEAR_SCREEN+ANSIParameters.CURSOR_HOME+
                    "Lost connection with server.\nPlease wait a few seconds and try to reconnect.");
            try {
                //if lookse connection with the server start the reconnection procedure
                view.resetConnection();
            }catch(IOException ex){
                System.out.println("Error while resetting connection");
            }
        }
    }

    /**
     * This is the main operation loop of the NetworkSocketHandler.
     * It initially establishes a socket connection with the specified host and port,
     * then continuously reads and processes incoming events from the server.
     * If it's reconnecting, it will send a "Reconnecting" message to the server, otherwise a "Connecting" message.
     * The function handles different types of events: "MVEvent", "SelectViewEvent", and "ping".
     * For each type of event, it executes the appropriate action.
     * If the connection is lost during the event handling, it attempts to reset the connection and inform the user.
     * This method continues to run and process events until the connection is lost or an unrecoverable error occurs.
     */
    @Override
    public void run()  {
        String message=null;
        String primaryType;
        Event event;
        boolean pingstarted=false;

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

                System.out.println("Reconnecting to the socket server...");
            } else{
                out.write("Connecting\n".getBytes());
                out.flush();
                out.write(
                        (new Gson().toJson(view.getConnectionInfo())+"\n").getBytes()
                );
                out.flush();
                String firstMessage = in.nextLine();
                System.out.println("Connected to the socket server!");
                receiveSelectViewEvent(firstMessage);

            }
        } catch (IOException e) {
            System.out.print("Error while connecting to server: please wait a few seconds and try again.\n> ");
            return;
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EventTypeAdapterFactory())
                .create();

        try {
            socket.setSoTimeout(10000);
        } catch (SocketException e) {
            System.out.println(ANSIParameters.CLEAR_SCREEN+ANSIParameters.CURSOR_HOME+
                    "Lost connection with server.\nPlease wait a few seconds and try to reconnect.");
            try {
                view.resetConnection();
            }catch(IOException ex){
                System.out.println("Error while resetting connection");
            }
        }
        while (true) {
            try{
                StringBuilder sb = new StringBuilder();
                int character;
                while((character = socket.getInputStream().read()) != -1){
                    if(character == '\n'){
                        break;
                    }
                    sb.append((char)character);
                }
                if(character == -1){
                    throw new IOException("Ricevuto -1 dallo stream di input");
                }
                message = sb.toString();
            }catch(IOException e){
                if(pingstarted) {
                    System.out.println(ANSIParameters.CLEAR_SCREEN + ANSIParameters.CURSOR_HOME +
                            "Lost connection with server.\nPlease wait a few seconds and try to reconnect.");
                    try {
                        socket.close();
                        view.resetConnection();
                    } catch (IOException ex) {
                        System.out.println("Error while resetting connection");
                    }
                    break;
                }
            }

            if(message != null){
                if(message.equals("ping")){
                    pingstarted=true;
                    pong();
                }else{
                    if(message.contains("deleted")){
                        System.out.println(message);
                        System.exit(0);
                    }
                    try{
                        event = gson.fromJson(message, Event.class);
                        primaryType = event.getPrimaryType();
                    }catch (JsonSyntaxException e) {
                        //Probably just received a fragment of the word "ping" due to a connection error in between the
                        //reading of the byted from the stream
                        continue;
                    }

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
    }

    /**
     * Sends a ping message to the server to check the connection.
     */
    @Override
    public void ping() {
        String pingMessage = "ping\n";
        try {
            out.write(pingMessage.getBytes());
            out.flush();
        }catch(Exception e){
            System.out.println(ANSIParameters.CLEAR_SCREEN+ANSIParameters.CURSOR_HOME+
                    "Lost connection with server.\nPlease wait a few seconds and try to reconnect.");
            try {
                view.resetConnection();
            }catch(IOException ex){
                System.out.println("Error while resetting connection");
            }
        }
    }

    /**
     * Sends a pong message to the server in response to a ping.
     */
    private void pong(){
        String pongMessage = "pong\n";
        try {
            out.write(pongMessage.getBytes());
            out.flush();
        }catch (SocketException e){
            System.out.println("Lost connection with server");
        }catch (IOException e) {
            throw new RuntimeException("Error while sending pong message to server");
        }

    }

}
