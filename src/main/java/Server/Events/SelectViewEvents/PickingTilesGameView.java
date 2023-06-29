package Server.Events.SelectViewEvents;

/**
 * Class representing a PickingTilesGameView, a type of GameView.
 * This view is used to show the state of the game where the player needs to select some tiles from the living room and
 * then press checkout to finalize their selection.
 * It extends the GameView class.
 * @author Marta Giliberto
 * @see GameView
 */
public class PickingTilesGameView extends GameView{
    private final String thirdType = "PickingTilesGameView";

    /**
     * Constructor for the PickingTilesGameView with a default message.
     * Initializes the view with the message "Pick some tiles and then checkout!".
     */
    public PickingTilesGameView( ){
        super("Pick some tiles and then checkout!");

    }

    /**
     * Constructor for the PickingTilesGameView with a custom message.
     * Initializes the view with the provided message.
     *
     * @param message The custom message to be displayed in this view.
     */
    public PickingTilesGameView(String message){
        super(message);

    }

    /**
     * Provides the type of this specific GameView, which is "PickingTilesGameView".
     * Overrides the method from the GameView class.
     *
     * @return A String representing the type of the view, which is "PickingTilesGameView".
     * @see GameView#getType()
     */
    @Override
    public String getType() {
        return "PickingTilesGameView";
    }
}
