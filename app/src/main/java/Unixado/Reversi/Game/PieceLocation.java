package Unixado.Reversi.Game;

public final class PieceLocation {

    private final int x;
    private final int y;
    private static final String EXCEPTION_MSG = "Locations on the board range from [0][0] to [7][7]";

    public PieceLocation(int x, int y) {

        // Locations on the board range from [0][0] to [7][7]
        if(x >= 0 && x <= 7 && y >= 0 && y <= 7){
            this.x = x;
            this.y = y;
            return;
        }
        
        throw new IllegalArgumentException (EXCEPTION_MSG);
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

}
