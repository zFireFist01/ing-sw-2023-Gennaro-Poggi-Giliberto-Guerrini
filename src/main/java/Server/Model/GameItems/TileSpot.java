package Server.Model.GameItems;

import com.google.gson.annotations.Expose;

import java.util.Objects;

/**
 * This class defines the tile spot
 * @author Patrick Poggi
 */
public class TileSpot {
    @Expose
    private TileType tile; //ok

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TileSpot tileSpot = (TileSpot) o;
        return tile.compareTo(tileSpot.tile)==0;
    }
}

