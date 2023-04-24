package Server.Model.Cards.CommonGoalCards;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.GameItems.Bookshelf;

public class CommonGoalCard12 extends CommonGoalCard {

    public CommonGoalCard12(int playersNum, boolean secondIstance) {
        super(playersNum, secondIstance);
    }
    @Override
    public boolean check(Bookshelf bookshelf) {

        return false;
    }
    /**
     * This method returns the ID of the common goal card
     * @return the ID of the common goal card
     */
    @Override
    public int getCardID() {
        return 12;
    }
}
