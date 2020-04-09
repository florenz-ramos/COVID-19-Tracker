package com.calyx.dev.app;
import android.os.AsyncTask;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.HttpURLConnection;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.Reader;
import android.app.ProgressDialog;

public class DataFetcher extends AsyncTask<Void,Void,Void>
{
  private String jsonText;
	private ProgressDialog dialog;
	@Override
	protected Void doInBackground(Void[] p1)
	{
		String urlLink = "https://pomber.github.io/covid19/timeseries.json";
		try
		{
		
			URL url = new URL(urlLink);
			HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
			InputStream inputStream = httpURLConnection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			jsonText = readContent(reader);
		}
		catch (MalformedURLException |IOException e)
		{
			e.printStackTrace();
		}	
		return null;
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
		
		//dialog = ProgressDialog.show(MainActivity.this, "Fetching records", "Please wait...");
			
	}

	
	
	
	@Override
	protected void onPostExecute(Void result)
	{
		// TODO: Implement this method
		super.onPostExecute(result);
		dialog.dismiss();
		
		//MainActivity.textView.setText(jsonText);
		
		//MainActivity.jsonString = jsonText;
		
	}
	
	
}
