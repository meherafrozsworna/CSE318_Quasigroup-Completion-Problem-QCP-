import java.io.*;
import java.util.ArrayList;

public class FileRead {
    ArrayList<String> files ;

    public FileRead(ArrayList<String> files) {
        this.files = files;
    }

    ArrayList<Board> read() throws IOException {
        ArrayList<Board> boards = new ArrayList<>();
        for (int i = 0; i < files.size() ; i++) {
            String fileName = files.get(i) ;

            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            String line = br.readLine();
            String s[] = line.split("=");


            int size = Integer.parseInt(s[1].substring(0,s[1].length()-1));

            int board [][] = new int[size][size] ;
            br.readLine();
            br.readLine();

            for (int j = 0; j < size ; j++) {
                line = br.readLine();
                String str[] = line.split(",");

                for (int k = 0; k < str.length-1; k++) {
                    board[j][k] = Integer.parseInt(str[k].replaceAll("\\s+",""));
                }


                //System.out.println(str[str.length-1]);
                //System.out.println(str[str.length-1].replaceAll("\\s+",""));
                String str2[] = str[str.length-1].replaceAll("\\s+","").split("\\|");
                //System.out.println(str2[0]);
                board[j][str.length-1] = Integer.parseInt(str2[0]);
            }
            fr.close();

            Board board1 = new Board(size,board);
            board1.fileName = fileName ;
            boards.add(board1) ;
        }



        return boards ;
    }
}
