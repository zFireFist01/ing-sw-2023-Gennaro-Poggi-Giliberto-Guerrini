package Server.Model;

/**
 * This class represents the bookshelf that every player has for the game
 * @param TileMatrix is a matrix of every tile spot in the BookShelf
 */
public class Bookshelf {
    private BookshelfTileSpot[][] TileMatrix;

    public BookshelfTileSpot[][] getTileMatrix() {
        return TileMatrix;
    }

    public void insertTile(int column, TileType tileType){
    }
}
