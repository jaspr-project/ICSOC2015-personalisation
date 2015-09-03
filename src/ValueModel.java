package prjContextAwareLearner;

import java.util.ArrayList;

public class ValueModel {
	
	public ContextCondition ctxCondition;
	public BaseLearner learner;
	
	public ValueModel (ArrayList<Integer> inputValueVector, int classInd, ArrayList<Integer> ctxValueVector)
	{
		ctxCondition=new ContextCondition(ctxValueVector);
		learner=new BaseLearner();
		learner.learn(inputValueVector, classInd);
	}
	
}
