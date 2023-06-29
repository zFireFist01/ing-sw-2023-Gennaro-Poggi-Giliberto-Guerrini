package Server.Model.Player;


import Server.Model.GameItems.Bookshelf;
import Server.Model.Cards.PersonalGoalCard;
import Server.Model.GameItems.PointsTile;
import Server.Model.GameItems.TileSpot;
import Server.Model.GameItems.TileType;
import Server.Model.Match;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * This class defines the player in game
 * @author Paolo Gennaro
 */
public class Player {
    @Expose
    private final int playerID;
    @Expose
    private final Bookshelf bookshelf;
    @Expose
    private final String playerNickName;
    @Expose
    private PersonalGoalCard personalGoalCard;
    @Expose
    private final ArrayList<PointsTile> pointsTiles;
    @Expose
    private TileType[] takenTiles;
    private Player nextPlayer;
    private Match m;

    public Player(Match m, int playerID, String playerNickName){
        this.m = m;
        this.playerID = playerID;
        this.bookshelf = new Bookshelf(m);
        this.playerNickName = playerNickName;
        this.pointsTiles = new ArrayList<PointsTile>();
    }

    public void setTakenTiles(TileType[] takenTiles) {
        this.takenTiles = takenTiles;
    }

    public TileType[] getTakenTiles(){
        return this.takenTiles;
    }

    /**
     * This method set the takenTiles to null
     */
    public void clearTakenTiles() {
        this.takenTiles = null;
    }

    public Player getNextPlayer() {
        //return nextPlayer considering the disconnected player
        Player localNextPlayer = nextPlayer;
        while(m.getDisconnectedPlayers().contains(localNextPlayer)) {
            localNextPlayer = localNextPlayer.getNextPlayer();
            if (localNextPlayer == this){
                break;
            }
        }
        return localNextPlayer;
    }

    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public ArrayList<PointsTile> getPointsTiles() {
        return new ArrayList<PointsTile>(this.pointsTiles);
    }

    public Bookshelf getBookshelf() {
        return bookshelf;
    }

    public PersonalGoalCard getPersonalGoalCard() {
        return personalGoalCard;
    }

    public void assignPersonalGoalCard(PersonalGoalCard personalGoalCard) {
        this.personalGoalCard = personalGoalCard;
    }

    public String getPlayerNickName() {
        return playerNickName;
    }

    public int getPlayerID() {
        return playerID;
    }

    /**
     * This method is for assign points to a specific player and store the value
     * @param tile is the PointsTile we will store in to the pointsTiles ArrayList
     */
    public void assignPointTile(PointsTile tile) throws UnsupportedOperationException{
        if(tile.toString().contains("1") && this.pointsTiles.stream().map(Enum::toString).anyMatch(t->t.contains("1"))) throw new UnsupportedOperationException("You can't have 2 tiles of the same CommonGoalCard!");
        if(tile.toString().contains("2") && this.pointsTiles.stream().map(Enum::toString).anyMatch(t->t.contains("2"))) throw new UnsupportedOperationException("You can't have 2 tiles of the same CommonGoalCard!");
        if (this.pointsTiles.contains(PointsTile.MATCH_ENDED)) throw new UnsupportedOperationException("You can't add more tiles after you took the tile for completing the bookshelf first!");
        this.pointsTiles.add(tile);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return playerID == player.playerID
                && Objects.equals(bookshelf, player.bookshelf)
                && Objects.equals(playerNickName, player.playerNickName)
                && personalGoalCard == player.personalGoalCard
                && Objects.equals(pointsTiles, player.pointsTiles)
                && Objects.equals(nextPlayer, player.nextPlayer)
                && Arrays.equals(takenTiles, player.takenTiles)
                && Objects.equals(m, player.m);
    }

}