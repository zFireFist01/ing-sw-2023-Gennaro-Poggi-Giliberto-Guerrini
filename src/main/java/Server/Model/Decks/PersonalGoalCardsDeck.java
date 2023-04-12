package Server.Model.Decks;

import Server.Model.Cards.*;

/**
 * PersonalGoalCardsDeck in order to store the order of the personal goals
 * @author due2
 */

public class PersonalGoalCardsDeck extends Deck {
    private int index;


    public PersonalGoalCardsDeck(int playersNum){
        super(playersNum);
    }

    /**
     * This method returns the first card of the deck, it also removes it from the deck setting order[0]=0
     * @return the first card of the deck
     */
    @Override
    public Card drawOne() {

        for(int i=0;i<size;i++){
            if(order[i]!=0){
                index = order[i];
                order[i]=0;
                break;
            }
        }
        switch (index) {
            case 1:
                return PersonalGoalCard.TYPE1;
            case 2:
                return PersonalGoalCard.TYPE2;
            case 3:
                return PersonalGoalCard.TYPE3;
            case 4:
                return PersonalGoalCard.TYPE4;
            case 5:
                return PersonalGoalCard.TYPE5;
            case 6:
                return PersonalGoalCard.TYPE6;
            case 7:
                return PersonalGoalCard.TYPE7;
            case 8:
                return PersonalGoalCard.TYPE8;
            case 9:
                return PersonalGoalCard.TYPE9;
            case 10:
                return PersonalGoalCard.TYPE10;
            case 11:
                return PersonalGoalCard.TYPE11;
            case 12:
                return PersonalGoalCard.TYPE12;
        }
        shuffle();

    }

}
