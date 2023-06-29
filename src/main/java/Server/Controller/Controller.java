
package Server.Controller;

import Server.Events.MVEvents.MatchStartedEvent;
import Server.Events.MVEvents.ModifiedMatchEndedEvent;
import Server.Events.SelectViewEvents.*;

import Server.Events.VCEvents.LoginEvent;
import Server.Listeners.SelectViewEventListener;

import Server.Listeners.VCEventListener;
import Server.Model.Chat.Message;
import Server.Model.GameItems.LivingRoom;
import Server.Model.GameItems.LivingRoomTileSpot;

import Server.Model.GameItems.TileType;
import Server.Model.LightMatch;
import Server.Model.Match;
import Server.Events.VCEvents.VCEvent;
import Server.Model.MatchStatus.NotRunning;
import Server.Model.MatchStatus.Running;
import Server.Model.MatchStatus.WaitingForPlayers;
import Server.Model.Player.Player;
import Server.Network.Server;
import Server.Network.VirtualRMIView;
import Server.Network.VirtualView;
import Utils.MathUtils.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.sql.Time;
import java.util.*;

/**
 * Controller class to manage the requests from the client
 * @author Valentino Guerrini & Paolo Gennaro
 */
public class Controller implements VCEventListener {

    private /*final*/ Match match;

    private VirtualView caller;
    private VirtualView currentPlayerView;
    private Map<Integer,VirtualView > PlayerViews=new HashMap<>();
    private List<SelectViewEventListener> selectViewEventListeners;
    private Map<Integer, Player> hashNicknames = new HashMap<>();
    private boolean everyoneOffline = false;
    private boolean onlyOneIsConnected = false;
    Timer timer = new Timer();
    private Server server;

    public Controller(Match match, Server server){
        this.match = match;
        this.server = server;
        selectViewEventListeners = new ArrayList<>();
    }

