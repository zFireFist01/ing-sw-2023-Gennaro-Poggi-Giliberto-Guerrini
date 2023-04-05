package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

/**
 * This class represents the second common goal card
 * @author due2
 */

public class CommonGoalCard2 extends CommonGoalCard {
    /**
     * This method checks if the common goal card is completed
     * @param bookshelf the bookshelf of the player
     * @return true if the common goal card is completed, false otherwise
     */
    @Override
    public boolean check(Bookshelf bookshelf) {
        BookshelfTileSpot[][] shelf = bookshelf.getTileMatrix();
        int count = 0;
        boolean flag;
        int[] verifier = new int[6];

        //verifies if there are at least two rows with one tile of each type

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++) {
                if (shelf[j][i].getTileType() == TileType.PLANTS) {
                    verifier[0] += 1;
                } else if (shelf[j][i].getTileType() == TileType.FRAMES) {
                    verifier[1] += 1;
                } else if (shelf[j][i].getTileType() == TileType.BOOKS) {
                    verifier[2] += 1;
                } else if (shelf[j][i].getTileType() == TileType.CATS) {
                    verifier[3] += 1;
                } else if (shelf[j][i].getTileType() == TileType.GAMES) {
                    verifier[4] += 1;
                } else if (shelf[j][i].getTileType() == TileType.TROPHIES) {
                    verifier[5] += 1;
                }
            }
            flag = true;
            for (int k = 0; k < 6; k++) {
                if (verifier[k] != 1) {
                    flag = false;
                }
            }
            if (flag) {
                count++;
            }
            for (int k = 0; k < 6; k++) {
                verifier[k] = 0;
            }
        }
        if (count >=2) {
            return true;
        } else {
            return false;
        }
    }

}
