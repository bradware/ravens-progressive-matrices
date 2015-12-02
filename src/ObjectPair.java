package project4;

//helper class to easily compare to objects

import java.util.*;
import project4.RavensObject;

public class ObjectPair {
	
	private RavensObject obj1;
	private RavensObject obj2;
	private HashMap<String, String> attrMap1;
	private HashMap<String, String> attrMap2;
	private ArrayList<RavensAttribute> diffList;	//list of the attribute names that are different
	private HashMap<String, String> diffMap;
	private boolean hasAngle;
	private ArrayList<Integer> angleList;
	
	public ObjectPair(RavensObject obj1, RavensObject obj2) {
		this.obj1 = obj1;
		this.obj2 = obj2;
		attrMap1 = new HashMap<String, String>();
		attrMap2 = new HashMap<String, String>();
		diffList = new ArrayList<RavensAttribute>();
		diffMap = new HashMap<String, String>();
		angleList = new ArrayList<Integer>();
		this.setUpAttributes(obj1, attrMap1);
		this.setUpAttributes(obj2, attrMap2);
		this.setUpDiffMap();
	}
	
	
	//Helper function to initialize the HashMaps with each of the attributes name and value associated
	private void setUpAttributes(RavensObject obj, HashMap<String, String> attributes) {
		for(int i = 0; i < obj.getAttributes().size(); i++) {
			if(obj.getAttributes().get(i).getName().equals("angle")) {
				hasAngle = true;
			}
			attributes.put(obj.getAttributes().get(i).getName(), obj.getAttributes().get(i).getValue());
		}
	}
	
	public ArrayList<RavensAttribute> compare() {
		int lowerBound = Math.min(obj1.getAttributes().size(), obj2.getAttributes().size());
		for(int i = 0; i < obj2.getAttributes().size(); i++) {
			RavensAttribute attr2 = obj2.getAttributes().get(i);
			String attr2Name = obj2.getAttributes().get(i).getName();
			String attr2Value = obj2.getAttributes().get(i).getValue();
			if(!attrMap1.containsKey(attr2Name)) {	//Scenario where Object1 attrMap doesn't contain the category of attr2
				if(!diffList.contains(attr2)) {
					diffList.add(attr2);
				}
			} else {	//Scenario where Object1 Map does contain it, but they have differing values
				String attr1Value = attrMap1.get(attr2Name);
				if(!attr1Value.equals(attr2Value)) {
					if(!diffList.contains(attr2)) {
						diffList.add(attr2);
					}
				}
			}
		}
		//Checking now all of obj1 attributes to see the differences between obj2 attributes
		for(int j = 0; j < obj1.getAttributes().size(); j++) {
			RavensAttribute attr1 = obj1.getAttributes().get(j);
			String attr1Name = attr1.getName();
			String attr1Value = attr1.getValue();
			if(!attrMap2.containsKey(attr1Name)) {	//Scenario where Object1 attrMap doesn't contain the category of attr1
				if(!diffList.contains(attr1)) {
					diffList.add(attr1);
				}
			} else {	//Scenario where Object1 Map does contain it, but they have differing values
				String attr2Value = attrMap2.get(attr1Name);
				if(!attr2Value.equals(attr1Value)) {
					if(!diffList.contains(attr1)) {
						diffList.add(attr1);
					}
				}
			}
		}
		if(diffList.contains("angle")) {
			hasAngle = true;
		}
		return diffList;
	}
	
	public void setUpDiffMap() {
		this.compare();
		for(RavensAttribute attr: diffList) {
			int index1 = obj1.getAttributes().indexOf(attr);
			int index2 = obj2.getAttributes().indexOf(attr);
			if(index1 == -1) { //not in map1
				String value = obj2.getAttributes().get(index2).getValue();
				String diff = "to" + value;
				diffMap.put(attr.getName(), diff);
			} else if(index2 == - 1) { //not in map2
				String value = obj1.getAttributes().get(index1).getValue();
				String diff = value;
				diffMap.put(attr.getName(), diff);
			} else {
				RavensAttribute attr1 = obj1.getAttributes().get(index1);
				RavensAttribute attr2 = obj1.getAttributes().get(index2);
				AttributePair newAttr = new AttributePair(attr1, attr2);
				diffMap.put(attr.getName(), newAttr.getDiffValue());
			}
		}
	}
	
	public HashMap<String, String> getDiffMap() {
		return diffMap;
	}
	
	public boolean getHasAngle() {
		if(hasAngle) {
			return true;
		} else{
			return false;
		}
	}
	
	public boolean shapeWithNoAngle() {
		if(getHasAngle()) {
			String obj1Angle = attrMap1.get("angle");
			String obj2Angle = attrMap2.get("angle");
			if(Integer.parseInt(obj1Angle) == 0 && Integer.parseInt(obj2Angle) == 0) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
	
	public int angleDiff() {
		int angleDiff = 0;
		if(getHasAngle()) {
			String obj1Angle = attrMap1.get("angle");
			String obj2Angle = attrMap2.get("angle");
			if(obj1Angle == null) {
			 angleList.add(Integer.parseInt(obj2Angle));
			 angleDiff = Math.abs(Integer.parseInt(obj2Angle) - 0);
			} else if(obj2Angle == null) {
				angleList.add(Integer.parseInt(obj1Angle));
				angleDiff = Math.abs(Integer.parseInt(obj1Angle) - 0);
			} else {
				angleList.add(Integer.parseInt(obj1Angle));
				angleList.add(Integer.parseInt(obj2Angle));
				angleDiff = Math.abs(Integer.parseInt(obj1Angle) - Integer.parseInt(obj2Angle));
			}
			
		}
		return angleDiff;
	}
	
	public String angleMod() {
		if(getHasAngle()) {
			String obj1Angle = attrMap1.get("angle");
			String obj2Angle = attrMap2.get("angle");
			if(Integer.parseInt(obj1Angle) % 90 == 0 && Integer.parseInt(obj2Angle) % 90 == 0) {
				return "full-turn";
			}  else if(Integer.parseInt(obj1Angle) % 45 == 0 && Integer.parseInt(obj2Angle) % 45 == 0) {
				return "half-turn";
			}else {
				return "half-turn";
			}
		}
		return null;
	}
	
	public boolean sameShape() {
		if(attrMap1.get("shape") != null && attrMap2.get("shape") != null
				&& attrMap1.get("shape").equals(attrMap2.get("shape"))) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean sameFill() {
		if(attrMap1.get("fill") != null && attrMap2.get("fill") != null
				&& attrMap1.get("fill").equals(attrMap2.get("fill"))) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean sameSize() {
		if(attrMap1.get("size") != null && attrMap2.get("size") != null
				&& attrMap1.get("size").equals(attrMap2.get("size"))) {
			return true;
		} else {
			return false;
		}
	}
	
	public ArrayList<Integer> angleList() {
		this.angleDiff();
		return angleList;
	}
	
}
