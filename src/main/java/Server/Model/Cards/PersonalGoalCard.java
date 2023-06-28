package Server.Model.Cards;

import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;
import com.google.gson.annotations.Expose;

/**
 * This class defines all the type of personal goal cards that exists in the actual game
 * @author Valentino Guerrini
 */

public enum PersonalGoalCard implements Card {
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

    @Expose
    private BookshelfTileSpot[][] tileMatrix;


    /**
     * Constructor for the PersonalGoalCard class
     * @param tileMatrix one of the type of the PersonalGoalCards
     */
    PersonalGoalCard(BookshelfTileSpot[][] tileMatrix) {
        this.tileMatrix = tileMatrix;
    }

    public BookshelfTileSpot[][] getTileMatrix(){
        return tileMatrix;
    }

    /**
     * Checks if the player has completed the personal goal card at the end of the match
     * @param playerBookshelf the bookshelf of the player
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


}
