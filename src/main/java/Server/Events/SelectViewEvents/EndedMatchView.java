package Server.Events.SelectViewEvents;

public class EndedMatchView extends ViewType{
    private final String message;

    public EndedMatchView(){
        this.message = "The match is ended";
    }

    public String getMessage(){
        return this.message;
    }

    public String getType(){
        return "EndedMatchView";
    }
}
