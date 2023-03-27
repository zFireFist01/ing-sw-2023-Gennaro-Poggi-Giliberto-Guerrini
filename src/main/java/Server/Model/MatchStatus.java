package Server.Model;

public abstract class MatchStatus {
    abstract protected MatchStatus evolve() throws UnsupportedOperationException;
}
