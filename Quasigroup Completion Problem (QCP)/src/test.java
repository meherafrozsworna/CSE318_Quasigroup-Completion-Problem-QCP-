import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args)
    {
        List<Integer> al = new ArrayList<>();
        al.add(10);
        al.add(20);
        al.add(30);
        al.add(1);
        al.add(2);

        // This makes a call to remove(Object) and
        // removes element 1
        al.remove(new Integer(1));

        // This makes a call to remove(Object) and
        // removes element 2
        al.remove(new Integer(100));

        System.out.println("Modified ArrayList : " + al);
    }
}
