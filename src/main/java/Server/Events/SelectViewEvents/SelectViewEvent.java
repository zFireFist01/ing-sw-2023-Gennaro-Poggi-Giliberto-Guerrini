package Server.Events.SelectViewEvents;

/*
    * This class is used to send the SelectView event to the client based on the current state of the players turn
    * @Author Valentino Guerrini
 */

import Server.Events.Event;

public abstract class SelectViewEvent extends Event {
    private final String primaryType = "SelectViewEvent";

    //private final String message;



    /**
     * Constructor for the SelectViewEvent
     * @param message the message to show in the view
     */




    /**
     * Constructor for the SelectViewEvent
     * @param viewType the type of view to send to the client
     */



    /**
     * Getter for the type of view
     * @return the type of view
     */


    public abstract String getType();





    /**
     * Getter for the message to show in the view
     * @return the message of the view
     */

    public abstract String getMessage();


    @Override
    public String getPrimaryType() {
        return "SelectViewEvent";
    }
}
