package Server.Model;

import Server.Events.MVEvents.MVEvent;
import Server.Events.MVEvents.MatchStartedEvent;
import Server.Events.MVEvents.ModifiedMatchEndedEvent;
import Server.Events.MVEvents.ModifiedPointsEvent;
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
import Server.Model.MatchStatus.MatchStatus;
import Server.Model.MatchStatus.NotRunning;
import Server.Model.Player.Player;

import java.sql.Time;
import java.util.*;


/**
 * match class in order to store information about the match
 * @author Marta Giliberto
 */
public class Match {
    private ArrayList<Player> players;
    private PlayersChat gameChat;
    private ArrayList<Integer> selectedTiles;
    private int width;
    private int height;
    private int numberOfPlayers;
    private final CommonGoalCard[] commonGoals;
    private MatchStatus matchStatus;
    private Player matchOpener;
    private Player firstPlayer;
    private Player currentPlayer;
    private Player winner;
    private LivingRoom livingRoom;
    private  CommonGoalCardsDeck commonGoalDeck;
    private  PersonalGoalCardsDeck personalGoalDeck;
    private Map<Player, Integer> scores;
    private Time matchDuration;
    private Player firstToFinish;
    private int count=0;

    private List<MVEventListener> mvEventListeners=new ArrayList<>();

    public Match(){
        this.matchStatus= new NotRunning(this);
        this.gameChat = null;

        this.matchOpener = null;
        this.livingRoom = null;
        this.commonGoalDeck = null;
        this.personalGoalDeck = null;
        this.players= new ArrayList<>();
        this.commonGoals=new CommonGoalCard[2];
        this.scores= new HashMap<>() ;
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
        this.players= new ArrayList<Player>();
        this.players.add(matchOpener);
        this.commonGoals=new CommonGoalCard[2];
        this.scores= new HashMap<Player, Integer>() ;
        this.width= matchOpener.getBookshelf().getBookshelfWidth();
        this.height=matchOpener.getBookshelf().getBookshelfHeight();
        this.firstToFinish = null;
        this.mvEventListeners = new ArrayList<>();
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
        int [] selectedTiles = new int[this.selectedTiles.size()];
        for (int i = 0; i < this.selectedTiles.size(); i++) {
            selectedTiles[i] = this.selectedTiles.get(i);
        }
        return selectedTiles;
    }

    /**
     * this method initializes the match
     * @author Marta Giliberto
     */
    public void setup(){

        for(int i=0; i<numberOfPlayers; i++){
            if(i<numberOfPlayers-1){
            players.get(i).setNextPlayer(players.get(i+1));
            players.get(i).assignPersonalGoalCard((PersonalGoalCard) personalGoalDeck.drawOne());
            }else{
                players.get(i).setNextPlayer(players.get(0));
                players.get(i).assignPersonalGoalCard((PersonalGoalCard) personalGoalDeck.drawOne());
            }
        }
        livingRoom.refreshLivingRoom();
        extractCommonGoals();
        extractFirstPlayer();
        this.currentPlayer= firstPlayer;
    }

    /**
     * this method checks if a player has completed a common goal
     * @author Marta Giliberto
     */
    public void checkCommonGoals(Player player){
        if(commonGoals[0].check(player.getBookshelf())) {
            player.assignPointTile(commonGoals[0].pickPointsTile());
            notifyMVEventListeners(new ModifiedPointsEvent(new LightMatch(this)));
        }

        if(commonGoals[1].check(player.getBookshelf())) {
            player.assignPointTile(commonGoals[1].pickPointsTile());
            notifyMVEventListeners(new ModifiedPointsEvent(new LightMatch(this)));
        }
    }

    /**
     * this method extracts first player
     * @author Marta Giliberto
     */
    private void extractFirstPlayer(){
        Random random = new Random();
        int indexOfFirstPlayer = random.nextInt(numberOfPlayers-1);
        this.firstPlayer= players.get(indexOfFirstPlayer);
    }

    /**
     * this method extracts the common goals cards of the mach from the deck
     * @author Marta Giliberto
     */
    public void extractCommonGoals(){
        commonGoalDeck.shuffle();
        commonGoals[0]=(CommonGoalCard) commonGoalDeck.drawOne();
        commonGoals[1]=(CommonGoalCard) commonGoalDeck.drawOne();
    }

