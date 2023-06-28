package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

import java.util.HashMap;
import java.util.Map;

/**
 *  This class represents the ninth common goal card:
 *  "Eight tiles of the same type. There’s no
 *  restriction about the position of these
 *  tiles. "
 * @author Patrick Poggi
 */
public class CommonGoalCard9 extends CommonGoalCard {

    public CommonGoalCard9() {
        super();
    }

    /**
     * constructor of the class CommonGoalCard9 that calls the constructor of the superclass
     * @param playersNum is the number of players in the game
     * @param secondInstance is true if there are 5 players in the game, false otherwise
     */
    public CommonGoalCard9(int playersNum, boolean secondInstance) {
        super(playersNum, secondInstance);
    }

    @Override
    public boolean check(Bookshelf bookshelf) {
        Map<TileType, Integer> count = new HashMap<>();
        BookshelfTileSpot[][] bsmat = bookshelf.getTileMatrix();

        for(TileType t : TileType.values()){
            count.put(t,0);
        }
        for(int i=0;i< bookshelf.getBookshelfHeight();i++){
            for(int j=0;j< bookshelf.getBookshelfWidth();j++){
                if(bsmat[i][j].isEmpty() == false){
                    TileType tt = bsmat[i][j].getTileType();
                    if (count.get(tt) >= 7) {
                        return true;
                    }
                    int temp = count.get(tt);
                    count.put(tt, ++temp);
                }
            }
        }

        return false;
    }

    @Override
    public String[] getCommonGoalDescription() {
        String[] description = new String[8];

        description[0] = "Eight tiles of the same    ";
        description[1] = "type. There is no restrict-";
        description[2] = "ion about the postion of   ";
        description[3] = "these tiles.               ";
        description[4] = "                           ";
        description[5] = "                           ";
        description[6] = "                           ";
        description[7] = "                           ";

        return description;
    }

    @Override
    public int getCardID() {
        return 9;
    }
}
