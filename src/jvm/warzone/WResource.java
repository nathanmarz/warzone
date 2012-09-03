package warzone;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;

import java.util.*;

public class WResource {
	private HashMap picturesRed;
	private HashMap picturesBlue;
	private HashMap unitModelsRed;
	private HashMap unitModelsBlue;
	private int unitSize;
	
	public WResource(int unitSize1) {
		unitSize = unitSize1;
		picturesRed = new HashMap();
		picturesBlue = new HashMap();
		unitModelsRed = new HashMap();
		unitModelsBlue = new HashMap();
	}
	
	public Image getUnitPic(String name, int team) {
		HashMap pictures;
		if(team==WUnit.RED_TEAM) {
			pictures = picturesRed;
		} else {
			pictures = picturesBlue;
		}
		return (Image) pictures.get(name.toLowerCase());
	}
	
	public WUnitModel getUnitModel(String name, int team) {
		HashMap unitModels;
		if(team==WUnit.RED_TEAM) {
			unitModels = unitModelsRed;
		} else {
			unitModels = unitModelsBlue;
		}
		return (WUnitModel) unitModels.get(name.toLowerCase());
	}
	
	public void addUnitType(String name, String fileImage2, WUnitModel model) {
		addUnitType(name, fileImage2, model, WUnit.RED_TEAM);
		addUnitType(name, fileImage2, model, WUnit.BLUE_TEAM);
	}
	
	public Image getTerrainPic(String name) {
		return getUnitPic(name, WUnit.RED_TEAM); //team doesn't matter
	}
	
	//for both teams
	public void addUnitType(String name, String fileImage2, WUnitModel model, int team) {
		HashMap unitModels;
		HashMap pictures;
		if(team==WUnit.RED_TEAM) {
			unitModels = unitModelsRed;
			pictures = picturesRed;
		} else {
			unitModels = unitModelsBlue;
			pictures = picturesBlue;
		}
		
		
		String fileImage = "Resources/" + fileImage2;
		unitModels.put(name.toLowerCase(), model);
		File file = new File(fileImage);
		BufferedImage image = null;
		try{
			image = ImageIO.read(file);
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Image file not found!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
		Image toStore = image.getScaledInstance(unitSize, unitSize, Image.SCALE_SMOOTH);
		pictures.put(name.toLowerCase(), toStore);
		
	}
}
