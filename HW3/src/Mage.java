import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Mage extends Player {
    private Integer manaPool;
    private Integer currentMana;
    private Integer manaCost; // ability cost
    private Integer spellPower; //ability scale factor
    private Integer hitsCount; //maximal number of times a single cast of the ability can hit
    private Integer abilityRange;
    
    
    
    public Mage(String name, Health health, Integer attackPoints, Integer defensePoints,Integer manaPool, Integer manaCost, Integer spellPower, Integer hitsCount, Integer abilityRange,char tile, int[] position) {
        super(name, health, attackPoints, defensePoints, tile, position);
        this.manaPool = manaPool;
        this.currentMana = this.manaPool / 4;
        this.manaCost = manaCost;
        this.spellPower = spellPower;
        this.hitsCount = hitsCount;
        this.abilityRange = abilityRange;
    }

    @Override
    public String levelUp() {
        super.levelUp();
        this.manaPool = this.manaPool + 25 * this.level;
        this.currentMana = Math.min(this.currentMana + (this.manaPool/4),this.manaPool);

        this.spellPower = this.spellPower + 10 * this.level;
        return this.getName()+" reached new level "+this.level+": +"+this.getLevel()*10+" Health, +"+this.getLevel()*4+" Attack, +"+this.getLevel()+" Defense, +"+this.getLevel()*25+" maximum mana, +"+10*this.getLevel()+" spell power.\n";
        
    }
    public void gameTick(){
        this.currentMana = Math.min(this.manaPool,this.currentMana + 1 * this.level);
    }
    
    public boolean canCastAbility(){
    return this.currentMana > this.manaCost;
	}

    @Override
    public String abilityCast(List<Enemy> enimesWithinRange){
    	String str = "";
    	
        if(canCastAbility()){
        	str = str + this.getName()+" cast Blizzard.\n";
        	if(enimesWithinRange.size() > 0) {
	        	
	        	Random rand = new Random();
	        	
	            this.currentMana = this.currentMana - this.manaCost;
	            int hit = 0;
	            //Blizzard ability
	            
	            List<Enemy> deadEnemies = new ArrayList<>();
	            
	            while(hit < this.hitsCount & enimesWithinRange.size()>0)
	            {   
	            	/// need to do: each enemy may defend itself
	            	
	            	
	            	Enemy enemyToDamage = enimesWithinRange.get(rand.nextInt(enimesWithinRange.size()));
	            	
	            	Random r = new Random();
	                int attackerAmount = this.spellPower;
	                int defenderAmount = r.nextInt(enemyToDamage.defensePoints + 1);
	                
	                
	                
	                
	                int amount = attackerAmount - defenderAmount;
	                
	                
	                int enemyRemainingHealth = enemyToDamage.health.getHealthAmount();
	                
	                
	                str = str + enemyToDamage.getName()+" rolled "+defenderAmount+" Defense Points.\n";
	                
	                if(amount > 0) {
		            	enemyRemainingHealth = enemyToDamage.health.getHealthAmount() - amount;
		            	enemyToDamage.health.setHealthAmount(enemyRemainingHealth);
		            	
			            str = str + this.getName()+" hit "+enemyToDamage.getName()+" for "+ amount + " ability Damage.\n";
		            	
	                }
	                else {
			            str = str + this.getName()+" hit "+enemyToDamage.getName()+" for 0 ability Damage.\n";

	                }
	            	if (enemyRemainingHealth <= 0)
		            {
		            	this.setExperience(this.getExperience() + enemyToDamage.getExperienceValue());
		            	//this.swap(enemyToDamage);
		            	enemyToDamage.setTile('.');
		            	
		            	deadEnemies.add(enemyToDamage);
		            	
		            	str = str + enemyToDamage.getName() +" died. "+this.getName()+" gained "+ enemyToDamage.getExperienceValue()+" experience.\n";

		            	
		            	enimesWithinRange.remove(enemyToDamage);
		            	
		            	while(this.getExperience() >= 50 * this.getLevel()) {
		                	str = str + this.levelUp();
		                }
		            }
	            	
	            	hit = hit + 1;
	            }
	            
	            while(deadEnemies.size()>0)
	            {
	            	Enemy temp = deadEnemies.get(0);
	            	enimesWithinRange.add(temp);
	            	deadEnemies.remove(0);
	            }
        	}
        	else
        	{
        		this.currentMana = this.currentMana - this.manaCost;
        	}
        }
        else {
        	str = str +this.getName()+ " tried to cast Blizzard, but the current mana: "+ this.currentMana+" <= current Mage mana cost = "+this.manaCost+"\n.";
        }
        return str;
    }
    
    
    
    @Override
    public String description() {
    	
		return this.getName() +"\tHealth: "+this.health.getHealthAmount()+"/"+this.health.getHealthPool()+"\tAttack: "+this.attackPoints+"\tDefense: "+this.defensePoints+"\tLevel: "+this.getLevel()+"\tExperience: "+this.getExperience()+"/"+50*this.getLevel()+"\tManaPool: "+this.currentMana+"/"+this.manaPool+"\t Spell Power: "+this.spellPower;

    	
    }
    
    
    public int getRange() {
    	return abilityRange;
    }
    
}
