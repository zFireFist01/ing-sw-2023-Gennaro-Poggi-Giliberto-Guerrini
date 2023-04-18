package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

import java.util.HashMap;
import java.util.Map;

public class CommonGoalCard9 extends CommonGoalCard {

    public CommonGoalCard9(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
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
                TileType tt = bsmat[i][j].getTileType();
                if(count.get(tt) >= 7){
                    return true;
                }
                int temp = count.get(tt);
                count.put(tt, ++temp);
            }
        }

        return false;
    }
}
