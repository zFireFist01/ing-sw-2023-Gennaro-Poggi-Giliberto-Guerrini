package Server.Model.Cards;

import Server.Model.GameItems.Bookshelf;
import Server.Model.Cards.Card;
import Server.Model.GameItems.PointsTile;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the abstract common goal card
 * @author due2
 */

public abstract class CommonGoalCard implements Card {


   private List<PointsTile> pointsTiles = new ArrayList<>();

   /**
    * constructor of the class, it sets the points tiles based on the number of players
    * @param playersNum the number of players
    * @param secondIstance true if it is the second card, false otherwise in order to know if the card has to be created with the second instance of the points tiles
    */
    public CommonGoalCard(int playersNum,boolean secondIstance) {
        if (playersNum == 2) {
           if(!secondIstance) {
               pointsTiles.add(PointsTile.FOUR_1);
               pointsTiles.add(PointsTile.EIGHT_1);
           }else{
               pointsTiles.add(PointsTile.FOUR_2);
               pointsTiles.add(PointsTile.EIGHT_2);
           }
        }else if(playersNum == 3){
            if(!secondIstance) {
                pointsTiles.add(PointsTile.FOUR_1);
                pointsTiles.add(PointsTile.SIX_1);
                pointsTiles.add(PointsTile.EIGHT_1);
            }else{
                pointsTiles.add(PointsTile.FOUR_2);
                pointsTiles.add(PointsTile.SIX_2);
                pointsTiles.add(PointsTile.EIGHT_2);
            }
        }else if(playersNum == 4){
            if(!secondIstance) {
                pointsTiles.add(PointsTile.TWO_1);
                pointsTiles.add(PointsTile.FOUR_1);
                pointsTiles.add(PointsTile.SIX_1);
                pointsTiles.add(PointsTile.EIGHT_1);

            }else{
                pointsTiles.add(PointsTile.TWO_2);
                pointsTiles.add(PointsTile.FOUR_2);
                pointsTiles.add(PointsTile.SIX_2);
                pointsTiles.add(PointsTile.EIGHT_2);
            }
        }
    }

    /**
     * This method returns the laste points tile of the common goal card
     *
     * @return the points tiles of the common goal card
     * @throws UnsupportedOperationException if the common goal card doesn't have points tiles
     */
    public PointsTile pickPointsTile() throws UnsupportedOperationException {
        if(pointsTiles.isEmpty()){
            throw new UnsupportedOperationException("this cards doesn't have points tiles!");
        }else {
            PointsTile tmp = pointsTiles.get(pointsTiles.size() - 1);
            pointsTiles.remove(pointsTiles.size() - 1);
            return tmp;
        }
    }

    /**
     * This method returns the points tiles of the common goal card
     * @return the points tiles of the common goal card
     */

    public List<PointsTile> getPointsTiles() {
        //return a copy of the list
        return new ArrayList<>(pointsTiles);
    }

    /**
     * This method returns the id of the card
     * @return the id of the card
     */
    public int getCardID(){
        return 0;
    }



    /**
     * This method checks if the common goal card is completed, since is abstract it must be implemented in the subclasses
     * @param bookshelf the bookshelf of the player
     * @return true if the common goal card is completed, false otherwise
     */
   abstract public boolean check(Bookshelf bookshelf);
}