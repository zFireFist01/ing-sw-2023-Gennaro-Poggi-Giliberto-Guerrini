package Server.Model;

import Server.Events.MVEvents.*;
import Server.Listeners.MVEventListener;
import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.PersonalGoalCard;
import Server.Model.Chat.PlayersChat;
import Server.Model.Decks.CommonGoalCardsDeck;
import Server.Model.Decks.PersonalGoalCardsDeck;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.LivingRoom;
import Server.Model.GameItems.PointsTile;
import Server.Model.GameItems.TileType;
import Server.Model.MatchStatus.*;
import Server.Model.Player.Player;
import Server.Network.VirtualView;

import java.sql.Time;
import java.util.*;


/**
 * This class represents the match
 * @author Marta Giliberto
 */

public class Match {
    private ArrayList<Player> players;
    private ArrayList<Player> disconnectedPlayers = new ArrayList<>();
    private PlayersChat gameChat;
    private ArrayList<Integer> selectedTiles;
    private int width;
    private int height;
    private int numberOfPlayers;
    private CommonGoalCard[] commonGoals;
    private MatchStatus matchStatus;
    private Player matchOpener;
    private Player firstPlayer;
    private Player currentPlayer;
    private Player winner;
    private LivingRoom livingRoom;
    private CommonGoalCardsDeck commonGoalDeck;
    private PersonalGoalCardsDeck personalGoalDeck;
    private Map<Player, Integer> scores;
    private Player firstToFinish;
    private int count = 0;
    private boolean allPlayersDisconnected = false;
    private List<MVEventListener> mvEventListeners = new ArrayList<>();
    private Map<MVEventListener, Player> disconnectedPlayersVirtualViews = new HashMap<>();

    public Match() {
        this.matchStatus = new NotRunning(this);
        this.gameChat = null;
        this.matchOpener = null;
        this.livingRoom = null;
        this.commonGoalDeck = null;
        this.personalGoalDeck = null;
        this.players = new ArrayList<>();
        this.commonGoals = new CommonGoalCard[2];
        this.scores = new HashMap<>();
        this.firstToFinish = null;
        this.mvEventListeners = new ArrayList<>();
    }

    public Match(int numberOfPlayers, Player matchOpener) {
        this.gameChat = new PlayersChat();
        this.numberOfPlayers = numberOfPlayers;
        this.matchOpener = matchOpener;
        this.livingRoom = new LivingRoom(this);
        this.commonGoalDeck = new CommonGoalCardsDeck(numberOfPlayers);
        this.personalGoalDeck = new PersonalGoalCardsDeck(numberOfPlayers);
        this.players = new ArrayList<Player>();
        this.players.add(matchOpener);
        this.commonGoals = new CommonGoalCard[2];
        this.scores = new HashMap<Player, Integer>();
        this.width = matchOpener.getBookshelf().getBookshelfWidth();
        this.height = matchOpener.getBookshelf().getBookshelfHeight();
        this.firstToFinish = null;
        this.mvEventListeners = new ArrayList<>();
    }


    /**
     * This method initializes the match
     */
    public void setup() {
        for (int i = 0; i < numberOfPlayers; i++) {
            if (i < numberOfPlayers - 1) {
                players.get(i).setNextPlayer(players.get(i + 1));
                players.get(i).assignPersonalGoalCard((PersonalGoalCard) personalGoalDeck.drawOne());
            } else {
                players.get(i).setNextPlayer(players.get(0));
                players.get(i).assignPersonalGoalCard((PersonalGoalCard) personalGoalDeck.drawOne());
            }
        }
        livingRoom.refreshLivingRoom();
        extractCommonGoals();
        extractFirstPlayer();
        this.currentPlayer = firstPlayer;
    }

    /**
     * This method is called by the Controller after a player has inserted some tiles in his bookshelf;
     * it checks if a player has completed a common goal
     * @param player who has just inserted some tiles in his bookshelf
     */
    public void checkCommonGoals(Player player) {
        if (commonGoals[0].check(player.getBookshelf())) {
            try {
                player.assignPointTile(commonGoals[0].getPointsTile());
                commonGoals[0].removePointsTile();
                notifyMVEventListeners(new ModifiedPointsEvent(new LightMatch(this)));
            } catch (UnsupportedOperationException e) {
                //do nothing
            }
        }

        if (commonGoals[1].check(player.getBookshelf())) {
            try {
                player.assignPointTile(commonGoals[1].getPointsTile());
                commonGoals[1].removePointsTile();
                notifyMVEventListeners(new ModifiedPointsEvent(new LightMatch(this)));
            } catch (UnsupportedOperationException e) {
                //do nothing
            }
        }
    }

