package Server.Model;


import com.sun.org.apache.xpath.internal.objects.XString;
import sun.jvm.hotspot.utilities.Observer;

/**
 * This class defines the player in game
 */
public class Player {
    private final int playerID;
    private PointsTile[] pointsTiles;
    private final Bookshelf bookshelf;
    private PersonalGoalCard personalGoalCard;
    private final String playerNickName;
    //private final ClientNetInterface playerNetInterface;
    //private final ClientUserInterface playerUserInterface;
    private List<Observer> observers;
    private PlayerStatus playerStatus;

    public Player(int playerID, Bookshelf bookshelf, String playerNickName, ClientNetInterface playerNetInterface, ClientUserInterface playerUserInterface) {
        this.playerID = playerID;
        this.bookshelf = bookshelf;
        this.playerNickName = playerNickName;
        //this.playerNetInterface = playerNetInterface;
        //this.playerUserInterface = playerUserInterface;
    }


    public PointsTile[] getPointsTiles() {
        return pointsTiles;
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public PersonalGoalCard getPersonalGoalCard() {
        return personalGoalCard;
    }

    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        this.personalGoalCard = personalGoalCard;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public void notify(){
    }

    public void assignPointTile(){
    }
}
