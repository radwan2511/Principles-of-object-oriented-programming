import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Hunter extends Player {
    
    private Integer abilityRange;
    private Integer arrowsCount;
    private Integer ticksCount;
    
    
    public Hunter(String name, Health health, Integer attackPoints, Integer defensePoints, Integer abilityRange,char tile, int[] position) {
        super(name, health, attackPoints, defensePoints, tile, position);
        this.abilityRange = abilityRange;
        this.arrowsCount = 10;
        this.ticksCount = 0;
    }

    @Override
    public String levelUp() {
        super.levelUp();
        this.arrowsCount = this.arrowsCount + (10*this.getLevel());
        this.attackPoints = this.attackPoints+(2*this.getLevel());
        this.defensePoints = this.defensePoints + this.getLevel();
        return this.getName()+" reached new level "+this.level+": +"+this.getLevel()*10+" Health, +"+this.getLevel()*4+" Attack, +"+this.getLevel()+" Defense.\n";
        
    }
    public void gameTick(){
        if(this.ticksCount == 10) {
        	this.arrowsCount = this.arrowsCount + this.getLevel();
        	this.ticksCount = 0;
        }
        else
        {
        	this.ticksCount = this.ticksCount + 1;
        }
    }
    
    public boolean canCastAbility(List<Enemy> enimesWithinRange){
    	return this.arrowsCount > 0 & enimesWithinRange.size()>0 ; // and there is no player in range
	}
    
    private Enemy getEnemyInMinRange(List<Enemy> enimesWithinRange)
    {
    	Enemy minEnemyInRange = enimesWithinRange.get(0);
    	int minRange = this.range(minEnemyInRange);
    	for(Enemy e: enimesWithinRange ) {
    		if(this.range(e) < minRange)
    		{
    			minEnemyInRange = e;
    			minRange = this.range(e);
    		}
    	}
    	return minEnemyInRange;
    }
    
    @Override
    public String abilityCast(List<Enemy> enimesWithinRange){
    	String str = "";
        if(this.arrowsCount <= 0)
        {
        	str = str +this.getName()+ " tried to shoot an arrow but have 0 arrows left.\n";
        }
        else if(enimesWithinRange.size() == 0) {
        	str = str +this.getName()+ " tried to shoot an arrow but there were no enemies in range.\n";
        }
        else
        {
        	
        	this.arrowsCount = this.arrowsCount-1;        		
	            
	        
			Enemy enemyToDamage = getEnemyInMinRange(enimesWithinRange);
			Random rand = new Random();
			
        	str = this.getName() +" fired an arrow at "+enemyToDamage.getName()+".\n";
			
			int attackerAmount = this.attackPoints;
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
        return str;
    }
    
    
    
    @Override
    public String description() {
    	
		return this.getName() +"\tHealth: "+this.health.getHealthAmount()+"/"+this.health.getHealthPool()+"\tAttack: "+this.attackPoints+"\tDefense: "+this.defensePoints+"\tLevel: "+this.getLevel()+"\tExperience: "+this.getExperience()+"/"+50*this.getLevel()+"\tArrows: "+this.arrowsCount+"\t Range: "+this.abilityRange;

    	
    }
    
    
    public int getRange() {
    	return abilityRange;
    }
    
}

