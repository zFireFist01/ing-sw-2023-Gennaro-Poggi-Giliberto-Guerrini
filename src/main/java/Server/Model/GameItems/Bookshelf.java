package Server.Model.GameItems;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * This class represents the bookshelf that every player has for the game
 */
public class Bookshelf {
    private BookshelfTileSpot[][] tileMatrix;
    private final static int BOOKSHELFHEIGHT = 6;
    private final static int BOOKSHELFWIDTH = 5;

    private Map<Integer, Integer> lastIndexes = null; //map<column_index, last_row_index
    public Bookshelf() {
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
        }
    }

    public int getBookshelfWidth(){
        return BOOKSHELFWIDTH;
    }

    public int getBookshelfHeight(){
        return BOOKSHELFHEIGHT;
    }

    public char[][] getCLIRepresentation(){
        char[][] res = new char[14][21];


        for(int i = 0; i<13; i++){
            for(int j = 0; j<21; j++){
                if(i%2 == 0){
                    if(j%4 == 0){
                        res[i][j] = '+';
                    }else{
                        res[i][j] = '-';
                    }
                }else{
                    if(j%4 == 0){
                        res[i][j] = '|';
                    }else{
                        res[i][j] = ' ';
                    }
                }
            }
        }

        res[13][2] = '0';
        res[13][6] = '1';
        res[13][10] = '2';
        res[13][14] = '3';
        res[13][18] = '4';


        return res;
    }

}