    /**
     * This method extracts first player.
     */
    private void extractFirstPlayer() {
        Random random = new Random();

        int indexOfFirstPlayer = random.nextInt(numberOfPlayers);
        this.firstPlayer = players.get(indexOfFirstPlayer);
    }

    /**
     * This method extracts the common goals cards of the mach from the deck
     */
    public void extractCommonGoals() {
        commonGoalDeck.shuffle();
        commonGoals[0] = (CommonGoalCard) commonGoalDeck.drawOne();
        commonGoals[1] = (CommonGoalCard) commonGoalDeck.drawOne();
    }

    /**
     * This method adds a new player to the match
     * @param newPlayer who wants to play
     */
    public void addContestant(Player newPlayer) throws UnsupportedOperationException {
        if (players.size() == 0) {
            players.add(newPlayer);
            scores.put(newPlayer, 0);
            try {
                matchStatus = matchStatus.evolve();
                this.commonGoalDeck = new CommonGoalCardsDeck(numberOfPlayers);
                this.personalGoalDeck = new PersonalGoalCardsDeck(numberOfPlayers);
                this.gameChat = new PlayersChat();
                this.matchOpener = newPlayer;
                this.livingRoom = new LivingRoom(this);
                this.width = matchOpener.getBookshelf().getBookshelfWidth();
                this.height = matchOpener.getBookshelf().getBookshelfHeight();
                return;
            } catch (UnsupportedOperationException e) {
                System.err.println(e.getMessage());
            }
        }
        boolean contains = false;
        for (Player p : this.players) {
            if (p.getPlayerNickName().equals(newPlayer.getPlayerNickName())||p.getPlayerID()==newPlayer.getPlayerID()){
                contains = true;
            }
        }
        //if contains == True the id or the nickname of the newPlayer are the same of another player already playing in this match
        if (!contains) {
            players.add(newPlayer);
            scores.put(newPlayer, 0);
            try {
                matchStatus = matchStatus.evolve();
                notifyMVEventListeners(new MatchStartedEvent(new LightMatch(this)));
            } catch (UnsupportedOperationException e) {
                System.err.println(e.getMessage());
            }
        } else {
            throw new UnsupportedOperationException("Id or nickname already existing!");
        }
    }

    /**
     * This method is used to remove a player from a match only if the match is in Waiting For Players status.
     * @param p who wants to leave the match
     */
    public void removeContestant(Player p){
        //We have to remove this player from all the data structures
        if(matchStatus instanceof WaitingForPlayers){
            players.remove(p);
            scores.remove(p);
            matchStatus = matchStatus.devolve();
        }
    }

    /**
     * This method checks if a player has the bookshelf full and, in case is the player
     * is the first one that has fulfilled the bookshelf, the method will set the firstToFinish
     * as the player
     * @param player who has just ended his move
     * @return false if bookshelf is empty or true if it is full
     */
    public boolean checkIfBookshelfIsFull(Player player) {
        BookshelfTileSpot[][] bookshelf;
        bookshelf = player.getBookshelf().getTileMatrix();

        for (int i = 0; i < width; i++) {
            if (bookshelf[0][i].isEmpty())
                return false;
        }

        if (firstToFinish == null) {
            firstToFinish = player;
            notifyMVEventListeners(new ModifiedPointsEvent(new LightMatch(this)));
        }

        return true;
    }

    /**
     * This recursive method is used to count how many tiles with the same tile type are adjacent
     * @param i        index of line
     * @param j        index of column
     * @param matrix   that represents the bookshelf but with int in the place of tile types
     * @param tileType int that represents the tile type, that I want to check now
     */
    private void howManyAdjacentTiles(int i, int j, int[][] matrix, int tileType) {
        if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[0].length || matrix[i][j] != tileType) {
            return;
        }
        this.count++;
        matrix[i][j] = 0;

