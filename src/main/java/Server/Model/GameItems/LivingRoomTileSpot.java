package Server.Model.GameItems;

import Server.Model.Match;
import com.google.gson.annotations.Expose;

/**
 * This class represents the spot of the LivingRoom
 * @value dotsNumber represents the actual dots in the living room presents in the physical game
 * @author Patrick Poggi
 */
public class LivingRoomTileSpot extends TileSpot {
    @Expose
    private final int dotsNumber;

    private Match match;

    public LivingRoomTileSpot(Match m, int dotsNumber) {
        super();
        this.match = m;
        this.dotsNumber = dotsNumber;
    }

    public LivingRoomTileSpot(LivingRoomTileSpot ts){
        super(ts.getTileType());
        this.match = ts.match;
        this.dotsNumber = ts.getDotsNumber();
    }

    /**
     *This method tells if the spot on the LivingRoom is available due to number of players and dotsNumber
     * @return true if and only if this spot is available for the match, where the LivingRoom of the match
     *          is obviously the LivingRoom this spot belongs to
     */
    public boolean isReal(){
        return dotsNumber >= 0 && dotsNumber <= match.getNumberOfPlayers();
    }

    public int getDotsNumber(){
        return dotsNumber;
    }

}
