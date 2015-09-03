package prjContextAwareLearner;

import java.util.ArrayList;

public class AndCondition {
    ArrayList <ArrayList <Integer>> clause =new ArrayList<ArrayList <Integer>> ();
    
    public AndCondition(ArrayList<Integer> contextValueVector)
    {
    	for (int i=0; i<Parameters.numOfCtxAttrs; i++)
    	{
    		ArrayList<Integer> valueList= new ArrayList <Integer>();
    		valueList.add(contextValueVector.get(i));    		
    		clause.add(valueList);
    	}
    }
    
    public boolean subsumes(ArrayList<Integer> contextValueVector)
    {
    	for (int i=0; i<Parameters.numOfCtxAttrs; i++)
    	{
    		if (!(checkValueInList(contextValueVector.get(i), clause.get(i))))
    			return false;
    	}
    	return true;
    }
    
    
    private boolean checkValueInList(int value, ArrayList<Integer> valueList)
    {
    	for (int i=0; i<valueList.size(); i++)
    	{
    		if (valueList.get(i).equals(value))
    			return true;
    	}
    	return false;
    }
    
}