    /**
     * Method to manage the login event, if there is a player with the same nickname, the login is not possible so
     * the method return a LoginView event with the boolean value set to false, otherwise the player is added to the match
     * @param nickname nickname of the player
     * @author ValentinoGuerrini
     */
    private void onLoginEvent(String nickname,int numberofPlayers) throws RemoteException{

        ArrayList<Player> players = match.getPlayers();

        if(players.size()==0 && numberofPlayers!=0){
            //This means that the match has no players and this login event is from the match opener (firstToJoin = true)
            if(nickname.length() > 20) {
                caller.onSelectViewEvent(new LoginView(true,"Nickname too long, max 20 characters"));
            }else if(nickname.length() < 3) {
                caller.onSelectViewEvent(new LoginView(true,"Nickname too short, min 3 characters"));
            }else if(nickname.contains(" ")) {
                caller.onSelectViewEvent(new LoginView(true,"Nickname cannot contain spaces"));
            }else {
                match.setNumberOfPlayers(numberofPlayers);
                Player newPlayer = new Player(match, nickname.hashCode(), nickname);
                match.addContestant(newPlayer);

                PlayerViews.put(nickname.hashCode(), caller);
                hashNicknames.put(nickname.hashCode(), newPlayer);
                caller.onSelectViewEvent(new GameView("Waiting for other players to join..."));
                caller.getConnectionInfo().setNickname(nickname);
                //server.updateConnectionStatus(caller.getConnectionInfo(), true);
                System.out.println("Controller connection info.nickname: " + caller.getConnectionInfo().getNickname());

                //Now that the match opener has joined, deciding how many players the match will have,
                //we have to check if there were any clients waiting for the match to start accepting players
                if(!server.getClientsWaitingForMatch().isEmpty()){
                    //This means there actually were some clients waiting
                    //Now the match will be in WaitingForPlsayers since the method addContestant evolves the match
                    Queue<VirtualView> noMoreWaitingClients = null;
                    noMoreWaitingClients =  server.dequeueWaitingClients();
                    for(VirtualView vv : noMoreWaitingClients){
                        this.addSelectViewEventListener(vv);
                        vv.addVCEventListener(this);
                        if(vv instanceof VirtualRMIView){
                            vv.run();
                        }else{
                            new Thread(vv).start();
                        }
                    }
                }
            }
        }else{
            for(Player player : players){
                if(player.getPlayerID()==nickname.hashCode()) {
                    caller.onSelectViewEvent(new LoginView("Nickname already taken"));
                    return;
                }
            }
            if(nickname.length() > 20) {
                caller.onSelectViewEvent(new LoginView("Nickname too long, max 20 characters"));
            }else if(nickname.length() < 3) {
                caller.onSelectViewEvent(new LoginView("Nickname too short, min 3 characters"));
            }else if(nickname.contains(" ")) {
                caller.onSelectViewEvent(new LoginView("Nickname cannot contain spaces"));
            }else{
                Player newPlayer = new Player(match, nickname.hashCode(),nickname);
                match.addContestant(newPlayer);
                PlayerViews.put(nickname.hashCode(),caller);
                hashNicknames.put(nickname.hashCode(),newPlayer);
                caller.getConnectionInfo().setNickname(nickname);
                System.out.println("Controller connection info.nickname: " + caller.getConnectionInfo().getNickname());
                if(match.getMatchStatus() instanceof WaitingForPlayers){
                    caller.onSelectViewEvent(new GameView("Waiting for other players to join..."));
                }else if (match.getMatchStatus() instanceof Running){
                    if(!server.getClientsWaitingForMatch().isEmpty()){
                        server.dequeueWaitingClients();
                    }
                    Player firstPlayer = match.getFirstPlayer();
                    currentPlayerView = PlayerViews.get(firstPlayer.getPlayerID());
                    for(VirtualView vv : PlayerViews.values()){
                        if(vv != currentPlayerView){
                            vv.onSelectViewEvent(new GameView("Match started! "+ firstPlayer.getPlayerNickName() + " is the first player!"));
                        }else{
                            vv.onSelectViewEvent(new PickingTilesGameView("Match started! You are the first player! Pick some tiles and then checkout!"));
                        }
                    }
                }

            }
        }





    }

    /**
     *Method to manage the OpenChat event, it returns a ViewType event with the chat open
     * @author ValentinoGuerrini
     */
    private void onOpenChatEvent() throws RemoteException{
        caller.onSelectViewEvent(new ChatONView());
    }

    /**
     *Method to manage the CloseChat event, it returns a ViewType event with the chat closed
     * @author ValentinoGuerrini
     */
    private void onCloseChatEvent() throws RemoteException {
        caller.onSelectViewEvent( new ChatOFFView());
    }

    /**
     * Method to manage the "send message" event, it adds the message to the chat
     * @param message to be added to the chat
     * @author ValentinoGuerrini
     */
    private void onSendMessageEvent(Message message){
        Player sender = message.getSender();
        Player receiver = message.getReceiver();
        if(receiver == null) {
            VirtualView[] views = new VirtualView[PlayerViews.size()];
            int i=0;
            for(VirtualView vv : PlayerViews.values()){
                views[i] = vv;
                i++;
            }
            match.getGameChat().addMessage(message, views);
        }else{
            match.getGameChat().addMessage(message, PlayerViews.get(sender.getPlayerID()),
                    PlayerViews.get(receiver.getPlayerID()));
        }
    }

