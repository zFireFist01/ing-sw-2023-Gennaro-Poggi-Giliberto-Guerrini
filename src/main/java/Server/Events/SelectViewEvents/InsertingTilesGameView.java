package Server.Events.SelectViewEvents;

/**
 * Class representing an InsertingTilesGameView, a type of GameView.
 * This view is used to show the state of the game where the player needs to select the column of the bookshelf
 * where they want to insert the picked tiles.
 * It extends the GameView class.
 * @author Valentino Guerrini
 *
 * @see GameView
 */
public class InsertingTilesGameView extends GameView{
    private final String thirdType = "InsertingTilesGameView";



    /**
     * Default constructor for the InsertingTilesGameView.
     * Initializes the view with a default message of "Select the column where you want to insert the tiles!".
     */
    public InsertingTilesGameView(){
        super("Select the column where you want to insert the tiles!");
    }

    /**
     * Constructor for the InsertingTilesGameView with a custom message.
     * Initializes the view with the provided message.
     *
     * @param message A custom message to be displayed in this view.
     */
    public InsertingTilesGameView(String message){
        super(message);

    }

    /**
     * Provides the type of this specific GameView, which is "InsertingTilesGameView".
     * Overrides the method from the GameView class.
     *
     * @return A String representing the type of the view, which is "InsertingTilesGameView".
     * @see GameView#getType()
     */
    @Override
    public String getType() {
        return "InsertingTilesGameView";
    }

}
