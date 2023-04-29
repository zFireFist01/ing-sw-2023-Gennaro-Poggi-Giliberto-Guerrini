package Server.Listeners;

import Server.Events.SelectViewEvents.SelectViewEvent;

public interface MVEventListener {
    public void onMVEvent(MVEvent event);
}
