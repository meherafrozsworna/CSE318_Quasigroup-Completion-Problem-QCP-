import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class forwardChecking_domddeg {
    int size ;
    int board[][];
    PriorityQueue<Variable> pq ;
    Variable variable[][];
    int number_of_nodes_visited ;
    int number_of_back_track ;

    public forwardChecking_domddeg(int size, int[][] b) {
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

                double ratio1,ratio2 ;
                if (lhs.max_forward_degree != 0){
                    ratio1 = (lhs.domain.size()*1.0)/lhs.max_forward_degree ;
                }else {
                    ratio1 = lhs.domain.size() ;
                }
                if (rhs.max_forward_degree != 0) {
                    ratio2 = (rhs.domain.size() * 1.0) / rhs.max_forward_degree;
                }else{
                    ratio2 = rhs.domain.size() ;
                }


                if (ratio1 > ratio2)
                    return +1;
                else if (ratio1 < ratio2)
                    return -1;

                return 0;
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

        for (int i = 0; i < size ; i++) {
            for (int j = 0; j < size ; j++) {
                if (board[i][j] == 0){
                    for (int k = 0; k < size ; k++) {
                        if (board[i][k] == 0 && k != j){
                            variable[i][k].max_forward_degree++ ;
                        }
                    }
                    for (int k = 0; k < size ; k++) {
                        if (board[k][j] == 0 && k != i){
                            variable[k][j].max_forward_degree++ ;
                        }
                    }
                }
            }
        }
    }

    void test(){
        while (!pq.isEmpty()){
            Variable v = pq.poll();
            System.out.println(v.domain.size() + "  " +v.max_forward_degree);
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
        //System.out.println(v.row +","+ v.col +"  "+ v.domain +"  " +v.max_forward_degree);
        //number_of_nodes_visited++ ;
        for (int i = 0; i < v.domain.size() ; i++) {
            board[v.row][v.col] = v.domain.get(i);
            number_of_nodes_visited++ ;
            //System.out.println(v.row +","+ v.col +" ----> "+ v.domain.get(i) );

            ArrayList<Variable> variablesContainingval = removeFromDomain(v.row,v.col,v.domain.get(i));

            if (checklatinSquare() == true)
                return true;

            addToDomain(variablesContainingval ,v.row,v.col,v.domain.get(i));
            number_of_back_track++ ;
            board[v.row][v.col] = 0 ;
        }


        pq.add(v) ;
        return false;
    }

    ArrayList<Variable> removeFromDomain(int row,int col, int val){
        ArrayList<Variable> arrayList = new ArrayList<>();
        for (int i = 0; i < size ; i++) {
            if (board[row][i] == 0 && (i!= col)){
                variable[row][i].max_forward_degree-- ;
                if (variable[row][i].domain.contains(val)){
                    variable[row][i].domain.remove(new Integer(val));
                    arrayList.add(variable[row][i]);
                }
            }
            if (board[i][col] == 0 && (i != row )){
                variable[i][col].max_forward_degree-- ;
                if (variable[i][col].domain.contains(val)){
                    variable[i][col].domain.remove(new Integer(val));
                    arrayList.add(variable[i][col]) ;
                }
            }
        }
        return arrayList ;
    }
    void addToDomain(ArrayList<Variable> arrayList ,int row,int col, int val){
        for (int i = 0; i < size; i++) {
            if (board[row][i] == 0 && (i!= col)){
                variable[row][i].max_forward_degree++ ;

            }
            if (board[i][col] == 0 && (i != row )){
                variable[i][col].max_forward_degree++ ;
            }
        }
        for (int i = 0; i < arrayList.size(); i++) {
            arrayList.get(i).domain.add(val);
        }
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
