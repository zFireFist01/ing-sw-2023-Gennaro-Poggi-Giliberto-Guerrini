package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;

/**
 * This class represents the third common goal card
 * @author due2
 */

public class CommonGoalCard3 extends CommonGoalCard {

    /**
     * This method checks if the common goal card is completed(if there are at least four 1x4 columns with the same tile type)
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
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 5; j++) {
                if (verifier[i][j] == verifier[i+1][j] && verifier[i][j] == verifier[i+2][j] && verifier[i][j] == verifier[i+3][j]) {
                    count++;
                    verifier[i][j] = 0;
                    verifier[i+1][j] = 0;
                    verifier[i+2][j] = 0;
                    verifier[i+3][j] = 0;
                }
            }
        }
        if (count >= 4) {
            return true;
        } else {
            return false;
        }

    }
}
