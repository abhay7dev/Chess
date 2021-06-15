package me.abhaydaksh.chess.pieces;

import me.abhaydaksh.chess.Location;
import me.abhaydaksh.chess.Board;
import java.util.ArrayList;

public abstract class Piece {

    protected String symbol;
    protected String color;
	protected boolean moved;

    protected Location location;

	public abstract boolean checkMove(Location l, Board b);
	public abstract Piece clone();
    
    public Piece(String symbol, String color, Location l) {
        this.symbol = symbol;
        this.color = color;
		this.location = l;
		this.moved = false;
    }

	public ArrayList<Location> getMoves(Board board) {
		ArrayList<Location> res = new ArrayList<Location>();
		Piece[][] b = board.getBoard();

		for(int i = 0; i < b.length; i++) {
			for(int j = 0; j < b[i].length; j++) {
				if(i == location.x && j == location.y) continue;
				Piece p = b[i][j];
				if((p == null || !p.getColor().equals(color)) && this.checkMove(new Location(i, j), board)) {
                    res.add(new Location(i, j));
                }
			}
        }

        return res;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public String getColor(){
        return this.color;
    }

    public Location getLocation() {
        return this.location;
    }

	public void setLocation(Location l) {
		this.location = new Location(l);
	}

    public boolean getMoved() {
        return this.moved;
    }

    public void setMoved(boolean newMove) {
        this.moved = newMove;
    }

}