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
            res[i][0] = '|';
            res[i][14] = '|';
            res[i][1] = ' ';
            res[i][13]= ' ';
        }

        for(int i = 0; i<13; i++){
            for(int j=2; j<13; j++){
                if(i%2==0){
                    if(j%2==0){
                        res[i][j] = '+';
                    }else if(j==3||j==7||j==11) {
                        res[i][j] = '-';
                    }else{
                        res[i][j] = ' ';
                    }
                }else{
                    if(j%2==0){
                        res[i][j] = '|';
                    }else{
                        res[i][j] = ' ';
                    }
                }
            }
        }

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



