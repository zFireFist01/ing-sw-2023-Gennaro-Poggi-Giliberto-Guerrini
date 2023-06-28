package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;

/** This class represents the eleventh common goal card:
 *  "Five tiles of the same type forming a
 * diagonal."
 * @author Patrick Poggi
 */
public class    CommonGoalCard11 extends CommonGoalCard {

    public CommonGoalCard11() {
        super();
    }
    /**
     * constructor of the class CommonGoalCard11 that calls the constructor of the superclass
     * @param playersNum is the number of players in the game
     * @param secondInstance is true if there are 5 players in the game, false otherwise
     */
    public CommonGoalCard11(int playersNum, boolean secondInstance) {
        super(playersNum, secondInstance);
    }

    @Override
    public boolean check(Bookshelf bookshelf) {
        BookshelfTileSpot[][] bs = bookshelf.getTileMatrix();
        boolean res = false;
        res =       (
                        !bs[0][0].isEmpty()
                        && !bs[1][1].isEmpty() && bs[1][1].getTileType() == bs[0][0].getTileType()
                        && !bs[2][2].isEmpty() && bs[2][2].getTileType() == bs[0][0].getTileType()
                        && !bs[3][3].isEmpty() && bs[3][3].getTileType() == bs[0][0].getTileType()
                        && !bs[4][4].isEmpty() && bs[4][4].getTileType() == bs[0][0].getTileType()
                    )
                ||
                    (
                        !bs[1][0].isEmpty()
                        && !bs[2][1].isEmpty() && bs[2][1].getTileType() == bs[1][0].getTileType()
                        && !bs[3][2].isEmpty() && bs[3][2].getTileType() == bs[1][0].getTileType()
                        && !bs[4][3].isEmpty() && bs[4][3].getTileType() == bs[1][0].getTileType()
                        && !bs[5][4].isEmpty() && bs[5][4].getTileType() == bs[1][0].getTileType()
                    )
                ||
                    (
                        !bs[0][4].isEmpty()
                        && !bs[1][3].isEmpty() && bs[1][3].getTileType() == bs[0][4].getTileType()
                        && !bs[2][2].isEmpty() && bs[2][2].getTileType() == bs[0][4].getTileType()
                        && !bs[3][1].isEmpty() && bs[3][1].getTileType() == bs[0][4].getTileType()
                        && !bs[4][0].isEmpty() && bs[4][0].getTileType() == bs[0][4].getTileType()
                    )
                ||
                    (
                        !bs[1][4].isEmpty()
                        && !bs[2][3].isEmpty() && bs[2][3].getTileType() == bs[1][4].getTileType()
                        && !bs[3][2].isEmpty() && bs[3][2].getTileType() == bs[1][4].getTileType()
                        && !bs[4][1].isEmpty() && bs[4][1].getTileType() == bs[1][4].getTileType()
                        && !bs[5][0].isEmpty() && bs[5][0].getTileType() == bs[1][4].getTileType()
                    );
        return res;
    }

    @Override
    public int getCardID() {
        return 11;
    }

    @Override
    public String[] getCommonGoalDescription(){
        String[] description = new String[8];

        description[0] = "Five tiles of the same type";
        description[1] = "forming a diagonal.        ";
        description[2] = "                           ";
        description[3] = "                           ";
        description[4] = "                           ";
        description[5] = "                           ";
        description[6] = "                           ";
        description[7] = "                           ";
        return description;
    }
}
