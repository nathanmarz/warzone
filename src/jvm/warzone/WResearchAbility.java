package warzone;

import java.util.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.*;
import java.awt.event.*;


public class WResearchAbility extends WSpecialAbility {
	private static int NUM_PROJECTS = 3;
	
	public static ArrayList availProjRed;
	private static ArrayList availProjBlue;
	
	
	
	public static void InitializeTechnologies(WViewer viewer) {
		availProjRed = new ArrayList();
		availProjBlue = new ArrayList();
		
		
		WUnitModel factNew = new WUnitModel(viewer.getDatabase().getUnitModel(WUnit.FACTORY, WUnit.RED_TEAM));
		factNew.setMoves(2);
		WUnitUpgrade factoryUpgrade = new WUnitUpgrade(viewer, "Streamlined Factory Production", 40, 3, WUnit.FACTORY, factNew, "factory.PNG", true);
		addTech(factoryUpgrade);
		
		WUnitModel soldierNew = new WUnitModel(viewer.getDatabase().getUnitModel(WUnit.BAR_SOLDIER, WUnit.RED_TEAM));
		soldierNew.setAttack(4);
		soldierNew.setDefend(4);
		soldierNew.setHP(2);
		WUnitUpgrade soldierUpgrade = new WUnitUpgrade(viewer, "Delta Force", 50, 2, WUnit.BAR_SOLDIER, soldierNew, "soldier.PNG");
		addTech(soldierUpgrade);
		
		
		ArrayList validDefTransports = new ArrayList();
		validDefTransports.add(WUnit.SOLDIER);
		validDefTransports.add(WUnit.SETTLER);
		validDefTransports.add(WUnit.BAR_SOLDIER);
		validDefTransports.add(WUnit.GRENADIER);
		WTransportAbility wta = new WTransportAbility(viewer, validDefTransports, null, 3);
		wta.setDescription("Can transport 3 of any infantry.");
		WUnitModel defenderNew = new WUnitModel(viewer.getDatabase().getUnitModel(WUnit.DEFENDER, WUnit.RED_TEAM));
		defenderNew.setSpecial(wta);
		WUnitUpgrade defenderUpgrade = new WUnitUpgrade(viewer, "Defender - Soldier Transport", 25, 2, WUnit.DEFENDER, defenderNew, "defender.PNG");
		addTech(defenderUpgrade);
		
		WUnitModel hTankNew = new WUnitModel(viewer.getDatabase().getUnitModel(WUnit.HEAVY_TANK, WUnit.RED_TEAM));
		hTankNew.setMoves(2);
		WUnitUpgrade heavyTankUpgrade = new WUnitUpgrade(viewer, "Fast Heavy Tanks", 60, 4, WUnit.HEAVY_TANK, hTankNew, "heavyTank.PNG");
		addTech(heavyTankUpgrade);
		
		WUnitModel artilleryNew = new WUnitModel(viewer.getDatabase().getUnitModel(WUnit.ARTILLERY, WUnit.RED_TEAM));
		artilleryNew.setMoves(3);
		WUnitUpgrade artilleryUpgrade = new WUnitUpgrade(viewer, "Quick Artillery", 40, 2, WUnit.ARTILLERY, artilleryNew, "artillery.PNG");
		addTech(artilleryUpgrade);
		
		WUnitModel settlerNew = new WUnitModel(viewer.getDatabase().getUnitModel(WUnit.SETTLER, WUnit.RED_TEAM));
		settlerNew.setMoves(2);
		WUnitUpgrade settlerUpgrade = new WUnitUpgrade(viewer, "Fast Settlers", 60, 2, WUnit.SETTLER, settlerNew, "settler.PNG");
		addTech(settlerUpgrade);
		
		WFarmUpgrade wfu = new WFarmUpgrade(viewer, "Farm Upgrade", 100,3,4);
		addTech(wfu);
		
		WUnitModel jeepNew = new WUnitModel(viewer.getDatabase().getUnitModel(WUnit.JEEP, WUnit.RED_TEAM));
		jeepNew.setMoves(jeepNew.getMoves()+1);
		WUnitUpgrade jeepUpgrade = new WUnitUpgrade(viewer, "...", 0, 0, WUnit.JEEP, jeepNew, "jeep.PNG");
		WUnitModel ltNew = new WUnitModel(viewer.getDatabase().getUnitModel(WUnit.LIGHT_TANK, WUnit.RED_TEAM));
		ltNew.setMoves(ltNew.getMoves()+1);
		WUnitUpgrade ltUpgrade = new WUnitUpgrade(viewer, "...", 0, 0, WUnit.LIGHT_TANK, ltNew, "lightTank.PNG");
		WUnitModel mtNew = new WUnitModel(viewer.getDatabase().getUnitModel(WUnit.MEDIUM_TANK, WUnit.RED_TEAM));
		mtNew.setMoves(mtNew.getMoves()+1);
		WUnitUpgrade mtUpgrade = new WUnitUpgrade(viewer, "...", 0, 0, WUnit.MEDIUM_TANK, mtNew, "mediumTank.PNG");
		ArrayList multUpgrade = new ArrayList();
		multUpgrade.add(jeepUpgrade);
		multUpgrade.add(ltUpgrade);
		multUpgrade.add(mtUpgrade);
		WMultipleUnitUpgrade lightEngUpgrade = new WMultipleUnitUpgrade(viewer, "Light Vehicle Engine Boost",70,3,multUpgrade);
		addTech(lightEngUpgrade);
		
		
	}
	
