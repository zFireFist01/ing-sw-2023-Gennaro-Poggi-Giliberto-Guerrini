package Client.View.CLI;
//import org.fusesource.jansi.AnsiConsole;


import Client.ConnectionType;
import Client.NetworkHandler;
import Client.NetworkRMIHandler;
import Client.NetworkSocketHandler;
import Client.View.View;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.LoginView;
import Server.Events.SelectViewEvents.SelectViewEvent;


import Server.Events.VCEvents.ClickOnTile;
import Server.Events.VCEvents.LoginEvent;


import Server.Events.VCEvents.*;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.Chat.Message;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.LivingRoomTileSpot;
import Server.Model.GameItems.PointsTile;
import Server.Model.GameItems.TileType;
import Server.Model.LightMatch;
import Server.Model.Player.Player;
import Utils.ConnectionInfo;
import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.*;


/**
 * This class is used to manage the CLI view of the game
 * @author Valentino Guerrini & Patrick Poggi & Marta Giliberto & Paolo Gennaro
 */
public class CLI implements Runnable , View {

    //draw coordinates
    private final static int LIVINGROOM_I= 5;
    private final static int LIVINGROOM_J= 43;
    private final static int CARDLINE= 9;
    private final static int PERSONAL_J= 10;
    private final static int COMMON_1_J= 90;
    private final static int POINTS_1_J= 108;
    private final static int DESCRIPTION_I = 13;
    private final static int DESCRIPTION_1_J = 106;
    private final static int COMMON_2_J= 135;
    private final static int DESCRIPTION_2_J = 151;
    private final static int POINTS_2_J= 155;
    private final static int BOOKSHELF_I = 26;
    private final static int BOOKSHELF_1_J = 8;
    private final static int BOOKSHELF_2_J = 46;
    private final static int BOOKSHELF_3_J = 84;
    private final static int BOOKSHELF_4_J = 122;
    private final static int END_TILE_I = 27;
    private final static int END_TILE_1_J = 30;
    private final static int END_TILE_2_J = 68;
    private final static int END_TILE_3_J = 106;
    private final static int END_TILE_4_J = 144;
    private final static int NAME_LENGTH = 20;
    private final static int PLAYER_NAME_I = 41;
    private final static int PLAYER_POINTS_I = 42;
    private final static int PLAYER_1_POINTS_1_J =6;
    private final static int PLAYER_1_POINTS_2_J = 21;
    private final static int PLAYER_2_POINTS_1_J = 45;
    private final static int PLAYER_2_POINTS_2_J = 60;
    private final static int PLAYER_3_POINTS_1_J = 83;
    private final static int PLAYER_3_POINTS_2_J = 98;
    private final static int PLAYER_4_POINTS_1_J = 121;
    private final static int PLAYER_4_POINTS_2_J = 136;
    private final static String[][] EMPTYSPOT= {{ANSIParameters.RED+"╔","═","═","═","═","═","═","═","╗" + ANSIParameters.CRESET},
            {ANSIParameters.RED+"║"," "," "," "," "," "," "," ","║"+ ANSIParameters.CRESET},
            {ANSIParameters.RED+"║"," "," "," "," "," "," "," ","║"+ ANSIParameters.CRESET},
            {ANSIParameters.RED+"╚","═","═","═","═","═","═","═","╝"+ ANSIParameters.CRESET}};

    private final static String[][] ENDTILE= {{ANSIParameters.RED+"╔","═","═","═","═","═","═","═","╗"+ ANSIParameters.CRESET},
            {ANSIParameters.RED+"║"," "," ","#","1"," "," "," ","║"+ ANSIParameters.CRESET},
            {ANSIParameters.RED+"║"," ","P","o","i","n","t"," ","║"+ ANSIParameters.CRESET},
            {ANSIParameters.RED+"╚","═","═","═","═","═","═","═","╝"+ ANSIParameters.CRESET}};

    private NetworkHandler networkHandler;
    private int countOfPicks = 0;
    private Environment board = new Environment();
    private ArrayList<String> chat = new ArrayList<>();
    private boolean chatIsOpened = false;
    private HashMap<Integer,Player> players = new HashMap<>();
    private HashMap<Integer,Integer> finalScores;
    private Player winner;
    private int numberPlayers;
    private Player me;
    private String myNick;
    private boolean matchStarted = false;
    private boolean MatchEnded = false;
    private SelectViewEvent currentView;

    private boolean myTurn = false;
    private Scanner scanner;
    private boolean isReconnecting = false;
    private ConnectionInfo connectionInfo = null;

    private FileReader fileReader;
    private FileWriter fileWriter;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private File connectionFile = null;
    private String directoryPath = null;
    private final String CONNECTION_INFO_DIRECTORY_NAME = "ClientFiles";

    public CLI(){
        scanner = new Scanner(System.in);
        connect();

        System.out.println("info        : show this message\n"+
                           "play        : login to the server\n"+
                           "quit        : quit the game\n");
        System.out.print("> ");
        new Thread(this::run).start();
        //refresh();
    }

    @Override
    public void run() {
        String input = "";
        do{
            this.parseInput(input);
            input = scanner.nextLine();
        }while(input!=null);
        manageQuitting();

    }

