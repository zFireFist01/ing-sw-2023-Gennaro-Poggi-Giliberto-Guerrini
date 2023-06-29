package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;
/**
 * This class represents the third common goal card:
 * "Four groups each containing at least
 * 4 tiles of the same type (not necessarily
 * in the depicted shape).
 * The tiles of one group can be different
 * from those of another group."
 * @author Valentino Guerrini
 */

public class CommonGoalCard3 extends CommonGoalCard {

    public CommonGoalCard3(){
        super();
    }

    /**
     * constructor of the class CommonGoalCard3 that calls the constructor of the superclass
     * @param playersNum the number of players in the game
     * @param secondInstance true if it is the second card, false otherwise in order to know
     *                      if the card has to be created with the second instance of the
     *                      points tiles
     */
    public CommonGoalCard3(int playersNum, boolean secondInstance) {
        super(playersNum, secondInstance);
    }

    @Override
    public boolean check(Bookshelf bookshelf) {
        BookshelfTileSpot[][] shelf = bookshelf.getTileMatrix();
        int count = 0;
        int[][] verifier = new int[6][5];

        //verifies if there are at least four 1x4 columns with the same tile type
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
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                if(verifier[i][j] != 0) {
                    int tile=verifier[i][j];
                    //verifier[i][j]=0;
                    int tmp =countAdjacentTiles(verifier, i, j, tile);
                    count+=(int)(tmp/4);

                }
            }
        }
        if (count >= 4) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * This method counts the number of adjacent tiles with the same tile type
     * @requires tileType !=0
     * @param shelf is an int matrix that was created starting from a bookshelf
     *              of tiles, associating a different number to each tileType
     * @param i index of row of the shelf
     * @param j index of column of the shelf
     * @param tileType is an int associated to the specific tileType I want to
     *                 find the count of adjacent tiles
     * @return the count of adjacent tiles of the same type as tileType
     */
    private int countAdjacentTiles(int[][] shelf, int i, int j, int tileType) {
        if (i < 0 || i >= shelf.length || j < 0 || j >= shelf[0].length || shelf[i][j] != tileType) {
            return 0;
        }

        // Mark the tile as visited
        shelf[i][j] = 0;

        // Count the tile and look for more in each direction
        return 1 + countAdjacentTiles(shelf, i + 1, j, tileType)
                + countAdjacentTiles(shelf, i - 1, j, tileType)
                + countAdjacentTiles(shelf, i, j + 1, tileType)
                + countAdjacentTiles(shelf, i, j - 1, tileType);
    }

    @Override
    public String[] getCommonGoalDescription(){
        String[] description = new String[8];

        description[0] = "Four groups each containing";
        description[1] = "at least 4 tiles of the    ";
        description[2] = "same type (not necessarily ";
        description[3] = "in the depicted shape).    ";
        description[4] = "The tiles of one group can ";
        description[5] = "be different from those of ";
        description[6] = "another group.             ";
        description[7] = "                           ";
        return description;
    }
    @Override
    public int getCardID() {
        return 3;
    }
}


