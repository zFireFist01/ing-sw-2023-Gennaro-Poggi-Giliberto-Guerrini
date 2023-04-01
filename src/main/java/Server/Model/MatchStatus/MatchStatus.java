package Server.Model.MatchStatus;

public abstract class MatchStatus {
    abstract protected MatchStatus evolve() throws UnsupportedOperationException;
}
