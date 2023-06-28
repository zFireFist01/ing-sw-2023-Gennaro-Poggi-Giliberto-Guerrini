package Server.Network;

import Server.Controller.Controller;

import java.util.*;

public class PingManager implements Runnable{
    List<VirtualView> virtualViews;
    Map<VirtualView, Controller> virtualViewControllerMap;
    Map<VirtualView, Boolean> virtualViewStatuses;
    Timer timer;
    Server server;

    Object pingManagerLocker = new Object();

    public PingManager(Server server, List<VirtualView> virtualViews, Map<VirtualView, Controller> virtualViewControllerMap){
        this.virtualViews = virtualViews;
        this.virtualViewControllerMap = virtualViewControllerMap;
        this.virtualViewStatuses = new HashMap<>();
        this.timer = new Timer();
        this.server = server;
    }

    public void addVirtualView(VirtualView virtualView, Controller controller){
        //synchronized (server) {
        /*synchronized (virtualViews){
            this.virtualViews.add(virtualView);
        }*/
            this.virtualViews.add(virtualView);
        /*synchronized (virtualViewControllerMap){
            this.virtualViewControllerMap.put(virtualView, controller);
        }*/
            this.virtualViewControllerMap.put(virtualView, controller);
        /*synchronized (virtualViewStatuses){
            this.virtualViewStatuses.put(virtualView, true);
        }*/
            this.virtualViewStatuses.put(virtualView, true);
        //}
    }
    public void removeVirtualView(VirtualView virtualView, Controller controller){
        //synchronized (server) {
        /*synchronized (virtualViews){
            this.virtualViews.remove(virtualView);
        }*/
            this.virtualViews.remove(virtualView);
        /*synchronized (virtualViewControllerMap){
            this.virtualViewControllerMap.remove(virtualView, controller);
        }*/
            this.virtualViewControllerMap.remove(virtualView, controller);
        /*synchronized (virtualViewStatuses){
            this.virtualViewStatuses.remove(virtualView);
        }*/
            this.virtualViewStatuses.remove(virtualView);
        //}

    }
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
                    //Thread.wait(2000); // 2 seconds
                } catch (InterruptedException e) {
                    System.err.println("Error occurred while waiting for pong response: "
                            + e.getMessage()+"\n" + e.getStackTrace());
                    throw new RuntimeException(e);
                }
                //synchronized (this){
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
                                //The player lost connection
                                System.err.println("Ciao ciao ciao");
                                virtualViewStatuses.put(virtualView, false);
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
        }, 5000, 5000); // 1 s
        System.out.println("Started ping manager");
    }
}
