package Server.Controller;

import Server.Events.SelectViewEvents.*;
import Server.Listeners.VCEventListener;
import Server.Model.Chat.Message;
import Server.Model.GameItems.LivingRoom;
import Server.Model.GameItems.LivingRoomTileSpot;
import Server.Model.GameItems.TileType;
import Server.Model.Match;
import Server.Events.VCEvents.VCEvent;
import Server.Model.Player.Player;
import Server.Network.VirtualView;
import Utils.MathUtils.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Controller class to manage the requests from the client
 * @Author ValentinoGuerrini & PaoloGennaro
 */
public class Controller implements VCEventListener {

    private final Match match;
    //private VirtualView[] virtualViews;
    private VirtualView caller;
    private VirtualView currentPlayerView;
    private Map<Integer,VirtualView > PlayerViews=new HashMap<>();






//    public Controller(Match match,VirtualView... virtualViews){
//        this.virtualViews = virtualViews;
//        this.match = match;
//        }

    public Controller(Match match){
        this.match = match;
    }



    /**
     * Method to manage the login event, if there is a player with the same nickname, the login is not possible so
     * the method return a LoginView event with the boolean value set to false, otherwise the player is added to the match
     * @param nickname
     * @return SelectViewEvent event
     * @Author ValentinoGuerrini
     */

    private SelectViewEvent onLoginEvent(String nickname){
        ArrayList<Player> players = match.getPlayers();
        for(Player player : players){
            if(player.getPlayerID()==nickname.hashCode()) {
                return new  SelectViewEvent(new LoginView("Nickname already taken"));
            }else if(nickname.length() > 20) {
                return new SelectViewEvent(new LoginView("Nickname too long, max 20 characters"));
            }else if(nickname.length() < 3) {
                return new SelectViewEvent(new LoginView("Nickname too short, min 3 characters"));
            }else if(nickname.contains(" ")) {
                return new SelectViewEvent(new LoginView("Nickname cannot contain spaces"));
            }else{
                match.addContestant(new Player(nickname.hashCode(),nickname));
                PlayerViews.put(nickname.hashCode(),caller);
                return new SelectViewEvent(new GameView());
            }
        }



    }


    /**
     *Method to manage the OpenChat event, it returns a ViewType event with the chat open
     * @return ViewType event
     * @Author ValentinoGuerrini
     */
    private SelectViewEvent onOpenChatEvent(){
        return new SelectViewEvent(new GameView(true));
    }


    /**
     *Method to manage the CloseChat event, it returns a ViewType event with the chat closed
     * @return ViewType event
     * @Author ValentinoGuerrini
     */
    private SelectViewEvent onCloseChatEvent(){
        return new SelectViewEvent(new GameView(false));
    }

    /**
     * Method to manage the send message event, it adds the message to the chat
     * @param message
     * @Author ValentinoGuerrini
     */
    private void onSendMessageEvent(Message message){
        match.getGameChat().addMessage(message);
    }



    private SelectViewEvent onSelectColumnEvent(int column){
        // TODO implement here
        //il metodo deve lanciare notyourturexception nel caso non sia il turno del giocatore che ha mandato l'evento
        //il metodo deve prima verificare che la colonna non sia piena nel caso sia piena deve restituire la stessa viewtype precedente con un messaggio di errore
        //nel caso non sia piena inserisce le tiles, aggiorna la view e restituisce la viewtype standard
        //ATTENZIONE il metodo deve essere chiamato solo se il giocatore ha selezionato le tiles
        //il metodo deve verificare se la bookshelf e piena dopo l'inserimento e nel caso assegnare la pointile inoltre bisogna creare un sistema per la gestione dell'ultimo giro
        // se necessario aggiungere metodi nella classe match
        //il metodo deve controllare se il giocatore ha completato una commongoal card e nel caso assegnare la point tile
        //deve anche controllare che il giocatore non aveva gia luna pointtile di quella commongoal
        //il metodo si occupa di impostare il prossimo giocatore sia nel controller che nel model e di conseguenza aggiornare le view
    }

    /**
     * Method to manage the selected tile event, the selected tiles are added to the match if there is space for them
     * @param coordinates
     * @author ValentinoGuerrini
     */

