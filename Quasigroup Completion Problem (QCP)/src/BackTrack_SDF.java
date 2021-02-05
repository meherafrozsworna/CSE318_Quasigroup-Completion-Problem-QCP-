import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BackTrack_SDF {
    int size ;
    int board[][];
    PriorityQueue<Variable> pq ;
    Variable variable[][];
    int number_of_nodes_visited ;
    int number_of_back_track ;

    public BackTrack_SDF(int size, int[][] b) {
        this.size = size;
        this.board = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = b[i][j];
            }
        }

        number_of_nodes_visited = 0;
        number_of_back_track = 0;

        pq = new PriorityQueue<Variable>(new Comparator<Variable>() {
            public int compare(Variable lhs, Variable rhs) {
                if (lhs.domain.size() > rhs.domain.size()) return +1;
                if (lhs.domain.size() ==rhs.domain.size()) return 0;
                return -1;
            }
        });

        variable = new Variable[size][size];


        for (int i = 0; i < size; i++) {
            for (int j = 0 ; j < size ; j++) {
                //domain[i*size+j]---> board[i][j]
                if (board[i][j] == 0){
                    ArrayList<Integer> arrayList = new ArrayList<>();
                    for (int k = 1; k <= size; k++) {
                        arrayList.add(k);
                    }

                    //changed with FC
                    for (int k = 0; k < size; k++) {
                        if (board[i][k] != 0){
                            arrayList.remove(new Integer(board[i][k]));
                        }
                        if (board[k][j] != 0){
                            arrayList.remove(new Integer(board[k][j]));
                        }

                    }
                    //domain[i*size+j] = arrayList ;
                    variable[i][j] = new Variable(i,j,arrayList);
                    pq.add(variable[i][j]);
                    //System.out.println(i+"  " +j+ arrayList);
                }

            }
        }
    }

    boolean checklatinSquare(){
        if(checkValidityOfCurrentBoard()== false)
            return false;

        if (pq.isEmpty()){
            for (int i = 0; i < size ; i++) {
                for (int j = 0; j < size ; j++) {
                    System.out.print(board[i][j] + "  ");
                }
                System.out.println();
            }
            return true ;
        }

        Variable v = pq.poll() ;
        //System.out.println(v.row +","+ v.col +"  "+ v.domain );
        //number_of_nodes_visited++ ;
        for (int i = 0; i < v.domain.size() ; i++) {
            number_of_nodes_visited++ ;
            if (canBePlaced(v.row,v.col,v.domain.get(i))){
                board[v.row][v.col] = v.domain.get(i);

                //System.out.println(v.row +","+ v.col +" ----> "+ v.domain.get(i) );


                if (checklatinSquare() == true)
                    return true;

                //number_of_back_track++ ;
                board[v.row][v.col] = 0 ;
            }
            else {
                number_of_back_track++ ;
            }


        }


        pq.add(v) ;
        return false;
    }

    boolean canBePlaced(int row,int col,int val){
        for (int i = 0; i < size ; i++) {
            if (board[row][i] == val)
                return false;
        }
        for (int i = 0; i < size ; i++) {
            if (board[i][col] == val)
                return false;
        }
        return true ;
    }

    boolean checkValidityOfCurrentBoard()
    {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != 0){
                    for (int k = 0; k < size ; k++) {
                        if(k!= j && board[i][k]== board[i][j])
                            return false ;
                    }
                    for (int k = 0; k < size ; k++) {
                        if(k!= i && board[k][j]== board[i][j])
                            return false ;
                    }
                }
            }
        }
        return true ;
    }

}
