package warzone;

import javax.swing.*;

public abstract class WTechnology {
	protected WViewer viewer;
	private int price;
	private int numTurnsTotal;
	private String name;
	
	private int numTurnsLeft;
	
	public int getPrice() {return price; }
	public int getNumTurns() {return numTurnsTotal; }
	public int getNumTurnsLeft() {return numTurnsLeft; }
	
	public WTechnology(WViewer viewer1, String name1, int price1, int numTurns1) {
		viewer = viewer1;
		price = price1;
		numTurnsTotal = numTurns1;
		name = name1;
		numTurnsLeft = -1;
	}
	
	public void reset() {
		numTurnsLeft = -1;
	}
	
	public void setAs(WTechnology other) {
		viewer = other.viewer;
		price = other.price;
		numTurnsTotal = other.numTurnsTotal;
		name = other.name;
	}
	
	public String toString() {
		if(numTurnsLeft==-1) 
			return (name + ": $" +price + ", " + numTurnsTotal + " turns");
		else
			return (name + " | Time until completion: " + numTurnsLeft + " turns");
	}
	
	public abstract WTechnology createNewInstance();
	
	public boolean activate() {
		numTurnsLeft = numTurnsTotal;
		return CheckUpgradeReady();
	}
	
	private boolean CheckUpgradeReady() {
		if(numTurnsLeft==0) {
			DoTechUpgrade();
			String player = viewer.getCurrentPlayer().toString();
			JOptionPane.showMessageDialog(viewer, "Player: " + player + "\n" + "\"" + name + "\" technology discovered!", "Discovery", JOptionPane.PLAIN_MESSAGE);
			numTurnsLeft = -1;
			return true;
		}
		return false;
	}
	
	public boolean newTurnStarted() { 
		numTurnsLeft--;
		return CheckUpgradeReady();
	}
	
	
	public abstract void DoTechUpgrade(); //does it for current player
}
