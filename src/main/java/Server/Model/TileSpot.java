package Server.Model;

/**
 * This class defines the tile spot
 * @param tile says what type of tile is on the spot (if null there is no tile)
 */
public abstract class TileSpot {
    private TileType tile;

    public boolean isEmpty(){
        if (!this.equals(null)){
            return false;
        }else{
            return true;
        }
    }
    public void setTile(TileType tile) {
        this.tile = tile;
    }
    public TileType getTile() {
        return tile;
    }
}


/**
 * This class defines the Bookshelf tile spot
 */
public class BookshelfTileSpot extends TileSpot{

}

/**
 * This class defines the living room tile spot
 * @param dotsNumber "Dovrebbe avere a che fare con il numero di giocatori (in questo momento non ricordo)
 */
public class LivingRoomTileSpot extends TileSpot{
    private int dotsNumber;

    public boolean isReal(){}

    public int getDotsNumber() {
        return dotsNumber;
    }
}
