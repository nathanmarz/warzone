package warzone;


import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.util.*;


public class WViewer extends JPanel {
	public static final int SCAN_MODE = 1;
	public static final int UNIT_MODE = 2;
	public static final int SPECIAL_SELECT_MODE = 3;
	
	private int width;
	private int height;
	
	private WCanvas canvas;
	private WResource database;
	private int unitSize;
	
	private JLabel inspHP;
	private JLabel inspAttack;
	private JLabel inspDefend;
	private JLabel inspMoves;
	private JPanel inspSide;
	private JLabel inspRes;
	private JButton switchSides;
	private boolean inspectorLock;
	
	public WBoardNode[][] grid;
	
	private WGameBoard gameBoard;
	
	private int mode;
	
	
	private int currTurn;
	
	private WUnit currSelected;
	private int currRow;
	private int currCol;
	
	
	private int turnNum;
	
	private WPlayer redPlayer;
	private WPlayer bluePlayer;
	
	private WPlayer currPlayer;
	
	
	private Random randGen;
	
	
	
	private WSpecialAbility tempSpecial;
	
	public WViewer(int width1, int height1) {
		super();
		randGen = new Random();
		inspectorLock = false;
		turnNum = 0;
		width = width1;
		height = height1;
		grid = new WBoardNode[height][width];
		currSelected = null;
		
		currTurn = WUnit.RED_TEAM;
		
		setLayout(new BorderLayout(6, 6));
		canvas = new WCanvas(width,height, this, grid);
		add(canvas, BorderLayout.CENTER);
		unitSize = canvas.getGridSize();
		database = new WResource(canvas.getUnitSize());
		WInitializer.Initialize(database, this);
	
		JPanel east = new JPanel();
		addInspectorControls(east);
		add(east, BorderLayout.WEST);
		
		setInspector(null);
		
		gameBoard = new WGameBoard(canvas);
		
		
		inspSide.setBackground(Color.red);
		changeMode(SCAN_MODE);
		
		redPlayer = new WPlayer(this, WUnit.RED_TEAM, 10);
		bluePlayer = new WPlayer(this, WUnit.BLUE_TEAM, 10);
		currPlayer = redPlayer;
		
		WResearchAbility.InitializeTechnologies(this);
		
		final int SPACING = 5;
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				if(!(i<SPACING&&j<SPACING||i>=height-SPACING&&j>=width-SPACING)) {
					createTerrain(WTerrain.TREE,i,j);
				}
			}
		}
		createUnit(WUnit.SETTLER, WUnit.RED_TEAM, 0, 0);
		createUnit(WUnit.SETTLER, WUnit.RED_TEAM, 1, 0);
		createUnit(WUnit.SETTLER, WUnit.RED_TEAM, 0, 1);
		createUnit(WUnit.SETTLER, WUnit.RED_TEAM, 1, 1);
		
		createUnit(WUnit.SETTLER, WUnit.BLUE_TEAM, height-1, width-1);
		createUnit(WUnit.SETTLER, WUnit.BLUE_TEAM, height-2, width-1);
		createUnit(WUnit.SETTLER, WUnit.BLUE_TEAM, height-1, width-2);
		createUnit(WUnit.SETTLER, WUnit.BLUE_TEAM, height-2, width-2);
		
		/*
		//createTerrain(WTerrain.BOULDER,1,0);
		
		createUnit(WUnit.HEAVY_TANK, WUnit.RED_TEAM, 2,2);
		createUnit(WUnit.JEEP, WUnit.BLUE_TEAM, 2,8);
		createUnit(WUnit.LIGHT_TANK, WUnit.RED_TEAM, 3,2);
		createUnit(WUnit.MEDIUM_TANK, WUnit.RED_TEAM, 5,2);
		createUnit(WUnit.DEFENDER, WUnit.BLUE_TEAM, 5,8);
		createUnit(WUnit.SETTLER, WUnit.RED_TEAM, 3,3);
		createUnit(WUnit.RANGED_ARTILLERY, WUnit.RED_TEAM, 4, 4);
		createUnit(WUnit.MISSILE, WUnit.RED_TEAM, 5, 4);
		createUnit(WUnit.SETTLER, WUnit.BLUE_TEAM, 3,9);
		*/
		//ArrayList poss = new ArrayList();
		//poss.add("unit");
		//poss.add("terrain");
		//HighlightRangeAround(4,4,2,poss);
		
		
	}
	
	
	public static String UNIT = "unit";
	public static String TERRAIN = "terrain";
	
	public void HighlightRangeAround(int row, int col, int amt, ArrayList poss) {
		for(int i=row-amt;i<=row+amt;i++) {
			for(int j=col-amt;j<=col+amt;j++) {
				if(InBounds(i,j)) {
					int dist = Math.abs(row-i)+Math.abs(col-j);
					if(dist<=amt+1&&dist>0) {
						String compare = null;
						if(gameBoard.UnitAtSpace(i,j)!=null) compare = UNIT;
						else if(gameBoard.TerrainAtSpace(i,j)!=null) compare = TERRAIN;
						if(poss.contains(compare)) {
							grid[i][j].changeHighlight(WBoardNode.VALID);
						} else {
							grid[i][j].changeHighlight(WBoardNode.INVALID);
						}
					}
				}
			}
		}
	}
	
	public void ChooseSquareForSpecial(WSpecialAbility sp) {
		tempSpecial = sp;
		changeMode(SPECIAL_SELECT_MODE);
	}
	
	public int whoseTurn() {return currTurn; }
	
	public int currTurn() {return turnNum; }
	
	public WCanvas getCanvas() {return canvas; }
	
	public WPlayer getCurrentPlayer() {return currPlayer; }
	
	public void createUnit(String name, int side, int row, int col) {
		WUnit wu = new WUnit(name, database, unitSize, side, this);
		addUnitToGame(wu,side,row,col);
	}
	
	public void addUnitToGame(WUnit wu, int side, int row, int col) {
		gameBoard.addUnit(wu, row, col);
		getPlayer(side).addUnit(wu);
	}
	
	public WPlayer getPlayer(int side) {
		if(side==WUnit.RED_TEAM) return redPlayer;
		else
			return bluePlayer;
	}
	
	public void createTerrain(int type, int row, int col) {
		gameBoard.addTerrain(new WTerrain(type, database),row,col);
	}
	
	public WGameBoard getGameBoard() {return gameBoard; }
	
	public int getMode() {return mode; }
	
	public void UpdateSelectedHP() {
		if(currSelected!=null) {
			inspHP.setText(" " + currSelected.getHP());
		}
	}
	
	public WResource getDatabase() {return database; }
	
	private boolean InBounds(int row, int col) {
		return row>=0&&row<height&&col>=0&&col<width;
	}
	
	public void HighlightEmptyAround(int row, int col) {
		for(int i=row-1;i<=row+1;i++) {
			for(int j=col-1;j<=col+1;j++) {
				if(InBounds(i,j)) {
					if(Math.abs(row-i)+Math.abs(col-j)==1) {
						if(gameBoard.UnitAtSpace(i,j)==null&&gameBoard.TerrainAtSpace(i,j)==null)
							grid[i][j].changeHighlight(WBoardNode.VALID);
					}
				}
			}
		}
	}
	
	public void TryUnitSpecial(int row, int col) {
		WUnit wu = gameBoard.UnitAtSpace(row, col);
		WSpecialAbility special = wu.getSpecial();
		if(special!=null) {
			special.DoSpecial(row, col);
		}
	}
	
	
	public void SpaceClicked(int row, int col, int button) {
		/*
		ArrayList poss = new ArrayList();
		poss.add(new WStoreItem(WUnit.SOLDIER, 5));
		poss.add(new WStoreItem(WUnit.SETTLER, 15));
		poss.add(new WStoreItem(WUnit.ARTILLERY, 40));
		(new WUnitCreator(this, poss)).DoSpecial(row,col);
		*/
		//System.out.println("got here");
		if(mode==SCAN_MODE) {
			WUnit wu = gameBoard.UnitAtSpace(row, col);
			if(wu!=null&&wu.getSide()==currTurn) {
				if(button==2) {
					TryUnitSpecial(row,col);
				}
				if(button==1) {
					if(wu.getMoves(turnNum)>0&&(wu.isMovable()||wu.getAttack()>0)) {
						wu.setSelected(true);
						currSelected = wu;
						currRow = row;
						currCol = col;
						changeMode(UNIT_MODE);
						inspectorLock = true;
						HighlightSquaresForMove(row, col);
					} else {
						TryUnitSpecial(row,col);
					}
				}
			}
		} else if(mode==UNIT_MODE) {
			if(button==2) {
				EndUnitTurn(currSelected);
				grid[row][col].doMouseEntered();
			} else if(grid[row][col].getHighLight()==WBoardNode.VALID) {
				WBoardNode target = grid[row][col];
				UnHighlightSquaresAround(currRow, currCol);
				WUnit targUnit = gameBoard.UnitAtSpace(row,col);
				if(targUnit==null) {
					WTerrain terat = gameBoard.TerrainAtSpace(row,col);
					
					if(terat==null) {
						gameBoard.moveUnit(currRow, currCol, row, col);
						currRow = row;
						currCol = col;
					} else if(terat.getType()==WTerrain.TREE) {
						currPlayer.deltaResources(3);
						gameBoard.removeTerrain(row,col);
					}
				} else {
					if(targUnit.getSide()==currTurn) {
						currSelected.useMove(turnNum);
						targUnit.loadUnitIntoTransport(currSelected);
						grid[row][col].doMouseEntered();
						UnHighlightSquaresAround(currRow, currCol);
						return;
					} else if(DoBattle(gameBoard.UnitAtSpace(currRow, currCol), targUnit)) {
						grid[row][col].doMouseEntered();
						UnHighlightSquaresAround(currRow, currCol);
						return;
					}
				
				}
				HighlightSquaresForMove(currRow, currCol);
				currSelected.useMove(turnNum);
				if(currSelected.getMoves(turnNum)==0) EndUnitTurn(currRow, currCol);
			}
		} else if(mode==SPECIAL_SELECT_MODE) {
			if(button==2) {
				tempSpecial.SpecialSelect(-1,-1);
				changeMode(SCAN_MODE);
			}
			if(button==1) {
				if(grid[row][col].getHighLight()==WBoardNode.VALID) {
					tempSpecial.SpecialSelect(row,col);
					changeMode(SCAN_MODE);
				}
			}
		}
	}
	
	public void SpecialSelect(WSpecialAbility wsa) {
		tempSpecial = wsa;
		changeMode(SPECIAL_SELECT_MODE);
	}
	
	private boolean DoBattle(WUnit attacker, WUnit defender) {
		int totality = attacker.getAttack()+defender.getDefend();
		while(true) {
			int r = randGen.nextInt(totality);
			if(r<attacker.getAttack()) {
				if(defender.changeHP(-1)) return false;
			} else {
				if(attacker.changeHP(-1)) return true;
			}
		}
	}
	
	public void UpdateMoveInspector(int newMoves) {
		inspMoves.setText(" " + newMoves);
	}
	
	private void EndUnitTurn(int row, int col) {
		if(currSelected==gameBoard.UnitAtSpace(row,col)) {
			changeMode(SCAN_MODE);
			UnHighlightSquaresAround(row, col);
			currSelected.setSelected(false);
			inspectorLock = false;
		}
	}
	
	public void EndUnitTurn(WUnit wu) {
		if(wu==currSelected) {
			WCoordinate wc = gameBoard.UnitPos(wu);
			EndUnitTurn(wc.getRow(), wc.getCol());
		}
	}
	
	private void changeMode(int newMode) {
		mode = newMode;
		if(mode==SCAN_MODE) {
			switchSides.setEnabled(true);
		} else {
			switchSides.setEnabled(false);
		}
	}
	
	private void HighlightSquaresForMove(int row, int col) {
		for(int i = row-1;i<=row+1;i++) {
			for(int j=col-1;j<=col+1;j++) {
				if(Math.abs(i-row)+Math.abs(j-col)==1) {
					if(i>=0&&i<height&&j>=0&&j<width) {
						if(gameBoard.TerrainAtSpace(i,j)==null||(gameBoard.TerrainAtSpace(i,j).getType()==WTerrain.TREE&&currSelected.getAttack()>0)) {
							if(gameBoard.UnitAtSpace(i,j)==null||(gameBoard.UnitAtSpace(i,j).getSide()!=currTurn&&currSelected.getAttack()>0)||(gameBoard.UnitAtSpace(i,j).getSide()==currTurn&&gameBoard.UnitAtSpace(i,j).canTransport(currSelected.getName()))) {
								if(!(gameBoard.TerrainAtSpace(i,j)==null&&gameBoard.UnitAtSpace(i,j)==null&&!currSelected.isMovable())) {
									grid[i][j].changeHighlight(WBoardNode.VALID);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void UnHighlightSquaresAround(int row, int col) {
		UnHighlightSquaresAround(row,col,1);
	}
	
	public void UnHighlightSquaresAround(int row, int col, int amt) {
		for(int i = row-amt;i<=row+amt;i++) {
			for(int j=col-amt;j<=col+amt;j++) {
				if(InBounds(i,j)) {					
					grid[i][j].changeHighlight(WBoardNode.NOTHING);					
				}
			}
		}
	}
	
	
	private void addInspectorControls(JPanel east) {
		east.setPreferredSize(new Dimension(130,0));
		east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));
	
		inspHP = new JLabel();
		inspAttack = new JLabel();
		inspDefend = new JLabel();
		inspMoves = new JLabel();
		inspRes = new JLabel();
		
		
		east.add(MakeTitledLabel("HP:", inspHP));
		east.add(MakeTitledLabel("Attack:", inspAttack));
		east.add(MakeTitledLabel("Defend:", inspDefend));
		east.add(MakeTitledLabel("Moves:", inspMoves));
		
		east.add(Box.createVerticalStrut(50));
		
		inspSide = new JPanel();
		inspSide.setMaximumSize(new Dimension(1000,50));
		east.add(inspSide);
		
		east.add(Box.createVerticalStrut(10));
		
		east.add(MakeTitledLabel("$$$:", inspRes));
		
		east.add(Box.createVerticalStrut(10));
		
		switchSides = new JButton("End Turn");
		switchSides.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EndTurn();
			}
		});
		east.add(switchSides);
		
		JButton about = new JButton("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(WViewer.this, "Designed and Programmed by Nathan Marz\n\nnathanm@stanford.edu", "About", JOptionPane.INFORMATION_MESSAGE);
			}
			
		});
		east.add(about);
		
	}
	
	public void EndTurn() {
		if(mode==SCAN_MODE) {
			currTurn*=-1;
			currPlayer = getPlayer(currTurn);
			if(currTurn==WUnit.RED_TEAM) {
				inspSide.setBackground(Color.red);
			} else {
				inspSide.setBackground(Color.blue);
			}
			currPlayer.TurnSwitchTo();
			turnNum++;
			ArrayList allUnits = (ArrayList) redPlayer.getUnits().clone();
			allUnits.addAll((Collection)bluePlayer.getUnits().clone());
			for(Iterator it = allUnits.iterator();it.hasNext();) {
				WUnit wu = (WUnit) it.next();
				wu.repaint();
			}
			
		}
	}
	
	public void changeResInsp(int side, int resources) {
		if(side==currTurn) {
			inspRes.setText(" "+resources);
		}
	}
	
	public void setInspector(WUnit which) {
		if(!inspectorLock) {
			if(which!=null) {
				inspHP.setText(" "+which.getHP());
				inspAttack.setText(" "+which.getAttack());
				inspDefend.setText(" "+which.getDefend());
				inspMoves.setText(" "+which.getMoves(turnNum));
			} else {
					inspHP.setText("---");
					inspAttack.setText("---");
					inspDefend.setText("---");
					inspMoves.setText("---");
			}
		}
	}
	
	
	private JPanel MakeTitledLabel(String title, JLabel which) {
		JPanel ret = new JPanel();
		ret.setLayout(new BoxLayout(ret, BoxLayout.X_AXIS));
		JLabel titleLab = new JLabel(title);
		titleLab.setFont(new Font("Dialog", Font.BOLD, 20));
		which.setFont(new Font("Dialog", Font.BOLD, 20));
		ret.add(titleLab);
		ret.add(which);
		ret.setAlignmentX(0);
		return ret;
	}
	
}
