
import java.util.Hashtable;


import java.util.List;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileInputStream;

public class GameFlow {
	private Player player;
	private int level;	
	private char[][] board;
	private boolean playerWinRound;
	private List<char[][]> boards;
	private int numberOflevels;
	
	private GameBoard gameBoard;
	
	
	//// path on radwan laptop for levels text file
	//   "C:\\Users\\radwa\\Desktop\\monha3\\levels_dir"
	
	public GameFlow() {
		this.board = board;
		this.level = 1;
		this.playerWinRound = false;
		this.boards = new ArrayList<>();
		this.numberOflevels = 0;
		
		
    	Scanner sc= new Scanner(System.in); //System.in is a standard input stream 
    	String path="";
		boolean validPath = false;
		
		int i=1;
		System.out.println("Please enter a file path that contains the boards each board must be in level<i>.txt files - i represints level number 1-->number of levels/boards");
		while(!validPath) {
			path = sc.nextLine();
			File f = new File(path+"//level"+i+".txt");
			validPath = f.exists();
			if(!validPath)
			{
				System.out.println("Wrong input!!! Try Again");
			}
		}
    	
		boolean allLevelsReaded = false;
		while(!allLevelsReaded)
		{
			try
			{
				File file = new File(path+"//level"+i+".txt");
				/*BufferedReader br = new BufferedReader(new FileReader(file));
				
				
				String st;
				  while ((st = br.readLine()) != null)
				  {
					  System.out.println(st);
				  }*/
				
				//File file = new File("data");
				FileInputStream fis = new FileInputStream(file);
				byte[] byteArray = new byte[(int)file.length()];
				fis.read(byteArray);
				String data = new String(byteArray);
				String[] stringArray = data.split("\r\n");
				//System.out.println("Number of lines in the file are ::"+stringArray.length);
				char[][] b = new char[stringArray.length][];
				int j=0;
				for(String s: stringArray)
				{
					b[j] = s.toCharArray();
					j = j+1;
				}
				boards.add(b);
				i = i+1;
				this.numberOflevels = this.numberOflevels + 1;
			}
			catch(Exception e)
			{
				// if we get here then all level files has been readed
				allLevelsReaded = true;
			}
		}
	}
	
	public void startGame() {
		this.board = this.boards.get(0);
		this.boards.remove(0);
		
		this.gameBoard = new GameBoard(this.board);
		boolean continueToNextBoard = true;
		playerWinRound = this.gameBoard.start();
		
		if(playerWinRound)
		{
			System.out.println("You Win!, congrats on wining the game on level: "+level+" loading next level");
			this.level = this.level+1;
			this.numberOflevels = this.numberOflevels-1;
		}
		else
		{
			System.out.println("You Lose!, you lost on level: "+level);
		}
		// if playerWinRound is true then player killed all enemies else player is dead
		
		// need to do this loop
		while(playerWinRound & true) {
			char[][] nextBoard = this.boards.get(0);
			this.boards.remove(0);
			this.gameBoard.updateBoard(nextBoard);
			playerWinRound = this.gameBoard.start();
			if(playerWinRound)
			{
				System.out.println("You Win!, congrats on wining the game on level: "+level+" loading next level");
				this.level = this.level+1;
				this.numberOflevels = this.numberOflevels-1;
			}
			else
			{
				System.out.println("You Lose!, you lost on level: "+level);
			}
		}
		
		if(this.numberOflevels == 0) {
			System.out.println("Congrats you have won all the levels in the game!!!");
		}
	}
}
