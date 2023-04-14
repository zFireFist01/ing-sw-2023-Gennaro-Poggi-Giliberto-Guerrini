package Server.Model;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.PersonalGoalCard;
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
 * @author marta23gili
 */
public class Match {
    private ArrayList<Player> players;
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

    public Match(int numberOfPlayers, Player matchOpener) {
        this.numberOfPlayers = numberOfPlayers;
        this.matchOpener = matchOpener;
        this.livingRoom = new LivingRoom(this);
        this.commonGoalDeck = new CommonGoalCardsDeck();
        this.personalGoalDeck = new PersonalGoalCardsDeck();
        this.players= new ArrayList<Player>();
        this.players.add(matchOpener);
        this.commonGoals=new CommonGoalCard[2];
        this.matchStatus= new WaitingForPlayers();
        this.scores= new HashMap<Player, Integer>() ;
        this.width= getBookshelfWidth();
        this.height=getBookshelfHeight();
    }

    /**
     * this method initializes the match
     */
    public void setup(){
        for(int i=0; i<numberOfPlayers; i++){
            players.get(i).setNextPlayer();
            players.get(i).setPersonalGoalCard(PersonalGoalCardsDeck.drawone());
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
            player.assignPointTile(commonGoals[0].getPointsTile());
        }

        if(commonGoals[1].check(player.getBookshelf())) {
            player.assignPointTile(commonGoals[1].getPointsTile());
        }
    }

    /**
     * this method extracts first player
     */
    private void extractFirstPlayer(){
        Random random = new Random();
        int indexOfFirstPlayer = random.nextInt(numberOfPlayers);
        this.firstPlayer= players.get(indexOfFirstPlayer);
    }

    /**
     * this method extracts the common goals cards of the mach from the deck
     */
    private void extractCommonGoals(){
        commonGoals[0]=CommonGoalCardsDeck.drawOne();
        commonGoals[1]=CommonGoalCardsDeck.drawOne();
    }

    /**
     * this method adds a new player to the match
     * @param newPlayer
     */
    public void addContestant(Player newPlayer){
        players.add(newPlayer);
        scores.put(newPlayer, 0);

        try{
           matchStatus= matchStatus.evolve();
           setup();
        }catch(UnsupportedOperationException e){
            System.err.println(e.getMessage());
        }
    }

    /**
     * this method checks if a player has ended the match
     * @param player
     * @return false if bookshelf is empty and true if it is full
     */
    private boolean checkIfBookShelfisFull(Player player){
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

    private boolean checkIfAreAdjacentTiles(BookshelfTileSpot bookshelf[][]){

    }



    /**
     * this method returns the points made by a player for adjacent tiles
     * @param player
     * @return points of adjacent tiles, made by a player
     */
    private Integer checkAdjacentTiles(Player player) {
        BookshelfTileSpot[][] bookshelf = player.getBookshelf().getTileMatrix();
        int[][] checked = new int[height][width];
        TileType tmp;
        int count;
        boolean ok;
        int numOfGroups=1;
        Integer scores=0;

        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){

        }

        return scores;
    }


    /**
     * this method calculates final scores of all players of the match, and it sets the winner
     */
    public void calculateFinalScores(){

        Integer maxScores=0;
        Integer hisScores;
        PersonalGoalCard hisPersonalGoalCard;
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
            hisPersonalGoalCard=tmp.getPersonalGoalCard();
            hisScores+=hisPersonalGoalCard.check(tmp.getBookshelf().getTileMatrix());

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
        return players;
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
