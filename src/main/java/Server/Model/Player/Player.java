package Server.Model.Player;


import Server.Model.GameItems.Bookshelf;
import Server.Model.Cards.PersonalGoalCard;
import Server.Model.GameItems.PointsTile;
import java.util.ArrayList;
import java.util.Dictionary;

/**
 * This class defines the player in game
 * @author Paolo Gennaro
 */
public class Player {
    private final int playerID;
    private Bookshelf bookshelf;
    private final String playerNickName;
    private PersonalGoalCard personalGoalCard;
    private PlayerStatus playerStatus;
    private final ArrayList<PointsTile> pointsTiles;
    private Player nextPlayer;

    public Player(int playerID, String playerNickName){
        this.playerID = playerID;
        this.bookshelf = new Bookshelf();
        this.playerNickName = playerNickName;
        this.pointsTiles = new ArrayList<PointsTile>();
        this.playerStatus = new Connected();
    }

    public Player getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public ArrayList<PointsTile> getPointsTiles() {
        return pointsTiles;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    /**
     * The Getter for the PersonalGoalCard
     * @return is the PersonalGoalCard assigned to the player
     */
    public PersonalGoalCard getPersonalGoalCard() {
        return personalGoalCard;
    }

    /**
     * We assign the Player his PersonalGoalCard
     * @param personalGoalCard is the PersonalGoalCard we assign to the player
     */
    public void assignPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        this.personalGoalCard = personalGoalCard;
    }

    /**
     * The Getter for the PlayerStatus
     * @return is the playerStatus in which the player is
     */
    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    /**
     * This method is to toggle the Status of the player either if it's connected or disconnected
     */
    public void togglePlayerStatus() {
        if (this.playerStatus instanceof Connected) {
            this.playerStatus = new Disconnected();
        } else {
            this.playerStatus = new Connected();
        }
    }

    /**
     * Method to know if the player is connected or not
     * @return 1 if connected, 0 if not
     */
    public boolean isConnected(){
        return this.playerStatus instanceof Connected;
    }

    /**
     * The Getter for the NickName
     * @return is the playerNickName defined in the Constructor of this class
     */
    public String getPlayerNickName() {
        return playerNickName;
    }

    /**
     * The Getter for the PlayerID
     * @return is the playerID defined in the Constructor of this class
     */
    public int getPlayerID() {
        return playerID;
    }

    /**
     * This method is for assign points to a specific player and store the value
     * @param tile is the PointsTile we will store in to the ArrayList
     */
    public void assignPointTile(PointsTile tile) throws UnsupportedOperationException{
        if(tile.toString().contains("1") && this.pointsTiles.stream().map(Enum::toString).anyMatch(t->t.contains("1"))) throw new UnsupportedOperationException("You can't have 2 tiles of the same CommonGoalCard!");
        if(tile.toString().contains("2") && this.pointsTiles.stream().map(Enum::toString).anyMatch(t->t.contains("2"))) throw new UnsupportedOperationException("You can't have 2 tiles of the same CommonGoalCard!");
        if (this.pointsTiles.contains(PointsTile.MATCH_ENDED)) throw new UnsupportedOperationException("You can't add more tiles after you took the tile for completing the bookshelf first!");
        this.pointsTiles.add(tile);
    }
}