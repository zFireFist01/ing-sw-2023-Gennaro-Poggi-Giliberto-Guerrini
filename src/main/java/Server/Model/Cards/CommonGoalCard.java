package Server.Model.Cards;

import Server.Model.GameItems.Bookshelf;
import Server.Model.Cards.Card;

public abstract class CommonGoalCard implements Card {
   abstract public boolean check(Bookshelf bookshelf);
}
