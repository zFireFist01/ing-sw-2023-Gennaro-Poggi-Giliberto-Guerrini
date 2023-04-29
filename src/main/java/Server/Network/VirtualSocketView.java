package Server.Network;

import Client.NetworkHandler;
import Server.Events.SelectViewEvents.SelectViewEvent;

import java.net.Socket;

public class VirtualSocketView implements VirtualView{

    Socket socket= new Socket();
    NetworkHandler client;
    public VirtualSocketView(NetworkHandler networkHandler) {
        this.client = networkHandler;
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
