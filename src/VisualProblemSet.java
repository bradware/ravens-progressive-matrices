/*
 * DO NOT MODIFY THIS FILE.
 * 
 * Any modifications to this file will not be used when grading your project.
 * If you have any questions, please email the TAs.
 * 
 */
package project4;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * A list of RavensProblems within one set.
 * 
 * Your agent does not need to use this class explicitly.
 * 
 */
public class VisualProblemSet {
    private String name;
    private ArrayList<VisualRavensProblem> problems;
    Random random;
    
    /**
     * Initializes a new VisualProblemSet with the given name, an empty set of
     * problems, and a new random number generator.
     * 
     * Your agent does not need to use this method. 
     * 
     * @param name The name of the problem set.
     */
    public VisualProblemSet(String name) {
        this.name=name;
        problems=new ArrayList<>();
        random=new Random();
    }
    
    /**
     * Returns the name of the problem set.
     * 
     * Your agent does not need to use this method. 
     * 
     * @return the name of the problem set as a String
     */
    public String getName() {
        return name;
    }
    /**
     * Returns an ArrayList of the RavensProblems in this problem set.
     * 
     * Your agent does not need to use this method. 
     * 
     * @return the RavensProblems in this set as an ArrayList.
     */
    public ArrayList<VisualRavensProblem> getProblems() {
        return problems;
    }
    /**
     * Adds a new problem to the problem set, read from an external file.
     * 
     * Your agent does not need to use this method. 
     * 
     * @param problem the File containing the new problem.
     */
    public void addProblem(File problem) {
        Scanner r=null;
        
        try{
            r = new Scanner(new File(problem.getAbsolutePath() + File.separatorChar + problem.getName() + ".txt"));
        } catch(Exception ex) {
            System.out.println(ex);
        }
        String name=r.nextLine();
        String type=r.nextLine();
        String currentAnswer=r.nextLine();
        //String answer="";
        String answer=currentAnswer;
        
        VisualRavensProblem newProblem = new VisualRavensProblem(name,type,answer);
        

        newProblem.getFigures().put("A",new VisualRavensFigure("A",problem.getAbsolutePath() + File.separatorChar + "A.png"));
        newProblem.getFigures().put("B",new VisualRavensFigure("B",problem.getAbsolutePath() + File.separatorChar + "B.png"));
        newProblem.getFigures().put("C",new VisualRavensFigure("C",problem.getAbsolutePath() + File.separatorChar + "C.png"));
        newProblem.getFigures().put("1",new VisualRavensFigure("1",problem.getAbsolutePath() + File.separatorChar + "1.png"));
        newProblem.getFigures().put("2",new VisualRavensFigure("2",problem.getAbsolutePath() + File.separatorChar + "2.png"));
        newProblem.getFigures().put("3",new VisualRavensFigure("3",problem.getAbsolutePath() + File.separatorChar + "3.png"));
        newProblem.getFigures().put("4",new VisualRavensFigure("4",problem.getAbsolutePath() + File.separatorChar + "4.png"));
        newProblem.getFigures().put("5",new VisualRavensFigure("5",problem.getAbsolutePath() + File.separatorChar + "5.png"));
        newProblem.getFigures().put("6",new VisualRavensFigure("6",problem.getAbsolutePath() + File.separatorChar + "6.png"));
        if(newProblem.getProblemType().equals("3x3 (Image)")) {
            newProblem.getFigures().put("D",new VisualRavensFigure("D",problem.getAbsolutePath() + File.separatorChar + "D.png"));
            newProblem.getFigures().put("E",new VisualRavensFigure("E",problem.getAbsolutePath() + File.separatorChar + "E.png"));
            newProblem.getFigures().put("F",new VisualRavensFigure("F",problem.getAbsolutePath() + File.separatorChar + "F.png"));
            newProblem.getFigures().put("G",new VisualRavensFigure("G",problem.getAbsolutePath() + File.separatorChar + "G.png"));
            newProblem.getFigures().put("H",new VisualRavensFigure("H",problem.getAbsolutePath() + File.separatorChar + "H.png"));
        }


        
        problems.add(newProblem);
    }
    private boolean tryParseInt(String i) {
     try  
     {  
         Integer.parseInt(i);  
         return true;  
      } catch(NumberFormatException nfe)  
      {  
          return false;  
      }  
    }
}
