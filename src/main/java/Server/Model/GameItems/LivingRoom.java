package Server.Model.GameItems;

import Server.Events.MVEvents.MVEvent;
import Server.Events.MVEvents.ModifiedLivingRoomEvent;
import Server.Model.LightMatch;
import Server.Model.Match;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static Utils.MathUtils.*;


/**
 * @Class Livingroom
 * @author patrickpoggi
 */
public class LivingRoom {

    private final static int LIVINGROOOMWIDTH = 9;
    private final static int LIVINGROOMHEIGHT = 9;

    private LivingRoomTileSpot[][] tileMatrix;
    private Map<TileType, Integer> tileSack;


    private Match m; // Link to the match this livingroom belongs to

    /**
     * Initializes the tileMatrix as a Livingoroom without tiles
     * Creates a tileSack
     * @param m refers to the match this livingroom belongs in
     */
    public LivingRoom(Match m){
        tileMatrix = new LivingRoomTileSpot[LIVINGROOOMWIDTH][LIVINGROOMHEIGHT];

        //The generic corner square
        LivingRoomTileSpot[][] cornerSquare = new LivingRoomTileSpot[LIVINGROOOMWIDTH/3][LIVINGROOMHEIGHT/3];
        //The generic edge square
        LivingRoomTileSpot[][] edgeSquare = new LivingRoomTileSpot[LIVINGROOOMWIDTH/3][LIVINGROOMHEIGHT/3];

        //Build the first corner square
        for(int i=0; i<LIVINGROOMHEIGHT/3; i++){
            for(int j=0; j<LIVINGROOOMWIDTH;j++){
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
        rotateMatrix(cornerSquare);
        copy(cornerSquare, tileMatrix, 0,6);

        //Now rotate it again and put it into the bottom-right corner
        rotateMatrix(cornerSquare);
        copy(cornerSquare, tileMatrix, 6,6);

        //Now rotate it again and put it into the bottom-left corner
        rotateMatrix(cornerSquare);
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
        rotateMatrix(edgeSquare);
        copy(edgeSquare, tileMatrix, 3,6);

        //Now rotate it again and put it into the bottom edge
        rotateMatrix(edgeSquare);
        copy(edgeSquare, tileMatrix, 6,3);

        //Now rotate it again and put it into the left edge
        rotateMatrix(edgeSquare);
        copy(edgeSquare, tileMatrix, 3,0);

        //Edges and corners are done, we still have the central square
        for(int i = LIVINGROOMHEIGHT/3; i < 2*LIVINGROOMHEIGHT/3; i++){
            for( int j = LIVINGROOOMWIDTH/3; j< 2*LIVINGROOOMWIDTH; j++){
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
     * @return A copy of the matrix that describes the livingroom of match m, in the current state
     */
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
     * Refills the Livingroom
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

    /**
     *
     * @return a map where the key is the TileType and the value is the number of tiles of that kind that
     * are still available
     */
    public Map<TileType, Integer> getTileSack(){
        //Sharing a copy of the sack, not the actual map
        Map<TileType, Integer> copy = new HashMap<>(tileSack);
        return copy;
    }

    /**
     *
     * @param i row of the tile the caller wants to select
     * @param j column of the tile the caller wants to select
     * @return the TileType of the picked tile
     * @throws UnsupportedOperationException when the tile isn't real or isn't pickable according to the game's rule
     */
    public TileType takeTile(int i, int j) throws UnsupportedOperationException{
        //TODO: think if this method should be a void
        if(!tileMatrix[i][j].isReal()){
            throw new UnsupportedOperationException("The tile you want to pick isn't real!");
        }
        if(oneFreeEdge(i,j)){
            //The tile can be picked
            TileType tt = tileMatrix[i][j].getTileType();
            tileMatrix[i][j].setEmpty();
            return tt;
        }else{
            throw new UnsupportedOperationException("The tile doesn't have at least one free edge");
        }
    }

    /**
     * This methods allows the controller to send an "order" of picking more than one tile with only one call
     * @param couples is an array of couples (Integer, Integer) that each represent the position of one selected tile
     * @return an array of TileType(s) where the i-th element corresponds to the type of the i-th tile picked
     * @throws UnsupportedOperationException whenever at least one of the tiles is not pick-able according to game rules
     */
    public TileType[] takeTiles(Couple<Integer, Integer>[] couples) throws UnsupportedOperationException{
        TileType[] resultTypes = new TileType[3];
        int len = couples.length;
        boolean inRow = true;
        boolean inCol = true;
        int i=0;
        //I have to check if all the desired tiles have at least one free edge BEFORE picking the first one of them
        for(Couple c: couples){
            if(!oneFreeEdge(c.getA(),c.getB())){
                throw new UnsupportedOperationException("The tile doesn't have at least one free edge");
            }
        }
        //Checking if they are aligned...
        for(int j=0; j<len-1;j++){
            if(inRow && (couples[j].getA() != couples[j+1].getA())){
                inRow = false;
                break;
            }
            if(inCol && (couples[j].getB() != couples[j].getB())){
                inCol = false;
                break;
            }
        }
        if(!(inRow || inCol)){
            throw new UnsupportedOperationException("The tiles you selected are not aligned!");
        }else{
            i=0;
            for(Couple c : couples){
                try{
                    resultTypes[i] = takeTile(c.getA(), c.getB());
                    i++;
                }catch(UnsupportedOperationException e){
                    System.err.println("Error occurred when picking tile in position "+c.toString()+": "+ e.getMessage());
                    break;      //I want to break if at least one tile is not pick-able
                }               //NOTE that I don't have to revert the "taking" beacuse the method takeTile checks
                                //before applying changes
            }
        }
        notifyMVEventListeners(new ModifiedLivingRoomEvent(new LightMatch(this.m)));
        return resultTypes;
    }

    /**
     * Helper method
     * @param i row of the tile we want to check
     * @param j column of the tile we want tp check
     * @return true if and only if the tile we're checking has at least one free edge
     */
    private boolean oneFreeEdge(int i, int j){
        int tli = (i==0 ? 0:i-1); // top line index
        int bli = (i==LIVINGROOMHEIGHT-1 ? i:i+1); // bottom line index
        int lci = (j==0 ? j:j-1); //left column index
        int rci = (j==LIVINGROOOMWIDTH-1 ? j:j+1); // right column index
        int count = 0;//how many free edges does the (i,j) tile have

        for(int k = tli; k<bli; k++){
            for(int l = lci; l<rci; l++){
                if(tileMatrix[k][l].isReal() == false || tileMatrix[k][l].isEmpty()){
                    count++;
                    if(count >= 1){ return true;}
                }
            }
        }
        return false;
    }

    /**
     * this method returns CLIRepresentation of the LivingRoom
     * @return a matrix of char that represents the LivingRoom
     * @author martagiliberto
     */
    public char[][] getCLIRepresentation(){
        char[][] res= new char[20][39];

        res[1][0]='a';
        res[3][0]='b';
        res[5][0]='c';
        res[7][0]='d';
        res[9][0]='e';
        res[11][0]='f';
        res[13][0]='g';
        res[15][0]='h';
        res[17][0]='i';

        res[19][4]='0';
        res[19][8]='1';
        res[19][12]='2';
        res[19][16]='3';
        res[19][20]='4';
        res[19][24]='5';
        res[19][28]='6';
        res[19][32]='7';
        res[19][36]='8';

        for(int i=0; i<19; i++){
            for(int j=2; j<39; j++){
                if(i%2==0){
                    if(j==2||j==6||j==10||j==14||j==18||j==22||j==26||j==30||j==34||j==38){
                        res[i][j]='+';
                    }else{
                        res[i][j]='-';
                    }

                }else{
                    if(j==2||j==6||j==10||j==14||j==18||j==22||j==26||j==30||j==34||j==38) {
                        res[i][j] = '|';
                    }
                }
            }
        }

        return res;
    }

    public void notifyMVEventListeners(MVEvent event){
        m.notifyMVEventListeners(new ModifiedLivingRoomEvent(new LightMatch(this.m)));
    }

}
