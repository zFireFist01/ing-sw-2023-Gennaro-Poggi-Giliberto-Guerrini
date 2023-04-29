package Server.Model;

import Server.Model.Chat.PlayersChat;
import Server.Model.GameItems.LivingRoom;
import Server.Model.Player.Player;

public class LightMatch {
    private final PlayersChat gameChat;
    private final int width;
    private final int height;
    private final Player currentPlayer;
    private final Player winner;
    private final LivingRoom livingRoom;
    private final Player firstToFinish;

    public LightMatch(Match match){
        this.gameChat = match.getGameChat();
        this.width = match.getWidth();
        this.height = match.getHeight();
        this.currentPlayer = match.getCurrentPlayer();
        this.winner = match.getWinner();
        this.livingRoom = match.getLivingRoom();
        this.firstToFinish = match.getFirstToFinish();
    }

    public PlayersChat getGameChat() {
        return gameChat;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public LivingRoom getLivingRoom() {
        return livingRoom;
    }

    public Player getFirstToFinish() {
        return firstToFinish;
    }
}
