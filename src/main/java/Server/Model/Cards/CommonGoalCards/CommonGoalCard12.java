package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;

/**
 * This class represents the twelfth common goal card
 * @author martagiliberto
 */
public class CommonGoalCard12 extends CommonGoalCard {

    public CommonGoalCard12() {
        super();
    }

    /**
     * constructor of the class CommonGoalCard12 that calls the constructor of the superclass
     * @param playersNum the number of players in the game
     * @param secondIstance true if it is the second card, false otherwise in order to know if
     *                      the card has to be created with the second instance of the points tiles
     */
    public CommonGoalCard12(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
    }

    /**
     * this method checks if CommonGoal12 is completed
     * @param bookshelf the bookshelf of the player
     * @return true if CommonGoal12 is completed, false if it is not
     * @author Marta Giliberto
     */
    @Override
    public boolean check(Bookshelf bookshelf) {
        int lungprec=0;
        int conto=0;
        boolean ok=true;
        int ordine=0;
        int j;

        for(j=0; j<5 && ok; j++){
            for(int i=5; i>=0; i--){
                if(!(bookshelf.getTileMatrix()[i][j].isEmpty())){
                    conto++;
                }
            }
            if(j==0){
                lungprec=conto;
            }else if(j==1){
                if(conto==lungprec+1){
                    ordine=1;
                    lungprec=conto;
                }else if(conto==lungprec-1){
                    ordine=2;
                    lungprec=conto;
                }else{
                    ok= false;
                }

            }else if(ordine==1){
                if(conto!=lungprec+1){
                    ok=false;
                }
                lungprec=conto;
            }else if(ordine==2){
                if(conto!=lungprec-1){
                    ok=false;
                }
                lungprec=conto;
            }

            conto=0;

        }

        return ok;
    }
    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 12;
    }

    /**
     * this method returns the CLI representation of CommonGoalCard12
     * @return a matrix of char that represents the CommonGoalCard12
     * @author martagiliberto
     */
    @Override
    public char[][] getCLIRepresentation(){
        char[][] res = new char[13][15];

        for(int i=0; i<13; i++) {
            for (int j = 0; j < 15; j++) {
                if (j == 0 || j == 14) {
                    res[i][j] = '|';
                } else {
                    res[i][j] = ' ';
                }
            }
        }

        res[1][2]='+';
        res[1][3]='-';
        res[1][4]='+';
        res[2][2]='|';
        res[2][4]='|';
        res[3][2]='+';
        res[3][3]='-';
        res[3][4]='+';
        res[3][5]='-';
        res[3][6]='+';
        res[4][2]='|';
        res[4][4]='|';
        res[4][6]='|';
        res[5][2]='+';
        res[5][3]='-';
        res[5][4]='+';
        res[5][5]='-';
        res[5][6]='+';
        res[5][7]='-';
        res[5][8]='+';
        res[6][2]='|';
        res[6][4]='|';
        res[6][6]='|';
        res[6][8]='|';
        res[7][2]='+';
        res[7][3]='-';
        res[7][4]='+';
        res[7][5]='-';
        res[7][6]='+';
        res[7][7]='-';
        res[7][8]='+';
        res[7][9]='-';
        res[7][10]='+';
        res[8][2]='|';
        res[8][4]='|';
        res[8][6]='|';
        res[8][8]='|';
        res[8][10]='|';
        res[9][2]='+';
        res[9][3]='-';
        res[9][4]='+';
        res[9][5]='-';
        res[9][6]='+';
        res[9][7]='-';
        res[9][8]='+';
        res[9][9]='-';
        res[9][10]='+';
        res[9][11]='-';
        res[9][12]='+';
        res[10][2]='|';
        res[10][4]='|';
        res[10][6]='|';
        res[10][8]='|';
        res[10][10]='|';
        res[10][12]='|';
        res[11][2]='+';
        res[11][3]='-';
        res[11][4]='+';
        res[11][5]='-';
        res[11][6]='+';
        res[11][7]='-';
        res[11][8]='+';
        res[11][9]='-';
        res[11][10]='+';
        res[11][11]='-';
        res[11][12]='+';

        return res;
    }

    /**
     * @return a string that describes CommonGoalCard12
     * @author martagiliberto
     */
    /*
    @Override
    public String getDescription(){
        return "Five columns of increasing or decreasing height. Starting from the first column on the left or on the right, each next column must be made of exactly one more tile.Tiles can be of any type.";
    }
    */
    /**
     * this method returns a CLI representation of CommonGoalCard12's description
     * @return the String[] "matrix" of the representation
     * @author martagiliberto
     */
    @Override
    public String[] getCommonGoalDescription(){
        String[] description = new String[8];

        description[0] = "Five columns of increasing ";
        description[1] = "or decreasing height.      ";
        description[2] = "Starting from the first    ";
        description[3] = "column on the left or on   ";
        description[4] = "the right, each next column";
        description[5] = "must be made of exactly one";
        description[6] = "more tile.                 ";
        description[7] = "Tiles can be of any type.  ";
        return description;
    }


}
