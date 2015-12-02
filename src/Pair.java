package project4;

import java.util.*;

public class Pair {
	
	public RavensFigure fig1;
	public RavensFigure fig2;
	private int score;
	private HashMap<Integer, HashMap<String, String>> scoreMap;
	private String diffNumObjects;
	private ArrayList<Integer> angleList;
	
	public Pair(RavensFigure fig1, RavensFigure fig2) {
		this.fig1 = fig1;
		this.fig2 = fig2;
		scoreMap = new HashMap<Integer, HashMap<String, String>>();
		angleList = new ArrayList<Integer>();
		this.setUpScoreMap();
	}
	
	public void setUpScoreMap() {
		this.getDiffNumObjects();
		int lowerBound;
		if(diffNumObjects.equals("same")) {
			for(int index = 0; index < fig1.getObjects().size(); index++) {
				ObjectPair currPair = new ObjectPair(fig1.getObjects().get(index), fig2.getObjects().get(index));
				HashMap<String, String> currDiffMap = currPair.getDiffMap();
				scoreMap.put(index, currDiffMap);
			}
		} else if(diffNumObjects.equals("add")) {
			lowerBound = fig1.getObjects().size();
			for(int index = 0; index < lowerBound; index++) {
				ObjectPair currPair = new ObjectPair(fig1.getObjects().get(index), fig2.getObjects().get(index));
				HashMap<String, String> currDiffMap = currPair.getDiffMap();
				scoreMap.put(index, currDiffMap);
			}
			while(lowerBound < fig2.getObjects().size()) {
				RavensObject obj = fig2.getObjects().get(lowerBound);
				HashMap<String, String> currDiffMap = new HashMap<String, String>();
				for(RavensAttribute currAttr: obj.getAttributes()) {
					String attrName = currAttr.getName();
					String diffValue = "to" + currAttr.getValue();
					currDiffMap.put(attrName, diffValue);
				}
				scoreMap.put(lowerBound, currDiffMap);
				lowerBound++;
			}
		} else { //an object has been lost, fig1 size is bigger
			lowerBound = fig2.getObjects().size();
			for(int index = 0; index < lowerBound; index++) {
				ObjectPair currPair = new ObjectPair(fig1.getObjects().get(index), fig2.getObjects().get(index));
				HashMap<String, String> currDiffMap = currPair.getDiffMap();
				scoreMap.put(index, currDiffMap);
			}
			while(lowerBound < fig1.getObjects().size()) {
				RavensObject obj = fig1.getObjects().get(lowerBound);
				HashMap<String, String> currDiffMap = new HashMap<String, String>();
				for(RavensAttribute currAttr: obj.getAttributes()) {
					String attrName = currAttr.getName();
					String diffValue = currAttr.getValue();
					currDiffMap.put(attrName, diffValue);
				}
				scoreMap.put(lowerBound, currDiffMap);
				lowerBound++;
			}
		}
				
	}
	
	
	public HashMap<Integer, HashMap<String, String>> getScoreMap() {
		return scoreMap;
	}
	
	public String getDiffNumObjects() {
		int fig1Objects = fig1.getObjects().size();
		int fig2Objects = fig2.getObjects().size();
		ArrayList<String> sizeList = new ArrayList<String>();
		if(fig1Objects == fig2Objects) {
			diffNumObjects = "same";
		} else if(fig1Objects < fig2Objects) {
			diffNumObjects = "add";
		} else {
			diffNumObjects = "remove";
		}
		return diffNumObjects;
	}
	
	public int getFillDifference() {
		int score = 0;
		int lowerBound = Math.min(fig1.getObjects().size(), fig2.getObjects().size());
		for(int i = 0; i < lowerBound; i++) {
			RavensObject obj1 = fig1.getObjects().get(i);
			RavensObject obj2 = fig2.getObjects().get(i);
			ObjectPair currPair = new ObjectPair(obj1, obj2);
			if(currPair.sameFill()) {
				score++;
			}
		}
		return score;
	}
	
	public int getSizeDifference() {
		int score = 0;
		int lowerBound = Math.min(fig1.getObjects().size(), fig2.getObjects().size());
		for(int i = 0; i < lowerBound; i++) {
			RavensObject obj1 = fig1.getObjects().get(i);
			RavensObject obj2 = fig2.getObjects().get(i);
			ObjectPair currPair = new ObjectPair(obj1, obj2);
			if(currPair.sameSize()) {
				score++;
			}
		}
		return score;
	}
	
	public ArrayList<Integer> angleList() {
		int lowerBound = Math.min(fig1.getObjects().size(), fig2.getObjects().size());
		for(int i = 0; i < lowerBound; i++) {
			RavensObject obj1 = fig1.getObjects().get(i);
			RavensObject obj2 = fig2.getObjects().get(i);
			ObjectPair currPair = new ObjectPair(obj1, obj2);
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
		return true;
	}
}
