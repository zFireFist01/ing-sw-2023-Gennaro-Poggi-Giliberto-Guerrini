package Server.Model.GameItemsTests;

import Server.Model.GameItems.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


import java.util.*;

import static org.junit.Assert.*;


public class BookshelfTest {
    private Bookshelf uut = null;
    private Random r;

    @Before
    public void setup(){
        uut = new Bookshelf();
        r = new Random();
    }

    @After
    public void tearDown(){}
    
    @Test
    public void insertTile_expectedIndexOutOfBuondException_Test(){
        int col1 = 0;
        int col2 = 5;
        assertThrows(IndexOutOfBoundsException.class, ()->uut.insertTile(col1, TileType.randomTileType()));
        assertThrows(IndexOutOfBoundsException.class, ()->uut.insertTile(col2, TileType.randomTileType()));
    }

    @Test
    public void insertTile_expectedUnsupportedOperationException_Test(){
        int col = r.nextInt(5); //superior boundary is exclcuded
        for(int i=0; i< 7; i++){
            if(i>=6){
                assertThrows(UnsupportedOperationException.class, () -> uut.insertTile(col, TileType.randomTileType()));
            }else{
                uut.insertTile(col, TileType.randomTileType());
            }
        }
    }

    @Test
    public void insertTileTest(){
        BookshelfTileSpot[][] bs = uut.getTileMatrix();
        int col;
        int temp;
        Map<Integer, Integer> columnTimesSelected = new HashMap<>();
        for(int i = 0; i<5;i++){
            columnTimesSelected.put(0, 0);
        }

        int numberOfInsertions = 1+r.nextInt(29);
        while(numberOfInsertions>0){
            do{
                col = r.nextInt(5);
                temp = columnTimesSelected.get(col);
            }while(temp>=6);

            columnTimesSelected.put(col,++temp);
            TileType inserted = TileType.randomTileType();
            int lastBefore = getLastIndex(bs, col);

            uut.insertTile(col, inserted);

            BookshelfTileSpot[][] bsAfter = uut.getTileMatrix();;
            int lastAfter = getLastIndex(bsAfter, col);
            //assertTrue("ERRORE: Stampo la matrice prima e dopo\nPrima: ", lastBefore>lastAfter);
            if(lastBefore>lastAfter){
                //Tutto bene
            }else{
                System.err.println("ERRORE: Stampo la matrice prima e dopo\\nPrima: ");
                for(int i=0; i<6;i++){
                    for (int j=0; j<5;j++){
                        System.err.println(" "+bs[i][j].toString()+" ");
                    }
                    System.err.println("\n");
                }

                System.err.println("Dopo:");
                for(int i=0; i<6;i++){
                    for (int j=0; j<5;j++){
                        System.err.println(" "+bsAfter[i][j].toString()+" ");
                    }
                    System.err.println("\n");
                }
                fail();
            }
            numberOfInsertions--;
        }


    }

    private int getLastIndex(BookshelfTileSpot[][] mat, int col){
        int min = 6;
        for(int i = 5; i>= 0; i--){
            if(!mat[i][col].isEmpty()){
                if(i<min){
                    min = i;
                }
            }
        }
        return min;
    }
}
