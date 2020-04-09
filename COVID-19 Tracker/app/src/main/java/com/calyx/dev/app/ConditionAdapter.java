package com.calyx.dev.app;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class ConditionAdapter extends ArrayAdapter<Condition>
{
	private int resourceLayout;
	private Context mContext;
	
	
	public ConditionAdapter(Context context, int resource, ArrayList<Condition> items) {
		super(context, resource, items);
		this.resourceLayout = resource;
		this.mContext = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		
		
		if (convertView == null) {
			LayoutInflater vi  = LayoutInflater.from(mContext);
			
			//Toast.makeText(mContext,resourceLayout,Toast.LENGTH_LONG).show();
			convertView = vi.inflate(resourceLayout, null);
		}
		
		Condition condition = getItem(position);
		
		
		System.out.println();
	

		
		if(condition!=null){
			TextView tt1 = convertView.findViewById(R.id. txtDate);
			TextView tt2 = convertView.findViewById(R.id.txtConfirmed);
			TextView tt3 = convertView.findViewById(R.id.txtRecovered);
			TextView tt4 = convertView.findViewById(R.id.txtDeaths);
			if(tt1!=null){
			tt1.setText(condition.getDate());
			}
			if(tt2!=null){
				String x = condition.getConfirmed() == 0 ?"0":
				String.valueOf(condition.getConfirmed());
			tt2.setText(x);
			}
			if(tt3!=null){
				String x = condition.getRecovered() == 0 ?"0":
					String.valueOf(condition.getRecovered());
			tt3.setText(x);
			}
			if(tt4!=null){
				String x = condition.getDeaths() == 0 ?"0":
					String.valueOf(condition.getDeaths());
			tt4.setText(x);
			}
		
		}
		
		return convertView;
	}

	
	
	
}
