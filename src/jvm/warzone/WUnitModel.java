package warzone;

public class WUnitModel {
	private String name;
	private int hp;
	private int attack;
	private int defend;
	private int moves;
	private WSpecialAbility special;
	private boolean movable;
	
	public WUnitModel(String name1, int hp1, int attack1, int defend1, int moves1, WSpecialAbility special1, boolean movable1) {
		name = name1;
		hp = hp1;
		attack = attack1;
		defend = defend1;
		moves = moves1;
		if(special1!=null) {
			special = special1.getClone();
		} else
			special = null;
		movable = movable1;
	}
	
	public WUnitModel(WUnitModel other) {
		name = other.name;
		hp = other.hp;
		attack = other.attack;
		defend = other.defend;
		moves = other.moves;
		if(other.special==null) special = null;
		else
			special = other.special.getClone();
		movable = other.movable;
	}
	
	public String getName() {return name; }
	public int getHP() {return hp; }
	public int getAttack() {return attack; }
	public int getDefend() {return defend; }
	public int getMoves() {return moves; }
	public WSpecialAbility getSpecial() {return special; }
	public boolean getMovable() {return movable; }
	
	public void setName(String name1) {name = name1; }
	public void setHP(int hp1) {hp = hp1; }
	public void setAttack(int attack1) {attack = attack1; }
	public void setDefend(int defend1) {defend = defend1; }
	public void setMoves(int num) { moves = num; }
	public void setSpecial(WSpecialAbility special1) {special = special1; }
	public void setMovable(boolean movable1) {movable = movable1; }
}
