package Server.Events.SelectViewEvents;

/**
 * This class is used to send the basic GameView type to the client. It is used when the player is waiting for his turn
 * @see ViewType for the Methods
 * @Author ValentinoGuerrini
 */

public class GameView extends ViewType{
    private final String message;


    public GameView(){
        this.message = "wait for your turn";

    }

    public GameView(String message){
        this.message = message;
    }




    public String getMessage(){
        return this.message;
    }

    public String getType(){
        return "GameView";
    }

}