    /**
     * this method adds a new player to the match
     * @param newPlayer who wants to play
     * @author Marta Giliberto
     */
    public void addContestant(Player newPlayer) throws UnsupportedOperationException{
        if(players.size()==0){
            players.add(newPlayer);
            scores.put(newPlayer, 0);
            try {
                matchStatus = matchStatus.evolve();
                this.commonGoalDeck = new CommonGoalCardsDeck(numberOfPlayers);
                this.personalGoalDeck = new PersonalGoalCardsDeck(numberOfPlayers);
                this.gameChat = new PlayersChat();
                this.matchOpener = newPlayer;
                this.livingRoom = new LivingRoom(this);
                this.width= matchOpener.getBookshelf().getBookshelfWidth();
                this.height=matchOpener.getBookshelf().getBookshelfHeight();
                //this.mvEventListeners = new ArrayList<>();

                //setup();
                //notifyMVEventListeners(new MatchStartedEvent(new LightMatch(this)));
                return;
            } catch (UnsupportedOperationException e) {
                System.err.println(e.getMessage());
            }
        }
        /*
        for(int i=0; i<players.size(); i++) {
            if ((newPlayer.getPlayerID() != players.get(i).getPlayerID()) &&
                    !(newPlayer.getPlayerNickName().equals(players.get(i).getPlayerNickName()))) {
                players.add(newPlayer);
                scores.put(newPlayer, 0);
                try {
                    matchStatus = matchStatus.evolve();
                    setup();
                    notifyMVEventListeners(new MatchStartedEvent(new LightMatch(this)));
                } catch (UnsupportedOperationException e) {
                    System.err.println(e.getMessage());
                }
            }else{ throw new UnsupportedOperationException("Id or nickname already existing!");
            }
        }*/
        boolean contains = false;
        for(Player p: this.players){
            if(p.equals(newPlayer)){
                contains = true;
            }
        }
        if(!contains){
            players.add(newPlayer);
            scores.put(newPlayer, 0);
            try {
                matchStatus = matchStatus.evolve();
                setup();
                notifyMVEventListeners(new MatchStartedEvent(new LightMatch(this)));
            } catch (UnsupportedOperationException e) {
                System.err.println(e.getMessage());
            }
        }else{
            throw new UnsupportedOperationException("Id or nickname already existing!");
        }
    }

    /**
     * this method checks if a player has ended the match
     * @param player who has just ended his move
     * @return false if bookshelf is empty or true if it is full
     * @author Marta Giliberto
     */
    public boolean checkIfBookshelfIsFull(Player player){
        BookshelfTileSpot[][] bookshelf;
        bookshelf=player.getBookshelf().getTileMatrix();

        for(int i=0; i<width; i++){
            for(int j=0; j<height; j++){
                if(bookshelf[i][j].isEmpty())
                    return false;
            }
        }
        if(firstToFinish==null) {
            firstToFinish = player;
            //player.assignPointTile(PointsTile.MATCH_ENDED);
            notifyMVEventListeners(new ModifiedPointsEvent(new LightMatch(this)));
        }

        return true;
    }

    /**
     * this recursive method is used to count how many tiles with the same tile type are adjacent
     * @param i index of line
     * @param j index of column
     * @param matrix that represents the bookshelf but with int in the place of tile types
     * @param tileType int that represents the tile type, that I want to check now
     * @author Marta Giliberto
     */
    private void howManyAdjacentTiles(int i, int j, int[][] matrix, int tileType ){
        this.count++;

        if(tileType==matrix[i][j+1]){
            matrix[i][j]=0;
            howManyAdjacentTiles(i, j+1, matrix, tileType);
        }

        if(tileType==matrix[i+1][j]){
            matrix[i][j]=0;
            howManyAdjacentTiles(i+1, j, matrix, tileType);
        }

        if(tileType==matrix[i-1][j]){
            matrix[i][j]=0;
            howManyAdjacentTiles(i-1, j, matrix, tileType);
        }

        if(tileType==matrix[i][j-1]){
            matrix[i][j]=0;
            howManyAdjacentTiles(i,j-1, matrix, tileType);
        }

        matrix[i][j]=0;

    }

