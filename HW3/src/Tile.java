import java.util.Dictionary;
import java.util.Hashtable;

public class Tile {
    protected char tile;
    protected int[] position; //x,y
    
    public Tile(char tile, int[] position) {
    	this.tile = tile;
    	this.position = position;
    }
    
    public char getTile() {
        return tile;
    }

    public int[] getPosition() {
        return position;
    }

    public void setTile(char tile) {
        this.tile = tile;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }
    
    public int range(Tile t)
    {
    	int[] other = t.getPosition();
    	return (int)( Math.sqrt( Math.pow((this.position[0] - other[0]),2) + Math.pow((this.position[1] - other[1]),2)) );  
    }
    
    public void swap(Tile t) {
    	int[] temp = this.position;
    	int[] temp2 = t.getPosition();
    	this.setPosition(new int[] {temp2[0],temp2[1]});
    	t.setPosition(new int[] {temp[0],temp[1]});
    }
    
    public char toChar() {
    	return this.tile;
    }
        
}
