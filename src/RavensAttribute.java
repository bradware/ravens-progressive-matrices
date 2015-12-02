/*
 * DO NOT MODIFY THIS FILE.
 * 
 * Any modifications to this file will not be used when grading your project.
 * If you have any questions, please email the TAs.
 * 
 */
package project4;

/**
 * A single variable-value pair that describes some element of a RavensObject.
 * For example, a circle might have the attributes shape:circle, size:large, and
 * filled:no.
 */
public class RavensAttribute {
    private String name;
    private String value;
    
    /**
     * Creates a new RavensAttribute.
     * 
     * Your agent does not need to use this method.
     * 
     * @param name the name of the attribute
     * @param value the value of the attribute
     */
    public RavensAttribute(String name,String value) {
        this.name=name;
        this.value=value;
    }

    /**
     * Returns the name of the attribute. For example, 'shape', 'size', or
     * 'fill'.
     * 
     * @return the name of the attribute
     */
    public String getName() {
        return name;
    }
    
    /**
     * Returns the value of the attribute. For example, 'circle', 'large', or
     * 'no'.
     * 
     * @return the value of the attribute
     */
    public String getValue() {
        return value;
    }
}
