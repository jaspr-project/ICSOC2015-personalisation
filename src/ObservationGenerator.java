package prjContextAwareLearner;

import java.util.ArrayList;

import java.util.Random;

public class ObservationGenerator {
	
	int noiseLevel=0;
	int ctxNoiseLevel=0;
	ArrayList<Random> attrGenerators=new ArrayList<Random> ();
	Random noiseGenerator=new Random();
	Random ctxNoiseGenerator=new Random();
	Random qualityGenerator=new Random();	
	ArrayList<Random> ctxAttrGenerators=new ArrayList<Random> ();
	
	public ObservationGenerator(int noiseLevel)
	{
		this.noiseLevel=noiseLevel;
		for (int i=0; i<Parameters.numOfAttrs; i++)
		{
			attrGenerators.add(new Random());
		}
		
		for (int i=0; i<Parameters.numOfCtxAttrs; i++)
		{
			ctxAttrGenerators.add(new Random());
		}
	}
	
	public ObservationGenerator(int noiseLevel, int ctxNoiseLevel)
	{
		this.noiseLevel=noiseLevel;
		this.ctxNoiseLevel=ctxNoiseLevel;
		for (int i=0; i<Parameters.numOfAttrs; i++)
		{
			attrGenerators.add(new Random());
		}
		
		for (int i=0; i<Parameters.numOfCtxAttrs; i++)
		{
			ctxAttrGenerators.add(new Random());
		}
	}
	
	public ArrayList<Integer> generateSampleFromBehaviour1()
	{
		ArrayList<Integer> sample=new ArrayList<Integer>();
		
		for (int i=0; i<Parameters.numOfAttrs; i++)
		{
			sample.add(attrGenerators.get(i).nextInt(Parameters.numOfAttrValues));
		}
		
		int classInd=-1;
		
		int currentNoiseInd=this.noiseGenerator.nextInt(100);
		if ((currentNoiseInd+1) > noiseLevel)
		{
			
		if ((sample.get(0).equals(0))&&(sample.get(1).equals(0)))
			classInd=0;
		else
			classInd=1;
		}
		else
			classInd=qualityGenerator.nextInt(Parameters.numOfClasses);
		
		sample.add(classInd);
		return sample;
	}
	
	public ArrayList<Integer> generateSampleFromBehaviour2()
	{
        ArrayList<Integer> sample=new ArrayList<Integer>();
		
		for (int i=0; i<Parameters.numOfAttrs; i++)
		{
			sample.add(attrGenerators.get(i).nextInt(Parameters.numOfAttrValues));
		}
		
		int classInd=-1;
		
		int currentNoiseInd=this.noiseGenerator.nextInt(100);
		if ((currentNoiseInd+1) > noiseLevel)
		{
			
		if ((sample.get(1).equals(1))||(sample.get(2).equals(1)))
			classInd=0;
		else
			classInd=2;
		}
		else
			classInd=qualityGenerator.nextInt(Parameters.numOfClasses);
		
		sample.add(classInd);
		return sample;
	}
	
	public ArrayList<Integer> generateSampleFromBehaviour3()
	{
        ArrayList<Integer> sample=new ArrayList<Integer>();
		
		for (int i=0; i<Parameters.numOfAttrs; i++)
		{
			sample.add(attrGenerators.get(i).nextInt(Parameters.numOfAttrValues));
		}
		
		int classInd=-1;
		
		int currentNoiseInd=this.noiseGenerator.nextInt(100);
		if ((currentNoiseInd+1) > noiseLevel)
		{
			
		if ((sample.get(0).equals(1))||(sample.get(0).equals(2)))
			classInd=3;
		else
			classInd=4;
		}
		else
			classInd=qualityGenerator.nextInt(Parameters.numOfClasses);
		
		sample.add(classInd);
		return sample;
	}
	
	private ArrayList<Integer> generateContextForBehaviour1()
	{
		ArrayList<Integer> contextValueVector=new ArrayList<Integer>();
		int currentCtxNoiseInd=this.ctxNoiseGenerator.nextInt(100);
		if ((currentCtxNoiseInd+1) > this.ctxNoiseLevel)
		{
			for (int i=0; i<Parameters.numOfCtxAttrs; i++)
			  contextValueVector.add(0);
		}
		else
		{
			for (int i=0; i<Parameters.numOfCtxAttrs; i++)
			  contextValueVector.add(ctxAttrGenerators.get(i).nextInt(Parameters.numOfCtxAttrValues));
		}	
		return contextValueVector;
	}
	
