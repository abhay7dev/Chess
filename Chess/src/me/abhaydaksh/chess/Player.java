package me.abhaydaksh.chess;

import me.abhaydaksh.chess.pieces.Pawn;

import java.util.Scanner;

public class Player{

    private Scanner scan;
    private String name;
	private String color;

    public Player(String name, String color) {
        scan = new Scanner(System.in);     
        this.name = name;
		this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public String getInput(Board b) {

        String toRet = "";

        boolean valid = false;

        while(!valid) {

            System.out.print("\n\n" + this.name + "\'s turn. Enter move(r to resign, p to propose draw): ");

            System.out.print(Finals.CYAN_BOLD);
            toRet = scan.nextLine().toLowerCase();
            System.out.print(Finals.RESET);
            
            if(toRet.equals("r")) {
                return toRet;
            } if(toRet.equals("p")) {
                return toRet;
            } else if(toRet.equals("0-0") || toRet.toLowerCase().equals("o-o") || toRet.toLowerCase().equals("ck")) {
                return "o-o";
            } else if(toRet.equals("0-0-0") || toRet.toLowerCase().equals("o-o-o") || toRet.toLowerCase().equals("cq")) {
                return "o-o-o";
            }

            String[] tokens = toRet.split("\\s");
            if(
                tokens.length == 2
                && !tokens[0].equals(tokens[1])
                && Finals.SPACES.contains(tokens[0]) 
                && Finals.SPACES.contains(tokens[1])
                && b.fromString(tokens[0]) != null
                && b.fromString(tokens[0]).getColor().equals(this.color)
                && (b.fromString(tokens[1]) == null 
				|| !b.fromString(tokens[1]).getColor().equals(this.color))
                && b.fromString(tokens[0]).checkMove(new Location(tokens[1]), b)
            ) {    
                valid = true;
            } else if(
            	tokens.length == 2
                && !tokens[0].equals(tokens[1])
                && Finals.SPACES.contains(tokens[0]) 
                && Finals.SPACES.contains(tokens[1])
                && b.fromString(tokens[0]) != null
                && b.fromString(tokens[0]).getColor().equals(this.color)
                && (b.fromString(tokens[1]) == null 
				|| !b.fromString(tokens[1]).getColor().equals(this.color))
				&& b.fromString(tokens[0]) instanceof Pawn
				&& ((Pawn) b.fromString(tokens[0])).enpassant(new Location(tokens[1]), b)
			) {
				b.setNull(new Location(new Location(tokens[1]).x, new Location(tokens[1]).y + (color.equals("WHITE")?-1:1)));
				valid = true;
			} else {
                Finals.CLEAR();
                if(!Menu.blind.equals("y")){
                    System.out.print(color.equals("WHITE")?b.printWhite():b.printBlack());
                }
                System.out.println(Finals.RED + "\n\nYou entered an invalid input.\nThe correct format is [space of piece you want to move] [space you want your piece to move to]\nExample: \"e2 e4\"\nIf you want to castle, then type o-o to castle kingside and o-o-o to castle queenside" + Finals.RESET);
            }                       

        }

        return toRet;

    }

}