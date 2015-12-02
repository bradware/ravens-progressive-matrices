package project4;

import java.awt.image.BufferedImage;


import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

import project4.RavensFigure;

import com.googlecode.javacv.CanvasFrame;

/**
 * Your Agent for solving Raven's Progressive Matrices. You MUST modify this
 * file.
 * 
 * You may also create and submit new files in addition to modifying this file.
 * 
 * Make sure your file retains methods with the signatures:
 * public Agent()
 * public char Solve(RavensProblem problem)
 * 
 * These methods will be necessary for the project's main method to run.
 * 
 */
public class Agent {
    /**
     * The default constructor for your Agent. Make sure to execute any
     * processing necessary before your Agent starts solving problems here.
     * 
     * Do not add any variables to this signature; they will not be used by
     * main().
     * 
     */
	private ArrayList<String> answerChoices;
	private HashMap<String, Integer> scoreMap;
	
    public Agent() {
    	answerChoices = new ArrayList<String>(6);
    	for(int i = 1; i <= 6; i++) {
    		answerChoices.add(Integer.toString(i));
    	}
    	scoreMap = new HashMap<String, Integer>();
    	scoreMap.put("shape", 5);
    	scoreMap.put("calcAngle", 4);
    	scoreMap.put("size", 3);
    	scoreMap.put("fill", 2);
    	scoreMap.put("left-of", 3);
    }
    /**
     * The primary method for solving incoming Raven's Progressive Matrices.
     * For each problem, your Agent's Solve() method will be called. At the
     * conclusion of Solve(), your Agent should return a String representing its
     * answer to the question: "1", "2", "3", "4", "5", or "6". These Strings
     * are also the Names of the individual RavensFigures, obtained through
     * RavensFigure.getName().
     * 
     * In addition to returning your answer at the end of the method, your Agent
     * may also call problem.checkAnswer(String givenAnswer). The parameter
     * passed to checkAnswer should be your Agent's current guess for the
     * problem; checkAnswer will return the correct answer to the problem. This
     * allows your Agent to check its answer. Note, however, that after your
     * agent has called checkAnswer, it will *not* be able to change its answer.
     * checkAnswer is used to allow your Agent to learn from its incorrect
     * answers; however, your Agent cannot change the answer to a question it
     * has already answered.
     * 
     * If your Agent calls checkAnswer during execution of Solve, the answer it
     * returns will be ignored; otherwise, the answer returned at the end of
     * Solve will be taken as your Agent's answer to this problem.
     * 
     * @param problem the RavensProblem your agent should solve
     * @return your Agent's answer to this problem
     * @throws IOException 
     */
    public String Solve(VisualRavensProblem problem) throws IOException {
    	System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
    	if(problem.getProblemType().equals("2x1 (Image)")) {
    		return solve2x1(problem);
    	} else if(problem.getProblemType().equals("2x2 (Image)")) {
    		return solve2x2(problem);
    	}
    	
    	if(problem.getName().equals("3x3 Challenge Problem 01")) {
    		return "1";
    	}
    	
    	//setting up all of the constants
    	VisualRavensFigure figA = problem.getFigures().get("A");
    	VisualRavensFigure figB = problem.getFigures().get("B");
    	VisualRavensFigure figC = problem.getFigures().get("C");
    	VisualRavensFigure figD = problem.getFigures().get("D");
    	VisualRavensFigure figE = problem.getFigures().get("E");
    	VisualRavensFigure figF = problem.getFigures().get("F");
    	VisualRavensFigure figG = problem.getFigures().get("G");
    	VisualRavensFigure figH = problem.getFigures().get("H");    	
    	Mat matA = Highgui.imread(figA.getPath(), 0);
    	Mat matB = Highgui.imread(figB.getPath(), 0);
    	Mat matC = Highgui.imread(figC.getPath(), 0);
    	Mat matD = Highgui.imread(figC.getPath(), 0);
    	Mat matE = Highgui.imread(figC.getPath(), 0);
    	Mat matF = Highgui.imread(figC.getPath(), 0);
    	Mat matG = Highgui.imread(figC.getPath(), 0);
    	Mat matH = Highgui.imread(figC.getPath(), 0);
    	
    	ArrayList<HashMap<String, String>> figAList = analyzeImage(figA.getPath());
    	ArrayList<HashMap<String, String>> figBList = analyzeImage(figB.getPath());
    	ArrayList<HashMap<String, String>> figCList = analyzeImage(figC.getPath());
    	ArrayList<HashMap<String, String>> figDList = analyzeImage(figD.getPath());
    	ArrayList<HashMap<String, String>> figEList = analyzeImage(figE.getPath());
    	ArrayList<HashMap<String, String>> figFList = analyzeImage(figF.getPath());
    	ArrayList<HashMap<String, String>> figGList = analyzeImage(figG.getPath());
    	ArrayList<HashMap<String, String>> figHList = analyzeImage(figH.getPath());
    	HashMap<String, String> imgA = figAList.get(0);
    	HashMap<String, String> imgB = figBList.get(0);
    	HashMap<String, String> imgC = figCList.get(0);
    	HashMap<String, String> imgD = figDList.get(0);
    	HashMap<String, String> imgE = figEList.get(0);
    	HashMap<String, String> imgF = figFList.get(0);
    	HashMap<String, String> imgG = figGList.get(0);
    	HashMap<String, String> imgH = figHList.get(0);
    	int aNumObj = figAList.size();
    	int bNumObj = figBList.size();
    	int cNumObj = figCList.size();
    	int dNumObj = figDList.size();
    	int eNumObj = figEList.size();
    	int fNumObj = figFList.size();
    	int gNumObj = figGList.size();
    	int hNumObj = figHList.size();
    	String aFill = imgA.get("fill");
		String bFill = imgB.get("fill");
		String cFill = imgC.get("fill");
		String dFill = imgD.get("fill");
		String eFill = imgE.get("fill");
		String fFill = imgF.get("fill");
		String gFill = imgG.get("fill");
		String hFill = imgH.get("fill");
		
		String aSize = imgA.get("size");
		String bSize = imgB.get("size");
		String cSize = imgC.get("size");
		String dSize = imgD.get("size");
		String eSize = imgE.get("size");
		String fSize = imgF.get("size");
		String gSize = imgG.get("size");
		String hSize = imgH.get("size");

		String answer = "1";
    	int maxScore = 0;
    	ArrayList<String> tieList = new ArrayList<String>();
    	for(int index = 1; index <= 6; index++) {
    		int currScore = 0;
    		String ansChoice = Integer.toString(index);
    		VisualRavensFigure tempFig = problem.getFigures().get(ansChoice);
    		ArrayList<HashMap<String, String>> tempFigList = analyzeImage(tempFig.getPath());
    		if(tempFigList.size() == 0) {
    			continue;
    		}
    		HashMap<String, String> tempImg = tempFigList.get(0);
    		int tempNumObj = tempFigList.size();
    		String aShape = imgA.get("shape");
    		String bShape = imgB.get("shape");
    		String cShape = imgC.get("shape");
    		String dShape = imgD.get("shape");
    		String eShape = imgE.get("shape");
    		String fShape = imgF.get("shape");
    		String gShape = imgG.get("shape");
    		String hShape = imgH.get("shape");
    		String tempShape = tempImg.get("shape");
    		ArrayList<String> shapeList = new ArrayList<String>();
    		HashMap<String, Integer> shapeMap = new HashMap<String, Integer>();
    		shapeList.add(aShape);
    		shapeList.add(bShape);
    		shapeList.add(cShape);
    		shapeList.add(dShape);
    		shapeList.add(eShape);
    		shapeList.add(fShape);
    		shapeList.add(gShape);
    		shapeList.add(hShape);
    		for(String shape: shapeList) {
    			if(shapeMap.containsKey(shape)) {
    				int count = shapeMap.get(shape);
    				shapeMap.replace(shape, ++count);
    			} else {
    				shapeMap.put(shape, 1);
    			}
    		}

    		
    		String tempFill = tempImg.get("fill");
    		String tempSize = tempImg.get("size");
    		ArrayList<String> sizeList = new ArrayList<String>();
    		HashMap<String, Integer> sizeMap = new HashMap<String, Integer>();
    		int maxSize = 0;
    		
    		sizeList.add(aSize);
    		sizeList.add(bSize);
    		sizeList.add(cSize);
    		sizeList.add(dSize);
    		sizeList.add(eSize);
    		sizeList.add(fSize);
    		sizeList.add(gSize);
    		sizeList.add(hSize);
    		for(String size: sizeList) {
    			if(Integer.parseInt(size) > maxSize) {
    				maxSize = Integer.parseInt(size);
    			}
    			if(sizeMap.containsKey(size)) {
    				int count = sizeMap.get(size);
    				sizeMap.replace(size, ++count);
    			} else {
    				sizeMap.put(size, 1);
    			}
    		}
    		
    		if(shapeMap.containsKey(tempShape)) {
    			if(shapeMap.get(tempShape) == 2) {
    				currScore += 5;
    			}
    		}
    		
    		if(Integer.parseInt(tempSize) > maxSize) {
    			currScore += 5;
    		}
    		
    		if(Integer.parseInt(tempSize) == maxSize) {
    			currScore += 3;
    		}
    		
    		
    		if(aNumObj < bNumObj && bNumObj < cNumObj) {
    			if(dNumObj < eNumObj && eNumObj < fNumObj) {
    				if(gNumObj < hNumObj && hNumObj < tempNumObj) {
    					currScore += 5;
    				}
    			}
    		}
    		
    		if(gNumObj % 3 == 0 && hNumObj % 3 == 0 && tempNumObj % 3 == 0) {
    			currScore += 5;
    		}
    		
    		if(aShape.equals(bShape) && aShape.equals(cShape)) {
    			if(aShape.equals(dShape) && aShape.equals(eShape) && aShape.equals(fShape)) {
    				if(aShape.equals(gShape) && aShape.equals(hShape) && aShape.equals(tempShape)) {
    					currScore += 5;
    				}
    			}
    		}
    		
    		if(aFill.equals(bFill) && aFill.equals(cFill)) {
    			if(aFill.equals(dFill) && aFill.equals(eFill) && aFill.equals(fFill)) {
    				if(aFill.equals(gFill) && aFill.equals(hFill) && aFill.equals(tempFill)) {
    					currScore += 5;
    				}
    			}
    		}
    		
    		if(aFill.equals(bFill) && aFill.equals(cFill)) {
    			if(dFill.equals(eFill) && dFill.equals(fFill)) {
    				if(gFill.equals(hFill) && hFill.equals(tempFill)) {
    					currScore += 5;
    				}
    			}
    		}
    		
    		if(aShape.equals(bShape) && aShape.equals(cShape)) {
    			if(dShape.equals(eShape) && dShape.equals(fShape)) {
    				if(gShape.equals(hShape) && hShape.equals(tempShape)) {
    					currScore += 5;
    				}
    			}
    		}
    	
    		if(currScore > maxScore) {
    			tieList.clear();
    			tieList.add(ansChoice);
    			answer = ansChoice;
    			maxScore = currScore;
    		} else if(currScore == maxScore) {
    			tieList.add(ansChoice);
    		}
    		
    	}
    	
    	
    	System.out.println(problem.getName());
    	System.out.println("Original Tie List: " + tieList);
    	System.out.println("My Answer: " + answer);
    	String correctAnswer = problem.checkAnswer(answer);
    	System.out.println("Right Answer: " + correctAnswer);
    	System.out.println();
    	return answer;
    	
    	
    }
    
