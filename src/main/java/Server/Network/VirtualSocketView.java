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

    Socket socket= new Socket();
    Socket client;

    List<VCEventListener> vcEventListeners;

    public VirtualSocketView(Socket socket) {
        this.client = client;
        this.vcEventListeners= new ArrayList<>();
    }

    @Override
    public void run(){
        Scanner in;
        OutputStream out;
        try {
            in = new Scanner(socket.getInputStream());
            out = socket.getOutputStream();
        } catch (IOException e) {
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
        }
        while (true){
            String message = in.nextLine();
            manageMessage(message);
        }
    }

    private void manageMessage(String message){
        Gson gson = new Gson();
        VCEvent vcEvent = gson.fromJson(message, VCEvent.class);
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
    public void onMVEvent(MVEvent mvEvent){

    }

    @Override
    public void onSelectViewEvent(SelectViewEvent selectViewEvent){

    }

    public void sendVCEvent(VCEvent vcEvent){

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
