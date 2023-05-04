package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;

public class    CommonGoalCard11 extends CommonGoalCard {

    public CommonGoalCard11() {
        super();
    }
    public CommonGoalCard11(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
    }

    /**
     *
     * @param bookshelf the bookshelf of the player
     * @return true if and only if the player has achieved the 11th common goal (5 tiles of the same type in a diagonal)
     */
    @Override
    public boolean check(Bookshelf bookshelf) {
        BookshelfTileSpot[][] bs = bookshelf.getTileMatrix();
        boolean res = false;
        res =       (
                        !bs[0][0].isEmpty()
                        && !bs[1][1].isEmpty() && bs[1][1].getTileType() == bs[0][0].getTileType()
                        && !bs[2][2].isEmpty() && bs[2][2].getTileType() == bs[0][0].getTileType()
                        && !bs[3][3].isEmpty() && bs[3][3].getTileType() == bs[0][0].getTileType()
                        && !bs[4][4].isEmpty() && bs[4][4].getTileType() == bs[0][0].getTileType()
                    )
                ||
                    (
                        !bs[1][0].isEmpty()
                        && !bs[2][1].isEmpty() && bs[2][1].getTileType() == bs[1][0].getTileType()
                        && !bs[3][2].isEmpty() && bs[3][2].getTileType() == bs[1][0].getTileType()
                        && !bs[4][3].isEmpty() && bs[4][3].getTileType() == bs[1][0].getTileType()
                        && !bs[5][4].isEmpty() && bs[5][4].getTileType() == bs[1][0].getTileType()
                    )
                ||
                    (
                        !bs[0][4].isEmpty()
                        && !bs[1][3].isEmpty() && bs[1][3].getTileType() == bs[0][4].getTileType()
                        && !bs[2][2].isEmpty() && bs[2][2].getTileType() == bs[0][4].getTileType()
                        && !bs[3][1].isEmpty() && bs[3][1].getTileType() == bs[0][4].getTileType()
                        && !bs[4][0].isEmpty() && bs[4][0].getTileType() == bs[0][4].getTileType()
                    )
                ||
                    (
                        !bs[1][4].isEmpty()
                        && !bs[2][3].isEmpty() && bs[2][3].getTileType() == bs[1][4].getTileType()
                        && !bs[3][2].isEmpty() && bs[3][2].getTileType() == bs[1][4].getTileType()
                        && !bs[4][1].isEmpty() && bs[4][1].getTileType() == bs[1][4].getTileType()
                        && !bs[5][0].isEmpty() && bs[5][0].getTileType() == bs[1][4].getTileType()
                    );
        return res;
    }
    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 11;
    }

    /**
     * this method return the CLI representation of CommonGoalCard11
     * @return a matrix of char that represents the CommonGoalCard11
     * @author martagiliberto
     */
    @Override
    public char[][] getCLIRepresentation() {
        char[][] res = new char[13][15];

        for (int i = 0; i < 13; i++) {
            res[i][0] = '|';
            res[i][14] = '|';
        }

        res[1][2]  ='+';
        res[1][4]  ='+';
        res[3][2]  ='+';
        res[3][4]  ='+';
        res[3][6]  ='+';
        res[5][4]  ='+';
        res[5][6]  ='+';
        res[5][8]  ='+';
        res[7][6]  ='+';
        res[7][8]  ='+';
        res[7][10] ='+';
        res[9][8]  ='+';
        res[9][10] ='+';
        res[9][12] ='+';
        res[11][10]='+';
        res[11][12]='+';

        res[1][3]  ='-';
        res[3][3]  ='-';
        res[3][5]  ='-';
        res[5][5]  ='-';
        res[5][7]  ='-';
        res[7][7]  ='-';
        res[7][9]  ='-';
        res[9][9]  ='-';
        res[9][11] ='-';
        res[11][11]='-';

        res[2][3]  ='=';
        res[4][5]  ='=';
        res[6][7]  ='=';
        res[8][9]  ='=';
        res[10][11]='=';

        return res;
    }

    /**
     * @return a string that describes CommonGoalCard11
     * @author martagiliberto
     */
    /*
    @Override
    public String getDescription() {
        return "Five tiles of the same type forming a diagonal. ";
    }
    */
    /**
     * this method returns a CLI representation of CommonGoalCard11's description
     * @return the String[] "matrix" of the representation
     * @author martagiliberto
     */
    @Override
    public String[] getCommonGoalDescription(){
        String[] description = new String[8];

        description[0] = "Five tiles of the same type";
        description[1] = "forming a diagonal.        ";
        description[2] = "                           ";
        description[3] = "                           ";
        description[4] = "                           ";
        description[5] = "                           ";
        description[6] = "                           ";
        description[7] = "                           ";
        return description;
    }
}
