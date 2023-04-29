package Server.Network;

import Server.Listeners.MVEventListener;
import Server.Listeners.SelectViewEventListener;

public interface VirtualView extends Runnable, MVEventListener, SelectViewEventListener {

    public void addVCEventListener(VCEventListener listener);
    public void removeVCEventListener(VCEventListener listener);
}
