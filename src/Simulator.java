package prjContextAwareLearner;
import java.util.ArrayList;

import org.jfree.ui.RefineryUtilities;

public class Simulator {
	
	public void compareBaseLearnerNoPersonalisationLearner(int noiseLevel, int numOfSteps, int numOfRuns, int accuracyWindowSize)
	{
		ArrayList<Double> baseLearnerAverageAccuracy=new ArrayList<Double> ();
		ArrayList<Double> noPersAverageAccuracy=new ArrayList<Double> ();
		
		for (int i=0; i<numOfSteps; i++)
		{
			baseLearnerAverageAccuracy.add((double)0);
			noPersAverageAccuracy.add((double)0);
		}
		
		for (int k=0; k<numOfRuns; k++)
		{
		  ObservationGenerator obsGenerator=new ObservationGenerator(noiseLevel);
		  BaseLearner baseLearner=new BaseLearner();
		  NoPersonalisationLearner noPersLearner=new NoPersonalisationLearner();
		
		  ArrayList<Integer> baseLearnerPrediction=new ArrayList<Integer>();
		  ArrayList<Integer> noPersLearnerPrediction=new ArrayList<Integer>();
		  ArrayList<Integer> actualPrediction=new ArrayList<Integer>();
		
		
		  for (int i=0; i<numOfSteps; i++)
		  {
			  ArrayList<Integer> sample= obsGenerator.generateSampleFromBehaviour2();
			  int ActualClassInd=sample.get(Parameters.numOfAttrs);
			  baseLearnerPrediction.add(baseLearner.predict(sample));
			  noPersLearnerPrediction.add(noPersLearner.predict());
			  actualPrediction.add(ActualClassInd);
			  
			  baseLearner.learn(sample, ActualClassInd);
			  noPersLearner.learn(ActualClassInd);
		  }
		  ArrayList<Double> baseLearnerAccuracy=AccuracyEstimator.calculateClassifierAccuracy(accuracyWindowSize, baseLearnerPrediction, actualPrediction);
		  ArrayList<Double> noPersLearnerAccuracy=AccuracyEstimator.calculateClassifierAccuracy(accuracyWindowSize, noPersLearnerPrediction, actualPrediction);
		  
		  for (int i=0; i<numOfSteps; i++)
			{
				baseLearnerAverageAccuracy.set(i, baseLearnerAverageAccuracy.get(i)+ baseLearnerAccuracy.get(i));
				noPersAverageAccuracy.set(i, noPersAverageAccuracy.get(i)+ noPersLearnerAccuracy.get(i));
			}
		}
		
		for (int i=0; i<numOfSteps; i++)
		{
			baseLearnerAverageAccuracy.set(i, baseLearnerAverageAccuracy.get(i)/(double)numOfRuns);
  		    noPersAverageAccuracy.set(i, noPersAverageAccuracy.get(i)/(double)numOfRuns);	
		}
		
		//printResults(actualPrediction,baseLearnerPrediction,noPersLearnerPrediction);
		showResults(baseLearnerAverageAccuracy, noPersAverageAccuracy);
		writeToFile(baseLearnerAverageAccuracy, noPersAverageAccuracy,noiseLevel);
	}
	
	private void showResults(ArrayList<Double> baseLearnerAverageAccuracy, ArrayList<Double> noPersAverageAccuracy)
	{
		GraphResult graphResult = new GraphResult("mygraph", baseLearnerAverageAccuracy, "Base Leanrer Accuracy", noPersAverageAccuracy, "No Personalisation Accuracy");
	    graphResult.pack();
        RefineryUtilities.centerFrameOnScreen(graphResult);
        graphResult.setVisible(true);
	}
	
	private void writeToFile(ArrayList<Double> baseLearnerAverageAccuracy, ArrayList<Double> noPersAverageAccuracy, int noiseLevel)
	{
		FileResult baseLearnerFile = new FileResult("baseLearner_"+ noiseLevel+".txt");
		FileResult noPersLearnerFile = new FileResult("noPersLearner_"+ noiseLevel+".txt");
	    
		for (int i=0; i<baseLearnerAverageAccuracy.size(); i++)
		{
			baseLearnerFile.write(baseLearnerAverageAccuracy.get(i).toString());
			noPersLearnerFile.write(noPersAverageAccuracy.get(i).toString());
		}
	    
	    baseLearnerFile.close();
		noPersLearnerFile.close();
	}
	
	/*private void printResults(ArrayList<Integer> actualPrediction, ArrayList<Integer> baseLearnerPrediction,ArrayList<Integer> noPersLearnerPrediction)
	{
		for (int i=0; i< actualPrediction.size(); i++)
		{
			System.out.println("Run: "+i);
		    System.out.println("Actual Prediction: "+actualPrediction.get(i));
		    System.out.println("Base Prediction: "+baseLearnerPrediction.get(i));
		    System.out.println("No Personalisation Prediction: "+noPersLearnerPrediction.get(i));
		}
		
		     GraphResult graphResult = new GraphResult("mygraph", baseLearnerPrediction, "Base Prediction",noPersLearnerPrediction, "No Personalisation");
		     graphResult.pack();
	         RefineryUtilities.centerFrameOnScreen(graphResult);
	         graphResult.setVisible(true);
	}*/
}

