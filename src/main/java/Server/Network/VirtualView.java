package Server.Network;

import Server.Listeners.MVEventListener;
import Server.Listeners.SelectViewEventListener;
import Server.Listeners.VCEventListener;

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
}
