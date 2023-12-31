package Server.Network;

import Client.NetworkRMIHandler;
import Client.View.RemoteNetworkHandler;
import Server.Events.EventTypeAdapterFactory;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.LoginView;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Listeners.VCEventListener;
import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.CommonGoalCardAdapter;
import Utils.ConnectionInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.InvocationTargetException;
import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * This class is used to handle the network connection between the client and the server,
 * using RMI protocol. It is seen as a View from the controller and the model, it's seen as a VCEventListener from
 * the client. It sees the client as a NetworkRMIHandler, thus a MVEventListener and a SelectViewEventListener.
 *
 * @see VirtualView
 * @see NetworkRMIHandler
 * @author patrickpoggi
 */
public class VirtualRMIView extends UnicastRemoteObject implements VirtualView, RemoteVirtualView {
    RemoteNetworkHandler client;
    boolean isFirsToJoin;
    boolean pongReceived;

    boolean isConnected;
    Object pongLocker = new Object();
    ConnectionInfo connectionInfo;

    /**
     * This list contains all the VCEventListeners that are listening to this VirtualRMIView, which in fact is the only
     * controller of the match this view belongs to.
     */
    List<VCEventListener> vcEventListeners;

    public VirtualRMIView(RemoteNetworkHandler networkHandler, boolean isFirsToJoin) throws RemoteException {
        super();
        client = networkHandler;
        vcEventListeners = new ArrayList<>();
        this.isFirsToJoin = isFirsToJoin;
        this.pongReceived = true;
        this.isConnected = true;
    }

    @Override
    public void run() {
        try {
            client.receiveSelectViewEvent(
                    new Gson().toJson(
                            new LoginView(isFirsToJoin)
                    )
            );
        } catch (RemoteException e) {
            //We could say that the method invocation went wrong and so may be that the client lost connection
            System.err.println("VirtualRMIView: Unable to contact client");
        }
    }



    @Override
    /**
     * This method is used from the client to send a VCEvent to the server.
     * @param json the json representation of the VCEvent.
     * @throws NoSuchMethodException when one of the listeners throws it.
     * @throws InvocationTargetException when one of the listeners throws it.
     * @throws IllegalAccessException  when the event we receive is not a VCEvent.
     */
    public void receiveVCEvent(String json) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, RemoteException{
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new EventTypeAdapterFactory())
                .create();
        VCEvent vcEvent = gson.fromJson(json,VCEvent.class);
        List<VCEventListener> localVCEventListeners;
        synchronized (vcEventListeners){
            localVCEventListeners = new ArrayList<>(vcEventListeners);
        }
        for(VCEventListener listener : localVCEventListeners){
            listener.onVCEvent(vcEvent,this);
        }

    }

    /**
     * This method is called from the model when its state changes. It sends the MVEvent to the client in
     * the form of a json string, after serializing it through Gson.
     * @param event the MVEvent to be sent to the client.
     * @see Gson
     */
    public void onMVEvent(MVEvent event) {
        if(!isConnected){
            return;
        }
        synchronized (client){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(CommonGoalCard.class, new CommonGoalCardAdapter())
                    .create();
            String json = gson.toJson(event);
            try {
                client.receiveMVEvent(json);
            } catch (RemoteException e) {
                //We could say that the method invocation went wrong and so may be that the client lost connection
                System.err.println("[Virtual RMI View.onMVEvent]: Couldn't send. The nickname was: " + connectionInfo.getNickname());
            }
        }
    }

    /**
     * This method is called from the controller when it needs to change the view of the client.
     * It sends the SelectViewEvent to the client in the form of a json string, after serializing it through Gson.
     * @param event the SelectViewEvent to be sent to the client.
     * @see Gson
     */
    public void onSelectViewEvent(SelectViewEvent event) {
        if(!isConnected){
            return;
        }
        synchronized (client){
            Gson gson = new Gson();
            String json = gson.toJson(event);
            json += "\n";
            try {
                client.receiveSelectViewEvent(json);
            } catch (RemoteException e) {
                //We could say that the method invocation went wrong and so may be that the client lost connection
                System.err.println("[Virtual RMI View.onSelectViewEvent]: Couldn't send. The nickname was: " + connectionInfo.getNickname());
            }
        }
    }

    @Override
    public void addVCEventListener(VCEventListener listener){
        synchronized(vcEventListeners){
            vcEventListeners.add(listener);
        }
    }

    @Override
    public void removeVCEventListener(VCEventListener listener){
        synchronized(vcEventListeners){
            vcEventListeners.remove(listener);
        }
    }

    @Override
    public void ping() {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Void> future = executor.submit(() -> {
            if(!pongReceived){
                return null;
            }
            try{
                synchronized (pongLocker){
                    pongReceived = false;
                }
                System.out.println("Pinging client");
                synchronized (client){
                    client.pong();
                }
                synchronized (pongLocker){
                    pongReceived = true;
                }
                System.out.println("Pong received");
            } catch (ConnectException e){
                System.err.println("[Virtual RMI View.ping]: One RMI client has disconnected. The nickname of that client was: " + connectionInfo.getNickname());
            }catch (RemoteException e){
                System.err.println("[Virtual RMI View.ping]: Lost connection with one RMI client");
            }
            return null;
        });
        try {
            future.get(3, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            System.out.println("La chiamata remota ha superato il timeout di 3 secondi.");
            pongReceived = false;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    @Override
    public boolean checkPongResponse() {
       synchronized (pongLocker){
           if(!pongReceived){
               System.err.println("[Virtual RMI View.checkPongResponse()]: Client disconnected");
               return false;
           }else{
               return true;
           }
       }
    }

    @Override
    public void setPongReceived(){
        synchronized (pongLocker){
            pongReceived = true;
        }
    }

    /**
     * Must be used only by server inside "dequeueWaitingClients" method
     * @param isFirstToJoin
     */
    @Override
    public void setIsFirstToJoin(boolean isFirstToJoin) {
        this.isFirsToJoin = isFirstToJoin;
    }

    public ConnectionInfo getConnectionInfo() {
        return connectionInfo;
    }

    public void setConnectionInfo(ConnectionInfo connectionInfo) {
        this.connectionInfo = connectionInfo;
    }

    public void setClient(RemoteNetworkHandler client) {
        synchronized (client){
            this.client = client;
        }
    }

    /**
     * @return true iff this client is the first to join the game
     */
    public boolean isFirsToJoin() {
        return isFirsToJoin;
    }

    @Override
    public void removeAllVCEventListeners() {
        synchronized (vcEventListeners){
            vcEventListeners.clear();
        }
    }

    @Override
    public List<VCEventListener> getVCEventListeners() {
        return vcEventListeners;
    }

    @Override
    public void pong() {
        System.out.println("Ponged RMI client");
        return;
    }

    public void setConnected(boolean connected) {
        this.isConnected = connected;
    }
}
