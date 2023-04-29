package Client.View.CLI;

import Server.Listeners.MVEventListener;
import Server.Listeners.SelectViewEventListener;
import Server.Listeners.VCEventListener;

public interface View extends SelectViewEventListener, Runnable, MVEventListener {
}
