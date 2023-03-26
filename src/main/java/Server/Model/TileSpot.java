package Server.Model;
public class TileSpot {
    private TileType tile;

    protected boolean isEmpty(){
        return tile==null;
    }

    protected void setTile(TileType type){}

    protected TileType getTileType(){
        return tile;
    }
}
