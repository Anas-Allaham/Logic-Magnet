import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Level extends JFrame{


    Grid table;
    boolean is_solved=false;
    List<CellType[][]>states=new ArrayList<>();
    Level(){}

    Level(String filename) {
        table=new Grid(filename);
        is_solved=table.isSolved();
        states.add(Static_Methods.Cpy(table.cells));
    }


    public void setupUI() {
        setTitle("Grid Puzzle Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(150*table.gridSizeM, 150* table.gridSizeN);
        int gridSize=table.n;
        setLayout(new GridLayout(table.gridSizeN, table.gridSizeM));

        for (int i = 0; i < table.gridSizeN; i++) {
            for (int j = 0; j < table.gridSizeM; j++) {
                CellButton button = table.gridButtons[i][j];
                int finalI = i;
                int finalJ = j;

                button.addActionListener(e ->table.handleButtonClick(finalI, finalJ,this));
                add(button);
            }
        }
        setVisible(true);
    }
    public void print(int idx) {
        JFrame printFrame = new JFrame();
        CellType[][] table = states.get(idx);
        printFrame.setTitle("Grid Puzzle Game at move: " + idx);
        printFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        printFrame.setSize(150 * table[0].length, 150 * table.length);
        printFrame.setLayout(new GridLayout(table.length, table[0].length));

        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                CellButton button = new CellButton(table[i][j]);
                printFrame.add(button);
            }
        }

        printFrame.setVisible(true);
    }
    public boolean comp(int i,int j){
        if(i<0 || j<0 ||i>=states.size()||j>=states.size()){
            return false;
        }
        return(states.get(i).equals(states.get(j)));
    }
    public void solution_path(){
        for(CellType[][] s : states){
            Static_Methods.print(s);
            System.out.println();
        }
    }

    public boolean is_visited(CellType g[][]){
        for(CellType [][]i : states){
            if(i.equals(g))return true;
        }
        return false;
    }



    public void bfs(Grid f_grid, boolean www) {
        Grid init = new Grid(f_grid);
        Deque<Grid> dq = new ArrayDeque<>();
        dq.addLast(f_grid);
        Map<Grid, Grid> parent = new HashMap<>();
        Set<Grid> visited = new HashSet<>();
        Grid lst = null;
        parent.put(f_grid, null);
        visited.add(f_grid);
//        int t=0;
        while (!dq.isEmpty()) {
//            t++;
            Grid Grid_cur = dq.pollFirst();
            if (Grid_cur.isSolved()) {
                lst = Grid_cur;
                dq.clear();
                break;
            }

            for (int i = 0; i < table.gridSizeN; i++) {
                for (int j = 0; j < table.gridSizeM; j++) {
                    for (int ni = 0; ni < table.gridSizeN; ni++) {
                        for (int nj = 0; nj < table.gridSizeM; nj++) {
                            if (!Grid_cur.isValidMove(i, j, ni, nj)) {
                                continue;
                            }

                            Grid Grid_child = new Grid(Grid_cur);
                            Grid_child.update(i, j, ni, nj);

                            if (visited.contains(Grid_child)) {
                                continue;
                            }

                            visited.add(Grid_child);
                            parent.put(Grid_child, Grid_cur);
                            dq.addLast(Grid_child);
                        }
                    }
                }
            }
        }
        dq.clear();
//        System.out.println(t);
        List<Grid> path = new ArrayList<>();
        while (lst != null && !lst.equals(init)) {
            path.add(lst);
            lst = parent.get(lst);
        }
        path.add(init);

        Collections.reverse(path);
        int i=0;
        for (Grid grid : path) {
            System.out.println("Step : "+ ++i );
            Static_Methods.print(grid.cells);
            System.out.println();
        }
    }


}
