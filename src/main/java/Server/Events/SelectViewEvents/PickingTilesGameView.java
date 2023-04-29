package Server.Events.SelectViewEvents;

/**
 * This class is used to send the PickingTiles GameView type to the client
 * @see ViewType for the Methods
 * @Author ValentinoGuerrini
 */

public class PickingTilesGameView extends GameView{

    private final String message;


    public PickingTilesGameView( ){
        super();
        this.message="please, select a tile or check out selected tiles";
    }
    public PickingTilesGameView(String message){
        super();
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    @Override
    public String getType() {
        return "PickingTilesGameView";
    }
}
