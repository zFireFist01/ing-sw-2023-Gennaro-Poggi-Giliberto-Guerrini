package Server.Network;


import Server.Events.EventTypeAdapterFactory;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.LoginView;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Listeners.VCEventListener;
import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.CommonGoalCardAdapter;
import com.google.gson.*;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VirtualSocketView implements VirtualView{

    Socket socket;
    Scanner in;
    OutputStream out;
    boolean isFirstToJoin;
    boolean pongReceived;

    List<VCEventListener> vcEventListeners;

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
            String message = in.nextLine();
            System.out.println("Message received: "+message);
            manageMessage(message);
        }
    }

    private void manageMessage(String message){
        /*Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EventTypeAdapterFactory())
                .create();
        VCEvent vcEvent = gson.fromJson(message, VCEvent.class);
        sendVCEvent(vcEvent);*/
        if(message.contains("pong")){
            pongReceived = true;
            message = message.replace("pong", "");

        }
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
    @Override
    public void onMVEvent(MVEvent mvEvent){
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(CommonGoalCard.class, new CommonGoalCardAdapter())
                .create();
        String message = gson.toJson(mvEvent);
        message+="\n";
        try {
            out.write(message.getBytes());
            out.flush();
            System.out.println("Message sent: "+message);
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onSelectViewEvent(SelectViewEvent selectViewEvent){
        Gson gson = new Gson();
        String message = gson.toJson(selectViewEvent);
        message+="\n";
        try {
            out.write(message.getBytes());
            out.flush();
            System.out.println("Message sent "+ message);
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
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
        pongReceived = false;
        String pingMessage = "ping";
        try {
            out.write(pingMessage.getBytes());
            out.flush();
            System.out.println("Ping sent: "+pingMessage);
            System.out.flush();
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean checkPongResponse() {
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
