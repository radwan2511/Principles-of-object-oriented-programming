import java.util.List;
import java.util.Random;

public class Warrior extends Player {
    private Integer abilityCooldown; //number of game ticks required to pass before the warrior can cast the ability again
    private Integer remainingCooldown; //number of ticks remained until the warrior can cast its special ability
    
    private final int warriorRange = 3;
    
    public Warrior(String name, Health health, Integer attackPoints, Integer defensePoints,Integer abilityCooldown,char tile, int[] position) {
        super(name, health, attackPoints, defensePoints, tile,  position);
        this.abilityCooldown = abilityCooldown;
        this.remainingCooldown = 0;
    }
    
    public int getRange() {
    	return warriorRange;
    }
    
    public boolean canCastAbility(){
        return this.remainingCooldown <= 0;
    }
    @Override
    public String abilityCast(List<Enemy> enimesWithinRange){
    	String str = "";
        if(canCastAbility()){
        	 this.remainingCooldown = this.abilityCooldown+1;
	         this.health.setHealthAmount(Math.min(this.health.getHealthAmount()+(10*this.defensePoints),this.health.getHealthPool()));  
	         str = this.getName() +"used Avenger's Shield, healing for "+10*this.defensePoints+".\n";
	         if(enimesWithinRange.size() > 0) {
        		
	        	Random rand = new Random();
	        	
	           //edited by radwan
	            //need to return the amount of damage by the spiecial ability to affect the random enemy within range
	            //return new int[] {this.health.getHealthPool(), warriorRange};
	            
	           
	            Enemy enemyToDamage = enimesWithinRange.get(rand.nextInt(enimesWithinRange.size()));
	            
	            int attackerAmount = this.health.getHealthPool()/10;
	            int defenderAmount = rand.nextInt(enemyToDamage.defensePoints + 1);

	            int amount = attackerAmount - defenderAmount;

	            str = str + enemyToDamage.getName() +" rolled " + defenderAmount+" defense points.\n";
	            
	            int enemyRemainingHealth = enemyToDamage.health.getHealthAmount();
	            // here damaging the random enemy
	            if(amount > 0) {
		            enemyRemainingHealth = enemyToDamage.health.getHealthAmount() - amount;
		            enemyToDamage.health.setHealthAmount(enemyRemainingHealth);
		            str = str + this.getName()+" hit "+enemyToDamage.getName()+" for "+ amount + " ability Damage.\n";
	            }
	            else
	            {
		            str = str + this.getName()+" hit "+enemyToDamage.getName()+" for 0 ability Damage.\n";
	            }
	            if (enemyRemainingHealth <= 0)
	            {
	            	this.setExperience(this.getExperience() + enemyToDamage.getExperienceValue());
	            	//this.swap(enemyToDamage);
	            	enemyToDamage.setTile('.');
	            	
	            	str = str + enemyToDamage.getName() +" died. "+this.getName()+" gained "+ enemyToDamage.getExperienceValue()+" experience.\n";
	            	//enimesWithinRange.remove(enemyToDamage);
	            	
	            	while(this.getExperience() >= 50 * this.getLevel()) {
	                	str = str + this.levelUp();
	                }
	            }
        	}
            
        }
        else {
        	str = str +this.getName()+ " tried to cast Avenger's Shield, but there is a cooldown: "+ this.remainingCooldown+"\n.";
        }
        return str;
    }
    
    public void gameTick(){
        if(this.remainingCooldown > 0){
            this.remainingCooldown--;
        }
    }
    
    
    
    
    
    
    
    @Override
    public String levelUp() {
        super.levelUp();
        this.remainingCooldown = 0;
        this.health.setHealthPool(this.health.getHealthPool() + 5 * this.level);
        this.attackPoints = this.attackPoints + 2 * this.level;
        this.defensePoints = this.defensePoints + 1 * this.level;
        return this.getName()+" reached new level "+this.level+": +"+this.getLevel()*15+" Health, +"+this.getLevel()*6+" Attack, +"+this.getLevel()*3+" Defense.\n";
    }

    @Override
    public String description() {

    	return this.getName() +"\tHealth: "+this.health.getHealthAmount()+"/"+this.health.getHealthPool()+"\tAttack: "+this.attackPoints+"\tDefense: "+this.defensePoints+"\tLevel: "+this.getLevel()+"\tExperience:"+this.experience+"/"+50*this.level+"\t Cooldown: "+this.remainingCooldown+"/"+this.abilityCooldown;

    }
    
}
