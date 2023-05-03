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

    public CommonGoalCard5(){
        super();
    }

    /**
     * constructor of the class CommonGoalCard5 that calls the constructor of the superclass
     * @param playersNum the number of players in the game
     * @param secondIstance true if it is the second card, false otherwise in order to know if the card has to be created with the second instance of the points tiles
     */
    public CommonGoalCard5(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
    }

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
            for (int i = 0; i < 5 ; i++) {
                for(int j=0;j<6 && flag;j++){
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
                flag=true;

            }

        }
        if(count>=3){
            return true;
        }
        else {
            return false;
        }

    }
    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 5;
    }


    public char[][] getCLIRepresentation() {
        char[][] res = new char[13][15];

        for(int i = 0; i<13; i++){
            for(int j = 0; j<15; j++){
                if(j ==0 || j == 14){
                    res[i][j] = '|';
                }
            }
        }

        res[0][5] = '+';
        res[0][6] = '-';
        res[0][7] = '+';
        res[1][5] = '+';
        res[1][7] = '+';
        res[2][5] = '+';
        res[2][6] = '-';
        res[2][7] = '+';
        res[3][5] = '+';
        res[3][7] = '+';
        res[4][5] = '+';
        res[4][6] = '-';
        res[4][7] = '+';
        res[5][5] = '+';
        res[5][7] = '+';
        res[6][5] = '+';
        res[6][6] = '-';
        res[6][7] = '+';
        res[7][5] = '+';
        res[7][7] = '+';
        res[8][5] = '+';
        res[8][6] = '-';
        res[8][7] = '+';
        res[9][5] = '+';
        res[9][7] = '+';
        res[10][5] = '+';
        res[10][6] = '-';
        res[10][7] = '+';
        res[11][5] = '+';
        res[11][7] = '+';
        res[12][5] = '+';
        res[12][6] = '-';
        res[12][7] = '+';

        return res;
    }

    public String[] getCommonGoalDescription(){
        String[] description = new String[8];

        description[0] = "Three columns each formed  ";
        description[1] = "by 6 tiles of maximum three";
        description[2] = "different types. One column";
        description[3] = "can show the same or a     ";
        description[4] = "different combination of   ";
        description[5] = "another column.            ";
        description[6] = "                           ";
        description[7] = "                           ";
        return description;
    }


}



