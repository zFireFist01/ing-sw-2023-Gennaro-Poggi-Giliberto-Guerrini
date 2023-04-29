package Server.Events.VCEvents;

public class LoginEvent extends VCEvent{
    private final String methodName;
    private final String nickname;


    public LoginEvent(String nickname){
        this.methodName = "onLoginEvent";
        this.nickname = nickname;
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
