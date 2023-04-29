package Client.View.CLI;

import Client.ConnectionHandler;
import Client.ConnectionType;

import java.util.Scanner;

public class CLI implements Runnable{
    private ConnectionHandler connectionHandler;


    private Scanner scanner;

    public CLI(){
        scanner = new Scanner(System.in);
        connect();

        System.out.println("info        : show this message\n"+
                           "login       : login to the server\n"+
                           "quit        : quit the game\n");
        new Thread(this::run).start();
        //refresh();
    }

    @Override
    public void run() {
        String input;
        input = scanner.nextLine();
        while(input != null){
            connectionHandler.parseInput(input);
            if(input.equals("quit")){
                System.exit(0);
            }
            input = scanner.nextLine();
        }


    }

    //connect to the server

    private void connect(){
        boolean flag = true;
        ConnectionType connectionType = null;
        System.out.println("Select connection type: ");
        System.out.println("1) Socket 2) RMI");
        String connection =scanner.nextLine();
        System.out.println("Server Adress:");
        String host = scanner.nextLine();

        System.out.println("ServerPort:");
        int port = scanner.nextInt();

        while(flag){
            switch (connection){
                case "1":
                    connectionType = ConnectionType.SOCKET;
                    flag = false;
                    break;
                case "2":
                    connectionType = ConnectionType.RMI;
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid connection type");
                    System.out.println("1) Socket 2) RMI");
                    connection =scanner.nextLine();
            }

        }
        try{
            connectionHandler = new ConnectionHandler(connectionType,port,host);
        }catch (Exception e){
            System.out.println("Connection failed");
            System.exit(1);
        }
        System.out.println("Connection successful");

    }

    //refresh the CLI

    //update()

}
