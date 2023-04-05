package Server.Model.GameItems;

public class BookshelfTileSpot extends TileSpot {

    public BookshelfTileSpot(){
        super();
    }
    public BookshelfTileSpot(TileType tile){
        super(tile);
    }

    //attenzione servono 2 metodi settiletype uno che non prende in ingresso parametri (e imposta tiletype a null) e uno che prende in ingresso un tiletype ceh vadano in overload

    //servono metodi getTileType

    @Override
    public boolean equals(Object o){
        return false;
    } //TODO

}
