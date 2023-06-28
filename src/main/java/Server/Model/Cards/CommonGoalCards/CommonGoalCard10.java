package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

/**
 *  This class represents the tenth common goal card:
 *  "Five tiles of the same type forming an X. "
 * @author Patrick Poggi
 */
public class CommonGoalCard10 extends CommonGoalCard {

    public CommonGoalCard10() {
        super();
    }

    /**
     * constructor of the class CommonGoalCard10 that calls the constructor of the superclass
     * @param playersNum is the number of players in the game
     * @param secondInstance is true if there are 5 players in the game, false otherwise
     */
    public CommonGoalCard10(int playersNum, boolean secondInstance) {
        super(playersNum, secondInstance);
    }

    @Override
    public boolean check(Bookshelf bookshelf) {
        //5 tiles pf the same kind forming an X in the bookshelf
        BookshelfTileSpot[][] bsmat = bookshelf.getTileMatrix();

        for(int i=0; i<bookshelf.getBookshelfHeight()-2;i++){
            for(int j=0; j< bookshelf.getBookshelfWidth()-2;j++){
                TileType tt = bsmat[i][j].getTileType();
                if(
                        bsmat[i][j].isEmpty() == false
                        && bsmat[i+1][j+1].isEmpty() == false
                        && bsmat[i][j+2].isEmpty() == false
                        && bsmat[i+2][j].isEmpty() == false
                        && bsmat[i+2][j+2].isEmpty() == false
                        &&
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

    @Override
    public int getCardID() {
        return 10;
    }

    @Override
    public String[] getCommonGoalDescription(){
        String[] description = new String[8];
        description[0] = "Five tiles of the same type";
        description[1] = "forming an X.              ";
        description[2] = "                           ";
        description[3] = "                           ";
        description[4] = "                           ";
        description[5] = "                           ";
        description[6] = "                           ";
        description[7] = "                           ";
        return description;
    }
}
