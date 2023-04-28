package Server.Events.SelectViewEvents;

/**
 * This class is used to send the basic GameView type to the client. It is used when the player is waiting for his turn
 * @see ViewType for the Methods
 * @Author ValentinoGuerrini
 */

public class GameView extends ViewType{
    private final String message;
    private final boolean chatOn;

    public GameView(){
        this.message = "wait for your turn";
        this.chatOn = true;
    }

    public GameView(boolean chatOn){
        this.message = "wait for your turn";
        this.chatOn = chatOn;
    }

    public boolean getChatOn(){
        return this.chatOn;
    }

    public String getMessage(){
        return this.message;
    }

    public String getType(){
        return "GameView";
    }

}
