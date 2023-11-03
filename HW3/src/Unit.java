public abstract class Unit extends Tile {
    protected String name;
    protected Health health;
    protected Integer attackPoints;
    protected Integer defensePoints;

    public Unit(String name, Health health, Integer attackPoints, Integer defensePoints,char tile, int[] position) {
    	super(tile,position);
        this.name = name;
        this.health = health;
        this.attackPoints = attackPoints;
        this.defensePoints = defensePoints;
    }

    /*public double range(){
        return 0;
    }*/
    
    public String getName() {
    	return this.name;
    }
    
    @Override
    public String toString() {
        return "Unit{" +
                "name='" + name + '\'' +
                ", health=" + health +
                ", attackPoints=" + attackPoints +
                ", defensePoints=" + defensePoints +
                '}';
    }
    public abstract String description();
    

}
