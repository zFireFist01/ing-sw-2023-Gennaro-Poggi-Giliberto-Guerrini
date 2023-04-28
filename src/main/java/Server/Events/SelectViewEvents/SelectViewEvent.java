package Server.Events.SelectViewEvents;

/*
    * This class is used to send the SelectView event to the client based on the current state of the players turn
    * @Author Valentino Guerrini
 */

public class SelectViewEvent {

    private final ViewType viewType;

    /**
     * Constructor for the SelectViewEvent
     * @param viewType the type of view to send to the client
     */

    public SelectViewEvent(ViewType viewType) {
        this.viewType = viewType;
    }

    /**
     * Getter for the type of view
     * @return the type of view
     */

    public String getType(){
        return this.viewType.getType();
    }

    /**
     * Getter for the message to show in the view
     * @return the message of the view
     */

    public String getMessage(){
        return this.viewType.getMessage();
    }

    /**
     * Getter for the chatOn boolean
     * @return the chatOn boolean
     */
    public Boolean getChatOn(){
        return this.viewType.getChatOn();
    }


}
