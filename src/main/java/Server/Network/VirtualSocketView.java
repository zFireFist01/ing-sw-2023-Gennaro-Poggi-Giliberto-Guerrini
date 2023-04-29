package Server.Network;


import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Listeners.VCEventListener;

import java.net.Socket;
import java.util.List;

public class VirtualSocketView implements VirtualView{

    Socket socket= new Socket();
    Socket client;

    List<VCEventListener> vcEventListeners;

    public VirtualSocketView(Socket client) {
        this.client = client;
    }

    @Override
    public void run(){

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
    public void addVCEventListener(VCEventListener listener) {

    }

    @Override
    public void removeVCEventListener(VCEventListener listener) {

    }
}
