package Server.Events.SelectViewEvents;

public class ChatOFFView extends SelectViewEvent{
    //private final String primaryType = "SelectViewEvent";
    private final String secondaryType = "ChatOFFView";

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
