package project4;

public class AttributeGroup {
	
	private RavensAttribute attr1;
	private RavensAttribute attr2;
	private RavensAttribute attr3;
	private String index;
	private String diffValue;
	
	public AttributeGroup(RavensAttribute attr1, RavensAttribute attr2, RavensAttribute attr3) {
		this.attr1 = attr1;
		this.attr2 = attr2;
		this.attr3 = attr3;
		index = attr1.getName();
	}
	
	public String getDiffValue() {
		String value1 = attr1.getValue();
		String value2 = attr2.getValue();
		String value3 = attr3.getValue();
		if(value1.equals(value2) && value1.equals(value3)) {
			return null;
		} else {
			if(!value1.equals(value2)) {
				diffValue = "to" + value2;
				return diffValue;
			} else {
				diffValue = "to" + value3;
				return diffValue;
			}
		}
	}
	
	public String getIndex() {
		return index;
	}
	
}
