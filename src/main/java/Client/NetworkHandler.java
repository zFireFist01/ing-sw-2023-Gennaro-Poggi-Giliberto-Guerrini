package Client;

import Server.Events.VCEvents.VCEvent;
import Server.Listeners.VCEventListener;
import Server.Network.VirtualView;
import Utils.ConnectionInfo;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * The NetworkHandler interface defines the contract for handling network events.
 * It extends the VCEventListener interface and implements the Runnable interface.
 *
 * @author Patrick Poggi
 */
public interface NetworkHandler extends VCEventListener, Runnable{

    /**
     * Receives the model-view event in JSON format.
     *
     * @param json The JSON representation of the model-view event.
     * @throws RemoteException If a remote exception occurs during the method invocation.
     */
    public void receiveMVEvent(String json) throws RemoteException;

    /**
     * Receives the select view event in JSON format.
     *
     * @param json The JSON representation of the select view event.
     * @throws RemoteException If a remote exception occurs during the method invocation.
     */
    public void receiveSelectViewEvent(String json) throws RemoteException;

    /**
     * Overrides the onVCEvent method from the VCEventListener interface.
     * This method handles the VCEvent with the associated VirtualView.
     *
     * @param event The VCEvent to handle.
     * @param view  The associated VirtualView.
     * @throws NoSuchMethodException      If the method called by the VCEvent does not exist.
     * @throws InvocationTargetException If an exception occurs during the method invocation.
     * @throws IllegalAccessException    If the method cannot be accessed due to restrictions.
     */
    @Override
    void onVCEvent(VCEvent event, VirtualView view) throws NoSuchMethodException,
            InvocationTargetException, IllegalAccessException;

    /**
     * Overrides the onVCEvent method from the VCEventListener interface.
     * This method handles the VCEvent without any associated VirtualView.
     *
     * @param event The VCEvent to handle.
     * @throws NoSuchMethodException      If the method called by the VCEvent does not exist.
     * @throws InvocationTargetException If an exception occurs during the method invocation.
     * @throws IllegalAccessException    If the method cannot be accessed due to restrictions.
     */
    @Override
    void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException;

    /**
     * Sends a ping signal.
     * This method is used to check the network connectivity or responsiveness.
     */
    @Override
    void run();


    void ping();
}
