package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;
/**
 * This class represents the fourth common goal card
 * @author due2
 */

public class CommonGoalCard4 extends CommonGoalCard {

    /**
     * constructor of the class CommonGoalCard4 that calls the constructor of the superclass
     * @param playersNum the number of players in the game
     * @param secondIstance true if it is the second card, false otherwise in order to know if the card has to be created with the second instance of the points tiles
     */
    public CommonGoalCard4(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
    }

    /**
     * This method checks if the common goal card is completed if there are at least six 2x1 or 1x2 tiles with the same tile type
     * @param bookshelf the bookshelf of the player
     * @return true if the common goal card is completed, false otherwise
     */
    @Override
    public boolean check(Bookshelf bookshelf) {
        BookshelfTileSpot[][] shelf = bookshelf.getTileMatrix();
        int count = 0;
        int[][] verifier = new int[6][5];

        //verifies if there are at least six 2x1 rows with the same tile type
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
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (verifier[i][j] == verifier[i+1][j] && verifier[i][j] != 0) {
                    count++;
                    verifier[i][j] = 0;
                    verifier[i+1][j] = 0;
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (verifier[i][j] == verifier[i][j+1] && verifier[i][j] != 0) {
                    count++;
                    verifier[i][j] = 0;
                    verifier[i][j+1] = 0;
                }
            }
        }

        if (count >= 6) {
            return true;
        } else {
            return false;
        }

    }
    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 4;
    }

}
