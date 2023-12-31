package Server.Model.CardsTests.CommonGoalCardsTests;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.CommonGoalCards.*;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.PointsTile;
import Server.Model.GameItems.TileType;
import Server.Model.Match;
import java.util.Random;
import Utils.MathUtils.*;
//import org.junit.jupiter.api.*;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * This class represents the tests of the common goal cards
 * @author due2, patrickpoggi, Marta Giliberto
 */
public class CommonGoalCardsTests {

    CommonGoalCard testCard;
    Bookshelf testBookshelf;

    /**
     * This method tests the pickPointTile method in the commonGoalCard abstract class
     * @author due2, Paolo Gennaro
     */

    @Test
    public void getCardID_getDescription_work_Test(){
        CommonGoalCard testcard;

        testcard = new CommonGoalCard1(2,false);
        assertEquals(testcard.getCardID(), 1);
        assertNotNull(testcard.getCommonGoalDescription());
        testcard = new CommonGoalCard2(2,false);
        assertEquals(testcard.getCardID(), 2);
        assertNotNull(testcard.getCommonGoalDescription());
        testcard = new CommonGoalCard3(2,false);
        assertEquals(testcard.getCardID(), 3);
        assertNotNull(testcard.getCommonGoalDescription());
        testcard = new CommonGoalCard4(2,false);
        assertEquals(testcard.getCardID(), 4);
        assertNotNull(testcard.getCommonGoalDescription());
        testcard = new CommonGoalCard5(2,false);
        assertEquals(testcard.getCardID(), 5);
        assertNotNull(testcard.getCommonGoalDescription());
        testcard = new CommonGoalCard6(2,false);
        assertEquals(testcard.getCardID(), 6);
        assertNotNull(testcard.getCommonGoalDescription());
        testcard = new CommonGoalCard7(2,false);
        assertEquals(testcard.getCardID(), 7);
        assertNotNull(testcard.getCommonGoalDescription());
        testcard = new CommonGoalCard8(2,false);
        assertEquals(testcard.getCardID(), 8);
        assertNotNull(testcard.getCommonGoalDescription());
        testcard = new CommonGoalCard9(2,false);
        assertEquals(testcard.getCardID(), 9);
        assertNotNull(testcard.getCommonGoalDescription());
        testcard = new CommonGoalCard10(2,false);
        assertEquals(testcard.getCardID(), 10);
        assertNotNull(testcard.getCommonGoalDescription());
        testcard = new CommonGoalCard11(2,false);
        assertEquals(testcard.getCardID(), 11);
        assertNotNull(testcard.getCommonGoalDescription());
        testcard = new CommonGoalCard12(2,false);
        assertEquals(testcard.getCardID(), 12);
        assertNotNull(testcard.getCommonGoalDescription());
    }

