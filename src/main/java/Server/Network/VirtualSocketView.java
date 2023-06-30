package Server.Network;


import Server.Events.EventTypeAdapterFactory;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.LoginView;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Listeners.VCEventListener;
import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.CommonGoalCardAdapter;
import Utils.ConnectionInfo;
import com.google.gson.*;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;
import java.util.concurrent.*;

/**
 * This class represents a VirtualSocketView. It's responsible for managing the socket connection to a client,
 * receiving events from the client, and sending events to the client.
 * It is designed to be run in a separate thread to handle the client's connection.
 * This class implements the VirtualView interface.
 * @ Patrick Poggi & Valentino Guerrini & Marta Giliberto
 */
public class VirtualSocketView implements VirtualView{

    Socket socket;
    Scanner in;
    OutputStream out;
    boolean isFirstToJoin;
    boolean pongReceived;
    boolean isConnected;

    Object pongLocker = new Object();
    List<VCEventListener> vcEventListeners;

    ConnectionInfo connectionInfo;

    /**
     * The constructor for VirtualSocketView.
     * It initializes the socket with the given socket, sets the pongReceived flag to true,
     * and initializes an empty list of VCEventListener objects.
     * It then creates a Scanner to read from the socket's InputStream and a OutputStream to write to the socket.
     * If any IOException occurs while initializing the streams, it will print the stack trace and rethrow the exception as a RuntimeException.
     *
     * @param socket The socket to communicate with the client.
     * @param isFirstToJoin Flag indicating whether this client is the first to join or not.
     */
    public VirtualSocketView(Socket socket , boolean isFirstToJoin) {
        this.socket = socket;
        this.pongReceived = true;
        this.vcEventListeners= new ArrayList<>();
        try {
            in = new Scanner(socket.getInputStream());
            out = socket.getOutputStream();
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
        }
        this.isFirstToJoin = isFirstToJoin;
        this.isConnected = true;
    }


    /**
     * The run method of this class is responsible for maintaining the connection and communication with the client.
     * It starts by sending a LoginView message to the client and then enters a loop where it constantly reads incoming messages from the client.
     * If the connection is lost, a NoSuchElementException will be thrown, at which point the method simply waits and does nothing.
     */
    @Override
    public void run(){

        Gson gson = new Gson();
        String mess = gson.toJson(new LoginView(isFirstToJoin));
        mess += "\n";
        try {
            out.write(mess.getBytes());
            out.flush();
            System.out.println("Message sent"+mess);
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
        }
        while (true){
            try{
                String message;
                synchronized (this.in){
                    message = in.nextLine();
                }
                System.out.println("Message received: "+message);
                manageMessage(message);
            }catch (NoSuchElementException e) {
                //This means that we lost connection with the client
                //We just wait...
            }
        }
    }

