package Server.Events.SelectViewEvents;

/**
 * This class is used to send the Login View to the client
 * @see ViewType for the Methods
 * @Author ValentinoGuerrini
 */

public class LoginView extends ViewType{
    private final boolean firstAttempt;
    private final String Message;

    public LoginView(){
        this.firstAttempt = true;
        this.Message = "Insert your username";

    }

    public LoginView(String Message){
        this.firstAttempt = false;
        this.Message = Message;
    }

    public String getMessage(){
        return this.Message;
    }


    public String getType(){
        return "LoginView";
    }




}
