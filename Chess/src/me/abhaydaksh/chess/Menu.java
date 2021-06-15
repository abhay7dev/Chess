package me.abhaydaksh.chess;

import java.util.Scanner;

public class Menu {

    private Scanner scan;

    public static String blind = "n";

    public Menu() {
        scan = new Scanner(System.in);
        start();
    }

    private void start() {
        System.out.print(Finals.WHITE_BOLD + "\033[H\033[2J\tWelcome to console chess!\n" + Finals.GREEN + "\n1. Play the game" + Finals.CYAN + "\n2. Help" + Finals.RED + "\n3. Exit\n" + Finals.RESET + "\nEnter input: ");

        int num = -1;
        String input = scan.nextLine();
        try {
            num = Integer.parseInt(input);
        } catch(Exception e) {
            if(!input.equals("")) {
                wait("You have entered incorrect input. Please enter an integer");
                start();
            } else {
                startGame();
                start();
            }
        }

        switch(num) {
            case 1:
                startGame();
                start();
                break;
            case 2:
                giveHelp();
                start();
                break;
            case 3:
                Finals.CLEAR();
                System.out.print("Bye!\n");
                System.exit(0);
            default:
				System.out.print(Finals.RED);
                wait("You have entered incorrect input");
				System.out.print(Finals.RESET);
                start();             
        }

    }

	private void startGame() {

        System.out.print("Enter player 1's name (White): ");
        String p1 = scan.nextLine();

        System.out.print("Enter player 2's name (Black): ");
        String p2 = scan.nextLine();

		System.out.print("Do you want to play blind chess? Blind chess does not allow you to see the board (y, n): ");

        blind = scan.nextLine().toLowerCase();     
        
        if(p1.trim().isEmpty() && !p2.trim().isEmpty()){
		    new Game("WHITE", p2);
        }else if(!p1.trim().isEmpty() && p2.trim().isEmpty()){
		    new Game(p1, "BLACK");
        }else if(p1.trim().isEmpty() && p2.trim().isEmpty()){
		    new Game("WHITE", "BLACK");
        }else{
		    new Game(p1, p2);
        }
	}

    private void giveHelp() {
        Finals.CLEAR();
        slowPrint("\tHow to play the game\nHow to play chess: https://www.chess.com/learn-how-to-play-chess\n\tHow to use this console chess game\nUse [Position of piece you want to move] [Position you want piece to move to]\nExample - \"e2 e4\"\nIf you want to castle on the kings side, type o-o. If you want to castle on the queens side, type o-o-o\n\nAdditionally, it is important to note that the orientation will be switched to black on bottom on blacks turn and white on bottom and whites turn\n");
        wait("");

    }

    private void slowPrint(String str) {
        try {
            for(char c: str.toCharArray()) {
                System.out.print(c);
                Thread.sleep(25);
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error printing");
        }
    }

    private void wait(String message){
        try {
            System.out.println(message + "\nPress enter or return to continue");
            System.in.read();
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

}