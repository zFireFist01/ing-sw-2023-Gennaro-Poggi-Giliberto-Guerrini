package Server.Model.Decks;

import Server.Model.Cards.Card;
import Server.Model.Cards.CommonGoalCards.*;

/**
 * CommonGoalCardsDeck to store the deck of commongoals and to save the order
 * @author due2
 */

public class CommonGoalCardsDeck extends Deck {
    private boolean secondIstance;
    public CommonGoalCardsDeck(int playersNum){
        super(playersNum);
        secondIstance=false;
    }

    /**
     * This method returns the first card of the deck, it also removes it from the deck setting order[0]=0
     * @return the first card of the deck
     */
    @Override
    public Card drawOne() {

        int index=0;
        for(int i=0;i<size;i++){
            if(order[i]!=0){
                index = order[i];
                order[i]=0;
                break;
            }
        }

        secondIstance=true;
        shuffle();

        if(index==1) {
            return new CommonGoalCard1(playersNum, secondIstance);
        }else if(index==2) {
            return new CommonGoalCard2(playersNum, secondIstance);
        }else if(index==3) {
            return new CommonGoalCard3(playersNum, secondIstance);
        }else if(index==4) {
            return new CommonGoalCard4(playersNum, secondIstance);
        }else if(index==5) {
            return new CommonGoalCard5(playersNum, secondIstance);
        }else if (index==6) {
            return new CommonGoalCard6(playersNum, secondIstance);
        }else if(index==7) {
            return new CommonGoalCard7(playersNum, secondIstance);
        }else if(index==8) {
            return new CommonGoalCard8(playersNum, secondIstance);
        } else if (index==9) {
            return new CommonGoalCard9(playersNum, secondIstance);
        }else if(index==10) {
            return new CommonGoalCard10(playersNum, secondIstance);
        }else if(index==11) {
            return new CommonGoalCard11(playersNum, secondIstance);
        }else{
            return new CommonGoalCard12(playersNum, secondIstance);
        }

    }
}
