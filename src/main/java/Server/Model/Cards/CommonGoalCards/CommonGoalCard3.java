package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;
/**
 * This class represents the third common goal card
 * @author due2
 */

public class CommonGoalCard3 extends CommonGoalCard {

    /**
     * constructor of the class CommonGoalCard3 that calls the constructor of the superclass
     * @param playersNum the number of players in the game
     * @param secondIstance true if it is the second card, false otherwise in order to know if the card has to be created with the second instance of the points tiles
     */
    public CommonGoalCard3(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
    }

    /**
     * This method checks if the common goal card is completed(if there are at least four adjacent tiles with the same tile type)
     * @param bookshelf the bookshelf of the player
     * @return true if the common goal card is completed, false otherwise
     */
    @Override
    public boolean check(Bookshelf bookshelf) {
        BookshelfTileSpot[][] shelf = bookshelf.getTileMatrix();
        int count = 0;
        int[][] verifier = new int[6][5];

        //verifies if there are at least four 1x4 columns with the same tile type
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (shelf[i][j].getTileType() == TileType.PLANTS) {
                    verifier[i][j] = 1;
                } else if (shelf[i][j].getTileType() == TileType.FRAMES) {
                    verifier[i][j] = 2;
                } else if (shelf[i][j].getTileType() == TileType.BOOKS) {
                    verifier[i][j] = 3;
                } else if (shelf[i][j].getTileType() == TileType.CATS) {
                    verifier[i][j] = 4;
                } else if (shelf[i][j].getTileType() == TileType.GAMES) {
                    verifier[i][j] = 5;
                } else if (shelf[i][j].getTileType() == TileType.TROPHIES) {
                    verifier[i][j] = 6;
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if(verifier[i][j] != 0) {
                    int tmp =countAdjacentTiles(verifier, i, j, verifier[i][j])+1;

                    count+=(int)(tmp/4);

                }
            }
        }
        if (count >= 4) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * this method counts the number of adjacent tiles with the same tile type
     * @requires tileType !=0
     * @param shelf
     * @param i
     * @param j
     * @param tileType
     * @return
     */

    private int countAdjacentTiles(int[][] shelf, int i, int j, int tileType) {
        int count = 0;
        if (i < 5 && j<4 && shelf[i][j+1]==tileType && shelf[i + 1][j] == tileType) {
            shelf[i + 1][j] = 0;
            shelf[i][j+1] = 0;

            return 2 + countAdjacentTiles(shelf, i + 1, j, tileType)+countAdjacentTiles(shelf, i , j+1, tileType);

        } else if (i < 5 && shelf[i + 1][j] == tileType) {
            shelf[i + 1][j] = 0;
            return 1 + countAdjacentTiles(shelf, i + 1, j, tileType);
        } else if (j < 4 && shelf[i][j + 1] == tileType) {
            shelf[i][j + 1] = 0;
            return 1 + countAdjacentTiles(shelf, i, j + 1, tileType);
        } else {
            return 0;

        }

    }
    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 3;
    }
}


