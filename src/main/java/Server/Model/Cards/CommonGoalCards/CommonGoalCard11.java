package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

public class CommonGoalCard11 extends CommonGoalCard {
    public CommonGoalCard11(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
    }

    /**
     *
     * @param bookshelf the bookshelf of the player
     * @return true if and only if the player has achieved the 11th common goal (5 tiles of the same type in a diagonal)
     */
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
                        && !bs[1][3].isEmpty() && bs[1][1].getTileType() == bs[0][4].getTileType()
                        && !bs[2][2].isEmpty() && bs[2][2].getTileType() == bs[0][4].getTileType()
                        && !bs[3][1].isEmpty() && bs[3][3].getTileType() == bs[0][4].getTileType()
                        && !bs[4][0].isEmpty() && bs[4][4].getTileType() == bs[0][4].getTileType()
                    )
                ||
                    (
                        !bs[1][4].isEmpty()
                        && !bs[2][3].isEmpty() && bs[1][1].getTileType() == bs[1][4].getTileType()
                        && !bs[3][2].isEmpty() && bs[2][2].getTileType() == bs[1][4].getTileType()
                        && !bs[4][1].isEmpty() && bs[3][3].getTileType() == bs[1][4].getTileType()
                        && !bs[5][0].isEmpty() && bs[4][4].getTileType() == bs[1][4].getTileType()
                    );
        return res;
    }
    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 11;
    }
}
