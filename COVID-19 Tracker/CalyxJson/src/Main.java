import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;


public class Main
{
	static String url = "https://pomber.github.io/covid19/timeseries.json";
	static List<Country> countries = getCountries(url);

	public static void main(String[] args)
	{
		List<Condition> conditions = SearchCountry("Afghanistan");	
	}
	
	
	private static List<Condition> SearchCountry(String countryName){
		for(Country country : countries){
			if(!country.getName().equals(countryName))
				continue;
				sortDates(country);
				return country.getConditons();	
		}
		return null;
	}
	
	private static void sortDates(Country country){

		Collections.sort(country.getConditons(), Collections.reverseOrder(
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


	public static ArrayList<Country> getCountries(String url)
	{
		ArrayList<Country> countries = new ArrayList<>();
		try
		{
			JSONObject jsonData = readJsonFromUrl(url);
			for (int i =0;i < jsonData.names().length(); i++)
			{
				String key = jsonData.names().getString(i);
				String value = jsonData.getString(key);
				ArrayList<Condition> conditions = getConditions(key, value);

				countries.add(new Country(key, conditions));
			}	
		}
		catch (IOException | JSONException e)
		{
			e.printStackTrace();	
		}	
		return countries;

	}

	private static ArrayList<Condition> getConditions(String key, String value)
	{

		ArrayList<Condition> conditions = new ArrayList<>();
		try
		{
			StringBuilder builder = new StringBuilder();
			builder.append("{");
			builder.append("\"root\": ");
			builder.append(value);
			builder.append("}");

			//System.out.println(builder.toString());
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

	private static String readAll(Reader rd) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1)
		{
			sb.append((char) cp);
		}
		return sb.toString();
	}

	private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException
	{
		InputStream is = new URL(url).openStream();
		try
		{
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		}
		finally
		{
			is.close();
		}
	}

}