        howManyAdjacentTiles(i, j + 1, matrix, tileType); // Right
        howManyAdjacentTiles(i + 1, j, matrix, tileType); // Down
        howManyAdjacentTiles(i - 1, j, matrix, tileType); // Up
        howManyAdjacentTiles(i, j - 1, matrix, tileType); // Left
    }

    /**
     * This method returns the points made by a player for all the adjacent tiles present in his bookshelf
     * @param player whose bookshelf I want to check
     * @return points of adjacent tiles, made by a player
     */
    public Integer checkAdjacentTiles(Player player) {
        BookshelfTileSpot[][] bookshelf = player.getBookshelf().getTileMatrix();
        int[][] checked = new int[height][width];
        boolean ok;
        Integer scores = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (bookshelf[i][j].getTileType() == TileType.PLANTS) {
                    checked[i][j] = 1;
                } else if (bookshelf[i][j].getTileType() == TileType.FRAMES) {
                    checked[i][j] = 2;
                } else if (bookshelf[i][j].getTileType() == TileType.BOOKS) {
                    checked[i][j] = 3;
                } else if (bookshelf[i][j].getTileType() == TileType.CATS) {
                    checked[i][j] = 4;
                } else if (bookshelf[i][j].getTileType() == TileType.GAMES) {
                    checked[i][j] = 5;
                } else if (bookshelf[i][j].getTileType() == TileType.TROPHIES) {
                    checked[i][j] = 6;
                } else {
                    checked[i][j] = 0;
                }
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                count = 0;
                if (checked[i][j] != 0) {
                    this.count = 0;
                    howManyAdjacentTiles(i, j, checked, checked[i][j]);

                    if (count == 3) {
                        scores += 2;
                    } else if (count == 4) {
                        scores += 3;
                    } else if (count == 5) {
                        scores += 5;
                    } else if (count >= 6) {
                        scores += 8;
                    }
                }
            }
        }

        return scores;
    }

    /**
     * This method calculate the final scores of all the players of the match, and it sets the winner
     */
    public void calculateFinalScores() {
        Integer maxScores = 0;
        Integer hisScores;
        ArrayList<PointsTile> hisPointsTiles;
        Player tmp;
        for (int i = 0; i < numberOfPlayers; i++) {
            tmp = players.get(i);
            hisScores = scores.get(tmp);

            hisPointsTiles = tmp.getPointsTiles();
            for (PointsTile p : hisPointsTiles) {
                if ((p.equals(PointsTile.TWO_1)) ||
                        (p.equals(PointsTile.TWO_2))) {
                    hisScores += 2;
                } else if ((p.equals(PointsTile.FOUR_1)) ||
                        (p.equals(PointsTile.FOUR_2))) {
                    hisScores += 4;
                } else if ((p.equals(PointsTile.SIX_1)) ||
                        (p.equals(PointsTile.SIX_2))) {
                    hisScores += 6;
                } else if ((p.equals(PointsTile.EIGHT_1)) ||
                        (p.equals(PointsTile.EIGHT_2))) {
                    hisScores += 8;
                }
            }
            //adding the point of the Match Ended Tile
            if (tmp.getPlayerID() == firstToFinish.getPlayerID()) {
                hisScores++;
            }

            //adding personalGoalCard points
            hisScores += tmp.getPersonalGoalCard().check(tmp.getBookshelf().getTileMatrix());

            //adding points for adjacent tiles of the same type
            hisScores += checkAdjacentTiles(tmp);

            scores.put(tmp, hisScores);

        }
        tmp = firstPlayer;
        for (int i = 0; i < numberOfPlayers; tmp = tmp.getNextPlayer(), i++) {
            if (scores.get(tmp) >= maxScores) {
                maxScores = scores.get(tmp);
                setWinner(tmp);
            }
        }
        if(this.matchStatus == null){
            this.matchStatus = new Closing(this);
        }else{
            this.matchStatus = this.matchStatus.evolve();
        }
        notifyMVEventListeners(new ModifiedMatchEndedEvent(new LightMatch(this)));
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    public void setCurrentPlayer() {
        //set the current player considering the disconnected player
        Player localFirst = currentPlayer;
        currentPlayer = currentPlayer.getNextPlayer();
        while (disconnectedPlayers.contains(currentPlayer)) {
            currentPlayer = currentPlayer.getNextPlayer();
            if (currentPlayer == localFirst) {
                break;
            }
        }
        //Notify the controller through the ModifiedTurnEvent
        notifyMVEventListeners(new ModifiedTurnEvent(new LightMatch(this)));
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getWinner() {
        return winner;
    }

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public CommonGoalCard[] getCommonGoals() {
        return commonGoals;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public Player getFirstToFinish() {
        return firstToFinish;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public LivingRoom getLivingRoom() {
        return livingRoom;
    }

    public Map<Player, Integer> getScores() {
        return scores;
    }

    public Map<Integer, Integer> getIDScores() {
        Map<Integer, Integer> idScores = new HashMap<>();
        for (Player p : scores.keySet()) {
            idScores.put(p.getPlayerID(), scores.get(p));
        }
        return idScores;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * This method assign the Match Ended Tile to the first player who filled the bookshelf
     * and set the first to finish attribute
     */
    public void assignMatchEndedTile() {
        if (this.firstToFinish == null) {
            if (checkIfBookshelfIsFull(this.currentPlayer)) {
                this.firstToFinish = this.currentPlayer;
            } else {
                throw new UnsupportedOperationException("Bookshelf not full!");
            }
        } else {
            throw new UnsupportedOperationException("Someone finished before!");
        }
    }

    public void addMVEventListener(MVEventListener listener) {
        this.mvEventListeners.add(listener);
    }

    public void removeMVEventListener(MVEventListener listener) {
        this.mvEventListeners.remove(listener);
    }

    /**
     * This method notify all the listeners of the match of an MVEvent
     * @param event the event that will be sent
     */
    public void notifyMVEventListeners(MVEvent event) {
        for (MVEventListener listener : this.mvEventListeners) {
            if (!disconnectedPlayersVirtualViews.containsKey(listener)) {
                listener.onMVEvent(event);
            }
        }
    }

    public List<Player> getDisconnectedPlayers() {
        return new ArrayList<>(disconnectedPlayers);
    }

    public Map<MVEventListener, Player> getDisconnectedPlayersVirtualViews() {
        return new HashMap<>(disconnectedPlayersVirtualViews);
    }

    /**
     * This method updates the model to keep track of the disconnection of a client. If the disconnected player was the
     * current player it sets the new one. If now all players are disconnected it sets the boolean attribute
     * allPlayersDisconnected to true
     * @param player player who is disconnecting
     * @param virtualView virtualView of the player who is disconnecting
     * @author Patrick Poggi
     */
    public void disconnectPlayer(Player player, VirtualView virtualView) {
        if(matchStatus instanceof NotRunning){
            mvEventListeners.clear();
            //We clear and set to null all the data structures
            return;
        }else if (matchStatus instanceof WaitingForPlayers) {
            removeContestant(player);
            if(matchStatus==null){
                //The match is now empty, we can remove it
                //We clear and set to null all the data structures (?) I don't think this is actually
                //needed thanks to what the controller and the server do in this case.
            }
            return;
        }
        //If I reach this point, the match is running
        if (!disconnectedPlayers.contains(player)) {
            //disconnectedPlayersVirtualViews.put(virtualView, player);
            disconnectedPlayers.add(player);
            disconnectedPlayersVirtualViews.put(virtualView, player);
        }
        if(disconnectedPlayers.size() == numberOfPlayers){
            //All the players are disconnected
            allPlayersDisconnected = true;
            return;
        }
        if(player.equals(currentPlayer)){
            //Only if the current player is the disconnected one, otherwise we will
            //clear the selected tiles of a player who is still connected, that is wrong
            setCurrentPlayer();
            clearSelectedTiles();
        }
        System.out.println("DISCONNECTED PLAYERS: " + disconnectedPlayers);

    }

    /**
     * This method updates the model to keep track of the reconnection of a client.
     * @param player player who is reconnecting
     * @param virtualView virtualView of the player who is reconnecting
     * @author Patrick Poggi
     */
    public void reconnectPlayer(Player player, VirtualView virtualView) {
        this.disconnectedPlayers.remove(player);
        this.disconnectedPlayersVirtualViews.remove(virtualView);
        if(allPlayersDisconnected){
            //All te players were disconnected
            allPlayersDisconnected = false;
            setCurrentPlayer();
        }
    }

    /**
     * This method is called by playerConnected method in Controller when a player is reconnecting and sends a
     * MatchStartedEvent
     */
    public void triggerMVUpdate(){
        notifyMVEventListeners(new MatchStartedEvent(new LightMatch(this)));
    }

    public boolean areAllPlayersDisconnected(){
        return allPlayersDisconnected;
    }

    public PlayersChat getGameChat() {
        return gameChat;
    }

    public void setSelectedTiles(int[] selectedTiles) {
        //copy the int array into the arraylist
        this.selectedTiles = new ArrayList<Integer>();
        for (int i = 0; i < selectedTiles.length; i++) {
            this.selectedTiles.add(selectedTiles[i]);
        }
    }

    public void clearSelectedTiles() {
        this.selectedTiles = null;
    }

    public int[] getSelectedTiles() {
        if (this.selectedTiles == null) return null;
        int[] selectedTiles = new int[this.selectedTiles.size()];
        for (int i = 0; i < this.selectedTiles.size(); i++) {
            selectedTiles[i] = this.selectedTiles.get(i);
        }
        return selectedTiles;
    }
}
