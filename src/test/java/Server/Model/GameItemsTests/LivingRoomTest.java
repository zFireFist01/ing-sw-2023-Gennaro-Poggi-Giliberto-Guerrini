package Server.Model.GameItemsTests;

import Server.Model.GameItems.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import java.util.Map;

import static org.junit.Assert.*;


public class LivingRoomTest {
    LivingRoom uut = null;
    MatchNeededMethods m = null;
    @Before
    public void setup(int numberOfPlayers){
        m = new MatchNeededMethods(numberOfPlayers);
    }

    @After
    public void tearDown(){}

    @Test
    public void constructorTest(){
        uut = new LivingRoom(m);

        //Defining the expected matrix after initialization
        LivingRoomTileSpot[][] exp = new LivingRoomTileSpot[9][9];
        exp[0][0] = new LivingRoomTileSpot(m, -1); exp[0][1] = new LivingRoomTileSpot(m, -1);
        exp[0][2] = new LivingRoomTileSpot(m, -1); exp[0][3] = new LivingRoomTileSpot(m, 3);
        exp[0][4] = new LivingRoomTileSpot(m, 4); exp[0][5] = new LivingRoomTileSpot(m, -1);
        exp[0][6] = new LivingRoomTileSpot(m, -1); exp[0][7] = new LivingRoomTileSpot(m, -1);
        exp[0][8] = new LivingRoomTileSpot(m, -1);

        exp[1][0] = new LivingRoomTileSpot(m, -1); exp[1][1] = new LivingRoomTileSpot(m, -1);
        exp[1][2] = new LivingRoomTileSpot(m, -1); exp[1][3] = new LivingRoomTileSpot(m, 0);
        exp[1][4] = new LivingRoomTileSpot(m, 0); exp[1][5] = new LivingRoomTileSpot(m, 4);
        exp[1][6] = new LivingRoomTileSpot(m, -1); exp[1][7] = new LivingRoomTileSpot(m, -1);
        exp[1][8] = new LivingRoomTileSpot(m, -1);

        exp[2][0] = new LivingRoomTileSpot(m, -1); exp[2][1] = new LivingRoomTileSpot(m, -1);
        exp[2][2] = new LivingRoomTileSpot(m, 3); exp[2][3] = new LivingRoomTileSpot(m, 0);
        exp[2][4] = new LivingRoomTileSpot(m, 0); exp[2][5] = new LivingRoomTileSpot(m, 0);
        exp[2][6] = new LivingRoomTileSpot(m, 3); exp[2][7] = new LivingRoomTileSpot(m, -1);
        exp[2][8] = new LivingRoomTileSpot(m, -1);

        exp[3][0] = new LivingRoomTileSpot(m, -1); exp[3][1] = new LivingRoomTileSpot(m, 4);
        exp[3][2] = new LivingRoomTileSpot(m, 0); exp[3][3] = new LivingRoomTileSpot(m, 0);
        exp[3][4] = new LivingRoomTileSpot(m, 0); exp[3][5] = new LivingRoomTileSpot(m, 0);
        exp[3][6] = new LivingRoomTileSpot(m, 0); exp[3][7] = new LivingRoomTileSpot(m, 0);
        exp[3][8] = new LivingRoomTileSpot(m, 3);

        exp[4][0] = new LivingRoomTileSpot(m, 4); exp[4][1] = new LivingRoomTileSpot(m, 0);
        exp[4][2] = new LivingRoomTileSpot(m, 0); exp[4][3] = new LivingRoomTileSpot(m, 0);
        exp[4][4] = new LivingRoomTileSpot(m, 0); exp[4][5] = new LivingRoomTileSpot(m, 0);
        exp[4][6] = new LivingRoomTileSpot(m, 0); exp[4][7] = new LivingRoomTileSpot(m, 0);
        exp[4][8] = new LivingRoomTileSpot(m, 4);

        exp[5][0] = new LivingRoomTileSpot(m, 3); exp[5][1] = new LivingRoomTileSpot(m, 0);
        exp[5][2] = new LivingRoomTileSpot(m, 0); exp[5][3] = new LivingRoomTileSpot(m, 0);
        exp[5][4] = new LivingRoomTileSpot(m, 0); exp[5][5] = new LivingRoomTileSpot(m, 0);
        exp[5][6] = new LivingRoomTileSpot(m, 0); exp[5][7] = new LivingRoomTileSpot(m, 4);
        exp[5][8] = new LivingRoomTileSpot(m, -1);

        exp[6][0] = new LivingRoomTileSpot(m, -1); exp[6][1] = new LivingRoomTileSpot(m, -1);
        exp[6][2] = new LivingRoomTileSpot(m, 3); exp[6][3] = new LivingRoomTileSpot(m, 0);
        exp[6][4] = new LivingRoomTileSpot(m, 0); exp[6][5] = new LivingRoomTileSpot(m, 0);
        exp[6][6] = new LivingRoomTileSpot(m, 3); exp[6][7] = new LivingRoomTileSpot(m, -1);
        exp[6][8] = new LivingRoomTileSpot(m, -1);

        exp[7][0] = new LivingRoomTileSpot(m, -1); exp[7][1] = new LivingRoomTileSpot(m, -1);
        exp[7][2] = new LivingRoomTileSpot(m, -1); exp[7][3] = new LivingRoomTileSpot(m, 4);
        exp[7][4] = new LivingRoomTileSpot(m, 0); exp[7][5] = new LivingRoomTileSpot(m, 0);
        exp[7][6] = new LivingRoomTileSpot(m, -1); exp[7][7] = new LivingRoomTileSpot(m, -1);
        exp[7][8] = new LivingRoomTileSpot(m, -1);

        exp[8][0] = new LivingRoomTileSpot(m, -1); exp[8][1] = new LivingRoomTileSpot(m, -1);
        exp[8][2] = new LivingRoomTileSpot(m, -1); exp[8][3] = new LivingRoomTileSpot(m, -1);
        exp[8][4] = new LivingRoomTileSpot(m, 4); exp[8][5] = new LivingRoomTileSpot(m, 3);
        exp[8][6] = new LivingRoomTileSpot(m, -1); exp[8][7] = new LivingRoomTileSpot(m, -1);
        exp[8][8] = new LivingRoomTileSpot(m, -1);

        LivingRoomTileSpot[][] mat = uut.getTileMatrix();
        //assertEquals(mat, exp);
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                assert ((mat[i][j].isReal()==exp[i][j].isReal()) &&
                        (!(exp[i][j].isReal()) ||
                                                (exp[i][j].getTileType() == mat[i][j].getTileType() &&
                                                        exp[i][j].getDotsNumber()==mat[i][j].getDotsNumber())));
            }
        }

        //We've also got to check the tile sack
        Map<TileType, Integer> ts = null;
        assert (ts.equals(uut.getTileSack())); //Using the equals method of class HashMap

    }

    @Test
    public void refreshLivingRoomTest(){
        uut.refreshLivingRoom();

        //Checking that "fake" spots are still fake
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                switch (i){
                    case 0:
                        switch (j){
                            case 0, 1, 2, 5, 6, 7, 8:
                                assert(uut.getTileMatrix()[i][j].isReal() == false);
                                break;
                            default:
                                assert(uut.getTileMatrix()[i][j].getTileType() != null);
                        }
                    case 1:
                        switch (j){
                            case 0, 1, 2, 6, 7, 8:
                                assert(uut.getTileMatrix()[i][j].isReal() == false);
                                break;
                            default:
                                assert(uut.getTileMatrix()[i][j].getTileType() != null);
                        }
                    case 2:
                        switch (j){
                            case 0, 1, 7, 8:
                                assert(uut.getTileMatrix()[i][j].isReal() == false);
                                break;
                            default:
                                assert(uut.getTileMatrix()[i][j].getTileType() != null);
                        }
                    case 3:
                        switch (j){
                            case 0:
                                assert(uut.getTileMatrix()[i][j].isReal() == false);
                                break;
                            default:
                                assert(uut.getTileMatrix()[i][j].getTileType() != null);
                        }
                    case 6:
                        switch (j){
                            case 0, 1, 7, 8:
                                assert(uut.getTileMatrix()[i][j].isReal() == false);
                                break;
                            default:
                                assert(uut.getTileMatrix()[i][j].getTileType() != null);
                        }
                    case 7:
                        switch (j){
                            case 0, 1, 2, 6, 7, 8:
                                assert(uut.getTileMatrix()[i][j].isReal() == false);
                                break;
                            default:
                                assert(uut.getTileMatrix()[i][j].getTileType() != null);
                        }
                    case 8:
                        switch (j){
                            case 0, 1, 2, 3, 6, 7, 8:
                                assert(uut.getTileMatrix()[i][j].isReal() == false);
                                break;
                            default:
                                assert(uut.getTileMatrix()[i][j].getTileType() != null);
                        }
                    default: assert(uut.getTileMatrix()[i][j] != null);
                }
            }
        }

        //Now, depending on the number of players (except 4 which is equivalent to what we've just done,
        // we have to check that no unavailable spot has been made available
        int n = m.getNumberOfPlayers();
        if(n<4){
            assert (uut.getTileMatrix()[0][4].isReal()==false);
            assert (uut.getTileMatrix()[1][5].isReal()==false);
            assert (uut.getTileMatrix()[3][1].isReal()==false);
            assert (uut.getTileMatrix()[4][0].isReal()==false);
            assert (uut.getTileMatrix()[4][8].isReal()==false);
            assert (uut.getTileMatrix()[5][7].isReal()==false);
            assert (uut.getTileMatrix()[7][3].isReal()==false);
            assert (uut.getTileMatrix()[8][4].isReal()==false);
            //All these have to be unavailable if the number of players is <4

            //Moreover if there are only two players (less than that is not possible)
            // we have to check the tiles with 3 spots
            if(n==2){
                assert (uut.getTileMatrix()[0][3]).isReal()==false;
                assert (uut.getTileMatrix()[2][2]).isReal()==false;
                assert (uut.getTileMatrix()[2][6]).isReal()==false;
                assert (uut.getTileMatrix()[3][8]).isReal()==false;
                assert (uut.getTileMatrix()[5][0]).isReal()==false;
                assert (uut.getTileMatrix()[6][2]).isReal()==false;
                assert (uut.getTileMatrix()[6][6]).isReal()==false;
                assert (uut.getTileMatrix()[8][5]).isReal()==false;
            }
        }

        //Now, if we got here, we are sure that all the spots that have to be unavailable or fake are like so
        //So we can check that all the others actually contain a tile by simply inspecting all the matrix
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(uut.getTileMatrix()[i][j].isReal()){
                    assert (uut.getTileMatrix()[i][j].getTileType() != null);
                }
            }
        }
    }

    @Test
    public void getTileSackTest(){
        Map<TileType, Integer> ts = uut.getTileSack();
        for(TileType tt : TileType.values()){
            assert (ts.get(tt)<=22 && ts.get(tt)>=0);
        }
        assert (ts.size()==6); //no tiletypes removed or added
    }

    @Test
    public void takeTile_expectedException_Test(){
        assertThrows(UnsupportedOperationException.class, ()->{uut.takeTile(0,0);});
        assertThrows(UnsupportedOperationException.class, ()->{uut.takeTile(4,4);}); //Should throw the exception
                                                                                            //only in the test, not in general
        if(m.getNumberOfPlayers()==3){
            assertThrows(UnsupportedOperationException.class, ()->{uut.takeTile(0,4);});
        } else if (m.getNumberOfPlayers() == 2) {
            assertThrows(UnsupportedOperationException.class, ()->{uut.takeTile(0,4);});
            assertThrows(UnsupportedOperationException.class, ()->{uut.takeTile(0,3);});
        }
    }

    @Test
    public void takeTile_expectedTile_Test(){
        assert(uut.takeTile(1,3) instanceof TileType );
        assertNotNull(uut.takeTile(1,3));
    }
}
