/*
 * DO NOT MODIFY THIS FILE.
 * 
 * Any modifications to this file will not be used when grading your project.
 * If you have any questions, please email the TAs.
 * 
 */
package project4;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A single figure in a Raven's Progressive Matrix problem, comprised of a name,
 * an Image, and a path to the image.
 * 
 */
public class VisualRavensFigure {
    private String name;
    private String fullpath;
    
    /**
     * Creates a new figure for a Raven's Progressive Matrix given a name.
     * 
     * Your agent does not need to use this method.
     * 
     * @param name the name of the figure
     */
    public VisualRavensFigure(String name,String fullpath) {
        this.name=name;
        this.fullpath=fullpath;
    }
    /**
     * Returns the name of the figure. The name of the figure will always match
     * the HashMap key for this figure.
     * 
     * The figures in the problem will be named A, B, and C for 2x1 and 2x2
     * problems. The figures in the problem will be named A, B, C, D, E, F, G,
     * and H in 3x3 problems. The first row is A, B, and C; the second row is
     * D, E, and F; and the third row is G and H.
     * 
     * Answer options will always be named 1 through 6.
     * 
     * The numbers for the answer options will be randomly generated on each run
     * of the problem. The correct answer will remain the same, but its number
     * will change.
     * 
     * @return the name of this figure
     */
    public String getName() {
        return name;
    }
    /**
     * Returns the full path to the original image representing the figure.
     * 
     * @return the full path for the figure as a String
     */
    public String getPath() {
        return fullpath;
    }
}
