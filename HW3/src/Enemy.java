import java.util.List;

public abstract class Enemy extends Unit{
    protected Integer experienceValue; //Amount of experience gained by defeating this enemy

    public Enemy(String name, Health health, Integer attackPoints, Integer defensePoints, Integer experienceValue,char tile, int[] position) {
        super(name, health, attackPoints, defensePoints, tile, position);
        this.experienceValue = experienceValue;
    }
    
    public abstract String enemyTurn(Player player,char[][] board, List<Tile> tiles);
    
    public abstract String description();
    
    public Integer getExperienceValue() {
        return experienceValue;
    }
}
