package Server.Network;

import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Listeners.MVEventListener;
import Server.Listeners.SelectViewEventListener;
import Server.Listeners.VCEventListener;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * This interface is used to generalize the VirtualView
 * @author patrickpoggi, Marta Giliberto, Paolo Gennaro
 */
public interface VirtualView extends Runnable, MVEventListener, SelectViewEventListener {

    /**
     * This method is used to add a VCEventListener to the list of VCEventListeners
     * @param listener the listener to add
     */
    public void addVCEventListener(VCEventListener listener);

    /**
     * This method is used to remove a VCEventListener from the list of VCEventListeners
     * @param listener
     */
    public void removeVCEventListener(VCEventListener listener);

    public void receiveVCEvent(String json) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException, RemoteException;

    @Override
    void onMVEvent(MVEvent event);

    @Override
    void onSelectViewEvent(SelectViewEvent event);

    @Override
    void run();

    void ping();

    boolean checkPongResponse();
}
