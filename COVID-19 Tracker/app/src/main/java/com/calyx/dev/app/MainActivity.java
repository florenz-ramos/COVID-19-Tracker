package com.calyx.dev.app;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import com.calyx.dev.app.MainActivity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.Adapter;
import android.view.View;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements SpinnerItemListener
{	
		ArrayAdapter<String> adapter;
		ConditionAdapter conditionAdapter;
	  Spinner spinner;
		ListView listView;
		String url = "https://pomber.github.io/covid19/timeseries.json";
	  public static String jsonString;
		public ArrayList<Country> ListCountries;
		
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
			
				try{	
				 JsonFetcher fetcher =new JsonFetcher(this);
				 fetcher.execute();
				 }
				 catch(Exception ex){
					ex.printStackTrace();
				}		
    }
		
	@Override
	public void OnListViewItemResult(ArrayList<Country> countries)
	{
		if(countries.size() == 0 || countries == null){
			return;
		}
		
		listView = findViewById(R.id.listConditions);
		
		Search(countries,"Afghanistan");
	}
	
	private void Search(ArrayList<Country> countries,String name){
		ArrayList<Condition> conditions = SearchCountry(countries,name);

		if(conditions == null || conditions.size()==0) return;

		conditionAdapter = new ConditionAdapter(getApplicationContext(),R.layout.list_items,conditions);
		listView.setAdapter(conditionAdapter);
	}
	
	
	
	private ArrayList<Condition> SearchCountry(ArrayList<Country> countries,String name){

		for(Country country :countries){
			if(!country.getName().equals(name))
				continue;
			return country.getConditons();

		}
		return null;
	}
	
	private void sortDates(Country country){
    Collections.sort(country.getConditons(),
		Collections.reverseOrder(
											 new Comparator<Condition>(){
												 @Override
												 public int compare(Condition p1, Condition p2)
												 {
													 return -1;
												 }
											 }
										 ));
	}

	@Override
	public void OnItemResult(List<String> countryNames)
	{
		if(countryNames.size()==0) return;

		spinner = findViewById(R.id.spinner);
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,countryNames);
		spinner.setAdapter(adapter);

		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

				@Override
				public void onNothingSelected(AdapterView<?> p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
				{
					String country = p1.getItemAtPosition(p3).toString();
					Toast.makeText(getApplicationContext(),country,Toast.LENGTH_LONG).show();
					Search(ListCountries,country);
					
					
				}
			});


	}
	
		
		
			
	public class JsonFetcher extends AsyncTask<Void,Void,ArrayList<Country>>
	{
		private String jsonText;
		private ProgressDialog dialog;
		public ArrayList<Country> countries;
		public List<String> countryNames;
		
		private SpinnerItemListener spinnerItemListener;
		
		public JsonFetcher(SpinnerItemListener listener){
		this.spinnerItemListener = listener;	
		}
		
		
		
		
		Tracker tracker = new Tracker();
		
		@Override
		protected ArrayList<Country> doInBackground(Void[] p1)
		{
			String urlLink = "https://pomber.github.io/covid19/timeseries.json";
			try
			{

				URL url = new URL(urlLink);
				HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
				InputStream inputStream = httpURLConnection.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
				jsonText = readContent(reader);
			  countries = tracker.getCountries(jsonText);
				countryNames = tracker.listCountries;
			}
			catch (MalformedURLException |IOException e)
			{
				e.printStackTrace();
			}	
			return countries;
		}

		private String readContent(Reader reader)
		{
			StringBuilder sb = new StringBuilder();
			int cp;
			try
			{
				while ((cp = reader.read()) != -1)
				{
					sb.append((char) cp);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			return sb.toString();
		}

		@Override
		protected void onPreExecute()
		{
			dialog = ProgressDialog.show(MainActivity.this, "Fetching records", "Please wait...");
		}
		
		@Override
		protected void onPostExecute(ArrayList<Country> result)
		{
			
			dialog.dismiss();
			
			if(result == null){
				Toast.makeText(MainActivity.this,"Bad JSON Response",Toast.LENGTH_LONG).show();
				return;
			}
			ListCountries = countries;
			
			spinnerItemListener.OnItemResult(countryNames);
			spinnerItemListener.OnListViewItemResult(countries);
			
			
		}	
	
	}
	
}
