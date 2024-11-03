import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Grid extends JFrame {
    int n,m;
//    ArrayList<ArrayList<CellType>>
    public int gridSizeN = 5;
    public int gridSizeM = 5;
    public int step=1000;
    public CellButton[][] gridButtons;
    public CellType[][] cells;
    public List<Point> Circles = new ArrayList<>();
    public Point selected = null;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Grid grid = (Grid) obj;
        for (int i = 0; i < gridSizeN; i++) {
            for (int j = 0; j < gridSizeM; j++) {
                if (this.cells[i][j] != grid.cells[i][j]) return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 17;
        for (int i = 0; i < gridSizeN; i++) {
            for (int j = 0; j < gridSizeM; j++) {
                result = 31 * result + (cells[i][j] != null ? cells[i][j].hashCode() : 0);
            }
        }
        return result;
    }


    public Grid(Grid original) {
        this.n = original.n;
        this.m = original.m;
        this.gridSizeN = original.gridSizeN;
        this.gridSizeM = original.gridSizeM;
        this.step = original.step;

        this.gridButtons = new CellButton[gridSizeN][gridSizeM];
        for (int i = 0; i < gridSizeN; i++) {
            for (int j = 0; j < gridSizeM; j++) {
                CellButton originalButton = original.gridButtons[i][j];
                this.gridButtons[i][j] = new CellButton(originalButton.getCellType());
            }
        }

        this.cells = new CellType[gridSizeN][gridSizeM];
        for (int i = 0; i < gridSizeN; i++) {
            for (int j = 0; j < gridSizeM; j++) {
                this.cells[i][j] = original.cells[i][j];
            }
        }

        for (Point p : original.Circles) {
            this.Circles.add(new Point(p));
        }
    }


    Grid(String filename){
        try (Scanner scanner = new Scanner(new File(filename))) {
            int temp;
            temp=scanner.nextInt();
            gridSizeN = scanner.nextInt();
            gridSizeM = scanner.nextInt();
            int targetCount = scanner.nextInt();
            gridButtons = new CellButton[gridSizeN][gridSizeM];
            cells = new CellType[gridSizeN][gridSizeM];

            for (int i = 0; i < gridSizeN; i++) {
                for (int j = 0; j < gridSizeM; j++) {
                    char cellType = scanner.next().charAt(0);
                    gridButtons[i][j] = new CellButton( Static_Methods.translateCellType(cellType));
                    cells[i][j]=Static_Methods.translateCellType(cellType);
                }
            }

            for (int i = 0; i < targetCount; i++) {
                int x = scanner.nextInt();
                int y = scanner.nextInt();
                Circles.add(new Point(x, y));
                Border specialBorder = BorderFactory.createLineBorder(Color.GREEN, 3);
                gridButtons[x][y].setBorder(specialBorder);
            }
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "PuzzleGame file not found.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
        }
    }
    Grid(int gridSizeN,int gridSizeM,CellButton[][]gridButtons,CellType[][] cells,List<Point> Circles ){
        this.gridSizeM=gridSizeM;
        this.gridSizeN=gridSizeN;
        this.cells=Static_Methods.Cpy(cells);
        this.Circles=Circles;
        this.gridButtons=Static_Methods.Cpy(gridButtons);
    }
    public void update(int x1, int y1, int x2, int y2) {
        CellType type = gridButtons[x1][y1].getCellType();

        swapCells(x1, y1, x2, y2);

        if (type == CellType.RED) {
            for (int i = x2 - 1; i >= Math.max(1, x2 - step); i--) {
                if (gridButtons[i][y2].getCellType() == CellType.EMPTY ) {
                    swapCells(i, y2, i - 1, y2);
                }
            }
            for (int i = x2 + 1; i <Math.min(gridSizeN - 1, x2 + step+1); i++) {
                if (gridButtons[i][y2].getCellType() == CellType.EMPTY) {
                    swapCells(i, y2, i + 1, y2);
                }
            }
            for (int j = y2 - 1; j >= Math.max(1, y2 - step); j--) {
                if (gridButtons[x2][j].getCellType() == CellType.EMPTY) {
                    swapCells(x2, j, x2, j - 1);
                }
            }
            for (int j = y2 + 1; j < Math.min(gridSizeM - 1, y2 + step+1); j++) {
                if (gridButtons[x2][j].getCellType() == CellType.EMPTY) {
                    swapCells(x2, j, x2, j + 1);
                }
            }
        } else if (type == CellType.BLUE) {
            step++;
            for (int i = Math.max(1, x2 - step); i<=x2-1 ; i++) {
                if (gridButtons[i-1][y2].getCellType() == CellType.EMPTY) {
                    swapCells(i, y2, i - 1, y2);
                }
            }
            for (int j =Math.min(gridSizeN-2,x2+step); j >= x2+1; j--) {
                if (gridButtons[j+1][y2].getCellType() == CellType.EMPTY) {
                    swapCells(j+1, y2, j, y2);
                }
            }
            for (int j =Math.max(y2-step,1); j <= y2-1; j++) {
                if (gridButtons[x2][j-1].getCellType() == CellType.EMPTY) {
                    swapCells(x2, j-1, x2, j);
                }
            }
            for (int j =Math.min(gridSizeM-2,y2+step); j >= y2+1; j--) {
                if (gridButtons[x2][j+1].getCellType() == CellType.EMPTY) {
                    swapCells(x2, j+1, x2, j);
                }
            }
            step--;
        }
    }

    private void swapCells(int x1, int y1, int x2, int y2) {
        if(cells[x2][y2]==CellType.Block || cells[x1][y1]==CellType.Block)return;
        CellType tempType = gridButtons[x1][y1].getCellType();
        gridButtons[x1][y1].setCellType(gridButtons[x2][y2].getCellType());
        gridButtons[x2][y2].setCellType(tempType);
        tempType = cells[x1][y1];
        cells[x1][y1]=cells[x2][y2];
        cells[x2][y2]=(tempType);
    }
    public boolean isSolved() {
        for (Point target : Circles) {
            if (gridButtons[target.x][target.y].getCellType() == CellType.EMPTY) {
                return false;
            }
        }
        return true;
    }



    public void handleButtonClick(int x, int y,Level level) {
        CellButton button = gridButtons[x][y];


        if (selected == null && button.getCellType() != CellType.EMPTY && button.getCellType() != CellType.MAGNET && button.getCellType()!=CellType.Block) {
            selected = new Point(x, y);
        } else if (selected != null) {
            int selX = selected.x;
            int selY = selected.y;

            if (isValidMove(selX, selY, x, y)) {
                update(selX, selY, x, y);
                level.states.add(Static_Methods.Cpy(cells));

                selected = null;
                if (isSolved()) {

                    JOptionPane.showMessageDialog(this, "Congratulations! Puzzle Solved!");
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Move! Try again.");
                selected = null;
            }
            level.is_solved=isSolved();
            if(level.is_solved){
                level.dispose();
                return;
            }
        }
    }

    public boolean is_valid(int x, int y)
    {
        return x >= 0 && y >= 0 && x < gridSizeN && y < gridSizeM;
    }

    public boolean isValidMove(int x1, int y1, int x2, int y2) {
        return is_valid(x2,y2)&& is_valid(x1,y1)&& gridButtons[x2][y2].getCellType() == CellType.EMPTY &&(gridButtons[x1][y1].getCellType() == CellType.BLUE ||gridButtons[x1][y1].getCellType() == CellType.RED) ;
    }

}