package Server.Network;

import Server.Controller.Controller;

import java.util.*;

/**
 * The PingManager class is responsible for managing the pinging process in a server-client model.
 * It checks whether clients are still connected by sending a "ping" and waiting for a "pong" response.
 * The PingManager runs as a separate thread and keeps track of all VirtualView instances.
 * If a "pong" response is not received from a client, it is assumed that the client has disconnected,
 * and the appropriate measures are taken (for instance, notifying the Server and Controller and updating the connection status).
 *
 * @author Patrick Poggi
 */
public class PingManager implements Runnable{
    List<VirtualView> virtualViews;
    Map<VirtualView, Controller> virtualViewControllerMap;
    Map<VirtualView, Boolean> virtualViewStatuses;
    Timer timer;
    Server server;

    Object pingManagerLocker = new Object();

    /**
     * The constructor for the PingManager class.
     *
     * @param server The server that this PingManager is associated with.
     * @param virtualViews The initial list of VirtualViews that the PingManager will manage.
     * @param virtualViewControllerMap The initial mapping of VirtualViews to their respective Controllers.
     */
    public PingManager(Server server, List<VirtualView> virtualViews, Map<VirtualView, Controller> virtualViewControllerMap){
        this.virtualViews = virtualViews;
        this.virtualViewControllerMap = virtualViewControllerMap;
        this.virtualViewStatuses = new HashMap<>();
        this.timer = new Timer();
        this.server = server;
    }

    /**
     * Adds a new VirtualView and its associated Controller to be managed by this PingManager.
     *
     * @param virtualView The VirtualView to be added.
     * @param controller The Controller associated with the VirtualView.
     */
    public void addVirtualView(VirtualView virtualView, Controller controller){

            this.virtualViews.add(virtualView);

            this.virtualViewControllerMap.put(virtualView, controller);

            this.virtualViewStatuses.put(virtualView, true);

    }

    /**
     * Removes a VirtualView and its associated Controller from the management of this PingManager.
     *
     * @param virtualView The VirtualView to be removed.
     * @param controller The Controller associated with the VirtualView.
     */
    public void removeVirtualView(VirtualView virtualView, Controller controller){

            this.virtualViews.remove(virtualView);

            this.virtualViewControllerMap.remove(virtualView, controller);

            this.virtualViewStatuses.remove(virtualView);


    }

    /**
     * The run method starts the pinging process. It creates and starts a TimerTask that sends a "ping"
     * to all VirtualView instances at fixed intervals.
     *
     * This method first pings all clients and waits for a "pong" response. After a fixed delay,
     * it checks if a response has been received from each client.
     * If a "pong" response has not been received, it is assumed that the client has disconnected.
     *
     * In case of a disconnect, this method notifies the corresponding Controller, updates the connection status,
     * and removes the disconnected VirtualView from the managed list.
     *
     * If the client reconnects and responds to the ping, the connection status is updated accordingly.
     */
    @Override
    public void run(){
        this.timer.schedule(new TimerTask() {
            @Override
            public void run() {
                boolean resp = false;
                System.out.println("Pinging all clients");
                for(VirtualView virtualView : virtualViews){
                    virtualView.ping();
                }
                System.out.println("Pinged all clients");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.err.println("Error occurred while waiting for pong response: "
                            + e.getMessage()+"\n" + e.getStackTrace());
                    throw new RuntimeException(e);
                }
                    List<VirtualView> toRemove = new ArrayList<>();
                    for(VirtualView virtualView : virtualViews){
                        if(toRemove==null || (toRemove!= null && !toRemove.contains(virtualView))){
                            resp = virtualView.checkPongResponse();
                            if(resp){
                                System.out.println("pong received from vv: "+ ((virtualView instanceof VirtualSocketView) ? "socket": "RMI"));
                            }
                            if(resp == false && (   virtualViewStatuses.get(virtualView) != null &&
                                                    virtualViewStatuses.get(virtualView) == true
                                                )
                            ){
                                System.err.println("Ciao ciao ciao");
                                virtualViewStatuses.put(virtualView, false);
                                virtualView.setConnected(false);
                                List ptr = virtualViewControllerMap.get(virtualView).playerDisconnected(virtualView);
                                if(ptr != null){
                                    toRemove.addAll(ptr);
                                }
                                server.updateConnectionStatus(virtualView.getConnectionInfo(), false);
                            }else{
                                if(resp == true &&  (   virtualViewStatuses.get(virtualView) != null &&
                                                        virtualViewStatuses.get(virtualView) == false
                                                    )
                                ){
                                    //The player lost connection and then reconnected
                                    virtualViewStatuses.put(virtualView, true);
                                }
                            }
                        }
                    }
                    if(toRemove != null){
                        for(VirtualView virtualView : toRemove) {
                            removeVirtualView(virtualView, virtualViewControllerMap.get(virtualView));
                        }
                        toRemove.clear();
                    }
                //}
            }
        }, 5000, 2000); // 5 s
        System.out.println("Started ping manager");
    }
}
