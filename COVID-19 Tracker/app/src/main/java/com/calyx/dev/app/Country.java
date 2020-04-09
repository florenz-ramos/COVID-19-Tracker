package com.calyx.dev.app;
import java.util.ArrayList;

public class Country
{
	private String Name;
	private ArrayList<Condition> Conditons;


	public Country(String name,ArrayList<Condition> conditions){
		this.Name=name;
		this.Conditons=conditions;
	}

	public void setConditons(ArrayList<Condition> conditons)
	{
		Conditons = conditons;
	}

	public ArrayList<Condition> getConditons()
	{
		return Conditons;
	}

	public void setName(String name)
	{
		Name = name;
	}

	public String getName()
	{
		return Name;
	}
	
}
