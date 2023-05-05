package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

import java.util.HashSet;
import java.util.Set;

public class CommonGoalCard7 extends CommonGoalCard {


    public CommonGoalCard7() {
        super();
    }

    /**
     * Constructor for CommonGoalCard7
     * @param playersNum is the number of players in the game
     * @param secondIstance is true if there are 5 players in the game, false otherwise
     */

    public CommonGoalCard7(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
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

    /**
     * This method returns a CLI representation (ASCII-art like) of the 7th common goal card
     * @return the char[][] matrix of the representation
     * @author patrickpoggi
     */
    @Override
    public char[][] getCLIRepresentation() {
        char[][] res  = new char[13][15];

        for(int i=0;i<13;i++){
            res[i][1] = ' ';
            res[i][13] = ' ';
            res[i][0] = '|';
            res[i][14] = '|';
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
        res[1][5] = 'C';
        res[1][7] = 'C';
        res[1][9] = 'C';
        res[1][11] = 'C';

        res[3][3] = 'B';
        res[3][5] = 'B';
        res[3][7] = 'B';
        res[3][9] = 'B';
        res[3][11] = 'B';

        res[5][3] = ' ';
        res[5][3] = ' ';
        res[5][3] = ' ';
        res[5][3] = ' ';
        res[5][3] = ' ';

        res[7][3] = 'T';
        res[7][5] = 'T';
        res[7][7] = 'T';
        res[7][9] = 'T';
        res[7][11] = 'T';

        res[9][3] = ' ';
        res[9][3] = ' ';
        res[9][3] = ' ';
        res[9][3] = ' ';
        res[9][3] = ' ';

        res[11][3] = 'C';
        res[11][5] = 'C';
        res[11][7] = 'C';
        res[11][9] = 'C';
        res[11][11] = 'C';

        return res;
    }

    /**
     * This method returns a CLI representation (ASCII-art like) of the 7th common goal card's description
     * @return the String[] "matrix" of the representation
     * @author patrickpoggi
     */
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
    /*
    /**
     * @return a string that describes the 7th common goal
     * @author patrickpoggi
     */

    /*
    @Override
    public String getDescription() {
        return "Four lines each formed by 5 tiles of maximum three different types. One line can show the same or " +
                "a different combination of another line";
    }
    */

    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 7;
    }
}
