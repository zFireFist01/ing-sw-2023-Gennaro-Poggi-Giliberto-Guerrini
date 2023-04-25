package Server.Model;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.PersonalGoalCard;
import Server.Model.Chat.PlayersChat;
import Server.Model.Decks.CommonGoalCardsDeck;
import Server.Model.Decks.PersonalGoalCardsDeck;
import Server.Model.GameItems.*;
import Server.Model.MatchStatus.MatchStatus;
import Server.Model.MatchStatus.WaitingForPlayers;
import Server.Model.Player.Player;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;



/**
 * match class in order to store information about the match
 * @author martagiliberto
 */
public class Match {
    private ArrayList<Player> players;
    private PlayersChat gameChat;
    private ArrayList<Integer> selectedTiles;
    private int width;
    private int height;
    private final int numberOfPlayers;
    private CommonGoalCard[] commonGoals;
    private MatchStatus matchStatus;
    private final Player matchOpener;
    private Player firstPlayer;
    private Player currentPlayer;
    private Player winner;
    private final LivingRoom livingRoom;
    private final CommonGoalCardsDeck commonGoalDeck;
    private final PersonalGoalCardsDeck personalGoalDeck;
    private Map<Player, Integer> scores;
    private Time matchDuration;

    private int count=0;

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
        this.matchStatus= new WaitingForPlayers();
        this.scores= new HashMap<Player, Integer>() ;
        this.width= matchOpener.getBookshelf().getBookshelfWidth();
        this.height=matchOpener.getBookshelf().getBookshelfHeight();
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
     */
    public void checkCommonGoals(Player player){
        if(commonGoals[0].check(player.getBookshelf())) {
            player.assignPointTile(commonGoals[0].pickPointsTile());
        }

        if(commonGoals[1].check(player.getBookshelf())) {
            player.assignPointTile(commonGoals[1].pickPointsTile());
        }
    }

    /**
     * this method extracts first player
     */
    private void extractFirstPlayer(){
        Random random = new Random();
        int indexOfFirstPlayer = random.nextInt(numberOfPlayers-1);
        this.firstPlayer= players.get(indexOfFirstPlayer);
    }

    /**
     * this method extracts the common goals cards of the mach from the deck
     */
    public void extractCommonGoals(){
        commonGoalDeck.shuffle();
        commonGoals[0]=(CommonGoalCard) commonGoalDeck.drawOne();
        commonGoals[1]=(CommonGoalCard) commonGoalDeck.drawOne();
    }

    /**
     * this method adds a new player to the match
     * @param newPlayer who wants to play
     */
    public void addContestant(Player newPlayer) throws UnsupportedOperationException{
        for(int i=0; i<players.size(); i++) {
            if ((newPlayer.getPlayerID() != players.get(i).getPlayerID()) &&
                    !(newPlayer.getPlayerNickName().equals(players.get(i).getPlayerNickName()))) {
                players.add(newPlayer);
                scores.put(newPlayer, 0);
                try {
                    matchStatus = matchStatus.evolve();
                    setup();
                } catch (UnsupportedOperationException e) {
                    System.err.println(e.getMessage());
                }
            }else{ throw new UnsupportedOperationException("Id or nickname already existing!");
            }
        }
    }

    /**
     * this method checks if a player has ended the match
     * @param player who has just ended his move
     * @return false if bookshelf is empty and true if it is full
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
        player.assignPointTile(PointsTile.MATCH_ENDED);
        return true;
    }


    /**
     * this recursive method is used to count how many tiles with the same tile type are adjacent
     * @param i index of line
     * @param j index of column
     * @param matrix that represents the bookshelf but with int in the place of tile types
     * @param tileType int that represents the tile type, that I want to check now
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
                }else if(hisPointsTiles.get(j).equals(PointsTile.MATCH_ENDED)){
                    hisScores++;
                }
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

    }

    /**
     * this method sets current player
     */
    public void setCurrentPlayer() {
        this.currentPlayer = currentPlayer.getNextPlayer();
    }

    /**
     * this method sets winner
     */
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    //METODI GETTER

    public ArrayList<Player> getPlayers() {
        return new ArrayList<>(players);
    }

    public CommonGoalCard[] getCommonGoals() {

        return commonGoals;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
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

    public Time getMatchDuration() {
        return matchDuration;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

}
