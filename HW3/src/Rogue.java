import java.util.List;
import java.util.Random;

public class Rogue extends Player {
    private Integer cost; // special ability cost
    private Integer currentEnergy;
    
    private final int rogueRange = 2;
    
    public Rogue(String name, Health health, Integer attackPoints, Integer defensePoints, Integer cost,char tile, int[] position) {
        super(name, health, attackPoints, defensePoints,tile,position);
        this.cost = cost;
        this.currentEnergy = 100;
    }

    @Override
    public String levelUp() {
        super.levelUp();
        this.currentEnergy = 100;
        this.attackPoints = this.attackPoints + 3 * this.level;
        return this.getName()+" reached new level "+this.level+": +"+this.getLevel()*10+" Health, +"+this.getLevel()*7+" Attack, +"+this.getLevel()+" Defense.\n";
    }
    public void gameTick(){
        this.currentEnergy = Math.min(this.currentEnergy + 10,100);
    }
    
    public boolean canCastAbility(){
        return this.currentEnergy >= this.cost;
     }
    
    
    
    
    @Override
    public String abilityCast(List<Enemy> enimesWithinRange){
    	String str = "";
        if(canCastAbility()){
        	str = str+this.getName()+" cast Fan Of Knives.\n";
    		this.currentEnergy = this.currentEnergy - this.cost;

        	if(enimesWithinRange.size() > 0) {	            
	            for(Enemy e: enimesWithinRange)
	            {
	            	Random r = new Random();
	                int attackerAmount = this.attackPoints;
	                int defenderAmount = r.nextInt(e.defensePoints + 1);
	                
	                int amount = attackerAmount - defenderAmount;
	                
	            	int enemyRemainingHealth = e.health.getHealthAmount();

	                str = str + e.getName()+" rolled "+defenderAmount+" Defense Points.\n";

	                if(amount > 0) {
		            	enemyRemainingHealth = e.health.getHealthAmount() - amount;
		            	e.health.setHealthAmount(enemyRemainingHealth);
		            	
			            str = str + this.getName()+" hit "+e.getName()+" for "+ amount + " ability Damage.\n";
	                }
	                else
	                {
			            str = str + this.getName()+" hit "+e.getName()+" for 0 ability Damage.\n";

	                }
	                if (enemyRemainingHealth <= 0)
	                {              	
	                	this.setExperience(this.getExperience() + e.getExperienceValue());
	                	
	                	//this.swap(enemyToDamage);
	                	e.setTile('.');
	                	//enimesWithinRange.remove(enemyToDamage);
		            	str = str + e.getName() +" died. "+this.getName()+" gained "+ e.getExperienceValue()+" experience.\n";
		            	while(this.getExperience() >= 50 * this.getLevel()) {
		                	str = str + this.levelUp();
		                }
	                }
	            }
        	}
        }
        else {
        	str = str +this.getName()+ " tried to cast Fan Of Knives, but the current Energy: "+ this.currentEnergy +" < ability cost = "+this.cost+"\n.";

        }
        
        return str;
    }
    
    public int getRange() {
    	return rogueRange;
    }
    
    
    @Override
    public String description() {
        
        return this.getName() +"\tHealth: "+this.health.getHealthAmount()+"/"+this.health.getHealthPool()+"\tAttack: "+this.attackPoints+"\tDefense: "+this.defensePoints+"\tLevel: "+this.getLevel()+"\tExperience:"+this.getExperience()+"/"+50*this.getLevel()+"\tEnergy: "+this.currentEnergy+"/100";

        
    }
    
}
