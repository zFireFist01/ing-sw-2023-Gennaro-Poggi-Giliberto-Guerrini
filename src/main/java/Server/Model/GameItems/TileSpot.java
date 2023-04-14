package Server.Model.GameItems;

/**
 * This class defines the tile spot
 */
public class TileSpot {
    private TileType tile;

    public TileSpot() {
        this.tile=null;
    }

    public TileSpot(TileType tile) {
        this.tile = tile;
    }

    public boolean isEmpty(){
        return tile==null;
    }

    protected void setTile(TileType tile){ this.tile = tile;}

    public TileType getTileType(){
        return tile;
    }
}

