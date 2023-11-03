import java.util.List;
import java.util.Random;

public class Trap extends Enemy{
    private Integer visibilityTime; //Amount of ticks that the trap remains visible
    private Integer invisibilityTime;
    private Integer ticksCount; //Counts the number of ticks since last visibility state change
    private boolean visible;
    
    private final int trapRange = 2;
    
    public Trap(String name, Health health, Integer attackPoints, Integer defensePoints, Integer experienceValue, Integer visibilityTime, Integer invisibilityTime,char tile, int[] position) {
        super(name, health, attackPoints, defensePoints, experienceValue,tile, position);
        this.visibilityTime = visibilityTime;
        this.invisibilityTime = invisibilityTime;
        this.ticksCount = 0;
        this.visible = true;
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
    	visible = ticksCount < visibilityTime;
    	int i = this.getPosition()[0];
    	int j = this.getPosition()[1];
    	if(!visible) {
    		board[i][j] = '.';
    	}
    	else
    	{
    		board[i][j] = this.getTile();
    	}
    	
    	if(ticksCount == (visibilityTime + invisibilityTime)) {
    		ticksCount = 0;
    	}
    	else
    	{
    		ticksCount++;
    	}
    	if(this.range(player) < trapRange) {
    		str = attackPlayer(player,board);
    	}
    	return str;
    }
    
    public String description() {
    	return this.getName() +"\tHealth: "+this.health.getHealthAmount()+"/"+this.health.getHealthPool()+"\tAttack: "+this.attackPoints+"\tDefense: "+this.defensePoints+"\tExperience value:"+this.experienceValue;
    	
    }
    
}
