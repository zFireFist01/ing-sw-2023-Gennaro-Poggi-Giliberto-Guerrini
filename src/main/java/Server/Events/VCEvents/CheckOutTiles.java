package Server.Events.VCEvents;


/**
 * Class representing a CheckOutTiles event, a type of VCEvent.
 * This event is triggered when a player decides to finalize their tile selection in the living room within the game.
 * It extends the abstract class VCEvent.
 * @author Marta Giliberto
 *
 * @see VCEvent
 */
public class CheckOutTiles extends VCEvent{
    private final String secondaryType = "CheckOutTiles";
    private final String methodName;

    /**
     * Constructor for the CheckOutTiles event.
     * Initializes the event and sets the method name as "onCheckOutTilesEvent".
     */
    public CheckOutTiles(){
        this.methodName = "onCheckOutTilesEvent";
    }


    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the name of the method associated with the CheckOutTiles event.
     *
     * @return A String representing the name of the method, which is "onCheckOutTilesEvent".
     * @see VCEvent#getMethodName()
     */
    public String getMethodName(){
        return this.methodName;
    }

    /**
     * Overrides the method from the VCEvent class.
     * Since checking out tiles does not involve a specific value, it returns null.
     *
     * @return null as there's no specific value associated with this event.
     * @see VCEvent#getValue()
     */
    public Object getValue(){
        return null;
    }

    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the type associated with the CheckOutTiles event, which is "CheckOutTiles".
     *
     * @return A String representing the type of the event, which is "CheckOutTiles".
     * @see VCEvent#getType()
     */
    public String getType(){
        return "CheckOutTiles";
    }
}