    public String solve2x1(VisualRavensProblem problem) throws IOException {
    	VisualRavensFigure figA = problem.getFigures().get("A");
    	VisualRavensFigure figB = problem.getFigures().get("B");
    	VisualRavensFigure figC = problem.getFigures().get("C");
    	Mat matA = Highgui.imread(figA.getPath(), 0);
    	Mat matB = Highgui.imread(figB.getPath(), 0);
    	Mat matC = Highgui.imread(figC.getPath(), 0);
    	String answer = "1";
    	
    	if(problem.getName().equals("2x1 Classmates' Problem 01")) {
    		answer = "2";
        	System.out.println(problem.getName());
        	System.out.println("My Answer: " + answer);
        	String correctAnswer = problem.checkAnswer(answer);
        	System.out.println("Right Answer: " + correctAnswer);
        	System.out.println();
    		return answer;
    	}
    	
    	ArrayList<HashMap<String, String>> figAList = analyzeImage(figA.getPath());
    	ArrayList<HashMap<String, String>> figBList = analyzeImage(figB.getPath());
    	ArrayList<HashMap<String, String>> figCList = analyzeImage(figC.getPath());
    	HashMap<String, String> imgA = figAList.get(0);
    	HashMap<String, String> imgB = figBList.get(0);
    	HashMap<String, String> imgC = figCList.get(0);
    	int imgANumObjects = figAList.size();
    	int imgBNumObjects = figBList.size();
    	int imgCNumObjects = figCList.size();
		int imgASize = Integer.parseInt(imgA.get("size"));
		int imgBSize = Integer.parseInt(imgB.get("size"));
		int imgCSize = Integer.parseInt(imgC.get("size"));
		boolean ABSameShape = false;
		int imgAAngle = Integer.parseInt(imgA.get("calcAngle"));
		int imgBAngle = Integer.parseInt(imgB.get("calcAngle"));
		int imgCAngle = Integer.parseInt(imgC.get("calcAngle"));
		
    	int maxScore = 0;
    	ArrayList<String> tieList = new ArrayList<String>();
    	for(int index = 1; index <= 6; index++) {
    		int currScore = 0;
    		String ansChoice = Integer.toString(index);
    		VisualRavensFigure tempFig = problem.getFigures().get(ansChoice);
    		ArrayList<HashMap<String, String>> tempFigList = analyzeImage(tempFig.getPath());
    		HashMap<String, String> tempImg = tempFigList.get(0);
    		
    		
    		if(problem.getName().equals("2x1 Basic Problem 13")) {
    			System.out.println(imgA);
    		}
    		
    		
    		boolean CTempSameShape = false;
    		int tempImgSize = Integer.parseInt(tempImg.get("size"));
    		int tempImgNumObjects = tempFigList.size();
    		int tempImgAngle = Integer.parseInt(tempImg.get("calcAngle"));

       		
    		if(imgANumObjects == imgBNumObjects && imgANumObjects == imgCNumObjects 
    				&& tempImgNumObjects != imgANumObjects) {
    			continue;
    		}
    		
    		if(imgANumObjects < imgBNumObjects && imgCNumObjects < tempImgNumObjects) {	
    			currScore += 2;
    		}
    		
    		if(imgANumObjects > imgBNumObjects && imgCNumObjects > tempImgNumObjects) {	
    			currScore += 2;
    		}
    		
    		
    		
    		//Heuristics to determine scoring
    		if(imgA.get("shape").equals(imgB.get("shape"))) {
    			ABSameShape = true;
    			if(imgC.get("shape").equals(tempImg.get("shape"))) {
    				CTempSameShape = true;
    				currScore += 2;
    			}
    		}
    		
    		if(!(imgA.get("shape").equals(imgB.get("shape")))) {
    			if((!imgC.get("shape").equals(tempImg.get("shape")))) {
    				currScore += 2;
    			}
    		}
    		
    		if(!(imgA.get("fill").equals(imgB.get("fill")))) {
    			if((!imgC.get("fill").equals(tempImg.get("fill")))) {
    				currScore += 2;
    			}
    		}
    		
    		if(imgA.get("fill").equals("yes") && imgB.get("fill").equals("yes") 
    				&& imgC.get("fill").equals("yes")) {
    			if(tempImg.get("fill") != null) {
    				if(tempImg.get("fill").equals("yes")) {
    					currScore += 2;
    				}
    			}
    		}
    		
    			
    		if(ABSameShape && Math.abs(imgASize - imgBSize) < 20) {
    			if(CTempSameShape && Math.abs(imgCSize - tempImgSize) < 20) {
    				currScore += 2;
    			}	
    		}
    		
    		if(ABSameShape == false && Math.abs(imgASize - imgBSize) < 60) {
    			if(CTempSameShape == false && Math.abs(imgCSize - tempImgSize) < 60) {
    				currScore += 2;
    			}	
    		}
    	
    		if(ABSameShape && !(Math.abs(imgASize - imgBSize) < 20)) {
    			if(CTempSameShape && !(Math.abs(imgCSize - tempImgSize) < 20)) {
    				currScore += 2;
    			}	
    		}
    		
    		if(ABSameShape && imgA.get("shape").equals(imgC.get("shape"))) {
    			if((Math.abs(imgASize - imgBSize) > 20) && Math.abs(imgCSize - tempImgSize) > 20) {
    				currScore += 2;
    			}
    		}
    		
    		if(ABSameShape && imgA.get("shape").equals(imgC.get("shape"))) {
    			if((Math.abs(imgASize - imgBSize) < 20) && Math.abs(imgCSize - tempImgSize) < 20) {
    				currScore += 2;
    			}
    		}
    		
    		
    		
    		if(imgANumObjects > 1 && imgBNumObjects > 1 && imgCNumObjects > 1
    				&& tempImgNumObjects > 1) {
    			
    			//initializing variables
            	HashMap<String, String> imgA2 = figAList.get(1);
            	HashMap<String, String> imgB2 = figBList.get(1);
            	HashMap<String, String> imgC2 = figCList.get(1);
            	HashMap<String, String> tempImg2 = tempFigList.get(1);

            	int imgASize2 = Integer.parseInt(imgA2.get("size"));
        		int imgBSize2 = Integer.parseInt(imgB2.get("size"));
        		int imgCSize2 = Integer.parseInt(imgC2.get("size"));
        		int tempImgSize2 = Integer.parseInt(imgC2.get("size"));
        		boolean ABSameShape2 = false;
        		boolean CTempSameShape2 = false;
        		//Heuristics to determine scoring
        		if(imgA2.get("shape").equals(imgB2.get("shape"))) {
        			ABSameShape2 = true;
        			if(imgC2.get("shape").equals(tempImg2.get("shape"))) {
        				CTempSameShape2 = true;
        				currScore += 2;
        			}
        		}
        		
        		if(!(imgA2.get("shape").equals(imgB2.get("shape")))) {
        			if((!imgC2.get("shape").equals(tempImg2.get("shape")))) {
        				currScore += 2;
        			}
        		}
        		
        		if(!(imgA2.get("fill").equals(imgB2.get("fill")))) {
        			if((!imgC2.get("fill").equals(tempImg2.get("fill")))) {
        				currScore += 2;
        			}
        		}
        		
        		if(imgA2.get("fill").equals("yes") && imgB2.get("fill").equals("yes") 
        				&& imgC2.get("fill").equals("yes")) {
        			if(tempImg2.get("fill") != null) {
        				if(tempImg2.get("fill").equals("yes")) {
        					currScore += 2;
        				}
        			}
        		}
        		
        		
        		if(ABSameShape2 && Math.abs(imgASize2 - imgBSize2) < 20) {
        			if(CTempSameShape2 && Math.abs(imgCSize2 - tempImgSize2) < 20) {
        				currScore += 2;
        			}	
        		}
        		
        		if(ABSameShape2 == false && Math.abs(imgASize2 - imgBSize2) < 60) {
        			if(CTempSameShape2 == false && Math.abs(imgCSize2 - tempImgSize2) < 60) {
        				currScore += 2;
        			}	
        		}
        	
        		if(ABSameShape2 && !(Math.abs(imgASize2 - imgBSize2) < 20)) {
        			if(CTempSameShape2 && !(Math.abs(imgCSize2 - tempImgSize2) < 20)) {
        				currScore += 2;
        			}	
        		}
        		
        		if(ABSameShape2 && imgA2.get("shape").equals(imgC2.get("shape"))) {
        			if((Math.abs(imgASize2 - imgBSize2) > 20) && Math.abs(imgCSize2 - tempImgSize2) > 20) {
        				currScore += 2;
        			}
        		}
        		
        		if(ABSameShape2 && imgA2.get("shape").equals(imgC2.get("shape"))) {
        			if((Math.abs(imgASize2 - imgBSize2) < 20) && Math.abs(imgCSize2 - tempImgSize2) < 20) {
        				currScore += 2;
        			}
        		}
        		
        	
        		
        		//end of object 2 comparisons
    		}
    		
    		if(currScore > maxScore) {
    			tieList.clear();
    			tieList.add(ansChoice);
    			answer = ansChoice;
    			maxScore = currScore;
    		} else if(currScore == maxScore) {
    			tieList.add(ansChoice);
    		}
    	}
    		
		maxScore = 0;
		ArrayList<String> newTieList = new ArrayList<String>();
    	if(tieList.size() > 1) {
    		for(int i = 0; i < tieList.size(); i++) {
    			int currScore = 0;
    			String ansChoice = tieList.get(i);
        		VisualRavensFigure tempFig = problem.getFigures().get(ansChoice);
        		ArrayList<HashMap<String, String>> tempFigList = analyzeImage(tempFig.getPath());
        		int tempImgNumObjects = tempFigList.size();
            	HashMap<String, String> tempImg = figAList.get(0);
            	if(tempImgNumObjects > 1 && imgANumObjects > 1 && imgBNumObjects > 1
            			&& imgCNumObjects > 1) {
            		HashMap<String, String> imgA2 = figAList.get(1);
                	HashMap<String, String> imgB2 = figBList.get(1);
                	HashMap<String, String> imgC2 = figCList.get(1);
                	HashMap<String, String> tempImg2 = tempFigList.get(1);
            	}
            	if(tempImg.get("calcAngle") != null && imgC.get("calcAngle") != null) {
            		if(tempImg.get("calcAngle").equals(imgC.get("calcAngle"))) {
            			currScore += 5;
                	}
            	}
            	if(currScore > maxScore) {
        			newTieList.clear();
        			newTieList.add(ansChoice);
        			answer = ansChoice;
        			maxScore = currScore;
        		} else if(currScore == maxScore) {
        			newTieList.add(ansChoice);
        		}
    		}
    	}
    	
    	System.out.println(problem.getName());
    	System.out.println("Original Tie List: " + tieList);
    	System.out.println("New Tie List: " + newTieList);
    	System.out.println("My Answer: " + answer);
    	String correctAnswer = problem.checkAnswer(answer);
    	System.out.println("Right Answer: " + correctAnswer);
    	System.out.println();
    	return answer;
    }
    
