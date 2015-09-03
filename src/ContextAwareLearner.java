package prjContextAwareLearner;

import java.util.ArrayList;

public class ContextAwareLearner {
	
	ArrayList<ValueModel> historicalModels=new ArrayList<ValueModel>();
	ValueModel tempraryModel;
	boolean isTempraryModel=false;
	ValueModel activeModel;
	ArrayList<ArrayList<Integer>> testWindow;
	int obsNum=15;
	double threshold=0.5;
	
	public ContextAwareLearner(ArrayList<Integer> inputValueVector, int classInd, ArrayList<Integer> ctxValueVector)
	{
		ValueModel firstValueModel=new ValueModel(inputValueVector,classInd,ctxValueVector);
		activeModel=firstValueModel;
		historicalModels.add(firstValueModel);		
	}
	
	public int predict(ArrayList<Integer> inputValueVector)
	   { 
		 return this.activeModel.learner.predict(inputValueVector);
	   }
	
	public void learn (ArrayList<Integer> inputValueVector, int classInd, ArrayList<Integer> ctxValueVector)
	{
		
		if (this.isTempraryModel)
		{
			if (!(this.tempraryModel.ctxCondition.subsumes(ctxValueVector)))
			{
				this.tempraryModel=null;
				this.isTempraryModel=false;
				this.testWindow.clear();
				learnNoTemporary(inputValueVector, classInd, ctxValueVector);
			}
			else
			{ 
				this.tempraryModel.learner.learn(inputValueVector, classInd);
				this.testWindow.add(inputValueVector);
				if (this.testWindow.size()==this.obsNum)
				{
					ValueModel similarHistoricalModel=this.foundSimilarHistoricalModel();
					if (similarHistoricalModel != null)
					{
						this.activeModel=similarHistoricalModel;
						this.activeModel.ctxCondition.generalise(this.tempraryModel.ctxCondition);
					}
					else
					{
						this.historicalModels.add(this.tempraryModel);
						this.activeModel=this.tempraryModel;
					}
					this.tempraryModel=null;
					this.isTempraryModel=false;
					this.testWindow.clear();
				}			
			}
		}
		else
			learnNoTemporary(inputValueVector, classInd, ctxValueVector);
	}
	
	private void learnNoTemporary(ArrayList<Integer> inputValueVector, int classInd, ArrayList<Integer> ctxValueVector)
	{	
		if (this.activeModel.ctxCondition.subsumes(ctxValueVector))
		{
			this.activeModel.learner.learn(inputValueVector, classInd);
		}
		else
		{
			System.out.println("before matching model");
			ValueModel matchingHistoricalModel=this.getSubsumingHistoricalModel(ctxValueVector);
			System.out.println("after matching model");
			if (matchingHistoricalModel!=null)
			{
				System.out.println("matching model found");
				this.activeModel=matchingHistoricalModel;
				this.activeModel.learner.learn(inputValueVector, classInd);
			}
			else
			{
				System.out.println("matching model not found");
				this.isTempraryModel=true;
				this.tempraryModel=new ValueModel(inputValueVector, classInd, ctxValueVector);
				if (this.testWindow !=null)
					this.testWindow.clear();
				testWindow=new ArrayList<ArrayList<Integer>>();
				this.testWindow.add(inputValueVector);
			}
		}
	}
	
	private ValueModel getSubsumingHistoricalModel(ArrayList<Integer> ctxValueVector)
	{
		for (int i=0; i<this.historicalModels.size(); i++)
		{
			ValueModel currentHistoricalModel=this.historicalModels.get(i);
			if (currentHistoricalModel.ctxCondition.subsumes(ctxValueVector))
				return currentHistoricalModel;			
		}
		return null;
	}
	
	private ValueModel foundSimilarHistoricalModel()
	{
		ArrayList<Integer> historicalModelScores=new ArrayList<Integer>();
		for (int i=0; i<this.historicalModels.size(); i++)
			historicalModelScores.add(0);
		
		for (int i=0; i<this.testWindow.size(); i++)
		{
			ArrayList<Integer> currrentInputVector=this.testWindow.get(i);
			int temporalModelClassInd=this.tempraryModel.learner.predict(currrentInputVector);
			for (int k=0; k<this.historicalModels.size(); k++)
			{
				int historicalModelClassInd=this.historicalModels.get(k).learner.predict(currrentInputVector);
				if (temporalModelClassInd==historicalModelClassInd)
				{
					historicalModelScores.set(k, historicalModelScores.get(k)+1);
				}
				else
				{
					historicalModelScores.set(k, historicalModelScores.get(k)-1);
				}
			}		
		}
		
		int maxHistoricalModelInd= this.getMaxValueIndInList(historicalModelScores);
		if ((((double)historicalModelScores.get(maxHistoricalModelInd))/((double)this.obsNum)) >= this.threshold)
			return this.historicalModels.get(maxHistoricalModelInd);

		else
			return null;		
	}
	
	private int getMaxValueIndInList(ArrayList<Integer> valueList)
	{
		int maxInd=-1;
		int maxValue=-1000;
		for (int i=0; i<valueList.size(); i++)
		{
		   int currValue=valueList.get(i);
		   if (currValue > maxValue)
		   {
	          maxInd=i;
	          maxValue=currValue;
		   }
		}
		return maxInd;
	}

}
