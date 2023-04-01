package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;

public class CommonGoalCard2 extends CommonGoalCard {

    private BookshelfTileSpot[][] b;

    @Override
    public boolean check(Bookshelf bookshelf) {

        b = bookshelf.getTileMatrix();

        if ((b[0][0].isEmpty()) || (b[0][4].isEmpty()) || (b[5][0].isEmpty()) || (b[5][4].isEmpty())) {
            return false;
        } else if ((b[5][0].getTileType().equals(b[5][4].getTileType())) &&
                (b[0][0].getTileType().equals(b[0][4].getTileType())) &&
                (b[0][0].getTileType().equals(b[5][0].getTileType()))) {
            return true;
        }

        return false;
    }

}
