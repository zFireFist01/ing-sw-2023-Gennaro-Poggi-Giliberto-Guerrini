package Server.Controller;

import Server.Events.SelectViewEvents.*;
import Server.Listeners.VCEventListener;
import Server.Model.Chat.Message;
import Server.Model.GameItems.LivingRoom;
import Server.Model.GameItems.TileType;
import Server.Model.Match;
import Server.Events.VCEvents.VCEvent;
import Server.Model.Player.Player;
import Utils.MathUtils.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller class to manage the requests from the client
 * @Author ValentinoGuerrini & PaoloGennaro
 */
public class Controller implements VCEventListener {

    private final Match match;
    private List<VirtualView> virtualViews;


    public Controller(List<VirtualView> virtualViews, Match match){
        this.virtualViews = virtualViews;
        this.match = match;
    }

    /**
     * Method to manage the login event, if there is a player with the same nickname, the login is not possible so
     * the method return a LoginView event with the boolean value set to false, otherwise the player is added to the match
     * @param nickname
     * @return SelectViewEvent event
     */

    private SelectViewEvent onLoginEvent(String nickname){
        ArrayList<Player> players = match.getPlayers();
        for(Player player : players){
            if(player.getPlayerID()==nickname.hashCode()) {
                return new LoginView("Nickname already taken", false);
            }else if(nickname.length() > 20) {
                return new LoginView("Nickname too long, max 20 characters", false);
            }else if(nickname.length() < 3) {
                return new LoginView("Nickname too short, min 3 characters", false);
            }else if(nickname.contains(" ")) {
                return new LoginView("Nickname cannot contain spaces", false);
            }else{
                match.addContestant(new Player(nickname.hashCode(),nickname));
                return new GameView();
            }
        }



    }

    private SelectViewEvent onOpenChatEvent(){
        return new GameView(match.getCurrentPlayer(),true);
    }

    private SelectViewEvent onCloseChatEvent(){
        return new GameView(match.getCurrentPlayer(),false);
    }

    private void onSendMessageEvent(Message message){
        match.getGameChat().addMessage(message);
    }

    private SelectViewEvent onSelectColumnEvent(int column){
        
    }

    private void onClickOnTileEvent(int[] coordinates){
        int[] tmp,selectedTiles;
        tmp=match.getSelectedTiles();
        selectedTiles = new int[tmp.length+2];
        for(int i=0;i<tmp.length;i++){
            selectedTiles[i]=tmp[i];
        }
        selectedTiles[tmp.length]=coordinates[0];
        selectedTiles[tmp.length+1]=coordinates[1];
        match.setSelectedTiles(selectedTiles);
    }

    private SelectViewEvent onCheckoutTilesEvent(){
        Player currentPlayer = match.getCurrentPlayer();
        int[] selectedTiles = match.getSelectedTiles();
        LivingRoom livingRoom = match.getLivingRoom();
        TileType[] tiles;

        switch(selectedTiles.length){
            case 2 -> {
                try{
                    tiles=new TileType[1];
                    tiles[0]=livingRoom.takeTile(selectedTiles[0],selectedTiles[1]);
                    currentPlayer.setTakenTiles(tiles);
                    match.clearSelectedTiles();
                    return new InsertingTilesGameView();

                }catch(UnsupportedOperationException e){
                    match.clearSelectedTiles();
                    return new PickingTilesGameView(match.getCurrentPlayer(),false,e.getMessage());
                }
            }
            case 4 -> {
                try{
                    Couple<Integer,Integer>[] coordinates = new Couple[2];
                    coordinates[0] = new Couple(selectedTiles[0],selectedTiles[1]);
                    coordinates[1] = new Couple(selectedTiles[2],selectedTiles[3]);
                    tiles= livingRoom.takeTiles(coordinates);
                    match.clearSelectedTiles();
                    return new InsertingTilesGameView();
                }catch(UnsupportedOperationException e){
                    match.clearSelectedTiles();
                    return new PickingTilesGameView(match.getCurrentPlayer(),false,e.getMessage());
                }
            }
            case 6 -> {
                try{
                    Couple<Integer,Integer>[] coordinates = new Couple[3];
                    coordinates[0] = new Couple(selectedTiles[0],selectedTiles[1]);
                    coordinates[1] = new Couple(selectedTiles[2],selectedTiles[3]);
                    coordinates[2] = new Couple(selectedTiles[4],selectedTiles[5]);
                    tiles= livingRoom.takeTiles(coordinates);
                    match.clearSelectedTiles();
                    return new InsertingTilesGameView();
                }catch(UnsupportedOperationException e){
                    match.clearSelectedTiles();
                    return new PickingTilesGameView(match.getCurrentPlayer(),false,e.getMessage());
                }
            }
        }
    }

    @Override
    public void onVCEvent(VCEvent event) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = event.getMethodName();

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
