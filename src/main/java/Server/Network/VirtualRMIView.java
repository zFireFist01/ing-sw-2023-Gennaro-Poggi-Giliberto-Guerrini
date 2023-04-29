package Server.Network;

import Client.NetworkHandler;
import Client.NetworkRMIHandler;
import Server.Controller.Controller;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.LoginView;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.LoginEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Listeners.MVEventListener;
import Server.Listeners.SelectViewEventListener;
import Server.Listeners.VCEventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static java.lang.System.currentTimeMillis;

public class VirtualRMIView extends UnicastRemoteObject implements VirtualView, Remote {
    NetworkRMIHandler client;
    List<VCEventListener> vcEventListeners;
    public VirtualRMIView(NetworkRMIHandler networkHandler) throws RemoteException {
        super();
        client = networkHandler;
        vcEventListeners = new ArrayList<>();
    }

    @Override
    public void run() {
        /*boolean done = false;
        while(!done){
            try{
                client.onSelectViewEvent(new SelectViewEvent(new LoginView()));
            }catch (RemoteException e){
                //We could say that the method invocation went wrong and so may be that the client lost connection
                System.err.println(e.getStackTrace());
            }
        }*/
        client.onSelectViewEvent(new SelectViewEvent(new LoginView()));
    }

    /*
    @Override
    public void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String type = event.getMethodName();
        Method method = this.getClass().getDeclaredMethod(type);
        method.invoke(...);
    }
    */

    public void sendVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for(VCEventListener listener : vcEventListeners){
            listener.onVCEvent(event,this);
        }
    }


    public void onMVEvent(MVEvent event) {
        /*boolean done = false;
        while(!done){
            try{
                client.onMVEvent(event);
                done = true;
            }catch (RemoteException e){
                //We could say that the method invocation went wrong and so may be that the client lost connection
                System.err.println(e.getStackTrace());
            }
        }*/
        Gson gson = new Gson();
        client.onMVEvent(event);
    }


    public void onSelectViewEvent(SelectViewEvent event) {
        /*boolean done = false;
        while(!done){
            try{
                client.onSelectViewEvent(event);
                done = true;
            }catch (RemoteException e){
                //We could say that the method invocation went wrong and so may be that the client lost connection
                System.err.println(e.getStackTrace());
            }
        }*/
        client.onSelectViewEvent(event);
    }

    @Override
    public void addVCEventListener(VCEventListener listener){
        vcEventListeners.add(listener);
    }

    public void removeVCEventListener(VCEventListener listener){
        vcEventListeners.remove(listener);
    }

}
