package Server.Model.Cards;

import Server.Model.GameItems.Bookshelf;
import Server.Model.GameItems.PointsTile;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the abstract common goal card
 * @author due2
 */

public abstract class CommonGoalCard implements Card {
    @Expose
    private List<PointsTile> pointsTiles = new ArrayList<>();
    //USED FOR SERIALIZATION
    public CommonGoalCard(){}

   /**
    * Constructor of the class, it sets the points tiles based on the number of players
    * @param playersNum the number of players
    * @param secondInstance true if it is the second card, false otherwise in order to
    *                       know if the card has to be created with the second instance
    *                       of the points tiles
    */
    public CommonGoalCard(int playersNum,boolean secondInstance) {
        if (playersNum == 2) {
           if(!secondInstance) {
               pointsTiles.add(PointsTile.FOUR_1);
               pointsTiles.add(PointsTile.EIGHT_1);
           }else{
               pointsTiles.add(PointsTile.FOUR_2);
               pointsTiles.add(PointsTile.EIGHT_2);
           }
        }else if(playersNum == 3){
            if(!secondInstance) {
                pointsTiles.add(PointsTile.FOUR_1);
                pointsTiles.add(PointsTile.SIX_1);
                pointsTiles.add(PointsTile.EIGHT_1);
            }else{
                pointsTiles.add(PointsTile.FOUR_2);
                pointsTiles.add(PointsTile.SIX_2);
                pointsTiles.add(PointsTile.EIGHT_2);
            }
        }else if(playersNum == 4){
            if(!secondInstance) {
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
     * This method removes the last element of pointsTiles list of the common goal card
     *
     * @throws UnsupportedOperationException if the common goal card doesn't have points tiles
     */
    public void  removePointsTile() throws UnsupportedOperationException {
        if(pointsTiles.isEmpty()){
            throw new UnsupportedOperationException("this cards doesn't have points tiles!");
        }else {
            pointsTiles.remove(pointsTiles.size() - 1);
        }
    }

    /**
     * This method returns the last element of pointsTiles list of the common goal card
     *
     * @throws UnsupportedOperationException if the common goal card doesn't have points tiles
     */
    public PointsTile getPointsTile() throws UnsupportedOperationException {
        if(pointsTiles.isEmpty()){
            throw new UnsupportedOperationException("this cards doesn't have points tiles!");
        }else {
            PointsTile tmp = pointsTiles.get(pointsTiles.size() - 1);
            return tmp;
        }
    }

    /**
     * This method is used for CLI in order to provide the description of the common goal card
     * @return the description of the common goal card
     */
    public abstract String[] getCommonGoalDescription();

    public List<PointsTile> getPointsTiles() {
        //return a copy of the list
        return new ArrayList<>(pointsTiles);
    }

    /**
     * This method returns the ID of the common goal card; it changes for every common goal card
     * @return the ID of the common goal card
     */
    public int getCardID(){
        return 0;
    }


    //USED FOR DESERIALIZATION

    public void setPointsTiles(List<PointsTile> pointsTiles) {
        this.pointsTiles = pointsTiles;
    }

    /**
     * This method checks if the common goal card is completed,
     * since is abstract it must be implemented in the subclasses
     * @param bookshelf the bookshelf of the player
     * @return true if the common goal card is completed, false otherwise
     */
    public abstract boolean check(Bookshelf bookshelf);
}
