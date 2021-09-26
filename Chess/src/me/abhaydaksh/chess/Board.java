package me.abhaydaksh.chess;

import me.abhaydaksh.chess.pieces.*;

public class Board {

    private Piece[][] board;
	public int nonInterestingMoves = 0;

    public Board() {
        board = new Piece[Finals.BOARD_WIDTH][Finals.BOARD_HEIGHT];
        setup();
    }

    public Board(Piece[][] newBoard){
        this();
		for(int i = 0; i < newBoard.length; i++)
			for(int j = 0; j < newBoard[0].length; j++)
				board[i][j] = newBoard[i][j];
    }

    private void setup() {
        
        // Set up pawns

        for(int i = 0; i < Finals.BOARD_WIDTH; i++) {
			board[i][1] = new Pawn("♟", "WHITE", new Location(i, 1));
			board[i][6] = new Pawn("♙", "BLACK", new Location(i, 6));
		}

        // Set up rooks

		board[0][0] = new Rook("♜", "WHITE", new Location(0, 0));
		board[7][0] = new Rook("♜", "WHITE", new Location(7, 0));

        board[0][7] = new Rook("♖", "BLACK", new Location(0, 7));
		board[7][7] = new Rook("♖", "BLACK", new Location(7, 7));

        // Set up knights

        board[1][0] = new Knight("♞", "WHITE", new Location(1, 0));
		board[6][0] = new Knight("♞", "WHITE", new Location(6, 0));

        board[1][7] = new Knight("♘", "BLACK", new Location(1, 7));
		board[6][7] = new Knight("♘", "BLACK", new Location(6, 7));

        // Set up bishops

        board[2][0] = new Bishop("♝", "WHITE", new Location(2, 0));
		board[5][0] = new Bishop("♝", "WHITE", new Location(5, 0));

        board[2][7] = new Bishop("♗", "BLACK", new Location(2, 7));
		board[5][7] = new Bishop("♗", "BLACK", new Location(5, 7));

        //Set up kings and queens

        board[3][0] = new Queen("♛", "WHITE", new Location(3, 0));
		board[3][7] = new Queen("♕", "BLACK", new Location(3, 7));

        board[4][0] = new King("♚", "WHITE", new Location(4, 0));
		board[4][7] = new King("♔", "BLACK", new Location(4, 7));
        
    }

    public Piece[][] getBoard() {
        return board;
    }

	public String print(int currentPlayer) {
		if(currentPlayer == 0) return printWhite();
		return printBlack();
	}
    