	private ArrayList<Integer> generateContextForBehaviour2()
	{
		ArrayList<Integer> contextValueVector=new ArrayList<Integer>();
		int currentCtxNoiseInd=this.ctxNoiseGenerator.nextInt(100);
		if ((currentCtxNoiseInd+1) > this.ctxNoiseLevel)
		{
			for (int i=0; i<Parameters.numOfCtxAttrs; i++)
			  contextValueVector.add(1);
		}
		else
		{
			for (int i=0; i<Parameters.numOfCtxAttrs; i++)
			  contextValueVector.add(ctxAttrGenerators.get(i).nextInt(Parameters.numOfCtxAttrValues));
		}	
		return contextValueVector;
	}
	
	private ArrayList<Integer> generateContextForBehaviour3()
	{
		ArrayList<Integer> contextValueVector=new ArrayList<Integer>();
		int currentCtxNoiseInd=this.ctxNoiseGenerator.nextInt(100);
		if ((currentCtxNoiseInd+1) > this.ctxNoiseLevel)
		{
			for (int i=0; i<Parameters.numOfCtxAttrs; i++)
			  contextValueVector.add(2);
		}
		else
		{
			for (int i=0; i<Parameters.numOfCtxAttrs; i++)
			  contextValueVector.add(ctxAttrGenerators.get(i).nextInt(Parameters.numOfCtxAttrValues));
		}	
		return contextValueVector;
	}
	
	private ArrayList<ArrayList<Integer>> generateSampleWithContextFromBehaviour1()
	{
		ArrayList<Integer> sample=new ArrayList<Integer>();
		
		for (int i=0; i<Parameters.numOfAttrs; i++)
		{
			sample.add(attrGenerators.get(i).nextInt(Parameters.numOfAttrValues));
		}
		
		int classInd=-1;
		
		int currentNoiseInd=this.noiseGenerator.nextInt(100);
		if ((currentNoiseInd+1) > noiseLevel)
		{
			
		if ((sample.get(0).equals(0))&&(sample.get(1).equals(0)))
			classInd=0;
		else
			classInd=1;
		}
		else
			classInd=qualityGenerator.nextInt(Parameters.numOfClasses);
		
		sample.add(classInd);
		
		ArrayList<ArrayList<Integer>> sampleWithContext=new ArrayList<ArrayList<Integer>>();
		sampleWithContext.add(sample);
		sampleWithContext.add(this.generateContextForBehaviour1());
		return sampleWithContext;
	}
	
	private ArrayList<ArrayList <Integer>> generateSampleWithContextFromBehaviour2()
	{
        ArrayList<Integer> sample=new ArrayList<Integer>();
		
		for (int i=0; i<Parameters.numOfAttrs; i++)
		{
			sample.add(attrGenerators.get(i).nextInt(Parameters.numOfAttrValues));
		}
		
		int classInd=-1;
		
		int currentNoiseInd=this.noiseGenerator.nextInt(100);
		if ((currentNoiseInd+1) > noiseLevel)
		{
			
		if ((sample.get(1).equals(1))||(sample.get(2).equals(1)))
			classInd=0;
		else
			classInd=2;
		}
		else
			classInd=qualityGenerator.nextInt(Parameters.numOfClasses);
		
		sample.add(classInd);
		
		ArrayList<ArrayList<Integer>> sampleWithContext=new ArrayList<ArrayList<Integer>>();
		sampleWithContext.add(sample);
		sampleWithContext.add(this.generateContextForBehaviour2());
		return sampleWithContext;
	}
	
	private ArrayList<ArrayList <Integer>> generateSampleWithContextFromBehaviour3()
	{
        ArrayList<Integer> sample=new ArrayList<Integer>();
		
		for (int i=0; i<Parameters.numOfAttrs; i++)
		{
			sample.add(attrGenerators.get(i).nextInt(Parameters.numOfAttrValues));
		}
		
		int classInd=-1;
		
		int currentNoiseInd=this.noiseGenerator.nextInt(100);
		if ((currentNoiseInd+1) > noiseLevel)
		{
			
		if ((sample.get(0).equals(1))||(sample.get(0).equals(2)))
			classInd=3;
		else
			classInd=4;
		}
		else
			classInd=qualityGenerator.nextInt(Parameters.numOfClasses);
		
		sample.add(classInd);
		ArrayList<ArrayList<Integer>> sampleWithContext=new ArrayList<ArrayList<Integer>>();
		sampleWithContext.add(sample);
		sampleWithContext.add(this.generateContextForBehaviour3());
		return sampleWithContext;
	}
	
	public ArrayList<ArrayList<Integer>> generateSampleWithContext(int behaviourIndex)
	{
		if (behaviourIndex==0)
			return this.generateSampleWithContextFromBehaviour1();
		
		if (behaviourIndex==1)
			return this.generateSampleWithContextFromBehaviour2();
		
		return this.generateSampleWithContextFromBehaviour3();
	}

}
