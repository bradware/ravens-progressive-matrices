/*
 * DO NOT MODIFY THIS FILE.
 * 
 * Any modifications to this file will not be used when grading your project.
 * If you have any questions, please email the TAs.
 * 
 */
package project4;

import java.util.ArrayList;

/**
 * A single object in a RavensFigure -- typically, a single shape in a frame,
 * such as a triangle or a circle -- comprised of a list of RavensAttributes.
 * 
 */
public class RavensObject {
    private String name;
    private ArrayList<RavensAttribute> attributes;
    
    /**
     * Constructs a new RavensObject given a name.
     * 
     * Your agent does not need to use this method.
     * 
     * @param name the name of the object
     */
    public RavensObject(String name) {
        this.name=name;
        attributes=new ArrayList<>();
    }

    /**
     * The name of this RavensObject. Names are assigned starting with the
     * letter Z and proceeding backwards in the alphabet through the objects
     * in the Frame. Names do not imply correspondence between shapes in
     * different frames. Names are simply provided to allow agents to organize
     * their reasoning over different figures.
     * 
     * Within a RavensFigure, each RavensObject has a unique name.
     * 
     * @return the name of the RavensObject
     */
    public String getName() {
        return name;
    }
    /**
     * Returns an ArrayList of RavensAttribute characterizing this RavensObject.
     * 
     * @return an ArrayList of RavensAttribute
     * 
     */
    public ArrayList<RavensAttribute> getAttributes() {
        return attributes;
    }
}
