package Server.Model.Cards;

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
    public int check(BookshelfTileSpot playerBookshelf){
        int score = 0;
        for (int i = 0; i < tileMatrix.length; i++) {
            for (int j = 0; j < tileMatrix[i].length; j++) {
                if (tileMatrix[i][j].getTileType() == playerBookshelf.getTileType()){
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


}
