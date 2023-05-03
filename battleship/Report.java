package battleship;

public class Report {
	private Position p;
	private boolean damage;
	private boolean sunk;
	
	public Report(){
	}
	
	public Report(Position p, boolean damage, boolean sunk) {
		this.p = p;
		this.damage = damage;
		this.sunk = sunk;
	}
	public Position getP() {
		return p;
	}
	public void setP(Position p) {
		this.p = p;
	}
	public boolean isDamage() {
		return damage;
	}
	public void setDamage(boolean damage) {
		this.damage = damage;
	}
	public boolean isSunk() {
		return sunk;
	}
	public void setSunk(boolean sunk) {
		this.sunk = sunk;
	}	
	public String toString(){
		return "coordinate:"+p+" hit:"+damage+" sunk:"+sunk;
	}
}
