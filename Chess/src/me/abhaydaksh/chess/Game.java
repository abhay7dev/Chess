package me.abhaydaksh.chess;

import me.abhaydaksh.chess.pieces.*;
import java.util.Scanner;

public class Game {

    private Board b;
    private Player[] players;
    int currentPlayer = 0;
    private Scanner scan = new Scanner(System.in);

    public Game() {
        this("WHITE", "BLACK");
    }

    public Game(String p1, String p2) {
        b = new Board();
        Finals.CLEAR();
		players = new Player[2];
		this.players[0] = new Player(p1, "WHITE");
		this.players[1] = new Player(p2, "BLACK");
        play();
    }

    private void play() {
        boolean inGame = true;
		boolean inCheck = false;
		boolean illegal = false;
        while(inGame) {
			Finals.CLEAR();

		    if(!Menu.blind.equals("y")) {
                System.out.print(b.print(currentPlayer));
            }
			
			inCheck = b.check(currentPlayer);
            if(inCheck && b.checkMate(currentPlayer)){
                win("\n\n" + players[currentPlayer] + " has been checkmated. "); 
                Finals.CLEAR();
                return; 
            } else if(!inCheck && b.checkMate(currentPlayer)) {
                draw("\n\n" + players[currentPlayer] + " has been stalemated. "); 
                Finals.CLEAR();
                return; 
            } else if(b.onlyKings()) {
				draw("\n\nOnly kings are remaining. The game is over"); 
                Finals.CLEAR();
                return; 
			} else if(b.nonInterestingMoves == 100) {
				draw("\n\nThe 50 move rule is applied. Both players did not capture a piece or move a pawn for 50 consecutive moves. Therefore, the game is over."); 
                Finals.CLEAR();
                return; 
			}
			
			if(inCheck) System.out.print(Finals.RED + "\n\nYou are in check");
			else System.out.print("\n");
			if(illegal) {
                System.out.print(Finals.RED + "\nIllegal move");
                illegal = false;
            }
			System.out.print(Finals.RESET);

            String move = players[currentPlayer].getInput(b);  
			
            if(move.replaceAll("\\s","").equals("r")) {
                win("\n\n" + players[currentPlayer] + " has resigned. "); 
                return;     
            } else if(move.replaceAll("\\s","").equals("p")) {
                System.out.print(players[currentPlayer].getName() + " proposes a draw. Do you accept " + players[currentPlayer ^ 1].getName() + " (y, n)?: ");
				
				String response = scan.nextLine().toLowerCase();

				while(!response.equals("y") && !response.equals("n")) {
					System.out.print(players[currentPlayer].getName() + " proposes a draw. Do you accept " + players[currentPlayer ^ 1].getName() + " (y, n)?: ");
				
					response = scan.nextLine().toLowerCase();
				}

				if(response.equals("y")) {
					draw("\n\nBoth players agree to draw. Game is over.\n"); 
					Finals.CLEAR();
					return; 
				} else if(response.equals("n")) {
					System.out.print(players[currentPlayer ^ 1].getName() + " does not accept the draw. Game will continue.");
					try {
						System.out.println("Press enter to continue"); 
						System.in.read();
					} catch(Exception e) {
						System.out.println("Error: " + e);
						System.exit(1);
					}
					continue;
				}
            } else if(move.equals("o-o")) {
                if(b.checkCastleKing(currentPlayer)) {
                    b.castleKing(currentPlayer);
                    switchPlayers();
                    continue;
                } else {
                    illegal = true;
                    continue;
                }
            } else if(move.equals("o-o-o")) {
                if(b.checkCastleQueen(currentPlayer)) {
                    b.castleQueen(currentPlayer);
                    switchPlayers(); 
                    continue;
                } else {
                    illegal = true;
                    continue;
                }
            }

            //System.out.println(move + "\t\t" + move.substring(0, 2) + " : " + move.substring(3));

            Piece p = b.move(new Location(move.substring(0, 2)), new Location(move.substring(3)));
            
			if(b.check(currentPlayer)) {
				b.move(new Location(move.substring(3)), new Location(move.substring(0, 2)));
				illegal = true;
				if(p != null) b.setPiece(new Location(move.substring(3)), p);
				continue;
			} else illegal = false;

			if(currentPlayer == 0) {
				for(int i = 0; i < b.getBoard().length; i++) {
					Location l = new Location(i, b.getBoard().length - 1);
					if(b.fromLocation(l) instanceof Pawn && b.fromLocation(l).getColor().equals(currentPlayer == 0?"WHITE":"BLACK")) {

                        String output = "\n\nYour pawn has reached the end of the board and it now needs to be promoted.\nType Q for Queen\nType K for Knight\nType R for Rook\nType B for bishop\n\n";

                        System.out.print(output);
                        String choice = "";
						do {
                            System.out.print("Enter choice: ");
                            choice = scan.nextLine().toLowerCase();
						} while(!choice.equals("q") && !choice.equals("r") && !choice.equals("b") && !choice.equals("k"));

                        switch(choice) {
                            case "q":     
								b.replace(l, new Queen("♛", "WHITE", l));
                            	break;
                            case "r":
							    b.replace(l, new Rook("♜", "WHITE", l));
                            	break;
                            case "b":
							    b.replace(l, new Bishop("♝", "WHITE", l));
                            	break;
                            case "k":
								b.replace(l, new Knight("♞", "WHITE", l));
                                break;
                            default:
                                System.out.println("Error getting input");
                                System.exit(0);
                        }                        
					}
				}
			} else {
                for(int i = 0; i < b.getBoard().length; i++) {
					Location l = new Location(i, 0);
					
                    if(b.fromLocation(l) instanceof Pawn && b.fromLocation(l).getColor().equals(currentPlayer == 0?"WHITE":"BLACK")) {

                        String output = "\n\nYour pawn has reached the end of the board and it now needs to be promoted.\nType Q for Queen\nType K for Knight\nType R for Rook\nType B for bishop\n\n";

                        System.out.print(output);
                        String choice = "";
						do {
                            System.out.print("Enter choice: ");
                            choice = scan.nextLine().toLowerCase();
						} while(!choice.equals("q") && !choice.equals("r") && !choice.equals("b") && !choice.equals("k"));

                        switch(choice) {
                            case "q":     
								b.replace(l, new Queen("♕", "BLACK", l));
                            	break;
                            case "r":
							    b.replace(l, new Rook("♖", "BLACK", l));
                            	break;
                            case "b":
							    b.replace(l, new Bishop("♗", "BLACK", l));
                            	break;
                            case "k":
								b.replace(l, new Knight("♘", "BLACK", l));
                                break;
                            default:
                                System.out.println("Error getting input");
                                System.exit(0);
                        }                        
					}
				}
            }
		
            switchPlayers();
        }
    }

    private void switchPlayers(){
		currentPlayer ^= 1;
    }

    public void win(String loser) {
        System.out.print(loser);
        switchPlayers();

        System.out.print(players[currentPlayer] + " wins!\n\n");
        
        try {
            System.out.println("Press enter to continue"); 
            System.in.read();
            return;
        } catch(Exception e) {
            System.out.println("Error: " + e);
            System.exit(1);
        }
    }

    public void draw(String loser) {
        System.out.print(loser);

        System.out.print(" The game has reached a draw\n");
        
        try {
            System.out.println("Press enter to continue"); 
            System.in.read();
            return;
        } catch(Exception e) {
            System.out.println("Error: " + e);
            System.exit(1);
        }
    }

}