    public void printRaw() {
        for(Piece[] str: board){
            for(Piece strs: str){
                if(strs != null) {
                	System.out.print(strs.getSymbol() + " ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
    }
    
    public String printWhite() {
		String s = "";
        for(int i = board.length - 1; i >= 0; i--) {
            s += i + 1 + " ";
            for(int j = 0; j < board[0].length; j++) {
                s += ((i + j) % 2 == 0 ?Finals.BLACK_BACKGROUND:Finals.BLACK_BACKGROUND_BRIGHT);
                s += (board[j][i]==null?"  ":board[j][i].getSymbol()+" ");
                s += Finals.RESET;
            }
            s += "\n";
        }
        s += "  ";
        for(char i='a';i<='h';i++)
            s+=i+" ";
		return s;
    }

	public String printBlack() {
		String s = "";
        for(int i = 0; i < board.length; i++) {
            s += i + 1 + " ";
            for(int j = board[0].length - 1; j >= 0; j--) {
                s += ((i + j) % 2 == 0 ?Finals.BLACK_BACKGROUND:Finals.BLACK_BACKGROUND_BRIGHT);
                s += (board[j][i]==null?"  ":board[j][i].getSymbol()+" ");
                s += Finals.RESET;
            }
            s += "\n";
        }
        s += "  ";
        for(char i = 'h'; i >= 'a'; i--)
            s+=i+" ";
		return s;
	}

    public Piece fromLocation(Location l) {
		return board[l.x][l.y];
	}

	public Piece fromString(String s) {
		return this.fromLocation(new Location(s));
	}

	public Piece move(Location from, Location to) {

		nonInterestingMoves++;

		if(fromLocation(from) instanceof Pawn || fromLocation(to) != null) { 
			nonInterestingMoves = 0;
		}

		fromLocation(from).setMoved(true);

		if(fromLocation(from) instanceof Pawn) {
			((Pawn) fromLocation(from)).jumped = (Math.abs(from.y-to.y) == 2);
		}

		Piece toRet = null;

		if(board[to.x][to.y] != null) toRet = board[to.x][to.y];

        board[to.x][to.y] = fromLocation(from);
		fromLocation(to).setLocation(to);
		board[from.x][from.y] = null;
		return toRet;
	}

	private void move(Location from, Location to, boolean changeMoved) {
		if(changeMoved) fromLocation(from).setMoved(true);
        board[to.x][to.y] = fromLocation(from);
		fromLocation(to).setLocation(to);
		board[from.x][from.y] = null;
	}

	public boolean check(int currentPlayer) {

		Location l = findKing(currentPlayer);
		int x = l.x, y = l.y;
		String color = (currentPlayer == 0?"WHITE":"BLACK");

		// Rook and Queen

		// Horizontal right
		for(int i = x + 1; i < board[0].length; i++) {
			if(board[i][y] != null) {
				if((board[i][y] instanceof Rook || board[i][y] instanceof Queen) && !board[i][y].getColor().equals(color))
					return true;
				
				break;
			}
		}

		// Horizontal left
		for(int i = x - 1; i >= 0; i--) {
			if(board[i][y] != null) {
				if((board[i][y] instanceof Rook || board[i][y] instanceof Queen) && !board[i][y].getColor().equals(color))
					return true;
				
				break;
			}
		}

		// Vertical up
		for(int i = y + 1; i < board.length; i++) {
			if(board[x][i] != null) {
				if((board[x][i] instanceof Rook || board[x][i] instanceof Queen) && !board[x][i].getColor().equals(color))
					return true;
				
				break;
			}
		}

		// Vertical down
		for(int i = y - 1; i >= 0; i--) {
			if(board[x][i] != null) {
				if((board[x][i] instanceof Rook || board[x][i] instanceof Queen) && !board[x][i].getColor().equals(color))
					return true;
				
				break;
			}
		}

		// Bishop and Queen

		// Diagonal top right
		for(int i = x + 1; i < board.length; i++) {
			if(y + i - x == board[0].length) break;
			if(board[i][y + i - x] != null) {
				if((board[i][y + i - x] instanceof Bishop || board[i][y + i - x] instanceof Queen) && !board[i][y + i - x].getColor().equals(color))
					return true;

				break;
			}
		}

		// Diagonal bottom right
		for(int i = x + 1; i < board.length; i++) {
			if(y - i + x == -1) break;
			if(board[i][y - i + x] != null) {
				if((board[i][y - i + x] instanceof Bishop || board[i][y - i + x] instanceof Queen) && !board[i][y - i + x].getColor().equals(color))
					return true;
				break;
			}
		}

		// Diagonal top left
		for(int i = x - 1; i >= 0; i--) {
			if(y - i + x == board.length) break;
			if(board[i][y - i + x] != null) {
				if((board[i][y - i + x] instanceof Bishop || board[i][y - i + x] instanceof Queen) && !board[i][y - i + x].getColor().equals(color))
					return true;

				break;
			}
		}

		// Diagonal bottom left
		for(int i = x - 1; i >= 0; i--) {
			if(y + i - x == -1) break;
			if(board[i][y + i - x] != null) {
				if((board[i][y + i - x] instanceof Bishop || board[i][y + i - x] instanceof Queen) && !board[i][y + i - x].getColor().equals(color))
					return true;

				break;
			}
		}

		// Knight

		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				if(i != x && j != y && Math.abs(i - x) + Math.abs(j - y) == 3) {
					if(board[i][j] instanceof Knight && !board[i][j].getColor().equals(color))
						return true;
				}
			}
		}

		// Pawn

		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j] instanceof Pawn && Math.abs(x-i) == 1 && (color.equals("WHITE") && j - y == 1 || color.equals("BLACK") && y - j == 1) && !board[i][j].getColor().equals(color))
					return true;
			}
		}

		// King

		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j] instanceof King && Math.abs(x-i) <= 1 && Math.abs(y-j) <= 1 && !board[i][j].getColor().equals(color))
					return true;
			}
		}        

        return false;
	}

    public boolean checkMate(int currentPlayer) {

		String color = (currentPlayer == 0?"WHITE":"BLACK");

        for(Piece[] arr: board) {
            for(Piece p: arr) {
                if(p != null && p.getColor().equals(color)) {
                    for(Location l: p.getMoves(this)) {
                        Location temp = new Location(p.getLocation());
						Piece piece = null;
						if(fromLocation(l) != null) piece = fromLocation(l).clone();
                        move(p.getLocation(), l, false);
						// this should be move(p.getLocation(), l, false) , lets try
						
                        if(!check(currentPlayer))  {
							move(l, temp, false);// nvm lets just try this 
							board[l.x][l.y] = piece;
							return false;
                        }
                        
						move(l, temp, false);
						board[l.x][l.y] = piece;
                    }
                }
            }
        }

        return true;
    }

    private Location findKing(int currentPlayer) {
        String color = (currentPlayer == 0?"WHITE":"BLACK");

        for(Piece[] pArr: board) {
            for(Piece p: pArr) {
                if(p instanceof King && p.getColor().equals(color)) {
                    return p.getLocation();
                }
            }
        }

        return null;

    }

	public boolean checkCastleKing(int currentPlayer) {

        String color = currentPlayer == 0?"WHITE":"BLACK";

		if(currentPlayer == 0) {
            
            if(
                fromLocation(new Location("h1")) instanceof Rook 
                && fromLocation(new Location("e1")) instanceof King
            ) {
               
				

                if(

                    fromLocation(new Location("h1")).getMoved()
                    || !fromLocation(new Location("h1")).getColor().equals(color) 
                    || fromLocation(new Location("e1")).getMoved() 
                    || !fromLocation(new Location("e1")).getColor().equals(color)
                    
                    || check(currentPlayer)

                    || fromLocation(new Location("f1")) != null
					|| fromLocation(new Location("g1")) != null
				) {
                    return false;
                }

				move(new Location("e1"), new Location("f1"), false);
				
                if(check(currentPlayer)) {
                    move(new Location("f1"), new Location("e1"), false);
                    return false;
                }

				move(new Location("f1"), new Location("g1"), false);

				if(check(currentPlayer)) {
					move(new Location("g1"), new Location("e1"), false);
					return false;
				}
                
                move(new Location("g1"), new Location("e1"), false);
                
            } else {
                return false;
            }

        } else if(currentPlayer == 1) {

            if(
                fromLocation(new Location("h8")) instanceof Rook 
                && fromLocation(new Location("e8")) instanceof King
            ) {
               
				

                if(

                    fromLocation(new Location("h8")).getMoved()
                    || !fromLocation(new Location("h8")).getColor().equals(color) 
                    || fromLocation(new Location("e8")).getMoved() 
                    || !fromLocation(new Location("e8")).getColor().equals(color)
                    
                    || check(currentPlayer)

                    || fromLocation(new Location("f8")) != null
					|| fromLocation(new Location("g8")) != null
				) {
                    return false;
                }

				move(new Location("e8"), new Location("f8"), false);

				if(check(currentPlayer)) {
					move(new Location("f8"), new Location("e8"), false);
					return false;
				}
                
				move(new Location("f8"), new Location("g8"), false);

				if(check(currentPlayer)) {
					move(new Location("g8"), new Location("e8"), false);
					return false;
				}

				move(new Location("g8"), new Location("e8"), false);
                
            } else {
                return false;
            }
        }

        return true;
	}
    
    public boolean checkCastleQueen(int currentPlayer) {
        String color = currentPlayer == 0?"WHITE":"BLACK";

		if(currentPlayer == 0) {
            
            if(
                fromLocation(new Location("a1")) instanceof Rook 
                && fromLocation(new Location("e1")) instanceof King
            ) {

                if(

                    fromLocation(new Location("a1")).getMoved()
                    || !fromLocation(new Location("a1")).getColor().equals(color) 
                    || fromLocation(new Location("e1")).getMoved() 
                    || !fromLocation(new Location("e1")).getColor().equals(color)
                    
                    || check(currentPlayer)

                    || fromLocation(new Location("b1")) != null
					|| fromLocation(new Location("c1")) != null
                    || fromLocation(new Location("d1")) != null
				) {
                    return false;
                }

				move(new Location("e1"), new Location("d1"), false);
				
                if(check(currentPlayer)) {
                    move(new Location("d1"), new Location("e1"), false);
                    return false;
                }

				move(new Location("d1"), new Location("c1"), false);

				if(check(currentPlayer)) {
					move(new Location("c1"), new Location("e1"), false);
					return false;
				}

				move(new Location("c1"), new Location("b1"), false);

				if(check(currentPlayer)) {
					move(new Location("b1"), new Location("e1"), false);
					return false;
				}
                
                move(new Location("b1"), new Location("e1"), false);
                
            } else {
                return false;
            }

        } else if(currentPlayer == 1) {

            if(
                fromLocation(new Location("a8")) instanceof Rook 
                && fromLocation(new Location("e8")) instanceof King
            ) {
               
				

                if(

                    fromLocation(new Location("a8")).getMoved()
                    || !fromLocation(new Location("a8")).getColor().equals(color) 
                    || fromLocation(new Location("e8")).getMoved() 
                    || !fromLocation(new Location("e8")).getColor().equals(color)
                    
                    || check(currentPlayer)

                    || fromLocation(new Location("b8")) != null
					|| fromLocation(new Location("c8")) != null
					|| fromLocation(new Location("d8")) != null
				) {
                    return false;
                }

                move(new Location("e8"), new Location("d8"), false);
				
                if(check(currentPlayer)) {
                    move(new Location("d8"), new Location("e8"), false);
                    return false;
                }

				move(new Location("d8"), new Location("c8"), false);

				if(check(currentPlayer)) {
					move(new Location("c8"), new Location("e8"), false);
					return false;
				}

				move(new Location("c8"), new Location("b8"), false);

				if(check(currentPlayer)) {
					move(new Location("b8"), new Location("e8"), false);
					return false;
				}
                
                move(new Location("b8"), new Location("e8"), false);
                
            } else {
                return false;
            }
        }

        return true;
    }

	public void castleKing(int currentPlayer) {
        if(currentPlayer == 0) {
            move(new Location("e1"), new Location("g1"));
            move(new Location("h1"), new Location("f1"));
        } else {
            move(new Location("e8"), new Location("g8"));
            move(new Location("h8"), new Location("f8"));
        }
	}

	public void castleQueen(int currentPlayer) {
		if(currentPlayer == 0) {
			move(new Location("e1"), new Location("c1"));
			move(new Location("a1"), new Location("d1"));
		} else {
			move(new Location("e8"), new Location("c8"));
			move(new Location("a8"), new Location("d8"));
		}
	}

    public void replace(Location l, Piece p) {
        board[l.x][l.y] = p;
    }

	public boolean onlyKings() {
		for(Piece[] arr: board) {
			for(Piece p: arr) {
				if(p != null && !(p instanceof King)) {
					return false;
				}
			}
		}
		return true;
	}

	public void setNull(Location l) {
		board[l.x][l.y] = null;
	}

	public void setPiece(Location l, Piece pieceToAdd) {
		board[l.x][l.y] = pieceToAdd;
	}
}