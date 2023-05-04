package Server.Events.VCEvents;

public class LoginEvent extends VCEvent{
    private final String secondaryType = "LoginEvent";
    private final String methodName;
    private final String nickname;
    private final int numberOfPlayers;

    public LoginEvent(String nickname, int numberOfPlayers){
        this.methodName = "onLoginEvent";
        this.nickname = nickname;
        this.numberOfPlayers=numberOfPlayers;
    }




    public LoginEvent(String nickname){
        this.methodName = "onLoginEvent";
        this.nickname = nickname;
        this.numberOfPlayers=0;
    }
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public String getMethodName() {
        return this.methodName;
    }

    public Object getValue() {
        return this.nickname;
    }

    public String getType(){
        return "LoginEvent";
    }
}
