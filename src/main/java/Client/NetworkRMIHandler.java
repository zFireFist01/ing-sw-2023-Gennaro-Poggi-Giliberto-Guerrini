package Client;

import Server.Events.SelectViewEvents.SelectViewEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NetworkRMIHandler extends UnicastRemoteObject implements NetworkHandler, Remote {
    @Override
    public void onMVEvent(MVEvent event) throws RemoteException {
        String type = event.getType();
        switch (type){

        }
    }

    @Override
    public void onSelectViewEvent(SelectViewEvent event) throws RemoteException{

    }
}
