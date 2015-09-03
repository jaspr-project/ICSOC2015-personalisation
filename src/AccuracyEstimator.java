package prjContextAwareLearner;

import java.util.ArrayList;

public class AccuracyEstimator {

	public static ArrayList<Double> calculateClassifierAccuracy(int windowSize, ArrayList<Integer> classifierPredictions, ArrayList<Integer> actualPredictions)
	{
		ArrayList<Double> accuracyArray=new ArrayList<Double>();
		for (int i=0; i<classifierPredictions.size(); i++)
		{
			int numOfObsConsidered=Math.min(windowSize, i+1);
			int startIndForCalculation= (i-numOfObsConsidered)+1;
			int numOfCorrectPredictions=0;
			
			for (int j=0; j<numOfObsConsidered; j++)
			{
				if (classifierPredictions.get(startIndForCalculation+j).equals(actualPredictions.get(startIndForCalculation+j)))
				{
					numOfCorrectPredictions=numOfCorrectPredictions+1;
				}
			}
			
			accuracyArray.add((double)numOfCorrectPredictions/(double)numOfObsConsidered);		
		}
		
		return accuracyArray;
	}
	
}