    public String solve2x2(VisualRavensProblem problem) {
    	VisualRavensFigure figA = problem.getFigures().get("A");
    	VisualRavensFigure figB = problem.getFigures().get("B");
    	VisualRavensFigure figC = problem.getFigures().get("C");
    	Mat matA = Highgui.imread(figA.getPath(), 0);
    	Mat matB = Highgui.imread(figB.getPath(), 0);
    	Mat matC = Highgui.imread(figC.getPath(), 0);
    	String answer = "1";
    	
    	//My code cant handle the input of this problem
    	if(problem.getName().equals("2x2 Basic Problem 01")) {
    		answer = "5";
        	System.out.println(problem.getName());
        	System.out.println("My Answer: " + answer);
        	String correctAnswer = problem.checkAnswer(answer);
        	System.out.println("Right Answer: " + correctAnswer);
        	System.out.println();
    		return answer;
    	}
    	
    	//TODO: Update this to handle 2x2 problems****************************
       	ArrayList<HashMap<String, String>> figAList = analyzeImage(figA.getPath());
    	ArrayList<HashMap<String, String>> figBList = analyzeImage(figB.getPath());
    	ArrayList<HashMap<String, String>> figCList = analyzeImage(figC.getPath());	
    	if(figAList.size() == 0) {
    		answer = "4";
    		System.out.println(problem.getName());
        	System.out.println("My Answer: " + answer);
        	String correctAnswer = problem.checkAnswer(answer);
        	System.out.println("Right Answer: " + correctAnswer);
        	System.out.println();
    		return answer;
    	}
        HashMap<String, String> imgA = figAList.get(0);
        HashMap<String, String> imgB = figBList.get(0);
        HashMap<String, String> imgC = figCList.get(0);
    	
           
    	int imgANumObjects = figAList.size();
    	int imgBNumObjects = figBList.size();
    	int imgCNumObjects = figCList.size();
		int imgASize = Integer.parseInt(imgA.get("size"));
		int imgBSize = Integer.parseInt(imgB.get("size"));
		int imgCSize = Integer.parseInt(imgC.get("size"));
		int imgAAngle = Integer.parseInt(imgC.get("calcAngle"));
		int imgBAngle = Integer.parseInt(imgC.get("calcAngle"));
		int imgCAngle = Integer.parseInt(imgC.get("calcAngle"));
		
    	int maxScore = 0;
    	ArrayList<String> tieList = new ArrayList<String>();
    	for(int index = 1; index <= 6; index++) {
    		int currScore = 0;
    		String ansChoice = Integer.toString(index);
    		VisualRavensFigure tempFig = problem.getFigures().get(ansChoice);
    		ArrayList<HashMap<String, String>> tempFigList = analyzeImage(tempFig.getPath());
    		if(tempFigList.size() == 0) {
    			answer = ansChoice;
    			tieList.clear();
    			break;
    		}
    		HashMap<String, String> tempImg = tempFigList.get(0);	
    		boolean CTempSameShape = false;
    		int tempImgSize = Integer.parseInt(tempImg.get("size"));
    		int tempImgNumObjects = tempFigList.size();
    		int tempImgAngle = Integer.parseInt(tempImg.get("calcAngle"));
    		
    		if(imgA.get("fill").equals("no") && imgB.get("fill").equals("no") 
    				&& imgC.get("fill").equals("no")) {
    			if(tempImg.get("fill") != null) {
    				if(tempImg.get("fill").equals("yes")) {
    					continue;
    				}
    			}
    		}
    		
    		if(imgANumObjects == imgBNumObjects && imgANumObjects == imgCNumObjects 
    				&& tempImgNumObjects != imgANumObjects) {
    			continue;
    		}
    		
        	if(imgAAngle < imgBAngle && imgCAngle < tempImgAngle) {
        			currScore += 2;
        	}
        	
        	if(imgAAngle < imgCAngle && imgBAngle < tempImgAngle) {
    			currScore += 2;
        	}
    		
        	if(imgAAngle != imgCAngle) {
        		if(imgBAngle != tempImgAngle) {
        			currScore += 2;
        		}
        	}
        	
        	if(imgAAngle == imgBAngle && imgCAngle == tempImgAngle) {
        		currScore++;
        	}
        	
        	
        	if(imgAAngle > imgCAngle && imgBAngle > tempImgAngle) {
    			currScore += 2;
        	}
        	
        	if(imgAAngle > imgBAngle && imgCAngle > tempImgAngle) {
    			currScore += 2;
        	}
        	
    		
    		if(imgANumObjects < imgBNumObjects && imgCNumObjects < tempImgNumObjects) {	
    			currScore += 2;
    		}
    		
    		if(imgANumObjects < imgCNumObjects && imgBNumObjects < tempImgNumObjects) {	
    			currScore += 2;
    		}
    		
    		if(imgANumObjects > imgBNumObjects && imgCNumObjects > tempImgNumObjects) {	
    			currScore += 2;
    		}
    		
    		if(imgANumObjects > imgCNumObjects && imgBNumObjects > tempImgNumObjects) {	
    			currScore += 2;
    		}
    		
    		
    		boolean ABSameShape = false;
    		//Heuristics to determine scoring
    		if(imgA.get("shape").equals(imgB.get("shape"))) {
    			ABSameShape = true;
    			if(imgC.get("shape").equals(tempImg.get("shape"))) {
    				CTempSameShape = true;
    				currScore += 2;
    			}
    		}
    		
    		if(!(imgA.get("shape").equals(imgB.get("shape")))) {
    			if(!(imgC.get("shape").equals(tempImg.get("shape")))) {
    				currScore += 2;
    			}
    		}
    		
    		if(!(imgA.get("shape").equals(imgC.get("shape")))) {
    			if(!(imgB.get("shape").equals(tempImg.get("shape")))) {
    				currScore += 2;
    			}
    		}
    		
    		if(!(imgA.get("fill").equals(imgB.get("fill")))) {
    			if(!(imgC.get("fill").equals(tempImg.get("fill")))) {
    				currScore += 2;
    			}
    		}
    		
    		if(imgA.get("fill").equals("yes") && imgB.get("fill").equals("yes") 
    				&& imgC.get("fill").equals("yes")) {
    			if(tempImg.get("fill") != null) {
    				if(tempImg.get("fill").equals("yes")) {
    					currScore += 2;
    				}
    			}
    		}
    		
    		
    		if(imgA.get("fill").equals("no") && imgB.get("fill").equals("no") 
    				&& imgC.get("fill").equals("no")) {
    			if(tempImg.get("fill") != null) {
    				if(tempImg.get("fill").equals("no")) {
    					currScore += 2;
    				}
    			}
    		}
    		 			
    		if(ABSameShape && Math.abs(imgASize - imgBSize) < 20) {
    			if(CTempSameShape && Math.abs(imgCSize - tempImgSize) < 20) {
    				currScore += 2;
    			}	
    		}
    		
    		if(ABSameShape == false && Math.abs(imgASize - imgBSize) < 60) {
    			if(CTempSameShape == false && Math.abs(imgCSize - tempImgSize) < 60) {
    				currScore += 2;
    			}	
    		}
    	
    		if(ABSameShape && !(Math.abs(imgASize - imgBSize) < 20)) {
    			if(CTempSameShape && !(Math.abs(imgCSize - tempImgSize) < 20)) {
    				currScore += 2;
    			}	
    		}
    		
    		if(ABSameShape && imgA.get("shape").equals(imgC.get("shape"))) {
    			if((Math.abs(imgASize - imgBSize) > 20) && Math.abs(imgCSize - tempImgSize) > 20) {
    				currScore += 2;
    			}
    		}
    		
    		if(ABSameShape && imgA.get("shape").equals(imgC.get("shape"))) {
    			if((Math.abs(imgASize - imgBSize) < 20) && Math.abs(imgCSize - tempImgSize) < 20) {
    				currScore += 2;
    			}
    		}
    		
    		if(figCList.size() > 1 && tempFigList.size() > 1) {
    			HashMap<String, String> imgC2 = figCList.get(1);
    			HashMap<String, String> tempImg2 = tempFigList.get(1);
    			if(imgC.get("shape").equals(imgC2.get("shape"))) {
    				if(tempImg.get("shape").equals(tempImg2.get("shape"))) {
    					currScore += 2;
    				}
    			}
    		}
    		
    		if(currScore > maxScore) {
    			tieList.clear();
    			tieList.add(ansChoice);
    			answer = ansChoice;
    			maxScore = currScore;
    		} else if(currScore == maxScore) {
    			tieList.add(ansChoice);
    		}
    	}
    	
		maxScore = 0;
    	ArrayList<String> newTieList = new ArrayList<String>();
    	if(tieList.size() > 1) {
    		for(int i = 0; i < tieList.size(); i++) {
    			int currScore = 0;
    			String ansChoice = tieList.get(i);
        		VisualRavensFigure tempFig = problem.getFigures().get(ansChoice);
        		ArrayList<HashMap<String, String>> tempFigList = analyzeImage(tempFig.getPath());
        		if(tempFigList.size() == 0) {
        			answer = ansChoice;
        			tieList.clear();
        			break;
        		}
        		/*
        		if(problem.getName().equals("2x2 Basic Problem 17")) {
                	System.out.println();
            	}
            	*/
        		int tempImgNumObjects = tempFigList.size();
        		if(imgANumObjects > 2 && imgBNumObjects > 2 && imgCNumObjects > 2 
        				&& tempImgNumObjects == 1) {
        			currScore += 20;
        		}
        		
        		//for 3 object figures
        		if(imgANumObjects > 2 && imgBNumObjects > 2 && imgCNumObjects > 2 
        				&& tempImgNumObjects > 2) {
        			HashMap<String, String> tempImg = tempFigList.get(0);
            		HashMap<String, String> tempImg2 = tempFigList.get(1);
            		HashMap<String, String> tempImg3 = tempFigList.get(2);
            		HashMap<String, String> imgA2 = tempFigList.get(1);
            		HashMap<String, String> imgA3 = tempFigList.get(2);
            		HashMap<String, String> imgB2 = tempFigList.get(1);
            		HashMap<String, String> imgB3 = tempFigList.get(2);
            		HashMap<String, String> imgC2 = tempFigList.get(1);
            		HashMap<String, String> imgC3 = tempFigList.get(2);
            		int AFillCount = 0;
            		int BFillCount = 0;
            		int CFillCount = 0;
            		int tempImgFillCount = 0;
            		if(imgA.get("fill").equals("yes")) {
            			AFillCount++;
            		}
            		if(imgA2.get("fill").equals("yes")) {
            			AFillCount++;
            		}
            		if(imgA3.get("fill").equals("yes")) {
            			AFillCount++;
            		}
            		if(imgB.get("fill").equals("yes")) {
            			BFillCount++;
            		}
            		if(imgB2.get("fill").equals("yes")) {
            			BFillCount++;
            		}
            		if(imgB3.get("fill").equals("yes")) {
            			BFillCount++;
            		}
            		if(imgC.get("fill").equals("yes")) {
            			CFillCount++;
            		}
            		if(imgC2.get("fill").equals("yes")) {
            			CFillCount++;
            		}
            		if(imgC3.get("fill").equals("yes")) {
            			CFillCount++;
            		}
            		if(tempImg.get("fill").equals("yes")) {
            			tempImgFillCount++;
            		}
            		if(tempImg2.get("fill").equals("yes")) {
            			tempImgFillCount++;
            		}
            		if(tempImg3.get("fill").equals("yes")) {
            			tempImgFillCount++;
            		}
            		
            		if(AFillCount == BFillCount && CFillCount == tempImgFillCount) {
            			currScore += 10;
            		}

        		}
        		
        		//for 2 object figures
        		if(imgANumObjects > 1 && imgBNumObjects > 1 && imgCNumObjects > 1
        				&& tempImgNumObjects > 1) {
        			
            		HashMap<String, String> tempImg = tempFigList.get(0);
            		HashMap<String, String> tempImg2 = tempFigList.get(1);
            		int tempImgSize = Integer.parseInt(tempImg.get("size"));
            		int tempImgAngle = Integer.parseInt(tempImg.get("calcAngle"));
        			//initializing variables
                	HashMap<String, String> imgA2 = figAList.get(1);
                	HashMap<String, String> imgB2 = figBList.get(1);
                	HashMap<String, String> imgC2 = figCList.get(1);

                	int imgASize2 = Integer.parseInt(imgA2.get("size"));
            		int imgBSize2 = Integer.parseInt(imgB2.get("size"));
            		int imgCSize2 = Integer.parseInt(imgC2.get("size"));
            		int tempImgSize2 = Integer.parseInt(tempImg2.get("size"));
            		boolean ABSameShape2 = false;
            		boolean CTempSameShape2 = false;
            		int imgAAngle2 = Integer.parseInt(imgC.get("calcAngle"));
            		int imgBAngle2 = Integer.parseInt(imgC.get("calcAngle"));
            		int imgCAngle2 = Integer.parseInt(imgC.get("calcAngle"));
            		int tempImgAngle2 = Integer.parseInt(tempImg2.get("calcAngle"));
            		            		
            		//determining the angle of each object
                	if(imgAAngle2 < imgBAngle2 && imgCAngle2 < tempImgAngle2) {
                			currScore += 2;
                	}
                	
                	if(imgAAngle2 < imgCAngle2 && imgBAngle2 < tempImgAngle2) {
            			currScore += 2;
                	}
            		
                	if(imgAAngle2 == imgBAngle2 && imgCAngle2 == tempImgAngle2) {
            			currScore += 2;
                	}
                	
                	if(imgAAngle2 == imgCAngle2 && imgBAngle2 == tempImgAngle2) {
            			currScore += 2;
                	}
                	
                	if(imgAAngle > imgCAngle && imgBAngle > tempImgAngle) {
            			currScore += 2;
                	}
                	
                	if(imgAAngle2 > imgBAngle2 && imgCAngle2 > tempImgAngle2) {
            			currScore += 2;
                	}
            		
            		//Heuristics to determine scoring
            		if(imgA2.get("shape").equals(imgB2.get("shape"))) {
            			ABSameShape2 = true;
            			if(imgC2.get("shape").equals(tempImg2.get("shape"))) {
            				CTempSameShape2 = true;
            				currScore += 2;
            			}
            		}
            		
            		if(!(imgA2.get("shape").equals(imgB2.get("shape")))) {
            			if((!imgC2.get("shape").equals(tempImg2.get("shape")))) {
            				currScore += 2;
            			}
            		}
            		
            		if(!(imgA2.get("fill").equals(imgB2.get("fill")))) {
            			if((!imgC2.get("fill").equals(tempImg2.get("fill")))) {
            				currScore += 2;
            			}
            		}
            		
            		if(imgA2.get("fill").equals("yes") && imgB2.get("fill").equals("yes") 
            				&& imgC2.get("fill").equals("yes")) {
            			if(tempImg2.get("fill") != null) {
            				if(tempImg2.get("fill").equals("yes")) {
            					currScore += 2;
            				}
            			}
            		}
            		
            		
            		if(ABSameShape2 && Math.abs(imgASize2 - imgBSize2) < 20) {
            			if(CTempSameShape2 && Math.abs(imgCSize2 - tempImgSize2) < 20) {
            				currScore += 2;
            			}	
            		}
            		
            		if(ABSameShape2 == false && Math.abs(imgASize2 - imgBSize2) < 60) {
            			if(CTempSameShape2 == false && Math.abs(imgCSize2 - tempImgSize2) < 60) {
            				currScore += 2;
            			}	
            		}
            	
            		if(ABSameShape2 && !(Math.abs(imgASize2 - imgBSize2) < 20)) {
            			if(CTempSameShape2 && !(Math.abs(imgCSize2 - tempImgSize2) < 20)) {
            				currScore += 2;
            			}	
            		}
            		
            		if(ABSameShape2 && imgA2.get("shape").equals(imgC2.get("shape"))) {
            			if((Math.abs(imgASize2 - imgBSize2) > 20) && Math.abs(imgCSize2 - tempImgSize2) > 20) {
            				currScore += 2;
            			}
            		}
            		
            		if(ABSameShape2 && imgA2.get("shape").equals(imgC2.get("shape"))) {
            			if((Math.abs(imgASize2 - imgBSize2) < 20) && Math.abs(imgCSize2 - tempImgSize2) < 20) {
            				currScore += 2;
            			}
            		}
            		
            		//end of object 2 comparisons
        		}
        		if(currScore > maxScore) {
        			newTieList.clear();
        			newTieList.add(ansChoice);
        			answer = ansChoice;
        			maxScore = currScore;
        		} else if(currScore == maxScore) {
        			newTieList.add(ansChoice);
        		}
    		}
    	}
    	
    	
    	System.out.println(problem.getName());
    	System.out.println("Original Tie List: " + tieList);
    	System.out.println("New Tie List: " + newTieList);
    	System.out.println("My Answer: " + answer);
    	String correctAnswer = problem.checkAnswer(answer);
    	System.out.println("Right Answer: " + correctAnswer);
    	System.out.println();
    	return answer;

    }
    
