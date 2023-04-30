package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;

/**
 * This class represents the twelfth common goal card
 * @author martagiliberto
 */
public class CommonGoalCard12 extends CommonGoalCard {

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
     * @author martagiliberto
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
                }else if(conto==lungprec-1){
                    ordine=2;
                }else{
                    ok= false;
                }

            }else if(ordine==1){
                if(conto!=lungprec+1){
                    ok=false;
                }
            }else if(ordine==2){
                if(conto!=lungprec-1){
                    ok=false;
                }
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
        int conto=3;

        for(int i=0; i<13; i++){
            res[i][0]=  '|';
            res[i][14]= '|';
        }

        for(int i=1; i<12; i++){
            for(int j=2; j<conto; j++){
                if(i%2==1){
                    if(j%2==0){
                        res[i][j]='+';
                    }else{
                        res[i][j]='-';
                    }
                }else{
                    if(j%2==0){
                        res[i][j]='|';
                    }
                }
            }
            conto+=2;
        }

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
