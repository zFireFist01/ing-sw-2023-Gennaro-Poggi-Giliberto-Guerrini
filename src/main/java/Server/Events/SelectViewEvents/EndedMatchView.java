package Server.Events.SelectViewEvents;

public class EndedMatchView extends SelectViewEvent{
    //private final String primaryType = "SelectViewEvent";
    private final String secondaryType = "EndedMatchView";
    private final String message;

    public EndedMatchView(){
        this.message = "The match is ended";
    }

    public EndedMatchView(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public String getType(){
        return "EndedMatchView";
    }
}
