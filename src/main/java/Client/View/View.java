package Client.View;

import Server.Listeners.MVEventListener;
import Server.Listeners.SelectViewEventListener;
import Server.Listeners.VCEventListener;
import Utils.ConnectionInfo;

import java.io.IOException;

/**
 * This interface is used to represent the View
 * @author Patrick Poggi
 */
public interface View extends SelectViewEventListener, Runnable, MVEventListener {
    boolean isReconnecting();

    public ConnectionInfo getConnectionInfo();

    public void resetConnection() throws IOException;

}
