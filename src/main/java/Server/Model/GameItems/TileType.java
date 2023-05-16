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

}
