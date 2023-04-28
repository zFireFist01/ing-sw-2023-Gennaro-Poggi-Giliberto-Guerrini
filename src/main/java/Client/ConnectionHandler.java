package Client;

import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;

public class ConnectionHandler {
    private boolean loggedIn = false;
    private final int PORT;
    private final String SERVER_IP;

    private ConnectionType connectionType;
    private Socket socket;
    private Scanner inputStream;
    private PrintWriter outputStream;



    public ConnectionHandler(ConnectionType connectionType,int PORT,String SERVER_IP) throws ConnectException {
        this.connectionType = connectionType;
        this.PORT = PORT;
        this.SERVER_IP = SERVER_IP;
        Connect();
    }

    private void Connect() throws ConnectException{
        switch (connectionType){
            case SOCKET :
                connectSocket();
                loggedIn = true;
                break;
            case RMI:
                connectRMI();
                loggedIn = true;
                break;
        }
    }

    private void connectSocket() throws ConnectException {
        try {
            socket = new Socket(SERVER_IP, PORT);
            inputStream = new Scanner(socket.getInputStream());
            outputStream = new PrintWriter(socket.getOutputStream()); //output messages to server
        }catch (Exception e){
            throw new ConnectException(e.getMessage());
        }

    }

    private void connectRMI() throws ConnectException {
        //TODO
    }

    public void parseInput(String command){
        command=command.trim();
        if(command.length()>0){
            switch(command){
                case "info":
                    info();
                    break;
                case "login":
                    login();
                    break;
                case "quit":
                    quit();
                    break;
                default:
                    inGameCommands(command);
            }
        }

    }

    private void info(){
        if (loggedIn){
            System.out.println(
                    "-info            : show this message\n"+
                    "-open_chat       : open chat\n"+
                    "-send @nick      : send message to nick\n"+
                    "-send @all       : send message to all\n"+
                    "-select i j      : select the tile i,j\n"+
                    "-checkout        :  take selected tiles\n"+
                    "-insert i        : insert in the column i\n"+
                    "-close_chat      : close the chat\n"+
                    "-quit            : quit the game\n"
            );
        }else{
            System.out.println("info        : show this message\n"+
                    "login       : login to the server\n"+
                    "quit        : quit the game\n");
        }
    }

    private void login (){
        //TODO
    }

    private void quit(){
        //TODO
    }

    private void inGameCommands(String command){
        //TODO send command to server
    }











}
