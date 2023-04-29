package Server.Controller;

import Server.Events.SelectViewEvents.*;

import Server.Listeners.SelectViewEventListener;

import Server.Listeners.VCEventListener;
import Server.Model.Chat.Message;
import Server.Model.GameItems.LivingRoom;
import Server.Model.GameItems.LivingRoomTileSpot;

import Server.Model.GameItems.TileType;
import Server.Model.Match;
import Server.Events.VCEvents.VCEvent;
import Server.Model.Player.Player;
import Server.Network.Server;
import Server.Network.VirtualView;
import Utils.MathUtils.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.*;

/**
 * Controller class to manage the requests from the client
 * @author Valentino Guerrini & Paolo Gennaro
 */
public class Controller implements VCEventListener {

    private final Match match;
    //private VirtualView[] virtualViews;
    private VirtualView caller;
    private VirtualView currentPlayerView;
    private final Map<Integer,VirtualView > PlayerViews=new HashMap<>();

    private List<SelectViewEventListener> selectViewEventListeners;




//    public Controller(Match match,VirtualView... virtualViews){
//        this.virtualViews = virtualViews;
//        this.match = match;
//        }

    public Controller(Match match){
        this.match = match;
        selectViewEventListeners = new ArrayList<>();
    }

    /**
     * Method to manage the login event, if there is a player with the same nickname, the login is not possible so
     * the method return a LoginView event with the boolean value set to false, otherwise the player is added to the match
     * @param nickname nickname of the player
     * @author ValentinoGuerrini
     */

    private void onLoginEvent(String nickname) throws RemoteException{

        ArrayList<Player> players = match.getPlayers();
        for(Player player : players){
            if(player.getPlayerID()==nickname.hashCode()) {
                currentPlayerView.onSelectViewEvent(new  SelectViewEvent(new LoginView("Nickname already taken")));
            }else if(nickname.length() > 20) {
                currentPlayerView.onSelectViewEvent(new SelectViewEvent(new LoginView("Nickname too long, max 20 characters")));
            }else if(nickname.length() < 3) {
                currentPlayerView.onSelectViewEvent(new SelectViewEvent(new LoginView("Nickname too short, min 3 characters")));
            }else if(nickname.contains(" ")) {
                currentPlayerView.onSelectViewEvent(new SelectViewEvent(new LoginView("Nickname cannot contain spaces")));
            }else{
                match.addContestant(new Player(nickname.hashCode(),nickname));
                PlayerViews.put(nickname.hashCode(),caller);
                currentPlayerView.onSelectViewEvent(new SelectViewEvent(new GameView()));
            }
        }



    }

    /**
     *Method to manage the OpenChat event, it returns a ViewType event with the chat open
     * @author ValentinoGuerrini
     */
    private void onOpenChatEvent() throws RemoteException{
        currentPlayerView.onSelectViewEvent(new SelectViewEvent(new ChatONView()));
    }

    /**
     *Method to manage the CloseChat event, it returns a ViewType event with the chat closed
     * @author ValentinoGuerrini
     */
    private void onCloseChatEvent() throws RemoteException {
        currentPlayerView.onSelectViewEvent( new SelectViewEvent(new ChatOFFView()));
    }

    /**
     * Method to manage the send message event, it adds the message to the chat
     * @param message to be added to the chat
     * @author ValentinoGuerrini
     */
    private void onSendMessageEvent(Message message){
        match.getGameChat().addMessage(message);
    }

