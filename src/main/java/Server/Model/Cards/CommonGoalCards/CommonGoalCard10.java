package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

public class CommonGoalCard10 extends CommonGoalCard {

    public CommonGoalCard10() {
        super();
    }

    public CommonGoalCard10(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
    }

    @Override
    public boolean check(Bookshelf bookshelf) {
        //5 tiles pf the same kind forming an X in the bookshelf
        BookshelfTileSpot[][] bsmat = bookshelf.getTileMatrix();

        for(int i=0; i<bookshelf.getBookshelfHeight()-2;i++){
            for(int j=0; j< bookshelf.getBookshelfWidth()-2;j++){
                TileType tt = bsmat[i][j].getTileType();
                if(
                        bsmat[i][j].isEmpty() == false
                        && bsmat[i+1][j+1].isEmpty() == false
                        && bsmat[i][j+2].isEmpty() == false
                        && bsmat[i+2][j].isEmpty() == false
                        && bsmat[i+2][j+2].isEmpty() == false
                        &&
                        bsmat[i+1][j+1].getTileType() == tt
                        && bsmat[i][j+2].getTileType() == tt
                        && bsmat[i+2][j].getTileType() == tt
                        && bsmat[i+2][j+2].getTileType() == tt
                ){
                    //All the types are equal in a X disposition
                    return true;
                }
            }
        }

        return false;
    }
    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 10;
    }

    /**
     * this method return the CLI representation of CommonGoalCard10
     * @return a matrix of char that represents the CommonGoalCard10
     * @author martagiliberto
     */
    @Override
    public char[][] getCLIRepresentation() {
        char[][] res = new char[13][15];

        for (int i = 0; i < 13; i++) {
            for (int j = 0; j < 15; j++) {
                if(j==0||j==14){
                    res[i][j]='|';
                }else {
                    res[i][j] = ' ';
                }
            }
        }

        res[3][6]='+';
        res[3][8]='+';
        res[5][4]='+';
        res[5][6]='+';
        res[5][8]='+';
        res[5][10]='+';
        res[7][4]='+';
        res[7][6]='+';
        res[7][8]='+';
        res[7][10]='+';
        res[9][6]='+';
        res[9][8]='+';

        res[3][7]='-';
        res[5][5]='-';
        res[5][7]='-';
        res[5][9]='-';
        res[7][5]='-';
        res[7][7]='-';
        res[7][9]='-';
        res[9][7]='-';

        res[4][7]='=';
        res[6][5]='=';
        res[6][7]='=';
        res[6][9]='=';
        res[8][7]='=';

        return res;
    }

    /**
     * @return a string that describes CommonGoalCard10
     * @author martagiliberto
     */
/*
    @Override
    public String getDescription() {
        return "Five tiles of the same type forming an X.";
    }
    */

    /**
     * this method returns a CLI representation of CommonGoalCard10's description
     * @return the String[] "matrix" of the representation
     * @author martagiliberto
     */
    @Override
    public String[] getCommonGoalDescription(){
        String[] description = new String[8];

        description[0] = "Five tiles of the same type";
        description[1] = "forming an X.              ";
        description[2] = "                           ";
        description[3] = "                           ";
        description[4] = "                           ";
        description[5] = "                           ";
        description[6] = "                           ";
        description[7] = "                           ";
        return description;
    }
}
