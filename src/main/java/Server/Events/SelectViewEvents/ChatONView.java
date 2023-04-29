package Server.Events.SelectViewEvents;

public class ChatONView extends ViewType{
    private final String message;

    public ChatONView(){
        this.message = "Chat is now on";
    }

    public String getMessage(){
        return this.message;
    }

    public String getType(){
        return "ChatONView";
    }

}
