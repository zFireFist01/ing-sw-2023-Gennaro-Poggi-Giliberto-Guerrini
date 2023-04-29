package Server.Events.SelectViewEvents;

public class ChatOFFView extends ViewType{

    private final String message;

    public ChatOFFView(){
        this.message = "Chat is now off";
    }

    public String getMessage(){
        return this.message;
    }

    public String getType(){
        return "ChatOFFView";
    }
}
