package Client.View.CLI;
//import org.fusesource.jansi.AnsiConsole;


import Client.*;
import Client.View.View;
import Server.Controller.Controller;
import Server.Events.MVEvents.MVEvent;
import Server.Events.SelectViewEvents.SelectViewEvent;


import Server.Events.SelectViewEvents.ViewType;


import Server.Events.VCEvents.ClickOnTile;
import Server.Events.VCEvents.LoginEvent;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.Chat.Message;
import Server.Model.LightMatch;
import Server.Model.Match;
import Server.Model.Player.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;



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



    private NetworkHandler networkHandler;
    private Environment board = new Environment();
    private ArrayList<String> chat = new ArrayList<>();
    private boolean chatIsOpened = false;
    private HashMap<Integer,Player> players = new HashMap<>();
    private HashMap<Player,Integer> finalScores;
    private Player winner;
    private int numberPlayers;
    private Player me;
    private String myNick;
    private boolean matchStarted = false;
    private boolean MatchEnded = false;
    private ViewType currentView;

    private boolean myTurn = false;






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
            this.parseInput(input);


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
            //connectionHandler = new ConnectionHandler(connectionType,port,host);
            if(connectionType == ConnectionType.SOCKET) {
                networkHandler = new NetworkSocketHandler(host, port, this);
            }else{
                networkHandler = new NetworkRMIHandler(this);
            }
        }catch (Exception e){
            System.out.println("Connection failed");
            System.exit(1);
        }
        System.out.println("Connection successful");

    }

    /**
     * This method is used to manage the MVEvents sended by the model
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
     * This method is used to updete the points tiles of the players
     * @param match
     *
     */

    private void onModifiedPointsEvent(LightMatch match){
        switch(numberPlayers) {
            case 2 -> {
                if(match.getPlayers().get(0).getPointsTiles().size() == 1) {
                    printPlayer1Points(match.getPlayers().get(0).getPointsTiles().get(0).getCLIRepresentation(), EMPTYSPOT);
                }else if(match.getPlayers().get(0).getPointsTiles().size() == 2){
                    printPlayer1Points(match.getPlayers().get(0).getPointsTiles().get(0).getCLIRepresentation(),match.getPlayers().get(0).getPointsTiles().get(1).getCLIRepresentation());
                }
                if(match.getPlayers()[1].getPointsTiles().size() == 1) {
                    printPlayer2Points(match.getPlayers()[1].getPointsTiles()[0], EMPTYSPOT);
                }else if(match.getPlayers()[1].getPointsTiles().size() == 2){
                    printPlayer2Points(match.getPlayers()[1].getPointsTiles()[0], match.getPlayers()[1].getPointsTiles()[1]);
                }
            }
            case 3->{
                if(match.getPlayers()[0].getPointsTiles().size() == 1) {
                    printPlayer1Points(match.getPlayers()[0].getPointsTiles()[0], EMPTYSPOT);
                }else if(match.getPlayers()[0].getPointsTiles().size() == 2){
                    printPlayer1Points(match.getPlayers()[0].getPointsTiles()[0], match.getPlayers()[0].getPointsTiles()[1]);

                }

                if(match.getPlayers()[1].getPointsTiles().size() == 1) {
                    printPlayer2Points(match.getPlayers()[1].getPointsTiles()[0], EMPTYSPOT);
                }else if(match.getPlayers()[1].getPointsTiles().size() == 2){
                    printPlayer2Points(match.getPlayers()[1].getPointsTiles()[0], match.getPlayers()[1].getPointsTiles()[1]);
                }

                if(match.getPlayers()[2].getPointsTiles().size() == 1) {
                    printPlayer3Points(match.getPlayers()[2].getPointsTiles()[0], EMPTYSPOT);
                }else if(match.getPlayers()[2].getPointsTiles().size() == 2){
                    printPlayer3Points(match.getPlayers()[2].getPointsTiles()[0], match.getPlayers()[2].getPointsTiles()[1]);

                }
            }case 4->{
                if(match.getPlayers()[0].getPointsTiles().size() == 1) {
                    printPlayer1Points(match.getPlayers()[0].getPointsTiles()[0], EMPTYSPOT);
                }else if(match.getPlayers()[0].getPointsTiles().size() == 2){
                    printPlayer1Points(match.getPlayers()[0].getPointsTiles()[0], match.getPlayers()[0].getPointsTiles()[1]);

                }

                if(match.getPlayers()[1].getPointsTiles().size() == 1) {
                    printPlayer2Points(match.getPlayers()[1].getPointsTiles()[0], EMPTYSPOT);
                }else if(match.getPlayers()[1].getPointsTiles().size() == 2){
                    printPlayer2Points(match.getPlayers()[1].getPointsTiles()[0], match.getPlayers()[1].getPointsTiles()[1]);
                }

                if(match.getPlayers()[2].getPointsTiles().size() == 1) {
                    printPlayer3Points(match.getPlayers()[2].getPointsTiles()[0], EMPTYSPOT);
                }else if(match.getPlayers()[2].getPointsTiles().size() == 2) {
                    printPlayer3Points(match.getPlayers()[2].getPointsTiles()[0], match.getPlayers()[2].getPointsTiles()[1]);
                }

                if(match.getPlayers()[3].getPointsTiles().size() == 1) {
                    printPlayer4Points(match.getPlayers()[3].getPointsTiles()[0], EMPTYSPOT);
                }else if(match.getPlayers()[3].getPointsTiles().size() == 2){
                    printPlayer4Points(match.getPlayers()[3].getPointsTiles()[0], match.getPlayers()[3].getPointsTiles()[1]);
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

    private void onModifiedBookshelfEvent(LightMatch match){
        switch(numberPlayers) {
            case 2 -> {
                printBookshelf1(match.getPlayers()[0].getBookshelf().getCLIRepresentation());
                printBookshelf2(match.getPlayers()[1].getBookshelf().getCLIRepresentation());
            }
            case 3->{
                printBookshelf1(match.getPlayers()[0].getBookshelf().getCLIRepresentation());
                printBookshelf2(match.getPlayers()[1].getBookshelf().getCLIRepresentation());
                printBookshelf3(match.getPlayers()[2].getBookshelf().getCLIRepresentation());
            }case 4->{
                printBookshelf1(match.getPlayers()[0].getBookshelf().getCLIRepresentation());
                printBookshelf2(match.getPlayers()[1].getBookshelf().getCLIRepresentation());
                printBookshelf3(match.getPlayers()[2].getBookshelf().getCLIRepresentation());
                printBookshelf4(match.getPlayers()[3].getBookshelf().getCLIRepresentation());
            }

        }
    }

    private void onModifiedLivingRoomEvent(LightMatch match){
        printLivingRoom(match.getLivingRoom().getCLIRepresentation());
    }

    private void onModifiedMatchEndedEvent(LightMatch match){
        finalScores= (HashMap<Player, Integer>) match.getScores();
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
                 currentView = event.getViewType();
                 print();
             }


         }


    }

    private void onLoginViewEvent(SelectViewEvent event){
        currentView = event.getViewType();
        System.out.print(ANSIParameters.CLEAR_SCREEN + ANSIParameters.CURSOR_HOME);
        System.out.flush();
        System.out.println("Welcome to MyShelfie!");

        System.out.println("Please insert your nickname: ");
    }

    private void onGameViewEvent(SelectViewEvent event){
        currentView = event.getViewType();
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
        currentView = event.getViewType();

        System.out.print(ANSIParameters.CLEAR_SCREEN + ANSIParameters.CURSOR_HOME);
        System.out.flush();

        System.out.println("Match ended");

        System.out.println("The final scores are: ");
        Player p;

        for(int i=0;i<numberPlayers;i++){
            p =players.get(i);
            System.out.println(ANSIParameters.RED + p.getPlayerNickName() + ANSIParameters.CRESET + " with: " + ANSIParameters.MAGENTA + finalScores.get(p) + " points" + ANSIParameters.CRESET);

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
            for(int j = 0; j < length; j++){
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