    /**
     * Method to manage the select column event, it adds the selectedTiles of the current player
     * to the column selected by him.
     * @param column selected from the player
     * @author Paolo Gennaro
     */
    private void onSelectColumnEvent(int column) throws RemoteException{
        boolean flag=false;

        if(PlayerViews.get(match.getCurrentPlayer().getPlayerID())!=caller){
            caller.onSelectViewEvent(new GameView("It's " + match.getCurrentPlayer().getPlayerNickName() + "'s turn! Wait for your turn!"));
            return;
        }


        Player currentPlayer = match.getCurrentPlayer();
        int numberTakenTiles=0;
        if(currentPlayer.getTakenTiles() != null) {
            for (int i = 0; i < currentPlayer.getTakenTiles().length; i++) {
                if (currentPlayer.getTakenTiles()[i] != null) {
                    numberTakenTiles++;
                }
            }
        }

        if(numberTakenTiles == 0){
            currentPlayerView.onSelectViewEvent(new GameView("It's " + match.getCurrentPlayer().getPlayerNickName() + "'s turn! Wait for your turn!"));
            flag=true;
        }

        if(currentPlayer.getBookshelf().getLastIndexes().get(column) < numberTakenTiles){
            currentPlayerView.onSelectViewEvent(new InsertingTilesGameView("Not enough space in this column!"));

            return;
        }

        try{
            for(int i=0; !flag && i<match.getCurrentPlayer().getTakenTiles().length; i++){
                if(match.getCurrentPlayer().getTakenTiles()[i] != null) {
                    match.getCurrentPlayer().getBookshelf().insertTile(column, match.getCurrentPlayer().getTakenTiles()[i]);
                }
            }
            match.getCurrentPlayer().clearTakenTiles();
            match.clearSelectedTiles();

            match.checkCommonGoals(currentPlayer);


            int numberOfPlayers = match.getNumberOfPlayers();


            if(match.getFirstToFinish()==null){
                if(match.checkIfBookshelfIsFull(currentPlayer)){
                    try{
                        //match.assignMatchEndedTile();
                        if(currentPlayer.getNextPlayer().equals(match.getFirstPlayer())){
                            match.calculateFinalScores();
                            for(int i=0; i<numberOfPlayers; i++){
                                PlayerViews.get(match.getPlayers().get(i).getPlayerID()).onSelectViewEvent(new EndedMatchView());
                            }

                        }else{
                            match.setCurrentPlayer();
                            for(int i=0; i<numberOfPlayers; i++){
                                if(PlayerViews.get(match.getPlayers().get(i).getPlayerID())!=currentPlayerView && PlayerViews.get(match.getPlayers().get(i).getPlayerID())!=PlayerViews.get(match.getCurrentPlayer().getPlayerID())){
                                    PlayerViews.get(match.getPlayers().get(i).getPlayerID()).onSelectViewEvent(new GameView("It's " + match.getCurrentPlayer().getPlayerNickName() + "'s turn! Wait for your turn!"));
                                }
                            }

                            currentPlayerView.onSelectViewEvent(new GameView("It's " + match.getCurrentPlayer().getPlayerNickName() + "'s turn! Wait for match to finish"));
                            currentPlayerView = PlayerViews.get(match.getCurrentPlayer().getPlayerID());
                            currentPlayerView.onSelectViewEvent(new PickingTilesGameView("This is your last turn!"));
                        }

                    }catch (UnsupportedOperationException e) {
                        //do nothing
                    }
                }else{
                    match.setCurrentPlayer();

                    for(int i=0; i<numberOfPlayers; i++){
                        if(!flag && PlayerViews.get(match.getPlayers().get(i).getPlayerID())!=PlayerViews.get(match.getCurrentPlayer().getPlayerID())){
                            PlayerViews.get(match.getPlayers().get(i).getPlayerID()).onSelectViewEvent(new GameView("It's " + match.getCurrentPlayer().getPlayerNickName() + "'s turn! Wait for your turn!"));
                        }
                    }


                    currentPlayerView = PlayerViews.get(match.getCurrentPlayer().getPlayerID());

                    currentPlayerView.onSelectViewEvent(new PickingTilesGameView());
                }

            }else{
                if(currentPlayer.getNextPlayer().equals(match.getFirstPlayer())){
                    match.calculateFinalScores();
                    for(int i=0; i<numberOfPlayers; i++){
                        PlayerViews.get(match.getPlayers().get(i).getPlayerID()).onSelectViewEvent(new EndedMatchView());
                    }

                }else{
                    match.setCurrentPlayer();
                    for(int i=0; i<numberOfPlayers; i++){
                        if(PlayerViews.get(match.getPlayers().get(i).getPlayerID())!=currentPlayerView && PlayerViews.get(match.getPlayers().get(i).getPlayerID())!=PlayerViews.get(match.getCurrentPlayer().getPlayerID())){
                            PlayerViews.get(match.getPlayers().get(i).getPlayerID()).onSelectViewEvent(new GameView("It's " + match.getCurrentPlayer().getPlayerNickName() + "'s turn! Wait for match to finish" ));
                        }
                    }

                    currentPlayerView.onSelectViewEvent(new GameView("It's " + match.getCurrentPlayer().getPlayerNickName() + "'s turn! Wait for match to finish" ));
                    currentPlayerView = PlayerViews.get(match.getCurrentPlayer().getPlayerID());

                    currentPlayerView.onSelectViewEvent(new PickingTilesGameView("This is your last turn!"));
                }
            }

        } catch (UnsupportedOperationException e) {
            currentPlayerView.onSelectViewEvent(new InsertingTilesGameView("This column is already full|\nPlease select another one!"));
        } catch (IndexOutOfBoundsException e){
            currentPlayerView.onSelectViewEvent(new InsertingTilesGameView( "This column does not exists!"));
        }

    }
    /**
     * Method to manage the selected tile event, the selected tiles are added to the match if there is space for them
     * @param coordinates of the tile selected by the player
     * @author ValentinoGuerrini
     */

