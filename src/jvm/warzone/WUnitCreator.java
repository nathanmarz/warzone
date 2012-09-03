package warzone;

import java.util.*;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;


public abstract class WUnitCreator extends WSpecialAbility {
	public class WUnitDialog extends JDialog {
		private WStoreItem selectedItem;
		private JComboBox selector;
		private JLabel inspHP;
		private JLabel inspAttack;
		private JLabel inspDefend;
		private JLabel inspMoves;
		private JTextArea inspSpecial;
		private JScrollPane scroller;
		
		public WUnitDialog(int row, int col, WCanvas canvas) {
			super((JFrame)viewer.getRootPane().getParent(), "Choose...", true);
			JFrame par = (JFrame)viewer.getRootPane().getParent();
			selectedItem = null;
			setLocation(par.getX()+canvas.getX()+col*canvas.getGridSize(), par.getY()+canvas.getY()+row*canvas.getGridSize());
			JPanel content = new JPanel();
			content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
			setContentPane(content);
			
		
			
			selector = new JComboBox(possibleUnits.toArray());
			content.add(selector);
			selector.setRenderer(new WComboBoxRenderer(viewer.getDatabase(),viewer));
			selector.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0) {
					ShowSelectedStats();
				}
				
			});
			
			
			JButton accept = new JButton("Accept");
			accept.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					selectedItem = ((WStoreItem) selector.getSelectedItem());
					WUnitDialog.this.dispose();
				}
				
			});
			content.add(accept);
			
			JButton reject = new JButton("Cancel");
			reject.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WUnitDialog.this.dispose();
				}
				
			});
			content.add(reject);
			
			accept.setAlignmentX((float) 0.5);
			reject.setAlignmentX((float) 0.5);
			
			JPanel stats = new JPanel();
			stats.setLayout(new BoxLayout(stats,BoxLayout.Y_AXIS));
			content.add(stats);
			
			
			JLabel buffer = new JLabel("   ");
			content.add(buffer);
			
			JLabel sd = new JLabel("Special:");
			sd.setAlignmentX((float) 0.5);
			content.add(sd);
			
			
			inspSpecial = new JTextArea();
			inspSpecial.setLineWrap(true);
			inspSpecial.setWrapStyleWord(true);
			inspSpecial.setBackground(content.getBackground());
			inspSpecial.setDisabledTextColor(Color.black);
			inspSpecial.setRows(5);
			
			inspSpecial.disable();
			scroller = new JScrollPane(inspSpecial);
			content.add(scroller);
			
			
//			JTextPane pane = new JTextPane();
//			pane.setText("this is a very very very very long message for special ability");
//			pane.disable();
//			pane.setMaximumSize(new Dimension(80,30));
//			pane.resize(new Dimension(30,1000));
//			selAndStats.add(pane);
			
			inspHP = new JLabel();
			inspAttack = new JLabel();
			inspDefend = new JLabel();
			inspMoves = new JLabel();
			stats.add(inspHP);
			stats.add(inspAttack);
			stats.add(inspDefend);
			stats.add(inspMoves);
			stats.setAlignmentX((float)0.5);
			//stats.setAlignmentY(-1);
			
			ShowSelectedStats();
			pack();
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setResizable(false);
			setVisible(true);
			
		}
		
		private void ShowSelectedStats() {
			WStoreItem wsi = (WStoreItem) selector.getSelectedItem();
			String unit = wsi.getUnit();
			WUnitModel model = viewer.getDatabase().getUnitModel(unit,viewer.whoseTurn());
			inspHP.setText("HP: " + model.getHP());
			inspAttack.setText("Attack: " + model.getAttack());
			inspDefend.setText("Defend: " + model.getDefend());
			inspMoves.setText("Moves: " + model.getMoves());
			WSpecialAbility wsa = model.getSpecial();
			if(wsa==null) {
				inspSpecial.setText("              None");
			} else {
				inspSpecial.setText(wsa.getDescription());
			}
			//this.pack();
		}
		
		public WStoreItem selection() {return selectedItem; }
	}
	
	
	protected ArrayList possibleUnits;
	
	public WUnitCreator(WViewer viewer1, ArrayList possibleUnits1) {
		super(viewer1);
		possibleUnits = possibleUnits1;
	}
	
	
	public void DoSpecial(int row, int col) {
		WStoreItem unitToPlace = chooseUnit(row, col);
		if(unitToPlace==null) return;
		WPlayer currPlayer = viewer.getCurrentPlayer();
		if(unitToPlace.getPrice()==0||currPlayer.getResources()>=unitToPlace.getPrice()) {
			PlaceUnit(row,col,unitToPlace);
		} else {
			JOptionPane.showMessageDialog(viewer, "Not enough resources!", "Production failed", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//resources already checked
	protected abstract void PlaceUnit(int row, int col, WStoreItem unitToPlace);
	
	//input specify location for pop-up dialog
	private WStoreItem chooseUnit(int row, int col) {
		WUnitDialog selector = new WUnitDialog(row,col,viewer.getCanvas());
		return selector.selection();
	}

}
