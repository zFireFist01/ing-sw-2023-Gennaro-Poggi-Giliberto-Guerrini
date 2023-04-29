package Client.View.CLI;



public class Environment {

    private static final int DEFAULT_WIDTH = 170;
    private static final int DEFAULT_HEIGHT = 50;


    private final char [][] matrix;

    public Environment() {
        this.matrix = new char[DEFAULT_HEIGHT][DEFAULT_WIDTH];

        for (int i = 0; i < DEFAULT_HEIGHT; i++) {
            for (int j = 0; j < DEFAULT_WIDTH; j++) {
                this.matrix[i][j] = ' ';
            }
        }

        for (int i = 0; i < DEFAULT_HEIGHT; i++) {
            this.matrix[i][0] = '|';
            this.matrix[i][DEFAULT_WIDTH - 1] = '|';
        }

        for (int i = 0; i < DEFAULT_WIDTH; i++) {
            this.matrix[0][i] = '-';
            this.matrix[DEFAULT_HEIGHT - 1][i] = '-';
        }
    }

        public void setChar(int i, int j, char c) {
            this.matrix[i][j] = c;
        }



        public char[][] getMatrix() {
            return this.matrix.clone();
        }

        public void print(){

            //AnsiConsole.systemInstall();

            System.out.print(ANSIParameters.CLEAR_SCREEN + ANSIParameters.CURSOR_HOME);
            System.out.flush();

            for (int i = 0; i < DEFAULT_HEIGHT; i++) {
                for (int j = 0; j < DEFAULT_WIDTH; j++) {
                    System.out.print(this.matrix[i][j]);
                }
                System.out.println();
            }
            //AnsiConsole.systemUninstall();



        }








    }







}
