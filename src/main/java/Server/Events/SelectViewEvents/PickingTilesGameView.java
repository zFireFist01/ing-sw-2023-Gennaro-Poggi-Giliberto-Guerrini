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
        super("please, select a tile or check out selected tiles");

    }
    public PickingTilesGameView(String message){
        super(message);

    }


    @Override
    public String getType() {
        return "PickingTilesGameView";
    }
}
