import javax.swing.*;
import java.awt.*;

class CellButton extends JButton {
    CellType cellType;
    public CellButton(CellType cellType) {
        this.cellType = cellType;
        setCellColor();
    }

    public void setCellType(CellType cellType) {
        this.cellType = cellType;
        setCellColor();
    }

    private void setCellColor() {
        switch (cellType) {
            case RED -> setBackground(Color.RED);
            case BLUE -> setBackground(Color.BLUE);
            case MAGNET -> setBackground(Color.darkGray);
            case EMPTY -> setBackground(Color.LIGHT_GRAY);
            case Block -> setBackground(Color.BLACK);
        }
    }

    public CellType getCellType() {
        return cellType;
    }
}