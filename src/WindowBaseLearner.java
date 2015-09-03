package prjContextAwareLearner;

import java.util.ArrayList;

public class WindowBaseLearner {
	   int totalObsNum=0;
	   ArrayList<Integer> classCount=new ArrayList<Integer>();
	   ArrayList<ArrayList<AttrValueCount>> classAttrCount=new ArrayList<ArrayList<AttrValueCount>>();
	   int windowSize=0;
	   ArrayList<ArrayList<Integer>> currentWindow=new ArrayList<ArrayList<Integer>>();
	   
	   public WindowBaseLearner (int windowSize)
	   {
		   this.windowSize=windowSize;
		   
		   this.totalObsNum = Parameters.numOfClasses*Parameters.numOfAttrValues;
		   
		   for (int i=0; i<Parameters.numOfClasses; i++)
		   {
			   classCount.add(Parameters.numOfAttrValues);
			   ArrayList<AttrValueCount> classArrayList=new ArrayList<AttrValueCount> ();
			   for (int j=0; j<Parameters.numOfAttrs; j++)
			   {
				   classArrayList.add(new AttrValueCount(Parameters.numOfAttrValues));
			   }
			   classAttrCount.add(classArrayList);
		   }		   
	   }
	   
	   public int predict(ArrayList<Integer> inputValueVector)
	   {
		   ArrayList<Double> classProbabilities=new ArrayList<Double> ();
		   
		   for (int i=0; i<Parameters.numOfClasses; i++)
		   {
			   classProbabilities.add(calculateClassProbability(i,inputValueVector));
		   }
		   return calculateMaxProbability(classProbabilities);	   
	   }   
	   
	   private double calculateClassProbability(int classInd, ArrayList<Integer> inputValueVector)
	   {
		   double classProbability=1;
		   int currentClassCount=classCount.get(classInd);
		   if (currentClassCount==0)
			   return 0;
		   
		   classProbability=classProbability*((double)currentClassCount/(double)totalObsNum);
		   ArrayList<AttrValueCount> currentClassArray=classAttrCount.get(classInd);
		   
		   for (int i=0; i<Parameters.numOfAttrs; i++)
		   {
			   int currAttrValue=inputValueVector.get(i);
			   int currAttrValueCount= currentClassArray.get(i).getValueCount(currAttrValue);
			   classProbability=classProbability*((double)currAttrValueCount/(double)currentClassCount);
		   }
		   return classProbability;
	   }
	   
	   private int calculateMaxProbability(ArrayList<Double> classProbabilities)
	   {
		   int maxClassInd=0;
		   double maxProbability=0;
		   
		   for (int i=0; i<Parameters.numOfClasses; i++)
		   {
			   double currentClassProbability=classProbabilities.get(i);
			   if (currentClassProbability > maxProbability)
			   {
				   maxProbability=currentClassProbability;
				   maxClassInd=i;
			   }		   
		   }
		   return maxClassInd;
	   }
	   
	   public void learn(ArrayList<Integer> inputValueVector, int classInd)
	   {
		   System.out.println("window size"+ this.currentWindow.size());
		   this.currentWindow.add(inputValueVector);
		   this.classCount.set(classInd, this.classCount.get(classInd)+1);
		   ArrayList<AttrValueCount> classArrayList=this.classAttrCount.get(classInd);
		   for (int i=0; i<Parameters.numOfAttrs; i++)
		   {
			   int currAttrValue=inputValueVector.get(i);
			   classArrayList.get(i).increaseValueCount(currAttrValue);
		   }		   
		   
		   if (this.currentWindow.size() <= this.windowSize)
		   {
			   this.totalObsNum=this.totalObsNum+1;
		   }	   
		   else
		   {
			   System.out.println("total observations"+ this.totalObsNum);
			 
			   ArrayList<Integer> sampleToRemove=this.currentWindow.get(0);
			   this.currentWindow.remove(0);
			   int sampleToRemoveClassInd= sampleToRemove.get(Parameters.numOfAttrs);
			   this.classCount.set(sampleToRemoveClassInd, this.classCount.get(sampleToRemoveClassInd)-1);
			   ArrayList<AttrValueCount> classToRemoveArrayList=this.classAttrCount.get(sampleToRemoveClassInd);
			   for (int i=0; i<Parameters.numOfAttrs; i++)
			   {
				   int attrValueToRemove=sampleToRemove.get(i);
				   classToRemoveArrayList.get(i).decreaseValueCount(attrValueToRemove);
			   }
		   }
	   }			   
}