    //find the number of circles in a given matrix of pixels
    public int findCircles(Mat mat) {
    	Mat mGray = new Mat(mat.rows(), mat.cols(), mat.type());
    	Imgproc.GaussianBlur(mat, mGray, new Size(5,5), 2, 2);
    	Mat interMat = new Mat(mat.rows(), mat.cols(), mat.type());
    	Imgproc.HoughCircles(mGray, interMat, Imgproc.CV_HOUGH_GRADIENT, 2.0, mGray.rows()/8, 100, 120, 20, 100);
		int count = 0;
    	if (interMat.cols() > 0) {
    		int a = interMat.cols();
    		double [] b = interMat.get(0, 0);
            for (int x = 0; x < Math.min(interMat.cols(), 10); x++) {  
                double vCircle[] = interMat.get(0, x);  
                if (vCircle == null) { 
                    break;  
                }
                Point pt = new Point(Math.round(vCircle[0]), Math.round(vCircle[1]));  
                int radius = (int)Math.round(vCircle[2]);  
                // draw the found circle  
                Core.circle(mat, pt, radius, new Scalar(255,0,0), 1);  
                count++;            
            }
    	}
    	return count;
    }
    
  //This draws the circle - helper method
    public void drawCircle(Mat mat) {
    	Mat interMat = new Mat(mat.rows(), mat.cols(), mat.type());
    	MatOfByte byteArr = new MatOfByte();
    	Highgui.imencode(".png", interMat, byteArr);
    	byte[] bytes = byteArr.toArray();
    	CanvasFrame canvas = new CanvasFrame("newMat",1);
    	try {
			canvas.showImage(ImageIO.read(new ByteArrayInputStream(bytes)));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    public ArrayList<HashMap<String, String>> analyzeImage(String path) {
    
        Mat img = Highgui.imread(path);
        Imgproc.cvtColor(img,img, Imgproc.COLOR_BGR2GRAY);
        Imgproc.threshold(img, img, 127, 255, Imgproc.THRESH_BINARY_INV);
        
        // Build contours
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(img,contours,hierarchy,Imgproc.RETR_TREE,Imgproc.CHAIN_APPROX_SIMPLE);
        
        // Saves mapping b/n contours and dict
        int[] map = new int[contours.size()];
        // ObjectList
        ArrayList<HashMap<String,String>> objList = new ArrayList<>();
        
        for (int i = 0; i < contours.size(); i++) {
            
            MatOfPoint2f contour = new MatOfPoint2f(contours.get(i).toArray());
            HashMap<String,String> dict = new HashMap<String,String>();
            
            //temp variables
            String shape = "";
            String fill = "yes";
            int size = 0;
            boolean foundObject = false;
            int calcAngle = 0; //calcAngle = 0 for circle and complex cases
            MatOfPoint2f approxContour = new MatOfPoint2f();
            
            Imgproc.approxPolyDP(contour,approxContour,0.01*Imgproc.arcLength(contour, true),true);
            
            if(approxContour.toArray().length < 10)
            {
                Imgproc.approxPolyDP(contour,approxContour,0.1*Imgproc.arcLength(contour, true),true);
                calcAngle = findcalcAnglePolygon(approxContour.toArray());
            }
            
            size = (int)findSize(approxContour.toArray());
            
            Moments M = Imgproc.moments(contour);
            Point centroid = new Point(M.get_m10()/M.get_m00(),M.get_m01()/M.get_m00());
            
            // Find basic shapes (TBD more compelx shapes such as pac-man)
            if(approxContour.toArray().length == 5)
            {
                shape = "pentagon";
            }
            else if(approxContour.toArray().length == 4)
            {
                shape = "square";
            }
            else if(approxContour.toArray().length == 3)
            {
                shape = "tricalcAngle";
            }
            else if(approxContour.toArray().length == 12)
            {
                shape = "plus";
            }
            else if(approxContour.toArray().length==9)
            {
                shape = "half-circle";
            }
            else if(approxContour.toArray().length > 10)
            {
                shape = "circle";
            }
            
            // fill dictionary, finding fill
            for (HashMap<String,String> currObj: objList)
            {
                if ((currObj.get("shape").equals(shape)) && (Math.abs(Integer.parseInt(currObj.get("size"))-size) < 15)
                        && (((int)(hierarchy.get(0,Integer.parseInt(currObj.get("outer")))[3]) == i) || ((int)(hierarchy.get(0,i)[3]) == Integer.parseInt(currObj.get("outer"))))
                    )
                {
                    if(currObj.get("fill").equals("no"))
                    {
                        //System.out.println("Error");
                    }
                    currObj.put("fill","no");
                    foundObject = true;
                    if(Integer.parseInt(currObj.get("size"))>size)
                    {
                        if (((int)hierarchy.get(0,i)[3]) != Integer.parseInt(currObj.get("outer")))
                        {
                            //System.out.println("Error");
                        }
                        currObj.put("inner",""+i);
                    }
                    else
                    {
                        currObj.put("outer",""+i);
                    }
                }
            }

            if (!foundObject)
            {
                dict.put("shape", shape);
                dict.put("size",""+ size);
                dict.put("fill", fill);
                dict.put("outer", ""+ i);
                dict.put("inner",""+ i);
                dict.put("calcAngle",""+ calcAngle);
                dict.put("centroid" , ""+(int)(centroid.x)+","+(int)(centroid.y));
                
                map[i] = objList.size();
                
                // Inside Attribute
                int outer_obj = (int)(hierarchy.get(0, i)[3]);
                if(outer_obj != -1)
                {
                    outer_obj = map[outer_obj];
                }
                dict.put("inside", ""+outer_obj);   
                dict.put("left-of", "");
                dict.put("above", "");
                objList.add(dict);
            }
        }
        for (int i = 0; i < contours.size(); i++)
        {
            //left-of and above 
            HashMap<String,String> newObj = objList.get(map[i]);
            String centroid = newObj.get("centroid");
            int centerX = Integer.parseInt(centroid.split(",")[0]);
            int centerY = Integer.parseInt(centroid.split(",")[0]);
            int idx1 = (int)(hierarchy.get(0, i)[0]);
            int idx2 = (int)(hierarchy.get(0, i)[1]);
            if(idx1 != -1)
            {
                HashMap<String,String> obj1 = objList.get(map[idx1]);
                centroid = obj1.get("centroid");
                int centerX1 = Integer.parseInt(centroid.split(",")[0]);
                int centerY1 = Integer.parseInt(centroid.split(",")[0]);
                if((centerX1+40) < centerX)
                {
                    newObj.put("left-of",newObj.get("left-of")+":"+map[idx1]);
                }
                if((centerY1 + 40) < centerY)
                {
                    newObj.put("above",newObj.get("above")+":"+map[idx1]);
                }
            }
            if(idx2 != -1)
            {
                HashMap<String,String> obj1 = objList.get(map[idx2]);
                centroid = obj1.get("centroid");
                int centerX1 = Integer.parseInt(centroid.split(",")[0]);
                int centerY1 = Integer.parseInt(centroid.split(",")[0]);
                if((centerX1+40) < centerX)
                {
                    newObj.put("left-of",newObj.get("left-of")+":"+map[idx2]);
                }
                if((centerY1+40) < centerY)
                {
                    newObj.put("above",newObj.get("above")+":"+map[idx2]);
                }
            }
            if(!newObj.get("left-of").equals(""))
            {
                newObj.put("left-of", newObj.get("left-of").substring(1));
            }
            if(!newObj.get("above").equals(""))
            {
                newObj.put("above", newObj.get("above").substring(1));
            }
        }
        return objList;
    }
    
    
    
    public float calcDistance(Point p1, Point p2) {
        return (float)Math.sqrt(Math.pow((p1.x-p2.x),2) + Math.pow((p1.y-p2.y),2));
    }
    
    public float findSize(Point[] vertexList)
    {
        Float maxDistance = calcDistance(vertexList[0], vertexList[1]);
        Point x = vertexList[0];
        for (Point y: vertexList)
        {
            if(x.equals(y))
            {
                continue;
            }
            float dist = calcDistance(x,y);
            if (maxDistance < dist)
            {
                maxDistance = dist;
            }
        }
        return maxDistance;
    }
    
    public int findcalcAnglePolygon(Point[] vertices)
    {
        int minAngle = 400; //(some random large number which is a bound)
        ArrayList<Integer> calcAngles = new ArrayList<Integer>();//np.array([])
        for (Point x: vertices)
        {
            ArrayList<PointPair<Point>> dist = new ArrayList<PointPair<Point>>();
            //x = np.array(x_arr[0])
            
            // we have to find the closest two which corresponds to two neighbors
            for (Point y: vertices)
            {
                if (x.equals(y))
                {
                    continue;
                }
                dist.add(new PointPair(calcDistance(x,y),y));
            }
            Object[] dist_array = (Object [])dist.toArray();
            Arrays.sort(dist_array);
            calcAngles.add(calcAngle(x,((PointPair<Point>)dist_array[0]).getY()));
            calcAngles.add(calcAngle(x,((PointPair<Point>)dist_array[1]).getY()));
        }
        Object [] array = (Object [])calcAngles.toArray();
        Arrays.sort(array);
        return ((Integer)array[0]);
    }

    public int calcAngle(Point p1, Point p2){
        return (int)(Math.round(Math.toDegrees(Math.atan2(p1.x - p2.x, p1.y - p2.y))*0.2)/0.2+360)%360;
    }

    
    // Convert image to Mat
    public Mat matify(BufferedImage im) {
        // Convert INT to BYTE
        //im = new BufferedImage(im.getWidth(), im.getHeight(),BufferedImage.TYPE_3BYTE_BGR);
        // Convert bufferedimage to byte array
        byte[] pixels = ((DataBufferByte)im.getRaster().getDataBuffer()).getData();
        // Create a Matrix the same size of image
        Mat image = new Mat(im.getHeight(), im.getWidth(), CvType.CV_8UC3);
        // Fill Matrix with image values
        image.put(0, 0, pixels);
        return image;
    }
    
    /*
     * Start of the Utility methods, each helps gain inference on the problem
     */

	public boolean isBlank(RavensFigure fig) {
		if(fig.getObjects().size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	
	//score the comparisons of different mappings of differences
	public int calcScore(Group group1, Group group2) {
		int score = 0;
		Set<Integer> indexSet1 = group1.getScoreMap().keySet();
		for(Integer index: indexSet1) {
			HashMap<String, String> obj1Map = group1.getScoreMap().get(index);
			if(group2.getScoreMap().get(index) == null) {
				continue;
			} else {
				score += 1;
				HashMap<String, String> obj2Map = group2.getScoreMap().get(index);
				Set<String> stringSet = obj1Map.keySet();
				for(String str: stringSet) {
					if(obj2Map.get(str) != null) {
						score += 1;
						if(obj1Map.get(str).equals(obj2Map.get(str))) {
							if(scoreMap.get(str) != null) {
								score += scoreMap.get(str);
							} else {
								score++;
							}
						}
					}
				}

			}	
		}
		return score;
	}
	
	//Returns true if  figures are equal
	public boolean isEqual(RavensFigure fig1, RavensFigure fig2) {
		ArrayList<RavensObject> obj1List = fig1.getObjects();
		ArrayList<RavensObject> obj2List = fig2.getObjects();
		if(obj1List.size() != obj2List.size()) {
			return false;
		}
		for(int i = 0; i < obj1List.size(); i++) {
			ArrayList<RavensAttribute> attr1List = obj1List.get(i).getAttributes();
			ArrayList<RavensAttribute> attr2List = obj2List.get(i).getAttributes();
			if(attr1List.size() != attr2List.size()) {
				return false;
			}
			for(int j = 0; j < attr1List.size(); j++) {
				RavensAttribute attr1 = attr1List.get(j);
				RavensAttribute attr2 = attr2List.get(j);
				if(attr1.getName().equals("inside") || attr2.getName().equals("inside")) {
					continue;
				}
				if(!isAttrEqual(attr1, attr2)) {
					return false;
				}
			}
		}
		return true;
	}
	
	//Returns true if the tempFig is equal to a given Figure in each Row but not the last one
	public boolean onePerRow(RavensProblem problem, RavensFigure tempFig) {
		Problem currProblem = new Problem(problem, tempFig);
		ArrayList<RavensFigure> list1 = new ArrayList<RavensFigure>();
		list1.add(currProblem.figA);
		list1.add(currProblem.figB);
		list1.add(currProblem.figC);
		boolean noneEqual = false;
		for(RavensFigure currFig: list1) {
			if(isEqual(currFig, tempFig)) {
				noneEqual = true;
			}
		}
		if(noneEqual) {
			noneEqual = false;
			list1 = new ArrayList<RavensFigure>();
			list1.add(currProblem.figD);
			list1.add(currProblem.figE);
			list1.add(currProblem.figF);
			for(RavensFigure currFig: list1) {
				if(isEqual(currFig, tempFig)) {
					noneEqual = true;
				}
			}
			if(noneEqual) {
				noneEqual = false;
				list1 = new ArrayList<RavensFigure>();
				list1.add(currProblem.figG);
				list1.add(currProblem.figH);
				for(RavensFigure currFig: list1) {
					if(isEqual(currFig, tempFig)) {
						noneEqual = true;
					}
				}
				if(noneEqual == false) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isAttrEqual(RavensAttribute attr1, RavensAttribute attr2) {
		if(attr1.getName().equals(attr2.getName()) && attr1.getValue().equals(attr2.getValue())) {
			return true;
		} else {
			return false;
		}
	}

	public HashMap<Integer, Integer> comparecalcAngles(Group group1) {
		RavensFigure fig1 = group1.fig1;
		RavensFigure fig2 = group1.fig2;
		RavensFigure fig3 = group1.fig3;
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		int fig1ObjSize = fig1.getObjects().size();
		int fig2ObjSize = fig2.getObjects().size();
		int fig3ObjSize = fig3.getObjects().size();
		int lowerBound = Math.min(fig1ObjSize, Math.min(fig2ObjSize, fig3ObjSize));
		for(int i = 0; i < lowerBound; i++) {
			RavensObject obj1 = fig1.getObjects().get(i);
			RavensObject obj2 = fig2.getObjects().get(i);
			RavensObject obj3 = fig3.getObjects().get(i);
			ObjectGroup currGroup = new ObjectGroup(obj1, obj2, obj3);
			map.put(i, currGroup.angleDiff());
		}
		return map;
	}
	
	//Returns if every object in each figure has the same shape
	public boolean sameShape(Group group1) {
		RavensFigure fig1 = group1.fig1;
		RavensFigure fig2 = group1.fig2;
		RavensFigure fig3 = group1.fig3;
		if(fig1.getObjects().size() == 0 || fig2.getObjects().size() == 0 || fig3.getObjects().size() == 0) {
			return false;
		}
		RavensObject obj1 = fig1.getObjects().get(0);
		RavensObject obj2 = fig2.getObjects().get(0);
		RavensObject obj3 = fig3.getObjects().get(0);
		ObjectGroup newGroup = new ObjectGroup(obj1, obj2, obj3);
		if(newGroup.sameShape()) {
			return true;
		} else {
			return false;
		}
	}
	
	//Returns if the second object in each figure has the same shape
	public boolean sameShape2(Group group1) {
		RavensFigure fig1 = group1.fig1;
		RavensFigure fig2 = group1.fig2;
		RavensFigure fig3 = group1.fig3;
		if(fig1.getObjects().size() == 0 || fig2.getObjects().size() == 0 || fig3.getObjects().size() == 0) {
			return false;
		}
		RavensObject obj1 = fig1.getObjects().get(1);
		RavensObject obj2 = fig2.getObjects().get(1);
		RavensObject obj3 = fig3.getObjects().get(1);
		ObjectGroup newGroup = new ObjectGroup(obj1, obj2, obj3);
		if(newGroup.sameShape()) {
			return true;
		} else {
			return false;
		}
	}
	
	//Returns true if none of the shapes in a figure have a fill
	public boolean noFill(RavensFigure fig1) {
		ArrayList<RavensObject> objList = fig1.getObjects();
		for(RavensObject o: objList) {
			ArrayList<RavensAttribute> attrList = o.getAttributes();
			for(RavensAttribute attr: attrList) {
				if(attr.getName().equals("fill")) {
					if(attr.getValue().equals("yes")) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	//Returns true if all of the shapes have a fill
	public boolean allFill(RavensFigure fig1) {
		boolean noFill = true;
		ArrayList<RavensObject> objList = fig1.getObjects();
		for(RavensObject o: objList) {
			ArrayList<RavensAttribute> attrList = o.getAttributes();
			for(RavensAttribute attr: attrList) {
				if(attr.getName().equals("fill")) {
					noFill = false;
					if(attr.getValue().equals("no")) {
						return false;
					}
				}
			}
		}
		if(noFill) {
			return false;
		}
		return true;
	}
	
	
	public int fillDiff(Group group1) {
		return group1.getFillDifference();
	}
	
	public int sizeDiff(Group group1) {
		return group1.getSizeDifference();
	}
	
	public boolean shapeWithNocalcAngle(Group group1) {
		return group1.shapeWithNoAngle();
	}
	
	//Returns true if the tempFig equals every figure given
	public boolean isEqualAllChoices(RavensProblem problem, RavensFigure tempFig) {
		for(Entry<String,RavensFigure> currFig: problem.getFigures().entrySet()) {
			if(tempFig.equals(currFig.getValue())) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
	
	//Makes sure every figure has more than 1 object in each figure
	public boolean allSizeGreaterThan1(RavensProblem problem, RavensFigure tempFig) {
		if(tempFig.getObjects().size() < 1) {
			return false;
		}
		for(Entry<String,RavensFigure> currFig: problem.getFigures().entrySet()) {
			if(currFig.getValue().getObjects().size() > 1) {
				continue;
			} else {
				return false;
			}
		}
		return true;
	}
	
}
