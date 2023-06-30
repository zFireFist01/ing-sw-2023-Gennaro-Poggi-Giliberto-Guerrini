package Server.Events.SelectViewEvents;


/**
 * Class representing a LoginView, a type of SelectViewEvent.
 * This view is used to prompt the player for login-related information, such as their username.
 * If the player is the first to join, they also need to provide the number of players.
 * It extends the SelectViewEvent class.
 * @author Paolo Gennaro
 * @see SelectViewEvent
 */
public class LoginView extends SelectViewEvent{
    private final String secondaryType = "LoginView";
    private final boolean firstAttempt;
    private final String Message;
    private final boolean isFirstToJoin;

    /**
     * Constructor for the LoginView, with the isFirstToJoin parameter.
     * Initializes the view with a default message of "Insert your username".
     *
     * @param isFirstToJoin A boolean indicating whether the player is the first to join the game.
     */
    public LoginView(boolean isFirstToJoin){
        this.firstAttempt = true;
        this.Message = "Insert your username";
        this.isFirstToJoin=isFirstToJoin;
    }

    /**
     * Constructor for the LoginView, with the isFirstToJoin parameter and a custom message.
     * Initializes the view with the provided message.
     *
     * @param isFirstToJoin A boolean indicating whether the player is the first to join the game.
     * @param Message A custom message to be displayed in this view.
     */
    public LoginView(boolean isFirstToJoin, String Message){
        this.firstAttempt = false;
        this.Message = Message;
        this.isFirstToJoin=isFirstToJoin;
    }

    /**
     * Default constructor for the LoginView.
     * Initializes the view with a default message of "Insert your username", and assumes the player is not the first to join.
     */
    public LoginView(){
        this.firstAttempt = true;
        this.Message = "Insert your username";
        isFirstToJoin=false;

    }

    /**
     * Constructor for the LoginView with a custom message.
     * Initializes the view with the provided message and assumes the player is not the first to join.
     *
     * @param Message A custom message to be displayed in this view.
     */
    public LoginView(String Message){
        this.firstAttempt = false;
        this.Message = Message;
        isFirstToJoin=false;
    }

    /**
     * Check whether the player is the first to join the game.
     *
     * @return A boolean indicating whether the player is the first to join the game.
     */
    public boolean isFirstToJoin(){
        return isFirstToJoin;
    }


    /**
     * Provides the message associated with the LoginView.
     * Overrides the abstract method from the SelectViewEvent class.
     *
     * @return A String representing the message of the event.
     * @see SelectViewEvent#getMessage()
     */
    public String getMessage(){
        return this.Message;
    }


    /**
     * Provides the type of this specific SelectViewEvent, which is "LoginView".
     * Overrides the abstract method from the SelectViewEvent class.
     *
     * @return A String representing the type of the view, which is "LoginView".
     * @see SelectViewEvent#getType()
     */
    public String getType(){
        return "LoginView";
    }




}
