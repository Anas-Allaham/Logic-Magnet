public class Static_Methods {
    public static CellType translateCellType(char type) {
        return switch (type) {
            case 'r' -> CellType.RED;
            case 'b' -> CellType.BLUE;
            case 'm' -> CellType.MAGNET;
            case 'x' -> CellType.Block;
            default -> CellType.EMPTY;
        };
    }
    public static void print(CellType[][]s){
        for(CellType[] i : s){
            for(CellType j : i){
                System.out.print(j + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static CellType[][] Cpy(CellType[][] original) {
        CellType[][] copy = new CellType[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }
    public static CellButton[][] Cpy(CellButton[][] original) {
        CellButton[][] copy = new CellButton[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                copy[i][j] = original[i][j];
            }
        }
        return copy;
    }
}