	private static void addTech(WTechnology wt) {
		WTechnology forRed = wt.createNewInstance();
		forRed.setAs(wt);
		
		WTechnology forBlue = wt.createNewInstance();
		forBlue.setAs(wt);
		
		availProjRed.add(forRed);
		availProjBlue.add(forBlue);
		
		
	}
	
	public class WResearchDialog extends JDialog {
		private ArrayList availProj;
		
		public WResearchDialog(int row, int col, WCanvas canvas, ArrayList availProj1) {
			super((JFrame)viewer.getRootPane().getParent(), "Academy", true);
			JFrame par = (JFrame)viewer.getRootPane().getParent();
			setLocation(par.getX()+canvas.getX()+col*canvas.getGridSize(), par.getY()+canvas.getY()+row*canvas.getGridSize());
			JPanel content = new JPanel();
			content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
			setContentPane(content);
			availProj = availProj1;
		
			JButton newProj = new JButton("  New project...    ");
			newProj.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WResearchDialog.this.dispose();
					new WResearchChooseDialog(viewer.getCanvas(), availProj);
				}
				
			});
			content.add(newProj);
			
			JButton currProj = new JButton(" Project Status...");
			currProj.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WResearchDialog.this.dispose();
					new WResearchStatusDialog(viewer.getCanvas());
				}
				
			});
			content.add(currProj);
			
			JButton done = new JButton("            Exit            ");
			done.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WResearchDialog.this.dispose();
				}
				
			});
			content.add(done);
			
			newProj.setAlignmentX((float) 0.5);
			currProj.setAlignmentX((float) 0.5);
			done.setAlignmentX((float) 0.5);
			
			pack();
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setResizable(false);
			setVisible(true);
		}
		
		
	}
	
	public class WResearchStatusDialog extends JDialog {
		
		public WResearchStatusDialog(WCanvas canvas) {
			super((JFrame)viewer.getRootPane().getParent(), "Status", true);
			JFrame par = (JFrame)viewer.getRootPane().getParent();
			JPanel content = new JPanel();
			content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
			setContentPane(content);
		
			JList stat = new JList(currProjects.toArray());
			JScrollPane scroller = new JScrollPane(stat);
			content.add(scroller);
			
			JButton done = new JButton("Exit");
			done.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WResearchStatusDialog.this.dispose();
				}
				
			});
			content.add(done);
			
			
			done.setAlignmentX((float) 0.5);
			
			pack();
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setResizable(false);
			setLocation(par.getX()+canvas.getX()+canvas.getWidth()/2-getWidth()/2, par.getY()+canvas.getY()+canvas.getHeight()/2-getHeight()/2);
			setVisible(true);
		}
	}
	
	
	public class WResearchChooseDialog extends JDialog {	
		private ArrayList availProj;
		private JList chooser;
		
		public WResearchChooseDialog(WCanvas canvas, ArrayList availProj1) {
			super((JFrame)viewer.getRootPane().getParent(), "Choose Project", true);
			//FillUpProjects();
			JFrame par = (JFrame)viewer.getRootPane().getParent();
			JPanel content = new JPanel();
			content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
			setContentPane(content);
			availProj = availProj1;
		
			chooser = new JList(availProj.toArray());
			
			JScrollPane scroller = new JScrollPane(chooser);
			chooser.setSelectedIndex(0);
			content.add(scroller);
			
			JButton chooseThis = new JButton("Begin Project");
			chooseThis.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int pos = chooser.getSelectedIndex();
					if(pos==-1) return;
					WTechnology wt = (WTechnology) availProj.get(pos);
					WPlayer currPlayer = viewer.getCurrentPlayer();
					if(currPlayer.getResources()>=wt.getPrice()||wt.getPrice()==0) {
						currPlayer.deltaResources(-wt.getPrice());
						wt.activate();
						currProjects.add(wt);
						availProj.remove(wt);
						WResearchChooseDialog.this.dispose();
					} else {
						JOptionPane.showMessageDialog(viewer, "Not enough resources!", "Research failed", JOptionPane.ERROR_MESSAGE);
					}
				}
				
			});
			content.add(chooseThis);
			if(availProj.size()==0) chooseThis.setEnabled(false);
			
			JButton done = new JButton("Exit");
			done.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WResearchChooseDialog.this.dispose();
				}
				
			});
			content.add(done);
			
			chooseThis.setAlignmentX((float) 0.5);
			done.setAlignmentX((float) 0.5);
			
			pack();
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setResizable(false);
			setLocation(par.getX()+canvas.getX()+canvas.getWidth()/2-getWidth()/2, par.getY()+canvas.getY()+canvas.getHeight()/2-getHeight()/2);
			setVisible(true);
		}
	}
	
	/*
	 * super((JFrame)viewer.getRootPane().getParent(), "Choose...", true);
			JFrame par = (JFrame)viewer.getRootPane().getParent();
			selectedItem = null;
			setLocation(par.getX()+canvas.getX()+col*canvas.getGridSize(), par.getY()+canvas.getY()+row*canvas.getGridSize());
	 * 
	 */
	
	private Random randGen;
	private ArrayList availProjects;
	
	private ArrayList currProjects;
	
	private void ReturnProjects(int side) {
		ArrayList projectList;
		if(side==WUnit.RED_TEAM) {
			projectList = availProjRed;
		} else {
			projectList = availProjBlue;
		}
		projectList.addAll(currProjects);
		projectList.addAll(availProjects);
		Iterator it = projectList.iterator();
		while(it.hasNext()) {
			WTechnology wt = (WTechnology) it.next();
			wt.reset();
		}
		currProjects.clear();
		availProjects.clear();
	}
	
	public WResearchAbility(WViewer viewer1) {
		super(viewer1);
		currProjects = new ArrayList();
		availProjects = new ArrayList();
		randGen = new Random();
	}
	
	private void FillUpProjects() {
		while(getNewProject()){}
	}
	
	public void UnitKilled(WUnit owner) {
		ReturnProjects(owner.getSide());
	}
	
	
	
	private boolean getNewProject() {
		if(availProjects.size()>=NUM_PROJECTS) return false;
		ArrayList projectList;
		if(viewer.whoseTurn()==WUnit.RED_TEAM) {
			projectList = availProjRed;
		} else {
			projectList = availProjBlue;
		}
		if(projectList.size()==0) return false;
		int pos = randGen.nextInt(projectList.size());
		WTechnology wt = (WTechnology) projectList.remove(pos);
		availProjects.add(wt);
		return true;
	}
	
	public void DoSpecial(int row, int col) {
		new WResearchDialog(row, col, viewer.getCanvas(), availProjects);
		//dialog box here
		//should have:
		//1. Research new technology (can exit back to MAIN viewer)
		//2. View current projects (can exit back to MAIN viewer)
		//3 Exit
	}
	
	protected WSpecialAbility getCopy() {
		return new WResearchAbility(viewer);
	}

	public void DoTurnEnter() {
		FillUpProjects();
		Iterator it = currProjects.iterator();
		while(it.hasNext()) {
			WTechnology wt = (WTechnology) it.next();
			if(wt.newTurnStarted()) {
				it.remove();
			}
		}
	}
	
	
	
}
