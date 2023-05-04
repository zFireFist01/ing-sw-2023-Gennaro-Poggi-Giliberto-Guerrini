package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;
/**
 * This class represents the fourth common goal card
 * @author due2
 */

public class CommonGoalCard4 extends CommonGoalCard {

    public CommonGoalCard4(){
        super();
    }

    /**
     * constructor of the class CommonGoalCard4 that calls the constructor of the superclass
     * @param playersNum the number of players in the game
     * @param secondIstance true if it is the second card, false otherwise in order to know if the card has to be created with the second instance of the points tiles
     */
    public CommonGoalCard4(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
    }

    /**
     * This method checks if the common goal card is completed if there are at least six 2x1 or 1x2 tiles with the same tile type
     * @param bookshelf the bookshelf of the player
     * @return true if the common goal card is completed, false otherwise
     */
    @Override
    public boolean check(Bookshelf bookshelf) {
        BookshelfTileSpot[][] shelf = bookshelf.getTileMatrix();
        int count = 0;
        int[][] verifier = new int[6][5];

        //verifies if there are at least six 2x1 rows with the same tile type
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if (shelf[i][j].getTileType() == TileType.PLANTS) {
                    verifier[i][j] = 1;
                } else if (shelf[i][j].getTileType() == TileType.FRAMES) {
                    verifier[i][j] = 2;
                } else if (shelf[i][j].getTileType() == TileType.BOOKS) {
                    verifier[i][j] = 3;
                } else if (shelf[i][j].getTileType() == TileType.CATS) {
                    verifier[i][j] = 4;
                } else if (shelf[i][j].getTileType() == TileType.GAMES) {
                    verifier[i][j] = 5;
                } else if (shelf[i][j].getTileType() == TileType.TROPHIES) {
                    verifier[i][j] = 6;
                }
            }
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (verifier[i][j] == verifier[i+1][j] && verifier[i][j] != 0) {
                    count++;
                    verifier[i][j] = 0;
                    verifier[i+1][j] = 0;
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 4; j++) {
                if (verifier[i][j] == verifier[i][j+1] && verifier[i][j] != 0) {
                    count++;
                    verifier[i][j] = 0;
                    verifier[i][j+1] = 0;
                }
            }
        }

        if (count >= 6) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public char[][] getCLIRepresentation(){
        char[][] res = new char[13][15];

        for(int i = 0; i<13; i++){
            for(int j = 0; j<15; j++){
                if(j == 0 || j == 14){
                    res[i][j] = '|';
                }else{
                    res[i][j] = ' ';
                }
            }
        }

        res[4][5] = '+';
        res[4][6] = '-';
        res[4][7] = '+';
        res[5][5] = '+';
        res[5][6] = 'X';
        res[5][7] = '+';
        res[6][5] = '+';
        res[6][6] = '-';
        res[6][7] = '+';
        res[7][5] = '+';
        res[7][6] = 'X';
        res[7][7] = '+';
        res[8][5] = '+';
        res[8][6] = '-';
        res[8][7] = '+';

        return res;
    }

    @Override
    public String[] getCommonGoalDescription(){
        String[] description = new String[8];

        description[0] = "Six groups each containing ";
        description[1] = "at least 2 tiles of the    ";
        description[2] = "same type (not necessarily ";
        description[3] = "in the depicted shape).    ";
        description[4] = "The tiles of one group can ";
        description[5] = "be different from those of ";
        description[6] = "the other square.          ";
        description[7] = "                           ";
        return description;
    }


    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 4;
    }

}


