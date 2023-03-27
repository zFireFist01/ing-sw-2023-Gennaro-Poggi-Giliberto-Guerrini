package Server.Model;

/**
 * This class defines the tile spot
 */
public class TileSpot {
    private TileType tile;

    protected boolean isEmpty(){
        return tile==null;
    }

    protected void setTile(TileType type){ tile = type;}

    protected TileType getTileType(){
        return tile;
    }
}

