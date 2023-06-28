package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;

/**
 *  This class represents the eighth common goal card:
 *  "Four tiles of the same type in the four
 *  corners of the bookshelf. "
 * @author Patrick Poggi
 */
public class CommonGoalCard8 extends CommonGoalCard {

    public CommonGoalCard8() {
        super();
    }

    /**
     * constructor of the class CommonGoalCard8 that calls the constructor of the superclass
     * @param playersNum is the number of players in the game
     * @param secondInstance is true if there are 5 players in the game, false otherwise
     */
    public CommonGoalCard8(int playersNum, boolean secondInstance) {
        super(playersNum, secondInstance);
    }

    @Override
    public boolean check(Bookshelf bookshelf) {
        BookshelfTileSpot[][] bsmat = bookshelf.getTileMatrix();
        return bsmat[0][0].isEmpty()==false && bsmat[0][bookshelf.getBookshelfWidth()-1].isEmpty()==false
                && bsmat[bookshelf.getBookshelfHeight()-1][0].isEmpty() == false &&
                bsmat[bookshelf.getBookshelfHeight()-1][bookshelf.getBookshelfWidth()-1].isEmpty() == false //not empty
                && //types equal
                bsmat[0][0].getTileType() == bsmat[0][bookshelf.getBookshelfWidth()-1].getTileType() &&
                bsmat[0][0].getTileType() == bsmat[bookshelf.getBookshelfHeight()-1][0].getTileType() &&
                bsmat[0][0].getTileType() == bsmat[bookshelf.getBookshelfHeight()-1][bookshelf.getBookshelfWidth()-1].getTileType()
                ;
    }

    @Override
    public String[] getCommonGoalDescription() {
        String[] description = new String[8];

        description[0] = "Four tiles of the same type";
        description[1] = "in the four corners of the ";
        description[2] = "bookshelf                  ";
        description[3] = "                           ";
        description[4] = "                           ";
        description[5] = "                           ";
        description[6] = "                           ";
        description[7] = "                           ";

        return description;
    }

    @Override
    public int getCardID() {
        return 8;
    }
}
