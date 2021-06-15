package me.abhaydaksh.chess;

public class Location {
    public int x, y;
    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

	public Location(String s) {
		this.x = s.charAt(0) - 'a';
		this.y = s.charAt(1) - '1';
	}

	public Location(Location l) {
		this.x = l.x;
		this.y = l.y;
	}
    
    public String toString() {
        return this.x + " " + this.y;
    }

}