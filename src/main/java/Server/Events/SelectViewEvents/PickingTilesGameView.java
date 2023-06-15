package Server.Events.SelectViewEvents;

/**
 * This class is used to send the PickingTiles GameView type to the client

 * @Author ValentinoGuerrini
 */

public class PickingTilesGameView extends GameView{
    //private final String primaryType = "SelectViewEvent";
    private final String thirdType = "PickingTilesGameView";

    //private final String message;





    public PickingTilesGameView( ){
        super("Pick some tiles and then checkout!");

    }
    public PickingTilesGameView(String message){
        super(message);

    }


    @Override
    public String getType() {
        return "PickingTilesGameView";
    }
}
