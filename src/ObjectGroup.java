package project4;

//helper class to easily compare to objects

import java.util.*;
import project4.RavensObject;

public class ObjectGroup {
	
	private RavensObject obj1;
	private RavensObject obj2;
	private RavensObject obj3;
	private HashMap<String, String> attrMap1;
	private HashMap<String, String> attrMap2;
	private HashMap<String, String> attrMap3;
	private ArrayList<RavensAttribute> diffList;	//list of the attribute names that are different
	private HashMap<String, String> diffMap;
	private boolean hasAngle;
	private ArrayList<Integer> angleList;
	public HashMap<String, Integer> sizeMap;

	
	public ObjectGroup(RavensObject obj1, RavensObject obj2, RavensObject obj3) {
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.obj3 = obj3;
		attrMap1 = new HashMap<String, String>();
		attrMap2 = new HashMap<String, String>();
		attrMap3 = new HashMap<String, String>();
		diffList = new ArrayList<RavensAttribute>();
		diffMap = new HashMap<String, String>();
		angleList = new ArrayList<Integer>();
		this.setUpAttributes(obj1, attrMap1);
		this.setUpAttributes(obj2, attrMap2);
		this.setUpAttributes(obj3, attrMap3);
		this.setUpDiffMap();
		sizeMap = new HashMap<String, Integer>();
    	sizeMap.put("very-small", 0);
    	sizeMap.put("small", 1);
    	sizeMap.put("medium", 2);
    	sizeMap.put("large", 3);
    	sizeMap.put("very-large", 4);
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
		//Checking now all of obj1 attributes to see the differences between obj2 attributes
		for(int j = 0; j < obj1.getAttributes().size(); j++) {
			RavensAttribute attr1 = obj1.getAttributes().get(j);
			String attr1Name = attr1.getName();
			String attr1Value = attr1.getValue();
			if(!attrMap2.containsKey(attr1Name) || !attrMap3.containsKey(attr1Name)) {	//Scenario where Object1 attrMap doesn't contain the category of attr1
				if(!diffList.contains(attr1)) {
					diffList.add(attr1);
				}
			} else {	//Scenario where Object1 Map does contain it, but they have differing values
				String attr2Value = attrMap2.get(attr1Name);
				String attr3Value = attrMap3.get(attr1Name);
				if(!attr2Value.equals(attr1Value) || !attr3Value.equals(attr1Value)) {
					if(!diffList.contains(attr1)) {
						diffList.add(attr1);
					}
				}
			}
		}
		
		for(int i = 0; i < obj2.getAttributes().size(); i++) {
			RavensAttribute attr2 = obj2.getAttributes().get(i);
			String attr2Name = obj2.getAttributes().get(i).getName();
			String attr2Value = obj2.getAttributes().get(i).getValue();
			if(!attrMap1.containsKey(attr2Name) || !attrMap3.containsKey(attr2Name)) {	//Scenario where Object1 attrMap doesn't contain the category of attr2
				if(!diffList.contains(attr2)) {
					diffList.add(attr2);
				}
			} else {	//Scenario where Object1 Map does contain it, but they have differing values
				String attr1Value = attrMap1.get(attr2Name);
				String attr3Value = attrMap3.get(attr2Name);
				if(!attr1Value.equals(attr2Value) || !attr3Value.equals(attr2Value)) {
					if(!diffList.contains(attr2)) {
						diffList.add(attr2);
					}
				}
			}
		}
		
		for(int i = 0; i < obj3.getAttributes().size(); i++) {
			RavensAttribute attr3 = obj3.getAttributes().get(i);
			String attr3Name = obj3.getAttributes().get(i).getName();
			String attr3Value = obj3.getAttributes().get(i).getValue();
			if(!attrMap1.containsKey(attr3Name) || !attrMap2.containsKey(attr3Name)) {	//Scenario where Object1 attrMap doesn't contain the category of attr2
				if(!diffList.contains(attr3)) {
					diffList.add(attr3);
				}
			} else {	//Scenario where Object1 Map does contain it, but they have differing values
				String attr1Value = attrMap1.get(attr3Name);
				String attr2Value = attrMap2.get(attr3Name);
				if(!attr1Value.equals(attr3Value) || !attr2Value.equals(attr3Value)) {
					if(!diffList.contains(attr3)) {
						diffList.add(attr3);
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
			int index3 = obj3.getAttributes().indexOf(attr);
			if(index1 == -1) { //not in map1
				if(index2 == -1) { //in map 3
					String value = obj3.getAttributes().get(index3).getValue();
					String diff = "to" + value;
					diffMap.put(attr.getName(), diff);
				} else {	//in map 2
					String value = obj2.getAttributes().get(index2).getValue();
					String diff = "to" + value;
					diffMap.put(attr.getName(), diff);
				}
			} else if(index2 == - 1) { //not in map2
				if(index3 == -1) {	//in map 1
					String value = obj1.getAttributes().get(index1).getValue();
					String diff = "to" + value;
					diffMap.put(attr.getName(), diff);
				} else {	//in map3
					String value = obj3.getAttributes().get(index3).getValue();
					String diff = "to" + value;
					diffMap.put(attr.getName(), diff);
				}
			} else if(index3 == -1){	//not in map3
				if(index2 == -1) { //in map 1
					String value = obj1.getAttributes().get(index1).getValue();
					String diff = "to" + value;
					diffMap.put(attr.getName(), diff);
				} else { //in map 2
					String value = obj2.getAttributes().get(index2).getValue();
					String diff = "to" + value;
					diffMap.put(attr.getName(), diff);
				}
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
			String obj1Angle = attrMap1.get("angle");
			String obj2Angle = attrMap2.get("angle");
			String obj3Angle = attrMap3.get("angle");
			ArrayList<String> angleList = new ArrayList<String>();
			angleList.add(obj1Angle);
			angleList.add(obj2Angle);
			angleList.add(obj3Angle);
			for(String angle: angleList) {
				if(angle == null || Integer.parseInt(angle) == 0) {
					continue;
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
			String obj3Angle = attrMap3.get("angle");
			ArrayList<String> currList = new ArrayList<String>();
			currList.add(obj1Angle);
			currList.add(obj2Angle);
			currList.add(obj3Angle);
			ArrayList<String> tempList = new ArrayList<String>();
			int index = 0;
			for(String angle: currList) {
				if(angle != null) {
					tempList.add(angle);
					angleList.add(Integer.parseInt(angle));
				} 
			}
			if(tempList.size() == 1) {
				angleDiff = Integer.parseInt(tempList.get(0));
			} 
			else if(tempList.size() == 2) {
				angleDiff = Math.abs(Integer.parseInt(tempList.get(0)) - Integer.parseInt(tempList.get(1)));
			} 
			else {
				int angleDiff1 = Math.abs(Integer.parseInt(tempList.get(0)) - Integer.parseInt(tempList.get(1)));
				int angleDiff2 = Math.abs(Integer.parseInt(tempList.get(0)) - Integer.parseInt(tempList.get(2)));
				int angleDiff3 = Math.abs(Integer.parseInt(tempList.get(1)) - Integer.parseInt(tempList.get(2)));
				angleDiff = Math.max(angleDiff1, Math.max(angleDiff2, angleDiff3));
			}
		}
		return angleDiff;
	}
	
	public String angleMod() {
		if(getHasAngle()) {
			String obj1Angle = attrMap1.get("angle");
			String obj2Angle = attrMap2.get("angle");
			String obj3Angle = attrMap3.get("angle");
			if(Integer.parseInt(obj1Angle) % 90 == 0 && Integer.parseInt(obj2Angle) % 90 == 0 && Integer.parseInt(obj3Angle) % 90 == 0) {
				return "full-turn";
			}  else if(Integer.parseInt(obj1Angle) % 45 == 0 && Integer.parseInt(obj2Angle) % 45 == 0 && 
					Integer.parseInt(obj3Angle) % 45 == 0) {
				return "half-turn";
			}else {
				return "half-turn";
			}
		}
		return null;
	}
	
	public boolean sameShape() {
		if(attrMap1.get("shape") != null && attrMap2.get("shape") != null && attrMap3.get("shape") != null) {
			if(attrMap1.get("shape").equals(attrMap2.get("shape")) && attrMap1.get("shape").equals(attrMap3.get("shape"))) {
				return true;
			}
		} 
		else if(attrMap1.get("shape") == null && attrMap2.get("shape") == null && attrMap3.get("shape") == null) {
			return true;
		} 
		return false;		
	}
	
	public boolean sameFill() {
		if(attrMap1.get("fill") != null && attrMap2.get("fill") != null && attrMap3.get("fill") != null) {
			if(attrMap1.get("fill").equals(attrMap2.get("fill")) && attrMap1.get("fill").equals(attrMap3.get("fill"))) {
				return true;
			}
		} 
		else if(attrMap1.get("fill") == null && attrMap2.get("fill") == null && attrMap3.get("fill") == null) {
			return true;
		} 
		return false;
	}
	
	public boolean sameSize() {
		if(attrMap1.get("size") != null && attrMap2.get("size") != null && attrMap3.get("size") != null) {
			if(attrMap1.get("size").equals(attrMap2.get("size")) && attrMap1.get("size").equals(attrMap3.get("size"))) {
				return true;
			}
		} 
		else if(attrMap1.get("size") == null && attrMap2.get("size") == null && attrMap3.get("size") == null) {
			return true;
		} 
		return false;
	}
	
	public ArrayList<Integer> angleList() {
		this.angleDiff();
		return angleList;
	}
	
	public boolean sizeIncrease() {
		if(attrMap1.get("size") != null && attrMap2.get("size") != null && attrMap3.get("size") != null) {
			String size1 = attrMap1.get("size");
			String size2 = attrMap2.get("size");
			String size3 = attrMap3.get("size");
			if(sizeMap.get(size1) < sizeMap.get(size2) && sizeMap.get(size2) < sizeMap.get(size3)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean sizeDecrease() {
		if(attrMap1.get("size") != null && attrMap2.get("size") != null && attrMap3.get("size") != null) {
			String size1 = attrMap1.get("size");
			String size2 = attrMap2.get("size");
			String size3 = attrMap3.get("size");
			if(sizeMap.get(size1) > sizeMap.get(size2) && sizeMap.get(size2) > sizeMap.get(size3)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean directionAlt() {
		if(attrMap1.get("angle") != null && attrMap2.get("angle") != null && attrMap3.get("angle") != null) {
			String size1 = attrMap1.get("angle");
			String size2 = attrMap2.get("angle");
			String size3 = attrMap3.get("angle");
			if(Integer.parseInt(size1) == 0) {
				if(Integer.parseInt(size2) == 180) {
					if(Integer.parseInt(size3) == 0) {
						return true;
					}
				}
			} else if(Integer.parseInt(size1) == 180){
				if(Integer.parseInt(size2) == 0) {
					if(Integer.parseInt(size3) == 180) {
						return true;
					}
				}
				
			}
		}
		return false;
	}
}
