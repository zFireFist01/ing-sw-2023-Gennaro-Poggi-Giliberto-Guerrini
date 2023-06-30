package Server.Events.VCEvents;


/**
 * Class representing a ClickOnTile event, a type of VCEvent.
 * This event is triggered when a player selects a tile from the living room within the game, but hasn't yet picked it up.
 * It extends the abstract class VCEvent.
 *
 * @see VCEvent
 */
public class ClickOnTile extends VCEvent{
    private final String secondaryType = "ClickOnTile";

    private final int[] coordinates;
    private final String methodName;

    /**
     * Constructor for the ClickOnTile event.
     * Initializes the event with the provided coordinates of the selected tile
     * and sets the method name as "onClickOnTileEvent".
     *
     * @param coordinates The coordinates of the selected tile in the living room.
     */
    public ClickOnTile(int[] coordinates){
        this.coordinates = coordinates;
        this.methodName = "onClickOnTileEvent";
    }

    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the name of the method associated with the ClickOnTile event.
     *
     * @return A String representing the name of the method, which is "onClickOnTileEvent".
     * @see VCEvent#getMethodName()
     */
    public String getMethodName(){
        return this.methodName;
    }

    /**
     * Overrides the method from the VCEvent class.
     * Provides the value associated with the ClickOnTile event, which is the coordinates of the selected tile.
     *
     * @return An array of integers that represent the coordinates of the selected tile in the living room.
     * @see VCEvent#getValue()
     */
    public Object getValue(){
        return this.coordinates;
    }

    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the type associated with the ClickOnTile event, which is "ClickOnTile".
     *
     * @return A String representing the type of the event, which is "ClickOnTile".
     * @see VCEvent#getType()
     */
    public String getType(){
        return "ClickOnTile";
    }
}
