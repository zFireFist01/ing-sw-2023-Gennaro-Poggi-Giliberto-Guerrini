package Server.Events.VCEvents;

/**
 * Class representing a LoginEvent, a type of VCEvent.
 * This event is triggered when a player attempts to log in to the game.
 * It extends the abstract class VCEvent.
 * @author Marta Giliberto
 * @see VCEvent
 */
public class LoginEvent extends VCEvent{
    private final String secondaryType = "LoginEvent";
    private final String methodName;
    private final String nickname;
    private final int numberOfPlayers;

    /**
     * Constructor for the LoginEvent when the player is the first to enter the match.
     * Initializes the event with the provided nickname and the number of players,
     * and sets the method name as "onLoginEvent".
     *
     * @param nickname The nickname of the player logging in.
     * @param numberOfPlayers The total number of players that will participate in the match.
     */
    public LoginEvent(String nickname, int numberOfPlayers){
        this.methodName = "onLoginEvent";
        this.nickname = nickname;
        this.numberOfPlayers=numberOfPlayers;
    }



    /**
     * Constructor for the LoginEvent for players other than the first.
     * Initializes the event with the provided nickname and sets the method name as "onLoginEvent".
     * As this constructor is used when the player is not the first to enter the match,
     * numberOfPlayers is set to 0 in order to let the controller know that must be ignored.
     *
     * @param nickname The nickname of the player logging in.
     */
    public LoginEvent(String nickname){
        this.methodName = "onLoginEvent";
        this.nickname = nickname;
        this.numberOfPlayers=0;
    }

    /**
     * Returns the number of players in the game.
     *
     * @return The total number of players that will participate in the match.
     * Returns 0 if the player is not the first to enter the match.
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the name of the method associated with the LoginEvent.
     *
     * @return A String representing the name of the method, which is "onLoginEvent".
     * @see VCEvent#getMethodName()
     */
    public String getMethodName() {
        return this.methodName;
    }

    /**
     * Overrides the method from the VCEvent class.
     * Provides the value associated with the LoginEvent, which is the player's nickname.
     *
     * @return A String that represents the player's nickname.
     * @see VCEvent#getValue()
     */
    public Object getValue() {
        return this.nickname;
    }

    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the type associated with the LoginEvent, which is "LoginEvent".
     *
     * @return A String representing the type of the event, which is "LoginEvent".
     * @see VCEvent#getType()
     */
    public String getType(){
        return "LoginEvent";
    }
}
