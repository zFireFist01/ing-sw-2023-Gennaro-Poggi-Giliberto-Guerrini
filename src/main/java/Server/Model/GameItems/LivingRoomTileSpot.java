package Server.Model.GameItems;

import Server.Model.Match;

/**
 * @Class LivingRoomTileSpot
 * @author patrickpoggi
 */
public class LivingRoomTileSpot extends TileSpot {
    private final int dotsNumber;
    private Match match;

    public LivingRoomTileSpot(Match m, int dotsNumber) {
        super();
        this.match = m;
        this.dotsNumber = dotsNumber;
    }

    public LivingRoomTileSpot(Match m, int dotsNumber, TileType tt){
        super(tt);
        this.match = m;
        this.dotsNumber = dotsNumber;
    }

    public LivingRoomTileSpot(LivingRoomTileSpot ts){
        super(ts.getTileType());
        this.match = ts.match;
        this.dotsNumber = ts.getDotsNumber();
    }

    /**
     *
     * @return true if and only if this spot is available for the match, where the livingroom of the match
     *          is obviously the livingroom this spot belongs to
     */
    public boolean isReal(){
        return dotsNumber >= 0 && dotsNumber <= match.getNumberOfPlayers();
    }

    /**
     *
     * @return the number of dots of this spot (See game description for further info)
     */
    public int getDotsNumber(){
        return dotsNumber;
    }

}
