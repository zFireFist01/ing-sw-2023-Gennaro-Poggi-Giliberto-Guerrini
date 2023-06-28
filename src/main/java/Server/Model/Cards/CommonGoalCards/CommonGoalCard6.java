package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.TileType;

/**
 * This class represents the sixth common goal card:
 * "Two lines each formed by 5 different
 * types of tiles. One line can show the
 * same or a different combination of the
 * other line."
 * @author Valentino Guerrini
 */
public class CommonGoalCard6 extends CommonGoalCard {

    public CommonGoalCard6(){
        super();
    }

    /**
     * constructor of the class CommonGoalCard6 that calls the constructor of the superclass
     * @param playersNum the number of players in the game
     * @param secondInstance true if it is the second card, false otherwise in order to know
     *                       if the card has to be created with the second instance of the
     *                       points tiles
     */
    public CommonGoalCard6(int playersNum, boolean secondInstance) {
        super(playersNum, secondInstance);
    }

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

            }
            if (count == 2) {
                return true;
            }
            count2=0;
            flag= new boolean[]{false, false, false, false, false,false};

        }
        return false;
    }

    @Override
    public int getCardID() {
        return 6;
    }

    public String[] getCommonGoalDescription(){
        String[] description = new String[8];

        description[0] = "Two lines each formed by 5 ";
        description[1] = "different types of tiles.  ";
        description[2] = "One line can show the same ";
        description[3] = "or a different combination ";
        description[4] = "of the other line          ";
        description[5] = "                           ";
        description[6] = "                           ";
        description[7] = "                           ";
        return description;
    }


}
