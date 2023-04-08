package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;

public class CommonGoalCard8 extends CommonGoalCard {

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
}
