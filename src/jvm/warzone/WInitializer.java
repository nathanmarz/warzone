package warzone;

import java.util.*;

public class WInitializer {
	
	//change special abilities...
	public static void Initialize(WResource database, WViewer viewer) {
		database.addUnitType(WTerrain.TREE_NAME, "tree.PNG", null);
		database.addUnitType(WTerrain.BOULDER_NAME, "rock.PNG", null);
		
		database.addUnitType(WUnit.SOLDIER, "soldier.PNG", new WUnitModel(WUnit.SOLDIER, 2,1,1,1, null, true));
		database.addUnitType(WUnit.BAR_SOLDIER, "soldier.PNG", new WUnitModel(WUnit.BAR_SOLDIER,2,2,2,2,null, true));
		
		ArrayList possUnits = new ArrayList();
		possUnits.add(new WStoreItem(WUnit.RES_FARM, 0));
		possUnits.add(new WStoreItem(WUnit.BARRACKS, 0));
		possUnits.add(new WStoreItem(WUnit.FACTORY, 0));
		possUnits.add(new WStoreItem(WUnit.TURRET, 5));
		possUnits.add(new WStoreItem(WUnit.ACADEMY, 20));
		possUnits.add(new WStoreItem(WUnit.MISSILE_BASE, 70));
		WSettlerSpecial ss = new WSettlerSpecial(viewer, possUnits);
		ss.setDescription("Transforms into any building, for a cost.");
		
		
		ArrayList possUnitsFact = new ArrayList();
		//possUnitsFact.add(new WStoreItem(WUnit.SOLDIER, 5));
		possUnitsFact.add(new WStoreItem(WUnit.SETTLER, 15));
		possUnitsFact.add(new WStoreItem(WUnit.JEEP, 10));
		possUnitsFact.add(new WStoreItem(WUnit.LIGHT_TANK, 15));
		possUnitsFact.add(new WStoreItem(WUnit.MEDIUM_TANK, 25));
		possUnitsFact.add(new WStoreItem(WUnit.HEAVY_TANK, 35));
		possUnitsFact.add(new WStoreItem(WUnit.ARTILLERY, 40));
		possUnitsFact.add(new WStoreItem(WUnit.DEFENDER, 40));
		possUnitsFact.add(new WStoreItem(WUnit.RANGED_ARTILLERY, 45));
		possUnitsFact.add(new WStoreItem(WUnit.MISSILE, 50));
		possUnitsFact.add(new WStoreItem(WUnit.TRANSPORT_HELICOPTER, 75));
		WSpecialFactory wsf = new WSpecialFactory(viewer, possUnitsFact);
		wsf.setDescription("Produces settlers and vehicle units, for a cost.");
		
		ArrayList possUnitsBarr = new ArrayList();
		possUnitsBarr.add(new WStoreItem(WUnit.BAR_SOLDIER, 5));
		possUnitsBarr.add(new WStoreItem(WUnit.GRENADIER, 7));
		possUnitsBarr.add(new WStoreItem(WUnit.SETTLER, 10));
		WSpecialFactory wsbarr = new WSpecialFactory(viewer, possUnitsBarr);
		wsbarr.setDescription("Produces settlers and infantry, for a cost.");
		
		ArrayList ranger = new ArrayList();
		ranger.add(WViewer.UNIT);
		WArtillerySpecial rarts = new WArtillerySpecial(viewer, 2, ranger);
		rarts.setDescription("Has ranged attack of radius 2 that can inflict one point of damage per move point.");
		
		WArtillerySpecial garts = new WArtillerySpecial(viewer, 1, ranger);
		garts.setDescription("Has ranged attack of radius 1 that can inflict one point of damage per move point.");
		database.addUnitType(WUnit.GRENADIER, "grenadier.PNG", new WUnitModel(WUnit.GRENADIER, 2, 1, 1, 2, garts, true));
		
		ArrayList missRanger = new ArrayList();
		missRanger.add(WViewer.UNIT);
		missRanger.add(WViewer.TERRAIN);
		WMissileSpecial missS = new WMissileSpecial(viewer, 3, missRanger);
		missS.setDescription("Can sacrifice self to destroy anything within radius 3.");
		WMissileBaseSpecial missBaseS = new WMissileBaseSpecial(viewer, 3, missRanger, 10);
		missBaseS.setDescription("Can destroy anything within radius 3 for a cost of 10 resources.");
		
		database.addUnitType(WUnit.RANGED_ARTILLERY, "rangedArtillery.PNG", new WUnitModel(WUnit.RANGED_ARTILLERY,4,1,1,3,rarts, true));
		database.addUnitType(WUnit.TURRET, "turret.PNG", new WUnitModel(WUnit.TURRET,6,0,7,2,rarts, false));
		database.addUnitType(WUnit.MISSILE_BASE, "missileBase.PNG", new WUnitModel(WUnit.MISSILE_BASE,4,0,1,1,missBaseS, false));
		database.addUnitType(WUnit.MISSILE, "missile.PNG", new WUnitModel(WUnit.MISSILE,3,0,1,1,missS, true));
		
		database.addUnitType(WUnit.SETTLER, "settler.PNG", new WUnitModel(WUnit.SETTLER, 2,0,0,1, ss, true));
		database.addUnitType(WUnit.FACTORY, "factory.PNG", new WUnitModel(WUnit.FACTORY, 6,0,1,1, wsf, false));
		database.addUnitType(WUnit.BARRACKS, "barracks.PNG", new WUnitModel(WUnit.BARRACKS, 4,0,1,3, wsbarr, false));
		
		
		database.addUnitType(WUnit.JEEP, "jeep.PNG", new WUnitModel(WUnit.JEEP,3,2,1,4, null, true));
		
		database.addUnitType(WUnit.LIGHT_TANK, "lightTank.PNG", new WUnitModel(WUnit.LIGHT_TANK, 4,4,3,3, null, true));
		database.addUnitType(WUnit.MEDIUM_TANK, "mediumTank.PNG", new WUnitModel(WUnit.MEDIUM_TANK, 6,7,7,2, null, true));
		database.addUnitType(WUnit.HEAVY_TANK, "heavyTank.PNG", new WUnitModel(WUnit.HEAVY_TANK, 8,13,13,1, null, true));
	
		database.addUnitType(WUnit.ARTILLERY, "artillery.PNG", new WUnitModel(WUnit.ARTILLERY, 6,30,3,2, null, true));
		
		
		database.addUnitType(WUnit.DEFENDER, "defender.PNG", new WUnitModel(WUnit.DEFENDER, 8,3,20,3, null, true));
		
		
		ArrayList thNAllowed = new ArrayList();
		thNAllowed.add(WUnit.TRANSPORT_HELICOPTER);
		thNAllowed.add(WUnit.MISSILE);
		WTransportAbility wtha = new WTransportAbility(viewer, null, thNAllowed, 5);
		wtha.setDescription("Can transport up to 5 units (other than helicopters and missiles).");
		
		database.addUnitType(WUnit.TRANSPORT_HELICOPTER, "transportHelicopter.PNG", new WUnitModel(WUnit.TRANSPORT_HELICOPTER, 6,0,3,8, wtha, true));
	
		
		WResearchAbility wra = new WResearchAbility(viewer);
		wra.setDescription("Can research new technology and upgrades.");
		database.addUnitType(WUnit.ACADEMY, "academy.PNG", new WUnitModel(WUnit.ACADEMY,6,0,1,0,wra,false));
		
		
		WSpecialFarm wsfarm = new WSpecialFarm(viewer);
		wsfarm.setDescription("Produces 3 resources per turn.");
		database.addUnitType(WUnit.RES_FARM, "resourceFarm.PNG", new WUnitModel(WUnit.RES_FARM, 2, 0, 1, 0, wsfarm, false));
	}
}
