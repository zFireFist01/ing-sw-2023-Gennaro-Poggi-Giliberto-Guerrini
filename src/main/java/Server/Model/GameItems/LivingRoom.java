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
 * @Class Livingroom
 * @author patrickpoggi
 */
public class LivingRoom {

    private final static int LIVINGROOOMWIDTH = 9;
    private final static int LIVINGROOMHEIGHT = 9;

    @Expose
    private LivingRoomTileSpot[][] tileMatrix; //ok

    private Map<TileType, Integer> tileSack;


    private Match m; // Link to the match this livingroom belongs to

    /**
     * Initializes the tileMatrix as a Livingoroom without tiles
     * Creates a tileSack
     * @param m refers to the match this livingroom belongs in
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
     * This methods allows the controller to send an "order" of picking more than one tile with only one call
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
     * @author Marta Giliberto
     */
    public char[][] getCLIRepresentation(){
        char[][] res= new char[20][39];

        for(int i=0; i<19; i++) {
            if (i % 2 == 0) {
                if (this.getTileMatrix()[i][0].isEmpty()) {
                    res[i][0] = ' ';
                } else {
                    res[i][0] = this.getTileMatrix()[i][0].getTileType().getCLIRepresentation()[0][0];
                }
            } else {
                res[i][3] = ' ';

                if (this.getTileMatrix()[i][4].isEmpty()) {
                    res[i][4] = ' ';
                } else {
                    res[i][4] = this.getTileMatrix()[i][4].getTileType().getCLIRepresentation()[0][0];
                }

                res[i][5] = ' ';
                res[i][7] = ' ';

                if (this.getTileMatrix()[i][8].isEmpty()) {
                    res[i][8] = ' ';
                } else {
                    res[i][8] = this.getTileMatrix()[i][8].getTileType().getCLIRepresentation()[0][0];
                }

                res[i][9] = ' ';
                res[i][11] = ' ';

                if (this.getTileMatrix()[i][12].isEmpty()) {
                    res[i][12] = ' ';
                } else {
                    res[i][12] = this.getTileMatrix()[i][12].getTileType().getCLIRepresentation()[0][0];
                }

                res[i][13] = ' ';
                res[i][15] = ' ';

                if (this.getTileMatrix()[i][16].isEmpty()) {
                    res[i][16] = ' ';
                } else {
                    res[i][16] = this.getTileMatrix()[i][16].getTileType().getCLIRepresentation()[0][0];
                }

                res[i][17] = ' ';
                res[i][19] = ' ';

                if (this.getTileMatrix()[i][20].isEmpty()) {
                    res[i][20] = ' ';
                } else {
                    res[i][20] = this.getTileMatrix()[i][20].getTileType().getCLIRepresentation()[0][0];
                }

                res[i][21] = ' ';
                res[i][23] = ' ';

                if (this.getTileMatrix()[i][24].isEmpty()) {
                    res[i][24] = ' ';
                } else {
                    res[i][24] = this.getTileMatrix()[i][24].getTileType().getCLIRepresentation()[0][0];
                }

                res[i][25] = ' ';
                res[i][27] = ' ';

                if (this.getTileMatrix()[i][28].isEmpty()) {
                    res[i][28] = ' ';
                } else {
                    res[i][28] = this.getTileMatrix()[i][28].getTileType().getCLIRepresentation()[0][0];
                }

                res[i][29] = ' ';
                res[i][31] = ' ';

                if (this.getTileMatrix()[i][32].isEmpty()) {
                    res[i][32] = ' ';
                } else {
                    res[i][32] = this.getTileMatrix()[i][32].getTileType().getCLIRepresentation()[0][0];
                }

                res[i][33] = ' ';
                res[i][35] = ' ';

                if (this.getTileMatrix()[i][36].isEmpty()) {
                    res[i][36] = ' ';
                } else {
                    res[i][36] = this.getTileMatrix()[i][0].getTileType().getCLIRepresentation()[0][0];
                }

                res[i][37] = ' ';
            }
        }

        for(int j=0; j<39;j++){
            if(j%4!=0){
                res[19][j]=' ';
            }
        }


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

        //return new char[20][38];
    }


    public void notifyMVEventListeners(MVEvent event){
        m.notifyMVEventListeners(new ModifiedLivingRoomEvent(new LightMatch(this.m)));
    }

}
