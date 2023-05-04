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
import Server.Model.LightMatch;
import Server.Model.Player.Player;

import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


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
    private final static char[][] EMPTYSPOT= {{'+','-','-','-','-','-','-','-','+'},
            {'|',' ',' ',' ',' ',' ',' ',' ','|'},
            {'|',' ',' ',' ',' ',' ',' ',' ','|'},
            {'|',' ',' ',' ',' ',' ',' ',' ','|'},
            {'+','-','-','-','-','-','-','-','+'}};

    private final static char[][] ENDTILE= {{'+','-','-','-','-','-','-','-','+'},
            {'|',' ',' ','#','1',' ',' ',' ','|'},
            {'|',' ','P','o','i','n','t',' ','|'},
            {'|',' ',' ',' ',' ',' ',' ',' ','|'},
            {'+','-','-','-','-','-','-','-','+'}};

    private NetworkHandler networkHandler;
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


    public CLI(){
        scanner = new Scanner(System.in);
        connect();

        System.out.println("info        : show this message\n"+
                           "play        : login to the server\n"+
                           "quit        : quit the game\n");
        new Thread(this::run).start();
        //refresh();
    }

    @Override
    public void run() {
        String input;
        input = scanner.nextLine();
        while(input != null){
            this.parseInput(input);


            input = scanner.nextLine();
        }


    }

    //connect to the server


    /**
     * This method is used to connect to the server
     * @author Valentino Guerrini & Paolo Gennaro & Patrick Poggi
     */
    private void connect(){
        boolean flag = true;
        ConnectionType connectionType = null;
        System.out.println("Select connection type: ");
        System.out.println("1) Socket 2) RMI");
        String connection =scanner.nextLine();
        System.out.println("Server Address:");
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
            //connectionHandler = new ConnectionHandler(connectionType,port,host);
            if(connectionType == ConnectionType.SOCKET) {
                networkHandler = new NetworkSocketHandler(host, port, this);
            }else{
                networkHandler = new NetworkRMIHandler(this);
            }
        }catch (Exception e){
            System.out.println("Connection failed");
            System.err.println(e.getMessage()+"\n"+e.getStackTrace());
            System.exit(1);
        }
        System.out.println("Connection successful");

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
            }
            case "onModiefiedBookshelfEvent" -> {
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

        }
        if(!chatIsOpened && !MatchEnded){
            print();
        }

    }

    /**
     * This method is used to setup the board at the start of the match
     * @param match
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
        printLivingRoom(match.getLivingRoom().getCLIRepresentation());
        printPersonalGoal(me.getPersonalGoalCard().getCLIRepresentation());
        CommonGoalCard commonGoal= match.getCommonGoals()[0];
        printCommonGoal1(commonGoal.getCLIRepresentation());
        printPoints1(commonGoal.getPointsTiles().get(0).getCLIRepresentation());
        printDescription1(commonGoal.getCommonGoalDescription());
        commonGoal = match.getCommonGoals()[1];
        printCommonGoal2(commonGoal.getCLIRepresentation());
        printPoints2(commonGoal.getPointsTiles().get(0).getCLIRepresentation());
        printDescription2(commonGoal.getCommonGoalDescription());

        switch(numberPlayers) {
            case 2 -> {
                printBookshelf1(players.get(0).getBookshelf().getCLIRepresentation());
                printEndTile1(EMPTYSPOT);
                printPlayer1Points(EMPTYSPOT, EMPTYSPOT);

                printBookshelf2(players.get(1).getBookshelf().getCLIRepresentation());
                printEndTile2(EMPTYSPOT);
                printPlayer2Points(EMPTYSPOT, EMPTYSPOT);
            }
            case 3 -> {
                printBookshelf1(players.get(0).getBookshelf().getCLIRepresentation());
                printEndTile1(EMPTYSPOT);
                printPlayer1Points(EMPTYSPOT, EMPTYSPOT);

                printBookshelf2(players.get(1).getBookshelf().getCLIRepresentation());
                printEndTile2(EMPTYSPOT);
                printPlayer2Points(EMPTYSPOT, EMPTYSPOT);

                printBookshelf3(players.get(2).getBookshelf().getCLIRepresentation());
                printEndTile3(EMPTYSPOT);
                printPlayer3Points(EMPTYSPOT, EMPTYSPOT);
            }
            case 4 -> {
                printBookshelf1(players.get(0).getBookshelf().getCLIRepresentation());
                printEndTile1(EMPTYSPOT);
                printPlayer1Points(EMPTYSPOT, EMPTYSPOT);

                printBookshelf2(players.get(1).getBookshelf().getCLIRepresentation());
                printEndTile2(EMPTYSPOT);
                printPlayer2Points(EMPTYSPOT, EMPTYSPOT);

                printBookshelf3(players.get(2).getBookshelf().getCLIRepresentation());
                printEndTile3(EMPTYSPOT);
                printPlayer3Points(EMPTYSPOT, EMPTYSPOT);

                printBookshelf4(players.get(3).getBookshelf().getCLIRepresentation());
                printEndTile4(EMPTYSPOT);
                printPlayer4Points(EMPTYSPOT, EMPTYSPOT);
            }
        }
        String[] names = new String[numberPlayers];

        for (int i = 0; i < numberPlayers; i++) {
            names[i] = players.get(i).getPlayerNickName();
        }

        printNames(names);


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
                    printPlayer1Points(match.getPlayers().get(0).getPointsTiles().get(0).getCLIRepresentation(), EMPTYSPOT);
                }else if(match.getPlayers().get(0).getPointsTiles().size() == 2){
                    printPlayer1Points(match.getPlayers().get(0).getPointsTiles().get(0).getCLIRepresentation(),match.getPlayers().get(0).getPointsTiles().get(1).getCLIRepresentation());
                }
                if(match.getPlayers().get(1).getPointsTiles().size() == 1) {
                    printPlayer2Points(match.getPlayers().get(1).getPointsTiles().get(0).getCLIRepresentation(), EMPTYSPOT);
                }else if(match.getPlayers().get(1).getPointsTiles().size() == 2){
                    printPlayer2Points(match.getPlayers().get(1).getPointsTiles().get(0).getCLIRepresentation(), match.getPlayers().get(1).getPointsTiles().get(1).getCLIRepresentation());
                }
            }
            case 3->{
                if(match.getPlayers().get(0).getPointsTiles().size() == 1) {
                    printPlayer1Points(match.getPlayers().get(0).getPointsTiles().get(0).getCLIRepresentation(), EMPTYSPOT);
                }else if(match.getPlayers().get(0).getPointsTiles().size() == 2){
                    printPlayer1Points(match.getPlayers().get(0).getPointsTiles().get(0).getCLIRepresentation(), match.getPlayers().get(0).getPointsTiles().get(1).getCLIRepresentation());

                }

                if(match.getPlayers().get(1).getPointsTiles().size() == 1) {
                    printPlayer2Points(match.getPlayers().get(1).getPointsTiles().get(0).getCLIRepresentation(), EMPTYSPOT);
                }else if(match.getPlayers().get(1).getPointsTiles().size() == 2){
                    printPlayer2Points(match.getPlayers().get(1).getPointsTiles().get(0).getCLIRepresentation(), match.getPlayers().get(1).getPointsTiles().get(1).getCLIRepresentation());
                }

                if(match.getPlayers().get(2).getPointsTiles().size() == 1) {
                    printPlayer3Points(match.getPlayers().get(2).getPointsTiles().get(0).getCLIRepresentation(), EMPTYSPOT);
                }else if(match.getPlayers().get(2).getPointsTiles().size() == 2){
                    printPlayer3Points(match.getPlayers().get(2).getPointsTiles().get(0).getCLIRepresentation(), match.getPlayers().get(2).getPointsTiles().get(1).getCLIRepresentation());

                }
            }case 4->{
                if(match.getPlayers().get(0).getPointsTiles().size() == 1) {
                    printPlayer1Points(match.getPlayers().get(0).getPointsTiles().get(0).getCLIRepresentation(), EMPTYSPOT);
                }else if(match.getPlayers().get(0).getPointsTiles().size() == 2){
                    printPlayer1Points(match.getPlayers().get(0).getPointsTiles().get(0).getCLIRepresentation(), match.getPlayers().get(0).getPointsTiles().get(1).getCLIRepresentation());

                }

                if(match.getPlayers().get(1).getPointsTiles().size() == 1) {
                    printPlayer2Points(match.getPlayers().get(1).getPointsTiles().get(0).getCLIRepresentation(), EMPTYSPOT);
                }else if(match.getPlayers().get(1).getPointsTiles().size() == 2){
                    printPlayer2Points(match.getPlayers().get(1).getPointsTiles().get(0).getCLIRepresentation(), match.getPlayers().get(1).getPointsTiles().get(1).getCLIRepresentation());
                }

                if(match.getPlayers().get(2).getPointsTiles().size() == 1) {
                    printPlayer3Points(match.getPlayers().get(2).getPointsTiles().get(0).getCLIRepresentation(), EMPTYSPOT);
                }else if(match.getPlayers().get(2).getPointsTiles().size() == 2) {
                    printPlayer3Points(match.getPlayers().get(2).getPointsTiles().get(0).getCLIRepresentation(), match.getPlayers().get(2).getPointsTiles().get(1).getCLIRepresentation());
                }

                if(match.getPlayers().get(3).getPointsTiles().size() == 1) {
                    printPlayer4Points(match.getPlayers().get(3).getPointsTiles().get(0).getCLIRepresentation(), EMPTYSPOT);
                }else if(match.getPlayers().get(3).getPointsTiles().size() == 2){
                    printPlayer4Points(match.getPlayers().get(3).getPointsTiles().get(0).getCLIRepresentation(), match.getPlayers().get(3).getPointsTiles().get(1).getCLIRepresentation());
                }
            }

        }
        printPoints1(match.getCommonGoals()[0].getPointsTiles().get(0).getCLIRepresentation());
        printPoints2(match.getCommonGoals()[1].getPointsTiles().get(0).getCLIRepresentation());
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
                printBookshelf1(match.getPlayers().get(0).getBookshelf().getCLIRepresentation());
                printBookshelf2(match.getPlayers().get(1).getBookshelf().getCLIRepresentation());
            }
            case 3->{
                printBookshelf1(match.getPlayers().get(0).getBookshelf().getCLIRepresentation());
                printBookshelf2(match.getPlayers().get(1).getBookshelf().getCLIRepresentation());
                printBookshelf3(match.getPlayers().get(2).getBookshelf().getCLIRepresentation());
            }case 4->{
                printBookshelf1(match.getPlayers().get(0).getBookshelf().getCLIRepresentation());
                printBookshelf2(match.getPlayers().get(1).getBookshelf().getCLIRepresentation());
                printBookshelf3(match.getPlayers().get(2).getBookshelf().getCLIRepresentation());
                printBookshelf4(match.getPlayers().get(3).getBookshelf().getCLIRepresentation());
            }

        }
    }

    /**
     * This method is used to update the LivingRoom
     * @param match is the light version of the match
     * @author Valentino Guerrini
     */
    private void onModifiedLivingRoomEvent(LightMatch match){
        printLivingRoom(match.getLivingRoom().getCLIRepresentation());
    }

    /**
     * This method is used to notify players that a player completed his bookshelf and took the "MatchEnded" tile
     * @param match is the light version of the match
     */
    private void onModifiedMatchEndedEvent(LightMatch match){
        finalScores= (HashMap<Integer, Integer>) match.getScores();
        winner = match.getWinner();
    }

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

    private void onLoginViewEvent(SelectViewEvent event){
        currentView = event;
        LoginView loginEvent = (LoginView) event;
        System.out.print(ANSIParameters.CLEAR_SCREEN + ANSIParameters.CURSOR_HOME);
        System.out.flush();
        System.out.println("Welcome to MyShelfie!");

        if(loginEvent.isFirstToJoin()) {
            System.out.println("You are the first player to join the match");
            System.out.println("Please insert your nickname and the number of players for the match: ");


        }else{
            System.out.println("Please insert your nickname: ");

        }

    }

    private void onGameViewEvent(SelectViewEvent event){
        currentView = event;
        if (!matchStarted) {
            System.out.print(ANSIParameters.CLEAR_SCREEN + ANSIParameters.CURSOR_HOME);
            System.out.flush();
            System.out.println("Hi"+ ANSIParameters.BLUE + myNick+ ANSIParameters.CRESET+"Welcome to MyShelfie");
            System.out.println("Please wait for the other players to join the match");
        }
        print();

    }

    private void onEndedMatchViewEvent(SelectViewEvent event){
        MatchEnded = true;
        currentView = event;

        System.out.print(ANSIParameters.CLEAR_SCREEN + ANSIParameters.CURSOR_HOME);
        System.out.flush();

        System.out.println("Match ended");

        System.out.println("The final scores are: ");
        Player p;

        for(int i=0;i<numberPlayers;i++){
            p =players.get(i);
            System.out.println(ANSIParameters.RED + p.getPlayerNickName() + ANSIParameters.CRESET + " with: " + ANSIParameters.MAGENTA + finalScores.get(p.getPlayerID()) + " points" + ANSIParameters.CRESET);

        }
        System.out.println("The winner is: " + ANSIParameters.YELLOW + winner.getPlayerNickName() + ANSIParameters.CRESET );
    }

    //CHAT

    public void onOpenChatEvent(){
        chatIsOpened = true;
        System.out.print(ANSIParameters.CLEAR_SCREEN + ANSIParameters.CURSOR_HOME);
        System.out.flush();
        System.out.println("Chat:");

        for(String s : chat){
            String message = s.substring(2,s.length()-1);
            //print s[0] In RED
            System.out.println(ANSIParameters.RED + s.split(" ")[0]+ ANSIParameters.CRESET + "Sent to :"  +  ANSIParameters.BLUE + s.split(" ")[1] + ANSIParameters.CRESET + " A message: " + message );

        }

    }

    public void onCloseChatEvent(){
        chatIsOpened = false;
        print();
    }

    public void onModifiedChatEvent(Message El_loco_message){
        String s = MessageToString(El_loco_message);

        chat.add(s);
        if(chatIsOpened){
            String message = s.substring(2,s.length()-1);
            //print s[0] In RED
            System.out.println(ANSIParameters.RED + s.split(" ")[0]+ ANSIParameters.CRESET + "Sent to :"  +  ANSIParameters.BLUE + s.split(" ")[1] + ANSIParameters.CRESET + " A message: " + message );
        }
    }

    private String MessageToString(Message message){
        String receiver;
        if(message.getReceiver() == null) {
            receiver = "@All";
        }else{
            receiver = message.getReceiver().getPlayerNickName();
        }

        String s = message.getSender().getPlayerNickName() + "  @" + receiver + "  " + message.getContent();
        return s;

    }


    private void printHelp(){
        if(currentView.getType().equals("LoginView")){
            System.out.println("info play \n quit");
        }else if(currentView.getType().equals("PickingTileView")) {
            System.out.println("info pick \n checkout open \n close send \n quit");
        }else if(currentView.getType().equals("InsertingTilesGameView")){
            System.out.println("info select \n open close \n send quit");
        }else if(currentView.getType().equals("EndedMatchView")) {
            System.out.println("info open \n close send \n quit");
        }else if(currentView.getType().equals("GameView")) {
            System.out.println("info open \n close send \n quit");
        }else if(currentView.getType().equals("ChatONView")) {
            System.out.println("info close \n send quit");
        }else if(currentView.getType().equals("ChatOFFView")) {
            System.out.println("info open \n quit");
        }
    }

    private void parseInput(String input){
        String[] inputArray = input.split(" ");
        if(currentView!=null && currentView.getType().equals("LoginView")) {

            LoginView loginview = (LoginView) currentView;

            if(loginview.isFirstToJoin()){
                if(inputArray.length == 2){
                    myNick = inputArray[0];
                    numberPlayers = Integer.parseInt(inputArray[1]);
                    try {
                        networkHandler.onVCEvent(new LoginEvent(myNick, numberPlayers));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    System.out.println("Please insert your nickname and the number of players for the match: ");
                }
            }else{
                if (inputArray.length == 1) {
                    myNick = inputArray[0];
                    try {
                        networkHandler.onVCEvent(new LoginEvent(myNick));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Nickname must be one word");
                }
            }

        }
        switch (inputArray[0]){
            case "info" -> {
                printHelp();
            }
            case "play" -> {
                new Thread(networkHandler).start();

            }

            case "quit" -> {
                System.out.println("Bye!");
                System.exit(0);
            }
            case "select" -> {
                if(inputArray.length == 2){
                    int index = Integer.parseInt(inputArray[1]);
                    try {
                        networkHandler.onVCEvent(new SelectColumn(index));
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    System.out.println("Invalid input");
                }

            }
            case "pick" -> {
                if(inputArray.length == 2){
                    String[] coordinates = inputArray[1].split(",");
                    char row= coordinates[0].charAt(0);
                    int column = Integer.parseInt(coordinates[1]);
                    int[] coordinatesInt = new int[2];
                    if(row>='a' && row <='i' && column>=0 && column<=8) {
                        coordinatesInt[0] = row - 'a';
                        coordinatesInt[1] = column;
                        try {
                            networkHandler.onVCEvent(new ClickOnTile(coordinatesInt));
                        } catch (NoSuchMethodException e) {
                            throw new RuntimeException(e);
                        } catch (InvocationTargetException e) {
                            throw new RuntimeException(e);
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }else{
                    System.out.println("Invalid input");
                }
            }
            case "checkout" ->{
                if(inputArray.length==1){
                    try {
                        networkHandler.onVCEvent(new CheckOutTiles());
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    System.out.println("Invalid input");
                }
            }
            case "open" ->{
                if(inputArray.length==1){
                    try {
                        networkHandler.onVCEvent(new OpenChat());
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    System.out.println("Invalid input");
                }
            }
            case "close" ->{
                if(inputArray.length==1){
                    try {
                        networkHandler.onVCEvent(new CloseChat());
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    System.out.println("Invalid input");
                }
            }
            case "send" ->{
                if(inputArray.length>=3){
                    try {
                        String message = new String("");
                        for(int i = 2; i < inputArray.length; i++){
                            message = message + inputArray[i] + " ";
                        }
                        Message messageToSend=null;
                        boolean flag=false;
                        if(inputArray[1].equals("@All")) {
                            messageToSend=new Message(this.me,message,new Time(60));
                            flag=true;
                        }else{

                            for(Integer i: this.players.keySet()){
                                if(("@"+this.players.get(i).getPlayerNickName()).equals(inputArray[1])){
                                    messageToSend=new Message(this.me,message,new Time(60), this.players.get(i));
                                    flag=true;
                                }
                            }
                            if(!flag){
                                System.out.println("nickname does not exists");
                                break;
                            }
                        }
                     if(flag) {
                         networkHandler.onVCEvent(new SendMessage(messageToSend));
                     }
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }else {
                    System.out.println("Invalid input");
                }
            }
        }

    }



    //refresh the board

    private void printLivingRoom(char[][] livingRoom){

        for(int i = 0; i < livingRoom.length; i++){
            for(int j = 0; j < livingRoom[i].length; j++){
                board.setChar(i + LIVINGROOM_I ,j + LIVINGROOM_J,livingRoom[i][j]);
            }
        }

    }

    private void printPersonalGoal(char[][] personalGoal){
        for(char c : "Personal Goal".toCharArray()){
            board.setChar(CARDLINE -1,PERSONAL_J+1,c);
        }

        for(int i = 0; i < personalGoal.length; i++){
            for(int j = 0; j < personalGoal[i].length; j++){
                board.setChar(i + CARDLINE,j + PERSONAL_J,personalGoal[i][j]);
            }
        }
    }

    private void printCommonGoal1(char[][] commonGoal){
        for(char c : "#1 CommonGoal".toCharArray()){
            board.setChar(CARDLINE -1,COMMON_1_J+1,c);
        }

        for(int i = 0; i < commonGoal.length; i++){
            for(int j = 0; j < commonGoal[i].length; j++){
                board.setChar(i + CARDLINE,j + COMMON_1_J,commonGoal[i][j]);
            }
        }
    }

    private void printPoints1(char[][] points){


        for(int i = 0; i < points.length; i++){
            for(int j = 0; j < points[i].length; j++){
                board.setChar(i + CARDLINE,j + POINTS_1_J,points[i][j]);
            }
        }
    }

    private void printCommonGoal2(char[][] commonGoal){
        for(char c : "#2 CommonGoal".toCharArray()){
            board.setChar(CARDLINE -1,COMMON_2_J+1,c);
        }

        for(int i = 0; i < commonGoal.length; i++){
            for(int j = 0; j < commonGoal[i].length; j++){
                board.setChar(i + CARDLINE,j + COMMON_2_J,commonGoal[i][j]);
            }
        }
    }

    private void printPoints2(char[][] points){
        for(int i = 0; i < points.length; i++){
            for(int j = 0; j < points[i].length; j++){
                board.setChar(i + CARDLINE,j + POINTS_2_J,points[i][j]);
            }
        }
    }

    private void printDescription1(String[] description){
        for (int i = 0; i < description.length; i++){
            for(int j = 0; j < description[i].length(); j++){
                board.setChar(i + DESCRIPTION_I,j + DESCRIPTION_1_J,description[i].charAt(j));
            }
        }

    }

    private void printDescription2(String[] description){
        for (int i = 0; i < description.length; i++){
            for(int j = 0; j < description[i].length(); j++){
                board.setChar(i + DESCRIPTION_I,j + DESCRIPTION_2_J,description[i].charAt(j));
            }
        }

    }

    private void printBookshelf1(char[][] bookshelf){
        for(int i = 0; i < bookshelf.length; i++){
            for(int j = 0; j < bookshelf[i].length; j++){
                board.setChar(i + BOOKSHELF_I,j + BOOKSHELF_1_J,bookshelf[i][j]);
            }
        }

    }

    private void printBookshelf2(char[][] bookshelf){
        for(int i = 0; i < bookshelf.length; i++){
            for(int j = 0; j < bookshelf[i].length; j++){
                board.setChar(i + BOOKSHELF_I,j + BOOKSHELF_2_J,bookshelf[i][j]);
            }
        }

    }

    private void printBookshelf3(char[][] bookshelf){
        for(int i = 0; i < bookshelf.length; i++){
            for(int j = 0; j < bookshelf[i].length; j++){
                board.setChar(i + BOOKSHELF_I,j + BOOKSHELF_3_J,bookshelf[i][j]);
            }
        }

    }

    private void printBookshelf4(char[][] bookshelf){
        for(int i = 0; i < bookshelf.length; i++){
            for(int j = 0; j < bookshelf[i].length; j++){
                board.setChar(i + BOOKSHELF_I,j + BOOKSHELF_4_J,bookshelf[i][j]);
            }
        }

    }

    private void printEndTile1(char[][] endTile){
        for(int i = 0; i < endTile.length; i++){
            for(int j = 0; j < endTile[i].length; j++){
                board.setChar(i + END_TILE_I,j + END_TILE_1_J,endTile[i][j]);
            }
        }

    }

    private void printEndTile2(char[][] endTile){
        for(int i = 0; i < endTile.length; i++){
            for(int j = 0; j < endTile[i].length; j++){
                board.setChar(i + END_TILE_I,j + END_TILE_2_J,endTile[i][j]);
            }
        }

    }

    private void printEndTile3(char[][] endTile){
        for(int i = 0; i < endTile.length; i++){
            for(int j = 0; j < endTile[i].length; j++){
                board.setChar(i + END_TILE_I,j + END_TILE_3_J,endTile[i][j]);
            }
        }

    }

    private void printEndTile4(char[][] endTile){
        for(int i = 0; i < endTile.length; i++){
            for(int j = 0; j < endTile[i].length; j++){
                board.setChar(i + END_TILE_I,j + END_TILE_4_J,endTile[i][j]);
            }
        }

    }

    private void printNames(String[] names){
        int length;
        int number;

        for(int i = 0; i < names.length; i++){
            length = 0;
            number=BOOKSHELF_1_J+ 38*i;
            while(length*2 +names[i].length() < NAME_LENGTH){

                length++;
            }
            for(int j = 0; j < names[i].length(); j++){
                board.setChar(i + PLAYER_NAME_I,j + number+length,names[i].charAt(j));
            }

        }
    }

    private void printPlayer1Points(char[][] points1 ,char[][] points2){
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

    private void printPlayer2Points(char[][] points1 ,char[][] points2){
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

    private void printPlayer3Points(char[][] points1 ,char[][] points2){
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

    private void printPlayer4Points(char[][] points1 ,char[][] points2){
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
    }








    //refresh the CLI

    //update()

}