    @Test
    public void getPointsTile_work_Test(){
        CommonGoalCard testcard_2player = new CommonGoalCard1(2,false);
        CommonGoalCard testcard_3player = new CommonGoalCard1(3,false);
        CommonGoalCard testcard_4player = new CommonGoalCard1(4,false);

        //test with 2 players

        assertEquals(testcard_2player.getPointsTile(), PointsTile.EIGHT_1);
        testcard_2player.removePointsTile();
        assertEquals(testcard_2player.getPointsTile(), PointsTile.FOUR_1);
        testcard_2player.removePointsTile();

        testcard_2player = new CommonGoalCard1(2,true);

        assertEquals(testcard_2player.getPointsTile(), PointsTile.EIGHT_2);
        testcard_2player.removePointsTile();
        assertEquals(testcard_2player.getPointsTile(), PointsTile.FOUR_2);
        testcard_2player.removePointsTile();

        //test with 3 players

        assertEquals(testcard_3player.getPointsTile(), PointsTile.EIGHT_1);
        testcard_3player.removePointsTile();
        assertEquals(testcard_3player.getPointsTile(), PointsTile.SIX_1);
        testcard_3player.removePointsTile();
        assertEquals(testcard_3player.getPointsTile(), PointsTile.FOUR_1);
        testcard_3player.removePointsTile();

        testcard_3player = new CommonGoalCard1(3,true);

        assertEquals(testcard_3player.getPointsTile(), PointsTile.EIGHT_2);
        testcard_3player.removePointsTile();
        assertEquals(testcard_3player.getPointsTile(), PointsTile.SIX_2);
        testcard_3player.removePointsTile();
        assertEquals(testcard_3player.getPointsTile(), PointsTile.FOUR_2);
        testcard_3player.removePointsTile();

        //test with 4 players

        assertEquals(testcard_4player.getPointsTile(), PointsTile.EIGHT_1);
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTile(), PointsTile.SIX_1);
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTile(), PointsTile.FOUR_1);
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTile(), PointsTile.TWO_1);
        testcard_4player.removePointsTile();

        testcard_4player = new CommonGoalCard1(4,true);

        assertEquals(testcard_4player.getPointsTile(), PointsTile.EIGHT_2);
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTile(), PointsTile.SIX_2);
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTile(), PointsTile.FOUR_2);
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTile(), PointsTile.TWO_2);
        testcard_4player.removePointsTile();

        //test pickpointstile throws exception when no more points tiles are available

        CommonGoalCard finalTestcard_2player = testcard_2player;
        assertThrows(UnsupportedOperationException.class, () -> {
            finalTestcard_2player.getPointsTile();
            finalTestcard_2player.removePointsTile();
        });

        CommonGoalCard finalTestcard_3player = testcard_3player;
        assertThrows(UnsupportedOperationException.class, () -> {
            finalTestcard_3player.getPointsTile();
            finalTestcard_3player.removePointsTile();
        });

        CommonGoalCard finalTestcard_4player = testcard_4player;
        assertThrows(UnsupportedOperationException.class, () -> {
            finalTestcard_4player.getPointsTile();
            finalTestcard_4player.removePointsTile();
        });

    }

    /**
     * This method tests the getPointsTiles method in the commonGoalCard abstract class
     * @author due2, Paolo Gennaro
     */
    @Test
    public void getPointsTiles_return_Test(){
        CommonGoalCard testcard_2player = new CommonGoalCard1(2,false);
        CommonGoalCard testcard_3player = new CommonGoalCard1(3,false);
        CommonGoalCard testcard_4player = new CommonGoalCard1(4,false);

        //test with 2 players

        assertEquals(testcard_2player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.FOUR_1,PointsTile.EIGHT_1)));
        testcard_2player.removePointsTile();
        assertEquals(testcard_2player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.FOUR_1)));
        testcard_2player.removePointsTile();
        assertEquals(testcard_2player.getPointsTiles(), new ArrayList<>());

        testcard_2player = new CommonGoalCard1(2,true);

        assertEquals(testcard_2player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.FOUR_2,PointsTile.EIGHT_2)));
        testcard_2player.removePointsTile();
        assertEquals(testcard_2player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.FOUR_2)));
        testcard_2player.removePointsTile();
        assertEquals(testcard_2player.getPointsTiles(), new ArrayList<>());

        //test with 3 players

        assertEquals(testcard_3player.getPointsTiles(), new ArrayList<>(Arrays.asList( PointsTile.FOUR_1, PointsTile.SIX_1,PointsTile.EIGHT_1)));
        testcard_3player.removePointsTile();
        assertEquals(testcard_3player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.FOUR_1,PointsTile.SIX_1)));
        testcard_3player.removePointsTile();
        assertEquals(testcard_3player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.FOUR_1)));
        testcard_3player.removePointsTile();
        assertEquals(testcard_3player.getPointsTiles(), new ArrayList<>());

        testcard_3player = new CommonGoalCard1(3,true);

        assertEquals(testcard_3player.getPointsTiles(), new ArrayList<>(Arrays.asList( PointsTile.FOUR_2, PointsTile.SIX_2,PointsTile.EIGHT_2)));
        testcard_3player.removePointsTile();
        assertEquals(testcard_3player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.FOUR_2,PointsTile.SIX_2)));
        testcard_3player.removePointsTile();
        assertEquals(testcard_3player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.FOUR_2)));
        testcard_3player.removePointsTile();
        assertEquals(testcard_3player.getPointsTiles(), new ArrayList<>());

        //test with 4 players

        assertEquals(testcard_4player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.TWO_1, PointsTile.FOUR_1,PointsTile.SIX_1 , PointsTile.EIGHT_1)));
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.TWO_1,PointsTile.FOUR_1,PointsTile.SIX_1)));
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.TWO_1,PointsTile.FOUR_1)));
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.TWO_1)));
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTiles(), new ArrayList<>());




        testcard_4player = new CommonGoalCard1(4,true);

        assertEquals(testcard_4player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.TWO_2, PointsTile.FOUR_2,PointsTile.SIX_2 , PointsTile.EIGHT_2)));
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.TWO_2,PointsTile.FOUR_2,PointsTile.SIX_2)));
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.TWO_2,PointsTile.FOUR_2)));
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTiles(), new ArrayList<>(Arrays.asList(PointsTile.TWO_2)));
        testcard_4player.removePointsTile();
        assertEquals(testcard_4player.getPointsTiles(), new ArrayList<>());


    }

    /**
     * This method tests the 1st common goal card in an environment where the check function
     * should return false
     * @author due2
     */
    @Test
    public void CommonGoalCard1_expectedFalse_Test() {

        testCard = new CommonGoalCard1(4, false);
        testBookshelf = new Bookshelf(new Match());
        boolean flag = false;
        int index;
        BookshelfTileSpot precedentTile = null;
        BookshelfTileSpot currentTile = new BookshelfTileSpot();
        BookshelfTileSpot matchingTile = new BookshelfTileSpot();
        //test with an empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //test with a non-empty bookshelf without 2x2 submatrix of the same tile type
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                while (i > 0 && precedentTile.equals(currentTile)) {
                    currentTile.setTile(TileType.randomTileType());
                }
                testBookshelf.insertTile(j, currentTile.getTileType());
                if(i!=0)
                    precedentTile.setTile(currentTile.getTileType());
                else
                    precedentTile = new BookshelfTileSpot(currentTile.getTileType());

            }
        }
        assertFalse(testCard.check(testBookshelf));

        //test with a bookshelf with only 1 2x2 submatrix of the same tile type
        testBookshelf = new Bookshelf(new Match());

        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                while (i < 5 && precedentTile.equals(currentTile)) {
                    currentTile.setTile(TileType.randomTileType());
                }
                testBookshelf.insertTile(j, currentTile.getTileType());
                if(i!=0)
                    precedentTile.setTile(currentTile.getTileType());
                else
                    precedentTile = new BookshelfTileSpot(currentTile.getTileType());
            }
            if ((!flag && i >= 1 && Math.random() >= 0.5) || !flag && i == 2) {
                flag = true;
                index = (int) (Math.random() * 3);
                matchingTile.setTile(TileType.randomTileType());
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 5; j++) {
                        if (j == index || j == index + 1) {
                            testBookshelf.insertTile(j, matchingTile.getTileType());
                            if(i!=0)
                                precedentTile.setTile(matchingTile.getTileType());
                            else
                                precedentTile = new BookshelfTileSpot(matchingTile.getTileType());
                        } else {
                            currentTile.setTile(TileType.randomTileType());
                            while (precedentTile.equals(currentTile)) {
                                currentTile.setTile(TileType.randomTileType());
                            }
                            testBookshelf.insertTile(j, currentTile.getTileType());
                            if(i!=0)
                                precedentTile.setTile(currentTile.getTileType());
                            else
                                precedentTile = new BookshelfTileSpot(currentTile.getTileType());
                        }

                    }
                    i--;
                }
            }

        }
        assertFalse(testCard.check(testBookshelf));
    }
    /**
     * This method tests the 1st common goal card in an environment where the check function
     * should return true
     * @author due2
     **/
    @Test
    public void CommonGoalCard1_expectedTrue_Test(){
        testCard = new CommonGoalCard1(4, false);
        testBookshelf = new Bookshelf(new Match());
        boolean flag = false;
        int index;
        BookshelfTileSpot precedentTile = null;
        BookshelfTileSpot currentTile = new BookshelfTileSpot();
        BookshelfTileSpot matchingTile = new BookshelfTileSpot();

        //test with a bookshelf with 2 2x2 submatrix of the same tile type

        index=-1;
        for (int i = 5; i >=0; i--) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                while(i!=5 && precedentTile.equals(currentTile)){
                    currentTile.setTile(TileType.randomTileType());
                }
                testBookshelf.insertTile(j,currentTile.getTileType());
                if(i!=5)
                    precedentTile.setTile(currentTile.getTileType());
                else
                    precedentTile = new BookshelfTileSpot(currentTile.getTileType());
            }
            if((!flag && i>=2 && Math.random()>=0.5)||!flag && i==2||index==-1 && i==5){
                if(index!=-1)
                    flag = true;
                index = (int) (Math.random() * 3);
                matchingTile.setTile(TileType.randomTileType());
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 5; j++) {
                        if (j == index || j == index + 1) {
                            testBookshelf.insertTile(j, matchingTile.getTileType());
                            if(i!=0)
                                precedentTile.setTile(matchingTile.getTileType());
                            else
                                precedentTile = new BookshelfTileSpot(matchingTile.getTileType());
                        } else {
                            currentTile.setTile(TileType.randomTileType());
                            while (precedentTile.equals(currentTile)) {
                                currentTile.setTile(TileType.randomTileType());
                            }
                            testBookshelf.insertTile(j, currentTile.getTileType());
                            if(i!=0)
                                precedentTile.setTile(currentTile.getTileType());
                            else
                                precedentTile = new BookshelfTileSpot(currentTile.getTileType());
                        }

                    }
                    i--;
                }
            }

        }
        assertTrue(testCard.check(testBookshelf));
        //test with a bookshelf with at least 2 2x2 submatrix of the same tile type
        testBookshelf = new Bookshelf(new Match());
        flag = false;
        index=-1;

        for (int i = 5; i >=0; i--) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                while(i!=5 && precedentTile.equals(currentTile)){
                    currentTile.setTile(TileType.randomTileType());
                }
                testBookshelf.insertTile(j,currentTile.getTileType());
                if(i!=5)
                    precedentTile.setTile(currentTile.getTileType());
                else
                    precedentTile = new BookshelfTileSpot(currentTile.getTileType());
            }
            if((i>=2 && Math.random()>=0.5)||!flag && i==2||index==-1 && i==5){
                if(index!=-1)
                    flag = true;
                index = (int) (Math.random() * 3);
                matchingTile.setTile(TileType.randomTileType());
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 5; j++) {
                        if (j == index || j == index + 1) {
                            testBookshelf.insertTile(j, matchingTile.getTileType());
                            if(i!=0)
                                precedentTile.setTile(matchingTile.getTileType());
                            else
                                precedentTile = new BookshelfTileSpot(matchingTile.getTileType());
                        } else {
                            currentTile.setTile(TileType.randomTileType());
                            while (precedentTile.equals(currentTile)) {
                                currentTile.setTile(TileType.randomTileType());
                            }
                            testBookshelf.insertTile(j, currentTile.getTileType());
                            if(i!=0)
                                precedentTile.setTile(currentTile.getTileType());
                            else
                                precedentTile = new BookshelfTileSpot(currentTile.getTileType());
                        }

                    }
                    i--;
                }
            }
        }
        assertTrue(testCard.check(testBookshelf));
    }

    /**
     * This method tests the 2nd common goal card in an environment where the check function
     * should return false
     * @author due2, Paolo Gennaro
     */
    @Test
    public void CommonGoalCard2_expectedFalse_Test() {
        testCard = new CommonGoalCard2(4, false);
        testBookshelf = new Bookshelf(new Match());
        boolean[] flag = {false, false, false, false, false, false};
        int index1, index2;
        BookshelfTileSpot[] precedentTile = new BookshelfTileSpot[6];
        BookshelfTileSpot currentTile = new BookshelfTileSpot();
        BookshelfTileSpot[][] help = new BookshelfTileSpot[6][5];

        //test with an empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //test with a non empty bookshelf without a column of different tile types


        /*
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                if (!flag[j] && i == 5) {
                    testBookshelf.insertTile(j, precedentTile[j].getTileType());
                    flag[j] = true;
                } else {
                    if (i>0 && precedentTile[j].equals(currentTile))
                        flag[j] = true;
                    precedentTile[j] = currentTile;
                    testBookshelf.insertTile(j, currentTile.getTileType());
                }
            }
        }
        */

        //Fill a matrix with random tile types
        for(int i = 0; i<6; i++){
            for(int j = 0; j < 5; j++){
                testBookshelf.insertTile(j, TileType.randomTileType());
            }
        }

        int rigaDaModificare;
        //Check if there is a column with all different tile types
        for(int j = 0; j < 5; j++){
            boolean colonnaUguale = false;
            TileType elementoUguale = testBookshelf.getTileMatrix()[0][j].getTileType();


            //Searching if elementoUguale is present more than once in the column
            for(int i = 1; i<6; i++){
                if(testBookshelf.getTileMatrix()[i][j].getTileType() == elementoUguale){
                    colonnaUguale = true;
                    break;
                }
            }
            help = testBookshelf.getTileMatrix();
            //If there is a column an element of the same type as elementoUguale we change a random element of the column and set it to elementoUguale's type
            if(!colonnaUguale){
                do {
                    rigaDaModificare = (int) (Math.random() * 6);
                }while( rigaDaModificare == 0);
                help[rigaDaModificare][j].setTile(elementoUguale);
                testBookshelf.setTileMatrix(help);
            }
        }


        //boolean flag2 = testCard.check(testBookshelf);
        assertFalse(testCard.check(testBookshelf));


        //test with a bookshelf with only one column of different tile types
        precedentTile = new BookshelfTileSpot[6];
        testBookshelf = new Bookshelf(new Match());
        /*
        flag = new boolean[]{false, false, false, false, false, false};
        index1 = (int) (Math.random() * 4);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                if (!flag[j] && i == 5 && j != index1) {
                    testBookshelf.insertTile(j, precedentTile[j].getTileType());
                    flag[j] = true;
                } else {
                    if (i > 0 && precedentTile[j].equals(currentTile) && j != index1)
                        flag[j] = true;
                    precedentTile[j] = currentTile;
                    if (j != index1) {
                        testBookshelf.insertTile(j, currentTile.getTileType());
                    } else {
                        testBookshelf.insertTile(j, TileType.values()[i]);
                    }
                }
            }
        }
        */
        index1 = (int) (Math.random() * 5);
        TileType tileType = null;
        TileType[] tileTypes = TileType.values();
        for (int i = 0; i < 6; i++) {
            tileType = tileTypes[i];
            testBookshelf.insertTile(index1, tileType);
        }
        for(int i = 0; i<6; i++) {
            for (int j = 0; j < 5; j++) {
                if (j == index1) {
                    continue;
                }
                testBookshelf.insertTile(j, TileType.randomTileType());
            }
        }
        //Check if there is a column with all different tile types that is not index1 column
        for(int j = 0; j < 5; j++){
            if (j == index1) {
                continue;
            }
            boolean colonnaUguale = false;
            TileType elementoUguale = testBookshelf.getTileMatrix()[0][j].getTileType();

            //Searching if elementoUguale is present more than once in the column
            for(int i = 1; i<6; i++){
                if(testBookshelf.getTileMatrix()[i][j].getTileType() == elementoUguale){
                    colonnaUguale = true;
                    break;
                }
            }

            help = testBookshelf.getTileMatrix();
            //If there is a column an element of the same type as elementoUguale we change a random element of the column and set it to elementoUguale's type
            if(!colonnaUguale){
                do {
                    rigaDaModificare = (int) (Math.random() * 6);
                }while( rigaDaModificare == 0);
                help[rigaDaModificare][j].setTile(elementoUguale);
                testBookshelf.setTileMatrix(help);
            }
        }
        
        assertFalse(testCard.check(testBookshelf));
    }
    /**
     * This method tests the 2nd common goal card in an environment where the check function
     * should return true
     * @author due2
     */
    @Test
    public void CommonGoalCard2_expectedTrue_Test(){
        testCard = new CommonGoalCard2(4, false);
        testBookshelf = new Bookshelf(new Match());
        boolean[] flag = new boolean[]{false,false,false,false,false,false};
        int index1, index2;
        BookshelfTileSpot[] precedentTile = new BookshelfTileSpot[6];
        BookshelfTileSpot currentTile = new BookshelfTileSpot();


        //test with a bookshelf with two rows of different tile types


        index1=(int)(Math.random()*4);
        index2=(int)(Math.random()*4);
        while(index1==index2)
            index2=(int)(Math.random()*4);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                if(!flag[j] && i==5 && j!=index1 && j!=index2){
                    testBookshelf.insertTile(j,precedentTile[j].getTileType());
                    flag[j]=true;
                }else{
                    if(i>0 && precedentTile[j].equals(currentTile) && j!=index1 && j!=index2)
                        flag[j]=true;
                    precedentTile[j] = currentTile;
                    if(j!=index1 && j!=index2) {
                        testBookshelf.insertTile(j, currentTile.getTileType());
                    }else{
                        testBookshelf.insertTile(j, TileType.values()[i]);
                    }
                }
            }
        }
        assertTrue(testCard.check(testBookshelf));



    }
    /**
     * This method tests the 3rd common goal card in an environment where the check function
     * should return false
     * @author due2
     */
    @Test
    public void CommonGoalCard3_expectedFalse_Test(){
        CommonGoalCard3 testCard = new CommonGoalCard3(4, false);
        Bookshelf testBookshelf = new Bookshelf(new Match());

        // Define an array of different tile types to use
        TileType[] tileTypes = TileType.values();

        // Fill the bookshelf such that no group of four tiles of the same type are adjacent
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                // Calculate the index of the tile type to use
                int index = (i + j) % 4;

                // Insert the tile into the bookshelf
                testBookshelf.insertTile(j, tileTypes[index]);
            }
        }

        // Check that the card's condition is not met
        assertFalse(testCard.check(testBookshelf));

    }

    /**
     * This method tests the 3rd common goal card in an environment where the check function
     * should return true
     * @author due2
     */
    @Test
    public void CommonGoalCard3_expectedTrue_Test(){
        testCard = new CommonGoalCard3(4, false);
        testBookshelf = new Bookshelf(new Match());

        int[] i_index = {0,0,0,0};
        int count;
        BookshelfTileSpot testTile = new BookshelfTileSpot();
        BookshelfTileSpot[] precedentTile = new BookshelfTileSpot[5];
        BookshelfTileSpot[] currentTile = {new BookshelfTileSpot(),new BookshelfTileSpot(),new BookshelfTileSpot(),new BookshelfTileSpot(),new BookshelfTileSpot()};

        testTile.setTile(TileType.randomTileType());

        for(int i=0; i<4 ;i++){
            i_index[i]=(int)(Math.random()*2);
        }


        HashSet<Integer> indices = new HashSet<Integer>();
        while(indices.size() < 4) {
            indices.add((int) (Math.random() * 4));
        }
        Integer[] j_index = indices.toArray(new Integer[4]);

        count=0;


        for(int i=0;i<5;i++){
            currentTile[i]=new BookshelfTileSpot(TileType.randomTileType());
        }

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++) {
                if(count<4 && ((i==i_index[0] && j==j_index[0])||(i==i_index[1] && j==j_index[1])||(i==i_index[2] && j==j_index[2])||(i==i_index[3] && j==j_index[3]))) {
                    testTile.setTile(TileType.randomTileType());
                    testBookshelf.insertTile(j, testTile.getTileType());
                    testBookshelf.insertTile(j, testTile.getTileType());
                    testBookshelf.insertTile(j, testTile.getTileType());
                    testBookshelf.insertTile(j, testTile.getTileType());
                    count++;
                }else if(((i<i_index[0] ||i>i_index[0]+3)&& j==j_index[0]) || ((i<i_index[1] ||i>i_index[1]+3)&& j==j_index[1]) || ((i<i_index[2] ||i>i_index[2]+3)&& j==j_index[2]) || ((i<i_index[3] ||i>i_index[3]+3)&& j==j_index[3])||j!=j_index[0]&&j!=j_index[1]&&j!=j_index[2]&&j!=j_index[3]){
                    testBookshelf.insertTile(j, currentTile[j].getTileType());
                    if(precedentTile[j]==null)
                        precedentTile[j]=new BookshelfTileSpot(currentTile[j].getTileType());
                    else
                        precedentTile[j].setTile(currentTile[j].getTileType());
                    do {
                        currentTile[j].setTile(TileType.randomTileType());
                    }while(currentTile[j].getTileType().equals(precedentTile[j].getTileType()));
                }
            }
        }
        assertTrue(testCard.check(testBookshelf));

        //tests with 4 group of 4 tiles of the same type

        testBookshelf = new Bookshelf(new Match());
        int [][] Tiles={
                {0,1,1,2,5},
                {2,1,1,3,5},
                {2,2,2,3,4},
                {0,4,5,3,4},
                {5,2,1,3,5},
                {1,1,1,1,0}};

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                testBookshelf.insertTile(j, TileType.values()[Tiles[i][j]]);
            }
        }
        assertTrue(testCard.check(testBookshelf));





    }

    /**
     * This method tests the 4th common goal card in an environment where the check function
     * should return false
     * @author due2
     */
    @Test
    public void CommonGoalCard4_expectedFalse_Test(){
        testCard = new CommonGoalCard3(4, false);
        testBookshelf = new Bookshelf(new Match());
        BookshelfTileSpot[] precedentTile = new BookshelfTileSpot[5];
        BookshelfTileSpot[] currentTile = new BookshelfTileSpot[5];
        int count=0,rnd1,index=7,rnd2;

        //test with an empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //test with less than 6 2x1 columns of same tiles and no 3x1 columns of same tiles
        for(int i=0;i<5;i++){
            currentTile[i]=new BookshelfTileSpot(TileType.values()[i]);
        }
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++) {
                testBookshelf.insertTile(j, currentTile[j].getTileType());
                if(precedentTile[j]==null)
                    precedentTile[j]=new BookshelfTileSpot(currentTile[j].getTileType());
                else
                    precedentTile[j].setTile(currentTile[j].getTileType());
                do {
                    currentTile[j].setTile(TileType.randomTileType());
                }while(currentTile[j].getTileType()==precedentTile[j].getTileType() || currentTile[j].getTileType()==currentTile[(j+1)%5].getTileType());

            }
            rnd1=(int)(Math.random()*6);
            if(count<5 && rnd1%3==0){
                do {
                    rnd2 = (int) (Math.random() * 5);
                }while(rnd2==index);
                index=rnd2;
                currentTile[index].setTile(precedentTile[index].getTileType());
            }
        }
        assertFalse(testCard.check(testBookshelf));


    }

    /**
     * This method tests the 4th common goal card in an environment where the check function
     * should return true
     * @author due2
     */
    @Test
    public void CommonGoalCard4_expectedTrue_Test(){
        testCard = new CommonGoalCard4(4, false);
        testBookshelf = new Bookshelf(new Match());
        BookshelfTileSpot[] precedentTile = new BookshelfTileSpot[5];
        BookshelfTileSpot[] currentTile = new BookshelfTileSpot[5];
        int count=0,index=7,rnd2,rnd1;

        for(int i=0;i<5;i++){
            currentTile[i]=new BookshelfTileSpot(TileType.randomTileType());
        }
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++) {
                testBookshelf.insertTile(j, currentTile[j].getTileType());

                if(precedentTile[j]==null) {
                    precedentTile[j] = new BookshelfTileSpot(currentTile[j].getTileType());
                }else {
                    precedentTile[j].setTile(currentTile[j].getTileType());
                }

                do {
                    currentTile[j].setTile(TileType.randomTileType());
                }while(currentTile[j].getTileType()==precedentTile[j].getTileType());
            }

            if(count<6){
                do {
                    rnd2 = (int) (Math.random() * 4);
                    rnd1=rnd2+1;
                }while(rnd2==index || rnd1==index || rnd2==index+1);
                if(count==3){
                    currentTile[rnd1].setTile(precedentTile[rnd1].getTileType());
                }
                index=rnd2;
                currentTile[index].setTile(precedentTile[index].getTileType());

                count++;
            }
        }
        assertTrue(testCard.check(testBookshelf));
    }


    /**
     * This method tests the 5th common goal card in an environment where the check function
     * should return false
     * @author due2
     */
    @Test
    public void CommonGoalCard5_expectedFalse_Test(){
        testCard = new CommonGoalCard5(4, false);
        testBookshelf = new Bookshelf(new Match());


        BookshelfTileSpot[] column1 = new BookshelfTileSpot[6];
        BookshelfTileSpot[] column2 = new BookshelfTileSpot[6];
        BookshelfTileSpot[] currentTile = new BookshelfTileSpot[6];
        int tmp[] = new int[6];
        int rnd1,rnd2;
        boolean flag=false;


        //test with an empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //test with less than 3 columns of at least 3 same tiles




        for(int i =0; i<6;i++){
            tmp[i]=i;
        }
        for(int i=0;i<6;i++){
            int random = (int) (Math.random() * 6);
            int temp = tmp[i];
            tmp[i] = tmp[random];
            tmp[random] = temp;
        }
        for(int i=0;i<6;i++){
            currentTile[i]=new BookshelfTileSpot(TileType.values()[tmp[i]]);
        }



        //prepare coloumns
        rnd1=(int)(Math.random()*2);

        rnd2=(int)(Math.random()*3 +1);
        for(int j=0;j<rnd2;j++){
            column1[j]=new BookshelfTileSpot(TileType.randomTileType());
        }
        for(int j=rnd2;j<6;j++){
            column1[j]=new BookshelfTileSpot(column1[0].getTileType());
        }

        if(rnd1==1){
            rnd2=(int)(Math.random()*3 +1);
            for(int j=0;j<rnd2;j++){
                column2[j]=new BookshelfTileSpot(TileType.randomTileType());
            }
            for(int j=rnd2;j<6;j++){
                column2[j]=new BookshelfTileSpot(column2[0].getTileType());
            }
            flag=true;
        }
        rnd1=(int)(Math.random()*5);
        do {
            rnd2 = (int) (Math.random() * 5);
        }while(rnd2==rnd1);

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                if(j==rnd1){
                    testBookshelf.insertTile(j,column1[i].getTileType());
                }
                else if(flag && j==rnd2){
                    testBookshelf.insertTile(j,column2[i].getTileType());
                }
                else{
                    testBookshelf.insertTile(j,currentTile[i].getTileType());
                }

            }
        }
        assertFalse(testCard.check(testBookshelf));


    }
    /**
     * This method tests the 5th common goal card in an environment where the check function
     * should return true
     * @author due2
     */
    @Test
    public void CommonGoalCard5_expectedTrue_Test() {
        testCard = new CommonGoalCard5(4, false);
        testBookshelf = new Bookshelf(new Match());
        BookshelfTileSpot[] column1 = new BookshelfTileSpot[6];
        BookshelfTileSpot[] column2 = new BookshelfTileSpot[6];
        BookshelfTileSpot[] column3 = new BookshelfTileSpot[6];

        int rnd1, rnd2, rnd3;




        rnd1=(int)(Math.random()*3 +1);
        for(int j=0;j<rnd1;j++){
            column1[j]=new BookshelfTileSpot(TileType.randomTileType());
        }
        for(int j=rnd1;j<6;j++){
            column1[j]=new BookshelfTileSpot(column1[0].getTileType());
        }


        rnd2=(int)(Math.random()*3 +1);
        for(int j=0;j<rnd2;j++){
            column2[j]=new BookshelfTileSpot(TileType.randomTileType());
        }
        for(int j=rnd2;j<6;j++){
            column2[j]=new BookshelfTileSpot(column2[0].getTileType());
        }

        rnd3=(int)(Math.random()*3 +1);
        for(int j=0;j<rnd3;j++){
            column3[j]=new BookshelfTileSpot(TileType.randomTileType());
        }
        for(int j=rnd3;j<6;j++){
            column3[j]=new BookshelfTileSpot(column2[0].getTileType());
        }

        rnd1=(int)(Math.random()*5);
        do {
            rnd2 = (int) (Math.random() * 5);
        }while(rnd2==rnd1);
        do {
            rnd3 = (int) (Math.random() * 5);
        }while(rnd3==rnd1 || rnd3==rnd2);

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                if(j==rnd1){
                    testBookshelf.insertTile(j,column1[i].getTileType());
                }
                else if(j==rnd2){
                    testBookshelf.insertTile(j,column2[i].getTileType());
                }
                else if(j==rnd3){
                    testBookshelf.insertTile(j,column3[i].getTileType());
                }
                else{
                    testBookshelf.insertTile(j,TileType.randomTileType());
                }

            }
        }
        assertTrue(testCard.check(testBookshelf));

    }

    /**
     * This method tests the 6th common goal card in an environment where the check function
     * should return false
     * @author due2
     */
    @Test
    public void CommonGoalCard6_expectedFalse_Test() {
        testCard = new CommonGoalCard6(4, false);
        testBookshelf = new Bookshelf(new Match());
        BookshelfTileSpot[] row = new BookshelfTileSpot[5];
        int[] rnd = new int[5];
        int rnd1,rnd2;
        BookshelfTileSpot currentTile = new BookshelfTileSpot(TileType.randomTileType());

        //test with empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //prepare row with 5 tiles of different types
        for(int i=0;i<5;i++){
            rnd[i]=i;
        }
        //shuffle rnd
        for(int i=0;i<5;i++){
            rnd1 = (int)(Math.random()*5);

            int temp = rnd[rnd1];
            rnd[rnd1]=rnd[i];
            rnd[i]=temp;
        }
        for(int i=0;i<5;i++){
            row[i]=new BookshelfTileSpot(TileType.values()[rnd[i]]);
        }

        //create a bookshelf with only one row of different tiletypes
        rnd1=(int)(Math.random()*5);
        for(int i=0;i<6;i++){
            rnd2=(int)(Math.random()*4);
            for(int j=0;j<5;j++){
                if(i==rnd1)
                    testBookshelf.insertTile(j,row[j].getTileType());
                else {
                    testBookshelf.insertTile(j, currentTile.getTileType());
                    if(j!=rnd2){
                        currentTile.setTile(TileType.randomTileType());
                    }

                }

            }
        }
        assertFalse(testCard.check(testBookshelf));

    }

    /**
     * This method tests the 6th common goal card in an environment where the check function
     * should return true
     * @author due2
     */
    @Test
    public void CommonGoalCard6_expectedTrue_Test() {
        testCard = new CommonGoalCard6(4, false);
        testBookshelf = new Bookshelf(new Match());
        BookshelfTileSpot[] row1 = new BookshelfTileSpot[5];
        BookshelfTileSpot[] row2 = new BookshelfTileSpot[5];
        int[] arnd1 = new int[5], arnd2 = new int[5];
        int rnd1,rnd2;
        BookshelfTileSpot currentTile = new BookshelfTileSpot(TileType.randomTileType());

        //prepare row with 5 tiles of different types
        for(int i=0;i<5;i++){
            arnd1[i]=i;
            arnd2[i]=i;
        }
        //shuffle rnd
        for(int i=0;i<5;i++){
            rnd1 = (int)(Math.random()*5);
            rnd2 = (int)(Math.random()*5);

            int temp1 = arnd1[rnd1];
            arnd1[rnd1]=arnd1[i];
            arnd1[i]=temp1;

            int temp2 = arnd2[rnd2];
            arnd2[rnd2]=arnd2[i];
            arnd2[i]=temp2;
        }
        for(int i=0;i<5;i++){
            row1[i]=new BookshelfTileSpot(TileType.values()[arnd1[i]]);
            row2[i]=new BookshelfTileSpot(TileType.values()[arnd2[i]]);
        }

        //create a bookshelf with at least two rows of different tiletypes
        rnd1=(int)(Math.random()*6);
        do {
            rnd2 = (int) (Math.random() * 6);
        }while(rnd2==rnd1);

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++){
                if(i==rnd1)
                    testBookshelf.insertTile(j,row1[j].getTileType());
                else if(i==rnd2)
                    testBookshelf.insertTile(j,row2[j].getTileType());
                else {
                    testBookshelf.insertTile(j, currentTile.getTileType());
                    currentTile.setTile(TileType.randomTileType());

                }

            }
        }
        assertTrue(testCard.check(testBookshelf));

    }

    /**
     * This method tests the 7th common goal card in an environment where the check function
     * should return true
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard7_expectedTrue_Test(){
        //Four rows of tiles of at most three different types
        testCard = new CommonGoalCard7(4, false);
        Match m = new Match();
        testBookshelf = new Bookshelf(m);
        Random r = new Random();
        TileType rtt = TileType.randomTileType();
        TileType rtt2, rtt3;

        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting in the lowest row
            testBookshelf.insertTile(j,rtt);                //every tile same type
        }

        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the 2nd to last row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }

        rtt = TileType.randomTileType();
        do{
            rtt2 = TileType.randomTileType();
            rtt3 = TileType.randomTileType();
        }while(rtt2 == rtt || rtt3 == rtt);
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting in the third to last row
            int c = r.nextInt(3);
            switch (c){                         //At most three different types
                case 0:
                    testBookshelf.insertTile(j,rtt);
                    break;
                case 1:
                    testBookshelf.insertTile(j,rtt2);
                    break;
                case 2:
                    testBookshelf.insertTile(j,rtt3);
                    break;
            }
        }

        rtt = TileType.randomTileType();
        do{
            rtt2 = TileType.randomTileType();
        }while(rtt2 == rtt);
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting in the 4th to last row
            int c = r.nextInt(2);
            switch (c){                         //At most two different types
                case 0:
                    testBookshelf.insertTile(j,rtt);
                    break;
                case 1:
                    testBookshelf.insertTile(j,rtt2);
                    break;
            }
        }

        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting in the 2nd row
            testBookshelf.insertTile(j,rtt);                //every tile same type
        }

        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //iserting in the 1st row
            if(j!=3) {testBookshelf.insertTile(j,rtt);} // leaving a blank
        }

        assertTrue(testCard.check(testBookshelf));
    }

    /**
     * This method tests the 7th common goal card in an environment where the check function
     * should return false
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard7_expectedFalse_Test(){
        testCard  =  new CommonGoalCard7(4, false);
        testBookshelf = new Bookshelf(new Match());
        BookshelfTileSpot[][] matrix = testBookshelf.getPrivateTileMatrix();
        Random r = new Random();

        //Zero-th case: empty bookshelf
        assertFalse(testCard.check(testBookshelf));
        //First case: we have exactly 4 rows of length 5 but each of them has 4 different types (so that
        // we check the limit of the condition)
        for(int k = 0;k<20;k++){ //Do the thing several times, since some things are random (shouldn't be a problem though)
            int[] rows = new int[]{-1, -1, -1, -1};
            for(int i=0;i<4;i++){
                int guess = -1;
                do{
                    guess = r.nextInt(6); //There are 6 lines: 0--5 and the parameter is exclusive
                }while(rows[0]==guess || rows[1]==guess || rows[2]==guess || rows[3]==guess); //So we don't insert the same row twice
                rows[i] = guess;
            }
            TileType[] types = new TileType[]{null,null,null,null};
            for(int i=0;i<4;i++){
                TileType tt;
                do{
                    tt = TileType.randomTileType();
                }while(tt == types[0] || tt == types[1] || tt == types[2] || tt == types[3]); //So we don't insert the same type twice
                types[i] = tt;
            }
            for(int i=0;i<4;i++){ //for all the 4 rows
                int row = rows[i];
                for(int j=0;j<5;j++){ //for the 5 columns
                    matrix[row][j].setTile(types[j%4]);
                }
            }
            //Now the matrix has 4 random rows of length 5, each with exactly 4 different types selected randomly
            assertFalse(testCard.check(testBookshelf));
        }

        //Second case: we have rows with at most 3 differnt types, but we have less than 4 rows
        //2.1: 1 type in those rows
        int numrows = 1;
        List<Integer> rows;
        while(numrows<4){
            testBookshelf = new Bookshelf(new Match());
            matrix = testBookshelf.getPrivateTileMatrix();
            rows = new ArrayList<>();
            for(int i=0;i<numrows;i++){
                int row;
                do{
                    row = r.nextInt(6);
                }while(rows.contains(row));
                rows.add(row);
            }

            for(TileType tt: TileType.values()){
                for(int i=0;i<numrows;i++){ //for all the numrows rows
                    int row = rows.get(i);
                    for(int j=0;j<5;j++){ //for the 5 columns
                        matrix[row][j].setTile(tt); //we don't care what type we insert, as long as
                        //it comes from the selected ones, that ensures us they're exactly 4 different types
                    }
                }
                assertFalse(testCard.check(testBookshelf));
            }
            numrows++;
        }

        //2.2 exactly 2 or exactly 3 different types in those rows
        rows = new ArrayList<>();
        numrows = 1;
        int numtypes;

        List<TileType> types;
        for(int k=0;k<20;k++){ //Do the thing several times, since some things are random (shouldn't be a problem though)
            while(numrows<4){
                numtypes = 2;
                while(numtypes <4){
                    testBookshelf = new Bookshelf(new Match());
                    matrix = testBookshelf.getPrivateTileMatrix();
                    rows = new ArrayList<>();
                    types = new ArrayList<>();
                    for(int i=0;i<numrows;i++){
                        int row;
                        do{
                            row = r.nextInt(6);
                        }while(rows.contains(row));
                        rows.add(row);
                    }
                    for(int i=0;i<numtypes;i++){
                        TileType tt;
                        do{
                            tt = TileType.randomTileType();
                        }while(types.contains(tt));
                        types.add(tt);
                    }
                    for(int i=0;i<numrows;i++){ //for all the numrows rows
                        int row = rows.get(i);
                        for(int j=0;j<5;j++){ //for the 5 columns
                            matrix[row][j].setTile(types.get(j%numtypes)); //we don't care what type we insert, as long as
                            //it comes from the selected ones, that ensures us they're exactly 4 different types
                        }
                    }
                    assertFalse(testCard.check(testBookshelf));
                    numtypes++;
                }
                numrows++;
            }
        }

        //Third case: we have four rows of length 5, but each with at most x different types and a blank, for x in {1,2,3,4,5,6}
        for(int k=0;k<20;k++){ //Do the thing several times, since some things are random (shouldn't be a problem though)
            testBookshelf = new Bookshelf(new Match());
            matrix = testBookshelf.getPrivateTileMatrix();
            int numDifferentTypes = 1;
            int numRows = 4;
            rows = new ArrayList<>();
            types = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                int guess;
                do {
                    guess = r.nextInt(6); //There are 6 lines: 0--5 and the parameter is exclusive
                } while (rows.contains(guess)); //So we don't insert the same row twice
                rows.add(guess);
            }
            for (int i = 0; i < numDifferentTypes; i++) {
                TileType tt;
                do {
                    tt = TileType.randomTileType();
                } while (types.contains(tt)); //So we don't insert the same type twice
                types.add(tt);
            }
            //Now we have the rows and the types, we need to decide where to put the blanks. Blanks should not be in the same
            //column if their respective rows are one on top of the other, so we need to check that. Since we are using the
            //method tileMatrix.setTile (and not the method bookshelf.insertTile) we shouldn't have the need of ensuring
            //that no blank is adjacent to one below it.
            for (int i = 0; i < numRows; i++) { //for all the 4 rows
                int row = rows.get(i);
                int columnOfTheBlanck = r.nextInt(5);
                for (int j = 0; j < 5; j++) { //for the 5 columns
                    if (j == columnOfTheBlanck) {
                        matrix[row][j].setTile(null);
                    } else {
                        if (numDifferentTypes == 1) {
                            matrix[row][j].setTile(types.get(0));
                        } else {
                            matrix[row][j].setTile(types.get(j%numDifferentTypes)); //we don't care what type we insert, as long as
                            //it comes from the selected ones, that ensures us they're exactly 4 different types
                        }
                    }
                }
            }
            assertFalse(testCard.check(testBookshelf));
        }
    }

    /**
     * This method tests the 8th common goal card in an environment where the check function
     * should return true
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard8_expectedTrue_Test(){
        testCard = new CommonGoalCard8(4, false);
        testBookshelf = new Bookshelf(new Match());

        TileType rtt = TileType.randomTileType();

        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){
            if(j==0 || j== testBookshelf.getBookshelfWidth()-1){
                testBookshelf.insertTile(j, rtt);
            }else{
                testBookshelf.insertTile(j, TileType.randomTileType());
            }
        }
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){
            testBookshelf.insertTile(j, TileType.randomTileType());
        }
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){
            testBookshelf.insertTile(j, TileType.randomTileType());
        }
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){
            testBookshelf.insertTile(j, TileType.randomTileType());
        }
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){
            testBookshelf.insertTile(j, TileType.randomTileType());
        }
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){
            if(j==0 || j== testBookshelf.getBookshelfWidth()-1){
                testBookshelf.insertTile(j, rtt);
            }else{
                testBookshelf.insertTile(j, TileType.randomTileType());
            }
        }

        assertTrue(testCard.check(testBookshelf));

    }

    /**
     * This method tests the 8th common goal card in an environment where the check function
     * should return false
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard8_expectedFalse_Test(){
        testCard = new CommonGoalCard8(4, false);
        testBookshelf = new Bookshelf(new Match());
        TileType tt = TileType.randomTileType();
        TileType wrongtt = TileType.randomTileType();

        //Checking the empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //BookshelfTileSpot[][] threeOkOneNotMatrix = new BookshelfTileSpot[6][5]; //(0,0) is not ok
        //Only (0,0) is different from the others 3 corners
        for(int i=0;i<testBookshelf.getBookshelfWidth();i++){                   //last row
            if(i==0 || i == testBookshelf.getBookshelfWidth()-1){
                testBookshelf.insertTile(i, tt);
            }else{
                testBookshelf.insertTile(i,TileType.randomTileType());
            }
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //2nd to last row
            testBookshelf.insertTile(i,TileType.randomTileType());
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //3rd to last row
            testBookshelf.insertTile(i,TileType.randomTileType());
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //4th to last row
            testBookshelf.insertTile(i,TileType.randomTileType());
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //2nd row
            testBookshelf.insertTile(i,TileType.randomTileType());
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //1st row
            do{
                wrongtt = TileType.randomTileType();
            }while(wrongtt == tt);
            if(i==0){
                testBookshelf.insertTile(i, wrongtt);
            }else if(i== testBookshelf.getBookshelfWidth()-1){
                testBookshelf.insertTile(i,tt);
            }else{
                testBookshelf.insertTile(i,TileType.randomTileType());
            }
        }

        assertFalse(testCard.check(testBookshelf));

        //Only (0,testBookshelf.getWidth()-1) is different from the others 3 corners
        testBookshelf = new Bookshelf(new Match());
        for(int i=0;i<testBookshelf.getBookshelfWidth();i++){                   //las row
            if(i==0 || i == testBookshelf.getBookshelfWidth()-1){
                testBookshelf.insertTile(i, tt);
            }else{
                testBookshelf.insertTile(i,TileType.randomTileType());
            }
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //2nd to last row
            testBookshelf.insertTile(i,TileType.randomTileType());
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //3rd to last row
            testBookshelf.insertTile(i,TileType.randomTileType());
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //4th to last row
            testBookshelf.insertTile(i,TileType.randomTileType());
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //2nd row
            testBookshelf.insertTile(i,TileType.randomTileType());
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //1st row
            do{
                wrongtt = TileType.randomTileType();
            }while(wrongtt == tt);
            if(i==testBookshelf.getBookshelfWidth()-1){
                testBookshelf.insertTile(i, wrongtt);
            }else if(i== 0){
                testBookshelf.insertTile(i,tt);
            }else{
                testBookshelf.insertTile(i,TileType.randomTileType());
            }
        }
        assertFalse(testCard.check(testBookshelf));

        //Only (testBookShelf.getHeight()-1,0) is different from the others 3 corners
        testBookshelf = new Bookshelf(new Match());
        for(int i=0;i<testBookshelf.getBookshelfWidth();i++){                   //las rowwrongtt;
            do{
                wrongtt = TileType.randomTileType();
            }while(wrongtt == tt);
            if(i==0){
                testBookshelf.insertTile(i, wrongtt);
            }else if(i== testBookshelf.getBookshelfWidth()-1){
                testBookshelf.insertTile(i,tt);
            }else{
                testBookshelf.insertTile(i,TileType.randomTileType());
            }
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //2nd to last row
            testBookshelf.insertTile(i,TileType.randomTileType());
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //3rd to last row
            testBookshelf.insertTile(i,TileType.randomTileType());
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //4th to last row
            testBookshelf.insertTile(i,TileType.randomTileType());
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //2nd row
            testBookshelf.insertTile(i,TileType.randomTileType());
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //1st row
            if(i==0 || i == testBookshelf.getBookshelfWidth()-1){
                testBookshelf.insertTile(i, tt);
            }else{
                testBookshelf.insertTile(i,TileType.randomTileType());
            }
        }
        assertFalse(testCard.check(testBookshelf));

        //Only (testBookShelf.getHeight()-1,testBookShelf.getHeight()-1) is different from the others 3 corners
        testBookshelf = new Bookshelf(new Match());
        do{
            wrongtt = TileType.randomTileType();
        }while(wrongtt == tt);
        for(int i=0;i<testBookshelf.getBookshelfWidth();i++){                   //las row
            if(i==testBookshelf.getBookshelfHeight()-1){
                testBookshelf.insertTile(i, wrongtt);
            }else if(i==0){
                testBookshelf.insertTile(i,tt);
            }else{
                testBookshelf.insertTile(i,wrongtt);
            }
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //2nd to last row
            testBookshelf.insertTile(i,wrongtt);
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //3rd to last row
            testBookshelf.insertTile(i,wrongtt);
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //4th to last row
            testBookshelf.insertTile(i,wrongtt);
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //2nd row
            testBookshelf.insertTile(i,wrongtt);
        }
        for(int i=0;i< testBookshelf.getBookshelfWidth();i++){                  //1st row
            if(i==0 || i == testBookshelf.getBookshelfWidth()-1){
                testBookshelf.insertTile(i, tt);
            }else{
                testBookshelf.insertTile(i,wrongtt);
            }
        }
        assertFalse(testCard.check(testBookshelf));

        //Every possible combination of the 4 squares not being of the same type will be false for one of these reasons,
        //so there's no need to check them.
    }

    /**
     * This method tests the 9th common goal card in an environment where the check function
     * should return true
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard9_expectedTrue_Test(){
        testCard = new CommonGoalCard9(4, false);
        testBookshelf = new Bookshelf(new Match());
        //Set<Couple<Integer, Integer>> positions = new HashSet<>();
        List<Couple<Integer, Integer>> positions = new ArrayList<>();
        //Set<Couple> donePositions = new HashSet<>();
        Random r = new Random();
        for(TileType tt : TileType.values()){
            //Select random positions where this type should go
            for(int i=0;i<8;i++){
                Couple chosen = null;
                do{
                    chosen = new Couple<Integer, Integer>(r.nextInt(6), r.nextInt(5));
                }while(positions.contains(chosen));
                positions.add(chosen);
            }
            for(int i=(testBookshelf.getBookshelfHeight()-1);i>=0;i--){
                for(int j=0;j<testBookshelf.getBookshelfWidth();j++){
                    if(positions.contains(new Couple<Integer, Integer>(i,j))){
                        //donePositions.add(new Couple(i,j));
                        //if(positions.contains(new Couple<Integer, Integer>(i,j))
                        testBookshelf.insertTile(j,tt);
                        positions.remove(new Couple<Integer, Integer>(i,j));
                    }else{
                        TileType othertt;
                        do{
                            othertt = TileType.randomTileType();
                        }while(othertt == tt);
                        testBookshelf.insertTile(j,othertt);    //This way I'm ensuring that there are only those 8
                                                                // positions with the type tt
                    }
                }
            }
            assertTrue(testCard.check(testBookshelf));
            testBookshelf = new Bookshelf(new Match());
        }
    }

    /**
     * This method tests the 9th common goal card in an environment where the check function
     * should return false
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard9_expectedFalse_Test(){
        testCard = new CommonGoalCard9(4, false);
        testBookshelf = new Bookshelf(new Match());

        //Check the empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //Corner cases: I want the bookshelf to have 7 (case 1) and 0 (case 2) tiles of every given type
        //Set<Couple<Integer, Integer>> positions = new HashSet<>();
        //List<Couple<Integer, Integer>> positions = new ArrayList<>();
        Map<Couple<Integer,Integer>, TileType> positions = new HashMap<>();
        //Set<Couple> donePositions = new HashSet<>();
        Random r = new Random();

        //Case 1:
        for(TileType tt: TileType.values()){
            for(int i=0;i<7;i++){
                Couple c;
                do{
                    c = new Couple<>(r.nextInt(6), r.nextInt(5));
                }while(positions.containsKey(c));
                positions.put(c, tt);
            }
        }                           //Every type may appear at most 7 times, but 6*7 = 42 and we have only 30 spots..
        for(int i=(testBookshelf.getBookshelfHeight()-1);i>=0;i--){
            for(int j=0;j<testBookshelf.getBookshelfWidth();j++){
                Couple c = new Couple(i,j);
                if(positions.containsKey(c)){
                    //donePositions.add(new Couple(i,j));
                    testBookshelf.insertTile(j, positions.get(c));
                    positions.remove(c);
                }
            }
        }
        assertFalse(testCard.check(testBookshelf));
        testBookshelf = new Bookshelf(new Match());
        positions.clear();


        //Case 2: the empty bookshelf has already been tested...

    }

    /**
     * This method tests the 10th common goal card in an environment where the check function
     * should return true
     * @author patrickpoggi, Paolo Gennaro
     */
    @Test
    public void CommonGoalCard10_expectedTrue_Test(){
        testCard = new CommonGoalCard10(4, false);
        testBookshelf = new Bookshelf(new Match());
        TileType othertt;
        Random r = new Random();

        for(TileType tt : TileType.values()){
            for(int k=0;k<5;k++){                       //Doing 5 tries for every type
                Couple<Integer, Integer> startingPos = new Couple<Integer, Integer>(r.nextInt(4), r.nextInt(3));
                for(int i= testBookshelf.getBookshelfHeight()-1;i>=0;i--){
                    for(int j=0;j< testBookshelf.getBookshelfWidth();j++){
                        if(     (i == startingPos.getA() && j == startingPos.getB()) ||
                                (i == startingPos.getA() && j == (startingPos.getB()+2)) ||
                                ((i == startingPos.getA()+1) && (j == startingPos.getB()+1)) ||
                                (i == (startingPos.getA()+2) && j == startingPos.getB()) ||
                                (i == startingPos.getA()+2 && j == (startingPos.getB()+2))
                        ){
                            testBookshelf.insertTile(j,tt);
                        }else{
                            do{
                                othertt = TileType.randomTileType();
                            }while(othertt == tt);
                            testBookshelf.insertTile(j,othertt);
                        }
                    }
                }
                assertTrue(testCard.check(testBookshelf));
                testBookshelf = new Bookshelf(new Match());
            }
        }
    }

    /**
     * This method tests the 10th common goal card in an environment where the check function
     * should return false
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard10_expectedFalse_Test(){
        testCard = new CommonGoalCard10(4, false);
        testBookshelf = new Bookshelf(new Match());
        Random r = new Random();

        //check the empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //IDEA: Build five different bookshelves: in each one we put just one tile wrong for the X formation

        //Bookshelf 1: the tile in the starting position is wrong
        for(TileType tt : TileType.values()){
            for(int k=0;k<5;k++){                       //Doing 5 tries for every type
                Couple<Integer, Integer> startingPos = new Couple<Integer, Integer>(r.nextInt(4), r.nextInt(3));
                for(int i= testBookshelf.getBookshelfHeight();i>=0;i--){
                    for(int j=0;j< testBookshelf.getBookshelfWidth();j++){
                        if(
                                (i == startingPos.getA() && j == (startingPos.getB()+2)) ||
                                ((i == startingPos.getA()+1) && (j == startingPos.getB()+1)) ||
                                (i == (startingPos.getA()+2) && j == startingPos.getB()) ||
                                (i == startingPos.getA()+2 && j == (startingPos.getB()+2))
                        ){
                            testBookshelf.insertTile(j,tt);
                        }else{
                            TileType othertt;
                            do{
                                othertt = TileType.randomTileType();
                            }while(othertt == tt);
                        }
                    }
                }
                assertFalse(testCard.check(testBookshelf));
                testBookshelf = new Bookshelf(new Match());
            }
        }

        //Bookshelf 2: the tile in the upper-right corner is wrong
        for(TileType tt : TileType.values()){
            for(int k=0;k<5;k++){                       //Doing 5 tries for every type
                Couple<Integer, Integer> startingPos = new Couple<Integer, Integer>(r.nextInt(4), r.nextInt(3));
                for(int i= testBookshelf.getBookshelfHeight();i>=0;i--){
                    for(int j=0;j< testBookshelf.getBookshelfWidth();j++){
                        if(     (i == startingPos.getA() && j == startingPos.getB()) ||

                                ((i == startingPos.getA()+1) && (j == startingPos.getB()+1)) ||
                                (i == (startingPos.getA()+2) && j == startingPos.getB()) ||
                                (i == startingPos.getA()+2 && j == (startingPos.getB()+2))
                        ){
                            testBookshelf.insertTile(j,tt);
                        }else{
                            TileType othertt;
                            do{
                                othertt = TileType.randomTileType();
                            }while(othertt == tt);
                        }
                    }
                }
                assertFalse(testCard.check(testBookshelf));
                testBookshelf = new Bookshelf(new Match());
            }
        }

        //Bookshelf 3: the tile in the middle of the X is wrong
        for(TileType tt : TileType.values()){
            for(int k=0;k<5;k++){                       //Doing 5 tries for every type
                Couple<Integer, Integer> startingPos = new Couple<Integer, Integer>(r.nextInt(4), r.nextInt(3));
                for(int i= testBookshelf.getBookshelfHeight();i>=0;i--){
                    for(int j=0;j< testBookshelf.getBookshelfWidth();j++){
                        if(     (i == startingPos.getA() && j == startingPos.getB()) ||
                                (i == startingPos.getA() && j == (startingPos.getB()+2)) ||

                                (i == (startingPos.getA()+2) && j == startingPos.getB()) ||
                                (i == startingPos.getA()+2 && j == (startingPos.getB()+2))
                        ){
                            testBookshelf.insertTile(j,tt);
                        }else{
                            TileType othertt;
                            do{
                                othertt = TileType.randomTileType();
                            }while(othertt == tt);
                        }
                    }
                }
                assertFalse(testCard.check(testBookshelf));
                testBookshelf = new Bookshelf(new Match());
            }
        }

        //Bookshelf 4: the tile in the lower-left corner is wrong
        for(TileType tt : TileType.values()){
            for(int k=0;k<5;k++){                       //Doing 5 tries for every type
                Couple<Integer, Integer> startingPos = new Couple<Integer, Integer>(r.nextInt(4), r.nextInt(3));
                for(int i= testBookshelf.getBookshelfHeight();i>=0;i--){
                    for(int j=0;j< testBookshelf.getBookshelfWidth();j++){
                        if(     (i == startingPos.getA() && j == startingPos.getB()) ||
                                (i == startingPos.getA() && j == (startingPos.getB()+2)) ||
                                ((i == startingPos.getA()+1) && (j == startingPos.getB()+1)) ||

                                (i == startingPos.getA()+2 && j == (startingPos.getB()+2))
                        ){
                            testBookshelf.insertTile(j,tt);
                        }else{
                            TileType othertt;
                            do{
                                othertt = TileType.randomTileType();
                            }while(othertt == tt);
                        }
                    }
                }
                assertFalse(testCard.check(testBookshelf));
                testBookshelf = new Bookshelf(new Match());
            }
        }

        //Bookshelf 5: the tile in the lower-right corner is wrong:
        for(TileType tt : TileType.values()){
            for(int k=0;k<5;k++){                       //Doing 5 tries for every type
                Couple<Integer, Integer> startingPos = new Couple<Integer, Integer>(r.nextInt(4), r.nextInt(3));
                for(int i= testBookshelf.getBookshelfHeight();i>=0;i--){
                    for(int j=0;j< testBookshelf.getBookshelfWidth();j++){
                        if(     (i == startingPos.getA() && j == startingPos.getB()) ||
                                (i == startingPos.getA() && j == (startingPos.getB()+2)) ||
                                ((i == startingPos.getA()+1) && (j == startingPos.getB()+1)) ||
                                (i == (startingPos.getA()+2) && j == startingPos.getB())

                        ){
                            testBookshelf.insertTile(j,tt);
                        }else{
                            TileType othertt;
                            do{
                                othertt = TileType.randomTileType();
                            }while(othertt == tt);
                        }
                    }
                }
                assertFalse(testCard.check(testBookshelf));
                testBookshelf = new Bookshelf(new Match());
            }
        }

        //Every possible false situation is a combination of one or more of these
    }

    /**
     * This method tests the 11th common goal card in an environment where the check function should return true
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard11_ExpectedTrue_test(){
        testCard = new CommonGoalCard11(4, false);
        testBookshelf = new Bookshelf(new Match());

        BookshelfTileSpot[][] tbsmat = testBookshelf.getPrivateTileMatrix();
        for(TileType tt : TileType.values()){
            tbsmat[0][0].setTile(tt);
            tbsmat[1][1].setTile(tt);
            tbsmat[2][2].setTile(tt);
            tbsmat[3][3].setTile(tt);
            tbsmat[4][4].setTile(tt);
            assertTrue(testCard.check(testBookshelf));
        }


        testBookshelf = new Bookshelf(new Match());
        tbsmat = testBookshelf.getPrivateTileMatrix();
        for(TileType tt : TileType.values()){
            tbsmat[1][0].setTile(tt);
            tbsmat[2][1].setTile(tt);
            tbsmat[3][2].setTile(tt);
            tbsmat[4][3].setTile(tt);
            tbsmat[5][4].setTile(tt);
            assertTrue(testCard.check(testBookshelf));
        }


        testBookshelf = new Bookshelf(new Match());
        tbsmat = testBookshelf.getPrivateTileMatrix();
        for(TileType tt : TileType.values()){
            tbsmat[0][4].setTile(tt);
            tbsmat[1][3].setTile(tt);
            tbsmat[2][2].setTile(tt);
            tbsmat[3][1].setTile(tt);
            tbsmat[4][0].setTile(tt);
            assertTrue(testCard.check(testBookshelf));
        }


        testBookshelf = new Bookshelf(new Match());
        tbsmat = testBookshelf.getPrivateTileMatrix();
        for(TileType tt : TileType.values()){
            tbsmat[1][4].setTile(tt);
            tbsmat[2][3].setTile(tt);
            tbsmat[3][2].setTile(tt);
            tbsmat[4][1].setTile(tt);
            tbsmat[5][0].setTile(tt);
            assertTrue(testCard.check(testBookshelf));
        }

    }

    /**
     * This method tests the 11th common goal card in an environment where the check function should return false
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard11_expectedFalse_Test(){
        testCard = new CommonGoalCard11(4, false);
        testBookshelf = new Bookshelf(new Match());
        BookshelfTileSpot[][] tbsmat = testBookshelf.getPrivateTileMatrix();

        for(TileType tt: TileType.values()){
            TileType tt2;
            do{
                tt2 = TileType.randomTileType();
            }while(tt2 == tt);
            //First diagonal -- different types
            tbsmat[0][0].setTile(tt);
            tbsmat[1][1].setTile(tt2);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][3].setTile(tt2);
            tbsmat[4][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][0].setTile(tt2);
            tbsmat[1][1].setTile(tt);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][3].setTile(tt2);
            tbsmat[4][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][0].setTile(tt2);
            tbsmat[1][1].setTile(tt2);
            tbsmat[2][2].setTile(tt);
            tbsmat[3][3].setTile(tt2);
            tbsmat[4][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][0].setTile(tt2);
            tbsmat[1][1].setTile(tt2);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][3].setTile(tt);
            tbsmat[4][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][0].setTile(tt2);
            tbsmat[1][1].setTile(tt2);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][3].setTile(tt2);
            tbsmat[4][4].setTile(tt);
            assertFalse(testCard.check(testBookshelf));

            //First diagonal -- one is empty
            tbsmat[0][0].setEmpty();
            tbsmat[1][1].setTile(tt2);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][3].setTile(tt2);
            tbsmat[4][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][0].setTile(tt2);
            tbsmat[1][1].setEmpty();
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][3].setTile(tt2);
            tbsmat[4][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][0].setTile(tt2);
            tbsmat[1][1].setTile(tt2);
            tbsmat[2][2].setEmpty();
            tbsmat[3][3].setTile(tt2);
            tbsmat[4][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][0].setTile(tt2);
            tbsmat[1][1].setTile(tt2);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][3].setEmpty();
            tbsmat[4][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][0].setTile(tt2);
            tbsmat[1][1].setTile(tt2);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][3].setTile(tt2);
            tbsmat[4][4].setEmpty();
            assertFalse(testCard.check(testBookshelf));

            //Second diagonal -- different types
            testBookshelf = new Bookshelf(new Match());
            tbsmat = testBookshelf.getPrivateTileMatrix();
            tbsmat[1][0].setTile(tt);
            tbsmat[2][1].setTile(tt2);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][3].setTile(tt2);
            tbsmat[5][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][0].setTile(tt2);
            tbsmat[2][1].setTile(tt);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][3].setTile(tt2);
            tbsmat[5][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][0].setTile(tt2);
            tbsmat[2][1].setTile(tt2);
            tbsmat[3][2].setTile(tt);
            tbsmat[4][3].setTile(tt2);
            tbsmat[5][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][0].setTile(tt2);
            tbsmat[2][1].setTile(tt2);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][3].setTile(tt);
            tbsmat[5][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][0].setTile(tt2);
            tbsmat[2][1].setTile(tt2);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][3].setTile(tt2);
            tbsmat[5][4].setTile(tt);
            assertFalse(testCard.check(testBookshelf));

            //Second diagonal -- one is empty
            tbsmat[1][0].setEmpty();
            tbsmat[2][1].setTile(tt2);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][3].setTile(tt2);
            tbsmat[5][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][0].setTile(tt2);
            tbsmat[2][1].setEmpty();
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][3].setTile(tt2);
            tbsmat[5][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][0].setTile(tt2);
            tbsmat[2][1].setTile(tt2);
            tbsmat[3][2].setEmpty();
            tbsmat[4][3].setTile(tt2);
            tbsmat[5][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][0].setTile(tt2);
            tbsmat[2][1].setTile(tt2);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][3].setEmpty();
            tbsmat[5][4].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][0].setTile(tt2);
            tbsmat[2][1].setTile(tt2);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][3].setTile(tt2);
            tbsmat[5][4].setEmpty();
            assertFalse(testCard.check(testBookshelf));

            //Third diagonal -- different types
            testBookshelf = new Bookshelf(new Match());
            tbsmat = testBookshelf.getPrivateTileMatrix();
            tbsmat[0][4].setTile(tt);
            tbsmat[1][3].setTile(tt2);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][1].setTile(tt2);
            tbsmat[4][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][4].setTile(tt2);
            tbsmat[1][3].setTile(tt);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][1].setTile(tt2);
            tbsmat[4][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][4].setTile(tt2);
            tbsmat[1][3].setTile(tt2);
            tbsmat[2][2].setTile(tt);
            tbsmat[3][1].setTile(tt2);
            tbsmat[4][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][4].setTile(tt2);
            tbsmat[1][3].setTile(tt2);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][1].setTile(tt);
            tbsmat[4][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][4].setTile(tt2);
            tbsmat[1][3].setTile(tt2);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][1].setTile(tt2);
            tbsmat[4][0].setTile(tt);
            assertFalse(testCard.check(testBookshelf));

            //Third diagonal -- one is empty
            tbsmat[0][4].setEmpty();
            tbsmat[1][3].setTile(tt2);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][1].setTile(tt2);
            tbsmat[4][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][4].setTile(tt2);
            tbsmat[1][3].setEmpty();
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][1].setTile(tt2);
            tbsmat[4][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][4].setTile(tt2);
            tbsmat[1][3].setTile(tt2);
            tbsmat[2][2].setEmpty();
            tbsmat[3][1].setTile(tt2);
            tbsmat[4][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][4].setTile(tt2);
            tbsmat[1][3].setTile(tt2);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][1].setEmpty();
            tbsmat[4][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[0][4].setTile(tt2);
            tbsmat[1][3].setTile(tt2);
            tbsmat[2][2].setTile(tt2);
            tbsmat[3][1].setTile(tt2);
            tbsmat[4][0].setEmpty();
            assertFalse(testCard.check(testBookshelf));

            //Fourth diagonal -- different types
            testBookshelf = new Bookshelf(new Match());
            tbsmat = testBookshelf.getPrivateTileMatrix();
            tbsmat[1][4].setTile(tt);
            tbsmat[2][3].setTile(tt2);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][1].setTile(tt2);
            tbsmat[5][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][4].setTile(tt2);
            tbsmat[2][3].setTile(tt);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][1].setTile(tt2);
            tbsmat[5][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][4].setTile(tt2);
            tbsmat[2][3].setTile(tt2);
            tbsmat[3][2].setTile(tt);
            tbsmat[4][1].setTile(tt2);
            tbsmat[5][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][4].setTile(tt2);
            tbsmat[2][3].setTile(tt2);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][1].setTile(tt);
            tbsmat[5][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][4].setTile(tt2);
            tbsmat[2][3].setTile(tt2);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][1].setTile(tt2);
            tbsmat[5][0].setTile(tt);
            assertFalse(testCard.check(testBookshelf));

            //Fourth diagonal -- one is empty
            tbsmat[1][4].setEmpty();
            tbsmat[2][3].setTile(tt2);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][1].setTile(tt2);
            tbsmat[5][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][4].setTile(tt2);
            tbsmat[2][3].setEmpty();
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][1].setTile(tt2);
            tbsmat[5][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][4].setTile(tt2);
            tbsmat[2][3].setTile(tt2);
            tbsmat[3][2].setEmpty();
            tbsmat[4][1].setTile(tt2);
            tbsmat[5][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][4].setTile(tt2);
            tbsmat[2][3].setTile(tt2);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][1].setEmpty();
            tbsmat[5][0].setTile(tt2);
            assertFalse(testCard.check(testBookshelf));

            tbsmat[1][4].setTile(tt2);
            tbsmat[2][3].setTile(tt2);
            tbsmat[3][2].setTile(tt2);
            tbsmat[4][1].setTile(tt2);
            tbsmat[5][0].setEmpty();
            assertFalse(testCard.check(testBookshelf));
        }
    }

    /**
     * This method tests the CommonGoalCard12 in an environment where the check function should return true
     * @author Marta Giliberto
     */
    @Test
    public void CommonGoalCard12_expectedTrue_Test1(){
        Match match=new Match();
        CommonGoalCard card12 = new CommonGoalCard12(4, false);
        Bookshelf bookshelf= new Bookshelf(match);
        BookshelfTileSpot[][] matrix = bookshelf.getTileMatrix();

        bookshelf.insertTile(0, TileType.CATS);
        bookshelf.insertTile(0, TileType.CATS);
        bookshelf.insertTile(0, TileType.CATS);
        bookshelf.insertTile(0, TileType.CATS);
        bookshelf.insertTile(0, TileType.CATS);
        bookshelf.insertTile(0, TileType.CATS);

        bookshelf.insertTile(1, TileType.CATS);
        bookshelf.insertTile(1, TileType.CATS);
        bookshelf.insertTile(1, TileType.CATS);
        bookshelf.insertTile(1, TileType.CATS);
        bookshelf.insertTile(1, TileType.CATS);

        bookshelf.insertTile(2, TileType.GAMES);
        bookshelf.insertTile(2, TileType.GAMES);
        bookshelf.insertTile(2, TileType.GAMES);
        bookshelf.insertTile(2, TileType.GAMES);

        bookshelf.insertTile(3, TileType.PLANTS);
        bookshelf.insertTile(3, TileType.PLANTS);
        bookshelf.insertTile(3, TileType.PLANTS);

        bookshelf.insertTile(4, TileType.PLANTS);
        bookshelf.insertTile(4, TileType.PLANTS);

        assertTrue(card12.check(bookshelf));
    }

    /**
     * This method tests the CommonGoalCard12 in an environment where the check function should return true
     * @author Marta Giliberto
     */
    @Test
    public void CommonGoalCard12_expectedTrue_Test2(){
        Match match=new Match();
        CommonGoalCard card12 = new CommonGoalCard12(4, false);
        Bookshelf bookshelf= new Bookshelf(match);
        BookshelfTileSpot[][] matrix = bookshelf.getTileMatrix();

        bookshelf.insertTile(0, TileType.CATS);
        bookshelf.insertTile(0, TileType.CATS);
        bookshelf.insertTile(0, TileType.CATS);
        bookshelf.insertTile(0, TileType.CATS);
        bookshelf.insertTile(0, TileType.CATS);

        bookshelf.insertTile(1, TileType.CATS);
        bookshelf.insertTile(1, TileType.CATS);
        bookshelf.insertTile(1, TileType.CATS);
        bookshelf.insertTile(1, TileType.CATS);

        bookshelf.insertTile(2, TileType.GAMES);
        bookshelf.insertTile(2, TileType.GAMES);
        bookshelf.insertTile(2, TileType.GAMES);

        bookshelf.insertTile(3, TileType.PLANTS);
        bookshelf.insertTile(3, TileType.PLANTS);

        bookshelf.insertTile(4, TileType.PLANTS);

        assertTrue(card12.check(bookshelf));
    }

    /**
     * This method tests the CommonGoalCard12 in an environment where the check function should return true
     * @author Marta Giliberto
     */
    @Test
    public void CommonGoalCard12_expectedTrue_Test3(){
        Match match=new Match();
        CommonGoalCard card12 = new CommonGoalCard12(4, false);
        Bookshelf bookshelf= new Bookshelf(match);
        BookshelfTileSpot[][] matrix = bookshelf.getTileMatrix();

        bookshelf.insertTile(4, TileType.CATS);
        bookshelf.insertTile(4, TileType.CATS);
        bookshelf.insertTile(4, TileType.CATS);
        bookshelf.insertTile(4, TileType.CATS);
        bookshelf.insertTile(4, TileType.CATS);
        bookshelf.insertTile(4, TileType.CATS);

        bookshelf.insertTile(3, TileType.CATS);
        bookshelf.insertTile(3, TileType.CATS);
        bookshelf.insertTile(3, TileType.CATS);
        bookshelf.insertTile(3, TileType.CATS);
        bookshelf.insertTile(3, TileType.CATS);

        bookshelf.insertTile(2, TileType.GAMES);
        bookshelf.insertTile(2, TileType.GAMES);
        bookshelf.insertTile(2, TileType.GAMES);
        bookshelf.insertTile(2, TileType.GAMES);

        bookshelf.insertTile(1, TileType.PLANTS);
        bookshelf.insertTile(1, TileType.PLANTS);
        bookshelf.insertTile(1, TileType.PLANTS);

        bookshelf.insertTile(0, TileType.PLANTS);
        bookshelf.insertTile(0, TileType.PLANTS);

        assertTrue(card12.check(bookshelf));
    }

    /**
     * This method tests the CommonGoalCard12 in an environment where the check function should return true
     * @author Marta Giliberto
     */
    @Test
    public void CommonGoalCard12_expectedTrue_Test4(){
        Match match=new Match();
        CommonGoalCard card12 = new CommonGoalCard12(4, false);
        Bookshelf bookshelf= new Bookshelf(match);
        BookshelfTileSpot[][] matrix = bookshelf.getTileMatrix();

        bookshelf.insertTile(4, TileType.CATS);
        bookshelf.insertTile(4, TileType.CATS);
        bookshelf.insertTile(4, TileType.CATS);
        bookshelf.insertTile(4, TileType.CATS);
        bookshelf.insertTile(4, TileType.CATS);

        bookshelf.insertTile(3, TileType.CATS);
        bookshelf.insertTile(3, TileType.CATS);
        bookshelf.insertTile(3, TileType.CATS);
        bookshelf.insertTile(3, TileType.CATS);

        bookshelf.insertTile(2, TileType.GAMES);
        bookshelf.insertTile(2, TileType.GAMES);
        bookshelf.insertTile(2, TileType.GAMES);

        bookshelf.insertTile(1, TileType.PLANTS);
        bookshelf.insertTile(1, TileType.PLANTS);

        bookshelf.insertTile(0, TileType.PLANTS);

        assertTrue(card12.check(bookshelf));
    }
    /**
     * This method tests the CommonGoalCard12 in an environment where the check function should return false
     * @author Marta Giliberto
     */
    @Test
    public void CommonGoalCard12_expectedFalse_Test(){
        Match match=new Match();
        CommonGoalCard card12 = new CommonGoalCard12(4, false);
        Bookshelf bookshelf= new Bookshelf(match);
        BookshelfTileSpot[][] matrix = bookshelf.getTileMatrix();

        bookshelf.insertTile(0, TileType.CATS);
        bookshelf.insertTile(0, TileType.CATS);
        bookshelf.insertTile(0, TileType.CATS);


        bookshelf.insertTile(1, TileType.CATS);
        bookshelf.insertTile(1, TileType.CATS);
        bookshelf.insertTile(1, TileType.CATS);
        bookshelf.insertTile(1, TileType.CATS);
        bookshelf.insertTile(1, TileType.CATS);

        bookshelf.insertTile(2, TileType.GAMES);
        bookshelf.insertTile(2, TileType.GAMES);
        bookshelf.insertTile(2, TileType.GAMES);
        bookshelf.insertTile(2, TileType.GAMES);

        bookshelf.insertTile(3, TileType.PLANTS);
        bookshelf.insertTile(3, TileType.PLANTS);
        bookshelf.insertTile(3, TileType.PLANTS);

        bookshelf.insertTile(4, TileType.PLANTS);
        bookshelf.insertTile(3, TileType.PLANTS);

        assertFalse(card12.check(bookshelf));
    }
}




