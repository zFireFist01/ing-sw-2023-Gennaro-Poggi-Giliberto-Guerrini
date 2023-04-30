package Server.Model;

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
    private final PlayersChat gameChat;
    private final int width;
    private final int height;
    private final Player currentPlayer;
    private final Player winner;
    private final LivingRoom livingRoom;
    private final Player firstToFinish;
    private ArrayList<Player> players;
    private final int numberOfPlayers;
    private final CommonGoalCard[] commonGoals;
    private MatchStatus matchStatus;
    private Map<Player, Integer> scores;


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
        this.scores = match.getScores();
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public Map<Player, Integer> getScores() {
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
