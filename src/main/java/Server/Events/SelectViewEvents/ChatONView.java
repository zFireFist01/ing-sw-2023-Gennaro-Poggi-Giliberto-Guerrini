package Server.Events.SelectViewEvents;

/**
 * This class is used to send the basic ChatONView type to the client. I

 * @Author ValentinoGuerrini
 */
public class ChatONView extends SelectViewEvent {
    //private final String primaryType = "SelectViewEvent";
    private final String secondaryType = "ChatONView";
    private final String message;

    public ChatONView() {
        this.message = "Chat is now on";
    }

    public String getMessage() {
        return this.message;
    }

    public String getType() {
        return "ChatONView";
    }
}