    /**
     * this method returns the points made by a player for adjacent tiles
     * @param player whose bookshelf I want to check
     * @return points of adjacent tiles, made by a player
     * @author Marta Giliberto
     */
    public Integer checkAdjacentTiles(Player player) {
        BookshelfTileSpot[][] bookshelf = player.getBookshelf().getTileMatrix();
        int[][] checked = new int[height][width];
        boolean ok;
        Integer scores=0;

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
                }else{
                    checked[i][j]=0;
                }
            }
        }

        for(int i=0; i<height;i++){
            for(int j=0; j<width; j++){
                count=0;
                if(checked[i][j]!=0){
                    this.count=0;
                    howManyAdjacentTiles(i, j, checked, checked[i][j]);

                    if(count==3){
                        scores+=2;
                    }else if(count==4){
                        scores+=3;
                    }else if(count==5){
                        scores+=5;
                    }else if(count>=6){
                        scores+=8;
                    }
                }
            }
        }

        return scores;
    }

    /**
     * this method calculates final scores of all players of the match, and it sets the winner
     * @author Marta Giliberto
     */
    public void calculateFinalScores(){

        Integer maxScores=0;
        Integer hisScores;
        ArrayList<PointsTile> hisPointsTiles;
        Player tmp;
        for(int i=0; i<numberOfPlayers; i++) {
            tmp = players.get(i);
            hisScores = scores.get(tmp);

            //controlla tessere punteggio common goal + fine partita
            hisPointsTiles=tmp.getPointsTiles();
            for(int j=0; hisPointsTiles.get(j)!=null; j++){
                if((hisPointsTiles.get(j).equals(PointsTile.TWO_1)) ||
                        (hisPointsTiles.get(j).equals(PointsTile.TWO_2))){
                    hisScores += 2;
                }else if((hisPointsTiles.get(j).equals(PointsTile.FOUR_1)) ||
                        (hisPointsTiles.get(j).equals(PointsTile.FOUR_2))) {
                    hisScores += 4;
                }else if((hisPointsTiles.get(j).equals(PointsTile.SIX_1)) ||
                        (hisPointsTiles.get(j).equals(PointsTile.SIX_2))){
                    hisScores += 6;
                }else if((hisPointsTiles.get(j).equals(PointsTile.EIGHT_1)) ||
                        (hisPointsTiles.get(j).equals(PointsTile.EIGHT_2))){
                    hisScores += 8;
                }//else if(hisPointsTiles.get(j).equals(PointsTile.MATCH_ENDED)){
                //    hisScores++;
                //}
            }
            if(tmp.getPlayerID()==firstToFinish.getPlayerID()){
                hisScores++;
            }


            //aggiungo i punti di personal goal card
            hisScores+=tmp.getPersonalGoalCard().check(tmp.getBookshelf().getTileMatrix());

            //aggiungo i punti per le tessere adiacenti
            hisScores+=checkAdjacentTiles(tmp);

            scores.put(tmp, hisScores);

        }
        tmp=firstPlayer;
        for(int i=0; i<numberOfPlayers; tmp=tmp.getNextPlayer(), i++){
            if(scores.get(tmp)>=maxScores) {
                maxScores = scores.get(tmp);
                setWinner(tmp);
            }
        }
        matchStatus.evolve();
        notifyMVEventListeners(new ModifiedMatchEndedEvent(new LightMatch(this)));
    }

    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * this method sets current player
     * @author Marta Giliberto
     */
    public void setCurrentPlayer() {
        this.currentPlayer = currentPlayer.getNextPlayer();
    }

    /**
     * this method sets winner
     * @author Marta Giliberto
     */
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    //METODI GETTER

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

    public Map<Integer,Integer>getIDScores(){
        Map<Integer,Integer> idScores=new HashMap<>();
        for(Player p: scores.keySet()){
            idScores.put(p.getPlayerID(),scores.get(p));
        }
        return idScores;
    }
    public Time getMatchDuration() {
        return matchDuration;
    }
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * this method assign the Match Ended Tile to the first player who filled the bookshelf and set the first to finish attribute
     * @author Paolo Gennaro
     */
    public void assignMatchEndedTile(){
        if(this.firstToFinish == null) {
            if (checkIfBookshelfIsFull(this.currentPlayer)) {
                this.firstToFinish = this.currentPlayer;
            } else{
                throw new UnsupportedOperationException("Bookshelf not full!");
            }
        }else{
            throw new UnsupportedOperationException("Someone finished before!");
        }
    }

    public void addMVEventListener(MVEventListener listener){
        this.mvEventListeners.add(listener);
    }

    public void removeMVEventListener(MVEventListener listener){
        this.mvEventListeners.remove(listener);
    }

    public void notifyMVEventListeners(MVEvent event){
        for(MVEventListener listener : this.mvEventListeners){
            listener.onMVEvent(event);
        }
    }
}
