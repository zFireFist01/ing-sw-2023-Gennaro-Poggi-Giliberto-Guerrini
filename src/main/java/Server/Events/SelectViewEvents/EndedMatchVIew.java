package Server.Events.SelectViewEvents;

public class EndedMatchVIew extends ViewType{
    private final String message;

    public EndedMatchVIew(){
        this.message = "The match is ended";
    }

    public String getMessage(){
        return this.message;
    }

    public String getType(){
        return "EndedMatchView";
    }
}
