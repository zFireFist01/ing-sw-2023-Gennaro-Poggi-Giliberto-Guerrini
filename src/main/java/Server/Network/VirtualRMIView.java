package Server.Network;

import Client.NetworkHandler;
import Client.NetworkRMIHandler;
import Server.Events.SelectViewEvents.LoginView;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Listeners.MVEventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import static java.lang.System.currentTimeMillis;

public class VirtualRMIView extends UnicastRemoteObject implements VirtualView, Remote {
    NetworkRMIHandler client;
    public VirtualRMIView(NetworkRMIHandler networkHandler) throws RemoteException {
        super();
        client = networkHandler;
    }

    @Override
    public void run() {
        boolean done = false;
        while(!done){
            try{
                /*
                long timeSeed = currentTimeMillis();
                Registry registry = LocateRegistry.getRegistry();
                try{
                    registry.bind("VirualView"+timeSeed, this);
                }catch (AlreadyBoundException e){
                    System.err.println(e.getMessage());
                }
                */
                onSelectViewEvent(new SelectViewEvent(new LoginView()));
            }catch (RemoteException e){
                System.err.println(e.getStackTrace());
            }
        }
    }

    /*
    @Override
    public void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String type = event.getMethodName();
        Method method = this.getClass().getDeclaredMethod(type);
        method.invoke(...);
    }
    */

    @Override
    public void onMVEvent(MVEvent event) {
        boolean done = false;
        while(!done){
            try{
                client.onMVEvent(event);
                done = true;
            }catch (RemoteException e){
                //We could say that the method invocation went wrong and so may be that the client lost connection
                System.err.println(e.getStackTrace());
            }
        }
    }

    @Override
    public void onSelectViewEvent(SelectViewEvent event) {
        boolean done = false;
        while(!done){
            try{
                client.onSelectViewEvent(event);
            }catch (RemoteException e){
                //We could say that the method invocation went wrong and so may be that the client lost connection
                System.err.println(e.getStackTrace());
            }
        }
    }
}
