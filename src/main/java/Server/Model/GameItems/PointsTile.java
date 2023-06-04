package Server.Model.GameItems;

public enum PointsTile {
    TWO_1, TWO_2, FOUR_1, FOUR_2,
    SIX_1, SIX_2, EIGHT_1, EIGHT_2,
    MATCH_ENDED;
    public static PointsTile randomPointsTile() {
        return values()[(int) (Math.random() * values().length)];
    }


    public int getNumberOfPoints(){
        if(this == TWO_1 || this == TWO_2){
            return 2;
        }else if(this == FOUR_1 || this == FOUR_2){
            return 4;
        }else if(this == SIX_1 || this == SIX_2){
            return 6;
        }else if(this == EIGHT_1 || this == EIGHT_2){
            return 8;
        }else{
            return -1;
        }
    }
}
