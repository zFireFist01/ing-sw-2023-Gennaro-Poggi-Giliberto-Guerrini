package Server.Model.Cards;

import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

/**
 * PersonalGoalCard defines all the type of personal goal cards that exists in the actual game
 * @author due2
 */

public enum PersonalGoalCard implements Card {
    // Personal Goal Cards
    //in the matrix 0,0 is the top left corner
    TYPE1(new BookshelfTileSpot[][] {
        { new BookshelfTileSpot(TileType.PLANTS), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.FRAMES), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.CATS) },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.BOOKS), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.GAMES), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.TROPHIES), new BookshelfTileSpot(), new BookshelfTileSpot() } }),
    TYPE2(new BookshelfTileSpot[][] {
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.PLANTS), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(TileType.CATS), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.GAMES), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.BOOKS) },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.TROPHIES), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.FRAMES) } }),
    TYPE3(new BookshelfTileSpot[][] {
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(TileType.FRAMES), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.GAMES), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.PLANTS), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.CATS), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.TROPHIES) },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(TileType.BOOKS), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() } }),
    TYPE4(new BookshelfTileSpot[][] {
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.GAMES) },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(TileType.TROPHIES), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.FRAMES), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.PLANTS), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.BOOKS), new BookshelfTileSpot(TileType.CATS), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() } }),
    TYPE5(new BookshelfTileSpot[][] {
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.TROPHIES), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.FRAMES), new BookshelfTileSpot(TileType.BOOKS), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.PLANTS) },
        { new BookshelfTileSpot(TileType.GAMES), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.CATS), new BookshelfTileSpot() } }),
    TYPE6(new BookshelfTileSpot[][] {
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.TROPHIES), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.CATS) },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.BOOKS), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.GAMES), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.FRAMES), new BookshelfTileSpot() },
        { new BookshelfTileSpot(TileType.PLANTS), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() } }),
    TYPE7(new BookshelfTileSpot[][] {
        { new BookshelfTileSpot(TileType.CATS), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.FRAMES), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.PLANTS), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(TileType.TROPHIES), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.GAMES) },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.BOOKS), new BookshelfTileSpot(), new BookshelfTileSpot() } }),
    TYPE8(new BookshelfTileSpot[][] {
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.FRAMES) },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.CATS), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.TROPHIES), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(TileType.PLANTS), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.BOOKS), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.GAMES), new BookshelfTileSpot() } }),
    TYPE9(new BookshelfTileSpot[][] {
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.GAMES), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.CATS), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.BOOKS) },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.TROPHIES), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.PLANTS) },
        { new BookshelfTileSpot(TileType.FRAMES), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() } }),
    TYPE10(new BookshelfTileSpot[][] {
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.TROPHIES) },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.GAMES), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(TileType.BOOKS), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.CATS), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.FRAMES), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.PLANTS), new BookshelfTileSpot() } }),
    TYPE11(new BookshelfTileSpot[][] {
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.PLANTS), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.BOOKS), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(TileType.GAMES), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.FRAMES), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.CATS) },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.TROPHIES), new BookshelfTileSpot() } }),
    TYPE12(new BookshelfTileSpot[][] {
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.BOOKS), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(TileType.PLANTS), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.FRAMES), new BookshelfTileSpot(), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.TROPHIES), new BookshelfTileSpot() },
        { new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(TileType.GAMES) },
        { new BookshelfTileSpot(TileType.CATS), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot(), new BookshelfTileSpot() } });








    private BookshelfTileSpot[][] tileMatrix;

    /**
     * Constructor for the PersonalGoalCard class
     * @param tileMatrix
     */
    PersonalGoalCard(BookshelfTileSpot[][] tileMatrix) {
        this.tileMatrix = tileMatrix;
    }

    /**
     * Returns the tile matrix of the personal goal card
     * @return tileMatrix
     */

    public BookshelfTileSpot[][] getTileMatrix(){
        return tileMatrix;
    }

    /**
     * Checks if the player has completed the personal goal card at the end of the match
     * @param playerBookshelf
     * @return Player's score
     */
    public int check(BookshelfTileSpot[][] playerBookshelf){
        int score = 0;
        for (int i = 0; i < tileMatrix.length; i++) {
            for (int j = 0; j < tileMatrix[i].length; j++) {
                if (tileMatrix[i][j].getTileType() == playerBookshelf[i][j].getTileType() && tileMatrix[i][j].getTileType() != null){
                    score++;
                }
            }
        }

        if(score<3){
            return score;
        }else if(score==3){
            return 4;
        }else if(score==4) {
            return 6;
        }else if(score==5) {
            return 9;
        }else{
            return 12;
        }

    }
    /**
     * Returns the card ID
     * @return cardID
     */
    @Override
    public int getCardID() {
        if(this.equals(TYPE1))
            return 1;
        else if(this.equals(TYPE2))
            return 2;
        else if(this.equals(TYPE3))
            return 3;
        else if(this.equals(TYPE4))
            return 4;
        else if(this.equals(TYPE5))
            return 5;
        else if(this.equals(TYPE6))
            return 6;
        else if(this.equals(TYPE7))
            return 7;
        else if(this.equals(TYPE8))
            return 8;
        else if(this.equals(TYPE9))
            return 9;
        else if(this.equals(TYPE10))
            return 10;
        else if(this.equals(TYPE11))
            return 11;
        else if(this.equals(TYPE12))
            return 12;
        else
            return 0;
    }

    /**
     * This method is used fromn the CLI to obtain a printable represention of this object
     * @return the char matrix that represents a "drawing" of this object
     */
    public char[][] getCLIRepresentation(){
        char[][] res  = new char[13][15];
        res[0][0] = '|';
        res[0][14] = '|';
        res[12][0] = '|';
        res[12][14] = '|';
        for(int i=0;i<13;i++){
            res[i][1] = ' ';
            res[i][13] = ' ';
        }
        for(int i=0;i<13;i++){
            for(int j=2;j<13;j++){
                if(i%2 == 0){
                    if(j%2 == 0){
                        res[i][j] = '+';
                    }else{
                        res[i][j] = '-';
                    }
                }else{
                    if(j%2 == 0){
                        res[i][j] = '|';
                    }else{
                        res[i][j] = ' ';
                    }
                }
            }
        }
        switch(this){
            case TYPE1 -> {
                res[1][3] = TileType.PLANTS.getCLIRepresentation()[0][0];
                res[1][7] = TileType.FRAMES.getCLIRepresentation()[0][0];
                res[3][11] = TileType.CATS.getCLIRepresentation()[0][0];
                res[5][9] = TileType.BOOKS.getCLIRepresentation()[0][0];
                res[7][5] = TileType.GAMES.getCLIRepresentation()[0][0];
                res[11][7] = TileType.TROPHIES.getCLIRepresentation()[0][0];

            }
            case TYPE2 -> {
                res[3][5]= TileType.PLANTS.getCLIRepresentation()[0][0];
                res[5][3]= TileType.CATS.getCLIRepresentation()[0][0];
                res[5][7]= TileType.GAMES.getCLIRepresentation()[0][0];
                res[7][11]= TileType.BOOKS.getCLIRepresentation()[0][0];
                res[9][9]= TileType.TROPHIES.getCLIRepresentation()[0][0];
                res[11][11]= TileType.FRAMES.getCLIRepresentation()[0][0];
            }
            case TYPE3 ->{
                res[3][3]= TileType.FRAMES.getCLIRepresentation()[0][0];
                res[3][9]= TileType.GAMES.getCLIRepresentation()[0][0];
                res[5][7]= TileType.PLANTS.getCLIRepresentation()[0][0];
                res[7][5]= TileType.CATS.getCLIRepresentation()[0][0];
                res[7][11]= TileType.TROPHIES.getCLIRepresentation()[0][0];
                res[9][3]= TileType.BOOKS.getCLIRepresentation()[0][0];
            }

            case TYPE4 -> {
                res[1][11] = TileType.GAMES.getCLIRepresentation()[0][0];
                res[5][3] = TileType.TROPHIES.getCLIRepresentation()[0][0];
                res[5][5] = TileType.FRAMES.getCLIRepresentation()[0][0];
                res[7][9] = TileType.PLANTS.getCLIRepresentation()[0][0];
                res[9][5] = TileType.BOOKS.getCLIRepresentation()[0][0];
                res[9][7] = TileType.CATS.getCLIRepresentation()[0][0];
            }
            case TYPE5 -> {
                res[3][5] = TileType.TROPHIES.getCLIRepresentation()[0][0];
                res[7][5] = TileType.FRAMES.getCLIRepresentation()[0][0];
                res[7][7] = TileType.BOOKS.getCLIRepresentation()[0][0];
                res[9][11] = TileType.PLANTS.getCLIRepresentation()[0][0];
                res[11][3] = TileType.GAMES.getCLIRepresentation()[0][0];
                res[11][9] = TileType.GAMES.getCLIRepresentation()[0][0];
            }
            case TYPE6 -> {
                res[1][7] = TileType.TROPHIES.getCLIRepresentation()[0][0];
                res[1][11] = TileType.CATS.getCLIRepresentation()[0][0];
                res[5][9] = TileType.BOOKS.getCLIRepresentation()[0][0];
                res[9][5] = TileType.GAMES.getCLIRepresentation()[0][0];
                res[9][9] = TileType.FRAMES.getCLIRepresentation()[0][0];
                res[11][3] = TileType.PLANTS.getCLIRepresentation()[0][0];
            }
        }

        return res;
    }
}
