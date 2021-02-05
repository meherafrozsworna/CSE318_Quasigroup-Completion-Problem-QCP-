import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class main {
    public static void main(String[] args) {

        ArrayList<String> files = new ArrayList<>();
        files.add("data/d-10-01.txt.txt");
        files.add("data/d-10-06.txt.txt");
        files.add("data/d-10-07.txt.txt");
        files.add("data/d-10-08.txt.txt");
        files.add("data/d-10-09.txt.txt");
        files.add("data/d-15-01.txt.txt");

        ArrayList<Integer> number_of_nodes_BT_SDF = new ArrayList<>();
        ArrayList<Integer> number_of_backtrack_BT_SDF = new ArrayList<>();

        ArrayList<Integer> number_of_nodes_FC_SDF = new ArrayList<>();
        ArrayList<Integer> number_of_backtrack_FC_SDF = new ArrayList<>();

        ArrayList<Integer> number_of_nodes_FC_domddeg = new ArrayList<>();
        ArrayList<Integer> number_of_backtrack_FC_domddeg = new ArrayList<>();

        ArrayList<Integer> number_of_nodes_FC_belaz = new ArrayList<>();
        ArrayList<Integer> number_of_backtrack_FC_belaz = new ArrayList<>();

        ArrayList<Integer> number_of_nodes_MAC_SDF = new ArrayList<>();
        ArrayList<Integer> number_of_backtrack_MAC_SDF = new ArrayList<>();

        ArrayList<Integer> number_of_nodes_MAC_belaz = new ArrayList<>();
        ArrayList<Integer> number_of_backtrack_MAC_belaz = new ArrayList<>();

        FileRead fileRead = new FileRead(files);
        try {
            ArrayList<Board> boards = fileRead.read();

            for (int i = 0; i < boards.size() ; i++) {
                //boards.get(i).printBoard();
                Board b = boards.get(i) ;
                System.out.println("XXXXXXXXXXXXXXXXXXXXXX"+b.fileName+"XXXXXXXXXXXXXXXXXXXXXXXXXX");

//                System.out.println(".....................BT_SDF.................................");
//                BackTrack_SDF bt = new BackTrack_SDF(b.size,b.board);
//
//                System.out.println(bt.checklatinSquare());
//                System.out.println(bt.number_of_nodes_visited);
//                System.out.println(bt.number_of_back_track);
//
//                number_of_nodes_BT_SDF.add(bt.number_of_nodes_visited);
//                number_of_backtrack_BT_SDF.add(bt.number_of_back_track);

                System.out.println(".....................fC_SDF.................................");
                forwardChecking_SDF f = new forwardChecking_SDF(b.size,b.board);

                System.out.println(f.checklatinSquare());
                System.out.println(f.number_of_nodes_visited);
                System.out.println(f.number_of_back_track);

                number_of_nodes_FC_SDF.add(f.number_of_nodes_visited);
                number_of_backtrack_FC_SDF.add(f.number_of_back_track);

                System.out.println(".....................fC_belaz.................................");

                forwardChecking_belaz fb = new forwardChecking_belaz(b.size,b.board);

                System.out.println(fb.checklatinSquare());
                System.out.println(fb.number_of_nodes_visited);
                System.out.println(fb.number_of_back_track);

                number_of_nodes_FC_belaz.add(fb.number_of_nodes_visited);
                number_of_backtrack_FC_belaz.add(fb.number_of_back_track);

                System.out.println(".....................FC_domddeg.................................");

                forwardChecking_domddeg fd = new forwardChecking_domddeg(b.size,b.board);

                System.out.println(fd.checklatinSquare());
                System.out.println(fd.number_of_nodes_visited);
                System.out.println(fd.number_of_back_track);

                number_of_nodes_FC_domddeg.add(fd.number_of_nodes_visited);
                number_of_backtrack_FC_domddeg.add(fd.number_of_back_track);

                System.out.println(".........................MAC_SDF.............................");

                MAC_SDF mac = new MAC_SDF(b.size,b.board);

                System.out.println(mac.checklatinSquare());
                System.out.println(mac.number_of_nodes_visited);
                System.out.println(mac.number_of_back_track);

                number_of_nodes_MAC_SDF.add(mac.number_of_nodes_visited);
                number_of_backtrack_MAC_SDF.add(mac.number_of_back_track);

                System.out.println("............................MAC_belaz.............................");

                MAC_belaz mac_belaz = new MAC_belaz(b.size,b.board);

                System.out.println(mac_belaz.checklatinSquare());
                System.out.println(mac_belaz.number_of_nodes_visited);
                System.out.println(mac_belaz.number_of_back_track);

                number_of_nodes_MAC_belaz.add(mac_belaz.number_of_nodes_visited);
                number_of_backtrack_MAC_belaz.add(mac_belaz.number_of_back_track);
                System.out.println(".........................................................");
            }

            System.out.println("Files                             FC_SDF                FC_Belaz              FC_domddeg     " +
                    "           MC_SDF                  MC_belaz");
//            System.out.println("Number_of_nodes  -- Number_of_backtrack|   Number_of_nodes  -- Number_of_backtrack|" +
//                    "Number_of_nodes  -- Number_of_backtrack|  Number_of_nodes  -- Number_of_backtrack| " +
//                    "Number_of_nodes  -- Number_of_backtrack|  Number_of_nodes  -- Number_of_backtrack|");
            System.out.println("                       nodes  -- Backtrack  |  nodes  -- Backtrack  |  " +
                    "nodes  -- Backtrack  |  nodes  -- Backtrack  | " +
                    "  nodes  -- Backtrack  ");

            for (int i = 0; i < files.size() ; i++) {
                //System.out.print(number_of_nodes_BT_SDF.get(i)+"  -- " + number_of_backtrack_BT_SDF.get(i) + "    |     ");
                System.out.print(files.get(i)+ "       ");
                System.out.print(number_of_nodes_FC_SDF.get(i)+"  -- " + number_of_backtrack_FC_SDF.get(i) + "      |      ");
                System.out.print(number_of_nodes_FC_belaz.get(i)+"  -- " + number_of_backtrack_FC_belaz.get(i) + "      |      ");
                System.out.print(number_of_nodes_FC_domddeg.get(i)+"  -- " + number_of_backtrack_FC_domddeg.get(i) + "      |      ");
                System.out.print(number_of_nodes_MAC_SDF.get(i)+"  -- " + number_of_backtrack_MAC_SDF.get(i) + "      |      ");
                System.out.print(number_of_nodes_MAC_belaz.get(i)+"  -- " + number_of_backtrack_MAC_belaz.get(i) + "      |      ");
                System.out.println();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }



        /*

        int b[][] =     {{0,2,0,0},
                        {0,0,0,0},
                        {2,1,0,0},
                        {0,0,0,2}};

        int bb1[][] =     {{0,0,0,0,0},
                        {0,2,0,0,0},
                        {0,0,2,0,0},
                        {0,0,0,2,0},
                         {0,0,0,0,2}
                        };


        int bb[][] =       {{1,0,3,0,5},
                            {0,3,0,0,0},
                            {3,5,0,0,0},
                            {0,0,2,0,3},
                            {0,4,0,0,0}};


        Backtrack backTrack_sdf = new Backtrack(10,sir2);

        System.out.println(backTrack_sdf.checklatinSquare());
        System.out.println(backTrack_sdf.number_of_nodes_visited);
        System.out.println(backTrack_sdf.number_of_back_track);

        System.out.println("-----------------------------------------------------------");

        */

    }
}