    private void onClickOnTileEvent(int[] coordinates) Throws NotYourTurnException, InvalidTileSelectionException {
        int[] tmp,selectedTiles;
        boolean flag=false;
        int playernumber = match.getNumberOfPlayers();
        tmp=match.getSelectedTiles();

        if(PlayerViews.get(match.getCurrentPlayer().getPlayerID())!=caller){
            throw new NotYourTurnException();
        }

        LivingRoomTileSpot[][] livingRoomTileSpots = match.getLivingRoom().getTileMatrix();

        //verifies if the tile in that position is selectable

        if(livingRoomTileSpots[coordinates[0]][coordinates[1]].getDotsNumber()==-1){
            throw new InvalidTileSelectionException();
        }else if(playernumber==2 && livingRoomTileSpots[coordinates[0]][coordinates[1]].getDotsNumber()>=3) {
            throw new InvalidTileSelectionException();;
        }else if(playernumber==3 && livingRoomTileSpots[coordinates[0]][coordinates[1]].getDotsNumber()>=4) {
            throw new InvalidTileSelectionException();;
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
            for(int i=0;i<tmp.length;i++){
                if(tmp[i]!=-1){
                    selectedTiles[j]=tmp[i];
                    j++;
                }
            }
        }else if(tmp.length <6){
            selectedTiles=new int[tmp.length+2];
            for(int i=0;i<tmp.length;i++){
                selectedTiles[i]=tmp[i];
            }
            selectedTiles[tmp.length]=coordinates[0];
            selectedTiles[tmp.length+1]=coordinates[1];
        }else{
            selectedTiles=new int[tmp.length];
            for(int i=0;i<tmp.length;i++){
                selectedTiles[i]=tmp[i];
            }
        }
        match.setSelectedTiles(selectedTiles);
    }


    /**
     * Method to manage the checkout tiles event, it returns a ViewType event depending on the position of the tiles selected
     * If the tiles are not pickable the method returns a PickingTilesGameView event, otherwise it returns a InsertingTilesGameView event
     * @return ViewType event
     * @Author ValentinoGuerrini
     */
    private SelectViewEvent onCheckoutTilesEvent(){
        Player currentPlayer = match.getCurrentPlayer();
        int[] selectedTiles = match.getSelectedTiles();
        LivingRoom livingRoom = match.getLivingRoom();
        TileType[] tiles;

        switch(selectedTiles.length){
            case 0 ->{
                match.clearSelectedTiles();
                match.setCurrentPlayer();
                currentPlayerView = PlayerViews.get(match.getCurrentPlayer().getPlayerID());
                currentPlayerView.updateView(new SelectViewEvent(new PickingTilesGameView(caller.chatIsOn())));
                return new SelectViewEvent(new GameView(caller.chatIsOn()));
            }
            case 2 -> {
                try{
                    tiles=new TileType[1];
                    tiles[0]=livingRoom.takeTile(selectedTiles[0],selectedTiles[1]);
                    currentPlayer.setTakenTiles(tiles);
                    match.clearSelectedTiles();
                    return new SelectViewEvent(new InsertingTilesGameView(caller.chatIsOn()));

                }catch(UnsupportedOperationException e){
                    match.clearSelectedTiles();
                    return new SelectViewEvent(new PickingTilesGameView(caller.chatIsOn(),e.getMessage()));
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
                    return new SelectViewEvent(new InsertingTilesGameView(caller.chatIsOn()));
                }catch(UnsupportedOperationException e){
                    match.clearSelectedTiles();
                    return new SelectViewEvent(new PickingTilesGameView(caller.chatIsOn(),e.getMessage()));
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
                    return new SelectViewEvent(new InsertingTilesGameView(caller.chatIsOn()));
                }catch(UnsupportedOperationException e){
                    match.clearSelectedTiles();
                    return new SelectViewEvent(new PickingTilesGameView(caller.chatIsOn(),e.getMessage()));
                }
            }
        }
    }

    /**
     * Method to manage VCEvents, it uses reflection to call the right method depending on the event
     * @param event
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */

    @Override
    public void onVCEvent(VCEvent event, VirtualView view) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = event.getMethodName();
        caller= view;

        switch(methodName){
            case"onLoginEvent" -> {
                Method method = Controller.class.getDeclaredMethod("onLoginEvent", String.class);
                method.invoke(this, event.getValue());
            }
            case "onSendMessageEvent" -> {
                Method method = Controller.class.getDeclaredMethod(methodName, Message.class);
                method.invoke(this, event.getValue());
            }
            case "onSelectColumnEvent" -> {
                Method method = Controller.class.getDeclaredMethod(methodName, int.class);
                method.invoke(this, event.getValue());
            }
            case "onClickOnTileEvent" -> {
                Method method = Controller.class.getDeclaredMethod(methodName, int[].class);
                method.invoke(this, event.getValue());
            }
            default -> {
                Method method = Controller.class.getDeclaredMethod(methodName);
                method.invoke(this);
            }
        }



    }





}
