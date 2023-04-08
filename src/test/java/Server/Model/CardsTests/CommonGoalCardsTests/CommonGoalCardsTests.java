package Server.Model.CardsTests.CommonGoalCardsTests;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.CommonGoalCards.CommonGoalCard1;
import Server.Model.Cards.CommonGoalCards.CommonGoalCard2;
import Server.Model.Cards.CommonGoalCards.CommonGoalCard7;
import Server.Model.Cards.CommonGoalCards.CommonGoalCard8;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;
//import org.junit.jupiter.api.*;
import jdk.jshell.execution.Util;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * This class represents the tests of the common goal cards
 * @author due2, patrickpoggi
 */
public class CommonGoalCardsTests {
    CommonGoalCard testCard;
    Bookshelf testBookshelf;
    /**
     * This method tests the first common goal card
     * @author due2
     */
    @Test
    public void CommonGoalCard1Test(){
        testCard = new CommonGoalCard1();
        testBookshelf = new Bookshelf();
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
                while(i>0 && precedentTile.equals(currentTile)){
                    currentTile.setTile(TileType.randomTileType());
                }
                testBookshelf.insertTile(j,currentTile.getTileType());
                precedentTile = currentTile;
            }
        }
        assertFalse(testCard.check(testBookshelf));

        //test with a bookshelf with only 1 2x2 submatrix of the same tile type
        testBookshelf = new Bookshelf();

        for (int i = 5; i >=0; i--) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                while(i<5 && precedentTile.equals(currentTile)){
                    currentTile.setTile(TileType.randomTileType());
                }
                testBookshelf.insertTile(j,currentTile.getTileType());
                precedentTile = currentTile;
            }
            if((!flag && i>=1 && Math.random()>=0.5)||!flag && i==1){
                flag = true;
                index = (int) (Math.random() * 3);
                matchingTile.setTile(TileType.randomTileType());
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 5; j++) {
                        if (j == index || j == index + 1) {
                            testBookshelf.insertTile(j, matchingTile.getTileType());
                            precedentTile = matchingTile;
                        } else {
                            currentTile.setTile(TileType.randomTileType());
                            while (precedentTile.equals(currentTile)) {
                                currentTile.setTile(TileType.randomTileType());
                            }
                            testBookshelf.insertTile(j, currentTile.getTileType());
                            precedentTile = currentTile;
                        }

                    }
                    i--;
                }
            }

        }
        assertFalse(testCard.check(testBookshelf));

        //test with a bookshelf with 2 2x2 submatrix of the same tile type
        testBookshelf = new Bookshelf();
        flag = false;
        index=-1;
        for (int i = 5; i >=0; i--) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                while(precedentTile.equals(currentTile)){
                    currentTile.setTile(TileType.randomTileType());
                }
                testBookshelf.insertTile(j,currentTile.getTileType());
                precedentTile = currentTile;
            }
            if((!flag && i>=1 && Math.random()>=0.5)||!flag && i==1||index==-1 && i==3){
                if(index!=-1)
                    flag = true;
                index = (int) (Math.random() * 3);
                matchingTile.setTile(TileType.randomTileType());
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 5; j++) {
                        if (j == index || j == index + 1) {
                            testBookshelf.insertTile(j, matchingTile.getTileType());
                            precedentTile = matchingTile;
                        } else {
                            currentTile.setTile(TileType.randomTileType());
                            while (precedentTile.equals(currentTile)) {
                                currentTile.setTile(TileType.randomTileType());
                            }
                            testBookshelf.insertTile(j, currentTile.getTileType());
                            precedentTile = currentTile;
                        }

                    }
                    i--;
                }
            }

        }
        assertTrue(testCard.check(testBookshelf));
        //test with a bookshelf with at least 2 2x2 submatrix of the same tile type
        testBookshelf = new Bookshelf();
        flag = false;
        index=-1;

        for (int i = 5; i >=0; i--) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                while(precedentTile.equals(currentTile)){
                    currentTile.setTile(TileType.randomTileType());
                }
                testBookshelf.insertTile(j,currentTile.getTileType());
                precedentTile = currentTile;
            }
            if((i>=1 && Math.random()>=0.5)||!flag && i==1||index==-1 && i==3){
                if(index!=-1)
                    flag = true;
                index = (int) (Math.random() * 3);
                matchingTile.setTile(TileType.randomTileType());
                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < 5; j++) {
                        if (j == index || j == index + 1) {
                            testBookshelf.insertTile(j, matchingTile.getTileType());
                            precedentTile = matchingTile;
                        } else {
                            currentTile.setTile(TileType.randomTileType());
                            while (precedentTile.equals(currentTile)) {
                                currentTile.setTile(TileType.randomTileType());
                            }
                            testBookshelf.insertTile(j, currentTile.getTileType());
                            precedentTile = currentTile;
                        }

                    }
                    i--;
                }
            }
        }
        assertTrue(testCard.check(testBookshelf));
    }

    /**
     * This method tests the second common goal card
     * @author due2
     */
    @Test
    public void CommonGoalCard2Test() {
        testCard = new CommonGoalCard2();
        testBookshelf = new Bookshelf();
        boolean[] flag = {false,false,false,false,false,false};
        int index1,index2;
        BookshelfTileSpot[] precedentTile = new BookshelfTileSpot[6];
        BookshelfTileSpot currentTile = new BookshelfTileSpot();

        //test with an empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //test with a non empty bookshelf without a row of different tile types

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                if(!flag[j] && i==5){
                    testBookshelf.insertTile(j,precedentTile[j].getTileType());
                    flag[j]=true;
                }else{
                    if(precedentTile[j].equals(currentTile))
                        flag[j]=true;
                    precedentTile[j] = currentTile;
                    testBookshelf.insertTile(j, currentTile.getTileType());
                }
            }
        }
        assertFalse(testCard.check(testBookshelf));
        //test with a bookshelf with only one row of different tile types
        testBookshelf = new Bookshelf();
        flag = new boolean[]{false,false,false,false,false,false};
        index1=(int)(Math.random()*4);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                if(!flag[j] && i==5 && j!=index1){
                    testBookshelf.insertTile(j,precedentTile[j].getTileType());
                    flag[j]=true;
                }else{
                    if(precedentTile[j].equals(currentTile) && j!=index1)
                        flag[j]=true;
                    precedentTile[j] = currentTile;
                    if(j!=index1) {
                        testBookshelf.insertTile(j, currentTile.getTileType());
                    }else{
                        testBookshelf.insertTile(j, TileType.values()[i]);
                    }
                }
            }
        }
        assertFalse(testCard.check(testBookshelf));

        //test with a bookshelf with two rows of different tile types
        testBookshelf = new Bookshelf();
        flag = new boolean[]{false,false,false,false,false,false};
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
                    if(precedentTile[j].equals(currentTile) && j!=index1 && j!=index2)
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
     * This method tests the 7th common goal card in an environment where the check function
     * should return true
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard7_expectedTrue_Test(){
        testCard = new CommonGoalCard7();
        testBookshelf = new Bookshelf();
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

        //Bookshelf is now loaded, let's assert
        assertTrue(testCard.check(testBookshelf));
    }

    /**
     * This method tests the 7th common goal card in an environment where the check function
     * should return false
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard7_expectedFalse_Test(){
        testCard = new CommonGoalCard7();
        testBookshelf = new Bookshelf();
        TileType rtt = TileType.randomTileType();
        Random r = new Random();

        //CASE 0: The bookshelf is empty
        //Bookshelf empty => not achieved the common goal
        assertFalse(testCard.check(testBookshelf));

        //FIRST CASE: 1st row empty, second row has tiles of the same kind,
        // others have tiles of different kinds
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the last row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the 2nd to last row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the 3rd to last row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the 4th to last row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }
        assertFalse(testCard.check(testBookshelf));

        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting in the 2nd row
            testBookshelf.insertTile(j,rtt);                //every tile same type
        }
        assertFalse(testCard.check(testBookshelf));

        //SECOND CASE: 6 rows full, the first three with all equal tiles,
        // but the last three have all different tiles
        testBookshelf = new Bookshelf();
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the last row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }
        assertFalse(testCard.check(testBookshelf));

        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the 2nd to last row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }
        assertFalse(testCard.check(testBookshelf));

        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the 3rd to last row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting in the 4th to last row
            testBookshelf.insertTile(j,rtt);                //every tile same type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting in the 2nd row
            testBookshelf.insertTile(j,rtt);                //every tile same type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting in the 1st row
            testBookshelf.insertTile(j,rtt);                //every tile same type
        }
        assertFalse(testCard.check(testBookshelf));

        //THIRD CASE: Every row has more than three different kinds
        testBookshelf = new Bookshelf();
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the last row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the 2nd to last row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the 3rd to last row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the 4th to last row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the 2nd row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting the 1st row
            testBookshelf.insertTile(j,TileType.values()[j]); //Every col. will have a different type
        }
        assertFalse(testCard.check(testBookshelf));


        //FOURTH CASE: Every row has at least a blank, but all the tiles are equal
        testBookshelf = new Bookshelf();
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting in the 2nd row
            int c = r.nextInt(5);
            if(j!= c) {testBookshelf.insertTile(j,rtt);}                //every tile same type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting in the 2nd row
            int c = r.nextInt(5);
            if(j!= c) {testBookshelf.insertTile(j,rtt);}                //every tile same type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting in the 2nd row
            int c = r.nextInt(5);
            if(j!= c) {testBookshelf.insertTile(j,rtt);}                //every tile same type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting in the 2nd row
            int c = r.nextInt(5);
            int c1;
            do{
                c1 = r.nextInt(5);

            }while (c == c1);
            if(j!= c && j != c1) {testBookshelf.insertTile(j,rtt);} //every tile same type
        }
        assertFalse(testCard.check(testBookshelf));
        for(int j=0;j<testBookshelf.getBookshelfWidth();j++){ //inserting in the 2nd row
            int c = r.nextInt(5);
            if(j!= c) {testBookshelf.insertTile(j,rtt);}                //every tile same type
        }
        assertFalse(testCard.check(testBookshelf));
    }

    @Test
    public void CommonGoalCard8_expectedTrue_Test(){
        testCard = new CommonGoalCard8();
        testBookshelf = new Bookshelf();

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

    @Test
    public void CommonGoalCard8_expectedFalse_Test(){

    }

}
