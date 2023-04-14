package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.TileType;

/**
 * This class represents the sixth common goal card
 * @author due2
 */

public class CommonGoalCard6 extends CommonGoalCard {

    /**
     * constructor of the class CommonGoalCard6 that calls the constructor of the superclass
     * @param playersNum the number of players in the game
     * @param secondIstance true if it is the second card, false otherwise in order to know if the card has to be created with the second instance of the points tiles
     */
    public CommonGoalCard6(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
    }
    /**
     * This method checks if the common goal card is completed if there are at least two rows with one tile of each type
     * @param bookshelf the bookshelf of the player
     * @return true if the common goal card is completed, false otherwise
     */
    @Override
    public boolean check(Bookshelf bookshelf) {
        int count = 0, count2 = 0;
        boolean flag[]= {false, false, false, false, false, false};
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (bookshelf.getTileMatrix()[i][j].getTileType() == TileType.PLANTS) {
                    flag[0] = true;
                } else if (bookshelf.getTileMatrix()[i][j].getTileType() == TileType.FRAMES) {
                    flag[1] = true;
                } else if (bookshelf.getTileMatrix()[i][j].getTileType() == TileType.BOOKS) {
                    flag[2] = true;
                } else if (bookshelf.getTileMatrix()[i][j].getTileType() == TileType.CATS) {
                    flag[3] = true;
                } else if (bookshelf.getTileMatrix()[i][j].getTileType() == TileType.GAMES) {
                    flag[4] = true;
                } else if (bookshelf.getTileMatrix()[i][j].getTileType() == TileType.TROPHIES) {
                    flag[5] = true;
                }
            }
            for (int k = 0; k < 6; k++) {
                if (!flag[k])
                    count2++;

            }
            if (count2 == 1) {
                count++;
                count2=0;
            }
            if (count == 2) {
                return true;
            }
            flag= new boolean[]{false, false, false, false, false,false};

        }
        return false;
    }
}
