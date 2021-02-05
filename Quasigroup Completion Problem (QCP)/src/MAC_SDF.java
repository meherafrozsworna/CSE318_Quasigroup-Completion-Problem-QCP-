import java.util.*;

public class MAC_SDF {
    int size ;
    int board[][];
    PriorityQueue<Variable> pq ;
    Variable variable[][];
    int number_of_nodes_visited ;
    int number_of_back_track ;

    public MAC_SDF(int size, int[][] b) {
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


        Map<Variable, ArrayList<Integer>> prevDomain =  savePreviousDomain() ;

        for (int i = 0; i < v.domain.size() ; i++) {
            board[v.row][v.col] = v.domain.get(i);
            number_of_nodes_visited++ ;
            //System.out.println(v.row +","+ v.col +" ----> "+ v.domain.get(i) );

            boolean at_least_one_value_for_every_variable =
                    maintaining_arc_consistency(v.row,v.col,v.domain.get(i));

            if (at_least_one_value_for_every_variable){
                if (checklatinSquare() == true)
                    return true;
            }


            number_of_back_track++ ;
            board[v.row][v.col] = 0 ;
            setDomainToPrevDomain(prevDomain);

        }


        pq.add(v) ;
        return false;
    }

    boolean maintaining_arc_consistency(int row,int col, int val){
        Queue<Variable> changed_nodes = new LinkedList<Variable>();

        for (int i = 0; i < size ; i++) {
            if (board[row][i] ==0 && (i!= col) && variable[row][i].domain.contains(val)){
                variable[row][i].domain.remove(new Integer(val));
                changed_nodes.add(variable[row][i]);
            }
            if (board[i][col] == 0 && (i != row ) &&  variable[i][col].domain.contains(val)){
                variable[i][col].domain.remove(new Integer(val));
                changed_nodes.add(variable[i][col]) ;
            }
        };

        Queue<Arc> arcQueue = new LinkedList<Arc>();

        while (!changed_nodes.isEmpty()){

            Variable v = changed_nodes.poll() ;

            for (int i = 0; i < size ; i++) {
                if (board[v.row][i] == 0 && i!= v.col ){
                    arcQueue.add(new Arc( variable[v.row][i] , variable[v.row][v.col] ));
                }
                if (board[i][v.col] == 0 && i!= v.row ){
                    arcQueue.add(new Arc( variable[i][v.col] , variable[v.row][v.col] ));
                }
            }
        }

        while (!arcQueue.isEmpty()){
            Arc arc = arcQueue.poll();
            if(domainChanged_for_arc(arc)){
                Variable vv = arc.v1 ;
                for (int i = 0; i < size ; i++) {
                    if (board[vv.row][i] == 0 && i!= vv.col ){
                        arcQueue.add(new Arc( variable[vv.row][i] , variable[vv.row][vv.col] ));
                    }
                    if (board[i][vv.col] == 0 && i!= vv.row ){
                        arcQueue.add(new Arc( variable[i][vv.col] , variable[vv.row][vv.col] ));
                    }
                }
            }

        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size ; j++) {
                if (board[i][j] == 0 && variable[i][j].domain.size()== 0){
                    return false ;
                }
            }
        }
        return true ;
    }


    public boolean domainChanged_for_arc(Arc arc){

        boolean changed = false ;
        for (int i = 0; i < arc.v1.domain.size(); i++) {
            int x = arc.v1.domain.get(i);

            boolean satisfy = false ;
            for (int j = 0; j < arc.v2.domain.size() ; j++) {
                int y = arc.v2.domain.get(j);
                if (x != y){
                    satisfy = true ;
                    break;
                }
            }
            if (satisfy == false){
                //System.out.println(">>>>>>>"+arc.v1.domain);
                arc.v1.domain.remove(new Integer(x));
                //System.out.println(arc.v1.domain);
                changed = true ;
            }
        }
        return changed ;
    }

    public Map<Variable, ArrayList<Integer>> savePreviousDomain()
    {
        Map<Variable, ArrayList<Integer>> map = new HashMap<Variable, ArrayList<Integer>>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size ; j++) {
                if (board[i][j] == 0 ){
                    ArrayList<Integer> arrayList = (ArrayList<Integer>)variable[i][j].domain.clone() ;
                    map.put(variable[i][j],arrayList) ;
                }
            }
        }

        return map ;
    }

    public void setDomainToPrevDomain(Map<Variable, ArrayList<Integer>> prevDomain)
    {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size ; j++) {
                if (board[i][j] == 0 ){
                    variable[i][j].domain = (ArrayList<Integer>)prevDomain.get(variable[i][j]).clone() ;
                }
            }
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