    private void onClickOnTileEvent(int[] coordinates) throws RemoteException{


        int[] tmp,selectedTiles;
        String messageTiles = "";
        boolean flag=false;
        int playernumber = match.getNumberOfPlayers();
        tmp=match.getSelectedTiles();

        if(PlayerViews.get(match.getCurrentPlayer().getPlayerID())!=caller){
            caller.onSelectViewEvent(new GameView("It's " + match.getCurrentPlayer().getPlayerNickName() + "'s turn! Wait for your turn!"));
        }

        LivingRoomTileSpot[][] livingRoomTileSpots = match.getLivingRoom().getTileMatrix();

        //verifies if the tile in that position is selectable

        if(livingRoomTileSpots[coordinates[0]][coordinates[1]].getDotsNumber()==-1){
            caller.onSelectViewEvent(new PickingTilesGameView("This tile is not selectable!"));
        }else if(playernumber==2 && livingRoomTileSpots[coordinates[0]][coordinates[1]].getDotsNumber()>=3) {
            caller.onSelectViewEvent(new PickingTilesGameView("This tile is not selectable!"));
        }else if(playernumber==3 && livingRoomTileSpots[coordinates[0]][coordinates[1]].getDotsNumber()>=4) {
            caller.onSelectViewEvent(new PickingTilesGameView("This tile is not selectable!"));
        }

        if(tmp==null){
            selectedTiles=new int[2];

            selectedTiles[0]=coordinates[0];
            selectedTiles[1]=coordinates[1];
            messageTiles += "Tiles Selected: ";
            for (int i = 0; i < selectedTiles.length; i+=2) {
                char c = (char) ('a' + selectedTiles[i]);
                messageTiles += c;
                messageTiles += ",";
                messageTiles += String.valueOf(selectedTiles[i+1]);
                messageTiles += " ";
            }

            match.setSelectedTiles(selectedTiles);
            caller.onSelectViewEvent(new PickingTilesGameView(messageTiles));
            return;
        }

        //check if the tile is already selected in if it is the coordinates are setted to -1


        for(int i=0;i<tmp.length;i+=2){
            if(tmp[i]== coordinates[0] && tmp[i+1]==coordinates[1]){
                tmp[i]=-1;
                tmp[i+1]=-1;
                flag=true;
            }
        }
        //if the tile is already selected it is removed from the selected tiles array else it is added


        if(flag){
            selectedTiles=new int[tmp.length-2];
            int j=0;
            for (int k : tmp) {
                if (k != -1) {
                    selectedTiles[j] = k;
                    j++;
                }
            }
        }else if(tmp.length <6){
            selectedTiles=new int[tmp.length+2];
            System.arraycopy(tmp, 0, selectedTiles, 0, tmp.length);
            selectedTiles[tmp.length]=coordinates[0];
            selectedTiles[tmp.length+1]=coordinates[1];
        }else{
            selectedTiles=new int[tmp.length];
            System.arraycopy(tmp, 0, selectedTiles, 0, tmp.length);

            messageTiles += "You can't select more than 3 tiles!";
        }
        messageTiles += "Tiles Selected: ";
        for (int i = 0; i < selectedTiles.length; i+=2) {
            char c = (char) ('a' + selectedTiles[i]);
            messageTiles += c;
            messageTiles += ",";
            messageTiles += String.valueOf(selectedTiles[i+1]);
            messageTiles += " ";
        }
        match.setSelectedTiles(selectedTiles);
        caller.onSelectViewEvent(new PickingTilesGameView(messageTiles));
    }

