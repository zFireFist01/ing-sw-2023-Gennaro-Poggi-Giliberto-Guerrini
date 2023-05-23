package Client;

import Client.View.CLI.CLI;
import Client.View.GUI.GUI;

import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        if (args.length > 1) {
            switch (args[0]) {
                case "-gui" :
                    //TODO GUI
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

    public void Setup(){
        boolean flag=true;
        int choice;

        while(flag){
            System.out.println("Enter 1 for CLI and 2 for GUI");
            Scanner sc = new Scanner(System.in);
            choice = sc.nextInt();
            if(choice==1){
                new CLI();
                flag=false;
            }
            else if(choice==2){
                new GUI();//TODO gui
                flag=false;
            }
            else {
                System.out.println("Invalid choice");
            }
        }
    }

}
