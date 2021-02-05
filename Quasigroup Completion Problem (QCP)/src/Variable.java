import java.util.ArrayList;

public class Variable {
    int row ;
    int col ;
    ArrayList<Integer> domain ;
    int max_forward_degree ;
    public Variable(int row, int col, ArrayList<Integer> domain) {
        max_forward_degree = 0 ;
        this.row = row;
        this.col = col;
        this.domain = domain;
    }
}
