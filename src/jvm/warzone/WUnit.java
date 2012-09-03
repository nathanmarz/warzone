package warzone;

import java.awt.*;

import javax.swing.*;

public class WUnit extends JComponent {
	public static final int BLUE_TEAM = -1;
	public static final int RED_TEAM = 1;
	
	//units
	public static final String SOLDIER = "soldier";
	public static final String BAR_SOLDIER = "bar_soldier";
	public static final String GRENADIER = "grenadier";
	public static final String SETTLER = "settler";
	public static final String JEEP = "jeep";
	public static final String LIGHT_TANK = "lighttank";
	public static final String MEDIUM_TANK = "mediumtank";
	public static final String HEAVY_TANK = "heavytank";
	public static final String ARTILLERY = "artillery";
	public static final String RANGED_ARTILLERY = "rangedart";
	public static final String DEFENDER = "defender";
	public static final String TRANSPORT_HELICOPTER = "transport_heli";
	public static final String MISSILE = "missile";
	
	//buildings
	public static final String RES_FARM = "resfarm";
	public static final String BARRACKS = "barracks";
	public static final String FACTORY = "factory";
	public static final String ACADEMY = "academy";
	public static final String TURRET = "turret";
	public static final String MISSILE_BASE = "missilebase";
	
	private Image myPic;
	private String name;
	
	private int hp;
	private int attack;
	private int defend;
	private int moves;
	private WSpecialAbility special;
	private int side;
	private boolean movable;
	
	private boolean selected;
	
	private int currTurn;
	private int movesForTurn;
	
	private WResource dataBase;

	private WViewer viewer;
	
	public int getHP() {return hp; }
	public int getAttack() {return attack; }
	public int getDefend() {return defend; }
	public int getSide() {return side; }
	public boolean isMovable() {return movable; }
	public String getUnitName() {return name; }
	public String getName() {return name; }
	
	
	public WUnit(String name1, WResource dataBase1, int unitSize, int side1, WViewer viewer1) {
		super();
		name = name1;
		currTurn = -2;
		viewer = viewer1;
		dataBase = dataBase1;
	
		selected = false;
		
		side = side1;
		WUnitModel model = new WUnitModel(dataBase.getUnitModel(name, side));
		SetAsModelBase(model);
		special = model.getSpecial();
		
		
		this.setSize(unitSize, unitSize);
		
		
		
		myPic = dataBase.getUnitPic(name, side);
		
	}
	
	private void SetAsModelBase(WUnitModel model) {
		hp = model.getHP();
		attack = model.getAttack();
		defend = model.getDefend();
		moves = model.getMoves();
		movable = model.getMovable();
	}
	
	public void useAllMoves(int turn) {
		while(getMoves(turn)>0) {
			useMove(turn);
		}
	}
	
	public void loadUnitIntoTransport(WUnit wu) {
		if(special instanceof WTransportAbility) {
			((WTransportAbility) special).addUnitIntoTransport(wu);
		}
	}
	
	public boolean canTransport(String unitType) {
		if(special==null) return false;
		if(special instanceof WTransportAbility) {
			return ((WTransportAbility) special).AcceptsUnitType(unitType);
		} else {
			return false;
		}
	}
	
	public void beginTurn() {
		if(special!=null) {
			special.DoTurnEnter();
		}
	}
	
	public WSpecialAbility getSpecial() {return special; }
	
	
	public void useMove(int turn) {
		useMove(turn, true);
	}
	public void useMove(int turn, boolean updateInspector) {
		if(turn!=currTurn) {
			movesForTurn = moves;
			currTurn = turn;
		}
		movesForTurn--;
		if(movesForTurn<0) movesForTurn = 0;
		if(updateInspector) viewer.UpdateMoveInspector(movesForTurn);
	}
	
	
	public void UpgradeToModel(WUnitModel wum) { //only updates base characteristics, won't change anything with state (i.e. WResarchAbility (a WSpecialAbility))
		SetAsModelBase(wum);
	}
	
	public int getMoves(int turn) {
		if(turn==currTurn) return movesForTurn;
		else {
			currTurn = turn;
			movesForTurn = moves;
			return moves; 
		}
	}
	
	//returns if this kills the unit
	public boolean changeHP(int delta) {
		hp+=delta;
		viewer.UpdateSelectedHP();
		if(hp<=0) {
			killThisUnit();
			return true;
		}
		return false;
	}
	
	public void killThisUnit() {
		viewer.EndUnitTurn(this);
		WGameBoard gb = viewer.getGameBoard();
		gb.removeUnit(this);
		viewer.getPlayer(side).removeUnit(this);
		//change to special.die - transport ability and research have special actions
		if(special!=null)
			special.UnitKilled(this);
	}
	
	public void killFromTransport() {
		viewer.getPlayer(side).removeUnit(this);
		if(special!=null)
			special.UnitKilled(this);
	}
	
	
	public void setSelected(boolean s) {
		selected = s; 
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(side == RED_TEAM) {
			g.setColor(Color.red);
		} else {
			g.setColor(Color.blue);
		}
		g.drawImage(myPic, WCanvas.GRID_BUFFER , WCanvas.GRID_BUFFER, this);
		g.drawRect(3,3,this.getWidth()-6, this.getHeight()-6);
		g.drawRect(4,4,this.getWidth()-8, this.getHeight()-8);
		if(selected) {
			g.setColor(Color.green);
			g.drawRect(1,1,this.getWidth()-2, this.getHeight()-2);
			g.drawRect(2,2,this.getWidth()-4, this.getHeight()-4);
		}
		if(moves>0&&!(getMoves(viewer.currTurn())==0)) {
			g.setColor(Color.black);
			g.fillOval(7,7,5,5);
		}
	}

}
