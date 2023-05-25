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
 */
public class Bookshelf {
    private Match m; //ok
    @Expose
    private BookshelfTileSpot[][] tileMatrix; //ok
    private final static int BOOKSHELFHEIGHT = 6; //ok
    private final static int BOOKSHELFWIDTH = 5;//ok
    @Expose
    private Map<Integer, Integer> lastIndexes = null; //map<column_index, last_row_index //ok

    public Bookshelf(Match m) {
        this.m = m;
        tileMatrix = new BookshelfTileSpot[6][5];
        for(int i=0;i<BOOKSHELFHEIGHT;i++){
            for(int j=0; j<BOOKSHELFWIDTH;j++){
                tileMatrix[i][j] = new BookshelfTileSpot();
            }
        }

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

    /**
     * For test usage only
     * @return
     */
    public BookshelfTileSpot[][] getPrivateTileMatrix(){
        return tileMatrix;
    }

    /*
        0: X
        1:    X
        2: X
        3:
        4:
        5:
            0  1  2  3  4

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
     * Iterates over every column and then returns the maximum number of insertible tiles, thus the
     * number of empty spots of the "emptier" column
     * @return
     */
    public int maxInsertableTiles(){
        int max = -1;
        for(Integer i: lastIndexes.keySet()){
            if(lastIndexes.get(i) > max){
                max = lastIndexes.get(i);
            }
        }
        return (max+1); //Adding 1 because the index starts from 0
    }

    public void notifyMVEventListeners(MVEvent event){
        this.m.notifyMVEventListeners(event);
    }
}