    /**
     * Method to manage the select column event, it adds the selectedTiles of the current player
     * to the column selected by him.
     * @param column selected from the player
     * @author Paolo Gennaro
     */
    private void onSelectColumnEvent(int column) throws RemoteException{
        //check if is the player turn
        if(PlayerViews.get(match.getCurrentPlayer().getPlayerID())!=caller){
            caller.onSelectViewEvent(new SelectViewEvent(new GameView("It's not your turn!")));
        }


        Player currentPlayer = match.getCurrentPlayer();
        int numberTakenTiles=0;

        for(int i=0; i<currentPlayer.getTakenTiles().length; i++){
            if(currentPlayer.getTakenTiles()[i] != null){
                numberTakenTiles++;
            }
        }

        //if(numberTakenTiles == 0){
        //    throw new NoTilesSelectedException();
        //    return new SelectViewEvent(new PickingTilesGameView(è));
        //}

        if(currentPlayer.getBookshelf().getLastIndexes().get(column) < numberTakenTiles){

            currentPlayerView.onSelectViewEvent(new SelectViewEvent(new InsertingTilesGameView("No space for this tiles!")));
        }

        try{
            for(int i=0; i<match.getCurrentPlayer().getTakenTiles().length; i++){
                if(match.getCurrentPlayer().getTakenTiles()[i] != null) {
                    match.getCurrentPlayer().getBookshelf().insertTile(column, match.getCurrentPlayer().getTakenTiles()[i]);
                }
            }
            match.getCurrentPlayer().clearTakenTiles();
            match.clearSelectedTiles();
            try{
                match.checkCommonGoals(currentPlayer);
            }catch(UnsupportedOperationException e){
                throw new UnsupportedOperationException();
            }

            int numberOfPlayers = match.getNumberOfPlayers();


            if(match.getFirstToFinish()==null){
                if(match.checkIfBookshelfIsFull(currentPlayer)){
                    try{
                        match.assignMatchEndedTile();
                        if(currentPlayer.getNextPlayer().equals(match.getFirstPlayer())){
                            match.calculateFinalScores();
                            for(int i=0; i<numberOfPlayers; i++){
                                PlayerViews.get(match.getPlayers().get(i).getPlayerID()).onSelectViewEvent(new SelectViewEvent(new EndedMatchVIew()));
                            }

                        }else{
                            currentPlayerView.onSelectViewEvent(new SelectViewEvent(new GameView()));
                            currentPlayerView = PlayerViews.get(currentPlayer.getNextPlayer().getPlayerID());
                            match.setCurrentPlayer();
                            currentPlayerView.onSelectViewEvent(new SelectViewEvent(new PickingTilesGameView("This is your last turn!")));
                        }

                    }catch (UnsupportedOperationException e) {
                        //do nothing
                    }
                }else{
                    currentPlayerView.onSelectViewEvent(new SelectViewEvent(new GameView()));
                    currentPlayerView = PlayerViews.get(currentPlayer.getNextPlayer().getPlayerID());
                    match.setCurrentPlayer();
                    currentPlayerView.onSelectViewEvent(new SelectViewEvent(new PickingTilesGameView()));
                }

            }else{
                if(currentPlayer.getNextPlayer().equals(match.getFirstPlayer())){
                    match.calculateFinalScores();
                    for(int i=0; i<numberOfPlayers; i++){
                        PlayerViews.get(match.getPlayers().get(i).getPlayerID()).onSelectViewEvent(new SelectViewEvent(new EndedMatchVIew()));
                    }

                }else{
                    currentPlayerView.onSelectViewEvent(new SelectViewEvent(new GameView()));
                    currentPlayerView = PlayerViews.get(currentPlayer.getNextPlayer().getPlayerID());
                    match.setCurrentPlayer();
                    currentPlayerView.onSelectViewEvent(new SelectViewEvent(new PickingTilesGameView("This is your last turn!")));
                }
            }

        } catch (UnsupportedOperationException e){

            currentPlayerView.onSelectViewEvent(new SelectViewEvent(new InsertingTilesGameView("This column is already full|")));
        } catch (IndexOutOfBoundsException e){

            currentPlayerView.onSelectViewEvent(new SelectViewEvent(new InsertingTilesGameView( "This column does not exists!")));

        }
    }
    /**
     * Method to manage the selected tile event, the selected tiles are added to the match if there is space for them
     * @param coordinates of the tile selected by the player
     * @author ValentinoGuerrini
     */



