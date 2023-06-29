package Server.Events.VCEvents;

/**
 * Class representing a SelectColumn event, a type of VCEvent.
 * This event is triggered when a player selects a column on their bookshelf to insert tiles in the game.
 * It extends the abstract class VCEvent.
 * @author Patrick Poggi
 * @see VCEvent
 */
public class SelectColumn extends VCEvent{
    private final String secondaryType = "SelectColumn";
    private final int column;
    private final String methodName;

    /**
     * Constructor for the SelectColumn event.
     * Initializes the event with the selected column index on the player's bookshelf
     * and sets the method name as "onSelectColumnEvent".
     *
     * @param column The index of the selected column on the player's bookshelf.
     */
    public SelectColumn(int column){
        this.column = column;
        this.methodName = "onSelectColumnEvent";

    }

    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the name of the method associated with the SelectColumn event.
     *
     * @return A String representing the name of the method, which is "onSelectColumnEvent".
     * @see VCEvent#getMethodName()
     */
    public String getMethodName(){
        return this.methodName;
    }

    /**
     * Overrides the method from the VCEvent class.
     * Provides the value associated with the SelectColumn event, which is the column index on the player's bookshelf.
     *
     * @return An Integer that represents the index of the selected column on the player's bookshelf.
     * @see VCEvent#getValue()
     */
    public Object getValue(){
        return this.column;
    }

    /**
     * Overrides the abstract method from the VCEvent class.
     * Provides the type associated with the SelectColumn event, which is "SelectColumn".
     *
     * @return A String representing the type of the event, which is "SelectColumn".
     * @see VCEvent#getType()
     */
    public String getType(){
        return "SelectColumn";
    }


}
