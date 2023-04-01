package Server.Model.Cards;

import Server.Model.GameItems.BookshelfTileSpot;
import Server.Model.GameItems.TileType;

public enum PersonalGoalCard implements Card {
    private BookshelfTileSpot[][] layout1 =
            {{new BookshelfTileSpot(TileType.CATS_1), new BookshelfTileSpot(), null, }},


    private BookshelfTileSpot[][] layout2;
    private BookshelfTileSpot[][] layout3;
    private BookshelfTileSpot[][] layout4;
    private BookshelfTileSpot[][] layout5;
    private BookshelfTileSpot[][] layout6;
    private BookshelfTileSpot[][] layout7;
    private BookshelfTileSpot[][] layout8;
    private BookshelfTileSpot[][] layout9;
    private BookshelfTileSpot[][] layout10;
    private BookshelfTileSpot[][] layout11;
    private BookshelfTileSpot[][] layout12;






    private BookshelfTileSpot[][] tileMatrix;


    PersonalGoalCard(BookshelfTileSpot[][] mat){

    }
    public BookshelfTileSpot[][] getTileMatrix(){

    }
    TYPE1(layout1),
    TYPE2(layout2),
    TYPE3(layout3),
    TYPE4(layout4),
    TYPE5(layout5),
    TYPE6(layout6),
    TYPE7(layout7),
    TYPE8(layout8),
    TYPE9(layout9),
    TYPE10(layout10),
    TYPE11(layout11),
    TYPE12(layout12)
}
