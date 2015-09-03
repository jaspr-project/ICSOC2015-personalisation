package prjContextAwareLearner;

import java.util.ArrayList;

import org.jfree.ui.RefineryUtilities;

public class SimulatorWithContext {
	
	int numOfRuns=0;
	int attrNoiseLevel=0;
	int ctxNoiseLevel=0;
	int numOfStepsPerConcept=0;
	int accuracyWindowSize=0;	
	int numOfConceptSequenceRepetition=0;
	
	public SimulatorWithContext(int numOfRuns, int attrNoiseLevel, int numOfStepsPerConcept, int accuracyWindowSize, int numOfConceptSequenceRepetition, int ctxNoiseLevel)
	{
		this.numOfRuns=numOfRuns;
		this.attrNoiseLevel=attrNoiseLevel;
		this.ctxNoiseLevel=ctxNoiseLevel;
		this.numOfStepsPerConcept=numOfStepsPerConcept;
		this.accuracyWindowSize=accuracyWindowSize;
		this.numOfConceptSequenceRepetition=numOfConceptSequenceRepetition;
	}

	public void compareContextLearnerBaseLearner()
	{
		ArrayList<Double> baseLearnerAverageAccuracy=new ArrayList<Double> ();
		ArrayList<Double> contextLearnerAverageAccuracy=new ArrayList<Double> ();
		int totalNumOfSteps=(this.numOfStepsPerConcept)*3*(this.numOfConceptSequenceRepetition);
		
		for (int i=0; i <totalNumOfSteps; i++)
		{
			baseLearnerAverageAccuracy.add((double)0);
			contextLearnerAverageAccuracy.add((double)0);
		}
		
		for (int k=0; k<numOfRuns; k++)
		{
		  ObservationGenerator obsGenerator=new ObservationGenerator(this.attrNoiseLevel,this.ctxNoiseLevel);
		  BaseLearner baseLearner=null;
		  //WindowBaseLearner baseLearner=null;
		  ContextAwareLearner contextLearner=null;
		
		  ArrayList<Integer> baseLearnerPrediction=new ArrayList<Integer>();
		  ArrayList<Integer> contextLearnerPrediction=new ArrayList<Integer>();
		  ArrayList<Integer> actualPrediction=new ArrayList<Integer>();
		  
		  for (int i=0; i<totalNumOfSteps; i++)
		  {		
			  System.out.println("iteration"+i);
			  int currentBehaviourInd= this.getBehaviourIndex(i);
			  ArrayList<ArrayList <Integer>> sampleWithContext=obsGenerator.generateSampleWithContext(currentBehaviourInd);
			  ArrayList<Integer> attrSample=sampleWithContext.get(0);
			  ArrayList<Integer> contextSample=sampleWithContext.get(1);
			  int actualClassInd=attrSample.get(Parameters.numOfAttrs);
			  actualPrediction.add(actualClassInd);
			  if (i==0)
			  {
				  baseLearner=new BaseLearner();
				  //baseLearner=new WindowBaseLearner(300);
				  int baseLearnerClassInd=baseLearner.predict(attrSample);
				  baseLearner.learn(attrSample, actualClassInd);
				  
				  contextLearner=new ContextAwareLearner(attrSample,actualClassInd,contextSample);
				 
				  baseLearnerPrediction.add(baseLearnerClassInd);
				  contextLearnerPrediction.add(baseLearnerClassInd);				  
			  }
			  
			  else
			  {
				  baseLearnerPrediction.add(baseLearner.predict(attrSample));
				  contextLearnerPrediction.add(contextLearner.predict(attrSample));				  
				  baseLearner.learn(attrSample, actualClassInd);
				  contextLearner.learn(attrSample, actualClassInd,contextSample);
			  }
		  }
		  
		  ArrayList<Double> baseLearnerAccuracy=AccuracyEstimator.calculateClassifierAccuracy(accuracyWindowSize, baseLearnerPrediction, actualPrediction);
		  ArrayList<Double> contextLearnerAccuracy=AccuracyEstimator.calculateClassifierAccuracy(accuracyWindowSize, contextLearnerPrediction, actualPrediction);
		  
		  for (int i=0; i<totalNumOfSteps; i++)
			{
				baseLearnerAverageAccuracy.set(i, baseLearnerAverageAccuracy.get(i)+ baseLearnerAccuracy.get(i));
				contextLearnerAverageAccuracy.set(i, contextLearnerAverageAccuracy.get(i)+ contextLearnerAccuracy.get(i));
			}
		}
		
		for (int i=0; i<totalNumOfSteps; i++)
		{
			baseLearnerAverageAccuracy.set(i, baseLearnerAverageAccuracy.get(i)/(double)numOfRuns);
			contextLearnerAverageAccuracy.set(i, contextLearnerAverageAccuracy.get(i)/(double)numOfRuns);	
		}
		
		//printResults(actualPrediction,baseLearnerPrediction,noPersLearnerPrediction);
		showResults(baseLearnerAverageAccuracy,"base learner", contextLearnerAverageAccuracy, "context aware learner");
		writeToFile(baseLearnerAverageAccuracy, "base learner", contextLearnerAverageAccuracy, "context aware learner", this.attrNoiseLevel);
	}
	
	private int getBehaviourIndex(int currentIterationInd)
	{
		int behaviourIndicatorReminder=currentIterationInd % (this.numOfStepsPerConcept*3);
		
		if (behaviourIndicatorReminder < this.numOfStepsPerConcept)
		  return 0;
		
		if (behaviourIndicatorReminder >= (this.numOfStepsPerConcept * 2))
			return 2;
		
		return 1;
	}
	
	private void showResults(ArrayList<Double> firstLearnerAverageAccuracy, String firstLearnerName, ArrayList<Double> secondLearnerAverageAccuracy, String secondLearnerName)
	{
		GraphResult graphResult = new GraphResult("mygraph", firstLearnerAverageAccuracy, firstLearnerName, secondLearnerAverageAccuracy, secondLearnerName);
	    graphResult.pack();
        RefineryUtilities.centerFrameOnScreen(graphResult);
        graphResult.setVisible(true);
	}
	
	private void writeToFile(ArrayList<Double> firstLearnerAverageAccuracy, String firstLearnerName, ArrayList<Double> secondLearnerAverageAccuracy, String secondLearnerName, int noiseLevel)
	{
		FileResult firstLearnerFile = new FileResult(firstLearnerName+ noiseLevel+".txt");
		FileResult secondLearnerFile = new FileResult(secondLearnerName+ noiseLevel+".txt");
	    
		for (int i=0; i<firstLearnerAverageAccuracy.size(); i++)
		{
			firstLearnerFile.write(firstLearnerAverageAccuracy.get(i).toString());
			secondLearnerFile.write(secondLearnerAverageAccuracy.get(i).toString());
		}
	    
		firstLearnerFile.close();
		secondLearnerFile.close();
	}
	
}
