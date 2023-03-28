package Server.Model;

import Server.Model.LivingRoom;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * match class in order to store information about the match
 * @author Valentino Guerrini
 */
public class Match {
    private ArrayList<Player> players;
    private final int numberOfPlayers;
    private CommnoGoalCard[] commonGoals;
    private MatchStatus matchStatus;
    private final Player matchOpener;
    private Player firstPlayer;
    private Player currentPlayer;
    private Player winner;
    private final LivingRoom livingRoom;
    private final CommonGoalCardsDeck commonGoalDeck;
    private final PersonalGoalCardsDeck personalGoalDeck;
    private Map<Player, Integer> scores;
    private Time matchDuration;

    public Match(int numberOfPlayers, Player matchOpener) {
        this.numberOfPlayers = numberOfPlayers;
        this.matchOpener = matchOpener;
        this.livingRoom = new LivingRoom();
        this.commonGoalDeck = new CommonGoalCardsDeck();
        this.personalGoalDeck = new PersonalGoalCardsDeck();
    }

    public void setup(){}

    public void checkCommonGoals(Player player){


    }

    private void extractFirstPlayer(){}

    private void extractCommonGoals(){}

    public void addContestant(){}

    public void calculateFinalScores(){}


    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    } //secondo me andrebbe messo getwinner il winner viene settato in calculatefinalscores

    //METODI GETTER

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public CommnoGoalCard[] getCommonGoals() {
        return commonGoals;
    }

    public MatchStatus getMatchStatus() {
        return matchStatus;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public LivingRoom getLivingRoom() {
        return livingRoom;
    }

    public Map<Player, Integer> getScores() {
        return scores;
    }

    public Time getMatchDuration() {
        return matchDuration;
    }

    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

}
