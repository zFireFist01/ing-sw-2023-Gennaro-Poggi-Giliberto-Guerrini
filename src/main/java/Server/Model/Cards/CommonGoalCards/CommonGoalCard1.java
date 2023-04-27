package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

/**
 * This class represents the first common goal card
 * @author due2
 */

public class CommonGoalCard1 extends CommonGoalCard {

    /**
     * constructor of the class CommonGoalCard1 that calls the constructor of the superclass
     * @param playersNum the number of players in the game
     * @param secondIstance true if it is the second card, false otherwise in order to know if the card has to be created with the second instance of the points tiles
     */
    public CommonGoalCard1(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
    }

    /**
     * This method checks if the common goal card is completed
     * @param bookshelf the bookshelf of the player
     * @return true if the common goal card is completed, false otherwise
     */
    @Override
    public boolean check(Bookshelf bookshelf) {
        BookshelfTileSpot[][] shelf = bookshelf.getTileMatrix();
        int count = 0;
        int[][] verifier = new int[6][5];
        //verify if the 6x5 matrix contains two 2x2 submatrix of the same tyle type
        for (TileType type : TileType.values()) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    if (shelf[i][j].getTileType() == type)
                        verifier[i][j] = 1;
                    else
                        verifier[i][j] = 0;

                }
            }
            for (int i = 0; i < 5 && count < 2; i++) {
                for (int j = 0; j < 4; j++) {
                    if (verifier[i][j] == 1 && verifier[i][j + 1] == 1 && verifier[i + 1][j] == 1 && verifier[i + 1][j + 1] == 1) {
                        count++;
                        verifier[i][j] = 0;
                        verifier[i][j + 1] = 0;
                        verifier[i + 1][j] = 0;
                        verifier[i + 1][j + 1] = 0;
                    }
                }
            }
        }
        if (count == 2) {
            return true;
        }




        return false;
    }

    public char[][] getCLIRepresentation(){
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
        res[1][11] = '=';
        res[1][9] = '=';
        res[3][11] = '=';
        res[3][9] = '=';

        res[9][3] = '=';
        res[9][5] = '=';
        res[11][3] = '=';
        res[11][5] = '=';

        return res;
    }

    public String[] getCommonGoalDescription(){
        String[] description = new String[8];

        description[0] = "Two groups each containing ";
        description[1] = "at least 4 tiles of the    ";
        description[2] = "same type in a 2x2 square. ";
        description[3] = "The tiles of one square can";
        description[4] = "be different from those of ";
        description[5] = "the other square.          ";
        description[6] = "                           ";
        description[7] = "                           ";
        return description;
    }

    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 1;
    }
}
