package Server.Model;

public class Type2Strategy implements CommonGoalCheckingStrategy {
    private boolean check;
    private BookshelfTileSpot[][] b;

    @Override
    public boolean apply(Bookshelf bookshelf) {

        b = bookshelf.getTileMatrix();

        if ((b[0][0].isEmpty()) || (b[0][4].isEmpty()) || (b[5][0].isEmpty()) || (b[5][4].isEmpty())) {
            return check;
        } else if ((b[5][0].getTile().equals(b[5][4].getTile())) &&
                (b[0][0].getTile().equals(b[0][4].getTile())) &&
                (b[0][0].getTile().equals(b[5][0].getTile()))) {
            check = true;
        }


        return check;
    }

}
