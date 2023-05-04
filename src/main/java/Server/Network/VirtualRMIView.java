package Server.Network;

import Client.NetworkRMIHandler;
import Server.Events.Event;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.LoginView;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Listeners.VCEventListener;
import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.CommonGoalCardAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to handle the network connection between the client and the server,
 * using RMI protocol. It is seen as a View from the controller and the model, it's seen as a VCEventListener from
 * the client. It sees the client as a NetworkRMIHandler, thus a MVEventListener and a SelectViewEventListener.
 *
 * @see VirtualView
 * @see NetworkRMIHandler
 * @author patrickpoggi
 */
public class VirtualRMIView extends UnicastRemoteObject implements VirtualView, Remote {
    NetworkRMIHandler client;

    /**
     * This list contains all the VCEventListeners that are listening to this VirtualRMIView, which in fact is the only
     * controller of the match this view belongs to.
     */
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
        try {
            client.sendSelectViewEvent(
                    new Gson().toJson(
                            new LoginView()
                    )
            );
        } catch (RemoteException e) {
            //We could say that the method invocation went wrong and so may be that the client lost connection
            System.err.println(e.getStackTrace());
            throw new RuntimeException(e);
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

    /**
     * This method is used from the client to send a VCEvent to the server.
     * @param json the json representation of the VCEvent.
     * @throws NoSuchMethodException when one of the listeners throws it.
     * @throws InvocationTargetException when one of the listeners throws it.
     * @throws IllegalAccessException  when the event we receive is not a VCEvent.
     */
    public void sendVCEvent(String json) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Gson gson = new Gson();
        Event event = gson.fromJson(json,Event.class);
        switch (event.getPrimaryType()){
            case "VCEvent":
                event = gson.fromJson(json,VCEvent.class);
                for(VCEventListener listener : vcEventListeners){
                    listener.onVCEvent((VCEvent)event,this);
                }
                break;
            case "MVEvent":
                throw new IllegalAccessException("MVEvent not allowed in this context");
            case "SelectViewEvent":
                throw new IllegalAccessException("SelectViewEvent not allowed in this context");
        }
    }

    /**
     * This method is called from the model when its state changes. It sends the MVEvent to the client in
     * the form of a json string, after serializing it through Gson.
     * @param event the MVEvent to be sent to the client.
     * @see Gson
     */
    public void onMVEvent(MVEvent event) {
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(CommonGoalCard.class, new CommonGoalCardAdapter())
                .create();
        String json = gson.toJson(event);
        try {
            client.sendMVEvent(json);
        } catch (RemoteException e) {
            //We could say that the method invocation went wrong and so may be that the client lost connection
            System.err.println(e.getStackTrace());
        }
    }

    /**
     * This method is called from the controller when it needs to change the view of the client.
     * It sends the SelectViewEvent to the client in the form of a json string, after serializing it through Gson.
     * @param event the SelectViewEvent to be sent to the client.
     * @see Gson
     */
    public void onSelectViewEvent(SelectViewEvent event) {
        Gson gson = new Gson();
        String json = gson.toJson(event);
        try {
            client.sendSelectViewEvent(json);
        } catch (RemoteException e) {
            //We could say that the method invocation went wrong and so may be that the client lost connection
            System.err.println(e.getStackTrace());
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
