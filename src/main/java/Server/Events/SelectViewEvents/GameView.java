package Server.Events.SelectViewEvents;

/**
 * Class representing a GameView, a type of SelectViewEvent.
 * This view is shown when a player is not currently the active player, or they are the only one connected to the game.
 * In this view, the player can see the state of the game and send messages on the chat, but cannot play because it's not their turn.
 * It extends the SelectViewEvent class.
 *
 * @author Patrick Poggi
 * @see SelectViewEvent
 */
public class GameView extends SelectViewEvent{
    private final String secondaryType = "GameView";
    private final String message;

    /**
     * Default constructor for the GameView.
     * Initializes the view without a specific message.
     */
    public GameView(){
        this.message = "";
    }

    /**
     * Constructor for the GameView with a custom message.
     * Initializes the view with the provided message.
     *
     * @param message A custom message to be displayed in this view.
     */
    public GameView(String message){
        this.message = message;
    }

    /**
     * Provides the message associated with the GameView.
     * Overrides the abstract method from the SelectViewEvent class.
     *
     * @return A String representing the message of the view.
     * @see SelectViewEvent#getMessage()
     */
    public String getMessage(){
        return this.message;
    }

    /**
     * Provides the type of this specific SelectViewEvent, which is "GameView".
     * Overrides the abstract method from the SelectViewEvent class.
     *
     * @return A String representing the type of the view, which is "GameView".
     * @see SelectViewEvent#getType()
     */
    public String getType(){
        return "GameView";
    }

}
