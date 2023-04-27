package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;

public class CommonGoalCard8 extends CommonGoalCard {

    public CommonGoalCard8(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
    }

    @Override
    public boolean check(Bookshelf bookshelf) {
        BookshelfTileSpot[][] bsmat = bookshelf.getTileMatrix();
        return bsmat[0][0].isEmpty()==false && bsmat[0][bookshelf.getBookshelfWidth()-1].isEmpty()==false
                && bsmat[bookshelf.getBookshelfHeight()-1][0].isEmpty() == false &&
                bsmat[bookshelf.getBookshelfHeight()-1][bookshelf.getBookshelfWidth()-1].isEmpty() == false //not empty
                && //types equal
                bsmat[0][0].getTileType() == bsmat[0][bookshelf.getBookshelfWidth()-1].getTileType() &&
                bsmat[0][0].getTileType() == bsmat[bookshelf.getBookshelfHeight()-1][0].getTileType() &&
                bsmat[0][0].getTileType() == bsmat[bookshelf.getBookshelfHeight()-1][bookshelf.getBookshelfWidth()-1].getTileType()
                ;

    }

    /**
     * This method returns a CLI representation (ASCII-art like) of the 8th common goal card
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

        res[1][3] = '=';
        res[11][3] = '=';
        res[1][11] = '=';
        res[11][11] = '=';
        return res;
    }

    /**
     * This method returns a CLI representation (ASCII-art like) of the 8th common goal card's description
     * @return the String[] "matrix" of the representation
     * @author patrickpoggi
     */
    @Override
    public String[] getCommonGoalDescription() {
        String[] description = new String[8];

        description[0] = "Four tiles of the same type";
        description[1] = "in the four corners of the ";
        description[2] = "bookshelf                  ";
        description[3] = "                           ";
        description[4] = "                           ";
        description[5] = "                           ";
        description[6] = "                           ";
        description[7] = "                           ";

        return description;
    }

    /**
     * @return a string that describes the 8th common goal
     * @author patrickpoggi
     */
    @Override
    public String getDescription() {
        return "Four tiles of the same type in the four corners of the bookshelf";
    }

    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 8;
    }
}
