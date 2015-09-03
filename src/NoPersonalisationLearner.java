package prjContextAwareLearner;

import java.util.ArrayList;

public class NoPersonalisationLearner {
	
	   int totalObsNum=0;
	   ArrayList<Integer> classCount=new ArrayList<Integer>();
	   
	   public NoPersonalisationLearner ()
	   {
		   this.totalObsNum = Parameters.numOfClasses;
		   
		   for (int i=0; i<Parameters.numOfClasses; i++)
		   {
			   classCount.add(1);
		   }	   
	   }
	   
	   public int predict()
	   {
		   ArrayList<Double> classProbabilities=new ArrayList<Double> ();
		   
		   for (int i=0; i<Parameters.numOfClasses; i++)
		   {
			   classProbabilities.add(calculateClassProbability(i));
		   }
		   return calculateMaxProbability(classProbabilities);	   
	   }   
	   
	   private double calculateClassProbability(int classInd)
	   {
		   int currentClassCount=classCount.get(classInd);
		   return (double)currentClassCount/(double)totalObsNum;
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
	   
	   public void learn(int classInd)
	   {
		   this.totalObsNum=this.totalObsNum+1;
		   this.classCount.set(classInd, this.classCount.get(classInd)+1);		   	  
	   }
}
