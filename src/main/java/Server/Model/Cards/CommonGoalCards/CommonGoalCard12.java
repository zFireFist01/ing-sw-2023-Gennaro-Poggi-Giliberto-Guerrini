package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;

/**
 * This class represents the twelfth common goal card:
 * "Five columns of increasing or decreasing
 * height. Starting from the first column on
 * the left or on the right, each next column
 * must be made of exactly one more tile.
 * Tiles can be of any type."
 * @author Marta Giliberto
 */
public class CommonGoalCard12 extends CommonGoalCard {

    public CommonGoalCard12() {
        super();
    }

    /**
     * constructor of the class CommonGoalCard12 that calls the constructor of the superclass
     * @param playersNum the number of players in the game
     * @param secondInstance true if it is the second card, false otherwise in order to know if
     *                      the card has to be created with the second instance of the points tiles
     */
    public CommonGoalCard12(int playersNum, boolean secondInstance) {
        super(playersNum, secondInstance);
    }

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

    @Override
    public int getCardID() {
        return 12;
    }

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
