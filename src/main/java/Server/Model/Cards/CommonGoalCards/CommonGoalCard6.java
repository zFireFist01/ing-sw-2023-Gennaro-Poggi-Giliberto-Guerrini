package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.TileType;

/**
 * This class represents the sixth common goal card
 * @author due2
 */

public class CommonGoalCard6 extends CommonGoalCard {

    /**
     * constructor of the class CommonGoalCard6 that calls the constructor of the superclass
     * @param playersNum the number of players in the game
     * @param secondIstance true if it is the second card, false otherwise in order to know if the card has to be created with the second instance of the points tiles
     */
    public CommonGoalCard6(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
    }
    /**
     * This method checks if the common goal card is completed if there are at least two rows with one tile of each type
     * @param bookshelf the bookshelf of the player
     * @return true if the common goal card is completed, false otherwise
     */
    @Override
    public boolean check(Bookshelf bookshelf) {
        int count = 0, count2 = 0;
        boolean flag[]= {false, false, false, false, false, false};
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (bookshelf.getTileMatrix()[i][j].getTileType() == TileType.PLANTS) {
                    flag[0] = true;
                } else if (bookshelf.getTileMatrix()[i][j].getTileType() == TileType.FRAMES) {
                    flag[1] = true;
                } else if (bookshelf.getTileMatrix()[i][j].getTileType() == TileType.BOOKS) {
                    flag[2] = true;
                } else if (bookshelf.getTileMatrix()[i][j].getTileType() == TileType.CATS) {
                    flag[3] = true;
                } else if (bookshelf.getTileMatrix()[i][j].getTileType() == TileType.GAMES) {
                    flag[4] = true;
                } else if (bookshelf.getTileMatrix()[i][j].getTileType() == TileType.TROPHIES) {
                    flag[5] = true;
                }
            }
            for (int k = 0; k < 6; k++) {
                if (!flag[k])
                    count2++;

            }
            if (count2 == 1) {
                count++;

            }
            if (count == 2) {
                return true;
            }
            count2=0;
            flag= new boolean[]{false, false, false, false, false,false};

        }
        return false;
    }
    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 6;
    }


    @Override
    public char[][] getCLIRepresentation() {
        char[][] res = new char[13][15];

        for(int i = 0; i<13; i++){
            for(int j = 0; j<15; j++){
                if(j ==0 || j == 14){
                    res[i][j] = '|';
                }
            }
        }
        res[4][2] = '+';
        res[4][3] = '-';
        res[4][4] = '+';
        res[4][5] = '-';
        res[4][6] = '+';
        res[4][7] = '-';
        res[4][8] = '+';
        res[4][9] = '-';
        res[4][10] = '+';
        res[4][11] = '-';
        res[4][12] = '+';
        res[5][2] = '|';
        res[5][4] = '|';
        res[5][6] = '|';
        res[5][8] = '|';
        res[5][10] = '|';
        res[5][12] = '|';
        res[6][2] = '+';
        res[6][3] = '-';
        res[6][4] = '+';
        res[6][5] = '-';
        res[6][6] = '+';
        res[6][7] = '-';
        res[6][8] = '+';
        res[6][9] = '-';
        res[6][10] = '+';
        res[6][11] = '-';
        res[6][12] = '+';

        return res;
    }

    public String[] getCommonGoalDescription(){
        String[] description = new String[7];

        description[0] = "Two lines each formed by 5 ";
        description[1] = "different types of tiles.  ";
        description[2] = "One line can show the same ";
        description[3] = "or a different combination ";
        description[4] = "of the other line          ";
        description[5] = "                           ";
        description[6] = "                           ";

        return description;
    }


}
