package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;
import org.junit.jupiter.api.*;

/**
 * This class represents the tests of the common goal cards
 * @author due2
 */
public class CommonGoalCardsTests {
    CommonGoalCard TestCard;
    Bookshelf TestBookshelf;
    /**
     * This method tests the first common goal card
     * @author due2
     */
    @Test
    void CommonGoalCard1Test() {
        TestCard = new CommonGoalCard1();
        TestBookshelf = new Bookshelf();
        boolean flag = false;
        int index;
        BookshelfTileSpot precedentTile = null;
        BookshelfTileSpot currentTile = new BookshelfTileSpot();
        BookshelfTileSpot matchingTile = new BookshelfTileSpot();
        //test with an empty bookshelf
        Assertions.assertFalse(TestCard.check(TestBookshelf));

        //test with a non-empty bookshelf without 2x2 submatrix of the same tile type
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTileType(TileType.randomTileType());
                while(i>0 && precedentTile.equals(currentTile)){
                    currentTile.setTileType(TileType.randomTileType());
                }
                TestBookshelf.insertTile(j,currentTile.getTileType());
                precedentTile = currentTile;
            }
        }
        Assertions.assertFalse(TestCard.check(TestBookshelf));

        //test with a bookshelf with only 1 2x2 submatrix of the same tile type
        TestBookshelf = new Bookshelf();

        for (int i = 5; i >=0; i--) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTileType(TileType.randomTileType());
                while(i<5 && precedentTile.equals(currentTile)){
                    currentTile.setTileType(TileType.randomTileType());
                }
                TestBookshelf.insertTile(j,currentTile.getTileType());
                precedentTile = currentTile;
            }
            if((!flag && i>=1 && Math.random()>=0.5)||!flag && i==1){
                flag = true;
                index = (int) (Math.random() * 3);
                matchingTile.setTileType(TileType.randomTileType());
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 5; j++) {
                        if (j == index || j == index + 1) {
                            TestBookshelf.insertTile(j, matchingTile.getTileType());
                            precedentTile = matchingTile;
                        } else {
                            currentTile.setTileType(TileType.randomTileType());
                            while (precedentTile.equals(currentTile)) {
                                currentTile.setTileType(TileType.randomTileType());
                            }
                            TestBookshelf.insertTile(j, currentTile.getTileType());
                            precedentTile = currentTile;
                        }

                    }
                    i--;
                }
            }

        }
        Assertions.assertFalse(TestCard.check(TestBookshelf));

        //test with a bookshelf with 2 2x2 submatrix of the same tile type
        TestBookshelf = new Bookshelf();
        flag = false;
        index=-1;
        for (int i = 5; i >=0; i--) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTileType(TileType.randomTileType());
                while(precedentTile.equals(currentTile)){
                    currentTile.setTileType(TileType.randomTileType());
                }
                TestBookshelf.insertTile(j,currentTile.getTileType());
                precedentTile = currentTile;
            }
            if((!flag && i>=1 && Math.random()>=0.5)||!flag && i==1||index==-1 && i==3){
                if(index!=-1)
                    flag = true;
                index = (int) (Math.random() * 3);
                matchingTile.setTileType(TileType.randomTileType());
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 5; j++) {
                        if (j == index || j == index + 1) {
                            TestBookshelf.insertTile(j, matchingTile.getTileType());
                            precedentTile = matchingTile;
                        } else {
                            currentTile.setTileType(TileType.randomTileType());
                            while (precedentTile.equals(currentTile)) {
                                currentTile.setTileType(TileType.randomTileType());
                            }
                            TestBookshelf.insertTile(j, currentTile.getTileType());
                            precedentTile = currentTile;
                        }

                    }
                    i--;
                }
            }

        }
        Assertions.assertTrue(TestCard.check(TestBookshelf));
        //test with a bookshelf with at least 2 2x2 submatrix of the same tile type
        TestBookshelf = new Bookshelf();
        flag = false;
        index=-1;

        for (int i = 5; i >=0; i--) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTileType(TileType.randomTileType());
                while(precedentTile.equals(currentTile)){
                    currentTile.setTileType(TileType.randomTileType());
                }
                TestBookshelf.insertTile(j,currentTile.getTileType());
                precedentTile = currentTile;
            }
            if((i>=1 && Math.random()>=0.5)||!flag && i==1||index==-1 && i==3){
                if(index!=-1)
                    flag = true;
                index = (int) (Math.random() * 3);
                matchingTile.setTileType(TileType.randomTileType());
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 5; j++) {
                        if (j == index || j == index + 1) {
                            TestBookshelf.insertTile(j, matchingTile.getTileType());
                            precedentTile = matchingTile;
                        } else {
                            currentTile.setTileType(TileType.randomTileType());
                            while (precedentTile.equals(currentTile)) {
                                currentTile.setTileType(TileType.randomTileType());
                            }
                            TestBookshelf.insertTile(j, currentTile.getTileType());
                            precedentTile = currentTile;
                        }

                    }
                    i--;
                }
            }
        }
        Assertions.assertTrue(TestCard.check(TestBookshelf));
    }

    /**
     * This method tests the second common goal card
     * @author due2
     */
    @Test
    void CommonGoalCard2Test() {
        TestCard = new CommonGoalCard2();
        TestBookshelf = new Bookshelf();
        boolean[] flag = {false,false,false,false,false,false};
        int index1,index2;
        BookshelfTileSpot[] precedentTile = new BookshelfTileSpot[6];
        BookshelfTileSpot currentTile = new BookshelfTileSpot();

        //test with an empty bookshelf
        Assertions.assertFalse(TestCard.check(TestBookshelf));

        //test with a non empty bookshelf without a row of different tile types

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTileType(TileType.randomTileType());
                if(!flag[j] && i==5){
                    TestBookshelf.insertTile(j,precedentTile[j].getTileType());
                    flag[j]=true;
                }else{
                    if(precedentTile[j].equals(currentTile))
                        flag[j]=true;
                    precedentTile[j] = currentTile;
                    TestBookshelf.insertTile(j, currentTile.getTileType());
                }
            }
        }
        Assertions.assertFalse(TestCard.check(TestBookshelf));
        //test with a bookshelf with only one row of different tile types
        TestBookshelf = new Bookshelf();
        flag = new boolean[]{false,false,false,false,false,false};
        index1=(int)(Math.random()*4);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTileType(TileType.randomTileType());
                if(!flag[j] && i==5 && j!=index1){
                    TestBookshelf.insertTile(j,precedentTile[j].getTileType());
                    flag[j]=true;
                }else{
                    if(precedentTile[j].equals(currentTile) && j!=index1)
                        flag[j]=true;
                    precedentTile[j] = currentTile;
                    if(j!=index1) {
                        TestBookshelf.insertTile(j, currentTile.getTileType());
                    }else{
                        TestBookshelf.insertTile(j, TileType.values()[i]);
                    }
                }
            }
        }
        Assertions.assertFalse(TestCard.check(TestBookshelf));

        //test with a bookshelf with two rows of different tile types
        TestBookshelf = new Bookshelf();
        flag = new boolean[]{false,false,false,false,false,false};
        index1=(int)(Math.random()*4);
        index2=(int)(Math.random()*4);
        while(index1==index2)
            index2=(int)(Math.random()*4);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTileType(TileType.randomTileType());
                if(!flag[j] && i==5 && j!=index1 && j!=index2){
                    TestBookshelf.insertTile(j,precedentTile[j].getTileType());
                    flag[j]=true;
                }else{
                    if(precedentTile[j].equals(currentTile) && j!=index1 && j!=index2)
                        flag[j]=true;
                    precedentTile[j] = currentTile;
                    if(j!=index1 && j!=index2) {
                        TestBookshelf.insertTile(j, currentTile.getTileType());
                    }else{
                        TestBookshelf.insertTile(j, TileType.values()[i]);
                    }
                }
            }
        }
        Assertions.assertTrue(TestCard.check(TestBookshelf));



    }



}
