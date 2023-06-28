package Server.Model.GameItems;

import Client.View.CLI.ANSIParameters;
import Server.Events.MVEvents.MVEvent;
import Server.Events.MVEvents.ModifiedBookshelfEvent;
import Server.Listeners.MVEventListener;
import Server.Model.LightMatch;
import Server.Model.Match;
import com.google.gson.annotations.Expose;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * This class represents the bookshelf that every player has for the game
 * @author Patrick Poggi
 */
public class Bookshelf {
    private Match m;
    @Expose
    private BookshelfTileSpot[][] tileMatrix;
    private final static int BOOKSHELFHEIGHT = 6;
    private final static int BOOKSHELFWIDTH = 5;
    @Expose
    private Map<Integer, Integer> lastIndexes = null; //map<column_index, last_row_index >

    /**
     * This is the constructor of the class
     * @param m is a reference of the match in which the bookshelf is
     */
    public Bookshelf(Match m) {
        this.m = m;
        tileMatrix = new BookshelfTileSpot[6][5];
        for(int i=0;i<BOOKSHELFHEIGHT;i++){
            for(int j=0; j<BOOKSHELFWIDTH;j++){
                tileMatrix[i][j] = new BookshelfTileSpot();
            }
        }
        //initialization of lastIndexes
        lastIndexes = new HashMap<>();
        lastIndexes.put(0,6);
        lastIndexes.put(1,6);
        lastIndexes.put(2,6);
        lastIndexes.put(3,6);
        lastIndexes.put(4,6);
    }

    public BookshelfTileSpot[][] getTileMatrix() {
        BookshelfTileSpot[][] copy = new BookshelfTileSpot[BOOKSHELFHEIGHT][BOOKSHELFWIDTH];
        for(int i = 0; i< BOOKSHELFHEIGHT;i++){
            for(int j=0;j<BOOKSHELFWIDTH;j++){
                copy[i][j] = new BookshelfTileSpot(tileMatrix[i][j].getTileType());
            }
        }
        return copy;
    }

    public void setTileMatrix(BookshelfTileSpot[][] tileMatrix) {
        this.tileMatrix = tileMatrix;
    }

    /**
     * For test usage only
     * @return matrix of the bookshelf
     */
    public BookshelfTileSpot[][] getPrivateTileMatrix(){
        return tileMatrix;
    }

    /**
     * This method is used to insert tile in the bookshelf
     * @param column index of column in which I want to insert the tile
     * @param tileType the type of the tile I'm inserting
     * @throws UnsupportedOperationException when the column is full
     * @throws IndexOutOfBoundsException when column doesn't exist
     * @throws NullPointerException when tile type is null
     */
    public void insertTile(int column, TileType tileType) throws UnsupportedOperationException,
            IndexOutOfBoundsException, NullPointerException{
        if(tileType == null){
            throw new NullPointerException("The tile's type cannot be null!");
        }else if(column < 0 || column > 4){
            throw new IndexOutOfBoundsException("Column is out of range!");
        } else if(lastIndexes.get(column) <= 0){
            throw new UnsupportedOperationException("This column is already full!");
        }else{
            int temp = lastIndexes.get(column);
            lastIndexes.put(column,--temp);
            tileMatrix[lastIndexes.get(column)][column].setTile(tileType);
            notifyMVEventListeners(new ModifiedBookshelfEvent(new LightMatch(this.m)));
        }
    }

    public int getBookshelfWidth(){
        return BOOKSHELFWIDTH;
    }

    public int getBookshelfHeight(){
        return BOOKSHELFHEIGHT;
    }

    public Map<Integer, Integer> getLastIndexes() {
        return lastIndexes;
    }

    /**
     * Iterates over every column and then returns the maximum number of insertable tiles, thus the
     * number of empty spots of the "emptier" column
     * @return the maximum number of insertable tiles
     */
    public int maxInsertableTiles(){
        int max = -1;
        for(Integer i: lastIndexes.keySet()){
            if(lastIndexes.get(i) > max){
                max = lastIndexes.get(i);
            }
        }
        return (max); //Adding 1 because the index starts from 0
    }

    /**
     * This method is called by insertTile method and is used to send an MVEvent,
     * in order to update the views.
     * @param event is the MVevent I want to send
     */
    public void notifyMVEventListeners(MVEvent event){
        this.m.notifyMVEventListeners(event);
    }
}
