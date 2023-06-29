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
        } catch (Exception e){
            System.out.println(ANSIParameters.CLEAR_SCREEN+ANSIParameters.CURSOR_HOME+
                    "Lost connection with server.\nPlease wait a few seconds and try to reconnect.");
            try {
                view.resetConnection();
            }catch(IOException ex){
                System.out.println("Error while resetting connection");
            }
        }
    }

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
                //In queso caso non devo aspettare il messaggio di benvenuto
                System.out.println("Reconnecting to the socket server...");
            } else{
                out.write("Connecting\n".getBytes());
                out.flush();
                out.write(
                        (new Gson().toJson(view.getConnectionInfo())+"\n").getBytes()
                );
                out.flush();
                String firstMessage = in.nextLine(); //Devo prima aspettare il messaggio di benvenuto
                System.out.println("Connected to the socket server!");
                receiveSelectViewEvent(firstMessage);
                //Potrebbe non essere il messaggio di benvenuto, ma un evento per dirmi che devo aspettare di essere
                // gestito perché il match opener non ha ancora deciso con quante persone giocare
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
                //message = in.nextLine();
                //message = (socket.getInputStream()).readNBytes(8192).toString();
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

    @Override
    public void ping() {
        String pingMessage = "ping\n";
        try {
            out.write(pingMessage.getBytes());
            out.flush();
            //System.out.println("Message sent: " + pingMessage);
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
