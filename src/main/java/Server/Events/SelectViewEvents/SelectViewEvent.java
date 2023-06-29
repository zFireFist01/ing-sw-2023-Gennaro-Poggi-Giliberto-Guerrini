package Server.Events.SelectViewEvents;

/*
    * This class is used to send the SelectView event to the client based on the current state of the players turn
    * @Author Valentino Guerrini
 */

import Server.Events.Event;

public abstract class SelectViewEvent extends Event {
    private final String primaryType = "SelectViewEvent";


    public abstract String getType();

    public abstract String getMessage();

    @Override
    public String getPrimaryType() {
        return "SelectViewEvent";
    }
}
