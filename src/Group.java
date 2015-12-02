package project4;

import java.util.*;
import project4.ObjectPair;

public class Group {
	
	public RavensFigure fig1;
	public RavensFigure fig2;
	public RavensFigure fig3;
	private int score;
	private HashMap<Integer, HashMap<String, String>> scoreMap;
	private String diffNumObjects;
	private ArrayList<Integer> angleList;

	
	public Group(RavensFigure fig1, RavensFigure fig2, RavensFigure fig3) {
		this.fig1 = fig1;
		this.fig2 = fig2;
		this.fig3 = fig3;
		scoreMap = new HashMap<Integer, HashMap<String, String>>();
		angleList = new ArrayList<Integer>();
		this.setUpScoreMap();
		
    	
	}
	
	public void setUpScoreMap() {
		this.getDiffNumObjects();
		int lowerBound = 0;
		if(diffNumObjects.equals("same")) {
			for(int index = 0; index < fig1.getObjects().size(); index++) {
				ObjectGroup currGroup = new ObjectGroup(fig1.getObjects().get(index), fig2.getObjects().get(index), 
						fig3.getObjects().get(index));
				HashMap<String, String> currDiffMap = currGroup.getDiffMap();
				scoreMap.put(index, currDiffMap);
			}
		} else {
			int fig1Size = fig1.getObjects().size();
			int fig2Size = fig2.getObjects().size();
			int fig3Size = fig3.getObjects().size();
			ArrayList<Integer> sizeList = new ArrayList<Integer>();
			sizeList.add(fig1Size);
			sizeList.add(fig2Size);
			sizeList.add(fig3Size);
			Collections.sort(sizeList);
			lowerBound = sizeList.get(0);
			for(int index = 0; index < lowerBound; index++) {
				ObjectGroup currGroup = new ObjectGroup(fig1.getObjects().get(index), fig2.getObjects().get(index),
						fig3.getObjects().get(index));
				HashMap<String, String> currDiffMap = currGroup.getDiffMap();
				scoreMap.put(index, currDiffMap);
			}
			if(lowerBound == fig1Size) {
				int newLowerBound = Math.min(fig2Size, fig3Size);
				while(lowerBound < newLowerBound) {
					RavensObject obj = fig2.getObjects().get(lowerBound);
					RavensObject obj2 = fig3.getObjects().get(lowerBound);
					ObjectPair currPair = new ObjectPair(obj, obj2);
					HashMap<String, String> currDiffMap = currPair.getDiffMap();
					scoreMap.put(lowerBound, currDiffMap);
					lowerBound++;
				}
			}
			else if(lowerBound == fig2Size) {
				int newLowerBound = Math.min(fig1Size, fig3Size);
				while(lowerBound < newLowerBound) {
					RavensObject obj = fig1.getObjects().get(lowerBound);
					RavensObject obj2 = fig3.getObjects().get(lowerBound);
					ObjectPair currPair = new ObjectPair(obj, obj2);
					HashMap<String, String> currDiffMap = currPair.getDiffMap();
					scoreMap.put(lowerBound, currDiffMap);
					lowerBound++;
				}
			}
			else { //lowerBound == fig3Size
				int newLowerBound = Math.min(fig1Size, fig2Size);
				while(lowerBound < newLowerBound) {
					RavensObject obj = fig1.getObjects().get(lowerBound);
					RavensObject obj2 = fig2.getObjects().get(lowerBound);
					ObjectPair currPair = new ObjectPair(obj, obj2);
					HashMap<String, String> currDiffMap = currPair.getDiffMap();
					scoreMap.put(lowerBound, currDiffMap);
					lowerBound++;
				}
			}
		}
	}
	
	
	public HashMap<Integer, HashMap<String, String>> getScoreMap() {
		return scoreMap;
	}
	
	public String getDiffNumObjects() {
		int fig1Objects = fig1.getObjects().size();
		int fig2Objects = fig2.getObjects().size();
		int fig3Objects = fig3.getObjects().size();
		if(fig1Objects == fig2Objects && fig1Objects == fig3Objects) {
			diffNumObjects = "same";
		} 
		else if(fig1Objects < fig2Objects && fig2Objects < fig3Objects) {
			diffNumObjects = "add";
		}
		else {
			diffNumObjects = "remove";
		}
		return diffNumObjects;
	}
	
	public boolean getDiffNumObjectsSpecific() {
		int fig1Objects = fig1.getObjects().size();
		int fig2Objects = fig2.getObjects().size();
		int fig3Objects = fig3.getObjects().size();
		int diff1 = Math.abs(fig1Objects - fig2Objects);
		int diff2 = Math.abs(fig2Objects - fig3Objects);
		if(diff1 == diff2) {
			return true;
		} else {
			return false;
		}
	}
	
	
	public int getFillDifference() {
		int score = 0;
		int lowerBound = Math.min(fig1.getObjects().size(), Math.min(fig2.getObjects().size(), fig3.getObjects().size()));
		for(int i = 0; i < lowerBound; i++) {
			RavensObject obj1 = fig1.getObjects().get(i);
			RavensObject obj2 = fig2.getObjects().get(i);
			RavensObject obj3 = fig3.getObjects().get(i);
			ObjectGroup currPair = new ObjectGroup(obj1, obj2, obj3);
			if(currPair.sameFill()) {
				score++;
			}
		}
		return score;
	}
	
