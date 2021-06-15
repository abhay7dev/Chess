package me.abhaydaksh.chess.pieces;

import me.abhaydaksh.chess.Location;
import me.abhaydaksh.chess.Board;

public class Bishop extends Piece {
	public Bishop(String symbol, String color, Location location) {
		super(symbol, color, location);
	}

	public Piece clone() {
		return new Bishop(symbol, color, new Location(location));
	}

    public boolean checkMove(Location to, Board b) {

		int x = location.x, y = location.y;

		if(x-y != to.x-to.y && x+y != to.x+to.y) return false;

		if(x-y == to.x-to.y) {
			if(x < to.x) {
				for(int i = x + 1; i < to.x; i++)
					if(b.fromLocation(new Location(i, y + i - x)) != null) 
						return false;
			} else {
				for(int i = x - 1; i > to.x; i--)
					if(b.fromLocation(new Location(i, y + i - x)) != null)
						return false;
			}
		} else {
			if(x < to.x) {
				for(int i = x + 1; i < to.x; i++)
					if(b.fromLocation(new Location(i, y - i + x)) != null) 
						return false;
			} else {
				for(int i = x - 1; i > to.x; i--)
					if(b.fromLocation(new Location(i, y - i + x)) != null) 
						return false;
			}
		}

		return true;
    }
}