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

    public void setEmpty(){ this.tile = null;}


    public void setTile(){this.tile = null;}

    public void setTile(TileType tile){ this.tile = tile;}

    public TileType getTileType(){
        return tile;
    }

    @Override
    public String toString() {
        return (this.tile == null ? "-" : this.tile.toString());
    }
}