	public int getSizeDifference() {
		int score = 0;
		int lowerBound = Math.min(fig1.getObjects().size(), Math.min(fig2.getObjects().size(), fig3.getObjects().size()));
		for(int i = 0; i < lowerBound; i++) {
			RavensObject obj1 = fig1.getObjects().get(i);
			RavensObject obj2 = fig2.getObjects().get(i);
			RavensObject obj3 = fig3.getObjects().get(i);
			ObjectGroup currPair = new ObjectGroup(obj1, obj2, obj3);
			if(currPair.sameSize()) {
				score++;
			}
		}
		return score;
	}
	
	public ArrayList<Integer> angleList() {
		int lowerBound = Math.min(fig1.getObjects().size(), Math.min(fig2.getObjects().size(), fig3.getObjects().size()));
		for(int i = 0; i < lowerBound; i++) {
			RavensObject obj1 = fig1.getObjects().get(i);
			RavensObject obj2 = fig2.getObjects().get(i);
			RavensObject obj3 = fig3.getObjects().get(i);
			ObjectGroup currPair = new ObjectGroup(obj1, obj2, obj3);
			ArrayList<Integer> currList = currPair.angleList();
			for(Integer curr: currList) {
				angleList.add(curr);
			}
		}
		return angleList;
	}
	
	public boolean shapeWithNoAngle() {
		ArrayList<RavensObject> obj1List = fig1.getObjects();
		for(int i = 0; i < obj1List.size(); i++) {
			RavensObject obj1 = fig1.getObjects().get(i);
			ArrayList<RavensAttribute> attrList = obj1.getAttributes();
			for(RavensAttribute attr: attrList) {
				if(attr.getName().equals("angle")) {
					if(Integer.parseInt(attr.getValue()) != 0) {
						return false;
					}
				}
			}
		}
		ArrayList<RavensObject> obj2List = fig2.getObjects();
		for(int i = 0; i < obj2List.size(); i++) {
			RavensObject obj2 = fig2.getObjects().get(i);
			ArrayList<RavensAttribute> attrList = obj2.getAttributes();
			for(RavensAttribute attr: attrList) {
				if(attr.getName().equals("angle")) {
					if(Integer.parseInt(attr.getValue()) != 0) {
						return false;
					}
				}
			}
		}
		ArrayList<RavensObject> obj3List = fig3.getObjects();
		for(int i = 0; i < obj3List.size(); i++) {
			RavensObject obj3 = fig3.getObjects().get(i);
			ArrayList<RavensAttribute> attrList = obj3.getAttributes();
			for(RavensAttribute attr: attrList) {
				if(attr.getName().equals("angle")) {
					if(Integer.parseInt(attr.getValue()) != 0) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public boolean sizeIncrease(int index) {
		if(!(fig1.getObjects().size() > index && fig2.getObjects().size() > index 
				&& fig3.getObjects().size() > index)) {
			return false;
		}
		RavensObject obj1 = fig1.getObjects().get(index);
		RavensObject obj2 = fig2.getObjects().get(index);
		RavensObject obj3 = fig3.getObjects().get(index);
		ObjectGroup group1 = new ObjectGroup(obj1, obj2, obj3);
		if(group1.sizeIncrease()) {
			return true;
		}
		return false;
	}
	
	public boolean sizeDecrease(int index) {
		RavensObject obj1 = fig1.getObjects().get(index);
		RavensObject obj2 = fig2.getObjects().get(index);
		RavensObject obj3 = fig3.getObjects().get(index);
		ObjectGroup group1 = new ObjectGroup(obj1, obj2, obj3);
		if(group1.sizeDecrease()) {
			return true;
		}
		return false;
	}
	
	public boolean sameShape(int index) {
		if(!(fig1.getObjects().size() > index && fig2.getObjects().size() > index 
				&& fig3.getObjects().size() > index)) {
			return false;
		}
		RavensObject obj1 = fig1.getObjects().get(index);
		RavensObject obj2 = fig2.getObjects().get(index);
		RavensObject obj3 = fig3.getObjects().get(index);
		ObjectGroup group1 = new ObjectGroup(obj1, obj2, obj3);
		if(group1.sameShape()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean directionAlt(int index) {
		if(!(fig1.getObjects().size() > index && fig2.getObjects().size() > index 
				&& fig3.getObjects().size() > index)) {
			return false;
		}
		RavensObject obj1 = fig1.getObjects().get(index);
		RavensObject obj2 = fig2.getObjects().get(index);
		RavensObject obj3 = fig3.getObjects().get(index);
		ObjectGroup group1 = new ObjectGroup(obj1, obj2, obj3);
		if(group1.directionAlt()) {
			return true;
		} else {
			return false;
		}
	}
	
	
}
