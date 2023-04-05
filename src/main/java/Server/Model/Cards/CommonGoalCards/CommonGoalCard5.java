package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;
/**
 * This class represents the fifth common goal card
 * @author due2
 */

public class CommonGoalCard5 extends CommonGoalCard {

    /**
     * This method checks if the common goal card is completed if the 6x5 matrix contains three rows of at most 3 different tile types each
     * @param bookshelf the bookshelf of the player
     * @return true if the common goal card is completed, false otherwise
     */

    @Override
    public boolean check(Bookshelf bookshelf) {
        BookshelfTileSpot[][] shelf = bookshelf.getTileMatrix();
        int count = 0;
        boolean flag;
        int countdifference = 0;
        int[][] verifier = new int[6][5];
        //verify if the 6x5 matrix contains three rows of at most 3 different tile types each
        for (TileType type : TileType.values()){
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    if (shelf[i][j].getTileType() == type)
                        verifier[i][j] = 2;
                    else if(shelf[i][j].getTileType() == null)
                        verifier[i][j] = 0;
                    else
                        verifier[i][j] = 1;

                }
            }
            flag= true;
            for (int i = 0; i < 5 && flag; i++) {
                for(int j=0;j<6;j++){
                    if(verifier[j][i]==0){
                        flag=false;
                    }else if(verifier[j][i]==1){
                        countdifference++;
                    }
                }
                if(countdifference>3){
                    flag=false;
                }
                if(flag){
                    count++;
                }
                countdifference=0;
            }

        }
        if(count>=3){
            return true;
        }
        else {
            return false;
        }

    }
}
