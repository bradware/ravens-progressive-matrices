package project4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Problem {
	
	public RavensFigure figA;
	public RavensFigure figB;
	public RavensFigure figC;
	public RavensFigure figD;
	public RavensFigure figE;
	public RavensFigure figF;
	public RavensFigure figG;
	public RavensFigure figH;
	public ArrayList<RavensFigure> figureList; 
	public RavensProblem problem;
	public RavensFigure tempFig;
	
	public Problem(RavensProblem problem, RavensFigure tempFig) {
    	this.problem = problem;
    	this.tempFig = tempFig;
		figA = problem.getFigures().get("A");
    	figB = problem.getFigures().get("B");
    	figC = problem.getFigures().get("C");
    	figD = problem.getFigures().get("D");
    	figE = problem.getFigures().get("E");
    	figF = problem.getFigures().get("F");
    	figG = problem.getFigures().get("G");
    	figH = problem.getFigures().get("H");
    	figureList = new ArrayList<RavensFigure>();
    	figureList.add(figA);
    	figureList.add(figB);
    	figureList.add(figC);
    	figureList.add(figD);
    	figureList.add(figE);
    	figureList.add(figF);
    	figureList.add(figG);
    	figureList.add(figH);
	}
	
	
	
	public boolean shapeLeast(int index) {
		if(!(tempFig.getObjects().size() > index)) {
			return false;
		}
		HashMap<String, Integer> shapeMap = new HashMap<String, Integer>();
    	for(RavensFigure fig: figureList) {
    		if(fig.getObjects().size() > index) {
    			RavensObject obj = fig.getObjects().get(index);
    			for(RavensAttribute attr: obj.getAttributes()) {
    				if(attr.getName().equals("shape")) {
    					if(shapeMap.containsKey(attr.getValue())) {
    						int value = shapeMap.get(attr.getValue());
    						value++;
    						shapeMap.replace(attr.getValue(), value);
    					} else {
    						shapeMap.put(attr.getValue(), 1);
    					}
    				}
    			}
    		}
    	}
    	Set<Map.Entry<String,Integer>> intSet = shapeMap.entrySet();
    	int min = Integer.MAX_VALUE;
    	String search = "";
    	for(Map.Entry<String, Integer> e: intSet) {
    		if(e.getValue() < min) {
    			search = e.getKey();
    			min = e.getValue();
    		}
    	}
    	if(tempFig.getObjects().size() > index) {
    		RavensObject obj = tempFig.getObjects().get(index);
			for(RavensAttribute attr: obj.getAttributes()) {
				if(attr.getName().equals("shape")) {
					if(attr.getValue().equals(search)) {
						return true;
					}
				}
			}
    	}
    	return false;
	}
	
	public boolean sizeLeast(int index) {
		if(!(tempFig.getObjects().size() > index)) {
			return false;
		}
		HashMap<String, Integer> sizeMap = new HashMap<String, Integer>();
    	for(RavensFigure fig: figureList) {
    		if(fig.getObjects().size() > index) {
    			RavensObject obj = fig.getObjects().get(index);
    			for(RavensAttribute attr: obj.getAttributes()) {
    				if(attr.getName().equals("size")) {
    					if(sizeMap.containsKey(attr.getValue())) {
    						int value = sizeMap.get(attr.getValue());
    						value++;
    						sizeMap.replace(attr.getValue(), value);
    					} else {
    						sizeMap.put(attr.getValue(), 1);
    					}
    				}
    			}
    		}
    	}
    	Set<Map.Entry<String,Integer>> intSet = sizeMap.entrySet();
    	int min = Integer.MAX_VALUE;
    	String search = "";
    	for(Map.Entry<String, Integer> e: intSet) {
    		if(e.getValue() < min) {
    			search = e.getKey();
        		min = e.getValue();
    		}
    	}
    	if(tempFig.getObjects().size() > index) {
    		RavensObject obj = tempFig.getObjects().get(index);
			for(RavensAttribute attr: obj.getAttributes()) {
				if(attr.getName().equals("size")) {
					if(attr.getValue().equals(search) || !sizeMap.containsKey(attr.getValue())) {
						return true;
					}
				}
			}
    	}
    	return false;
	}
	
	public boolean angleLeast(int index) {
		if(!(tempFig.getObjects().size() > index)) {
			return false;
		}
		HashMap<String, Integer> sizeMap = new HashMap<String, Integer>();
    	for(RavensFigure fig: figureList) {
    		if(fig.getObjects().size() > index) {
    			RavensObject obj = fig.getObjects().get(index);
    			for(RavensAttribute attr: obj.getAttributes()) {
    				if(attr.getName().equals("angle")) {
    					if(sizeMap.containsKey(attr.getValue())) {
    						int value = sizeMap.get(attr.getValue());
    						value++;
    						sizeMap.replace(attr.getValue(), value);
    					} else {
    						sizeMap.put(attr.getValue(), 1);
    					}
    				}
    			}
    		}
    	}
    	Set<Map.Entry<String,Integer>> intSet = sizeMap.entrySet();
    	int min = Integer.MAX_VALUE;
    	String search = "";
    	for(Map.Entry<String, Integer> e: intSet) {
    		if(e.getValue() < min) {
    			search = e.getKey();
        		min = e.getValue();
    		}
    	}
    	if(tempFig.getObjects().size() > index) {
    		RavensObject obj = tempFig.getObjects().get(index);
			for(RavensAttribute attr: obj.getAttributes()) {
				if(attr.getName().equals("angle")) {
					if(attr.getValue().equals(search)){ //|| !sizeMap.containsKey(attr.getValue())) {
						return true;
					}
				}
			}
    	}
    	return false;
	}
	
	
	public boolean angleNotContained(int index) {
		if(!(tempFig.getObjects().size() > index)) {
			return false;
		}
		HashMap<String, Integer> sizeMap = new HashMap<String, Integer>();
    	for(RavensFigure fig: figureList) {
    		if(fig.getObjects().size() > index) {
    			RavensObject obj = fig.getObjects().get(index);
    			for(RavensAttribute attr: obj.getAttributes()) {
    				if(attr.getName().equals("angle")) {
    					if(sizeMap.containsKey(attr.getValue())) {
    						int value = sizeMap.get(attr.getValue());
    						value++;
    						sizeMap.replace(attr.getValue(), value);
    					} else {
    						sizeMap.put(attr.getValue(), 1);
    					}
    				}
    			}
    		}
    	}
    	if(tempFig.getObjects().size() > index) {
    		RavensObject obj = tempFig.getObjects().get(index);
			for(RavensAttribute attr: obj.getAttributes()) {
				if(attr.getName().equals("angle")) {
					if(!sizeMap.containsKey(attr.getValue())) {
						return true;
					}
				}
			}
    	}
    	return false;
	}
	
	
	public boolean numObjectsIncrease() {
    	Group group1 = new Group(figA, figB, figC);
    	Group group2 = new Group(figD, figE, figF);
    	Group group3 = new Group(figA, figD, figG);
    	Group group4 = new Group(figB, figE, figH);
    	Group tempGroup1 = new Group(figG, figH, tempFig);
    	Group tempGroup2 = new Group(figC, figF, tempFig);
    	String group1Diff = group1.getDiffNumObjects();
    	String group2Diff = group2.getDiffNumObjects();
    	String group3Diff = group3.getDiffNumObjects();
    	String group4Diff = group4.getDiffNumObjects();
    	if(group1Diff.equals(group2Diff) && group3Diff.equals(group4Diff)) {
    		if(tempGroup1.getDiffNumObjects().equals(group1Diff) &&
    				tempGroup2.getDiffNumObjects().equals(group3Diff)) {
    			return true;
    		}
    	}
    	return false;
	}
	
	public boolean numObjectsIncreaseSpecific() {
    	Group tempGroup1 = new Group(figG, figH, tempFig);
    	Group tempGroup2 = new Group(figC, figF, tempFig);
    	if(tempGroup1.getDiffNumObjectsSpecific() && tempGroup2.getDiffNumObjectsSpecific()) {
    		return true;
    	}
    	return false;
	}
	
	public boolean sizeIncrease(int index) {
		if(!(tempFig.getObjects().size() > index)) {
			return false;
		}
		Group group1 = new Group(figA, figB, figC);
    	Group group2 = new Group(figD, figE, figF);
    	Group group3 = new Group(figA, figD, figG);
    	Group group4 = new Group(figB, figE, figH);
    	Group tempGroup1 = new Group(figG, figH, tempFig);
    	Group tempGroup2 = new Group(figC, figF, tempFig);
    	if(group1.sizeIncrease(index) && group2.sizeIncrease(index) 
    			&& group3.sizeIncrease(index) && group4.sizeIncrease(index)) {
    		if(tempGroup1.sizeIncrease(index) && tempGroup2.sizeIncrease(index)) {
    			return true;
    		}
    	}
    	return false;
	}
	
	public boolean sizeDecrease(int index) {
		if(!(tempFig.getObjects().size() > index)) {
			return false;
		}
		Group group1 = new Group(figA, figB, figC);
    	Group group2 = new Group(figD, figE, figF);
    	Group group3 = new Group(figA, figD, figG);
    	Group group4 = new Group(figB, figE, figH);
    	Group tempGroup1 = new Group(figG, figH, tempFig);
    	Group tempGroup2 = new Group(figC, figF, tempFig);
    	if(group1.sizeDecrease(index) && group2.sizeDecrease(index) 
    			&& group3.sizeDecrease(index) && group4.sizeDecrease(index)) {
    		if(tempGroup1.sizeDecrease(index) && tempGroup2.sizeDecrease(index)) {
    			return true;
    		}
    	}
    	return false;
	}
	
	public boolean sameShape(int index) {
		if(!(tempFig.getObjects().size() > index)) {
			return false;
		}
		Group group1 = new Group(figA, figB, figC);
    	Group group2 = new Group(figD, figE, figF);
    	Group group3 = new Group(figA, figD, figG);
    	Group group4 = new Group(figB, figE, figH);
    	if(group1.sameShape(index) && group2.sameShape(index) && group3.sameShape(index)
    			&& group4.sameShape(index)) {
    		return true;
    	}
    	return false;
	}
	
	public boolean directionAlt(int index) {
		Group tempGroup1 = new Group(figG, figH, tempFig);
    	Group tempGroup2 = new Group(figC, figF, tempFig);
    	if(tempGroup1.directionAlt(index) && tempGroup2.directionAlt(index)) {
    		return true;
    	}
    	return false;
	}
	
	
}
