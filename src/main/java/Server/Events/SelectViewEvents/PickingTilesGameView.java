package Server.Events.SelectViewEvents;

/**
 * This class is used to send the PickingTiles GameView type to the client
 * @see ViewType for the Methods
 * @Author ValentinoGuerrini
 */

public class PickingTilesGameView extends GameView{

    private final String message;


    public PickingTilesGameView(boolean chatOn){
        super(chatOn);
        this.message="please, select a tile or check out selected tiles";
    }
    public PickingTilesGameView(boolean chatOn ,String message){
        super(chatOn);
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
