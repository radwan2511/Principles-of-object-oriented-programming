import java.util.LinkedList;
import java.util.Set;
import java.util.Scanner;
import java.util.Hashtable;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class GameBoard {
    private boolean endGame;
    
    
    private List<Tile> tiles;
    private List<Enemy> enemies;
    
    
    //added by radwan
    private char[][] board;
    private Player player;
    
    // enemies define
    private String monstersChars = "skqzbgwMCK";
    private String trapsChars = "BQD";
    
    private String[] monstersNames = { "Lannister Solider", "Lannister Knight", "Queen’s Guard", "Wright",
    		"Bear-Wright", 	"Giant-Wright",  "White Walker", "The Mountain", "Queen Cersei", "Night’s King"};
    
    private String[] trapsNames = { "Bonus Trap", "Queen’s Trap", "Death Trap" };
    
    
    private Hashtable<String,Integer[]> monsters;
	
    private Hashtable<String,Integer[]> traps;
    
    
    // player define
    private Hashtable<String,Integer[]> warriors;
	
    private Hashtable<String,Integer[]> mages;
    
    private Hashtable<String,Integer[]> rouges;
    
    private Hashtable<String,Integer[]> hunters;
    
    
    public GameBoard(char[][] board)
    {
    	// putting all the defined values
    	monsters = new Hashtable<String,Integer[]>();
    	//                   name,                     health, attack, defense, Vision Range, Experience Value
    	monsters.put("Lannister Solider", new Integer[] {80,      8,       3,       3,             25});
    	monsters.put("Lannister Knight", new Integer[] {200,      14,       8,       4,             50});
    	monsters.put("Queen’s Guard", new Integer[] {400,      20,       15,       5,             100});
    	monsters.put("Wright", new Integer[] {600,      30,       15,       3,             100});
    	monsters.put("Bear-Wright", new Integer[] {1000,      75,       30,       4,             250});
    	monsters.put("Giant-Wright", new Integer[] {1500,      100,       40,       5,             500});
    	monsters.put("White Walker", new Integer[] {2000,      150,       50,       6,             1000});
    	monsters.put("The Mountain", new Integer[] {1000,      60,       25,       6,             500});
    	monsters.put("Queen Cersei", new Integer[] {100,      10,       10,       1,             1000});
    	monsters.put("Night’s King", new Integer[] {5000,      300,       150,       8,             5000});
    	
    	
    	traps = new Hashtable<String,Integer[]>();
    	//            name,                     health, attack, defense, Experience Value, Visibility Time, Invisibility Time
    	traps.put("Bonus Trap", new Integer[]    {1,      1,       1,       250,                  1,                  5});
    	traps.put("Queen’s Trap", new Integer[] {250,      50,       10,       100,                  3,                  7});
    	traps.put("Death Trap", new Integer[] {500,      100,       20,       250,                  1,                  10});
    	
    	
    	warriors = new Hashtable<String,Integer[]>() ;
    	//            name,              health, attack, defense, cooldown
		warriors.put("Jon Snow", new Integer[] {300,      30,       4,       3});
		warriors.put("The Hound", new Integer[] {400,      20,       6,       5});
    	
		
		
		mages = new Hashtable<String,Integer[]>();
    	//            name,                     health, attack, defense, Mana Pool, Mana Cost, Spell Power, Hit Count, Range
    	mages.put("Melisandre", new Integer[]    {100,      5,       1,       300,      30,         15,         5,       6});
    	mages.put("Thoros of Myr", new Integer[] {250,      25,      4,       150,      20,         20,         3,       4});
		
		
    	rouges = new Hashtable<String,Integer[]>();
    	//            name,                   health, attack,    defense,   cost
    	rouges.put("Arya Starck", new Integer[] {150,      40,       2,       20});
    	rouges.put("Bronn", new Integer[]       {250,      35,       3,       50});
    	
    	hunters = new Hashtable<String,Integer[]>();
    			//      name,                health, attack,    defense,   Range
    	hunters.put("Ygritte", new Integer[] {220,      30,       2,       6});
    	
    	
    	this.endGame = false;
    	tiles = new ArrayList<Tile>();
    	enemies = new ArrayList<Enemy>();
    	
    	buildTilesFromBoard(board);
    	this.board = board;
    }
    
    public boolean getEndGame() {
    	return this.endGame;
    }
    
    public void updateBoard(char[][] board) {
    	this.endGame = false;
    	
    	this.board = board;
    	
    	tiles = new ArrayList<Tile>();
    	enemies = new ArrayList<Enemy>();
    	
    	for (int i=0;i<board.length;i++) {
    		for(int j=0;j<board[i].length;j++) {
    			// when updating board for next round or level up the player remains the same
    			if(board[i][j]== '@')
    			{
    				//choosePlayer(board[i][j], i, j);
    				player.setPosition(new int[] {i,j});
    			}
    			if(board[i][j]== '.' | board[i][j]=='#') {
    				//gameBoard.add(new Tile(board[i][j],new int[]{i,j}) );
    				tiles.add(new Tile(board[i][j],new int[]{i,j}) );
    			}
    			else {
    				int idx = monstersChars.indexOf(board[i][j]);
    				if(idx != -1) {
    					Integer[] values = monsters.get(monstersNames[idx]);
    					Monster monster = new Monster(monstersNames[idx], new Health(values[0],values[0]),values[1],values[2],values[3],values[4],board[i][j],new int[]{i,j});
    					monster.setPosition(new int[] {i,j});
    					monster.setTile(board[i][j]);
    					//gameBoard.add(monster);
    					enemies.add(monster);
    				}
    				else {
    					idx = trapsChars.indexOf(board[i][j]);
    					if(idx != -1) {
    						Integer[] values = traps.get(trapsNames[idx]);
    						Trap trap = new Trap(trapsNames[idx], new Health(values[0],values[0]),values[1],values[2],values[3],values[4],values[5],board[i][j],new int[]{i,j});
    						trap.setPosition(new int[] {i,j});
    						trap.setTile(board[i][j]);
        					//gameBoard.add(trap);
    						enemies.add(trap);
    					}
    				}
    				
    			}
    		}
    	}
    }
    
    
    public void choosePlayer(char tile, int i, int j)
    {
    	Scanner sc= new Scanner(System.in); //System.in is a standard input stream 
		boolean validPlayer = false;
		System.out.println("choose player: (Enter Player Full Name)");
		Set<String> war = warriors.keySet();
		for(String s:war)
		{
			Integer[] values = warriors.get(s);
			System.out.println(s +"\tHealth: "+values[0]+"/"+values[0]+"\tAttack: "+values[1]+"\tDefense: "+values[2]+"\tLevel: 1\tExperience:0/50\t Cooldown: 0/"+values[3]);
		}
		
		Set<String> mag = mages.keySet();
		for(String s:mag)
		{
			Integer[] values = mages.get(s);
			System.out.println(s +"\tHealth: "+values[0]+"/"+values[0]+"\tAttack: "+values[1]+"\tDefense: "+values[2]+"\tLevel: 1\tExperience:0/50\tManaPool: "+values[3]/4+"/"+values[3]+"\t Spell Power: "+values[5]);
		}
		
		Set<String> rou = rouges.keySet();
		for(String s: rou)
		{
			Integer[] values = rouges.get(s);
			System.out.println(s +"\tHealth: "+values[0]+"/"+values[0]+"\tAttack: "+values[1]+"\tDefense: "+values[2]+"\tLevel: 1\tExperience:0/50\tEnergy: 100/100");
		}
		
		Set<String> hun = hunters.keySet();
		for(String s: hun)
		{
			Integer[] values = hunters.get(s);
			System.out.println(s +"\tHealth: "+values[0]+"/"+values[0]+"\tAttack: "+values[1]+"\tDefense: "+values[2]+"\tLevel: 1\tExperience:0/50\tArrows: 10\tRange: "+values[3]);
		}
		
		
		String str="";
		while(!validPlayer) {
			str= sc.nextLine();
			validPlayer = true;
			if(warriors.keySet().contains(str)) {
				Integer[] values = warriors.get(str);
				player = new Warrior(str,new Health(values[0],values[0]),values[1],values[2],values[3],tile,new int[] {i,j});
			}
			else if(mages.keySet().contains(str)) {
				Integer[] values = mages.get(str);
				player = new Mage(str,new Health(values[0],values[0]),values[1],values[2],values[3],values[4],values[5],values[6],values[7],tile,new int[] {i,j});
			}
			else if(rouges.keySet().contains(str)) {
				Integer[] values = rouges.get(str);
				player = new Rogue(str,new Health(values[0],values[0]),values[1],values[2],values[3],tile,new int[] {i,j});
			}
			else if(hunters.keySet().contains(str)) {
				Integer[] values = hunters.get(str);
				player = new Hunter(str,new Health(values[0],values[0]),values[1],values[2],values[3],tile,new int[] {i,j});
			}
			else
			{
				validPlayer = false;
				System.out.println("Please Choose a player from the list! (Write his Full Name as seen in the List)!!!");
			}
		}
		
		System.out.println("You Have Selected:\n"+str);
    }
    
    
    
    public void buildTilesFromBoard(char[][] board)
    {
    	for (int i=0;i<board.length;i++) {
    		for(int j=0;j<board[i].length;j++) {
    			if(board[i][j]== '@')
    			{
    				choosePlayer(board[i][j], i, j);
    				
    			}
    			if(board[i][j]== '.' | board[i][j]=='#') {
    				//gameBoard.add(new Tile(board[i][j],new int[]{i,j}) );
    				tiles.add(new Tile(board[i][j],new int[]{i,j}) );
    			}
    			else {
    				int idx = monstersChars.indexOf(board[i][j]);
    				if(idx != -1) {
    					Integer[] values = monsters.get(monstersNames[idx]);
    					Monster monster = new Monster(monstersNames[idx], new Health(values[0],values[0]),values[1],values[2],values[3],values[4],board[i][j],new int[]{i,j});
    					monster.setPosition(new int[] {i,j});
    					monster.setTile(board[i][j]);
    					//gameBoard.add(monster);
    					enemies.add(monster);
    				}
    				else {
    					idx = trapsChars.indexOf(board[i][j]);
    					if(idx != -1) {
    						Integer[] values = traps.get(trapsNames[idx]);
    						Trap trap = new Trap(trapsNames[idx], new Health(values[0],values[0]),values[1],values[2],values[3],values[4],values[5],board[i][j],new int[]{i,j});
    						trap.setPosition(new int[] {i,j});
    						trap.setTile(board[i][j]);
        					//gameBoard.add(trap);
    						enemies.add(trap);
    					}
    				}
    				
    			}
    		}
    	}
    }
    
    public void updateEnemiesDeadAfterSpecialAbility() {
    	//String str = "";
    	List<Enemy> needToRemove = new ArrayList<>();
    	for (Enemy e: enemies) {
    		if(e.getTile() == '.') {
    			//str = str+e.getName()+" is dead.\n";
    			Tile enemyDead = new Tile('.',new int[] {0,0});
    			board[e.getPosition()[0]][e.getPosition()[1]] = '.';
    			e.swap(enemyDead);
    			tiles.add(enemyDead);
    			//enemies.remove(e);
    			needToRemove.add(e);
    		}
    	}
    	enemies.removeAll(needToRemove);
    	//return str;
    }
    
    //added by radwan
    public String combatPlayerActivatesSpecialAbility() {
    	String str = "";
    	int range = player.getRange();
    	List<Enemy> enemiesInRange = getEnemeisWithInRange(range);
		str = this.player.abilityCast(enemiesInRange);
    	if(enemiesInRange.size() > 0) {    		
    		// need to do: check if enemy is dead to remove him from the list and add '.' tile to tile list
    		updateEnemiesDeadAfterSpecialAbility();
    	}
    	return str;
    	
    }
    
    public List<Enemy> getEnemeisWithInRange(int range)
    {
    	List<Enemy> enemiesFound = new ArrayList<>();
    	for (Enemy e : enemies) {
    		//if(t.getTile() != '@' & t.getTile() != '#' & t.getTile() != '.')
    		if(player.range(e) < range){
    			enemiesFound.add(e);
    		}
    	}
    	return enemiesFound;
    }
    
    public void printBoard()
    {
    	// need to update board from tiles changes
    	for(int i=0;i<board.length;i++) {
    		for(int j=0;j<board[i].length;j++) {
    			System.out.print(board[i][j]+" ");
    		}
    		System.out.println();
    	}
    }
    
    public String enemyTurn() {
    	String str = "";
    	for(Enemy e: enemies) {
    		str = str + e.enemyTurn(player, board, tiles);
    		if(this.checkIfGameEnded()) {
    			break; // game ended beacuase player is Dead
    		}
    	}
    	return str;
    }
    
    public String playerTurn()
    {
    	Scanner sc = new Scanner(System.in); //System.in is a standard input stream 
		boolean validInput = false;
		String str = "";
		
		int[] playerPosition = this.player.getPosition();
		
		boolean up, down, left, right;
		while(!validInput) {
			System.out.println("Please Choose Player Action");
			up = board[playerPosition[0]-1][playerPosition[1]] != '#';
			if(up)
				System.out.println("W - Move Up");
			down = board[playerPosition[0]+1][playerPosition[1]] != '#';
			if(down)
				System.out.println("S - Move Down");
			left = board[playerPosition[0]][playerPosition[1]-1] != '#';
			if(left)
				System.out.println("A - Move Left");
			right = board[playerPosition[0]][playerPosition[1]+1] != '#';
			if(right)
				System.out.println("D - Move Right");
			
			System.out.println("E - Cast Special Ability");
			System.out.println("Q - Do Nothing");
			
			
			str= sc.nextLine();
			validInput = ( ( up & (str.equals("w") | str.equals("W")) ) | (down &  (str.equals("s")| str.equals("S")) ) |(left & ( str.equals("a") | str.equals("A")) ) | ( right & ( str.equals("d") | str.equals("D")) ) | str.equals("E") | str.equals("e") | str.equals("q") | str.equals("Q") );      
			if(!validInput)
				System.out.println("Wrong Input!");
		}
		if(str.equals("E") | str.equals("e")) 
			str = combatPlayerActivatesSpecialAbility();
		else
			str = player.playerTurn(enemies, board, tiles, str);
		return str;
    }
    
    public void updateEnemiesDeadAfterPhysicalAttack()
    {
    	List<Enemy> needToRemove = new ArrayList<>();
    	for (Enemy e: enemies) {
    		if(e.getTile() == '.') {
    			tiles.add(new Tile('.',e.getPosition()));
    			//board[e.getPosition()[0]][e.getPosition()[1]] = '@';
    			//enemies.remove(e);
    			needToRemove.add(e);
    		}
    	}
    	enemies.removeAll(needToRemove);
    }
    
    public boolean start() { // here starting by the player turn
    	
    	//need to print enemies List
    	
    	//need to print player
    	this.printBoard();
    	System.out.println("\n"+player.description()+"\n");
    	
    	while (!checkIfGameEnded()) {
	    	String str = playerTurn();
	    	if(!str.equals(""))
	    		System.out.println(str);
			updateEnemiesDeadAfterPhysicalAttack();// if player attacked enemy and killed them
			if(!checkIfGameEnded())
			{
				str = enemyTurn();
				if(!str.equals(""))
		    		System.out.println(str);
			}
			this.player.gameTick();
			
			
			//need to print enemies List
	    	
	    	//need to print player
	    	
			this.printBoard();
			System.out.println("\n"+player.description()+"\n");
	    	
    	}
    	
    	boolean playerWin = this.enemies.size() == 0;
    	return playerWin;
    }
    
    public boolean checkIfGameEnded()
    {
    	this.endGame = this.player.getTile() == 'X' | this.enemies.size() == 0;
    	return this.endGame;
    }
}
