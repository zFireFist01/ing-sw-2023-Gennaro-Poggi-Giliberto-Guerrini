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
    private final Bookshelf bookshelf;
    private final String playerNickName;
    private PersonalGoalCard personalGoalCard;
    private PlayerStatus playerStatus;
    private final ArrayList<PointsTile> pointsTiles;

    public Player(int playerID, Bookshelf bookshelf, String playerNickName){
        this.playerID = playerID;
        this.bookshelf = bookshelf;
        this.playerNickName = playerNickName;
        this.pointsTiles = new ArrayList<PointsTile>();
        this.playerStatus = new Connected();
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
     * The Setter for the PersonalGoalCard
     * @param personalGoalCard is the PersonalGoalCard we assign to the player
     */
    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {
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
     * This method is to set the Status of the player either if it's connected or disconnected
     * @param playerStatus is the Status we are going to assign to the player
     */
    public void setPlayerStatus(PlayerStatus playerStatus) {
        if (playerStatus instanceof Connected) {
            this.playerStatus = new Connected();
        } else {
            this.playerStatus = new Disconnected();

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
    public void assignPointTile(PointsTile tile){
        this.pointsTiles.add(tile);
    }

}