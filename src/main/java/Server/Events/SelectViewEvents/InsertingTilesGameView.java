package Server.Events.SelectViewEvents;

/**
 * This class is used to send the InsertingTiles GameView type to the client
 * @Author ValentinoGuerrini
 */

public class InsertingTilesGameView extends GameView{
    //private final String primaryType = "SelectViewEvent";
    private final String secondaryType = "InsertingTilesGameView";

    private final String message;


    public InsertingTilesGameView(){
        super();
        this.message="please, select the coloumn where you want to insert the tiles";
    }
    public InsertingTilesGameView(String message){
        super();
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
