package Server.Model.GameItems;

import Server.Events.MVEvents.MVEvent;
import Server.Events.MVEvents.ModifiedLivingRoomEvent;
import Server.Model.LightMatch;
import Server.Model.Match;
import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static Utils.MathUtils.*;

/**
 * This method represents the living room
 * @author Patrick Poggi
 */
public class LivingRoom {

    private final static int LIVINGROOOMWIDTH = 9;
    private final static int LIVINGROOMHEIGHT = 9;

    @Expose
    private LivingRoomTileSpot[][] tileMatrix;

    private Map<TileType, Integer> tileSack;


    private Match m; // Link to the match this living room belongs to

    /**
     * Initializes the tileMatrix as a LivingRoom without tiles
     * Creates a tileSack
     * @param m refers to the match this LivingRoom belongs in
     */
    public LivingRoom(Match m){
        tileMatrix = new LivingRoomTileSpot[LIVINGROOOMWIDTH][LIVINGROOMHEIGHT];
        this.m = m;
        //The generic corner square
        LivingRoomTileSpot[][] cornerSquare = new LivingRoomTileSpot[LIVINGROOOMWIDTH/3][LIVINGROOMHEIGHT/3];
        //The generic edge square
        LivingRoomTileSpot[][] edgeSquare = new LivingRoomTileSpot[LIVINGROOOMWIDTH/3][LIVINGROOMHEIGHT/3];

        //Build the first corner square
        for(int i=0; i<LIVINGROOMHEIGHT/3; i++){
            for(int j=0; j<LIVINGROOOMWIDTH/3;j++){
                if(i == j && i == 2){
                    cornerSquare[i][j] = new LivingRoomTileSpot(m,3);
                }else{
                    cornerSquare[i][j] = new LivingRoomTileSpot(m, -1);
                }
            }
        }

        //Copy it into the tileMatrix into the top-left corner
        copy(cornerSquare, tileMatrix, 0, 0);

        //Now rotate this matrix and put it into the top-right corner
        rotateMatrix(cornerSquare,3,3);
        copy(cornerSquare, tileMatrix, 0,6);

        //Now rotate it again and put it into the bottom-right corner
        rotateMatrix(cornerSquare,3,3);
        copy(cornerSquare, tileMatrix, 6,6);

        //Now rotate it again and put it into the bottom-left corner
        rotateMatrix(cornerSquare,3,3);
        copy(cornerSquare, tileMatrix, 6,0);

        //Build the first edge square
        for(int i=0;i<LIVINGROOMHEIGHT/3;i++){
            for(int j=0; j< LIVINGROOOMWIDTH/3;j++){
                if(i == 0 && j == 0){ edgeSquare[i][j] = new LivingRoomTileSpot(m, 3);}
                else if(i==0 && j==1){ edgeSquare[i][j] = new LivingRoomTileSpot(m,4);}
                else if(i==0 && j == 2){ edgeSquare[i][j] = new LivingRoomTileSpot(m,-1);}
                else if(i == 1 && j == 2){ edgeSquare[i][j] = new LivingRoomTileSpot(m,4);}
                else{ edgeSquare[i][j] = new LivingRoomTileSpot(m, 0);}
            }
        }

        //Now copy it into the top edge
        copy(edgeSquare, tileMatrix, 0,3);

        //Now rotate it and copy it into the right edge
        rotateMatrix(edgeSquare,3,3);
        copy(edgeSquare, tileMatrix, 3,6);

        //Now rotate it again and put it into the bottom edge
        rotateMatrix(edgeSquare,3,3);
        copy(edgeSquare, tileMatrix, 6,3);

        //Now rotate it again and put it into the left edge
        rotateMatrix(edgeSquare,3,3);
        copy(edgeSquare, tileMatrix, 3,0);

        //Edges and corners are done, we still have the central square
        for(int i = LIVINGROOMHEIGHT/3; i < 2*LIVINGROOMHEIGHT/3; i++){
            for( int j = LIVINGROOOMWIDTH/3; j< 2*LIVINGROOOMWIDTH/3; j++){
                tileMatrix[i][j] = new LivingRoomTileSpot(m, 0);
            }
        }

        //Now create the tileSack
        tileSack = new HashMap<TileType, Integer>();
        for(TileType key : TileType.values()){
            tileSack.put(key,22);
        }
    }

