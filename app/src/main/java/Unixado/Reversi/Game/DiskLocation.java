package Unixado.Reversi.Game;

public final class DiskLocation {

    private final int row;
    private final int column;
    private static final String EXCEPTION_MSG = "Locations on the board range from [0][0] to [7][7]";

    public DiskLocation ( int row, int column) {

        // Locations on the board range from [0][0] to [7][7]
        if(row >= 0 && row <= 7 && column >= 0 && column <= 7){
            this.row = row;
            this.column = column;
            return;
        }
        
        throw new IllegalArgumentException (EXCEPTION_MSG);
    }

    public int getRow (){
        return this.row;
    }

    public int getColumn (){
        return this.column;
    }

}
