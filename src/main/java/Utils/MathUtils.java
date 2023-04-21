package Utils;

import Server.Model.GameItems.LivingRoomTileSpot;
import org.jetbrains.annotations.NotNull;

public class MathUtils {
    /**
     * Helper method
     * @param mat is the given matrix, the one we want to rotate by 90°. It is assumed to be a square matrix
     * This method modifies the matrix mat so that at the end of its execution it's rotated rightwards of 90°
     */
    public static void rotateMatrix(@NotNull LivingRoomTileSpot[][] mat){
        for(int i=0; i< mat[i].length;i++){
            for(int j=0; j< mat[j].length; j++){
                LivingRoomTileSpot temp = new LivingRoomTileSpot(mat[i][j]);
                mat[i][j] = mat[j][i];
                mat[j][i] = temp;
            }
        }
        for(int i=0; i<mat[i].length;i++){
            LivingRoomTileSpot temp = new LivingRoomTileSpot(mat[i][0]);
            mat[i][0] = mat[i][mat[i].length];
            mat[i][mat[i].length] = temp;
        }
    }

    /**
     * Helper method.
     * Both matrixes are assumed to be square
     * @param matSource is the matrix we want to copy
     * @param matDest is the matrix where we want to copy
     * @param si is the line-index of matDest where the copy will start
     * @param sj is the column-index where the copy will start
     */
    public static void copy(LivingRoomTileSpot[][] matSource, LivingRoomTileSpot[][] matDest, int si, int sj){
        for(int i=si;i<matSource[0].length;i++){
            for(int j = sj; j< matSource[0].length;j++){
                matDest[i][j] = new LivingRoomTileSpot(matSource[i-si][j-sj]);
            }
        }
    }

    public static class Couple<I extends Number, I1 extends Number> {
        Integer a,b;
        public Couple(Integer i, Integer j){
            this.a = i;
            this.b = j;
        }

        public Integer getA() {
            return a;
        }

        public Integer getB() {
            return b;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            return a.intValue()==((Couple<Number, Number>) o).getA() && b.intValue() == ((Couple<Number, Number>) o).getB();
        }

        @Override
        public String toString() {
            return "Couple{" +
                    "a=" + a +
                    ", b=" + b +
                    '}';
        }
    }
}
