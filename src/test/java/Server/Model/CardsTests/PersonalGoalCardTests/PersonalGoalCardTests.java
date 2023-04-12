package Server.Model.CardsTests.PersonalGoalCardTests;

import Server.Model.Cards.PersonalGoalCard;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;


import static org.junit.Assert.*;

public class PersonalGoalCardTests {
    @Test
    void checkTest(){
        int i,j;
        //test check method for PersonalGoalCard.TYPE1
        PersonalGoalCard p = PersonalGoalCard.TYPE1;
        BookshelfTileSpot[][] matrix = new BookshelfTileSpot[5][6];
        for(i = 0; i < 5; i++){
            for(j = 0; j < 6; j++){
                //if a random int is divisible by 2, matrix[i][j]=new BookshelfTileSpot(TileType.randomTileType); else matrix[i][j]=new BookshelfTileSpot();
                if((int)(Math.random()*10)%2==0)
                    matrix[i][j] = new BookshelfTileSpot(TileType.randomTileType());
                else
                    matrix[i][j] = new BookshelfTileSpot();
            }
        }
        matrix[0][2].setTile();
        matrix[1][4].setTile();
        matrix[2][3].setTile();
        matrix[3][1].setTile();
        matrix[5][2].setTile();
        matrix[0][0].setTile();
        assertTrue(p.check(matrix)==0);
        matrix[0][0].setTile(TileType.PLANTS);
        assertTrue(p.check(matrix)==1);
        matrix[0][2].setTile(TileType.FRAMES);
        assertTrue(p.check(matrix)==2);
        matrix[1][4].setTile(TileType.CATS);
        assertTrue(p.check(matrix)==4);
        matrix[2][3].setTile(TileType.BOOKS);
        assertTrue(p.check(matrix)==6);
        matrix[3][1].setTile(TileType.GAMES);
        assertTrue(p.check(matrix)==9);
        matrix[5][2].setTile(TileType.TROPHIES);
        assertTrue(p.check(matrix)==12);

        //test check method for PersonalGoalCard.TYPE2

        p = PersonalGoalCard.TYPE2;
        for(i = 0; i < 5; i++){
            for(j = 0; j < 6; j++){
                //if a random int is divisible by 2, matrix[i][j]=new BookshelfTileSpot(TileType.randomTileType); else matrix[i][j]=new BookshelfTileSpot();
                if((int)(Math.random()*10)%2==0)
                    matrix[i][j] = new BookshelfTileSpot(TileType.randomTileType());
                else
                    matrix[i][j] = new BookshelfTileSpot();
            }
        }
        matrix[2][0].setTile();
        matrix[2][2].setTile();
        matrix[3][4].setTile();
        matrix[4][3].setTile();
        matrix[5][4].setTile();
        matrix[1][1].setTile();
        assertTrue(p.check(matrix)==0);
        matrix[1][1].setTile(TileType.PLANTS);
        assertTrue(p.check(matrix)==1);
        matrix[2][0].setTile(TileType.CATS);
        assertTrue(p.check(matrix)==2);
        matrix[2][2].setTile(TileType.GAMES);
        assertTrue(p.check(matrix)==4);
        matrix[3][4].setTile(TileType.BOOKS);
        assertTrue(p.check(matrix)==6);
        matrix[4][3].setTile(TileType.TROPHIES);
        assertTrue(p.check(matrix)==9);
        matrix[5][4].setTile(TileType.FRAMES);
        assertTrue(p.check(matrix)==12);

        //test check method for PersonalGoalCard.TYPE3
        p = PersonalGoalCard.TYPE3;
        for(i = 0; i < 5; i++){
            for(j = 0; j < 6; j++){
                //if a random int is divisible by 2, matrix[i][j]=new BookshelfTileSpot(TileType.randomTileType); else matrix[i][j]=new BookshelfTileSpot();
                if((int)(Math.random()*10)%2==0)
                    matrix[i][j] = new BookshelfTileSpot(TileType.randomTileType());
                else
                    matrix[i][j] = new BookshelfTileSpot();
            }
        }
        matrix[1][3].setTile();
        matrix[2][2].setTile();
        matrix[3][1].setTile();
        matrix[3][4].setTile();
        matrix[5][0].setTile();
        matrix[1][0].setTile();
        assertTrue(p.check(matrix)==0);
        matrix[1][0].setTile(TileType.FRAMES);
        assertTrue(p.check(matrix)==1);
        matrix[1][3].setTile(TileType.GAMES);
        assertTrue(p.check(matrix)==2);
        matrix[2][2].setTile(TileType.PLANTS);
        assertTrue(p.check(matrix)==4);
        matrix[3][1].setTile(TileType.CATS);
        assertTrue(p.check(matrix)==6);
        matrix[3][4].setTile(TileType.TROPHIES);
        assertTrue(p.check(matrix)==9);
        matrix[5][0].setTile(TileType.BOOKS);
        assertTrue(p.check(matrix)==12);

        //test check method for PersonalGoalCard.TYPE4
        p = PersonalGoalCard.TYPE4;
        for(i = 0; i < 5; i++){
            for(j = 0; j < 6; j++){
                //if a random int is divisible by 2, matrix[i][j]=new BookshelfTileSpot(TileType.randomTileType); else matrix[i][j]=new BookshelfTileSpot();
                if((int)(Math.random()*10)%2==0)
                    matrix[i][j] = new BookshelfTileSpot(TileType.randomTileType());
                else
                    matrix[i][j] = new BookshelfTileSpot();
            }
        }
        matrix[2][0].setTile();
        matrix[2][2].setTile();
        matrix[3][3].setTile();
        matrix[4][1].setTile();
        matrix[4][2].setTile();
        matrix[0][4].setTile();
        assertTrue(p.check(matrix)==0);
        matrix[0][4].setTile(TileType.GAMES);
        assertTrue(p.check(matrix)==1);
        matrix[2][0].setTile(TileType.TROPHIES);
        assertTrue(p.check(matrix)==2);
        matrix[2][2].setTile(TileType.FRAMES);
        assertTrue(p.check(matrix)==4);
        matrix[3][3].setTile(TileType.PLANTS);
        assertTrue(p.check(matrix)==6);
        matrix[4][1].setTile(TileType.BOOKS);
        assertTrue(p.check(matrix)==9);
        matrix[4][2].setTile(TileType.CATS);
        assertTrue(p.check(matrix)==12);

        //test check method for PersonalGoalCard.TYPE5
        p = PersonalGoalCard.TYPE5;
        for(i = 0; i < 5; i++){
            for(j = 0; j < 6; j++){
                //if a random int is divisible by 2, matrix[i][j]=new BookshelfTileSpot(TileType.randomTileType); else matrix[i][j]=new BookshelfTileSpot();
                if((int)(Math.random()*10)%2==0)
                    matrix[i][j] = new BookshelfTileSpot(TileType.randomTileType());
                else
                    matrix[i][j] = new BookshelfTileSpot();
            }
        }
        matrix[1][1].setTile();
        matrix[3][1].setTile();
        matrix[3][2].setTile();
        matrix[4][4].setTile();
        matrix[5][0].setTile();
        matrix[5][3].setTile();
        assertTrue(p.check(matrix)==0);
        matrix[1][1].setTile(TileType.TROPHIES);
        assertTrue(p.check(matrix)==1);
        matrix[3][1].setTile(TileType.FRAMES);
        assertTrue(p.check(matrix)==2);
        matrix[3][2].setTile(TileType.BOOKS);
        assertTrue(p.check(matrix)==4);
        matrix[4][4].setTile(TileType.PLANTS);
        assertTrue(p.check(matrix)==6);
        matrix[5][0].setTile(TileType.GAMES);
        assertTrue(p.check(matrix)==9);
        matrix[5][3].setTile(TileType.CATS);
        assertTrue(p.check(matrix)==12);

        //test check method for PersonalGoalCard.TYPE6
        p = PersonalGoalCard.TYPE6;
        for(i = 0; i < 5; i++){
            for(j = 0; j < 6; j++){
                //if a random int is divisible by 2, matrix[i][j]=new BookshelfTileSpot(TileType.randomTileType); else matrix[i][j]=new BookshelfTileSpot();
                if((int)(Math.random()*10)%2==0)
                    matrix[i][j] = new BookshelfTileSpot(TileType.randomTileType());
                else
                    matrix[i][j] = new BookshelfTileSpot();
            }
        }
        matrix[0][2].setTile();
        matrix[0][4].setTile();
        matrix[2][3].setTile();
        matrix[4][1].setTile();
        matrix[4][3].setTile();
        matrix[5][0].setTile();
        assertTrue(p.check(matrix)==0);
        matrix[0][2].setTile(TileType.TROPHIES);
        assertTrue(p.check(matrix)==1);
        matrix[0][4].setTile(TileType.CATS);
        assertTrue(p.check(matrix)==2);
        matrix[2][3].setTile(TileType.BOOKS);
        assertTrue(p.check(matrix)==4);
        matrix[4][1].setTile(TileType.GAMES);
        assertTrue(p.check(matrix)==6);
        matrix[4][3].setTile(TileType.FRAMES);
        assertTrue(p.check(matrix)==9);
        matrix[5][0].setTile(TileType.PLANTS);
        assertTrue(p.check(matrix)==12);

        //test check method for PersonalGoalCard.TYPE7
        p = PersonalGoalCard.TYPE7;
        for(i = 0; i < 5; i++){
            for(j = 0; j < 6; j++){
                //if a random int is divisible by 2, matrix[i][j]=new BookshelfTileSpot(TileType.randomTileType); else matrix[i][j]=new BookshelfTileSpot();
                if((int)(Math.random()*10)%2==0)
                    matrix[i][j] = new BookshelfTileSpot(TileType.randomTileType());
                else
                    matrix[i][j] = new BookshelfTileSpot();
            }
        }
        matrix[0][0].setTile();
        matrix[1][3].setTile();
        matrix[2][1].setTile();
        matrix[3][0].setTile();
        matrix[4][4].setTile();
        matrix[5][2].setTile();
        assertTrue(p.check(matrix)==0);
        matrix[0][0].setTile(TileType.CATS);
        assertTrue(p.check(matrix)==1);
        matrix[1][3].setTile(TileType.FRAMES);
        assertTrue(p.check(matrix)==2);
        matrix[2][1].setTile(TileType.PLANTS);
        assertTrue(p.check(matrix)==4);
        matrix[3][0].setTile(TileType.TROPHIES);
        assertTrue(p.check(matrix)==6);
        matrix[4][4].setTile(TileType.GAMES);
        assertTrue(p.check(matrix)==9);
        matrix[5][2].setTile(TileType.BOOKS);
        assertTrue(p.check(matrix)==12);

        //test check method for PersonalGoalCard.TYPE8
        p = PersonalGoalCard.TYPE8;
        for(i = 0; i < 5; i++){
            for(j = 0; j < 6; j++){
                //if a random int is divisible by 2, matrix[i][j]=new BookshelfTileSpot(TileType.randomTileType); else matrix[i][j]=new BookshelfTileSpot();
                if((int)(Math.random()*10)%2==0)
                    matrix[i][j] = new BookshelfTileSpot(TileType.randomTileType());
                else
                    matrix[i][j] = new BookshelfTileSpot();
            }
        }

        matrix[0][4].setTile();
        matrix[1][1].setTile();
        matrix[2][2].setTile();
        matrix[3][0].setTile();
        matrix[4][3].setTile();
        matrix[5][3].setTile();
        assertTrue(p.check(matrix)==0);
        matrix[0][4].setTile(TileType.FRAMES);
        assertTrue(p.check(matrix)==1);
        matrix[1][1].setTile(TileType.CATS);
        assertTrue(p.check(matrix)==2);
        matrix[2][2].setTile(TileType.TROPHIES);
        assertTrue(p.check(matrix)==4);
        matrix[3][0].setTile(TileType.PLANTS);
        assertTrue(p.check(matrix)==6);
        matrix[4][3].setTile(TileType.BOOKS);
        assertTrue(p.check(matrix)==9);
        matrix[5][3].setTile(TileType.GAMES);
        assertTrue(p.check(matrix)==12);

        //test check method for PersonalGoalCard.TYPE9

        p = PersonalGoalCard.TYPE9;
        for(i = 0; i < 5; i++){
            for(j = 0; j < 6; j++){
                //if a random int is divisible by 2, matrix[i][j]=new BookshelfTileSpot(TileType.randomTileType); else matrix[i][j]=new BookshelfTileSpot();
                if((int)(Math.random()*10)%2==0)
                    matrix[i][j] = new BookshelfTileSpot(TileType.randomTileType());
                else
                    matrix[i][j] = new BookshelfTileSpot();
            }
        }
        matrix[0][2].setTile();
        matrix[2][2].setTile();
        matrix[3][4].setTile();
        matrix[4][1].setTile();
        matrix[4][4].setTile();
        matrix[5][0].setTile();
        assertTrue(p.check(matrix)==0);
        matrix[0][2].setTile(TileType.GAMES);
        assertTrue(p.check(matrix)==1);
        matrix[2][2].setTile(TileType.CATS);
        assertTrue(p.check(matrix)==2);
        matrix[3][4].setTile(TileType.BOOKS);
        assertTrue(p.check(matrix)==4);
        matrix[4][1].setTile(TileType.TROPHIES);
        assertTrue(p.check(matrix)==6);
        matrix[4][4].setTile(TileType.PLANTS);
        assertTrue(p.check(matrix)==9);
        matrix[5][0].setTile(TileType.FRAMES);
        assertTrue(p.check(matrix)==12);

        //test check method for PersonalGoalCard.TYPE10

        p = PersonalGoalCard.TYPE10;
        for(i = 0; i < 5; i++){
            for(j = 0; j < 6; j++){
                //if a random int is divisible by 2, matrix[i][j]=new BookshelfTileSpot(TileType.randomTileType); else matrix[i][j]=new BookshelfTileSpot();
                if((int)(Math.random()*10)%2==0)
                    matrix[i][j] = new BookshelfTileSpot(TileType.randomTileType());
                else
                    matrix[i][j] = new BookshelfTileSpot();
            }
        }
        matrix[0][4].setTile();
        matrix[1][1].setTile();
        matrix[2][0].setTile();
        matrix[3][3].setTile();
        matrix[4][1].setTile();
        matrix[5][3].setTile();
        assertTrue(p.check(matrix)==0);
        matrix[0][4].setTile(TileType.TROPHIES);
        assertTrue(p.check(matrix)==1);
        matrix[1][1].setTile(TileType.GAMES);
        assertTrue(p.check(matrix)==2);
        matrix[2][0].setTile(TileType.BOOKS);
        assertTrue(p.check(matrix)==4);
        matrix[3][3].setTile(TileType.CATS);
        assertTrue(p.check(matrix)==6);
        matrix[4][1].setTile(TileType.FRAMES);
        assertTrue(p.check(matrix)==9);
        matrix[5][3].setTile(TileType.PLANTS);
        assertTrue(p.check(matrix)==12);

        //test check method for PersonalGoalCard.TYPE11

        p = PersonalGoalCard.TYPE11;
        for(i = 0; i < 5; i++){
            for(j = 0; j < 6; j++){
                //if a random int is divisible by 2, matrix[i][j]=new BookshelfTileSpot(TileType.randomTileType); else matrix[i][j]=new BookshelfTileSpot();
                if((int)(Math.random()*10)%2==0)
                    matrix[i][j] = new BookshelfTileSpot(TileType.randomTileType());
                else
                    matrix[i][j] = new BookshelfTileSpot();
            }
        }
        matrix[0][2].setTile();
        matrix[1][1].setTile();
        matrix[2][0].setTile();
        matrix[3][2].setTile();
        matrix[4][4].setTile();
        matrix[5][3].setTile();
        assertTrue(p.check(matrix)==0);
        matrix[0][2].setTile(TileType.PLANTS);
        assertTrue(p.check(matrix)==1);
        matrix[1][1].setTile(TileType.BOOKS);
        assertTrue(p.check(matrix)==2);
        matrix[2][0].setTile(TileType.GAMES);
        assertTrue(p.check(matrix)==4);
        matrix[3][2].setTile(TileType.FRAMES);
        assertTrue(p.check(matrix)==6);
        matrix[4][4].setTile(TileType.CATS);
        assertTrue(p.check(matrix)==9);
        matrix[5][3].setTile(TileType.TROPHIES);
        assertTrue(p.check(matrix)==12);

        //test check method for PersonalGoalCard.TYPE12

        p = PersonalGoalCard.TYPE12;
        for(i = 0; i < 5; i++){
            for(j = 0; j < 6; j++){
                //if a random int is divisible by 2, matrix[i][j]=new BookshelfTileSpot(TileType.randomTileType); else matrix[i][j]=new BookshelfTileSpot();
                if((int)(Math.random()*10)%2==0)
                    matrix[i][j] = new BookshelfTileSpot(TileType.randomTileType());
                else
                    matrix[i][j] = new BookshelfTileSpot();
            }
        }
        matrix[0][2].setTile();
        matrix[1][1].setTile();
        matrix[2][2].setTile();
        matrix[3][3].setTile();
        matrix[4][4].setTile();
        matrix[5][0].setTile();
        assertTrue(p.check(matrix)==0);
        matrix[0][2].setTile(TileType.BOOKS);
        assertTrue(p.check(matrix)==1);
        matrix[1][1].setTile(TileType.PLANTS);
        assertTrue(p.check(matrix)==2);
        matrix[2][2].setTile(TileType.FRAMES);
        assertTrue(p.check(matrix)==4);
        matrix[3][3].setTile(TileType.TROPHIES);
        assertTrue(p.check(matrix)==6);
        matrix[4][4].setTile(TileType.GAMES);
        assertTrue(p.check(matrix)==9);
        matrix[5][0].setTile(TileType.CATS);
        assertTrue(p.check(matrix)==12);














    }

}