    /**
     * This method is responsible for managing the incoming messages from the client.
     * It checks for "pong" and "ping" messages and updates the pongReceived flag accordingly.
     * If the message is not "pong" or "ping", it is a VCEvent, which it then processes.
     *
     * @param message The message received from the client.
     */
    private synchronized void manageMessage(String message){
        
        if(message.equals("pong")){
            pongReceived = true;
        } else if (message.equals("ping")) {
            return;
        } else{

            try {
                receiveVCEvent(message);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /**
     * Sends an MVEvent to the client.
     * This method is synchronized on the OutputStream to avoid concurrent modifications.
     *
     * @param mvEvent The MVEvent to be sent to the client.
     */
    @Override
    public void onMVEvent(MVEvent mvEvent){
        if(!isConnected){
            return;
        }
        synchronized (this.out){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(CommonGoalCard.class, new CommonGoalCardAdapter())
                    .create();
            String message = gson.toJson(mvEvent);
            message+="\n";
            try {
                out.write(message.getBytes());
                out.flush();
                System.out.println("Message sent: "+message);
            }catch (SocketException e){
                //We don't need to notify the controller because it will be notified by the PingManager
                // checking the pongReceived variable
            }catch (IOException e) {
                System.err.println(e.getStackTrace());
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sends a SelectViewEvent to the client.
     * This method is synchronized on the OutputStream to avoid concurrent modifications.
     *
     * @param selectViewEvent The SelectViewEvent to be sent to the client.
     */
    @Override
    public void onSelectViewEvent(SelectViewEvent selectViewEvent){
        if(!isConnected){
            return;
        }
        synchronized (this.out){
            Gson gson = new Gson();
            String message = gson.toJson(selectViewEvent);
            message+="\n";
            try {
                out.write(message.getBytes());
                out.flush();
                System.out.println("Message sent "+ message);
            }catch (SocketException e){
                //We don't need to notify the controller because it will be notified by the PingManager
                // checking the pongReceived variable
            }catch (IOException e) {
                System.err.println(e.getStackTrace());
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Receives a VCEvent in the form of a JSON string, converts it to a VCEvent object, and notifies all VCEventListeners.
     *
     * @param json The JSON string representing the VCEvent.
     * @throws NoSuchMethodException If a matching method is not found.
     * @throws InvocationTargetException If the underlying method throws an exception.
     * @throws IllegalAccessException If this Method object is enforcing Java language access control and the underlying method is inaccessible.
     */
    @Override
    public void receiveVCEvent(String json) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EventTypeAdapterFactory())
                .create();
        VCEvent vcEvent = gson.fromJson(json, VCEvent.class);
        for(VCEventListener listener: vcEventListeners){
            try {
                listener.onVCEvent(vcEvent,this);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void addVCEventListener(VCEventListener listener){
        vcEventListeners.add(listener);
    }

    @Override
    public void removeVCEventListener(VCEventListener listener){
        vcEventListeners.remove(listener);
    }

    /**
     * Sends a "ping" message to the client.
     * This method is synchronized on the OutputStream to avoid concurrent modifications.
     * If the client is not connected, it will not attempt to send the "ping" message.
     * In case of a SocketException, the error is printed but not propagated as the controller will be
     * notified of the disconnection by the PingManager checking the checkPongResponse method.
     */
    @Override
    public void ping() {
        if(!isConnected){
            return;
        }
        synchronized (this.out){
            pongReceived = false;
            String pingMessage = "ping\n";
            try {
                out.write(pingMessage.getBytes());
                out.flush();
                System.out.println("Ping sent: "+pingMessage);
                System.out.flush();
            }catch (SocketException e){
                //System.out.println("Lost connection with the client");
                System.err.println("[Socket Exception]: One Socket client has disconnected, its nickname was: "
                        +connectionInfo.getNickname());
                //We don't need to notify the controller because it will be notified by the PingManager
                // checking the checkPongResponse method. We just wait.
            }catch (IOException e) {
                System.err.println("[IOException] Lost connection with one Socket client, its nickname was: "
                        +connectionInfo.getNickname());
            }
        }
    }

    /**
     * Checks if a "pong" response was received from the client.
     * This method is synchronized on the pongLocker object to avoid concurrent modifications of the pongReceived variable.
     * If a "pong" response was not received, it assumes that the client has disconnected and returns false.
     * Otherwise, it returns true.
     *
     * @return boolean Indicates if a "pong" response was received (true) or not (false).
     */
    @Override
    public boolean checkPongResponse() {
        synchronized (pongLocker){
            if(!pongReceived){
                //System.out.println("Client disconnected");
                return false;
            }else{
                //pongReceived = false;
                System.out.println("Pong received");
                return true;
            }
        }
    }

    public void setConnectionInfo(ConnectionInfo connectionInfo){
        this.connectionInfo = connectionInfo;
    }
    @Override
    public ConnectionInfo getConnectionInfo() {
        return this.connectionInfo;
    }

    @Override
    public void setPongReceived() {
        synchronized (pongLocker){
            pongReceived = true;
        }
    }

    /**
     * Must be used only by server inside "dequeueWaitingClients" method
     * @param isFirstToJoin
     */
    @Override
    public void setIsFirstToJoin(boolean isFirstToJoin) {
        this.isFirstToJoin = isFirstToJoin;
    }

    public void setSocket(Socket socket, Scanner in){
        this.socket = socket;
        synchronized (this.in){
            this.in = in;
        }
        try {
            synchronized (this.out){
                this.out = this.socket.getOutputStream();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isFirstToJoin() {
        return isFirstToJoin;
    }

    @Override
    public void removeAllVCEventListeners() {
        synchronized (vcEventListeners){
            vcEventListeners.clear();
        }
    }

    @Override
    public List<VCEventListener> getVCEventListeners() {
        return vcEventListeners;
    }

    @Override
    public void setConnected(boolean connected) {
        this.isConnected = connected;
    }
}
