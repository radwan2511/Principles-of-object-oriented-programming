import java.util.List;
import java.util.Random;
public class Monster extends Enemy {
    private Integer visionRange;

    public Monster(String name, Health health, Integer attackPoints, Integer defensePoints, Integer visionRange, Integer experienceValue,char tile, int[] position) {
        super(name, health, attackPoints, defensePoints, experienceValue, tile, position);
        this.visionRange = visionRange;
    }
    
    
    private void swapTileInPosition(List<Tile> tiles, int[] position,char[][] board)
    {
    	for(Tile t : tiles) {
    		if(t.getPosition()[0] == position[0] & t.getPosition()[1] == position[1]) {
    			this.swap(t);
    			// need to add to swap in board char[][]
    			char temp = this.getTile();
    			break;
    		}
    	}
    }
    
    private String moveLeft(Player player,char[][] board, List<Tile> tiles) {
    	
    	String str = "";
    	int x = this.getPosition()[0];
		int y = this.getPosition()[1];
		
		// check new position current value
		if(board[x][y-1] == '.')
		{
			swapTileInPosition(tiles,new int[] {x,y-1},board);
			board[x][y-1] = this.getTile();
			board[x][y] = '.';
		}
		else if(board[x][y-1] == '@')
		{
			// attack player
			str = attackPlayer(player,board);
		}
		else if(board[x][y-1] == '#')
		{
			// do random move because can't move to wall position
			str = doRandomMove(player,board,tiles);
		}
		return str;
    }
    
	private String moveRight(Player player,char[][] board, List<Tile> tiles) {
		String str = "";
		int x = this.getPosition()[0];
		int y = this.getPosition()[1];
		
		// check new position current value
		if(board[x][y+1] == '.')
		{
			swapTileInPosition(tiles,new int[] {x,y+1},board);
			board[x][y+1] = this.getTile();
			board[x][y] = '.';
		}
		else if(board[x][y+1] == '@')
		{
			// attack player
			str = attackPlayer(player,board);
		}
		else if(board[x][y+1] == '#')
		{
			// do random move because can move to wall position
			str = doRandomMove(player,board,tiles);
		}
		return str;
	}
	    
	private String moveUp(Player player,char[][] board, List<Tile> tiles) {
		String str = "";
		int x = this.getPosition()[0];
		int y = this.getPosition()[1];
		
		// check new position current value
		if(board[x-1][y] == '.')
		{
			swapTileInPosition(tiles,new int[] {x-1,y},board);
			board[x-1][y] = this.getTile();
			board[x][y] = '.';
		}
		else if(board[x-1][y] == '@')
		{
			// attack player
			str = attackPlayer(player,board);
		}
		else if(board[x-1][y] == '#')
		{
			// do random move because can move to wall position
			str = doRandomMove(player,board,tiles);
		}
		return str;
	}
	private String moveDown(Player player,char[][] board, List<Tile> tiles) {
		String str = "";
		int x = this.getPosition()[0];
		int y = this.getPosition()[1];
		
		// check new position current value
		if(board[x+1][y] == '.')
		{
			swapTileInPosition(tiles,new int[] {x+1,y},board);
			board[x+1][y] = this.getTile();
			board[x][y] = '.';
		}
		else if(board[x+1][y] == '@')
		{
			// attack player
			str = attackPlayer(player,board);
		}
		else if(board[x+1][y] == '#')
		{
			// do random move because can move to wall position
			str = doRandomMove(player,board,tiles);
		}
		return str;
	}

    
    
    public String doRandomMove(Player player,char[][] board, List<Tile> tiles) {
    	String str = "";
    	Random r = new Random();
    	int rand = r.nextInt(5);
    	//if(rand == 0) // do nothing
    	if(rand == 1) //move left
    	{
    		str = moveLeft(player,board,tiles);
    	}
    	else if(rand == 2) //move right
    	{
    		str = moveRight(player,board,tiles);
    	}
    	else if(rand == 3) //move up
    	{
    		str = moveUp(player,board,tiles);
    	}
    	else if(rand == 4) //move down
    	{
    		str = moveDown(player,board,tiles);
    	}
    	return str;
    }
    
    public String attackPlayer(Player player,char[][] board)
    {
    	String str = "";
    	Random r = new Random();
    	
        int attackerAmount = r.nextInt(this.attackPoints + 1);
        int defenderAmount = r.nextInt(player.defensePoints + 1);
        
        str = str + this.getName() + " engaged in combat with "+player.getName()+".\n";
        str = str+this.description()+"\n";
        str = str+player.description()+"\n";
        
        int amount = attackerAmount - defenderAmount;
        
        str = str + this.getName()+" rolled "+attackerAmount+" Attack Points.\n";
        str = str + player.getName()+" rolled "+defenderAmount+" Defense Points.\n";
        
        if(amount > 0){
            int nowHealth = player.health.getHealthAmount();
            player.health.setHealthAmount(nowHealth - amount);
            str = str + this.getName()+" dealt "+amount+" damage to "+player.getName()+".\n";

        }
        else {
            str = str + this.getName()+" dealt 0 damage to "+player.getName()+".\n";

        }
        if(player.health.getHealthAmount() <= 0){ //defender dies
            //TODO:  the player’s location is marked with ’X’
            //added by radwan
            player.setTile('X');
            board[player.getPosition()[0]][player.getPosition()[1]] = 'X';
            str = str + player.getName()+" died.";
            player.health.setHealthAmount(0);
        }
        return str;
    }
    
    
    public String enemyTurn(Player player,char[][] board, List<Tile> tiles) {
    	String str = "";
    	if (this.range(player) < this.visionRange) {
    		int dx = this.getPosition()[0] - player.getPosition()[0];
    		int dy = this.getPosition()[1] - player.getPosition()[1];
    		
    		int move = 0;
    		if(Math.abs(dy) > Math.abs(dx))
    		{
    			if(dx >= 0) {
    				// move left
    				str = moveLeft(player,board,tiles);
    			}
    			else
    			{
    				//moveRight
    				str = moveRight(player,board,tiles);
    			}
    		}
    		else
    		{
    			if(dy >= 0) {
    				//move up
    				str = moveUp(player,board,tiles);
    			}
    			else
    			{
    				//move down
    				str = moveDown(player,board,tiles);
    			}
    		}
    	}
    	else
    	{
    		//preform random movement action: left,right,up,down, or stay at the same place
    		str = doRandomMove(player,board,tiles);
    	}
    	return str;
    }
    @Override
    public String description() {
    	return this.getName() +"\tHealth: "+this.health.getHealthAmount()+"/"+this.health.getHealthPool()+"\tAttack: "+this.attackPoints+"\tDefense: "+this.defensePoints+"\tExperience value:"+this.experienceValue+"\t Vision Range: "+this.visionRange;

    }
}
