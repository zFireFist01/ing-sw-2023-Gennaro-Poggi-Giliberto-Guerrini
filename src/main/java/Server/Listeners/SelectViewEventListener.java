package Server.Listeners;

import Server.Events.SelectViewEvents.SelectViewEvent;

import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * The {@code SelectViewEventListener} interface represents an event listener for SelectViewEvents.
 */
public interface SelectViewEventListener {
    public void onSelectViewEvent(SelectViewEvent event);
}
