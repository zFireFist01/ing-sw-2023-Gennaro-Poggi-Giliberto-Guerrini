package Server.Events.SelectViewEvents;

/**
 * Class representing an EndedMatchView, a type of SelectViewEvent.
 * This view is shown when the match is over and the scoreboard is displayed.
 * It extends the SelectViewEvent class.
 * @author Marta Giliberto
 * @see SelectViewEvent
 */

public class EndedMatchView extends SelectViewEvent{
    private final String secondaryType = "EndedMatchView";
    private final String message;

    /**
     * Default constructor for the EndedMatchView.
     * Initializes the view with a default message of "The match is ended".
     */
    public EndedMatchView(){
        this.message = "The match is ended";
    }

    /**
     * Constructor for the EndedMatchView with a custom message.
     * Initializes the view with the provided message.
     *
     * @param message A custom message to be displayed in this view.
     */
    public EndedMatchView(String message) {
        this.message = message;
    }

    /**
     * Provides the message associated with the EndedMatchView.
     * Overrides the abstract method from the SelectViewEvent class.
     *
     * @return A String representing the message of the view.
     * @see SelectViewEvent#getMessage()
     */
    public String getMessage(){
        return this.message;
    }

    /**
     * Provides the type of this specific SelectViewEvent, which is "EndedMatchView".
     * Overrides the abstract method from the SelectViewEvent class.
     *
     * @return A String representing the type of the view, which is "EndedMatchView".
     * @see SelectViewEvent#getType()
     */
    public String getType(){
        return "EndedMatchView";
    }
}
