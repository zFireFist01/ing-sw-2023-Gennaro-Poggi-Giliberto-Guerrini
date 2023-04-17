package Server.Model.CardsTests.CommonGoalCardsTests;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.CommonGoalCards.*;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

import Utils.MathUtils.*;
//import org.junit.jupiter.api.*;
import jdk.jshell.execution.Util;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.*;

import static org.junit.Assert.*;

/**
 * This class represents the tests of the common goal cards
 * @author due2, patrickpoggi
 */
public class CommonGoalCardsTests {


    CommonGoalCard testCard;
    Bookshelf testBookshelf;

    /**
     * This method tests the 1st common goal card in an environment where the check function
     * should return false
     * @author due2
     */
    @Test
    public void CommonGoalCard1_expectedFalse_Test() {
        testCard = new CommonGoalCard1(4, false);
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
                while (i > 0 && precedentTile.equals(currentTile)) {
                    currentTile.setTile(TileType.randomTileType());
                }
                testBookshelf.insertTile(j, currentTile.getTileType());
                precedentTile = currentTile;
            }
        }
        assertFalse(testCard.check(testBookshelf));

        //test with a bookshelf with only 1 2x2 submatrix of the same tile type
        testBookshelf = new Bookshelf();

        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                while (i < 5 && precedentTile.equals(currentTile)) {
                    currentTile.setTile(TileType.randomTileType());
                }
                testBookshelf.insertTile(j, currentTile.getTileType());
                precedentTile = currentTile;
            }
            if ((!flag && i >= 1 && Math.random() >= 0.5) || !flag && i == 1) {
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
    }
    /**
     * This method tests the 1st common goal card in an environment where the check function
     * should return true
     * @author due2
     **/
    @Test
    public void CommonGoalCard1_expectedTrue_Test(){
        testCard = new CommonGoalCard1(4, false);
        testBookshelf = new Bookshelf();
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
     * This method tests the 2nd common goal card in an environment where the check function
     * should return false
     * @author due2
     */
    @Test
    public void CommonGoalCard2_expectedFalse_Test() {
        testCard = new CommonGoalCard2(4, false);
        testBookshelf = new Bookshelf();
        boolean[] flag = {false, false, false, false, false, false};
        int index1, index2;
        BookshelfTileSpot[] precedentTile = new BookshelfTileSpot[6];
        BookshelfTileSpot currentTile = new BookshelfTileSpot();

        //test with an empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //test with a non empty bookshelf without a row of different tile types

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                if (!flag[j] && i == 5) {
                    testBookshelf.insertTile(j, precedentTile[j].getTileType());
                    flag[j] = true;
                } else {
                    if (precedentTile[j].equals(currentTile))
                        flag[j] = true;
                    precedentTile[j] = currentTile;
                    testBookshelf.insertTile(j, currentTile.getTileType());
                }
            }
        }
        assertFalse(testCard.check(testBookshelf));
        //test with a bookshelf with only one row of different tile types
        testBookshelf = new Bookshelf();
        flag = new boolean[]{false, false, false, false, false, false};
        index1 = (int) (Math.random() * 4);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                currentTile.setTile(TileType.randomTileType());
                if (!flag[j] && i == 5 && j != index1) {
                    testBookshelf.insertTile(j, precedentTile[j].getTileType());
                    flag[j] = true;
                } else {
                    if (precedentTile[j].equals(currentTile) && j != index1)
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
        testBookshelf = new Bookshelf();
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
     * This method tests the 3rd common goal card in an environment where the check function
     * should return false
     * @author due2
     */
    @Test
    public void CommonGoalCard3_expectedFalse_Test(){
        testCard = new CommonGoalCard3(4, false);
        testBookshelf = new Bookshelf();
        int[] j_index = {0,0,0};
        int[] i_index = {0,0,0};
        boolean flag1 = false;
        boolean flag2 = false;
        int count,times;
        BookshelfTileSpot testTile = new BookshelfTileSpot();
        BookshelfTileSpot[] precedentTile = new BookshelfTileSpot[5];
        BookshelfTileSpot[] currentTile = {new BookshelfTileSpot(),new BookshelfTileSpot(),new BookshelfTileSpot(),new BookshelfTileSpot(),new BookshelfTileSpot()};


        //test with an empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //test with a non empty bookshelf without a coloumn of four tiles of same type

        for(int i=0;i<5;i++){
            currentTile[i]=new BookshelfTileSpot(TileType.randomTileType());
        }

        for(int i=0;i<5;i++){

            for(int j=0;j<5;j++){
                if(i==3){
                    testTile.setTile(TileType.randomTileType());
                    while(testTile.getTileType()==precedentTile[j].getTileType()){
                        testTile.setTile(TileType.randomTileType());
                    }
                    testBookshelf.insertTile(j, testTile.getTileType());
                    testBookshelf.insertTile(j, precedentTile[j].getTileType());
                }else {
                    testBookshelf.insertTile(j, currentTile[j].getTileType());
                    precedentTile[j].setTile(currentTile[j].getTileType());
                    currentTile[j].setTile(TileType.randomTileType());
                }
            }
        }
        assertFalse(testCard.check(testBookshelf));

        //test with a bookshelf with at least one coloumn of four tiles of same type bune never more than three
        testBookshelf = new Bookshelf();

        times=(int)(Math.random()*2)+1; //number of times to change the coloumn
        testTile.setTile(TileType.randomTileType());

        for(int i=0; i<times ;i++){
            i_index[i]=(int)(Math.random()*2);
        }

        for(int i=0 ; i<times;i++) {
            switch (i) {
                case 1:
                    j_index[0] = (int) (Math.random() * 3);
                    j_index[1] = j_index[0];
                    j_index[2] = j_index[0];
                case 2:
                    while (j_index[1] == j_index[0])
                        j_index[1] = (int) (Math.random() * 3);
                    j_index[2] = j_index[1];
                case 3:
                    while (j_index[2] == j_index[1] || j_index[2] == j_index[0])
                        j_index[2] = (int) (Math.random() * 3);
            }
        }
        count=0;


        for(int i=0;i<5;i++){
            currentTile[i]=new BookshelfTileSpot(TileType.randomTileType());
        }

        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++) {

                for(int k=0;k<times;k++){
                    if(i==i_index[k] && j==j_index[k]){
                        flag1=true;
                        break;
                    }
                }
                for(int k=0;k<times;k++){
                    if((i<i_index[k] || i>i_index[k]+3 )&& j==j_index[k]){
                        flag2=true;
                        break;
                    }
                }

                if(count<3 && flag1) {
                    testTile.setTile(TileType.randomTileType());
                    testBookshelf.insertTile(j, testTile.getTileType());
                    testBookshelf.insertTile(j, testTile.getTileType());
                    testBookshelf.insertTile(j, testTile.getTileType());
                    testBookshelf.insertTile(j, testTile.getTileType());
                    flag1=false;
                    count++;
                }else if(flag2 ){
                    testBookshelf.insertTile(j, currentTile[j].getTileType());
                    precedentTile[j].setTile(currentTile[j].getTileType());
                    do {
                        currentTile[j].setTile(TileType.randomTileType());
                    }while(currentTile[j].getTileType()==precedentTile[j].getTileType());
                    flag2=false;
                }
            }
        }
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
        testBookshelf = new Bookshelf();
        int[] j_index = {0,0,0,0};
        int[] i_index = {0,0,0,0};
        int count;
        BookshelfTileSpot testTile = new BookshelfTileSpot();
        BookshelfTileSpot[] precedentTile = new BookshelfTileSpot[5];
        BookshelfTileSpot[] currentTile = {new BookshelfTileSpot(),new BookshelfTileSpot(),new BookshelfTileSpot(),new BookshelfTileSpot(),new BookshelfTileSpot()};

        testTile.setTile(TileType.randomTileType());

        for(int i=0; i<4 ;i++){
            i_index[i]=(int)(Math.random()*2);
        }

        for(int i=0;i<4;i++) {
            switch (i) {
                case 1:
                    j_index[0] = (int) (Math.random() * 3);
                    j_index[1] = j_index[0];
                    j_index[2] = j_index[0];
                case 2:
                    while (j_index[1] == j_index[0])
                        j_index[1] = (int) (Math.random() * 3);
                    j_index[2] = j_index[1];
                case 3:
                    while (j_index[2] == j_index[1] || j_index[2] == j_index[0])
                        j_index[2] = (int) (Math.random() * 3);
                case 4:
                    while (j_index[3] == j_index[2] || j_index[3] == j_index[1] || j_index[3] == j_index[0])
                        j_index[3] = (int) (Math.random() * 3);
            }
        }
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
                    precedentTile[j].setTile(currentTile[j].getTileType());
                    do {
                        currentTile[j].setTile(TileType.randomTileType());
                    }while(currentTile[j].getTileType()==precedentTile[j].getTileType());
                }
            }
        }
        assertTrue(testCard.check(testBookshelf));

        //tests with 4 group of 4 tiles of the same type

        testBookshelf = new Bookshelf();
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
        testBookshelf = new Bookshelf();
        BookshelfTileSpot[] precedentTile = new BookshelfTileSpot[5];
        BookshelfTileSpot[] currentTile = new BookshelfTileSpot[5];
        int count=0,rnd1,index=7,rnd2;

        //test with an empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //test with less than 6 2x1 columns of same tiles and no 3x1 columns of same tiles
        for(int i=0;i<5;i++){
            currentTile[i]=new BookshelfTileSpot(TileType.randomTileType());
        }
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++) {
                testBookshelf.insertTile(j, currentTile[j].getTileType());
                precedentTile[j].setTile(currentTile[j].getTileType());
                do {
                    currentTile[j].setTile(TileType.randomTileType());
                }while(currentTile[j].getTileType()==precedentTile[j].getTileType());

            }
            rnd1=(int)(Math.random()*6);
            if(count<5 && rnd1%3==0){
                do {
                    rnd2 = (int) (Math.random() * 6);
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
        testCard = new CommonGoalCard3(4, false);
        testBookshelf = new Bookshelf();
        BookshelfTileSpot[] precedentTile = new BookshelfTileSpot[5];
        BookshelfTileSpot[] currentTile = new BookshelfTileSpot[5];
        int count=0,index=7,rnd2;

        for(int i=0;i<5;i++){
            currentTile[i]=new BookshelfTileSpot(TileType.randomTileType());
        }
        for(int i=0;i<6;i++){
            for(int j=0;j<5;j++) {
                testBookshelf.insertTile(j, currentTile[j].getTileType());
                precedentTile[j].setTile(currentTile[j].getTileType());
                do {
                    currentTile[j].setTile(TileType.randomTileType());
                }while(currentTile[j].getTileType()==precedentTile[j].getTileType());

            }

            if(count<6){
                do {
                    rnd2 = (int) (Math.random() * 6);
                }while(rnd2==index);
                index=rnd2;
                currentTile[index].setTile(precedentTile[index].getTileType());
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
        testCard = new CommonGoalCard3(4, false);
        testBookshelf = new Bookshelf();


        BookshelfTileSpot[] column1 = new BookshelfTileSpot[6];
        BookshelfTileSpot[] column2 = new BookshelfTileSpot[6];
        BookshelfTileSpot[] currentTile = new BookshelfTileSpot[5];
        int rnd1,rnd2;
        boolean flag=false;


        //test with an empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //test with less than 3 columns of at least 3 same tiles



        for(int i=0;i<5;i++){
            currentTile[i]=new BookshelfTileSpot(TileType.randomTileType());
        }

        //verify that currentTile[] contains at least 4 different tiles
        for(int i=0;i<5;i++){
            for(int j=i+1;j<5;j++){
                if(currentTile[i].getTileType()==currentTile[j].getTileType()){
                    do {
                        currentTile[j].setTile(TileType.randomTileType());
                    }while(currentTile[j].getTileType()==currentTile[i].getTileType());
                }
            }
        }

        //prepare coloumns
        rnd1=(int)(Math.random()*1);

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
        testCard = new CommonGoalCard3(4, false);
        testBookshelf = new Bookshelf();
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
        testBookshelf = new Bookshelf();
        BookshelfTileSpot[] row = new BookshelfTileSpot[5];
        int[] rnd = new int[5];
        int rnd1,rnd2;
        BookshelfTileSpot currentTile = new BookshelfTileSpot(TileType.randomTileType());

        //test with empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //prepare row with 5 tiles of different types
        for(int i=0;i<6;i++){
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
            rnd2=(int)(Math.random()*5);
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
        testBookshelf = new Bookshelf();
        BookshelfTileSpot[] row1 = new BookshelfTileSpot[5];
        BookshelfTileSpot[] row2 = new BookshelfTileSpot[5];
        int[] arnd1 = new int[5], arnd2 = new int[5];
        int rnd1,rnd2;
        BookshelfTileSpot currentTile = new BookshelfTileSpot(TileType.randomTileType());

        //prepare row with 5 tiles of different types
        for(int i=0;i<6;i++){
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
        rnd1=(int)(Math.random()*5);
        do {
            rnd2 = (int) (Math.random() * 5);
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

    /**
     * This method tests the 8th common goal card in an environment where the check function
     * should return true
     * @author patrickpoggi
     */
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

    /**
     * This method tests the 8th common goal card in an environment where the check function
     * should return false
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard8_expectedFalse_Test(){
        testCard = new CommonGoalCard8();
        testBookshelf = new Bookshelf();
        TileType tt = TileType.randomTileType();

        //Checking the empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //BookshelfTileSpot[][] threeOkOneNotMatrix = new BookshelfTileSpot[6][5]; //(0,0) is not ok
        //Only (0,0) is different from the others 3 corners
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
            TileType wrongtt;
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
            TileType wrongtt;
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
        for(int i=0;i<testBookshelf.getBookshelfWidth();i++){                   //las row
            TileType wrongtt;
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
        for(int i=0;i<testBookshelf.getBookshelfWidth();i++){                   //las row
            TileType wrongtt;
            do{
                wrongtt = TileType.randomTileType();
            }while(wrongtt == tt);
            if(i==testBookshelf.getBookshelfHeight()-1){
                testBookshelf.insertTile(i, wrongtt);
            }else if(i== 0){
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
        testCard = new CommonGoalCard9();
        testBookshelf = new Bookshelf();
        Set<Couple> positions = new HashSet<>();
        //Set<Couple> donePositions = new HashSet<>();
        Random r = new Random();
        for(TileType tt : TileType.values()){
            //Select random positions where this type should go
            for(int i=0;i<8;i++){
                positions.add(new Couple(r.nextInt(6), r.nextInt(5)));
            }
            for(int i=testBookshelf.getBookshelfHeight();i>=0;i--){
                for(int j=0;j<testBookshelf.getBookshelfWidth();j++){
                    if(positions.contains(new Couple(i,j))){
                        //donePositions.add(new Couple(i,j));
                        testBookshelf.insertTile(j,tt);
                        positions.remove(new Couple(i,j));
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
        }
    }

    /**
     * This method tests the 9th common goal card in an environment where the check function
     * should return false
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard9_expectedFalse_Test(){
        testCard = new CommonGoalCard9();
        testBookshelf = new Bookshelf();

        //Check the empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //Corner cases: I want the bookshelf to have 7 (case 1) and 0 (case 2) tiles of a given type
        Set<Couple> positions = new HashSet<>();
        //Set<Couple> donePositions = new HashSet<>();
        Random r = new Random();

        //Case 1:
        for(TileType tt : TileType.values()){
            //Select random positions where this type should go
            for(int i=0;i<7;i++){
                positions.add(new Couple(r.nextInt(6), r.nextInt(5)));
            }
            for(int i=testBookshelf.getBookshelfHeight();i>=0;i--){
                for(int j=0;j<testBookshelf.getBookshelfWidth();j++){
                    if(positions.contains(new Couple(i,j))){
                        //donePositions.add(new Couple(i,j));
                        testBookshelf.insertTile(j,tt);
                        positions.remove(new Couple(i,j));
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
            assertFalse(testCard.check(testBookshelf));
        }

        //Case 2:
        for(TileType tt : TileType.values()){
            for(int i=testBookshelf.getBookshelfHeight();i>=0;i--){
                for(int j=0;j<testBookshelf.getBookshelfWidth();j++){
                    TileType othertt;
                    do{
                        othertt = TileType.randomTileType();
                    }while(othertt == tt);
                    testBookshelf.insertTile(j,othertt);    //This way I'm ensuring that there are only those 8
                    // positions with the type tt
                }
            }
            assertFalse(testCard.check(testBookshelf));
        }
    }

    /**
     * This method tests the 10th common goal card in an environment where the check function
     * should return true
     * @author patrickpoggi
     */
    @Test
    public void CommonGoalCard10_expectedTrue_Test(){
        testCard = new CommonGoalCard10();
        testBookshelf = new Bookshelf();
        Random r = new Random();

        for(TileType tt : TileType.values()){
            for(int k=0;k<5;k++){                       //Doing 5 tries for every type
                Couple startingPos = new Couple(r.nextInt(4), r.nextInt(3));
                for(int i= testBookshelf.getBookshelfHeight();i>=0;i--){
                    for(int j=0;j< testBookshelf.getBookshelfWidth();j++){
                        if(     (i == startingPos.getA() && j == startingPos.getB()) ||
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
                assertTrue(testCard.check(testBookshelf));
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
        testCard = new CommonGoalCard10();
        testBookshelf = new Bookshelf();
        Random r = new Random();

        //check the empty bookshelf
        assertFalse(testCard.check(testBookshelf));

        //IDEA: Build five different bookshelves: in each one we put just one tile wrong for the X formation

        //Bookshelf 1: the tile in the starting position is wrong
        for(TileType tt : TileType.values()){
            for(int k=0;k<5;k++){                       //Doing 5 tries for every type
                Couple startingPos = new Couple(r.nextInt(4), r.nextInt(3));
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
            }
        }

        //Bookshelf 2: the tile in the upper-right corner is wrong
        for(TileType tt : TileType.values()){
            for(int k=0;k<5;k++){                       //Doing 5 tries for every type
                Couple startingPos = new Couple(r.nextInt(4), r.nextInt(3));
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
            }
        }

        //Bookshelf 3: the tile in the middle of the X is wrong
        for(TileType tt : TileType.values()){
            for(int k=0;k<5;k++){                       //Doing 5 tries for every type
                Couple startingPos = new Couple(r.nextInt(4), r.nextInt(3));
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
            }
        }

        //Bookshelf 4: the tile in the lower-left corner is wrong
        for(TileType tt : TileType.values()){
            for(int k=0;k<5;k++){                       //Doing 5 tries for every type
                Couple startingPos = new Couple(r.nextInt(4), r.nextInt(3));
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
            }
        }

        //Bookshelf 5: the tile in the lower-right corner is wrong:
        for(TileType tt : TileType.values()){
            for(int k=0;k<5;k++){                       //Doing 5 tries for every type
                Couple startingPos = new Couple(r.nextInt(4), r.nextInt(3));
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
            }
        }

        //Every possible false situation is a combination of one or more of these
    }
}
