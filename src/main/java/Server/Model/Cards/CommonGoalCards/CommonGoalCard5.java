package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;
/**
 * This class represents the fifth common goal card:
 * "Three columns each formed by 6 tiles
 * of maximum three different types. One
 * column can show the same or a different
 * combination of another column."
 * @author Valentino Guerrini
 */

public class CommonGoalCard5 extends CommonGoalCard {

    public CommonGoalCard5(){
        super();
    }

    /**
     * constructor of the class CommonGoalCard5 that calls the constructor of the superclass
     * @param playersNum the number of players in the game
     * @param secondInstance true if it is the second card, false otherwise in order to know
     *                       if the card has to be created with the second instance of the
     *                       points tiles
     */
    public CommonGoalCard5(int playersNum, boolean secondInstance) {
        super(playersNum, secondInstance);
    }

    public boolean check(Bookshelf bookshelf) {
        BookshelfTileSpot[][] shelf = bookshelf.getTileMatrix();
        int numColOK=0;
        int numType=0;
        boolean flag= true;
        int[][] verifier = new int[6][5];
        int[] types= new int[7];

        for(int k=0; k<7; k++){
            types[k]=0;
        }

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

        for(int j=0; j<5; j++){
            for(int i=0; i<6 && flag; i++){

                if(verifier[i][j]!=0){
                    if(types[verifier[i][j]]==0){
                        types[verifier[i][j]]=1;
                        numType++;
                    }
                    if(numType>3){
                        flag=false;
                    }
                }else{
                    flag=false;
                }
            }

            if(flag){
                numColOK++;
                if(numColOK==3){
                    return true;
                }
            }

            flag=true;
            numType=0;
            for(int k=0; k<7; k++){
                types[k]=0;
            }

        }
        return false;

    }

    @Override
    public int getCardID() {
        return 5;
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



