package Server.Model;

import com.google.gson.annotations.Expose;

import Server.Model.Cards.CommonGoalCard;
import Server.Model.Chat.PlayersChat;
import Server.Model.GameItems.LivingRoom;
import Server.Model.MatchStatus.MatchStatus;
import Server.Model.Player.Player;

import java.util.ArrayList;
import java.util.Map;

/**
 * This class is a light version of the Match class.
 * @author Paolo Gennaro
 */
public class LightMatch {

    private final PlayersChat gameChat; //ok
    private final int width;
    private final int height;
    @Expose
    private final Player currentPlayer; //ok
    @Expose
    private final Player winner; //ok
    @Expose
    private final LivingRoom livingRoom; //ok
    @Expose
    private final Player firstToFinish;//ok
    @Expose
    private ArrayList<Player> players;//ok
    @Expose
    private final int numberOfPlayers; //ok
    @Expose
    private final CommonGoalCard[] commonGoals;
    private MatchStatus matchStatus;
    @Expose
    private Map<Integer, Integer> scores; //PlayerID, score


    public LightMatch(Match match){
        this.gameChat = match.getGameChat();
        this.width = match.getWidth();
        this.height = match.getHeight();
        this.currentPlayer = match.getCurrentPlayer();
        this.winner = match.getWinner();
        this.livingRoom = match.getLivingRoom();
        this.firstToFinish = match.getFirstToFinish();
        this.players = match.getPlayers();
        this.numberOfPlayers = match.getNumberOfPlayers();
        this.commonGoals = match.getCommonGoals();
        this.matchStatus = match.getMatchStatus();
        this.scores = match.getIDScores();
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public Map<Integer, Integer> getScores() {
        return scores;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public CommonGoalCard[] getCommonGoals() {
        return commonGoals;
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
