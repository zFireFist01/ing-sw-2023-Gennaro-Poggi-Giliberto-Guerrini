package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

import java.util.HashSet;
import java.util.Set;

/**
 *  This class represents the seventh common goal card:
 *  "Four lines each formed by 5 tiles of
 *  maximum three different types. One
 *  line can show the same or a different
 *  combination of another line."
 * @author Patrick Poggi
 */
public class CommonGoalCard7 extends CommonGoalCard {

    public CommonGoalCard7() {
        super();
    }

    /**
     * constructor of the class CommonGoalCard7 that calls the constructor of the superclass
     * @param playersNum is the number of players in the game
     * @param secondInstance is true if there are 5 players in the game, false otherwise
     */
    public CommonGoalCard7(int playersNum, boolean secondInstance) {
        super(playersNum, secondInstance);
    }

    @Override
    public boolean check(Bookshelf bookshelf) {
        //4 rows of 5 tiles of 1, 2 or 3 different tipes
        int rowsFitting = 0;
        Set<TileType> rowTypes = new HashSet<>();
        BookshelfTileSpot[][] bsmat = bookshelf.getTileMatrix();
        boolean isRowOk = false; //Meaning: the row has satisfied the criteria SO FAR. I'll check its
                                    // value only at the end of the row
        boolean goOn = true;
        for(int i=0; i<6 && goOn; i++){
            for(int j=0;j<5;j++){
                if(bsmat[i][j].isEmpty()){
                    rowTypes.clear();
                    isRowOk = false;
                    break; // this row doesn't even have 5 tiles
                }else{
                    if(!rowTypes.contains(bsmat[i][j].getTileType())){
                        //It's  a new type inside the row;
                        if(rowTypes.size()+1>3){//If I add this new type it will be the 4th: that doesn't satisfy the criteria
                            rowTypes.clear();
                            isRowOk = false;
                            break; //This rows has too many types
                        }
                        rowTypes.add(bsmat[i][j].getTileType());
                    }
                    isRowOk = true;
                }
            }
            rowTypes.clear();
            rowsFitting = (isRowOk ? rowsFitting+1:rowsFitting);
            goOn = ((rowsFitting >= 4-(6-(i+1)))&&rowsFitting<4 ? true:false);
        }
        return rowsFitting >= 4;
    }

    @Override
    public String[] getCommonGoalDescription() {
        String[] description = new String[8];

        description[0] = "Four lines each formed by 5";
        description[1] = "tiles of maximum three dif-";
        description[2] = "ferent types. One line can ";
        description[3] = "show the same or a         ";
        description[4] = "different combination of   ";
        description[5] = "another line               ";
        description[6] = "                           ";
        description[7] = "                           ";

        return description;
    }

    @Override
    public int getCardID() {
        return 7;
    }
}
