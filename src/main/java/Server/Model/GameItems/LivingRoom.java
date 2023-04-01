package Server.Model.GameItems;

import java.util.Map;

public class LivingRoom {

    private LivingRoomTileSpot[][] tileMatrix;
    private Map<TileType, Integer> tileSack;
    protected LivingRoomTileSpot[][] getTileMatrix(){
        //TODO: DEFENSIVE PROHRAMMING => SHARE A COPY, NOT THE ACTUAL MATRIX
        return tileMatrix;
    };

    protected void refreshLivingRoom(TileType type){

    };

    protected Map<TileType, Integer> getTileSack(){
        //TODO: DEFENSIVE PROHRAMMING => SHARE A COPY, NOT THE ACTUAL MAP
        return tileSack;
    };

    protected void takeTile(int i, int j) throws UnsupportedOperationException{};
}
