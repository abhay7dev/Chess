package me.abhaydaksh.chess.pieces;

import me.abhaydaksh.chess.Location;
import me.abhaydaksh.chess.Board;

public class Pawn extends Piece {

	public boolean jumped;

    public Pawn(String symbol, String color, Location l) {
        super(symbol, color, l);
    }

	public Piece clone() {
		return new Pawn(symbol, color, new Location(location));
	}

    public boolean checkMove(Location to, Board b) {

		int x = location.x, y = location.y;


		if(color.equals("BLACK")) {
			y *= -1;
			to.y *= -1;
		}

		if(
            x == to.x 
            && (
                to.y - y != 1 
                && to.y - y != 2 
                || to.y - y == 2 
                && (y != 1 && y != -6 
				|| b.fromLocation(new Location(to.x, Math.abs(y) + (y / location.y))) != null
				)
                || b.fromLocation(new Location(to.x, Math.abs(to.y))) != null
                )
		    || x != to.x 
            && (Math.abs(x - to.x) != 1 
            || to.y - y != 1 
            || b.fromLocation(new Location(to.x, Math.abs(to.y))) == null)
        ) {
			return false;
        }
        
		to.y = Math.abs(to.y);
		
		return true;
    }

	public boolean enpassant(Location to, Board b) {
		int x = location.x, y = location.y;

		if(color.equals("BLACK")) {
			y *= -1;
			to.y *= -1;
		}

		if(
			Math.abs(x-to.x) != 1
			|| to.y - y != 1
			|| b.fromLocation(new Location(to.x, Math.abs(to.y))) != null
			|| !(b.fromLocation(new Location(to.x, Math.abs(to.y - 1))) instanceof Pawn)
			|| !((Pawn) b.fromLocation(new Location(to.x, Math.abs(to.y - 1)))).jumped
		) {
			return false;
		}

		return true;
	}
}