    /**
     * Method to manage the checkout tiles event, it returns a ViewType event depending on the position of the tiles selected
     * If the tiles are not pickable the method returns a PickingTilesGameView event, otherwise it returns a InsertingTilesGameView event
     * @author ValentinoGuerrini
     */
    private void onCheckOutTilesEvent() throws RemoteException{
        Player currentPlayer = match.getCurrentPlayer();
        int[] selectedTiles = match.getSelectedTiles();
        LivingRoom livingRoom = match.getLivingRoom();
        TileType[] tiles;

        if(selectedTiles==null){
            match.clearSelectedTiles();
            match.setCurrentPlayer();
            currentPlayerView = PlayerViews.get(match.getCurrentPlayer().getPlayerID());
            currentPlayerView.onSelectViewEvent(new PickingTilesGameView());
            for(int i=0; i< match.getNumberOfPlayers(); i++){
                if(PlayerViews.get(match.getPlayers().get(i).getPlayerID())!=currentPlayerView ){
                    PlayerViews.get(match.getPlayers().get(i).getPlayerID()).onSelectViewEvent(new GameView("It's " + match.getCurrentPlayer().getNextPlayer().getPlayerNickName() + "'s turn! Wait for your turn!" ));
                }
            }

            return;
        }

        if((selectedTiles.length/2) > currentPlayer.getBookshelf().maxInsertableTiles()){ //We've got to divide by 2 because every element of the array is a coordinate
            match.clearSelectedTiles();
            currentPlayerView.onSelectViewEvent(new PickingTilesGameView("You can't pick more than "
                    +currentPlayer.getBookshelf().maxInsertableTiles()+" tiles!"+ "\nSelect the tiles again!"));
            return;
        }
        switch(selectedTiles.length){
            case 0 ->{
                match.clearSelectedTiles();
                match.setCurrentPlayer();
                currentPlayerView = PlayerViews.get(match.getCurrentPlayer().getPlayerID());
                currentPlayerView.onSelectViewEvent(new PickingTilesGameView());
                caller.onSelectViewEvent(new GameView("It's " + match.getCurrentPlayer().getPlayerNickName() + "'s turn! Wait for your turn!"));
            }
            case 2 -> {
                try{
                    Couple<Integer, Integer>[] coordinates = new Couple[1];
                    coordinates[0]=new Couple<>(selectedTiles[0],selectedTiles[1]);

                    tiles = livingRoom.takeTiles(coordinates);
                    currentPlayer.setTakenTiles(tiles);
                    match.clearSelectedTiles();
                    currentPlayerView.onSelectViewEvent(new InsertingTilesGameView());

                }catch(UnsupportedOperationException e){
                    match.clearSelectedTiles();
                    currentPlayerView.onSelectViewEvent(new PickingTilesGameView(e.getMessage()));
                }
            }
            case 4 -> {
                try{
                    Couple<Integer,Integer>[] coordinates = new Couple[2];
                    coordinates[0] = new Couple(selectedTiles[0],selectedTiles[1]);
                    coordinates[1] = new Couple(selectedTiles[2],selectedTiles[3]);
                    tiles= livingRoom.takeTiles(coordinates);
                    currentPlayer.setTakenTiles(tiles);
                    match.clearSelectedTiles();
                    currentPlayerView.onSelectViewEvent(new InsertingTilesGameView());
                }catch(UnsupportedOperationException e){
                    match.clearSelectedTiles();
                    currentPlayerView.onSelectViewEvent(new PickingTilesGameView(e.getMessage()));
                }
            }
            case 6 -> {
                try{
                    Couple<Integer,Integer>[] coordinates = new Couple[3];
                    coordinates[0] = new Couple(selectedTiles[0],selectedTiles[1]);
                    coordinates[1] = new Couple(selectedTiles[2],selectedTiles[3]);
                    coordinates[2] = new Couple(selectedTiles[4],selectedTiles[5]);
                    tiles= livingRoom.takeTiles(coordinates);
                    currentPlayer.setTakenTiles(tiles);
                    match.clearSelectedTiles();
                    currentPlayerView.onSelectViewEvent(new InsertingTilesGameView());
                }catch(UnsupportedOperationException e){
                    match.clearSelectedTiles();
                    currentPlayerView.onSelectViewEvent(new PickingTilesGameView(e.getMessage()));
                }
            }
        }
    }

