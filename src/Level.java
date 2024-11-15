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


    public void dfs_algo(Grid f_grid){
        Grid init = new Grid(f_grid);
        Map<Grid, Grid> parent = new HashMap<>();
        Set<Grid> visited = new HashSet<>();
        parent.put(f_grid, null);
            class InnerFunction {
                boolean find=false;
                Grid dfs(Grid Grid_cur) {
                    if(Grid_cur.isSolved()){
                        find=true;
                        Grid lst1=null;
                        lst1=Grid_cur;
                        return new Grid(lst1);
                    }
                    if(find || visited.contains(Grid_cur))return null;
//                    Static_Methods.print(Grid_cur.cells);
                    visited.add(Grid_cur);
                    Grid ret=null;
                    for (int i = 0; i < table.gridSizeN; i++) {
                        for (int j = 0; j < table.gridSizeM; j++) {
                            if(Grid_cur.gridButtons[i][j].getCellType()!=CellType.RED &&Grid_cur.gridButtons[i][j].getCellType()!=CellType.BLUE)continue;
                            for (int ni = 0; ni < table.gridSizeN; ni++) {
                                for (int nj = 0; nj < table.gridSizeM; nj++) {
                                    if (!Grid_cur.isValidMove(i, j, ni, nj)) {
                                        continue;
                                    }

                                    Grid Grid_child = new Grid(Grid_cur);
                                    Grid_child.update(i, j, ni, nj);
                                    Grid ch= dfs(Grid_child);
                                    if(ch!=null){
                                        ret=ch;
                                    }
                                    parent.put(Grid_child, Grid_cur);
                                }
                            }
                        }
                    }
                    return ret;
                }
            }
            InnerFunction tmp=new InnerFunction();
        Grid final_state=tmp.dfs(f_grid);
//        System.out.println(final_state);
//        System.out.println();
//            return;
            Grid lst=new Grid(final_state);
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

    public void bfs_algo(Grid f_grid, boolean www) {
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
                    if(Grid_cur.gridButtons[i][j].getCellType()!=CellType.RED &&Grid_cur.gridButtons[i][j].getCellType()!=CellType.BLUE)continue;
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

        void UCS(Grid initial){
            PriorityQueue<Pair>pq=new PriorityQueue<>();
            Pair p=new Pair(0,initial);
            pq.add(p);
            Map<Grid ,Grid>parent=new HashMap<>();
            Map<Grid,Integer>visited=new HashMap<>();
            visited.put(initial,0);
            parent.put(initial,null);
            while(!pq.isEmpty()){
                Pair cur=pq.poll();
                Grid Grid_cur=cur.grid;
                int weight_cur=cur.weight;
                Grid lst=new Grid(Grid_cur);
                if(Grid_cur.isSolved()){

                    List<Grid> path = new ArrayList<>();
                    while (lst != null && !lst.equals(initial)) {
                        path.add(lst);
                        lst = parent.get(lst);
                    }
                    path.add(initial);

                    Collections.reverse(path);
                    int i=0;
                    for (Grid grid : path) {
                        System.out.println("Step : "+ ++i + " Cost : "+visited.get(grid));
                        Static_Methods.print(grid.cells);
                        System.out.println();
                    }
                    break;
                }
                for (int i = 0; i < table.gridSizeN; i++) {
                    for (int j = 0; j < table.gridSizeM; j++) {
                        if(Grid_cur.gridButtons[i][j].getCellType()!=CellType.RED &&Grid_cur.gridButtons[i][j].getCellType()!=CellType.BLUE)continue;
                        for (int ni = 0; ni < table.gridSizeN; ni++) {
                            for (int nj = 0; nj < table.gridSizeM; nj++) {
                                if (!Grid_cur.isValidMove(i, j, ni, nj)) {
                                    continue;
                                }

                                Grid Grid_child = new Grid(Grid_cur);
                                Grid_child.update(i, j, ni, nj);
                                int new_weight=weight_cur+(Grid_cur.gridButtons[i][j].getCellType()==CellType.BLUE?1:2);
                                if (visited.get(Grid_child)==null || visited.get(Grid_child)>new_weight) {
                                    visited.put(Grid_child,new_weight);
                                    parent.put(Grid_child, Grid_cur);
                                    Pair pp=new Pair(new_weight,Grid_child);
                                    pq.add(pp);
                                }


                            }
                        }
                    }
                }
            }

        }


}
