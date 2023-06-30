
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
import Server.Model.MatchStatus.Closing;
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
 * The Controller class is responsible for managing events and updating views in a game.
 * It contains a variety of methods to handle various game-related events, including
 * login, chat and gameplay interactions. These events are received via the VCEvent interface,
 * which this class implements.
 * <p>
 * The Controller also manages a list of SelectViewEventListeners, allowing it to notify
 * other components of changes in view selection.
 * <p>
 * All event methods such as onLoginEvent, onOpenChatEvent, onCloseChatEvent, onSendMessageEvent,
 * onSelectColumnEvent, onClickOnTileEvent and onCheckOutTilesEvent are handled here.
 * <p>
 * In addition to event management, the Controller class provides functionality for managing
 * the views of individual players.
 * <p>
 * Other functionalities include methods for a player's connection or disconnection,
 * manipulation of view event listeners, and control methods related to the game's virtual views.
 *
 * Below are the methods included in this class:
 *
 * <ul>
 * <li>{@link #onVCEvent(VCEvent event)} - Handles various VirtualView events. Should not be called directly.</li>
 * <li>{@link #onVCEvent(VCEvent event, VirtualView view)} - Handles VirtualView events with the provided event and view details.</li>
 * <li>{@link #playerDisconnected(VirtualView vv)} - Handles the disconnection of a player, updating the game state as required.</li>
 * <li>{@link #playerConnected(VirtualView vv)} - Handles the connection of a player, updating the game state as needed.</li>
 * <li>{@link #addSelectViewEventListener(SelectViewEventListener listener)} - Adds a SelectViewEventListener to the list of event listeners.</li>
 * <li>{@link #removeSelectViewEventListener(SelectViewEventListener listener)} - Removes a SelectViewEventListener from the list of event listeners.</li>
 * <li>{@link #getPlayerViews()} - Retrieves the views of the players.</li>
 * <li>{@link #onLoginEvent(String nickname, int numberOfPlayers)} - Handles the login event for a player.</li>
 * <li>{@link #onOpenChatEvent()} - Handles the event of a player opening a chat.</li>
 * <li>{@link #onCloseChatEvent()} - Handles the event of a player closing a chat.</li>
 * <li>{@link #onSendMessageEvent(Message message)} - Handles the event of a player sending a message in chat.</li>
 * <li>{@link #onSelectColumnEvent(int column)} - Handles the event of a player selecting a column in the bookshelf.</li>
 * <li>{@link #onClickOnTileEvent(int[] coordinates)} - Handles the event of a player clicking on a tile in the livingroom.</li>
 * <li>{@link #onCheckOutTilesEvent()} - Handles the event of a player checking out tiles in the game.</li>
 * </ul>
 *
 * @author Valentino Guerrini & Patrick Poggi & Paolo Gennaro
 *
 * @see VCEvent
 * @see VirtualView
 * @see SelectViewEventListener
 */
public class Controller implements VCEventListener {
    private /*final*/ Match match;
    private VirtualView caller;
    private VirtualView currentPlayerView;
    private Map<Integer, VirtualView> PlayerViews=new HashMap<>();
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
     *
     * Handles the login event when a player attempts to log in to the match.
     * This method is responsible for processing the login event by validating the provided nickname and determining the
     * appropriate actions based on the current state of the match and the number of players.
     *
     * @author Valentino Guerrini
     *
     * @param nickname The nickname of the player trying to log in.
     * @param numberOfPlayers The desired number of players for the match.
     * @throws RemoteException If a remote communication error occurs.
     */
    private void onLoginEvent(String nickname,int numberOfPlayers) throws RemoteException{

        ArrayList<Player> players = match.getPlayers();

        if(players.size()==0 && numberOfPlayers!=0){
            //This means that the match has no players and this login event is from the match opener (firstToJoin = true)
            if(nickname.length() > 20) {
                caller.onSelectViewEvent(new LoginView(true,"Nickname too long, max 20 characters"));
            }else if(nickname.length() < 3) {
                caller.onSelectViewEvent(new LoginView(true,"Nickname too short, min 3 characters"));
            }else if(nickname.contains(" ")) {
                caller.onSelectViewEvent(new LoginView(true,"Nickname cannot contain spaces"));
            }else {
                match.setNumberOfPlayers(numberOfPlayers);
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
     * Handles the selection of a column by the player to insert tiles. This method encapsulates the core game logic, including
     * determining if the player can make the desired move, handling the end of turns, checking for completion of common goals,
     * and managing the end of a match. This method also provides proper communication back to the player about the result
     * of their action or the current state of the game.
     *
     * @param column An integer representing the index of the column in the player's bookshelf where the player wants to insert tiles.
     * @throws RemoteException If a remote communication error occurs.
     *
     * @author Valentino Guerrini & Paolo Gennaro
     *
     * <p>This method throws a RemoteException because it uses RMI (Remote Method Invocation) for communication with the player's view.</p>
     *
     * <p>The game logic handled by this method includes:</p>
     *
     * <ul>
     * <li>Rejecting the move if the player making the request isn't the current player.</li>
     * <li>Counting the number of tiles the player has taken.</li>
     * <li>Passing the turn if the player has taken no tiles.</li>
     * <li>Rejecting the move if the selected column does not have enough space for the tiles.</li>
     * <li>Inserting the tiles into the column, and then clearing the player's taken and selected tiles.</li>
     * <li>Checking if the player has completed a common goal, and if they have, updating the points.</li>
     * <li>Checking if the player has completed their bookshelf, and if they have, checking if they're the last player.
     *     If they're the last player, the match ends, otherwise it continues with the next player.</li>
     * <li>If the player hasn't completed their bookshelf, the turn is passed to the next player.</li>
     * <li>If another player has already completed their bookshelf, the match ends if the current player is the last player.
     *     If the current player is not the last player, the turn is passed to the next player.</li>
     * </ul>
     *
     * <p>For each of these steps, the method communicates back to the player by triggering onSelectViewEvent with
     * appropriate view objects (like GameView or EndedMatchView), providing feedback about the current state of the game
     * or the result of their action.</p>
     */
    private void onSelectColumnEvent(int column) throws RemoteException{
        boolean flag=false;

        //handle a request from a player who is not supposed to play
        if(PlayerViews.get(match.getCurrentPlayer().getPlayerID())!=caller){
            caller.onSelectViewEvent(new GameView("It's " + match.getCurrentPlayer().getPlayerNickName() + "'s turn! Wait for your turn!"));
            return;
        }

        Player currentPlayer = match.getCurrentPlayer();
        int numberTakenTiles=0;

        //calculate the number of taken tiles
        if(currentPlayer.getTakenTiles() != null) {
            for (int i = 0; i < currentPlayer.getTakenTiles().length; i++) {
                if (currentPlayer.getTakenTiles()[i] != null) {
                    numberTakenTiles++;
                }
            }
        }

        //if the number of taken tiles equals zero the player pass the turn, a boolean is set true to notify to not send another view to the current player
        if(numberTakenTiles == 0){
            currentPlayerView.onSelectViewEvent(new GameView("It's " + match.getCurrentPlayer().getPlayerNickName() + "'s turn! Wait for your turn!"));
            flag=true;
        }

        //if the selected column have not enough space for the tiles the player must select another column
        if(currentPlayer.getBookshelf().getLastIndexes().get(column) < numberTakenTiles){
            currentPlayerView.onSelectViewEvent(new InsertingTilesGameView("Not enough space in this column!"));
            return;
        }

        try{
            //insert the tiles in the coloumn
            for(int i=0; !flag && i<match.getCurrentPlayer().getTakenTiles().length; i++){
                if(match.getCurrentPlayer().getTakenTiles()[i] != null) {
                    match.getCurrentPlayer().getBookshelf().insertTile(column, match.getCurrentPlayer().getTakenTiles()[i]);
                }
            }

            //once the tiles were inserted the tiles picked from the player must be empty
            match.getCurrentPlayer().clearTakenTiles();
            match.clearSelectedTiles();

            //since the player's bookshelf was updated we check the common goal
            match.checkCommonGoals(currentPlayer);


            int numberOfPlayers = match.getNumberOfPlayers();

            if(match.getFirstToFinish()==null){
                //there is not a player who already have completed the bookshelf

                if(match.checkIfBookshelfIsFull(currentPlayer)){
                    //after inserting the tiles the player completed the bookshelf
                    try{
                        //if the player that completed the bookshelf was the last player in the circle, the match is ended
                        if(currentPlayer.getNextPlayer().equals(match.getFirstPlayer())){
                            match.calculateFinalScores();
                            for(int i=0; i<numberOfPlayers; i++){
                                PlayerViews.get(match.getPlayers().get(i).getPlayerID()).onSelectViewEvent(new EndedMatchView());
                            }
                        //if the player that completed the bookshelf was not the last player in the circle another round to play
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
                        //do nothing, we tried to notify a disconnected player
                    }
                }else{
                    //after inserting the tiles the player have not completed the bookshelf
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
                //another player already completed the bookshelf

                //check if the current player is the last, if it is, the match should end
                if(currentPlayer.getNextPlayer().equals(match.getFirstPlayer())){
                    match.calculateFinalScores();
                    for(int i=0; i<numberOfPlayers; i++){
                        PlayerViews.get(match.getPlayers().get(i).getPlayerID()).onSelectViewEvent(new EndedMatchView());
                    }
                //if the current player is not the last at least another turn
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
     * Handles the event of a player clicking on a tile. This method encapsulates the logic for tile selection,
     * including verifying if the player's action is valid, updating the game state with the selected tiles, and
     * managing proper communication back to the player about the result of their action.
     *
     * @param coordinates An array of two integers representing the row and column indexes of the clicked tile in the livingroom .
     * @throws RemoteException If a remote communication error occurs.
     *
     * @author Valentino Guerrini
     *
     * <p>This method throws a RemoteException because it uses RMI (Remote Method Invocation) for communication with the player's view.</p>
     *
     * <p>The tile selection logic handled by this method includes:</p>
     *
     * <ul>
     * <li>Verifying that it is the player's turn.</li>
     * <li>Checking if the clicked tile is selectable based on its location and number of dots.</li>
     * <li>If no tile has been previously selected, setting the clicked tile as selected and communicating this to the player.</li>
     * <li>If tiles have been previously selected, verifying whether the clicked tile is already selected.
     *     If it is, it's deselected; if it isn't and the maximum number of selectable tiles hasn't been reached, it's added to the selection.</li>
     * <li>If the maximum number of selectable tiles has been reached and the clicked tile isn't already selected, notifying the player that they can't select more tiles.</li>
     * <li>For each of these steps, the method communicates back to the player by triggering onSelectViewEvent with
     *     a PickingTilesGameView object, providing feedback about the current selection of tiles or the result of their action.</li>
     * </ul>
     */
    private void onClickOnTileEvent(int[] coordinates) throws RemoteException{
        int[] tmp,selectedTiles;
        String messageTiles = "";
        boolean flag=false;
        int playernumber = match.getNumberOfPlayers();
        tmp=match.getSelectedTiles();

        //if it is not the turn of the player he can not select any tile
        if(PlayerViews.get(match.getCurrentPlayer().getPlayerID())!=caller){
            caller.onSelectViewEvent(new GameView("It's " + match.getCurrentPlayer().getPlayerNickName() + "'s turn! Wait for your turn!"));
            return;
        }

        LivingRoomTileSpot[][] livingRoomTileSpots = match.getLivingRoom().getTileMatrix();

        //verifies if the tile in that position is selectable
        if(livingRoomTileSpots[coordinates[0]][coordinates[1]].getDotsNumber()==-1){
            caller.onSelectViewEvent(new PickingTilesGameView("This tile is not selectable!"));
            return;
        }else if(playernumber==2 && livingRoomTileSpots[coordinates[0]][coordinates[1]].getDotsNumber()>=3) {
            caller.onSelectViewEvent(new PickingTilesGameView("This tile is not selectable!"));
            return;
        }else if(playernumber==3 && livingRoomTileSpots[coordinates[0]][coordinates[1]].getDotsNumber()>=4) {
            caller.onSelectViewEvent(new PickingTilesGameView("This tile is not selectable!"));
            return;
        }

        //if the player have not already selected any tiles instantiate an array of two coordinates for the selected tiles and notify the player with the selected tiles
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
            //if the number of selected tiles is > 3 notify the player that he can not take more that three tiles
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
     * Handles the event of a player checking out tiles after selecting them. This method ensures the validity of the
     * selection, performs the checkout operation and updates the game state accordingly.
     *
     * @throws RemoteException If a remote communication error occurs.
     *
     * @author Valentino Guerrini
     *
     * <p>This method throws a RemoteException because it uses RMI (Remote Method Invocation) for communication with the player's view.</p>
     *
     * <p>The check out logic handled by this method includes:</p>
     *
     * <ul>
     * <li>Verifying whether any tiles were selected. If none were selected, the player's turn is skipped.</li>
     * <li>Checking if the number of selected tiles exceeds the maximum number of insertable tiles in the player's bookshelf. If it does,
     *     the selection is cleared and the player is notified to select fewer tiles.</li>
     * <li>Depending on the number of tiles selected, the tiles are taken from the living room.
     *     If this operation fails due to the tiles being unpickable, the selection is cleared and the player is notified to select other tiles.</li>
     * <li>Once tiles are successfully taken, the game state is updated, the selection is cleared, and the player is notified to insert the taken tiles.</li>
     * <li>For each of these steps, the method communicates back to the player by triggering onSelectViewEvent with a
     *     corresponding GameView object, providing feedback about the current state or the result of their action.</li>
     * </ul>
     */
    private void onCheckOutTilesEvent() throws RemoteException{
        Player currentPlayer = match.getCurrentPlayer();
        int[] selectedTiles = match.getSelectedTiles();
        LivingRoom livingRoom = match.getLivingRoom();
        TileType[] tiles;

        //if the selected tiles are null, the player have not selected any tiles, no tiles have to be picked, so he skip the turn and
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

        //there is no space for so many tiles in the bookshelf so the player can not pick these tiles, he have to select less tiles
        if((selectedTiles.length/2) > currentPlayer.getBookshelf().maxInsertableTiles()){ //We've got to divide by 2 because every element of the array is a coordinate
            match.clearSelectedTiles();
            currentPlayerView.onSelectViewEvent(new PickingTilesGameView("You can't pick more than "
                    +currentPlayer.getBookshelf().maxInsertableTiles()+" tiles!"+ "\nSelect the tiles again!"));
            return;
        }

        switch(selectedTiles.length){
            //if the selected tiles are pickable they will be picked and the player will be notified, otherwise unsupportedoperationexceptione and the plyaer must select other tiles
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
     * Handles a received VCEvent, delegating it to the appropriate handler method using reflection. This method is part of
     * the implementation of the {@code VCEventHandler} interface.
     *
     * @param event The VCEvent to be processed.
     * @param view  The VirtualView that the event is associated with.
     * @throws NoSuchMethodException   If a method of the Controller with a name matching the VCEvent's method name does not exist.
     * @throws IllegalAccessException  If the currently executing method does not have access to the definition of the specified method.
     *
     * @author Valentino Guerrini & Patrick Poggi & Paolo Gennaro
     *
     * <p>This method performs the following steps:</p>
     *
     * <ul>
     * <li>Checks if all players are offline or only one is connected. If so, returns immediately.</li>
     * <li>Retrieves the name of the event method from the received VCEvent object.</li>
     * <li>Assigns the received VirtualView to the 'caller' field.</li>
     * <li>Depending on the event method name, it performs one of several actions:</li>
     *     <ul>
     *     <li>For specific event types ("onLoginEvent", "onSendMessageEvent", "onSelectColumnEvent", "onClickOnTileEvent"),
     *         it retrieves the respective method from the Controller class using reflection, then invokes the method on
     *         the current instance of the controller.</li>
     *     <li>For other event types, it tries to find a matching method in the Controller class and invokes it.
     *         If no such method exists, it throws a NoSuchMethodException.</li>
     *     </ul>
     * <li>In the case of any InvocationTargetException (i.e., if the invoked method throws an exception), it prints the stack trace.</li>
     * </ul>
     *
     * <p>Note that, as this method uses reflection to call other methods, the NoSuchMethodException and IllegalAccessException
     * are possible. It is expected that all relevant event handling methods exist and are accessible in the Controller class.</p>
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
    }

    /**
     * This method is an overridden version of the onVCEvent method that should not be called in this context.
     * If called, it throws an IllegalAccessException.
     *
     * @param event The VCEvent which is not expected in this context.
     * @throws IllegalAccessException Thrown when this method is called.
     */
    @Override
    public void onVCEvent(VCEvent event) throws IllegalAccessException {
        throw new IllegalAccessException("This method should not be called");
    }

    /**
     * Adds a SelectViewEventListener to the list of listeners that are notified about SelectView events.
     *
     * @param listener The SelectViewEventListener to be added.
     */
    public void addSelectViewEventListener(SelectViewEventListener listener){
        selectViewEventListeners.add(listener);
    }

    /**
     * Removes a SelectViewEventListener from the list of listeners that are notified about SelectView events.
     *
     * @param listener The SelectViewEventListener to be removed.
     */
    public void removeSelectViewEventListener(SelectViewEventListener listener){
        selectViewEventListeners.remove(listener);
    }

    /**
     * Handles player disconnection from the game by cleaning up associated data structures and modifying the game state as necessary.
     *
     * @param vv The VirtualView associated with the disconnected player.
     * @return A list of VirtualViews to be removed. If all players are disconnected, it returns null.
     *
     * @author Patrick Poggi
     *
     * <p>This method handles different scenarios based on the current state of the match and the state of the disconnected player:</p>
     *
     * <ul>
     * <li>If the match is not running, all data structures are cleared and the disconnected VirtualView is returned in a list.</li>
     * <li>If the player was logged in and the match is waiting for players, the method disconnects the player,
     *     clears the related data and returns the disconnected VirtualView in a list. If he was the last player in the match,
     *     everything is erased.</li>
     * <li>If the match is running and the player was logged in, the method performs several actions:
     *     <ul>
     *     <li>If all players are disconnected, it sets a flag indicating that all players are offline and returns null.</li>
     *     <li>If the current player before the disconnection is the same as the disconnected player, it sends a new
     *         PickingTilesGameView to the new current player.</li>
     *     <li>Notifies all other players that a player has disconnected and whose turn is it now.</li>
     *     </ul>
     * </li>
     * <li>If the player was not logged in, it simply removes the player as a listener and disconnects the client,
     *     returning the disconnected VirtualView in a list.</li>
     * </ul>
     *
     * <p>Note that in the cases where the match ends due to all players being disconnected or only one player remaining,
     * additional cleanup is done such as clearing data structures, disconnecting the clients and erasing the match.</p>
     */
    public List<VirtualView> playerDisconnected(VirtualView vv){
        List<VirtualView> virtualViewsToRemove = new ArrayList<>();

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
            if(match.areAllPlayersDisconnected() == true){
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
                }, 120000);
            }
            if(previousCurrentPlayer != null && previousCurrentPlayer.getPlayerID() == playerHash) {
                /*If the current player before the disconnection is registered is different from null and is equal to
                the disconnected one then match.disconnectPlayer called above will update the current player
                and we will have to send the new current player a PickingTilesGameView, otherwise we don't
                 */

                if(onlyOneIsConnected){
                    currentPlayerView.onSelectViewEvent(new GameView("Since you are the only player connected," +
                            "the match will be paused"));
                }else{
                    if(previousCurrentPlayer.getNextPlayer().getPlayerID()==match.getFirstPlayer().getPlayerID() && match.getFirstToFinish()!=null){
                        this.match.calculateFinalScores();

                        for(Integer i  : PlayerViews.keySet()) {
                            if (!match.getDisconnectedPlayersVirtualViews().containsKey(PlayerViews.get(i))) {
                                PlayerViews.get(i).onSelectViewEvent(new EndedMatchView());
                            }
                        }
                        match.removeMVEventListener(vv);
                        this.removeSelectViewEventListener(vv);
                        server.disconnectClient(vv);
                        virtualViewsToRemove.add(vv);
                        return virtualViewsToRemove;



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

    /**
     * Handles player reconnection to the game and updates the game state accordingly.
     *
     * @param vv The VirtualView associated with the reconnected player.
     *
     * @author Patrick Poggi
     *
     * <p>This method performs different actions based on the current state of the match and the number of connected players:</p>
     *
     * <ul>
     * <li>If all players were offline (everyoneOffline), it flags that only one player is connected (onlyOneIsConnected),
     *     schedules a timer to erase the match if no other player connects within 2 minutes, and sends a GameView indicating that
     *     the match is paused. The method also reconnects the player even if the match is paused.</li>
     * <li>If there was at least one player already connected, the method reconnects the player and handles the following scenarios:
     *     <ul>
     *     <li>If only one player was connected prior to this one, the timer is cancelled and a GameView is sent to the newly connected
     *         player indicating whose turn it is. A PickingTilesGameView is also sent to the current player.</li>
     *     <li>If there were more than one player connected, a GameView is sent to the newly connected player indicating whose turn is it.</li>
     *     </ul>
     * </li>
     * </ul>
     *
     * <p>After handling the player reconnection, the method triggers an update to all the ModelView listeners.</p>
     */
    public void playerConnected(VirtualView vv) {
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

        }
    }

    /**
     * Retrieves the current mapping of player IDs to their respective VirtualViews.
     *
     * @return A map where the keys are player IDs and the values are their corresponding VirtualViews.
     */
    public Map<Integer, VirtualView> getPlayerViews() {
        return PlayerViews;
    }
}
