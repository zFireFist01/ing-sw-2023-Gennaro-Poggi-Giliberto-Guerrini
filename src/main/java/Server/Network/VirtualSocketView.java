package Server.Network;


import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Listeners.VCEventListener;
import com.google.gson.Gson;

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

    List<VCEventListener> vcEventListeners;

    public VirtualSocketView(Socket socket) {
        this.socket = socket;
        this.vcEventListeners= new ArrayList<>();
        try {
            in = new Scanner(socket.getInputStream());
            out = socket.getOutputStream();
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(){
        while (true){
            String message = in.nextLine();
            manageMessage(message);
        }
    }

    private void manageMessage(String message){
        Gson gson = new Gson();
        VCEvent vcEvent = gson.fromJson(message, VCEvent.class);
        sendVCEvent(vcEvent);
    }
    @Override
    public void onMVEvent(MVEvent mvEvent){
        Gson gson = new Gson();
        String message = gson.toJson(mvEvent);
        try {
            out.write(message.getBytes());
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onSelectViewEvent(SelectViewEvent selectViewEvent){
        Gson gson = new Gson();
        String message = gson.toJson(selectViewEvent);
        try {
            out.write(message.getBytes());
            out.flush();
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
        }

    }


    public void sendVCEvent(VCEvent vcEvent){
        for(VCEventListener listener: vcEventListeners){
            try {
                listener.onVCEvent(vcEvent);
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

}
