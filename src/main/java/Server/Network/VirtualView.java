package Server.Network;

import Client.NetworkHandler;
import Server.Events.SelectViewEvents.SelectViewEvent;
import Server.Events.VCEvents.VCEvent;
import Server.Listeners.MVEventListener;
import Server.Listeners.SelectViewEventListener;
import Server.Listeners.VCEventListener;

import javax.swing.text.View;
import java.lang.reflect.InvocationTargetException;

public interface VirtualView extends Runnable, MVEventListener, SelectViewEventListener {


}
