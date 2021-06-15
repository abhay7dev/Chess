package me.abhaydaksh.chess.pieces;

import me.abhaydaksh.chess.Location;
import me.abhaydaksh.chess.Board;

public class Rook extends Piece {


    public Rook(String symbol, String color, Location l) {
        super(symbol, color, l);
    }

	public Piece clone() {
		return new Rook(symbol, color, new Location(location));
	}

    public boolean checkMove(Location to, Board b) {
        
        int x = this.location.x, y = this.location.y;

        if(x != to.x && y != to.y) {
            return false;
        }
        
        if(x != to.x) {
            
            // Move along the x axis
			if(x < to.x) {
				for(int i = x + 1; i < to.x; i++) {
                    if(b.fromLocation(new Location(i, y)) != null) {
						
                        return false;
                    }
                }
            } else {
                for(int i = x - 1; i > to.x; i--) {
                    if(b.fromLocation(new Location(i, y)) != null) {
						
                        return false;
                    }
                }
            }
        } else {
            
            // Move along the y axis
            if(y < to.y) {
				for(int i = y + 1; i < to.y; i++) {
                    if(b.fromLocation(new Location(x, i)) != null) {
						
                        return false;
                    }
                }
            } else {
                for(int i = y - 1; i > to.y; i--) {
                    if(b.fromLocation(new Location(x, i)) != null) {
                        return false;
                    }
                }
            }

        }
        
		return true;
    }

}