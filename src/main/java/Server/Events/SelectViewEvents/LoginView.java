package Server.Events.SelectViewEvents;

/**
 * This class is used to send the Login View to the client
 * @Author ValentinoGuerrini
 */

public class LoginView extends SelectViewEvent{
    //private final String primaryType = "SelectViewEvent";
    private final String secondaryType = "LoginView";
    private final boolean firstAttempt;
    private final String Message;
    private final boolean isFirstToJoin;

    public LoginView(boolean isFirstToJoin){
        this.firstAttempt = true;
        this.Message = "Insert your username";
        this.isFirstToJoin=isFirstToJoin;
    }

    public LoginView(boolean isFirstToJoin, String Message){
        this.firstAttempt = false;
        this.Message = Message;
        this.isFirstToJoin=isFirstToJoin;
    }

    public LoginView(){
        this.firstAttempt = true;
        this.Message = "Insert your username";
        isFirstToJoin=false;

    }

    public LoginView(String Message){
        this.firstAttempt = false;
        this.Message = Message;
        isFirstToJoin=false;
    }

    public boolean isFirstToJoin(){
        return isFirstToJoin;
    }

    public String getMessage(){
        return this.Message;
    }


    public String getType(){
        return "LoginView";
    }




}
