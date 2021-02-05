public class Board {
    int size ;
    int board[][] ;
    String fileName ="";

    public Board(int size, int[][] board) {
        this.size = size;
        this.board = board;
    }

    public void printBoard()
    {
        System.out.println("XXXXXXXXXXXXXXX");
        System.out.println(fileName);
        System.out.println(size+"\n");
        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size ; j++) {
                System.out.print(board[i][j]+"  ");
            }
            System.out.println();
        }
        System.out.println("XXXXXXXXXXXXXXX");


    }
}
