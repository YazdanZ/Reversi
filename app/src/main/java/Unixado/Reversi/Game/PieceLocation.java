package Unixado.Reversi.Game;

public class PieceLocation {

    private int x;
    private int y;

    public PieceLocation(int x, int y) throws IllegalArgumentException{
        if (x > 7 || x < 0 || y > 7 || y < 0)
            throw new IllegalArgumentException ("Locations on the board range from [0][0] to [7][7]");

        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setX( int x ) {
        if (x > 0 && x < 7) {
            this.x = x;
            return;
        }
        
    }
}
