import java.util.List;
import java.util.Random;

public abstract class Player extends Unit {
    protected Integer experience;
    protected Integer level;

    public Player(String name, Health health, Integer attackPoints, Integer defensePoints,char tile, int[] position) {
        super(name, health, attackPoints, defensePoints,tile, position);
        this.experience=0; // Increased by killing enemies.
        this.level = 1; //Increased by gaining experience.
    }


    public String levelUp(){
    	String str = "player new level is "+this.level + 1+" is reached, updating attributes:\n";
        this.experience = this.experience - 50 * this.level;
        str = str +"new experience point: "+this.experience +"\n";
        this.level = this.level + 1;
        this.health.setHealthPool(this.health.getHealthPool() + 10 * this.level);
        str = str +"new health pool: "+this.health.getHealthPool() +"\n";
        this.health.setHealthAmount(this.health.getHealthAmount());
        this.attackPoints = this.attackPoints + 4 * level;
        str = str +"new attackPoints point: "+this.attackPoints +"\n";
        this.defensePoints = this.defensePoints + 1 * level;
        str = str +"new defensePoints point: "+this.defensePoints +"\n";

        return str;
    }

    @Override
    public String toString() {
        return "Player{" +
                "experience=" + experience +
                ", level=" + level +
                ", name='" + name + '\'' +
                ", health=" + health +
                ", attackPoints=" + attackPoints +
                ", defensePoints=" + defensePoints +
                '}';
    }
    
    public abstract String description();

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
    // added by radwan
    public Integer getLevel() {
    	return this.level;
    }
    
    public abstract String abilityCast(List<Enemy> enimesWithinRange);
    public abstract void gameTick();
    public abstract int getRange();
    public String playerTurn(List<Enemy> enemies,char[][] board, List<Tile> tiles,String input) {
    	
    	
    	String str = "";
    	
    	if(input.equals("w") | input.equals("W")) {
    		str = moveUp(enemies,board,tiles);
    	}
    	if(input.equals("s") | input.equals("S")) {
    		str = moveDown(enemies,board,tiles);
    	}
    	if(input.equals("a") | input.equals("A")) {
    		str = moveLeft(enemies,board,tiles);
    	}
    	if(input.equals("d") | input.equals("D")) {
    		str = moveRight(enemies,board,tiles);
    	}
    	// if input equals to q or Q - the do nothing
    	return str;
    }
    
    
    private void swapTileInPosition(List<Tile> tiles, int[] position,char[][] board)
    {
    	for(Tile t : tiles) {
    		if(t.getPosition()[0] == position[0] & t.getPosition()[1] == position[1]) {
    			this.swap(t);
    			break;
    		}
    	}
    }
    
    private String moveLeft(List<Enemy> enemies, char[][] board, List<Tile> tiles) {
    	
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
		else if(board[x][y-1] == '#')
		{
			// do nothing
		}
		else
		{	
			// attack enemy
			str = attackEnemy(getEnemyInPosition(enemies,new int[] {x,y-1}),board);
		}
		return str;
    }
    
    private String moveRight(List<Enemy> enemies, char[][] board, List<Tile> tiles) {
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
		else if(board[x][y+1] == '#')
		{
			// do nothing
		}
		else
		{	
			// attack enemy
			str = attackEnemy(getEnemyInPosition(enemies,new int[] {x,y+1}),board);
		}
		return str;
    }
    
    private String moveUp(List<Enemy> enemies, char[][] board, List<Tile> tiles) {
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
		else if(board[x-1][y] == '#')
		{
			// do nothing
		}
		else
		{	
			// attack enemy
			str = attackEnemy(getEnemyInPosition(enemies,new int[] {x-1,y}),board);
		}
		return str;
    }
    
    private String moveDown(List<Enemy> enemies, char[][] board, List<Tile> tiles) {
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
		else if(board[x+1][y] == '#')
		{
			// do nothing
		}
		else
		{	
			// attack enemy
			str = attackEnemy(getEnemyInPosition(enemies,new int[] {x+1,y}),board);
		}
		return str;
    }
    
    
    private Enemy getEnemyInPosition(List<Enemy> enemies, int[] position)
    {
    	Enemy found=null;
    	for(Enemy e: enemies)
    	{
    		if(e.getPosition()[0] == position[0] & e.getPosition()[1] == position[1]) {
    			found = e;
    			break;
    		}
    	}
    	return found;
    }
    
    
    private String attackEnemy(Enemy e, char[][] board)
    {	
    	String str = "";
    	Random r = new Random();
        int attackerAmount = r.nextInt(this.attackPoints + 1);
        int defenderAmount = r.nextInt(e.defensePoints + 1);
        
        str = str + this.getName() + " engaged in combat with "+e.getName()+".\n";
        str = str+this.description()+"\n";
        str = str+e.description()+"\n";
        int amount = attackerAmount - defenderAmount;
        
        str = str + this.getName()+" rolled "+attackerAmount+" Attack Points.\n";
        str = str + e.getName()+" rolled "+defenderAmount+" Defense Points.\n";

        
        if(amount > 0){
            int nowHealth = e.health.getHealthAmount();
            e.health.setHealthAmount(nowHealth - amount);
            str = str + this.getName()+" dealt "+amount+" damage to "+e.getName()+".\n";
        }
        else
        {
            str = str + this.getName()+" dealt 0 damage to "+e.getName()+".\n";
        }
        if(e.health.getHealthAmount() <= 0){ //defender dies
        	this.setExperience(this.getExperience() + e.experienceValue);
        	e.setTile('.');
        	

        	board[e.getPosition()[0]][e.getPosition()[1]] = '@';
        	board[this.getPosition()[0]][this.getPosition()[1]] = '.';
        	
            this.swap(e);
            
        	str = str + e.getName() +" died. "+this.getName()+" gained "+ e.getExperienceValue()+" experience.\n";
            
            while(this.getExperience() >= 50 * this.getLevel()) {
            	str = str + this.levelUp();
            }
        }
        return str;
        
    }
}
