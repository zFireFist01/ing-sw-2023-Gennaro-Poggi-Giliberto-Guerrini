package Server.Network;

import Server.Controller.Controller;

import java.util.*;

public class PingManager implements Runnable{
    List<VirtualView> virtualViews;
    Map<VirtualView, Controller> virtualViewControllerMap;
    Map<VirtualView, Boolean> virtualViewStatuses;
    Timer timer;
    public PingManager(List<VirtualView> virtualViews, Map<VirtualView, Controller> virtualViewControllerMap){
        this.virtualViews = virtualViews;
        this.virtualViewControllerMap = virtualViewControllerMap;
        this.virtualViewStatuses = new HashMap<>();
        this.timer = new Timer();
    }

    public void addVirtualView(VirtualView virtualView, Controller controller){
        this.virtualViews.add(virtualView);
        this.virtualViewControllerMap.put(virtualView, controller);
        this.virtualViewStatuses.put(virtualView, true);
    }
    public void removeVirtualView(VirtualView virtualView, Controller controller){
        this.virtualViews.remove(virtualView);
        this.virtualViewControllerMap.remove(virtualView, controller);
        this.virtualViewStatuses.remove(virtualView);
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
                for(VirtualView virtualView : virtualViews){
                    resp = virtualView.checkPongResponse();
                    if(resp == false){
                        //The player lost connection
                        virtualViewStatuses.put(virtualView, false);
                        virtualViewControllerMap.get(virtualView).playerDisconnected(virtualView);
                    }else{
                        if(virtualViewStatuses.get(virtualView) == false){
                            //The player lost connection and then reconnected
                            virtualViewStatuses.put(virtualView, true);
                            virtualViewControllerMap.get(virtualView).playerConnected(virtualView);

                        }
                    }
                }
            }
        }, 5000, 1000); // 1 s
        System.out.println("Started ping manager");
    }
}
