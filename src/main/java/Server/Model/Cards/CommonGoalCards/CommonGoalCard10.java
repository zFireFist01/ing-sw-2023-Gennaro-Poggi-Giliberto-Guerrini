package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

public class CommonGoalCard10 extends CommonGoalCard {
    /*
     bsmat[i][j].getTileType() == bsmat[i+1][j+1].getTileType()
                        && bsmat[i][j].getTileType() == bsmat[i][j+2].getTileType()
                        && bsmat[i][j].getTileType() == bsmat[i+2][j].getTileType()
                        && bsmat[i][j].getTileType() == bsmat[i+2][j+2].getTileType()
     */

    @Override
    public boolean check(Bookshelf bookshelf) {
        //5 tiles pf the same kind forming an X in the bookshelf
        BookshelfTileSpot[][] bsmat = bookshelf.getTileMatrix();
        boolean[][] mask = new boolean[bookshelf.getBookshelfWidth()][bookshelf.getBookshelfHeight()];

        for(int i=0; i<bookshelf.getBookshelfHeight()-2;i++){
            for(int j=0; j< bookshelf.getBookshelfWidth()-2;j++){
                TileType tt = bsmat[i][j].getTileType();
                if(
                        bsmat[i+1][j+1].getTileType() == tt
                        && bsmat[i][j+2].getTileType() == tt
                        && bsmat[i+2][j].getTileType() == tt
                        && bsmat[i+2][j+2].getTileType() == tt
                ){
                    //All the types are equal in a X disposition
                    return true;
                }
            }
        }

        return false;
    }
}
