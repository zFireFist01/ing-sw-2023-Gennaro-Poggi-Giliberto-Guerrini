package Server.Model.GameItems;

import Client.View.CLI.ANSIParameters;

/**
 * TileType defines all the type of tile that exists in the actual game
 */
public enum TileType{
    CATS, BOOKS, GAMES, FRAMES, TROPHIES, PLANTS,
    ;
    //CATS_2, BOOKS_2, GAMES_2, FRAMES_2, TROPHIES_2, PLANTS_2,
    //CATS_3, BOOKS_3, GAMES_3, FRAMES_3, TROPHIES_3, PLANTS_3,

    /**
     * @return a random TileType
     */
     //used only for testing
    public static TileType randomTileType() {
        return values()[(int) (Math.random() * values().length)];
    }

    /**
     * This method is used from the CLI to obtain a printable representation of this object
     * @return the char matrix that represents a "drawing" of this object
     */
    public String[][] getCLIRepresentation(){
        String[][] res = new String[1][1];
        switch (this){
            case CATS -> res[0][0] = ANSIParameters.BG_GREEN +" " + ANSIParameters.CRESET;
            case BOOKS -> res[0][0] = ANSIParameters.BG_WHITE +" " + ANSIParameters.CRESET;
            case GAMES -> res[0][0] = ANSIParameters.BG_YELLOW +" " + ANSIParameters.CRESET;
            case FRAMES -> res[0][0] = ANSIParameters.BG_BLUE +" " + ANSIParameters.CRESET;
            case TROPHIES -> res[0][0] = ANSIParameters.BG_CYAN +" " + ANSIParameters.CRESET;
            case PLANTS -> res[0][0] = ANSIParameters.BG_MAGENTA +" " + ANSIParameters.CRESET;
            default -> res[0][0] = "?";

        }
        return res;
    }
}
