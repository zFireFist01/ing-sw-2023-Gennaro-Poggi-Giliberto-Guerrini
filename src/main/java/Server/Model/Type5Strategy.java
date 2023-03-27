package Server.Model;

public class Type5Strategy implements CommonGoalCheckingStrategy{
    private boolean check;

    @Override
    public boolean apply(Bookshelf bookshelf) {

        return check;
    }
}
