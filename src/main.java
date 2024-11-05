import javax.swing.*;
import java.util.Scanner;


class PuzzleGame extends JFrame {
    Scanner console=new Scanner(System.in);
    public PuzzleGame(String idx) {
        String s="java_levels/level"+idx+".txt";
        System.out.println(s);
        Level level=new Level(s);
        level.setupUI();
        while(true) {
            System.out.println("Enter the operation query you want :\n 1)- K-th move (single player)\n 2) restart a game\n 3) stop the game\n 4) compare between two states(single player)\n 5) Print the solution path (single player)\n 6) solve by BFS \n 7) solve by DFS\n");
            int op = console.nextInt();

            if (op == 1) {
                System.out.println("Enter the move number :");
                int k = console.nextInt();
                if(k>level.states.size()){
                    System.out.println("the number you insert is larger than solution size ");
                    continue;
                }
                if(k<=0){
                    System.out.println("Enter a number large than 0");
                    continue;
                }
                level.print(k-1);
            }
            if (op == 2) {
                level.dispose();
                new PuzzleGame(idx);
                return;
            }
            if (op == 3) {
                level.dispose();
                return;
            }
            if(op==4){
                System.out.println("Enter the first state number and the second one ");

                int i=console.nextInt(), j= console.nextInt();
                System.out.println((level.comp(i,j)?true:false));
            }
            if(op==5){
                level.solution_path();
            }
            if(op==6){
                level.bfs_algo(level.table,false);
            }
            if(op==7){
                level.dfs_algo(level.table);
            }
        }
    }
    public static void main(String[] args) {
        Scanner console=new Scanner(System.in);
        while(true) {
            System.out.println("Enter the level index to play:");
            String idx = console.next();
            new PuzzleGame(idx);

        }
    }
}
