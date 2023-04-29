package Server.Events.SelectViewEvents;

/**
 * This class is used to send the view type to the client
 * @see SelectViewEvent
 * @Author ValentinoGuerrini
 */

public abstract class ViewType {




    /**
     * Getter for the message to show in the view
     * @return the message of the view
     */

    public abstract String getMessage();

    /**
     * Getter for the type of view
     * @return the type of view
     */

    public abstract String getType();
}
