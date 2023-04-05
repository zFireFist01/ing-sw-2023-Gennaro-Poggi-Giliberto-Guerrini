package Server.Model.Cards;

import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;
import org.junit.jupiter.api.*;

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
        matrix[0][2].setTileType();
        matrix[1][4].setTileType();
        matrix[2][3].setTileType();
        matrix[3][1].setTileType();
        matrix[5][2].setTileType();
        matrix[0][0].setTileType();
        Assertions.assertTrue(p.check(matrix)==0);
        matrix[0][0].setTileType(TileType.PLANTS);
        Assertions.assertTrue(p.check(matrix)==1);
        matrix[0][2].setTileType(TileType.FRAMES);
        Assertions.assertTrue(p.check(matrix)==2);
        matrix[1][4].setTileType(TileType.CATS);
        Assertions.assertTrue(p.check(matrix)==4);
        matrix[2][3].setTileType(TileType.BOOKS);
        Assertions.assertTrue(p.check(matrix)==6);
        matrix[3][1].setTileType(TileType.GAMES);
        Assertions.assertTrue(p.check(matrix)==9);
        matrix[5][2].setTileType(TileType.TROPHIES);
        Assertions.assertTrue(p.check(matrix)==12);

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
        matrix[2][0].setTileType();
        matrix[2][2].setTileType();
        matrix[3][4].setTileType();
        matrix[4][3].setTileType();
        matrix[5][4].setTileType();
        matrix[1][1].setTileType();
        Assertions.assertTrue(p.check(matrix)==0);
        matrix[1][1].setTileType(TileType.PLANTS);
        Assertions.assertTrue(p.check(matrix)==1);
        matrix[2][0].setTileType(TileType.CATS);
        Assertions.assertTrue(p.check(matrix)==2);
        matrix[2][2].setTileType(TileType.GAMES);
        Assertions.assertTrue(p.check(matrix)==4);
        matrix[3][4].setTileType(TileType.BOOKS);
        Assertions.assertTrue(p.check(matrix)==6);
        matrix[4][3].setTileType(TileType.TROPHIES);
        Assertions.assertTrue(p.check(matrix)==9);
        matrix[5][4].setTileType(TileType.FRAMES);
        Assertions.assertTrue(p.check(matrix)==12);

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
        matrix[1][3].setTileType();
        matrix[2][2].setTileType();
        matrix[3][1].setTileType();
        matrix[3][4].setTileType();
        matrix[5][0].setTileType();
        matrix[1][0].setTileType();
        Assertions.assertTrue(p.check(matrix)==0);
        matrix[1][0].setTileType(TileType.FRAMES);
        Assertions.assertTrue(p.check(matrix)==1);
        matrix[1][3].setTileType(TileType.GAMES);
        Assertions.assertTrue(p.check(matrix)==2);
        matrix[2][2].setTileType(TileType.PLANTS);
        Assertions.assertTrue(p.check(matrix)==4);
        matrix[3][1].setTileType(TileType.CATS);
        Assertions.assertTrue(p.check(matrix)==6);
        matrix[3][4].setTileType(TileType.TROPHIES);
        Assertions.assertTrue(p.check(matrix)==9);
        matrix[5][0].setTileType(TileType.BOOKS);
        Assertions.assertTrue(p.check(matrix)==12);

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
        matrix[2][0].setTileType();
        matrix[2][2].setTileType();
        matrix[3][3].setTileType();
        matrix[4][1].setTileType();
        matrix[4][2].setTileType();
        matrix[0][4].setTileType();
        Assertions.assertTrue(p.check(matrix)==0);
        matrix[0][4].setTileType(TileType.GAMES);
        Assertions.assertTrue(p.check(matrix)==1);
        matrix[2][0].setTileType(TileType.TROPHIES);
        Assertions.assertTrue(p.check(matrix)==2);
        matrix[2][2].setTileType(TileType.FRAMES);
        Assertions.assertTrue(p.check(matrix)==4);
        matrix[3][3].setTileType(TileType.PLANTS);
        Assertions.assertTrue(p.check(matrix)==6);
        matrix[4][1].setTileType(TileType.BOOKS);
        Assertions.assertTrue(p.check(matrix)==9);
        matrix[4][2].setTileType(TileType.CATS);
        Assertions.assertTrue(p.check(matrix)==12);

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
        matrix[1][1].setTileType();
        matrix[3][1].setTileType();
        matrix[3][2].setTileType();
        matrix[4][4].setTileType();
        matrix[5][0].setTileType();
        matrix[5][3].setTileType();
        Assertions.assertTrue(p.check(matrix)==0);
        matrix[1][1].setTileType(TileType.TROPHIES);
        Assertions.assertTrue(p.check(matrix)==1);
        matrix[3][1].setTileType(TileType.FRAMES);
        Assertions.assertTrue(p.check(matrix)==2);
        matrix[3][2].setTileType(TileType.BOOKS);
        Assertions.assertTrue(p.check(matrix)==4);
        matrix[4][4].setTileType(TileType.PLANTS);
        Assertions.assertTrue(p.check(matrix)==6);
        matrix[5][0].setTileType(TileType.GAMES);
        Assertions.assertTrue(p.check(matrix)==9);
        matrix[5][3].setTileType(TileType.CATS);
        Assertions.assertTrue(p.check(matrix)==12);

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
        matrix[0][2].setTileType();
        matrix[0][4].setTileType();
        matrix[2][3].setTileType();
        matrix[4][1].setTileType();
        matrix[4][3].setTileType();
        matrix[5][0].setTileType();
        Assertions.assertTrue(p.check(matrix)==0);
        matrix[0][2].setTileType(TileType.TROPHIES);
        Assertions.assertTrue(p.check(matrix)==1);
        matrix[0][4].setTileType(TileType.CATS);
        Assertions.assertTrue(p.check(matrix)==2);
        matrix[2][3].setTileType(TileType.BOOKS);
        Assertions.assertTrue(p.check(matrix)==4);
        matrix[4][1].setTileType(TileType.GAMES);
        Assertions.assertTrue(p.check(matrix)==6);
        matrix[4][3].setTileType(TileType.FRAMES);
        Assertions.assertTrue(p.check(matrix)==9);
        matrix[5][0].setTileType(TileType.PLANTS);
        Assertions.assertTrue(p.check(matrix)==12);

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
        matrix[0][0].setTileType();
        matrix[1][3].setTileType();
        matrix[2][1].setTileType();
        matrix[3][0].setTileType();
        matrix[4][4].setTileType();
        matrix[5][2].setTileType();
        Assertions.assertTrue(p.check(matrix)==0);
        matrix[0][0].setTileType(TileType.CATS);
        Assertions.assertTrue(p.check(matrix)==1);
        matrix[1][3].setTileType(TileType.FRAMES);
        Assertions.assertTrue(p.check(matrix)==2);
        matrix[2][1].setTileType(TileType.PLANTS);
        Assertions.assertTrue(p.check(matrix)==4);
        matrix[3][0].setTileType(TileType.TROPHIES);
        Assertions.assertTrue(p.check(matrix)==6);
        matrix[4][4].setTileType(TileType.GAMES);
        Assertions.assertTrue(p.check(matrix)==9);
        matrix[5][2].setTileType(TileType.BOOKS);
        Assertions.assertTrue(p.check(matrix)==12);

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

        matrix[0][4].setTileType();
        matrix[1][1].setTileType();
        matrix[2][2].setTileType();
        matrix[3][0].setTileType();
        matrix[4][3].setTileType();
        matrix[5][3].setTileType();
        Assertions.assertTrue(p.check(matrix)==0);
        matrix[0][4].setTileType(TileType.FRAMES);
        Assertions.assertTrue(p.check(matrix)==1);
        matrix[1][1].setTileType(TileType.CATS);
        Assertions.assertTrue(p.check(matrix)==2);
        matrix[2][2].setTileType(TileType.TROPHIES);
        Assertions.assertTrue(p.check(matrix)==4);
        matrix[3][0].setTileType(TileType.PLANTS);
        Assertions.assertTrue(p.check(matrix)==6);
        matrix[4][3].setTileType(TileType.BOOKS);
        Assertions.assertTrue(p.check(matrix)==9);
        matrix[5][3].setTileType(TileType.GAMES);
        Assertions.assertTrue(p.check(matrix)==12);

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
        matrix[0][2].setTileType();
        matrix[2][2].setTileType();
        matrix[3][4].setTileType();
        matrix[4][1].setTileType();
        matrix[4][4].setTileType();
        matrix[5][0].setTileType();
        Assertions.assertTrue(p.check(matrix)==0);
        matrix[0][2].setTileType(TileType.GAMES);
        Assertions.assertTrue(p.check(matrix)==1);
        matrix[2][2].setTileType(TileType.CATS);
        Assertions.assertTrue(p.check(matrix)==2);
        matrix[3][4].setTileType(TileType.BOOKS);
        Assertions.assertTrue(p.check(matrix)==4);
        matrix[4][1].setTileType(TileType.TROPHIES);
        Assertions.assertTrue(p.check(matrix)==6);
        matrix[4][4].setTileType(TileType.PLANTS);
        Assertions.assertTrue(p.check(matrix)==9);
        matrix[5][0].setTileType(TileType.FRAMES);
        Assertions.assertTrue(p.check(matrix)==12);

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
        matrix[0][4].setTileType();
        matrix[1][1].setTileType();
        matrix[2][0].setTileType();
        matrix[3][3].setTileType();
        matrix[4][1].setTileType();
        matrix[5][3].setTileType();
        Assertions.assertTrue(p.check(matrix)==0);
        matrix[0][4].setTileType(TileType.TROPHIES);
        Assertions.assertTrue(p.check(matrix)==1);
        matrix[1][1].setTileType(TileType.GAMES);
        Assertions.assertTrue(p.check(matrix)==2);
        matrix[2][0].setTileType(TileType.BOOKS);
        Assertions.assertTrue(p.check(matrix)==4);
        matrix[3][3].setTileType(TileType.CATS);
        Assertions.assertTrue(p.check(matrix)==6);
        matrix[4][1].setTileType(TileType.FRAMES);
        Assertions.assertTrue(p.check(matrix)==9);
        matrix[5][3].setTileType(TileType.PLANTS);
        Assertions.assertTrue(p.check(matrix)==12);

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
        matrix[0][2].setTileType();
        matrix[1][1].setTileType();
        matrix[2][0].setTileType();
        matrix[3][2].setTileType();
        matrix[4][4].setTileType();
        matrix[5][3].setTileType();
        Assertions.assertTrue(p.check(matrix)==0);
        matrix[0][2].setTileType(TileType.PLANTS);
        Assertions.assertTrue(p.check(matrix)==1);
        matrix[1][1].setTileType(TileType.BOOKS);
        Assertions.assertTrue(p.check(matrix)==2);
        matrix[2][0].setTileType(TileType.GAMES);
        Assertions.assertTrue(p.check(matrix)==4);
        matrix[3][2].setTileType(TileType.FRAMES);
        Assertions.assertTrue(p.check(matrix)==6);
        matrix[4][4].setTileType(TileType.CATS);
        Assertions.assertTrue(p.check(matrix)==9);
        matrix[5][3].setTileType(TileType.TROPHIES);
        Assertions.assertTrue(p.check(matrix)==12);

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
        matrix[0][2].setTileType();
        matrix[1][1].setTileType();
        matrix[2][2].setTileType();
        matrix[3][3].setTileType();
        matrix[4][4].setTileType();
        matrix[5][0].setTileType();
        Assertions.assertTrue(p.check(matrix)==0);
        matrix[0][2].setTileType(TileType.BOOKS);
        Assertions.assertTrue(p.check(matrix)==1);
        matrix[1][1].setTileType(TileType.PLANTS);
        Assertions.assertTrue(p.check(matrix)==2);
        matrix[2][2].setTileType(TileType.FRAMES);
        Assertions.assertTrue(p.check(matrix)==4);
        matrix[3][3].setTileType(TileType.TROPHIES);
        Assertions.assertTrue(p.check(matrix)==6);
        matrix[4][4].setTileType(TileType.GAMES);
        Assertions.assertTrue(p.check(matrix)==9);
        matrix[5][0].setTileType(TileType.CATS);
        Assertions.assertTrue(p.check(matrix)==12);














    }

}
