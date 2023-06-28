package Server.Model.Decks;

import Server.Model.Cards.Card;
import Server.Model.Cards.CommonGoalCard;
import Server.Model.Cards.CommonGoalCards.*;

/**
 * This class is the deck of commonGoalCards.
 * @value secondInstance is needed for the constructor of the CommonGoalCard class
 * @author Valentino Guerrini
 */
public class CommonGoalCardsDeck extends Deck {

    private boolean secondInstance;
    public CommonGoalCardsDeck(int playersNum){
        super(playersNum);
        secondInstance=false;
    }

    /**
     * This method returns the first card of the deck, it also removes it from the deck
     * @return the first card of the deck
     */
    @Override
    public Card drawOne() {
        CommonGoalCard tmp;

        int index=0;
        for(int i=0;i<size;i++){
            if(order[i]!=0){
                index = order[i];
                order[i]=0;
                break;
            }
        }

        shuffle();

        if(index==1) {
            tmp = new CommonGoalCard1(playersNum, secondInstance);
        }else if(index==2) {
            tmp = new CommonGoalCard2(playersNum, secondInstance);
        }else if(index==3) {
            tmp = new CommonGoalCard3(playersNum, secondInstance);
        }else if(index==4) {
            tmp = new CommonGoalCard4(playersNum, secondInstance);
        }else if(index==5) {
            tmp = new CommonGoalCard5(playersNum, secondInstance);
        }else if (index==6) {
            tmp = new CommonGoalCard6(playersNum, secondInstance);
        }else if(index==7) {
            tmp = new CommonGoalCard7(playersNum, secondInstance);
        }else if(index==8) {
            tmp = new CommonGoalCard8(playersNum, secondInstance);
        } else if (index==9) {
            tmp = new CommonGoalCard9(playersNum, secondInstance);
        }else if(index==10) {
            tmp = new CommonGoalCard10(playersNum, secondInstance);
        }else if(index==11) {
            tmp = new CommonGoalCard11(playersNum, secondInstance);
        }else{
            tmp = new CommonGoalCard12(playersNum, secondInstance);
        }
        secondInstance=true;
        return tmp;

    }
}
