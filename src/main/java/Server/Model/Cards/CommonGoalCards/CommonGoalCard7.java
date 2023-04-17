package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

public class CommonGoalCard7 extends CommonGoalCard {

    @Override
    public boolean check(Bookshelf bookshelf) {
        //4 rows of 5 tiles of 1, 2 or 3 different tipes
        int rowsFitting = 0;
        Set<TileType> rowTypes = new HashSet<>();
        BookshelfTileSpot[][] bsmat = bookshelf.getTileMatrix();
        boolean isRowOk = false; //Meaning: the row has satisfied the criteria SO FAR. I'll check its
                                    // value only at the end of the row
        boolean goOn = false;
        for(int i=0; i<6 && goOn; i++){
            for(int j=0;j<5;j++){
                if(bsmat[i][j].isEmpty()){
                    rowTypes.clear();
                    isRowOk = false;
                    break; // this row doesn't even have 5 tiles
                }else{
                    if(!rowTypes.contains(bsmat[i][j].getTileType())){
                        //It's  a new type inside the row;
                        if(rowTypes.size()+1>3){//If I add this new type it will be the 4th, which doesn't satisfy the criteria
                            rowTypes.clear();
                            isRowOk = false;
                            break; //This rows has too many types
                        }
                        rowTypes.add(bsmat[i][j].getTileType());
                    }
                    isRowOk = true;
                }
            }
            rowsFitting = (isRowOk ? rowsFitting+1:rowsFitting);
            goOn = ((rowsFitting >= 4-(6-(i+1)))&&rowsFitting<4 ? true:false);
        }
        return rowsFitting >= 4;
    }
    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 7;
    }
}
