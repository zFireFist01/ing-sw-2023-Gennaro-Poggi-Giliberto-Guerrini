package Server.Model;

public class Type2Strategy implements CommonGoalCheckingStrategy {
    private boolean check;
    private TileType tmp;
    private BookshelfTileSpot[][] b;
    private int i;
    private int j;

    @Override
    public boolean apply(Bookshelf bookshelf) {

        b= bookshelf.getTileMatrix();






        return check;
    }

}
