package Server.Model.GameItems;

import Client.View.CLI.ANSIParameters;

/**
 * TileType defines all the type of tile that exists in the actual game
 * @author Patrick Poggi
 */
public enum TileType{
    CATS, BOOKS, GAMES, FRAMES, TROPHIES, PLANTS,
    ;

    /**
     * This method is used only for testing
     * @return a random TileType
     */
    public static TileType randomTileType() {
        return values()[(int) (Math.random() * values().length)];
    }

}