    /**
     * This method is used to check if the refresh of the LivingRoom is needed.
     * The LivingRoom needs to refresh when it is empty
     * or there are only tiles left with no adjacent tiles
     * @return true if the refresh is needed, false otherwise
     */
    private boolean livingRoomNeedRefresh(){
        for(int i=0;i<LIVINGROOMHEIGHT;i++){
            for(int j=0;j<LIVINGROOOMWIDTH;j++){
                if(tileMatrix[i][j].isReal() && !tileMatrix[i][j].isEmpty()){
                    if(i==0){
                        if(j==0){
                            if((tileMatrix[i+1][j].isReal() && !tileMatrix[i+1][j].isEmpty())
                                    || (tileMatrix[i][j+1].isReal() && !tileMatrix[i][j+1].isEmpty())){
                                return false;
                            }
                        }else if(j==LIVINGROOOMWIDTH-1){
                            if((tileMatrix[i+1][j].isReal() && !tileMatrix[i+1][j].isEmpty())
                                    || (tileMatrix[i][j-1].isReal() && !tileMatrix[i][j-1].isEmpty())){
                                return false;
                            }
                        }else{
                            if((tileMatrix[i+1][j].isReal() && !tileMatrix[i+1][j].isEmpty())
                                    || (tileMatrix[i][j+1].isReal() && !tileMatrix[i][j+1].isEmpty())
                                    || (tileMatrix[i][j-1].isReal() && !tileMatrix[i][j-1].isEmpty())){
                                return false;
                            }
                        }
                    }else if(i==LIVINGROOMHEIGHT-1){
                        if(j==0){
                            if((tileMatrix[i-1][j].isReal() && !tileMatrix[i-1][j].isEmpty())
                                    || (tileMatrix[i][j+1].isReal() && !tileMatrix[i][j+1].isEmpty())){
                                return false;
                            }
                        }else if(j==LIVINGROOOMWIDTH-1){
                            if((tileMatrix[i-1][j].isReal() && !tileMatrix[i-1][j].isEmpty())
                                    || (tileMatrix[i][j-1].isReal() && !tileMatrix[i][j-1].isEmpty())){
                                return false;
                            }
                        }else{
                            if((tileMatrix[i-1][j].isReal() && !tileMatrix[i-1][j].isEmpty())
                                    || (tileMatrix[i][j+1].isReal() && !tileMatrix[i][j+1].isEmpty())
                                    || (tileMatrix[i][j-1].isReal() && !tileMatrix[i][j-1].isEmpty())){
                                return false;
                            }
                        }
                    }else{
                        if(j==0){
                            if((tileMatrix[i-1][j].isReal() && !tileMatrix[i-1][j].isEmpty())
                                    || (tileMatrix[i+1][j].isReal() && !tileMatrix[i+1][j].isEmpty())
                                    || (tileMatrix[i][j+1].isReal() && !tileMatrix[i][j+1].isEmpty())){
                                return false;
                            }
                        }else if(j==LIVINGROOOMWIDTH-1){
                            if((tileMatrix[i-1][j].isReal() && !tileMatrix[i-1][j].isEmpty())
                                    || (tileMatrix[i+1][j].isReal() && !tileMatrix[i+1][j].isEmpty())
                                    || (tileMatrix[i][j-1].isReal() && !tileMatrix[i][j-1].isEmpty())){
                                return false;
                            }
                        }else{
                            if((tileMatrix[i-1][j].isReal() && !tileMatrix[i-1][j].isEmpty())
                                    || (tileMatrix[i+1][j].isReal() && !tileMatrix[i+1][j].isEmpty())
                                    || (tileMatrix[i][j+1].isReal() && !tileMatrix[i][j+1].isEmpty())
                                    || (tileMatrix[i][j-1].isReal() && !tileMatrix[i][j-1].isEmpty())){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public LivingRoomTileSpot[][] getTileMatrix(){
        //Sharing a copy of the Livingroom, not the actual matrix
        LivingRoomTileSpot[][] copy = new LivingRoomTileSpot[LIVINGROOOMWIDTH][LIVINGROOMHEIGHT];
        for(int i = 0; i < LIVINGROOMHEIGHT; i++ ){
            for ( int j = 0; j< LIVINGROOOMWIDTH; j++){
                copy[i][j] = new LivingRoomTileSpot(tileMatrix[i][j]);
            }
        }
        return copy;
    }

    /**
     * This method refresh the LivingRoom
     */
    public void refreshLivingRoom(){
        Random r = new Random();
        int choice = -1;
        for(int i = 0; i<LIVINGROOMHEIGHT;i++){
            for(int j=0; j<LIVINGROOOMWIDTH;j++){
                if(tileMatrix[i][j].isEmpty() && tileMatrix[i][j].isReal()){
                    choice = r.nextInt(6); // The tile types are 6, thus we need a drawing pool which is [0,6) = [0,5]
                    switch (choice){
                        case 0:
                            tileMatrix[i][j].setTile(TileType.BOOKS);
                            tileSack.put(TileType.BOOKS,
                                    (tileSack.get(TileType.BOOKS).intValue()-1 >= 0 ? tileSack.get(TileType.BOOKS).intValue()-1: 0));
                            break;
                        case 1:
                            tileMatrix[i][j].setTile(TileType.CATS);
                            tileSack.put(TileType.CATS,
                                    (tileSack.get(TileType.CATS).intValue()-1 >= 0 ? tileSack.get(TileType.CATS).intValue()-1: 0));
                            break;
                        case 2:
                            tileMatrix[i][j].setTile(TileType.GAMES);
                            tileSack.put(TileType.GAMES,
                                    (tileSack.get(TileType.GAMES).intValue()-1 >= 0 ? tileSack.get(TileType.GAMES).intValue()-1: 0));
                            break;
                        case 3:
                            tileMatrix[i][j].setTile(TileType.FRAMES);
                            tileSack.put(TileType.FRAMES,
                                    (tileSack.get(TileType.FRAMES).intValue()-1 >= 0 ? tileSack.get(TileType.FRAMES).intValue()-1: 0));
                            break;
                        case 4:
                            tileMatrix[i][j].setTile(TileType.PLANTS);
                            tileSack.put(TileType.PLANTS,
                                    (tileSack.get(TileType.PLANTS).intValue()-1 >= 0 ? tileSack.get(TileType.PLANTS).intValue()-1: 0));
                            break;
                        case 5:
                            tileMatrix[i][j].setTile(TileType.TROPHIES);
                            tileSack.put(TileType.TROPHIES,
                                    (tileSack.get(TileType.TROPHIES).intValue()-1 >= 0 ? tileSack.get(TileType.TROPHIES).intValue()-1: 0));
                            break;
                        default:
                            throw new RuntimeException("Random choice didn't go as planned in tileMatrix["+i+"]["+j+"]");
                    }

                }
            }
        }
    }

    public Map<TileType, Integer> getTileSack(){
        //Sharing a copy of the sack, not the actual map
        Map<TileType, Integer> copy = new HashMap<>(tileSack);
        return copy;
    }

    /**
     *This method allows the controller to send an "order" of picking one tile
     * @param i row of the tile the caller wants to select
     * @param j column of the tile the caller wants to select
     * @return the TileType of the picked tile
     * @throws UnsupportedOperationException when the tile isn't real or isn't pickable
     * according to the game's rule
     */
    public TileType takeTile(int i, int j) throws UnsupportedOperationException{
        if(!tileMatrix[i][j].isReal()){
            throw new UnsupportedOperationException("The tile you want to pick isn't real!");
        }
        if(oneFreeEdge(i,j)){
            //The tile can be picked
            TileType tt = tileMatrix[i][j].getTileType();
            if(tt==null){
                throw new UnsupportedOperationException("The tile you want to pick is empty!");
            }
            tileMatrix[i][j].setEmpty();
            return tt;
        }else{
            throw new UnsupportedOperationException("The tile doesn't have at least one free edge");
        }
    }

    /**
     * This method allows the controller to send an "order" of picking more than one tile with only one call
     * @param couples is an array of couples (Integer, Integer) that each represent the position of one selected tile
     * @return an array of TileType(s) where the i-th element corresponds to the type of the i-th tile picked
     * @throws UnsupportedOperationException whenever at least one of the tiles is not pick-able according to game rules
     */
    public TileType[] takeTiles(Couple<Integer, Integer>[] couples) throws UnsupportedOperationException{
        TileType[] resultTypes = new TileType[3];
        Map<Couple<Integer,Integer>, TileType> positions = new HashMap<>();
        int len = couples.length;
        boolean inRow = true;
        boolean inCol = true;
        boolean insertedAll = true;
        int i=0;
        //I have to check if all the desired tiles have at least one free edge BEFORE picking the first one of them
        for(Couple c: couples){
            if(!oneFreeEdge(c.getA(),c.getB())){
                throw new UnsupportedOperationException("The tile doesn't have at least one free edge");
            }
        }
        //Checking if they are aligned...
        for(int j=0; j<len-1&&(inRow || inCol);j++){
            if(inRow && (couples[j].getA() != couples[j+1].getA())){
                inRow = false;
            }
            if(inCol && (couples[j].getB() != couples[j+1].getB())){
                inCol = false;
            }
        }
        if(!(inRow || inCol)){
            throw new UnsupportedOperationException("The tiles you selected are not aligned!");
        }else{
            if(inCol){
                if(len == 2){
                    if(!(couples[0].getA() == couples[1].getA()+1 || couples[0].getA()+1 == couples[1].getA())){
                        throw new UnsupportedOperationException("The tiles you selected are not adjacent!");
                    }
                }else if(len == 3){
                    if(!((couples[0].getA() == couples[1].getA()+1 && couples[0].getA() == couples[2].getA()+2) ||
                            (couples[0].getA() == couples[1].getA()+2 && couples[0].getA() == couples[2].getA()+1) ||
                            (couples[0].getA() == couples[1].getA()-1 && couples[0].getA() == couples[2].getA()+1) ||
                            (couples[0].getA() == couples[1].getA()+1 && couples[0].getA() == couples[2].getA()-1) ||
                            (couples[0].getA() == couples[1].getA()-2 && couples[0].getA() == couples[2].getA()-1) ||
                            (couples[0].getA() == couples[1].getA()-1 && couples[0].getA() == couples[2].getA()-2))){
                        throw new UnsupportedOperationException("The tiles you selected are not adjacent!");
                    }
                }
            }else if(inRow){
                if(len == 2){
                    if(!(couples[0].getB() == couples[1].getB()+1 || couples[0].getB()+1 == couples[1].getB())){
                        throw new UnsupportedOperationException("The tiles you selected are not adjacent!");
                    }
                }else if(len == 3){
                    if(!((couples[0].getB() == couples[1].getB()+1 && couples[0].getB() == couples[2].getB()+2) ||
                            (couples[0].getB() == couples[1].getB()+2 && couples[0].getB() == couples[2].getB()+1) ||
                            (couples[0].getB() == couples[1].getB()-1 && couples[0].getB() == couples[2].getB()+1) ||
                            (couples[0].getB() == couples[1].getB()+1 && couples[0].getB() == couples[2].getB()-1) ||
                            (couples[0].getB() == couples[1].getB()-2 && couples[0].getB() == couples[2].getB()-1) ||
                            (couples[0].getB() == couples[1].getB()-1 && couples[0].getB() == couples[2].getB()-2))){
                        throw new UnsupportedOperationException("The tiles you selected are not adjacent!");
                    }
                }
            }
            i=0;
            for(Couple c : couples){
                try{
                    resultTypes[i] = takeTile(c.getA(), c.getB());
                    positions.put(c, resultTypes[i]);
                    i++;
                }catch(UnsupportedOperationException e){
                    insertedAll = false;
                    System.err.println("Error occurred when picking tile in position "+c.toString()+": "+ e.getMessage());
                    break;      //I want to break if at least one tile is not pick-able
                }
            }
        }
        if(!insertedAll){
            //I have to revert the changes
            for(Couple c : positions.keySet()){
                tileMatrix[c.getA()][c.getB()].setTile(positions.get(c));
            }
            throw new UnsupportedOperationException("At least one of the tiles you selected is not pick-able!");
        }
        notifyMVEventListeners(new ModifiedLivingRoomEvent(new LightMatch(this.m)));
        if(livingRoomNeedRefresh()){
            refreshLivingRoom();
            notifyMVEventListeners(new ModifiedLivingRoomEvent(new LightMatch(this.m)));
        }

        return resultTypes;
    }

    /**
     * This method checks if the tile with in position i,j of the LivingRoom as at least one free edge
     * @param i row of the tile we want to check
     * @param j column of the tile we want tp check
     * @return true if and only if the tile we're checking has at least one free edge
     */
    private boolean oneFreeEdge(int i, int j) {

        if (i == 0) {
            if (j == 0) {
                return (tileMatrix[i][j + 1].isReal() == false || tileMatrix[i][j + 1].isEmpty()) ||
                        (tileMatrix[i + 1][j].isReal() == false || tileMatrix[i + 1][j].isEmpty());
            } else if (j == LIVINGROOOMWIDTH - 1) {
                return (tileMatrix[i][j - 1].isReal() == false || tileMatrix[i][j - 1].isEmpty()) ||
                        (tileMatrix[i + 1][j].isReal() == false || tileMatrix[i + 1][j].isEmpty());
            }else{
                return (tileMatrix[i][j - 1].isReal() == false || tileMatrix[i][j - 1].isEmpty()) ||
                        (tileMatrix[i + 1][j].isReal() == false || tileMatrix[i + 1][j].isEmpty()) ||
                        (tileMatrix[i][j + 1].isReal() == false || tileMatrix[i][j + 1].isEmpty());
            }
        } else if (i == LIVINGROOMHEIGHT - 1) {
            if (j == 0) {
                return (tileMatrix[i][j + 1].isReal() == false || tileMatrix[i][j + 1].isEmpty()) ||
                        (tileMatrix[i - 1][j].isReal() == false || tileMatrix[i - 1][j].isEmpty());
            } else if (j == LIVINGROOOMWIDTH - 1) {
                return (tileMatrix[i][j - 1].isReal() == false || tileMatrix[i][j - 1].isEmpty()) ||
                        (tileMatrix[i - 1][j].isReal() == false || tileMatrix[i - 1][j].isEmpty());
            }else{
                return (tileMatrix[i][j - 1].isReal() == false || tileMatrix[i][j - 1].isEmpty()) ||
                        (tileMatrix[i - 1][j].isReal() == false || tileMatrix[i - 1][j].isEmpty()) ||
                        (tileMatrix[i][j + 1].isReal() == false || tileMatrix[i][j + 1].isEmpty());
            }
        } else {
            if (j == 0) {
                return (tileMatrix[i][j + 1].isReal() == false || tileMatrix[i][j + 1].isEmpty()) ||
                        (tileMatrix[i - 1][j].isReal() == false || tileMatrix[i - 1][j].isEmpty()) ||
                        (tileMatrix[i + 1][j].isReal() == false || tileMatrix[i + 1][j].isEmpty());
            } else if (j == LIVINGROOOMWIDTH - 1) {
                return (tileMatrix[i][j - 1].isReal() == false || tileMatrix[i][j - 1].isEmpty()) ||
                        (tileMatrix[i - 1][j].isReal() == false || tileMatrix[i - 1][j].isEmpty()) ||
                        (tileMatrix[i + 1][j].isReal() == false || tileMatrix[i + 1][j].isEmpty());
            } else {
                return (tileMatrix[i][j + 1].isReal() == false || tileMatrix[i][j + 1].isEmpty()) ||
                        (tileMatrix[i][j - 1].isReal() == false || tileMatrix[i][j - 1].isEmpty()) ||
                        (tileMatrix[i - 1][j].isReal() == false || tileMatrix[i - 1][j].isEmpty()) ||
                        (tileMatrix[i + 1][j].isReal() == false || tileMatrix[i + 1][j].isEmpty());
            }
        }
    }


    /**
     * This method is called by takeTiles method and is used to send an MVEvent,
     * in order to update the views.
     * @param event is the MVevent I want to send
     */
    public void notifyMVEventListeners(MVEvent event){
        this.m.notifyMVEventListeners(event);
    }

}
