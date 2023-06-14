package Server.Events.SelectViewEvents;

/**
 * This class is used to send the basic GameView type to the client. It is used when the player is waiting for his turn
 * @Author ValentinoGuerrini
 */

public class GameView extends SelectViewEvent{
    //private final String primaryType = "SelectViewEvent";
    private final String secondaryType = "GameView";
    private final String message;


    public GameView(){
        this.message = "Wait for your turn!";

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
