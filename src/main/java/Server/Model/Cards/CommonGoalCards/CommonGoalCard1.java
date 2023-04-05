package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

/**
 * This class represents the first common goal card
 * @author due2
 */

public class CommonGoalCard1 extends CommonGoalCard {

    /**
     * This method checks if the common goal card is completed
     * @param bookshelf the bookshelf of the player
     * @return true if the common goal card is completed, false otherwise
     */
    @Override
    public boolean check(Bookshelf bookshelf) {
        BookshelfTileSpot[][] shelf = bookshelf.getTileMatrix();
        int count = 0;
        int[][] verifier = new int[6][5];
        //verify if the 6x5 matrix contains two 2x2 submatrix of the same tyle type
        for (TileType type : TileType.values()){
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    if (shelf[i][j].getTileType() == type)
                        verifier[i][j] = 1;
                    else
                        verifier[i][j] = 0;

                }
            }
            for (int i = 0; i < 5 && count<2; i++) {
                for (int j = 0; j < 4; j++) {
                    if (verifier[i][j] == 1 && verifier[i][j + 1] == 1 && verifier[i + 1][j] == 1 && verifier[i + 1][j + 1] == 1) {
                        count++;
                        verifier[i][j] = 0;
                        verifier[i][j + 1] = 0;
                        verifier[i + 1][j] = 0;
                        verifier[i + 1][j + 1] = 0;
                    }
                }
            }
            if (count == 2) {
                return true;
            }else {
                count = 0;
            }
        }


        return false;
    }
}
