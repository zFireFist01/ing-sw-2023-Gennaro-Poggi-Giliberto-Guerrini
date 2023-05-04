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
                if(bsmat[i][j].isEmpty() == false){
                    TileType tt = bsmat[i][j].getTileType();
                    if (count.get(tt) >= 8) {
                        return true;
                    }
                    int temp = count.get(tt);
                    count.put(tt, ++temp);
                }
            }
        }

        return false;
    }


    /**
     * This method returns a CLI representation (ASCII-art like) of the 9th common goal card
     * @return the char[][] matrix of the representation
     * @author patrickpoggi
     */
    @Override
    public char[][] getCLIRepresentation() {
        char[][] res  = new char[13][15];
        res[0][0] = '|';
        res[0][14] = '|';
        res[12][0] = '|';
        res[12][14] = '|';
        for(int i=0;i<13;i++){
            res[i][1] = ' ';
            res[i][13] = ' ';
        }
        for(int i=0;i<13;i++){
            for(int j=2;j<13;j++){
                if(i%2 == 0){
                    if(j%2 == 0){
                        res[i][j] = '+';
                    }else{
                        res[i][j] = '-';
                    }
                }else{
                    if(j%2 == 0){
                        res[i][j] = '|';
                    }else{
                        res[i][j] = ' ';
                    }
                }
            }
        }

        res[1][3] = 'C';
        res[3][5] = 'C';
        res[3][11] = 'C';
        res[7][3] = 'C';
        res[7][5] = 'C';
        res[7][9] = 'C';
        res[9][7] = 'C';
        res[11][11] = 'C';

        return res;
    }

    /**
     * This method returns a CLI representation (ASCII-art like) of the 9th common goal card's description
     * @return the String[] "matrix" of the representation
     * @author patrickpoggi
     */
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

    /**
     * @return a string that describes the 9th common goal
     * @author patrickpoggi
     */

    /*
    @Override
    public String getDescription() {
        return "Eight tiles of the same type. There is no restriction about the postion of these tiles";
    }
    */
    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 9;
    }
}
