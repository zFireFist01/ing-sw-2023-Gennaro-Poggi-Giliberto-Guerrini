package Client.View;

import Server.Listeners.MVEventListener;
import Server.Listeners.SelectViewEventListener;
import Server.Listeners.VCEventListener;
import Utils.ConnectionInfo;

public interface View extends SelectViewEventListener, Runnable, MVEventListener {
    boolean isReconnecting();

    public ConnectionInfo getConnectionInfo();

    public void resetConnection();

}
