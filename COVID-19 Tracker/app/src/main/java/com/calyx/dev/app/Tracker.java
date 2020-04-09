package com.calyx.dev.app;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import org.json.JSONObject;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONArray;
import java.io.Reader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.net.URL;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Comparator;

public class Tracker
{
	public List<String> listCountries = new ArrayList<>();
	
	private void sortDates(Country country){
    Collections.sort(country.getConditons(),Collections.reverseOrder(
											 new Comparator<Condition>(){
												 @Override
												 public int compare(Condition p1, Condition p2)
												 {
													 // TODO: Implement this method
													 return -1;
												 }
											 }

										 ));
	}


	public ArrayList<Country> getCountries(String jsonText)
	{
		ArrayList<Country> countries = new ArrayList<>();
		try
		{
			JSONObject jsonData = readJsonFromString(jsonText);
			for (int i =0;i < jsonData.names().length(); i++)
			{
				
			
				
				String key = jsonData.names().getString(i);
				listCountries.add(key);
				String value = jsonData.getString(key);
				ArrayList<Condition> conditions = getConditions(key, value);
				
				Country country = new Country(key,conditions);
				
				sortDates(country);
				
				countries.add(country);
			}	
		}
		catch (JSONException e)
		{
			e.printStackTrace();	
		}	
		return countries;

	}

	private ArrayList<Condition> getConditions(String key, String value)
	{

		ArrayList<Condition> conditions = new ArrayList<>();
		try
		{
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"root\": ");
			builder.append(value);
			builder.append("}");
		  JSONObject jsonObject = new JSONObject(builder.toString());
			JSONArray jsonArray = jsonObject.getJSONArray("root");
			for (int i = 0; i < jsonArray.length();i++)
			{
				JSONObject jsonChildObject = jsonArray.getJSONObject(i);
				String date = jsonChildObject.getString("date");
				int confirmed = jsonChildObject.getInt("confirmed");
				int recovered = jsonChildObject.getInt("recovered");
				int deaths = jsonChildObject.getInt("deaths");
				conditions.add(new Condition(date, confirmed, recovered, deaths));	
			}	
		}
		catch (JSONException e)
		{
			e.printStackTrace();

		}

		return conditions;

	}
	private JSONObject readJsonFromString(String jsonText) throws JSONException
	{
			JSONObject json = new JSONObject(jsonText);
			return json;
	
	}
	
	
	
}
