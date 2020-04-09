public class Condition
{
	private String date;
	private int confirmed;
	private int recovered;
	private int deaths;

	public Condition(String date,int confirmed,int recovered,int deaths){
		this.date=date;
		this.confirmed=confirmed;
		this.recovered=recovered;
		this.deaths = deaths;
		
	}
	
	
	
	public void setDeaths(int deaths)
	{
		this.deaths = deaths;
	}

	public int getDeaths()
	{
		return deaths;
	}

	public void setRecovered(int recovered)
	{
		this.recovered = recovered;
	}

	public int getRecovered()
	{
		return recovered;
	}

	public void setConfirmed(int confirmed)
	{
		this.confirmed = confirmed;
	}

	public int getConfirmed()
	{
		return confirmed;
	}
	
	public void setDate(String date)
	{
		this.date = date;
	}

	public String getDate()
	{
		return date;
	}
	

	
}
