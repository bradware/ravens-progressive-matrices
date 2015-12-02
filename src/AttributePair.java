package project4;

public class AttributePair {
	
	private RavensAttribute attr1;
	private RavensAttribute attr2;
	private String index;
	private String diffValue;
	
	public AttributePair(RavensAttribute attr1, RavensAttribute attr2) {
		this.attr1 = attr1;
		this.attr2 = attr2;
		index = attr1.getName();
	}
	
	public String getDiffValue() {
		String value1 = attr1.getValue();
		String value2 = attr2.getValue();
		if(value1.equals(value2)) {
			return null;
		} else {
			diffValue = "to" + value2;
			return diffValue;
		}
	}
	
	public String getIndex() {
		return index;
	}
	
}
