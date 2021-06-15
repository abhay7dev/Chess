package me.abhaydaksh.chess.pieces;

import me.abhaydaksh.chess.Location;
import me.abhaydaksh.chess.Board;

public class Knight extends Piece {
	public Knight(String symbol, String color, Location location) {
		super(symbol, color, location);
	}

	public Piece clone() {
		return new Knight(symbol, color, new Location(location));
	}

    public boolean checkMove(Location to, Board b) {

		int x = location.x, y = location.y;
		// location=(1, 0) to=(6, 2)
		if(x == to.x || y == to.y || Math.abs(x-to.x) + Math.abs(y-to.y) != 3)
			return false;

		return true;
    }
}