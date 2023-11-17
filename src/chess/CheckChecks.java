package chess;

public class CheckChecks {
    //Check if white or black king is in check
    public static boolean checkCheck(Square squares[][], Piece king) {
        if(diagonalCheck(squares, king)) return true;
        if(straightCheck(squares, king)) return true;
        return false;
    }

    public static boolean straightCheck(Square squares[][], Piece king) {
        int x = king.getPosition().getRow();
        int y = king.getPosition().getColumn();

        //Moving vertically
        while(--x >= 0) {
            if(!squares[x][y].getPiece().getColor().equals(king.getColor())) return true;
            else if(squares[x][y].getPiece().getColor().equals(king.getColor())) x = 0;
        }
        x = king.getPosition().getRow();
        while(++x <= 7) {
            if(!squares[x][y].getPiece().getColor().equals(king.getColor())) return true;
            else if(squares[x][y].getPiece().getColor().equals(king.getColor())) x = 7;
        }
        x = king.getPosition().getRow();

        //Moving horizontally
        while(--y >= 0) {
            if(!squares[x][y].getPiece().getColor().equals(king.getColor())) return true;
            else if(squares[x][y].getPiece().getColor().equals(king.getColor())) y = 0;
        }
        y = king.getPosition().getColumn();
        while(++y <= 7) {
            if(!squares[x][y].getPiece().getColor().equals(king.getColor())) return true;
            else if(squares[x][y].getPiece().getColor().equals(king.getColor())) y = 7;
        }
        return false;
    }
    public static boolean diagonalCheck(Square squares[][], Piece king) {
        int x = king.getPosition().getRow();
        int y = king.getPosition().getColumn();

        //Moving left up on board
        while(--x >= 0 && --y >= 0) {
            if(!squares[x][y].getPiece().getColor().equals(king.getColor())) return true;
            else if(squares[x][y].getPiece().getColor().equals(king.getColor())) x = 0;
        }
        x = king.getPosition().getRow();
        y = king.getPosition().getColumn();

        //Moving left down on board
        while(++x <= 7 && --y >= 0) {
            if(!squares[x][y].getPiece().getColor().equals(king.getColor())) return true;
            else if(squares[x][y].getPiece().getColor().equals(king.getColor())) x = 0;
        }
        x = king.getPosition().getRow();
        y = king.getPosition().getColumn();

        //Moving right up on board
        while(--x >= 0 && ++y <= 7) {
            if(!squares[x][y].getPiece().getColor().equals(king.getColor())) return true;
            else if(squares[x][y].getPiece().getColor().equals(king.getColor())) x = 0;
        }
        x = king.getPosition().getRow();
        y = king.getPosition().getColumn();

        //Moving right down on board
        while(++x <= 7 && ++y <= 7) {
            if(!squares[x][y].getPiece().getColor().equals(king.getColor())) return true;
            else if(squares[x][y].getPiece().getColor().equals(king.getColor())) x = 0;
        }
        return false;
    }
}