    //connect to the server
    private File getDirectory(){
        // gettin absolute path of the jar file
        String jarFilePath = CLI.class.getProtectionDomain().getCodeSource().getLocation().getPath();

        // Getting its parent's file path
        String jarFolder = new File(jarFilePath).getParent();

        // Crea un oggetto File per la nuova directory nella cartella del file .jar
        File newDirectory = new File(jarFolder, CONNECTION_INFO_DIRECTORY_NAME);

        // Crea la directory
        if(!newDirectory.exists()) {
            newDirectory.mkdir();
        }
        //Get person's name in order to create a directory
        System.out.print("Insert your name:\n> ");
        String name = scanner.nextLine();
        // Creating the directory
        //directoryPath = "src/main/resources/ClientFiles/Client_"+name;
        directoryPath = jarFolder+"/"+CONNECTION_INFO_DIRECTORY_NAME+"/Client_"+name;
        File directory = new File(directoryPath);
        int i = 1;
        if(directory.exists() && directory.isDirectory()){
            try {
                ConnectionInfo ci = new Gson().fromJson(
                        new BufferedReader(
                                new FileReader(directoryPath+"/ConnectionInfo.txt")),
                        ConnectionInfo.class
                );
                if(     ci!=null &&
                        ci.getNickname()!=null
                ){
                    System.out.print("It seems you were already connected, is that right? (y/n)\n> ");
                    if(scanner.nextLine().equalsIgnoreCase("y")){
                        int j=1;
                        List<String> listDirectoryPath = new ArrayList<>();
                        listDirectoryPath.add(directoryPath);
                        File tempDirectory = new File(directoryPath+"_"+j);
                        while(tempDirectory.exists() && tempDirectory.isDirectory()){
                            listDirectoryPath.add(directoryPath+"_"+j);
                            //directory = new File(listDirectoryPath);
                            j++;
                            tempDirectory = new File(directoryPath+"_"+j);
                        }
                        System.out.println("If you remember which of the following directories is the right one," +
                                " please insert the number of it, otherwise type \'n\' and you will be connected as " +
                                "a new client.");
                        for(int k=0;k<listDirectoryPath.size();k++){
                            System.out.println(k+" - "+listDirectoryPath.get(k));
                        }
                        System.out.print("> ");
                        String choice = scanner.nextLine();
                        if(choice.equalsIgnoreCase("n")) {
                            System.out.println("Ok");
                            isReconnecting = false;
                        }else{
                            boolean ok = false;
                            while(!ok){
                                try{
                                    directoryPath = listDirectoryPath.get(Integer.parseInt(choice));
                                    ok = true;
                                }catch (NumberFormatException e) {
                                    System.out.println("Please insert a number");
                                    System.out.print("> ");
                                    choice = scanner.nextLine();
                                }
                            }
                            System.out.print("Do you want to reconnect? (y/n)\n> ");
                            if(scanner.nextLine().equalsIgnoreCase("y")){
                                isReconnecting = true;
                                System.out.println("Ok");
                                //break;
                            }else{
                                isReconnecting = false;
                                System.out.println("Ok");
                                //break;
                            }
                        }

                    }else{
                        System.out.println("Ok");
                        //break;
                    }
                }
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        while(!isReconnecting && directory.exists() && directory.isDirectory()){
            directoryPath = "src/main/resources/ClientFiles/Client_"+name+"_"+i;
            directory = new File(directoryPath);
            i++;
        }
        return directory;
    }

    private void connectionProcess(){
        boolean flag = true;
        String localIP = null;
        ConnectionType connectionType = null;
        System.out.println("Select connection type: ");
        System.out.println("1) Socket 2) RMI");
        System.out.print("> ");
        String connection =scanner.nextLine();
        while(!connection.equals("1") && !connection.equals("2")){
            System.out.println("Invalid input: please insert 1 or 2!");
            System.out.print("> ");
            connection =scanner.nextLine();
        }

        String host = null;
        String numbers[] = null;
        Integer numbersInt[] = new Integer[4];
        boolean flag2 = true;
        boolean parseOk = true;
        while(flag2){
            System.out.println("Server Address:");
            System.out.print("> ");
            host = scanner.nextLine();
            numbers = host.split("\\.");
            if(numbers.length == 4) {
                for (int j = 0; j < 4; j++) {
                    try {
                        numbersInt[j] = Integer.parseInt(numbers[j]);
                    } catch (NumberFormatException e) {
                        parseOk = false;
                        System.out.println("Invalid IP address");
                        break;
                    }
                }
                if(     parseOk &&
                        (numbersInt[0]>255
                                || numbersInt[1]>255
                                || numbersInt[2]>255
                                || numbersInt[3]>255
                                ||
                                numbersInt[0]<0
                                || numbersInt[1]<0
                                || numbersInt[2]<0
                                || numbersInt[3]<0)
                ) {
                    System.out.println("Invalid IP address");
                }else{
                    flag2 = !parseOk;
                }
            }else{
                System.out.println("Invalid IP address");
            }
        }

        String portString = null;
        int port = 0;
        flag2 = true;
        while (flag2){
            System.out.println("ServerPort:");
            System.out.print("> ");
            portString = scanner.nextLine();

            if(portString == null){
                System.out.println("Invalid port number");
            }else{
                try{
                    port = Integer.parseInt(portString);
                    if(port < 1024 || port > 65535) {
                        System.out.println("Invalid port number");
                    }else{
                        flag2 = false;
                    }
                }catch (NumberFormatException e){
                    System.out.println("Invalid port number");
                }
            }
        }


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
                    System.out.println("Invalid connection type, try again");
                    System.out.println("1) Socket 2) RMI");
                    System.out.print("> ");
                    connection =scanner.nextLine();
            }

        }
        try{
            //connectionHandler = new ConnectionHandler(connectionType,port,host);
            if(connectionType == ConnectionType.SOCKET) {
                networkHandler = new NetworkSocketHandler(host, port, this);
            }else{
                networkHandler = new NetworkRMIHandler(host, port,this);
            }
        }catch (Exception e){
            System.out.println("Connection failed");
            System.err.println(e.getMessage()+"\n"+e.getStackTrace());
            System.exit(1);
        }
        localIP = null;
        try {
            InetAddress ipAddress = InetAddress.getLocalHost();
            localIP = ipAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        connectionInfo = new ConnectionInfo(localIP,port, null, connectionType);
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(connectionFile, false));
            bufferedWriter.write(new Gson().toJson(connectionInfo)+"\n");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void reconnectionProcess(){
        String json = null;
        try {
            try {
                fileReader = new FileReader(connectionFile);
                bufferedReader = new BufferedReader(fileReader);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            json  = bufferedReader.readLine();
            connectionInfo = new Gson().fromJson(json, ConnectionInfo.class);
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(connectionInfo == null){
            System.err.println("AAAAAAAAAAA: "+json);
        }else{
            switch(connectionInfo.getConnectionType()){
                case SOCKET:
                    networkHandler = new NetworkSocketHandler(connectionInfo.getIp(), connectionInfo.getPort(), this);
                    break;
                case RMI:
                    try {
                        networkHandler = new NetworkRMIHandler(connectionInfo.getIp(), connectionInfo.getPort(), this);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
            //previousNickname = connectionInfo.getNickname();
            myNick = connectionInfo.getNickname();
        }
    }
    /**
     * This method is used to connect to the server
     * @author Valentino Guerrini & Paolo Gennaro & Patrick Poggi
     */
    private void connect(){
        //ConnectionType connectionType = null;
        File directory = this.getDirectory();
        System.out.println("Alright, your connection info will be stored locally at: "
                +ANSIParameters.CYAN
                +directoryPath
                +ANSIParameters.CRESET
                +"\n"
                +ANSIParameters.RED
                +"Please, try to remember it, you might need it in the future."
                +ANSIParameters.CRESET);
        try {
            connectionFile = new File(directoryPath+"/ConnectionInfo.txt");
            if(!isReconnecting){
                if (directory.mkdir()) {
                    System.out.println("The directory was created successfully.");
                    if(connectionFile.createNewFile()==false){
                        System.out.println("File already exists, but it should not.");
                    }
                } else {
                    System.out.println("Failed to create the directory.");
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred."+e.getMessage());
        }


        String localIP = null;
        if(!isReconnecting){
            connectionProcess();
        }else{
            reconnectionProcess();
        }
    }


    /**
     * This method is used to manage the MVEvents sent by the model
     * @param event is the event sended by the model
     * @author ValentinoGuerrini
     */
    @Override
    public void onMVEvent (MVEvent event){
        String methodName = event.getMethodName();


        switch(methodName) {
            case "onModifiedChatEvent" -> {
                onModifiedChatEvent((Message)event.getValue());
                return;
            }
            case "onModifiedBookshelfEvent" -> {
                onModifiedBookshelfEvent(event.getMatch());
            }
            case "onModifiedLivingRoomEvent" -> {
                onModifiedLivingRoomEvent(event.getMatch());
            }
            case "onModifiedMatchEndedEvent" -> {
                onModifiedMatchEndedEvent(event.getMatch());
            }
            case "onModifiedPointsEvent" -> {
                onModifiedPointsEvent(event.getMatch());
            }
            case "onMatchStartedEvent" -> {
                onMatchStartedEvent(event.getMatch());
            }
            case "onModifiedTurnEvent" -> {
                onModifiedTurnEvent(event.getMatch());
            }

        }

        if(!chatIsOpened && !MatchEnded){
            print();
        }

    }

    /**
     * This method is used to setup the board at the start of the match
     * @param match is a reference to the match object
     */
    private void onMatchStartedEvent(LightMatch match){
        matchStarted = true;
        numberPlayers = match.getPlayers().size();
        for(int i = 0; i < numberPlayers; i++){
            players.put(i,match.getPlayers().get(i));
            if (match.getPlayers().get(i).getPlayerNickName().equals(myNick)){
                me = match.getPlayers().get(i);
            }
        }
        System.out.println("Match started");
        System.out.println("You are " + myNick );
        //printHelp();
        printLivingRoom(renderLivingroom(match.getLivingRoom().getTileMatrix()));
        printPersonalGoal(renderPersonalGoalCard(me.getPersonalGoalCard().getTileMatrix(),
                me.getPersonalGoalCard().getCardID()));
        CommonGoalCard commonGoal= match.getCommonGoals()[0];
        printCommonGoal1(renderCommonGoalCard(commonGoal));
        printPoints1(renderPointTile(commonGoal.getPointsTiles().get(commonGoal.getPointsTiles().size()-1)));
        printDescription1(commonGoal.getCommonGoalDescription());
        commonGoal = match.getCommonGoals()[1];
        printCommonGoal2(renderCommonGoalCard(commonGoal));
        printPoints2(renderPointTile(commonGoal.getPointsTiles().get(commonGoal.getPointsTiles().size()-1)));
        printDescription2(commonGoal.getCommonGoalDescription());

        switch(numberPlayers) {
            case 2 -> {
                printBookshelf1(renderBookshelf(players.get(0).getBookshelf().getTileMatrix()));
                printEndTile1(EMPTYSPOT);
                printPlayer1Points(EMPTYSPOT, EMPTYSPOT);

                printBookshelf2(renderBookshelf(players.get(1).getBookshelf().getTileMatrix()));
                printEndTile2(EMPTYSPOT);
                printPlayer2Points(EMPTYSPOT, EMPTYSPOT);
            }
            case 3 -> {
                printBookshelf1(renderBookshelf(players.get(0).getBookshelf().getTileMatrix()));
                printEndTile1(EMPTYSPOT);
                printPlayer1Points(EMPTYSPOT, EMPTYSPOT);

                printBookshelf2(renderBookshelf(players.get(1).getBookshelf().getTileMatrix()));
                printEndTile2(EMPTYSPOT);
                printPlayer2Points(EMPTYSPOT, EMPTYSPOT);

                printBookshelf3(renderBookshelf(players.get(2).getBookshelf().getTileMatrix()));
                printEndTile3(EMPTYSPOT);
                printPlayer3Points(EMPTYSPOT, EMPTYSPOT);
            }
            case 4 -> {
                printBookshelf1(renderBookshelf(players.get(0).getBookshelf().getTileMatrix()));
                printEndTile1(EMPTYSPOT);
                printPlayer1Points(EMPTYSPOT, EMPTYSPOT);

                printBookshelf2(renderBookshelf(players.get(1).getBookshelf().getTileMatrix()));
                printEndTile2(EMPTYSPOT);
                printPlayer2Points(EMPTYSPOT, EMPTYSPOT);

                printBookshelf3(renderBookshelf(players.get(2).getBookshelf().getTileMatrix()));
                printEndTile3(EMPTYSPOT);
                printPlayer3Points(EMPTYSPOT, EMPTYSPOT);

                printBookshelf4(renderBookshelf(players.get(3).getBookshelf().getTileMatrix()));
                printEndTile4(EMPTYSPOT);
                printPlayer4Points(EMPTYSPOT, EMPTYSPOT);
            }
        }
        String[] names = new String[numberPlayers];

        for (int i = 0; i < numberPlayers; i++) {
            names[i] = players.get(i).getPlayerNickName();
        }

        printNames(names, match.getCurrentPlayer().getPlayerNickName());

    }

    /**
     * This method is used to print the names of the players
     * @param match is a reference to the match object
     */
    private void onModifiedTurnEvent(LightMatch match){
        String[] names = new String[numberPlayers];

        for (int i = 0; i < numberPlayers; i++) {
            names[i] = players.get(i).getPlayerNickName();
        }

        printNames(names, match.getCurrentPlayer().getPlayerNickName());        ;
    }

    /**
     * This method is used to update the points tiles of the players
     * @param match is the light version of the match
     * @author Valentino Guerrini & Paolo Gennaro
     */
    private void onModifiedPointsEvent(LightMatch match){
        switch(numberPlayers) {
            case 2 -> {
                if(match.getPlayers().get(0).getPointsTiles().size() == 1) {
                    printPlayer1Points(renderPointTile(match.getPlayers().get(0).getPointsTiles().get(0)), EMPTYSPOT);
                }else if(match.getPlayers().get(0).getPointsTiles().size() == 2){
                    printPlayer1Points(renderPointTile(match.getPlayers().get(0).getPointsTiles().get(0)),renderPointTile(match.getPlayers().get(0).getPointsTiles().get(1)));
                }
                if(match.getPlayers().get(1).getPointsTiles().size() == 1) {
                    printPlayer2Points(renderPointTile(match.getPlayers().get(1).getPointsTiles().get(0)), EMPTYSPOT);
                }else if(match.getPlayers().get(1).getPointsTiles().size() == 2){
                    printPlayer2Points(renderPointTile(match.getPlayers().get(1).getPointsTiles().get(0)), renderPointTile(match.getPlayers().get(1).getPointsTiles().get(1)));
                }
            }
            case 3->{
                if(match.getPlayers().get(0).getPointsTiles().size() == 1) {
                    printPlayer1Points(renderPointTile(match.getPlayers().get(0).getPointsTiles().get(0)), EMPTYSPOT);
                }else if(match.getPlayers().get(0).getPointsTiles().size() == 2){
                    printPlayer1Points(renderPointTile(match.getPlayers().get(0).getPointsTiles().get(0)), renderPointTile(match.getPlayers().get(0).getPointsTiles().get(1)));
                }

                if(match.getPlayers().get(1).getPointsTiles().size() == 1) {
                    printPlayer2Points(renderPointTile(match.getPlayers().get(1).getPointsTiles().get(0)), EMPTYSPOT);
                }else if(match.getPlayers().get(1).getPointsTiles().size() == 2){
                    printPlayer2Points(renderPointTile(match.getPlayers().get(1).getPointsTiles().get(0)), renderPointTile(match.getPlayers().get(1).getPointsTiles().get(1)));
                }

                if(match.getPlayers().get(2).getPointsTiles().size() == 1) {
                    printPlayer3Points(renderPointTile(match.getPlayers().get(2).getPointsTiles().get(0)), EMPTYSPOT);
                }else if(match.getPlayers().get(2).getPointsTiles().size() == 2){
                    printPlayer3Points(renderPointTile(match.getPlayers().get(2).getPointsTiles().get(0)), renderPointTile(match.getPlayers().get(2).getPointsTiles().get(1)));
                }
            }
            case 4->{
                if(match.getPlayers().get(0).getPointsTiles().size() == 1) {
                    printPlayer1Points(renderPointTile(match.getPlayers().get(0).getPointsTiles().get(0)), EMPTYSPOT);
                }else if(match.getPlayers().get(0).getPointsTiles().size() == 2){
                    printPlayer1Points(renderPointTile(match.getPlayers().get(0).getPointsTiles().get(0)), renderPointTile(match.getPlayers().get(0).getPointsTiles().get(1)));
                }

                if(match.getPlayers().get(1).getPointsTiles().size() == 1) {
                    printPlayer2Points(renderPointTile(match.getPlayers().get(1).getPointsTiles().get(0)), EMPTYSPOT);
                }else if(match.getPlayers().get(1).getPointsTiles().size() == 2){
                    printPlayer2Points(renderPointTile(match.getPlayers().get(1).getPointsTiles().get(0)), renderPointTile(match.getPlayers().get(1).getPointsTiles().get(1)));
                }

                if(match.getPlayers().get(2).getPointsTiles().size() == 1) {
                    printPlayer3Points(renderPointTile(match.getPlayers().get(2).getPointsTiles().get(0)), EMPTYSPOT);
                }else if(match.getPlayers().get(2).getPointsTiles().size() == 2) {
                    printPlayer3Points(renderPointTile(match.getPlayers().get(2).getPointsTiles().get(0)), renderPointTile(match.getPlayers().get(2).getPointsTiles().get(1)));
                }

                if(match.getPlayers().get(3).getPointsTiles().size() == 1) {
                    printPlayer4Points(renderPointTile(match.getPlayers().get(3).getPointsTiles().get(0)), EMPTYSPOT);
                }else if(match.getPlayers().get(3).getPointsTiles().size() == 2){
                    printPlayer4Points(renderPointTile(match.getPlayers().get(3).getPointsTiles().get(0)), renderPointTile(match.getPlayers().get(3).getPointsTiles().get(1)));
                }
            }

        }
        printPoints1(renderPointTile(match.getCommonGoals()[0].getPointsTiles().get(match.getCommonGoals()[0].getPointsTiles().size()-1)));
        printPoints2(renderPointTile(match.getCommonGoals()[1].getPointsTiles().get(match.getCommonGoals()[1].getPointsTiles().size()-1)));
        if(match.getFirstToFinish()!=null){
            int i;
            for(i=0;i<numberPlayers;i++){
                if(match.getFirstToFinish().getPlayerNickName().equals(players.get(i).getPlayerNickName())){
                    break;
                }
            }
            switch(i){
                case 0 -> printEndTile1(ENDTILE);
                case 1 -> printEndTile2(ENDTILE);
                case 2 -> printEndTile3(ENDTILE);
                case 3 -> printEndTile4(ENDTILE);
            }
        }
    }

    /**
     * This method is used to update the bookshelf of the players
     * @param match is the light version of the match
     * @author Valentino Guerrini & Paolo Gennaro
     */
    private void onModifiedBookshelfEvent(LightMatch match){
        switch(numberPlayers) {
            case 2 -> {
                printBookshelf1(renderBookshelf(match.getPlayers().get(0).getBookshelf().getTileMatrix()));
                printBookshelf2(renderBookshelf(match.getPlayers().get(1).getBookshelf().getTileMatrix()));
            }
            case 3->{
                printBookshelf1(renderBookshelf(match.getPlayers().get(0).getBookshelf().getTileMatrix()));
                printBookshelf2(renderBookshelf(match.getPlayers().get(1).getBookshelf().getTileMatrix()));
                printBookshelf3(renderBookshelf(match.getPlayers().get(2).getBookshelf().getTileMatrix()));
            }case 4->{
                printBookshelf1(renderBookshelf(match.getPlayers().get(0).getBookshelf().getTileMatrix()));
                printBookshelf2(renderBookshelf(match.getPlayers().get(1).getBookshelf().getTileMatrix()));
                printBookshelf3(renderBookshelf(match.getPlayers().get(2).getBookshelf().getTileMatrix()));
                printBookshelf4(renderBookshelf(match.getPlayers().get(3).getBookshelf().getTileMatrix()));
            }

        }

    }

    /**
     * This method is used to update the LivingRoom
     * @param match is the light version of the match
     * @author Valentino Guerrini
     */
    private void onModifiedLivingRoomEvent(LightMatch match){
        printLivingRoom(renderLivingroom(match.getLivingRoom().getTileMatrix()));
    }

    /**
     * This method is used to notify players that a player completed his bookshelf and took the "MatchEnded" tile
     * @param match is the light version of the match
     */
    private void onModifiedMatchEndedEvent(LightMatch match){
        finalScores= (HashMap<Integer, Integer>) match.getScores();
        winner = match.getWinner();
    }

    /**
     * This method is used to manage the SelectViewEvent
     * @param event is the SelectViewEvent that contains the type of the view to be opened
     */
    @Override
    public void onSelectViewEvent(SelectViewEvent event){
         String view = event.getType();
         switch(view){
             case "ChatONView" -> onOpenChatEvent();
             case "ChatOFFView" -> onCloseChatEvent();
             case "LoginView" -> onLoginViewEvent(event);
             case "GameView" -> onGameViewEvent(event);
             case "EndedMatchView" -> onEndedMatchViewEvent(event);

             default -> {
                 currentView = event;
                 print();
             }


         }

    }


    /**
     * This method is to show the LoginView when a LoginViewEvent is received
     * @param event
     */
    private void onLoginViewEvent(SelectViewEvent event){
        currentView = event;
        LoginView loginEvent = (LoginView) event;
        System.out.print(ANSIParameters.CLEAR_SCREEN + ANSIParameters.CURSOR_HOME);
        System.out.flush();

        if(loginEvent.isFirstToJoin()) {
            System.out.println("Welcome to MyShelfie!");
            if(event.getMessage().contains("Insert")){
                System.out.println("You are the first player to join the match");
                System.out.println("Please insert your nickname and the number of players for the match: ");
                System.out.print("> ");
            }else{
                System.out.println(event.getMessage());
                System.out.print("> ");
            }

        }else{
            if(event.getMessage()!=null && event.getMessage().contains("Waiting")) {
                System.out.println(ANSIParameters.RED + event.getMessage() + ANSIParameters.CRESET);

            }else if (!event.getMessage().equals("Insert your username")) {
                System.out.println(ANSIParameters.RED + event.getMessage() + ANSIParameters.CRESET);

            }else{
                System.out.println("Welcome to MyShelfie!");
                System.out.println("Please insert your nickname: ");
            }
            System.out.print("> ");
        }

    }

    /**
     * This method is used to show the GameView when a GameViewEvent is received
     * @param event
     */
    private void onGameViewEvent(SelectViewEvent event){
        currentView = event;
        if (!matchStarted) {
            System.out.print(ANSIParameters.CLEAR_SCREEN + ANSIParameters.CURSOR_HOME);
            System.out.flush();
            System.out.println("Hi "+ ANSIParameters.BLUE + myNick+ ANSIParameters.CRESET+" welcome to MyShelfie!");
        }
        print();

    }

    /**
     * This method is used to show the EndedMatchView when a EndedMatchViewEvent is received
     * @param event
     */
    private void onEndedMatchViewEvent(SelectViewEvent event){
        MatchEnded = true;
        currentView = event;

        System.out.print(ANSIParameters.CLEAR_SCREEN + ANSIParameters.CURSOR_HOME);
        System.out.flush();

        System.out.println("Match ended!");

        System.out.println("The final scores are: ");
        Player p;

        for(int i=0;i<numberPlayers;i++){
            p =players.get(i);
            System.out.println(ANSIParameters.RED + p.getPlayerNickName() + ANSIParameters.CRESET + " with: " + ANSIParameters.MAGENTA + finalScores.get(p.getPlayerID()) + " points" + ANSIParameters.CRESET);

        }
        System.out.println("The winner is: " + ANSIParameters.YELLOW + winner.getPlayerNickName() + ANSIParameters.CRESET );
        System.out.println("> ");
    }

    //CHAT

    /**
     * This method is used to show the chat when a ChatEvent is received
     */
    public void onOpenChatEvent(){
        chatIsOpened = true;
        System.out.print(ANSIParameters.CLEAR_SCREEN + ANSIParameters.CURSOR_HOME);
        System.out.flush();
        System.out.println("Chat:");

        for(String s : chat){
            String[] subStrings = s.split(" ");
            System.out.print(subStrings[0] + " " + ANSIParameters.RED + subStrings[1] + ANSIParameters.CRESET + " "  +  ANSIParameters.BLUE + "to " + subStrings[2] + ":" + ANSIParameters.CRESET + " ");
            for(int i = 3; i< subStrings.length; i++){
                System.out.print(subStrings[i] + " ");
            }
            System.out.println();
        }
        System.out.print("> ");

    }

    /**
     * This method is used to close the chat when a CloseChatEvent is received
     */
    public void onCloseChatEvent(){
        chatIsOpened = false;
        print();
    }

    /**
     * This method is used to update the chat, due to a message, through a ModifiedChatEvent
     * @param message is the message that has been sent
     */
    public void onModifiedChatEvent(Message message){
        String s = MessageToString(message);

        chat.add(s);
        if(chatIsOpened){
            if(message.getReceiver() != null) {
                System.out.print("\b\b\b");
                System.out.println("[" + message.getTimeSent() + "]" + " " + ANSIParameters.RED + message.getSender().getPlayerNickName()  + ANSIParameters.CRESET + " " + ANSIParameters.BLUE + "to @" + message.getReceiver().getPlayerNickName()+ ":" + ANSIParameters.CRESET + " " + message.getContent());
            }else{
                System.out.print("\b\b\b");
                System.out.println("[" + message.getTimeSent() + "]" + " " + ANSIParameters.RED + message.getSender().getPlayerNickName() + ANSIParameters.CRESET + " " + ANSIParameters.BLUE + "to @All:" + ANSIParameters.CRESET + " " + message.getContent());
            }
            System.out.print("> ");
        }
    }

    /**
     * This method is used to convert a type Message to a string
     */
    private String MessageToString(Message message){
        String receiver;
        if(message.getReceiver() == null) {
            receiver = "All";
        }else{
            receiver = message.getReceiver().getPlayerNickName();
        }

        String s = "[" + message.getTimeSent() + "]" + " " + message.getSender().getPlayerNickName() + " @" + receiver + " " + message.getContent();
        return s;

    }

    /**
     * This method is used when the user types "info" to show the available commands
     */
    private void printInfo(){
        System.out.println(ANSIParameters.GREEN);
        if(currentView == null){
            System.out.println( "info        : show this message\n"+
                                "help        : show commands' syntax\n"+
                                "play        : connect to the server\n"+
                                "quit        : quit the game\n");
        }else if (currentView.getType().equals("LoginView")) {
            System.out.printf("%-10s%s%n", "info", "play");
            System.out.printf("%-10s%s%n", "help","quit");
        } else if (currentView.getType().equals("PickingTilesGameView")) {
            System.out.printf("%-10s%s%n", "info", "pick");
            System.out.printf("%-10s%s%n", "checkout", "open");
            System.out.printf("%-10s%s%n", "close", "send");
            System.out.printf("%-10s%s%n", "help", "quit");
        } else if (currentView.getType().equals("InsertingTilesGameView")) {
            System.out.printf("%-10s%s%n", "info", "select");
            System.out.printf("%-10s%s%n", "open", "close");
            System.out.printf("%-10s%s%n", "send", "help");
            System.out.printf("%-10s%s%n", "quit", "");
        } else if (currentView.getType().equals("EndedMatchView")) {
            System.out.printf("%-10s%s%n", "info", "open");
            System.out.printf("%-10s%s%n", "close", "send");
            System.out.printf("%-10s%s%n", "help", "quit");
        } else if (currentView.getType().equals("GameView")) {
            System.out.printf("%-10s%s%n", "info", "open");
            System.out.printf("%-10s%s%n", "close", "send");
            System.out.printf("%-10s%s%n", "help", "quit");
        } else if (currentView.getType().equals("ChatONView")) {
            System.out.printf("%-10s%s%n", "info", "close");
            System.out.printf("%-10s%s%n", "send", "help");
            System.out.printf("%-10s%s%n", "quit", "");
        } else if (currentView.getType().equals("ChatOFFView")) {
            System.out.printf("%-10s%s%n", "info", "open");
            System.out.printf("%-10s%s%n", "send", "help");
            System.out.printf("%-10s%s%n", "quit", "");
        }
        System.out.println(ANSIParameters.CRESET);
        System.out.print("> ");
    }

    public void printHelp(){
        System.out.println(ANSIParameters.YELLOW + "Commands:" + ANSIParameters.CRESET);
        System.out.println( ANSIParameters.YELLOW+
                            "\"pick row_letter,livingroom_column_index\"      : to pick a tile\n"+
                            "\"checkout\"                                     : to checkout selected tiles\n"+
                            "\"select bookshelf_column_index\"                : to select the column of the bookshelf\n"+
                            "\"open\"                                         : to open the chat\n"+
                            "\"close\"                                        : to close the chat\n"+
                            "\"send \"message\"\"                             : to send a message\n"+
                            ANSIParameters.CRESET);
    }

    private void deleteDirectory(){
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();
        if(files != null) {
            for (File f : files) {
                f.delete();
            }
        }
        directory.delete();
    }
    private void manageQuitting(){
        if(myNick == null || myNick.equals("")){
            System.out.println(ANSIParameters.RED+"ATTENTION:"+ANSIParameters.CRESET+
                    "Since you haven't logged in yet, you won't be remembered");
            deleteDirectory();
        }else{
            System.out.println("Would you like to remember you've been connected and playing this match? (y/n)");
            String input = scanner.nextLine();
            if(input.equalsIgnoreCase("n")){
                deleteDirectory();
            }
        }
    }

    /**
     * This method parses the input and calls/gives the right method/output after it
     * @param input the input from the user
     */
    private void parseInput(String input){
        if(input.equals("info")) {
            printInfo();
            return;
        }
        if(input.equals("help")){
            printHelp();
            return;
        }
        if(input.equals("quit")){
            //TODO: manage quitting
            manageQuitting();
            System.out.println("Ok, bye!");
            System.exit(0);
            return;
        }
        String[] inputArray = input.split(" ");
        if(currentView!=null && currentView.getType().equals("LoginView")) {
            LoginView loginview = (LoginView) currentView;
            if(loginview.isFirstToJoin()){
                if(inputArray.length == 2){
                    if(inputArray[0].length() <= 20){
                        myNick = inputArray[0];
                        connectionInfo.setNickname(myNick);
                        try {
                            bufferedWriter = new BufferedWriter(new FileWriter(connectionFile, false));
                            bufferedWriter.write(new Gson().toJson(connectionInfo)+"\n");
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            numberPlayers = Integer.parseInt(inputArray[1]);
                            if(numberPlayers < 2 || numberPlayers > 4){
                                throw new NumberFormatException();
                            }
                            networkHandler.onVCEvent(new LoginEvent(myNick, numberPlayers));
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } catch (NumberFormatException e){
                            System.out.println("Invalid number of players! Number of players must be between 2 and 4");
                            System.out.print("> ");
                        }
                    }else{
                        System.out.println("Nickname must be max 20 char long! Try again: ");
                        System.out.print("> ");
                    }
                }else {
                    System.out.println("Please insert your nickname and the number of players for the match (between 2 and 4): ");
                    System.out.print("> ");
                }
            }else{
                if (inputArray.length == 1) {
                    if(inputArray[0].length() <= 20){
                        myNick = inputArray[0];
                        connectionInfo.setNickname(myNick);
                        try {
                            bufferedWriter = new BufferedWriter(new FileWriter(connectionFile, false));
                            bufferedWriter.write(new Gson().toJson(connectionInfo)+"\n");
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        try {
                            networkHandler.onVCEvent(new LoginEvent(myNick));
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }else{
                        System.out.println("Nickname must be max 20 char long! Try again: ");
                        System.out.print("> ");
                    }
                } else {
                    System.out.println("Nickname must be one word");
                    System.out.print("> ");
                }
            }

        }else{
            switch (inputArray[0]){
                case "info" -> {
                    printInfo();
                }
                case "play" -> {
                    //you can only use this command if the game is not started yet
                    if(currentView == null) {
                        new Thread(networkHandler).start();
                    }else{
                        System.out.println("You cannot use this command now!");
                        System.out.print("> ");
                    }
                }
                case "quit" -> {
                    System.out.println("Bye!");
                    System.exit(0);
                }
                case "select" -> {
                    //you can only use this command if you are in the InsertingTilesGameView
                    if(currentView.getType().equals("InsertingTilesGameView")){
                        if(inputArray.length == 2){
                            try{
                                int index = Integer.parseInt(inputArray[1]);
                                if(index<0 || index>4){
                                    System.out.println("Invalid input");
                                    System.out.print("> ");
                                    break;
                                }
                                networkHandler.onVCEvent(new SelectColumn(index));
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (NumberFormatException e){
                                System.out.println("Invalid input");
                                System.out.print("> ");
                            }
                        }else{
                            System.out.println("Invalid input");
                            System.out.print("> ");
                        }
                    }else{
                        System.out.println("You cannot use this command now!");
                        System.out.print("> ");
                    }
                }
                case "pick" -> {
                    //You can use this command only if you are in the PickingTilesGameView
                    if(currentView.getType().equals("PickingTilesGameView")){
                        if(inputArray.length == 2){
                            String[] coordinates = inputArray[1].split(",");
                            char row;
                            int column;
                            try {
                                row = coordinates[0].charAt(0);
                                if(coordinates[0].length() != 1 || row<'a' || row>'i'){
                                    System.out.println("Invalid input: row must be between a and i!");
                                    System.out.print("> ");
                                    break;
                                }
                                column = Integer.parseInt(coordinates[1]);
                                if(column < 0 || column > 8){
                                    System.out.println("Invalid input: column must be between 0 and 8!");
                                    System.out.print("> ");
                                    break;
                                }
                            }catch (NumberFormatException e){
                                System.out.println("Invalid input: column must be a number!");
                                System.out.print("> ");
                                break;
                            }
                            int[] coordinatesInt = new int[2];
                            if(row>='a' && row <='i' && column>=0 && column<=8) {
                                coordinatesInt[0] = row - 'a';
                                coordinatesInt[1] = column;
                                try {
                                    networkHandler.onVCEvent(new ClickOnTile(coordinatesInt));
                                    countOfPicks++;
                                } catch (Exception e){
                                    System.out.println("Invalid input");
                                    System.out.print("> ");
                                }
                            }
                        }else{
                            System.out.println("Invalid input");
                            System.out.print("> ");
                        }
                    }else{
                        System.out.println("You cannot use this command now!");
                        System.out.print("> ");
                    }
                }
                case "checkout" ->{
                    //If countOfPicks is not 0, it means that the player has picked at least one tile
                    if(countOfPicks != 0) {
                        if (inputArray.length == 1) {
                            try {
                                networkHandler.onVCEvent(new CheckOutTiles());
                                countOfPicks = 0;
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            System.out.println("Invalid input");
                            System.out.print("> ");

                        }
                    }else{
                        System.out.println("You cannot use this command now!");
                        System.out.print("> ");
                    }
                }
                case "open" ->{
                    //If current view is not null, it means that the game has started
                    if(currentView != null) {
                        if (inputArray.length == 1) {
                            try {
                                networkHandler.onVCEvent(new OpenChat());
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            System.out.println("Invalid input");
                            System.out.print("> ");
                        }
                    }else{
                        System.out.println("You cannot use this command now! Wait for the match to start!");
                        System.out.print("> ");
                    }
                }
                case "close" ->{
                    //If current view is not null, it means that the game has started
                    if(currentView != null) {
                        if (inputArray.length == 1) {
                            try {
                                networkHandler.onVCEvent(new CloseChat());
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            System.out.println("Invalid input");
                            System.out.print("> ");
                        }
                    }else {
                        System.out.println("You cannot use this command now! Wait for the match to start!");
                        System.out.print("> ");
                    }
                }
                case "send" ->{
                    //If current view is not null, it means that the game has started
                    if(currentView != null) {
                        if(!chatIsOpened){
                            System.out.println("You cannot use this command now! Open the chat first!");
                            System.out.print("> ");
                            break;
                        }
                        //If you are the only one connected you cannot write in the chat
                        if(players.size()<=1){
                            System.out.println("You cannot use this command now! Wait for other players to join!");
                            System.out.print("> ");
                            break;
                        }
                        if (inputArray.length >= 3) {
                            try {
                                String message = new String("");
                                for (int i = 2; i < inputArray.length; i++) {
                                    message = message + inputArray[i] + " ";
                                }
                                Message messageToSend = null;
                                boolean flag = false;
                                if (inputArray[1].equals("@All")) {
                                    messageToSend = new Message(this.me, message, LocalTime.now());
                                    flag = true;
                                } else {

                                    if(("@" + this.me.getPlayerNickName()).equals(inputArray[1])){
                                        System.out.println("You cannot send a message to yourself");
                                        System.out.print("> ");
                                        break;
                                    }

                                    for (Integer i : this.players.keySet()) {
                                        if (("@" + this.players.get(i).getPlayerNickName()).equals(inputArray[1])){
                                            messageToSend = new Message(this.me, message, LocalTime.now(), this.players.get(i));
                                            flag = true;
                                        }
                                    }
                                    if (!flag) {
                                        System.out.println("nickname does not exists");
                                        System.out.print("> ");
                                        break;
                                    }
                                }
                                if (flag) {
                                    networkHandler.onVCEvent(new SendMessage(messageToSend));
                                }
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            System.out.println("Invalid input");
                            System.out.print("> ");
                        }
                    }else{
                        System.out.println("You cannot use this command now! Wait for the match to start!");
                        System.out.print("> ");
                    }
                }
                //If you don't enter a valid command, the program will ask you to enter a valid one
                default -> {
                    if(input != ""){
                        System.out.println("Invalid input");
                        System.out.print("> ");
                    }
                }
            }
        }
    }



    //refresh the board

    /**
     * This method is used to print the living room board
     * @param livingRoom
     */
    private void printLivingRoom(String[][] livingRoom){

        for(int i = 0; i < livingRoom.length; i++){
            for(int j = 0; j < livingRoom[i].length; j++){
                board.setChar(i + LIVINGROOM_I ,j + LIVINGROOM_J,livingRoom[i][j]);
            }
        }

    }

    /**
     * This method is used to print the personalGoalCard of the player
     * @param personalGoal
     */
    private void printPersonalGoal(String[][] personalGoal){
        int i=0;
        for( char c : "Personal Goal".toCharArray()){
            board.setChar(CARDLINE -1,PERSONAL_J+i,String.valueOf(c));
            i++;
        }

        for(i = 0; i < personalGoal.length; i++){
            for(int j = 0; j < personalGoal[i].length; j++){
                board.setChar(i + CARDLINE,j + PERSONAL_J,personalGoal[i][j]);
            }
        }
    }

    /**
     * This method is used to print the first commonGoalCard of the game
     * @param commonGoal
     */
    private void printCommonGoal1(char[][] commonGoal){
        int i=0;
        for(char c : "#1 CommonGoal".toCharArray()){
            board.setChar(CARDLINE -1,COMMON_1_J+i,String.valueOf(c));
            i++;
        }

        for(i = 0; i < commonGoal.length; i++){
            for(int j = 0; j < commonGoal[i].length; j++){
                board.setChar(i + CARDLINE,j + COMMON_1_J,String.valueOf(commonGoal[i][j]));
            }
        }
    }

    /**
     * This method is used to print the remaining points of the first commonGoalCard of the game
     * @param points
     */
    private void printPoints1(String[][] points){
        for(int i = 0; i < points.length; i++){
            for(int j = 0; j < points[i].length; j++){
                board.setChar(i + CARDLINE,j + POINTS_1_J,String.valueOf(points[i][j]));
            }
        }
    }

    /**
     * This method is used to print the second commonGoalCard of the game
     * @param commonGoal
     */
    private void printCommonGoal2(char[][] commonGoal){
        int i=0;
        for(char c : "#2 CommonGoal".toCharArray()){
            board.setChar(CARDLINE -1,COMMON_2_J+i,String.valueOf(c));
            i++;
        }

        for(i = 0; i < commonGoal.length; i++){
            for(int j = 0; j < commonGoal[i].length; j++){
                board.setChar(i + CARDLINE,j + COMMON_2_J,String.valueOf(commonGoal[i][j]));
            }
        }
    }

    /**
     * This method is used to print the remaining points of the second commonGoalCard of the game
     * @param points
     */
    private void printPoints2(String[][] points){
        for(int i = 0; i < points.length; i++){
            for(int j = 0; j < points[i].length; j++){
                board.setChar(i + CARDLINE,j + POINTS_2_J,points[i][j]);
            }
        }
    }

    /**
     * This method is used to print the description of the first commonGoalCard of the game
     * @param description
     */
    private void printDescription1(String[] description){
        for (int i = 0; i < description.length; i++){
            for(int j = 0; j < description[i].length(); j++){
                board.setChar(i + DESCRIPTION_I,j + DESCRIPTION_1_J,String.valueOf(description[i].charAt(j)));
            }
        }

    }

    /**
     * This method is used to print the description of the second commonGoalCard of the game
     * @param description
     */
    private void printDescription2(String[] description){
        for (int i = 0; i < description.length; i++){
            for(int j = 0; j < description[i].length(); j++){
                board.setChar(i + DESCRIPTION_I,j + DESCRIPTION_2_J,String.valueOf(description[i].charAt(j)));
            }
        }

    }

    /**
     * This method is used to print the first BookShelf of the game
     * @param bookshelf
     */
    private void printBookshelf1(String[][] bookshelf){
        for(int i = 0; i < bookshelf.length; i++){
            for(int j = 0; j < bookshelf[i].length; j++){
                board.setChar(i + BOOKSHELF_I,j + BOOKSHELF_1_J,bookshelf[i][j]);
            }
        }

    }

    /**
     * This method is used to print the second BookShelf of the game
     * @param bookshelf
     */
    private void printBookshelf2(String[][] bookshelf){
        for(int i = 0; i < bookshelf.length; i++){
            for(int j = 0; j < bookshelf[i].length; j++){
                board.setChar(i + BOOKSHELF_I,j + BOOKSHELF_2_J,bookshelf[i][j]);
            }
        }

    }

    /**
     * This method is used to print the third BookShelf of the game, if needed
     * @param bookshelf
     */
    private void printBookshelf3(String[][] bookshelf){
        for(int i = 0; i < bookshelf.length; i++){
            for(int j = 0; j < bookshelf[i].length; j++){
                board.setChar(i + BOOKSHELF_I,j + BOOKSHELF_3_J,bookshelf[i][j]);
            }
        }

    }

    /**
     * This method is used to print the fourth BookShelf of the game, if needed
     * @param bookshelf
     */
    private void printBookshelf4(String[][] bookshelf){
        for(int i = 0; i < bookshelf.length; i++){
            for(int j = 0; j < bookshelf[i].length; j++){
                board.setChar(i + BOOKSHELF_I,j + BOOKSHELF_4_J,bookshelf[i][j]);
            }
        }

    }

    /**
     * This method is used to print the spot for the EndTile of the first bookshelf
     * @param endTile
     */
    private void printEndTile1(String[][] endTile){
        for(int i = 0; i < endTile.length; i++){
            for(int j = 0; j < endTile[i].length; j++){
                board.setChar(i + END_TILE_I,j + END_TILE_1_J,endTile[i][j]);
            }
        }

    }

    /**
     * This method is used to print the spot for the EndTile of the second bookshelf
     * @param endTile
     */
    private void printEndTile2(String[][] endTile){
        for(int i = 0; i < endTile.length; i++){
            for(int j = 0; j < endTile[i].length; j++){
                board.setChar(i + END_TILE_I,j + END_TILE_2_J,endTile[i][j]);
            }
        }

    }

    /**
     * This method is used to print the spot for the EndTile of the third bookshelf, if needed
     * @param endTile
     */
    private void printEndTile3(String[][] endTile){
        for(int i = 0; i < endTile.length; i++){
            for(int j = 0; j < endTile[i].length; j++){
                board.setChar(i + END_TILE_I,j + END_TILE_3_J,endTile[i][j]);
            }
        }

    }

    /**
     * This method is used to print the spot for the EndTile of the fourth bookshelf, if needed
     * @param endTile
     */
    private void printEndTile4(String[][] endTile){
        for(int i = 0; i < endTile.length; i++){
            for(int j = 0; j < endTile[i].length; j++){
                board.setChar(i + END_TILE_I,j + END_TILE_4_J,endTile[i][j]);
            }
        }

    }

    /**
     * This method is used to print for every bookshelf the name of the player assigned to it
     * @param names
     * @param current
     */
    private void printNames(String[] names, String current){
        int length;
        int number;

        for(int i = 0; i < names.length; i++){
            length = 0;
            number=BOOKSHELF_1_J+ 38*i;
            while(length*2 +names[i].length() < NAME_LENGTH){
                length++;
            }
            if(names[i].equals(current)){
                board.setChar(PLAYER_NAME_I, number+length-1, ANSIParameters.GREEN);
                board.setChar(PLAYER_NAME_I, number+length+names[i].length(), "\033[0m");
            }else{
                board.setChar(PLAYER_NAME_I, number+length-1, ANSIParameters.RED);
                board.setChar(PLAYER_NAME_I, number+length+names[i].length(), "\033[0m");
            }
            for(int j = 0; j < names[i].length(); j++){
                board.setChar(PLAYER_NAME_I,j + number+length,String.valueOf(names[i].charAt(j)));
            }

        }
    }

    /**
     * This method is used to print the points of the first bookshelf, related to the Common Goals
     * @param points1
     * @param points2
     */
    private void printPlayer1Points(String[][] points1 ,String[][] points2){
        for(int i = 0; i < points1.length; i++){
            for(int j = 0; j < points1[i].length; j++){
                board.setChar(i + PLAYER_POINTS_I,j + PLAYER_1_POINTS_1_J,points1[i][j]);
            }
        }

        for(int i = 0; i < points2.length; i++){
            for(int j = 0; j < points2[i].length; j++){
                board.setChar(i + PLAYER_POINTS_I,j + PLAYER_1_POINTS_2_J,points2[i][j]);
            }
        }
    }

    /**
     * This method is used to print the points of the second bookshelf, related to the Common Goals
     * @param points1
     * @param points2
     */
    private void printPlayer2Points(String[][] points1 ,String[][] points2){
        for(int i = 0; i < points1.length; i++){
            for(int j = 0; j < points1[i].length; j++){
                board.setChar(i + PLAYER_POINTS_I,j + PLAYER_2_POINTS_1_J,points1[i][j]);
            }
        }

        for(int i = 0; i < points2.length; i++){
            for(int j = 0; j < points2[i].length; j++){
                board.setChar(i + PLAYER_POINTS_I,j + PLAYER_2_POINTS_2_J,points2[i][j]);
            }
        }
    }

    /**
     * This method is used to print the points of the third bookshelf, related to the Common Goals
     * @param points1
     * @param points2
     */
    private void printPlayer3Points(String[][] points1 ,String[][] points2){
        for(int i = 0; i < points1.length; i++){
            for(int j = 0; j < points1[i].length; j++){
                board.setChar(i + PLAYER_POINTS_I,j + PLAYER_3_POINTS_1_J,points1[i][j]);
            }
        }

        for(int i = 0; i < points2.length; i++){
            for(int j = 0; j < points2[i].length; j++){
                board.setChar(i + PLAYER_POINTS_I,j + PLAYER_3_POINTS_2_J,points2[i][j]);
            }
        }
    }

    /**
     * This method is used to print the points of the fourth bookshelf, related to the Common Goals
     * @param points1
     * @param points2
     */
    private void printPlayer4Points(String[][] points1 ,String[][] points2){
        for(int i = 0; i < points1.length; i++){
            for(int j = 0; j < points1[i].length; j++){
                board.setChar(i + PLAYER_POINTS_I,j + PLAYER_4_POINTS_1_J,points1[i][j]);
            }
        }

        for(int i = 0; i < points2.length; i++){
            for(int j = 0; j < points2[i].length; j++){
                board.setChar(i + PLAYER_POINTS_I,j + PLAYER_4_POINTS_2_J,points2[i][j]);
            }
        }
    }


    private void print(){
        board.print();
        System.out.println(ANSIParameters.GREEN + currentView.getMessage() + ANSIParameters.CRESET);
        System.out.print("> ");
    }

    /**
     *
     * @param tileType
     * @return
     * @author Valentino Guerrini
     */
    private String[][] renderTileType(TileType tileType){
        String[][] res = new String[1][1];
        switch (tileType){
            case CATS -> res[0][0] = ANSIParameters.BG_GREEN +" " + ANSIParameters.CRESET;
            case BOOKS -> res[0][0] = ANSIParameters.BG_WHITE +" " + ANSIParameters.CRESET;
            case GAMES -> res[0][0] = ANSIParameters.BG_YELLOW +" " + ANSIParameters.CRESET;
            case FRAMES -> res[0][0] = ANSIParameters.BG_BLUE +" " + ANSIParameters.CRESET;
            case TROPHIES -> res[0][0] = ANSIParameters.BG_CYAN +" " + ANSIParameters.CRESET;
            case PLANTS -> res[0][0] = ANSIParameters.BG_MAGENTA +" " + ANSIParameters.CRESET;
            default -> res[0][0] = "?";

        }
        return res;
    }

    /**
     *
     * @param tileMatrix
     * @return
     * @author Paolo Gennaro & Valentino Guerrini
     */
    private String[][] renderBookshelf(BookshelfTileSpot[][] tileMatrix){
        String[][] res = new String[14][21];


        for(int i = 0; i<14; i++){
            for(int j = 0; j<21; j++){
                if(i%2 == 0){
                    if(j%4 == 0){
                        res[i][j] =ANSIParameters.BG_BROWN +  " " +ANSIParameters.CRESET;
                    }else{
                        res[i][j] =ANSIParameters.BG_BROWN+  " " +ANSIParameters.CRESET;
                    }
                }else{
                    if(j%4 == 0){
                        res[i][j] = ANSIParameters.BG_BROWN + "|" + ANSIParameters.CRESET;
                    }else{
                        res[i][j] = " ";
                    }
                }
            }
        }

        int k = 0;
        int m = 0;

        for(int i=0; i<14 && m<6;i++){
            for(int j=0; j<21 && k<5; j++){
                if((j-2)%4==0 && (i-1)%2==0 && (j-2)/4==k && (i-1)/2==m){
                    if(tileMatrix[m][k].getTileType() != null) {
                        res[i][j] = renderTileType(tileMatrix[m][k].getTileType())[0][0];
                        res[i][j-1] = renderTileType(tileMatrix[m][k].getTileType())[0][0];
                        res[i][j+1] = renderTileType(tileMatrix[m][k].getTileType())[0][0];
                    }
                    k++;
                    if (k == 5) {
                        k = 0;
                        m++;
                    }
                }
            }
        }

        res[13][2] = "0";
        res[13][6] = "1";
        res[13][10] = "2";
        res[13][14] = "3";
        res[13][18] = "4";


        return res;
    }

    /**
     *
     * @param tileMatrix
     * @param cardID
     * @return
     * @author Marta Giliberto, Patrick Poggi, Valentino Guerrini, Paolo Gennaro
     */
    private String[][] renderPersonalGoalCard(BookshelfTileSpot[][] tileMatrix, int cardID){
        String[][] res  = new String[13][15];
        for(int i=0;i<13;i++){
            res[i][1] = " ";
            res[i][13] = " ";
            res[i][0] = "║";
            res[i][14] = "║";
        }
        res[0][0]="╔";
        res[0][14]="╗";
        res[12][0]="╚";
        res[12][14]="╝";
        for(int i=0;i<13;i++){
            for(int j=2;j<13;j++){
                if(i%2 == 0){
                    if(j%2 == 0){
                        res[i][j] = "╬";
                        if(i==0){
                            res[i][j] = "╦";
                        }
                        if(i==12){
                            res[i][j] = "╩";
                        }
                    }else{
                        res[i][j] = "═";
                    }
                }else{
                    if(j%2 == 0){
                        res[i][j] = "║";
                    }else{
                        res[i][j] = " ";
                    }
                }
            }
        }
        switch(cardID){
            case 1 -> {
                res[1][3] = renderTileType(TileType.PLANTS)[0][0];
                res[1][7] = renderTileType(TileType.FRAMES)[0][0];
                res[3][11] = renderTileType(TileType.CATS)[0][0];
                res[5][9] = renderTileType(TileType.BOOKS)[0][0];
                res[7][5] = renderTileType(TileType.GAMES)[0][0];
                res[11][7] = renderTileType(TileType.TROPHIES)[0][0];

            }
            case 2 -> {
                res[3][5]= renderTileType(TileType.PLANTS)[0][0];
                res[5][3]= renderTileType(TileType.CATS)[0][0];
                res[5][7]= renderTileType(TileType.GAMES)[0][0];
                res[7][11]= renderTileType(TileType.BOOKS)[0][0];
                res[9][9]= renderTileType(TileType.TROPHIES)[0][0];
                res[11][11]= renderTileType(TileType.FRAMES)[0][0];
            }
            case 3 ->{
                res[3][3]= renderTileType(TileType.FRAMES)[0][0];
                res[3][9]= renderTileType(TileType.GAMES)[0][0];
                res[5][7]= renderTileType(TileType.PLANTS)[0][0];
                res[7][5]= renderTileType(TileType.CATS)[0][0];
                res[7][11]= renderTileType(TileType.TROPHIES)[0][0];
                res[9][3]= renderTileType(TileType.BOOKS)[0][0];
            }

            case 4 -> {
                res[1][11] = renderTileType(TileType.GAMES)[0][0];
                res[5][3] = renderTileType(TileType.TROPHIES)[0][0];
                res[5][5] = renderTileType(TileType.FRAMES)[0][0];
                res[7][9] = renderTileType(TileType.PLANTS)[0][0];
                res[9][5] = renderTileType(TileType.BOOKS)[0][0];
                res[9][7] = renderTileType(TileType.CATS)[0][0];
            }
            case 5 -> {
                res[3][5] = renderTileType(TileType.TROPHIES)[0][0];
                res[7][5] = renderTileType(TileType.FRAMES)[0][0];
                res[7][7] = renderTileType(TileType.BOOKS)[0][0];
                res[9][11] = renderTileType(TileType.PLANTS)[0][0];
                res[11][3] = renderTileType(TileType.GAMES)[0][0];
                res[11][9] = renderTileType(TileType.GAMES)[0][0];
            }
            case 6 -> {
                res[1][7] = renderTileType(TileType.TROPHIES)[0][0];
                res[1][11] = renderTileType(TileType.CATS)[0][0];
                res[5][9] = renderTileType(TileType.BOOKS)[0][0];
                res[9][5] = renderTileType(TileType.GAMES)[0][0];
                res[9][9] = renderTileType(TileType.FRAMES)[0][0];
                res[11][3] = renderTileType(TileType.PLANTS)[0][0];
            }
            case 7 -> {
                res[1][3] = renderTileType(TileType.CATS)[0][0];
                res[3][9] = renderTileType(TileType.FRAMES)[0][0];
                res[5][5] = renderTileType(TileType.PLANTS)[0][0];
                res[7][3] = renderTileType(TileType.TROPHIES)[0][0];
                res[9][7] = renderTileType(TileType.GAMES)[0][0];
                res[11][11] = renderTileType(TileType.BOOKS)[0][0];
            }
            case 8 -> {
                res[1][11] = renderTileType(TileType.FRAMES)[0][0];
                res[3][5] = renderTileType(TileType.CATS)[0][0];
                res[5][7] = renderTileType(TileType.TROPHIES)[0][0];
                res[7][3] = renderTileType(TileType.PLANTS)[0][0];
                res[9][9] = renderTileType(TileType.BOOKS)[0][0];
                res[11][9] = renderTileType(TileType.GAMES)[0][0];
            }
            case 9 -> {
                res[1][7] = renderTileType(TileType.GAMES)[0][0];
                res[5][7] = renderTileType(TileType.CATS)[0][0];
                res[7][11] = renderTileType(TileType.BOOKS)[0][0];
                res[9][5] = renderTileType(TileType.TROPHIES)[0][0];
                res[9][11] = renderTileType(TileType.PLANTS)[0][0];
                res[11][3] = renderTileType(TileType.FRAMES)[0][0];
            }
            case 10 -> {
                 res[5][3] = renderTileType(TileType.BOOKS)[0][0];
                res[9][5] = renderTileType(TileType.FRAMES)[0][0];
                res[3][5] = renderTileType(TileType.GAMES)[0][0];
                res[11][9] = renderTileType(TileType.PLANTS)[0][0];
                res[7][9] = renderTileType(TileType.CATS)[0][0];
                res[1][11] = renderTileType(TileType.TROPHIES)[0][0];
            }
            case 11 -> {
                res[5][3] = renderTileType(TileType.GAMES)[0][0];
                res[3][5] = renderTileType(TileType.BOOKS)[0][0];
                res[7][7] = renderTileType(TileType.FRAMES)[0][0];
                res[1][7] = renderTileType(TileType.PLANTS)[0][0];
                res[11][9] = renderTileType(TileType.TROPHIES)[0][0];
                res[9][11] = renderTileType(TileType.CATS)[0][0];
            }
            case 12 -> {
                res[11][3] = renderTileType(TileType.CATS)[0][0];
                res[3][5] = renderTileType(TileType.PLANTS)[0][0];
                res[5][7] = renderTileType(TileType.FRAMES)[0][0];
                res[1][7] = renderTileType(TileType.BOOKS)[0][0];
                res[7][9] = renderTileType(TileType.TROPHIES)[0][0];
                res[9][11] = renderTileType(TileType.BOOKS)[0][0];
            }
        }

        return res;
    }

    /**
     * @author Marta Giliberto
     */
    private String[][] renderLivingroom(LivingRoomTileSpot[][] tileMatrix){
        String[][] res= new String[20][39];
        int k=0;
        for(int i=0; i<19; i++){
            for(int j=2; j<39; j++){
                if(i%2==0){
                    if(j%4!=0 && j%2==0){
                        res[i][j]="╬";
                        if(i==0 || i==18){
                            res[i][j]="─";
                            if(j==2 && i==0){
                                res[i][j]="╔";
                            }else if(j==38 && i==0){
                                res[i][j]="╗";
                            }else if(j==2 && i==18){
                                res[i][j]="╚";
                            }else if(j==38 && i==18){
                                res[i][j]="╝";
                            }
                        }
                    }else{
                        res[i][j]="═";
                    }
                }else{
                    if(j%4!=0 && j%2==0){
                        res[i][j]="║";
                    }else if(j%2!=0){
                        //res[i][j]=" ";
                    }else{
                        if(tileMatrix[k][j/4-1].isEmpty()){
                            res[i][j]=" ";
                            res[i][j-1]=" ";
                            res[i][j+1]=" ";

                        }else{
                            res[i][j-1] = renderTileType(tileMatrix[k][j/4 -1].getTileType())[0][0];
                            res[i][j] = renderTileType(tileMatrix[k][j/4 -1].getTileType())[0][0];
                            res[i][j+1] = renderTileType(tileMatrix[k][j/4 -1].getTileType())[0][0];
                        }
                    }

                    if(j==38){
                        k++;
                        //k++;
                    }
                }
            }

        }

        for(int j=0; j<39;j++){
            if(j%4!=0 || j==0){
                res[19][j]=" ";
            }
        }
        for(int i=0; i<20; i++){
            if(i%2==0){
                res[i][0]=" ";
            }
            res[i][1]=" ";
        }

        res[1][0]="a";
        res[3][0]="b";
        res[5][0]="c";
        res[7][0]="d";
        res[9][0]="e";
        res[11][0]="f";
        res[13][0]="g";
        res[15][0]="h";
        res[17][0]="i";

        res[19][4]="0";
        res[19][8]="1";
        res[19][12]="2";
        res[19][16]="3";
        res[19][20]="4";
        res[19][24]="5";
        res[19][28]="6";
        res[19][32]="7";
        res[19][36]="8";

        return res;
    }

    /**
     *
     * @param commonGoalCard
     * @return
     * @author Marta Giliberto, Patrick Poggi, Valentino Guerrini, Paolo Gennaro
     */
    private char[][] renderCommonGoalCard(CommonGoalCard commonGoalCard){
        char[][] res  = new char[13][15];
        switch(commonGoalCard.getCardID()){
            //Valentino Guerrini
            case 1 -> {
                for(int i=0;i<13;i++){
                    res[i][1] = ' ';
                    res[i][13] = ' ';
                    res[i][0] = '|';
                    res[i][14] = '|';
                }
                for(int i=0;i<13;i++){
                    for(int j=2;j<13;j++){
                        if(i%2 == 0){
                            if(j%2 == 0){
                                res[i][j] = '+';
                            }else{
                                res[i][j] = '-';
                            }
                        }else{
                            if(j%2 == 0){
                                res[i][j] = '|';
                            }else{
                                res[i][j] = ' ';
                            }
                        }
                    }
                }
                res[1][11] = '=';
                res[1][9] = '=';
                res[3][11] = '=';
                res[3][9] = '=';

                res[9][3] = '=';
                res[9][5] = '=';
                res[11][3] = '=';
                res[11][5] = '=';

                break;
            }
            //Valentino Guerrini
            case 2 -> {
                for(int i=0;i<13;i++){
                    res[i][1] = ' ';
                    res[i][13] = ' ';
                    res[i][0] = '|';
                    res[i][14] = '|';
                }

                for(int i=0;i<13;i++){
                    for(int j=2;j<13;j++){
                        if(i%2 == 0){
                            if(j%2 == 0){
                                res[i][j] = '+';
                            }else{
                                res[i][j] = '-';
                            }
                        }else{
                            if(j%2 == 0){
                                res[i][j] = '|';
                            }else{
                                res[i][j] = ' ';
                            }
                        }
                    }
                }
                res[1][3] = '=';
                res[3][3] = '=';
                res[5][3] = '=';
                res[7][3] = '=';
                res[9][3] = '=';
                res[11][3] = '=';

                res[1][9] = '=';
                res[3][9] = '=';
                res[5][9] = '=';
                res[7][9] = '=';
                res[9][9] = '=';
                res[11][9] = '=';

                break;
            }
            //Valentino Guerrini
            case 3 -> {
                for(int i=0;i<13;i++){
                    res[i][1] = ' ';
                    res[i][13] = ' ';
                    res[i][0] = '|';
                    res[i][14] = '|';
                }
                for(int i=0;i<13;i++){
                    for(int j=2;j<13;j++){
                        if(i%2 == 0){
                            if(j%2 == 0){
                                res[i][j] = '+';
                            }else{
                                res[i][j] = '-';
                            }
                        }else{
                            if(j%2 == 0){
                                res[i][j] = '|';
                            }else{
                                res[i][j] = ' ';
                            }
                        }
                    }
                }
                res[1][11] = '=';
                res[1][9] = '=';
                res[3][11] = '=';
                res[3][9] = '=';

                res[3][3]= '=';
                res[3][5] = '=';
                res[5][3] = '=';
                res[5][5] = '=';

                res[5][11] = '=';
                res[5][9] = '=';
                res[7][11] = '=';
                res[7][9] = '=';

                res[9][3] = '=';
                res[9][5] = '=';
                res[11][3] = '=';
                res[11][5] = '=';
                break;
            }
            //Paolo Gennaro
            case 4 -> {
                for(int i = 0; i<13; i++){
                    for(int j = 0; j<15; j++){
                        if(j == 0 || j == 14){
                            res[i][j] = '|';
                        }else{
                            res[i][j] = ' ';
                        }
                    }
                }

                res[4][5] = '+';
                res[4][6] = '-';
                res[4][7] = '+';
                res[5][5] = '+';
                res[5][6] = 'X';
                res[5][7] = '+';
                res[6][5] = '+';
                res[6][6] = '-';
                res[6][7] = '+';
                res[7][5] = '+';
                res[7][6] = 'X';
                res[7][7] = '+';
                res[8][5] = '+';
                res[8][6] = '-';
                res[8][7] = '+';
                break;
            }
            //Marta Giliberto
            case 5 -> {
                for(int i = 0; i<13; i++){
                    res[i][0] = '|';
                    res[i][14] = '|';
                    res[i][1] = ' ';
                    res[i][13]= ' ';
                }

                for(int i = 0; i<13; i++){
                    for(int j=2; j<13; j++){
                        if(i%2==0){
                            if(j%2==0){
                                res[i][j] = '+';
                            }else if(j==3||j==7||j==11) {
                                res[i][j] = '-';
                            }else{
                                res[i][j] = ' ';
                            }
                        }else{
                            if(j%2==0){
                                res[i][j] = '|';
                            }else{
                                res[i][j] = ' ';
                            }
                        }
                    }
                }
                break;
            }
            //Paolo Gennaro
            case 6 -> {
                for(int i = 0; i<13; i++){
                    for(int j = 0; j<15; j++){
                        if(j ==0 || j == 14){
                            res[i][j] = '|';
                        }else{
                            res[i][j] = ' ';
                        }
                    }
                }
                res[4][2] = '+';
                res[4][3] = '-';
                res[4][4] = '+';
                res[4][5] = '-';
                res[4][6] = '+';
                res[4][7] = '-';
                res[4][8] = '+';
                res[4][9] = '-';
                res[4][10] = '+';
                res[4][11] = '-';
                res[4][12] = '+';
                res[5][2] = '|';
                res[5][4] = '|';
                res[5][6] = '|';
                res[5][8] = '|';
                res[5][10] = '|';
                res[5][12] = '|';
                res[6][2] = '+';
                res[6][3] = '-';
                res[6][4] = '+';
                res[6][5] = '-';
                res[6][6] = '+';
                res[6][7] = '-';
                res[6][8] = '+';
                res[6][9] = '-';
                res[6][10] = '+';
                res[6][11] = '-';
                res[6][12] = '+';
                break;
            }
            //Patrick Poggi
            case 7 -> {
                for(int i=0;i<13;i++){
                    res[i][1] = ' ';
                    res[i][13] = ' ';
                    res[i][0] = '|';
                    res[i][14] = '|';
                }
                for(int i=0;i<13;i++){
                    for(int j=2;j<13;j++){
                        if(i%2 == 0){
                            if(j%2 == 0){
                                res[i][j] = '+';
                            }else{
                                res[i][j] = '-';
                            }
                        }else{
                            if(j%2 == 0){
                                res[i][j] = '|';
                            }else{
                                res[i][j] = ' ';
                            }
                        }
                    }
                }

                res[1][3] = 'C';
                res[1][5] = 'C';
                res[1][7] = 'C';
                res[1][9] = 'C';
                res[1][11] = 'C';

                res[3][3] = 'B';
                res[3][5] = 'B';
                res[3][7] = 'B';
                res[3][9] = 'B';
                res[3][11] = 'B';

                res[5][3] = ' ';
                res[5][3] = ' ';
                res[5][3] = ' ';
                res[5][3] = ' ';
                res[5][3] = ' ';

                res[7][3] = 'T';
                res[7][5] = 'T';
                res[7][7] = 'T';
                res[7][9] = 'T';
                res[7][11] = 'T';

                res[9][3] = ' ';
                res[9][3] = ' ';
                res[9][3] = ' ';
                res[9][3] = ' ';
                res[9][3] = ' ';

                res[11][3] = 'C';
                res[11][5] = 'C';
                res[11][7] = 'C';
                res[11][9] = 'C';
                res[11][11] = 'C';

                break;
            }
            //Patrick Poggi
            case 8 -> {
                for(int i=0;i<13;i++){
                    res[i][1] = ' ';
                    res[i][13] = ' ';
                    res[i][0] = '|';
                    res[i][14] = '|';
                }
                for(int i=0;i<13;i++){
                    for(int j=2;j<13;j++){
                        if(i%2 == 0){
                            if(j%2 == 0){
                                res[i][j] = '+';
                            }else{
                                res[i][j] = '-';
                            }
                        }else{
                            if(j%2 == 0){
                                res[i][j] = '|';
                            }else{
                                res[i][j] = ' ';
                            }
                        }
                    }
                }

                res[1][3] = '=';
                res[11][3] = '=';
                res[1][11] = '=';
                res[11][11] = '=';
                break;
            }
            //Patrick Poggi
            case 9 -> {
                for(int i=0;i<13;i++){
                    res[i][1] = ' ';
                    res[i][13] = ' ';
                    res[i][0] = '|';
                    res[i][14] = '|';
                }
                for(int i=0;i<13;i++){
                    for(int j=2;j<13;j++){
                        if(i%2 == 0){
                            if(j%2 == 0){
                                res[i][j] = '+';
                            }else{
                                res[i][j] = '-';
                            }
                        }else{
                            if(j%2 == 0){
                                res[i][j] = '|';
                            }else{
                                res[i][j] = ' ';
                            }
                        }
                    }
                }

                res[1][3] = 'C';
                res[3][5] = 'C';
                res[3][11] = 'C';
                res[7][3] = 'C';
                res[7][5] = 'C';
                res[7][9] = 'C';
                res[9][7] = 'C';
                res[11][11] = 'C';
                break;
            }
            //Marta Giliberto
            case 10 -> {
                for (int i = 0; i < 13; i++) {
                    for (int j = 0; j < 15; j++) {
                        if(j==0||j==14){
                            res[i][j]='|';
                        }else {
                            res[i][j] = ' ';
                        }
                    }
                }

                res[3][4]='+';
                res[3][6]='+';
                res[3][8]='+';
                res[3][10]='+';
                res[5][4]='+';
                res[5][6]='+';
                res[5][8]='+';
                res[5][10]='+';
                res[7][4]='+';
                res[7][6]='+';
                res[7][8]='+';
                res[7][10]='+';
                res[9][4]='+';
                res[9][6]='+';
                res[9][8]='+';
                res[9][10]='+';


                res[3][5]='-';
                res[3][9]='-';
                res[5][5]='-';
                res[5][9]='-';
                res[7][5]='-';
                res[7][9]='-';
                res[9][5]='-';
                res[9][9]='-';

                res[4][4]='|';
                res[4][6]='|';
                res[4][8]='|';
                res[4][10]='|';
                res[6][6]='|';
                res[6][8]='|';
                res[8][4]='|';
                res[8][6]='|';
                res[8][8]='|';
                res[8][10]='|';

                res[4][5]='=';
                res[4][9]='=';
                res[6][7]='=';
                res[8][5]='=';
                res[8][9]='=';
                break;
            }
            //Marta Giliberto
            case 11 -> {
                for (int i = 0; i < 13; i++) {
                    for (int j = 0; j < 15; j++) {
                        if(j==0||j==14){
                            res[i][j]='|';
                        }else {
                            res[i][j] = ' ';
                        }
                    }
                }

                res[1][2]  ='+';
                res[1][4]  ='+';
                res[3][2]  ='+';
                res[3][4]  ='+';
                res[3][6]  ='+';
                res[5][4]  ='+';
                res[5][6]  ='+';
                res[5][8]  ='+';
                res[7][6]  ='+';
                res[7][8]  ='+';
                res[7][10] ='+';
                res[9][8]  ='+';
                res[9][10] ='+';
                res[9][12] ='+';
                res[11][10]='+';
                res[11][12]='+';

                res[1][3]  ='-';
                res[3][3]  ='-';
                res[3][5]  ='-';
                res[5][5]  ='-';
                res[5][7]  ='-';
                res[7][7]  ='-';
                res[7][9]  ='-';
                res[9][9]  ='-';
                res[9][11] ='-';
                res[11][11]='-';

                res[2][3]  ='=';
                res[4][5]  ='=';
                res[6][7]  ='=';
                res[8][9]  ='=';
                res[10][11]='=';
                break;
            }
            //Marta Giliberto
            case 12 -> {
                for(int i=0; i<13; i++) {
                    for (int j = 0; j < 15; j++) {
                        if (j == 0 || j == 14) {
                            res[i][j] = '|';
                        } else {
                            res[i][j] = ' ';
                        }
                    }
                }

                res[1][2]='+';
                res[1][3]='-';
                res[1][4]='+';
                res[2][2]='|';
                res[2][4]='|';
                res[3][2]='+';
                res[3][3]='-';
                res[3][4]='+';
                res[3][5]='-';
                res[3][6]='+';
                res[4][2]='|';
                res[4][4]='|';
                res[4][6]='|';
                res[5][2]='+';
                res[5][3]='-';
                res[5][4]='+';
                res[5][5]='-';
                res[5][6]='+';
                res[5][7]='-';
                res[5][8]='+';
                res[6][2]='|';
                res[6][4]='|';
                res[6][6]='|';
                res[6][8]='|';
                res[7][2]='+';
                res[7][3]='-';
                res[7][4]='+';
                res[7][5]='-';
                res[7][6]='+';
                res[7][7]='-';
                res[7][8]='+';
                res[7][9]='-';
                res[7][10]='+';
                res[8][2]='|';
                res[8][4]='|';
                res[8][6]='|';
                res[8][8]='|';
                res[8][10]='|';
                res[9][2]='+';
                res[9][3]='-';
                res[9][4]='+';
                res[9][5]='-';
                res[9][6]='+';
                res[9][7]='-';
                res[9][8]='+';
                res[9][9]='-';
                res[9][10]='+';
                res[9][11]='-';
                res[9][12]='+';
                res[10][2]='|';
                res[10][4]='|';
                res[10][6]='|';
                res[10][8]='|';
                res[10][10]='|';
                res[10][12]='|';
                res[11][2]='+';
                res[11][3]='-';
                res[11][4]='+';
                res[11][5]='-';
                res[11][6]='+';
                res[11][7]='-';
                res[11][8]='+';
                res[11][9]='-';
                res[11][10]='+';
                res[11][11]='-';
                res[11][12]='+';
                break;
            }
        }
        return res;
    }

    /**
     *
     * @param pointsTile
     * @return
     * @author: Valentino Guerrini, Paolo Gennao, Patrick Poggi
     */
    private String[][] renderPointTile(PointsTile pointsTile){
        String[][] res = new String[4][9];

        for(int i=0; i<4; i++){
            for(int j=0; j<9; j++){
                res[i][j] = " ";
            }
        }
        res[0][0] = "╔";
        res[0][8] = "╗";
        res[3][0] = "╚";
        res[3][8] = "╝";
        for(int j=1;j<8;j++){
            res[0][j] = "═";
            res[3][j] = "═";
        }
        res[1][0] = "║";
        res[2][0] = "║";

        res[1][8] = "║";
        res[2][8] = "║";

        res[2][2] = "p";
        res[2][3] = "o";
        res[2][4] = "i";
        res[2][5] = "n";
        res[2][6] = "t";
        res[2][7] = "s";

        switch(pointsTile.getNumberOfPoints()){
            case 2 -> {
                res[1][4] = "2";
                break;
            }
            case 4 -> {
                res[1][4] = "4";
                break;
            }
            case 6 -> {
                res[1][4] = "6";
                break;
            }
            case 8 -> {
                res[1][4] = "8";
                break;
            }
            default -> {
                res[1][4] = "?";
                break;
            }
        }

        return res;
    }

    public ConnectionInfo getConnectionInfo(){
        return connectionInfo;
    }

    @Override
    public boolean isReconnecting() {
        return isReconnecting;
    }

    @Override
    public void resetConnection() {
        boolean storeData = false;
        System.out.println(ANSIParameters.CLEAR_SCREEN+ANSIParameters.CURSOR_HOME);
        storeData = true;
        //Erasing all data structures referring to network and conenction
        isReconnecting = true;
        reconnectionProcess();
        currentView = null;
        printInfo();
    }

}
