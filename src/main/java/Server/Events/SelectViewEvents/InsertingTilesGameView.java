package Server.Events.SelectViewEvents;

/**
 * This class is used to send the InsertingTiles GameView type to the client
 * @see ViewType for the Methods
 * @Author ValentinoGuerrini
 */

public class InsertingTilesGameView extends GameView{

    private final String message;


    public InsertingTilesGameView(boolean chatOn){
        super(chatOn);
        this.message="please, select the coloumn where you want to insert the tiles";
    }
    public InsertingTilesGameView(boolean chatOn ,String message){
        super(chatOn);
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    @Override
    public String getType() {
        return "InsertingTilesGameView";
    }

}