    private void onClickOnTileEvent(int[] coordinates) throws RemoteException{


        int[] tmp,selectedTiles;
        boolean flag=false;
        int playernumber = match.getNumberOfPlayers();
        tmp=match.getSelectedTiles();

        if(PlayerViews.get(match.getCurrentPlayer().getPlayerID())!=caller){
            caller.onSelectViewEvent(new SelectViewEvent(new GameView("It's not your turn!")));
        }

        LivingRoomTileSpot[][] livingRoomTileSpots = match.getLivingRoom().getTileMatrix();

        //verifies if the tile in that position is selectable

        if(livingRoomTileSpots[coordinates[0]][coordinates[1]].getDotsNumber()==-1){
            caller.onSelectViewEvent(new SelectViewEvent(new PickingTilesGameView("This tile is not selectable!")));
        }else if(playernumber==2 && livingRoomTileSpots[coordinates[0]][coordinates[1]].getDotsNumber()>=3) {
            caller.onSelectViewEvent(new SelectViewEvent(new PickingTilesGameView("This tile is not selectable!")));
        }else if(playernumber==3 && livingRoomTileSpots[coordinates[0]][coordinates[1]].getDotsNumber()>=4) {
            caller.onSelectViewEvent(new SelectViewEvent(new PickingTilesGameView("This tile is not selectable!")));
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
        }
        match.setSelectedTiles(selectedTiles);
    }

    /**
     * Method to manage the checkout tiles event, it returns a ViewType event depending on the position of the tiles selected
     * If the tiles are not pickable the method returns a PickingTilesGameView event, otherwise it returns a InsertingTilesGameView event
     * @author ValentinoGuerrini
     */
    private void onCheckoutTilesEvent() throws RemoteException{
        Player currentPlayer = match.getCurrentPlayer();
        int[] selectedTiles = match.getSelectedTiles();
        LivingRoom livingRoom = match.getLivingRoom();
        TileType[] tiles;

        switch(selectedTiles.length){
            case 0 ->{
                match.clearSelectedTiles();
                match.setCurrentPlayer();
                currentPlayerView = PlayerViews.get(match.getCurrentPlayer().getPlayerID());
                currentPlayerView.onSelectViewEvent(new SelectViewEvent(new PickingTilesGameView()));
                caller.onSelectViewEvent(new SelectViewEvent(new GameView()));
            }
            case 2 -> {
                try{
                    tiles=new TileType[1];
                    tiles[0]=livingRoom.takeTile(selectedTiles[0],selectedTiles[1]);
                    currentPlayer.setTakenTiles(tiles);
                    match.clearSelectedTiles();
                    currentPlayerView.onSelectViewEvent(new SelectViewEvent(new InsertingTilesGameView()));

                }catch(UnsupportedOperationException e){
                    match.clearSelectedTiles();
                    currentPlayerView.onSelectViewEvent( new SelectViewEvent(new PickingTilesGameView(e.getMessage())));
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
                    currentPlayerView.onSelectViewEvent( new SelectViewEvent(new InsertingTilesGameView()));
                }catch(UnsupportedOperationException e){
                    match.clearSelectedTiles();
                    currentPlayerView.onSelectViewEvent( new SelectViewEvent(new PickingTilesGameView(e.getMessage())));
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
                    currentPlayerView.onSelectViewEvent( new SelectViewEvent(new InsertingTilesGameView()));
                }catch(UnsupportedOperationException e){
                    match.clearSelectedTiles();
                    currentPlayerView.onSelectViewEvent( new SelectViewEvent(new PickingTilesGameView(e.getMessage())));
                }
            }
        }
    }

    /**
     * Method to manage VCEvents, it uses reflection to call the right method depending on the event
     * @param event the event to manage
     * @throws NoSuchMethodException if the method called doesn't exist
     * @throws InvocationTargetException if the method called throws an exception
     * @throws IllegalAccessException if the method called is not accessible
     */
    @Override
    public void onVCEvent(VCEvent event, VirtualView view) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = event.getMethodName();
        caller= view;

        switch(methodName){
            case"onLoginEvent" -> {
                Method method = Controller.class.getDeclaredMethod("onLoginEvent", String.class);
                try {
                    method.invoke(this, (String)event.getValue());
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
    public void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        throw new IllegalAccessException("This method should not be called");
    }

    public void addSelectViewEventListener(SelectViewEventListener listener){
        selectViewEventListeners.add(listener);
    }

    public void removeSelectViewEventListener(SelectViewEventListener listener){
        selectViewEventListeners.remove(listener);
    }

}
