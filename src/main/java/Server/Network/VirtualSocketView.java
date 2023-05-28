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

public class VirtualSocketView implements VirtualView{

    Socket socket;
    Scanner in;
    OutputStream out;
    boolean isFirstToJoin;
    boolean pongReceived;

    Object pongLocker = new Object();
    List<VCEventListener> vcEventListeners;

    ConnectionInfo connectionInfo;

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
    }

    @Override
    public void run(){

        String welcomeMessage = "Benvenuto nel server!\n";
        try {
            out.write(welcomeMessage.getBytes());
            out.flush();
            System.out.println("Messaggio di benvenuto inviato");
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
        }

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

    private synchronized void manageMessage(String message){
        /*Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EventTypeAdapterFactory())
                .create();
        VCEvent vcEvent = gson.fromJson(message, VCEvent.class);
        sendVCEvent(vcEvent);*/
        if(message.equals("pong")){
            pongReceived = true;
            //message = message.replace("pong", "");
        }else{

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
    @Override
    public void onMVEvent(MVEvent mvEvent){
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
                //System.out.println("Lost connection with the client");
                //We don't need to notify the controller because it will be notified by the PingManager
                // checking the pongReceived variable
            }catch (IOException e) {
                System.err.println(e.getStackTrace());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void onSelectViewEvent(SelectViewEvent selectViewEvent){
        synchronized (this.out){
            Gson gson = new Gson();
            String message = gson.toJson(selectViewEvent);
            message+="\n";
            try {
                out.write(message.getBytes());
                out.flush();
                System.out.println("Message sent "+ message);
            }catch (SocketException e){
                //System.out.println("Lost connection with the client");
                //We don't need to notify the controller because it will be notified by the PingManager
                // checking the pongReceived variable
            }catch (IOException e) {
                System.err.println(e.getStackTrace());
                throw new RuntimeException(e);
            }
        }

    }

    /*
    public void sendVCEvent(VCEvent vcEvent){
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
    }*/

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

    @Override
    public void ping() {
        synchronized (this.out){
            pongReceived = false;
            String pingMessage = "ping\n";
            try {
                out.write(pingMessage.getBytes());
                out.flush();
                System.out.println("Ping sent: "+pingMessage);
                System.out.flush();
            }catch (SocketException e){
                System.out.println("Lost connection with the client");
                //We don't need to notify the controller because it will be notified by the PingManager
                // checking the checkPongResponse method. We just wait.
                //throw new RuntimeException(e);
            }catch (IOException e) {
                System.err.println(e.getStackTrace());
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public boolean checkPongResponse() {
        synchronized (pongLocker){
            if(!pongReceived){
                System.out.println("Client disconnected");
                return false;
            /*try {
                socket.close();
            } catch (IOException e) {
                System.err.println(e.getStackTrace());
                throw new RuntimeException(e);
            }*/
            }else{
                pongReceived = false;
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

}
