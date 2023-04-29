package Server.Network;

import Client.NetworkHandler;
import Client.NetworkSocketHandler;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Listeners.VCEventListener;

import java.net.Socket;
import java.util.List;

public class VirtualSocketView implements VirtualView{

    Socket socket= new Socket();
    NetworkSocketHandler client;

    List<VCEventListener> vcEventListeners;

    public VirtualSocketView(NetworkSocketHandler networkSocketHandler) {
        this.client = networkSocketHandler;
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
}
