package Client;

import Client.View.CLI.View;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NetworkRMIHandler extends UnicastRemoteObject implements NetworkHandler, Remote {

    View view;
    public NetworkRMIHandler(View view) throws RemoteException {
        super();
        this.view = view;
    }
    @Override
    public void onMVEvent(MVEvent event){
        String type = event.getType();
        switch (type){
            case "ModifiedBookshelfEvent" -> {
                try {
                    Method method = view.getClass().getDeclaredMethod(type);
                    method.invoke(event);
                } catch (NoSuchMethodException e) {
                    System.err.println("Method not found");
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                } catch (IllegalAccessException e) {
                    System.err.println("Illegal access");
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void onSelectViewEvent(SelectViewEvent event){
        String type = event.getType();
        switch (type){

        }
    }
}
