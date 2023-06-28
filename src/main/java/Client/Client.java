package Client;

import Client.View.CLI.CLI;
import Client.View.GUI.GUI;
import javafx.application.Application;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Client {
    public static void main(String[] args){
        if (args.length > 1) {
            switch (args[0]) {
                case "-gui" :
                    Application.launch(GUI.class);
                    System.exit(0);
                case "-cli" :
                System.out.println("Welcome to");
                    System.out.println("                                                                                                    \n" +
                            "\n" +
                            "                                          ▄▒░▄░▒                                                    " +
                            "\n" +
                            "        ▐░░░▓    ░░░░                    ▐░▐▀░░░░   ▄                 ░░▄   ░▄▄░                    " +
                            "\n" +
                            "         ▐░░▌    ▒░░▌                    ▐░▐  ▒▄▓   ▐░▐▀              ░▒ ▐░▄▀░▒ ░▓                  " +
                            "\n" +
                            "         ▐░░▐    ░░░            ▄ ▄       ░░░       ▐░▐               ░▒ ▌░▌ ▒░▄▄▀                  " +
                            "\n" +
                            "         ▐░▐▒▒  ░▌░░     ░▒░▀  ▄░▐█        ▒░░▒     ▐░▐▒░░▄           ░▒ ▐░▌    ▄░                  " +
                            "\n" +
                            "         ▐▒▐ ░▒░▓ ░▐       ▒░░▒░▄▀            ▒░░░  ▐░░░▄░░░▌   ░▄▄   ▒▒  ▒▐     ▄▀   ▄▄▄░          " +
                            "\n" +
                            "         ▐▒▐ ▐▒▐ ▐▒▐        ▀░▒▄▀         ▄    ▐░░▒ ▐▒░▀  ▒▒▌ ▒░▀  ▐░ ▒▒ ▐▀▒░▀▀ ▒▒▓ ▒░█   ▒▌        " +
                            "\n" +
                            "         ▐▒▒ ▐▄▄ ▒▒▐  ▄▒▒▒▄ ▒░█         ▒░▒░▒▄  ▒▒▐ ▐▒▐   ▒░ ▐▒▒▀▀▀▒▀ ▒▒   ▐▒▌  ▒▒▌ ▌▒▀▀▀▀▀▀        " +
                            "\n" +
                            "         ▄▒▒▌    ▒▒▒ ▐▌▒▒▒▀▒░█          ▒▒▒▒▒▌▄▒▒▓▌ ▐▒▒   ▒▒  ▒▒  ▒▒▒▌▒▒   ▐▒█  ▒▒▌ ▒▒▒ ▐▒▒▓        " +
                            "\n" +
                            "         ▓▒▒▒   ▐▒▒▒▓  ▒▒▒▄▓▀             ▀▒▓▒▒▓█▀  ▒▒▒▌ ▐▒▒█  ▀▒▓▓▓▀ ▒▒▌  ▒▓▓  ▒▓▀  ▀▒▒▒▓▀         " +
                            "                                                                                                    ");
                new CLI();
                break;
            }

        } else {
            System.out.println("Welcome to");
            System.out.println("                                                                                                    \n" +
                    "\n" +
                    "                                          ▄▒░▄░▒                                                    " +
                    "\n" +
                    "        ▐░░░▓    ░░░░                    ▐░▐▀░░░░   ▄                 ░░▄   ░▄▄░                    " +
                    "\n" +
                    "         ▐░░▌    ▒░░▌                    ▐░▐  ▒▄▓   ▐░▐▀              ░▒ ▐░▄▀░▒ ░▓                  " +
                    "\n" +
                    "         ▐░░▐    ░░░            ▄ ▄       ░░░       ▐░▐               ░▒ ▌░▌ ▒░▄▄▀                  " +
                    "\n" +
                    "         ▐░▐▒▒  ░▌░░     ░▒░▀  ▄░▐█        ▒░░▒     ▐░▐▒░░▄           ░▒ ▐░▌    ▄░                  " +
                    "\n" +
                    "         ▐▒▐ ░▒░▓ ░▐       ▒░░▒░▄▀            ▒░░░  ▐░░░▄░░░▌   ░▄▄   ▒▒  ▒▐     ▄▀   ▄▄▄░          " +
                    "\n" +
                    "         ▐▒▐ ▐▒▐ ▐▒▐        ▀░▒▄▀         ▄    ▐░░▒ ▐▒░▀  ▒▒▌ ▒░▀  ▐░ ▒▒ ▐▀▒░▀▀ ▒▒▓ ▒░█   ▒▌        " +
                    "\n" +
                    "         ▐▒▒ ▐▄▄ ▒▒▐  ▄▒▒▒▄ ▒░█         ▒░▒░▒▄  ▒▒▐ ▐▒▐   ▒░ ▐▒▒▀▀▀▒▀ ▒▒   ▐▒▌  ▒▒▌ ▌▒▀▀▀▀▀▀        " +
                    "\n" +
                    "         ▄▒▒▌    ▒▒▒ ▐▌▒▒▒▀▒░█          ▒▒▒▒▒▌▄▒▒▓▌ ▐▒▒   ▒▒  ▒▒  ▒▒▒▌▒▒   ▐▒█  ▒▒▌ ▒▒▒ ▐▒▒▓        " +
                    "\n" +
                    "         ▓▒▒▒   ▐▒▒▒▓  ▒▒▒▄▓▀             ▀▒▓▒▒▓█▀  ▒▒▒▌ ▐▒▒█  ▀▒▓▓▓▀ ▒▒▌  ▒▓▓  ▒▓▀  ▀▒▒▒▓▀         " +
                    "                                                                                                    ");
            new Client().Setup();
        }
    }


    /**
     * This method is used to set up the client and choose the interface
     */
    public void Setup(){
        boolean flag=true;
        int choice;

        while(flag){
            System.out.println("Enter 1 for CLI and 2 for GUI");
            System.out.print("> ");
            Scanner sc = new Scanner(System.in);
            try{
                choice = sc.nextInt();
                if(choice==1){
                    new CLI();
                    flag=false;
                }
                else if(choice==2){
                    Application.launch(GUI.class);
                    System.exit(0);
                    flag=false;
                }
                else {
                    System.out.println("Invalid choice: the number must be 1 or 2!");
                }
            }catch (InputMismatchException e){
                System.out.println("Invalid choice: Please insert a number!");
                continue;
            }

        }
    }

}
