package Server.Events.SelectViewEvents;

/**
 * This class is used to send the InsertingTiles GameView type to the client
 * @Author ValentinoGuerrini
 */

public class InsertingTilesGameView extends GameView{
    //private final String primaryType = "SelectViewEvent";
    private final String thirdType = "InsertingTilesGameView";




    public InsertingTilesGameView(){
        super("please, select the coloumn where you want to insert the tiles");
    }
    public InsertingTilesGameView(String message){
        super(message);

    }



    @Override
    public String getType() {
        return "InsertingTilesGameView";
    }

}
