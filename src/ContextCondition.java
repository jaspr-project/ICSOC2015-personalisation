package prjContextAwareLearner;

import java.util.ArrayList;

public class ContextCondition {
	
	ArrayList<AndCondition> andClauses=new ArrayList<AndCondition> ();
	
	public ContextCondition(ArrayList<Integer> contextValueVector)
	{
		andClauses.add(new AndCondition(contextValueVector));
	}
	
	public boolean subsumes(ArrayList<Integer> contextValueVector)
	{
		for (int i=0; i<this.andClauses.size(); i++)
		{
			if (this.andClauses.get(i).subsumes(contextValueVector))
				return true;
		}
		return false;
	}
	
	public void generalise(ContextCondition ctxCondition)
	{
		for (int i=0; i<ctxCondition.andClauses.size(); i++)
		{
			this.andClauses.add(ctxCondition.andClauses.get(i));
		}
	}
}