    /**
     * Method to manage VCEvents, it uses reflection to call the right method depending on the event
     * @param event the event to manage
     * @throws NoSuchMethodException if the method called doesn't exist
     * @throws IllegalAccessException if the method called is not accessible
     */
    @Override
    public void onVCEvent(VCEvent event, VirtualView view) throws NoSuchMethodException, IllegalAccessException {
        if(everyoneOffline || onlyOneIsConnected){
            return;
        }
        String methodName = event.getMethodName();
        caller= view;

        switch(methodName){
            case"onLoginEvent" -> {
                LoginEvent loginEvent = (LoginEvent) event;
                Method method = Controller.class.getDeclaredMethod("onLoginEvent", String.class,int.class);
                try {
                    method.invoke(this, (String)event.getValue(),loginEvent.getNumberOfPlayers());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            case "onSendMessageEvent" -> {
                Method method = Controller.class.getDeclaredMethod(methodName, Message.class);
                try {
                    method.invoke(this, (Message)event.getValue());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            case "onSelectColumnEvent" -> {
                Method method = Controller.class.getDeclaredMethod(methodName, int.class);
                try {
                    method.invoke(this, (int)event.getValue());
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
            case "onClickOnTileEvent" -> {
                Method method = Controller.class.getDeclaredMethod(methodName, int[].class);
                int[] intArray = (int[]) event.getValue();
                try {
                    method.invoke(this, intArray);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            default -> {
                Method method = Controller.class.getDeclaredMethod(methodName);
                try {
                    method.invoke(this);
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        //print



    }

    @Override
    public void onVCEvent(VCEvent event) throws IllegalAccessException {
        throw new IllegalAccessException("This method should not be called");
    }

    public void addSelectViewEventListener(SelectViewEventListener listener){
        selectViewEventListeners.add(listener);
    }

    public void removeSelectViewEventListener(SelectViewEventListener listener){
        selectViewEventListeners.remove(listener);
    }

    public List<VirtualView> playerDisconnected(VirtualView vv){
        List<VirtualView> virtualViewsToRemove = new ArrayList<>();
        //TODO: check
        //First: Is the match in NotRunning status?
        if(match.getMatchStatus() instanceof NotRunning){
            //It necessarily means that there was only one player in the match, who wasn't logged in
            //We must clear all the data structures and return
            PlayerViews.clear();
            PlayerViews = null;
            hashNicknames.clear();
            hashNicknames = null;
            selectViewEventListeners.clear();
            selectViewEventListeners = null;
            server.eraseMatch(match);
            match = null;
            virtualViewsToRemove.add(vv);
            return virtualViewsToRemove;
        }
        if(PlayerViews.containsValue(vv)){ //PlayerViews only contains players that are logged in
            Integer playerHash = null;
            for(Integer i : PlayerViews.keySet()){
                if(PlayerViews.get(i).equals(vv)){
                    playerHash = i;
                    break;
                }
            }

            if(match.getMatchStatus() instanceof WaitingForPlayers){
                //This means that we were waiting for players and the disconnected player was logged in
                match.disconnectPlayer(hashNicknames.get(playerHash), PlayerViews.get(playerHash));
                if(match.getMatchStatus() == null){
                    //He was the last player in the match
                    //We erase everything
                    PlayerViews.clear();
                    PlayerViews = null;
                    hashNicknames.clear();
                    hashNicknames = null;
                    selectViewEventListeners.clear();
                    selectViewEventListeners = null;
                    server.eraseMatch(match);
                    virtualViewsToRemove.add(vv);
                    match = null;
                }else{
                    //We only forget him
                    PlayerViews.remove(playerHash);
                    hashNicknames.remove(playerHash);
                    selectViewEventListeners.remove(vv);
                    server.disconnectClient(vv);
                    virtualViewsToRemove.add(vv);
                }
                return virtualViewsToRemove;
            }

            //If I reach this point it means that the match is in Running status and the disconnected player was logged in
            Player previousCurrentPlayer = match.getCurrentPlayer();
            match.disconnectPlayer(hashNicknames.get(playerHash), PlayerViews.get(playerHash));
            if(/*match.getMatchStatus() == null*/ match.areAllPlayersDisconnected() == true){
                everyoneOffline = true;
                return null;
            }
            Player currentPlayer = match.getCurrentPlayer();

            currentPlayerView = PlayerViews.get(currentPlayer.getPlayerID());
            if(match.getDisconnectedPlayers().size() == (match.getNumberOfPlayers()-1)){
                onlyOneIsConnected = true;
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(onlyOneIsConnected == true){
                            //Only one player connected, we erase the match
                            currentPlayerView.onSelectViewEvent(new EndedMatchView("Only one player connected"));
                            currentPlayerView.onMVEvent(new ModifiedMatchEndedEvent(new LightMatch(match)));
                            server.eraseMatch(match);
                        }
                    }
                }, 120000); //2 minutes
            }
            if(previousCurrentPlayer != null && previousCurrentPlayer.getPlayerID() == playerHash) {
                /*If the current player before the disconnection is registered is different from null and is equal to
                the disconnected one then match.disconnectPlayer called above will update the current player
                and we will have to send the new current player a PickingTilesGameView, otherwise we don't
                 */

                //match.clearSelectedTiles(); //rimosso perché gestito dal match solo nel caso in cui il player disconnesso
                // sia il current player
                if(onlyOneIsConnected){
                    currentPlayerView.onSelectViewEvent(new GameView("Since you are the only player connected," +
                            "the match will be paused"));
                }else{
                    if(previousCurrentPlayer.getNextPlayer().getPlayerID()==match.getFirstPlayer().getPlayerID() && match.getFirstToFinish()!=null){
                        match.calculateFinalScores();

                        for(Integer i  : PlayerViews.keySet()) {
                            if (!match.getDisconnectedPlayersVirtualViews().containsKey(PlayerViews.get(i))) {
                                PlayerViews.get(i).onSelectViewEvent(new EndedMatchView());
                            }
                        }

                    }else{
                        currentPlayerView.onSelectViewEvent(new PickingTilesGameView("A player has lost connection,"+
                                "so now it's your turn!\nPlease select some tiles and then checkout"));
                    }

                }
            }else if(onlyOneIsConnected){
                currentPlayerView.onSelectViewEvent(new GameView("Since you are the only player connected," +
                        "the match will be paused"));
            }


            for(Integer i  : PlayerViews.keySet()){
                if(!match.getDisconnectedPlayersVirtualViews().containsKey(PlayerViews.get(i))
                        && !PlayerViews.get(i).equals(currentPlayerView)){
                    PlayerViews.get(i).onSelectViewEvent(new GameView("A player has lost connection, It's " + match.getCurrentPlayer().getPlayerNickName() + "'s turn! Wait for your turn!" ));
                }
            }
        }else{
            //This will happen if the disconnected player was not logged in
            //If I reach this point the match was not in NotRunning status and the disconnected player was not logged in
            //Since in order for the match to be Running all the players need to be logged in, we can be sure that
            //the match is in WaitingForPlayers status
            //The client was not logged in => was not in the match as a contestant but only as a listener
            match.removeMVEventListener(vv);
            this.removeSelectViewEventListener(vv);
            server.disconnectClient(vv);
            virtualViewsToRemove.add(vv);
            return virtualViewsToRemove;
        }
        return null;
    }

    public void playerConnected(VirtualView vv) {
        //TODO: check
        if (PlayerViews.containsValue(vv)) {
            for (Integer i : PlayerViews.keySet()) {
                if (PlayerViews.get(i).equals(vv)) {
                    if(everyoneOffline){
                        //Now we have only one player connected
                        everyoneOffline = false;
                        onlyOneIsConnected = true;
                        timer = new Timer();
                        vv.onSelectViewEvent(new GameView("Since you are the only player connected," +
                                "the match will be paused"));
                        timer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                if(onlyOneIsConnected == true){
                                    //Only one player connected, we erase the match
                                    currentPlayerView.onSelectViewEvent(new EndedMatchView("Only one player connected"));
                                    currentPlayerView.onMVEvent(new ModifiedMatchEndedEvent(new LightMatch(match)));
                                    server.eraseMatch(match);
                                }
                            }
                        }, 120000); //2 minutes
                        match.reconnectPlayer(hashNicknames.get(i), PlayerViews.get(i)); //Reconnect him even if
                                                                                        // the match is paused
                    }else{
                        //There was at least one player connected, considering the newly connected one there are at
                        //least two
                        match.reconnectPlayer(hashNicknames.get(i), PlayerViews.get(i));
                        Player p = match.getCurrentPlayer();
                        if(onlyOneIsConnected){
                            //If only one player was conneceted and now another one connected, we cancel the timer
                            onlyOneIsConnected = false;
                            timer.cancel();
                            vv.onSelectViewEvent(new GameView("It's "+p.getPlayerNickName()+"'s turn! Wait for your turn!"));
                            //currentPlayerView.onSelectViewEvent(new PickingTilesGameView());
                            currentPlayerView = PlayerViews.get(p.getPlayerID());
                            currentPlayerView.onSelectViewEvent(new PickingTilesGameView());
                        }else{
                            //This player has connected but there was at least one
                            vv.onSelectViewEvent(new GameView("It's "+p.getPlayerNickName()+"'s turn! Wait for your turn!"));
                        }
                    }
                }
            }
            match.triggerMVUpdate();
            //match.reconnectPlayer(hashNicknames.get(playerHash), PlayerViews.get(playerHash));
        }
    }

    public Map<Integer, VirtualView> getPlayerViews() {
        return PlayerViews;
    }
}
