package com.calyx.dev.app;

import java.util.ArrayList;
import java.util.List;

public interface SpinnerItemListener
{
	void OnItemResult(List<String> countryNames);
	void OnListViewItemResult(ArrayList<Country> countries);
}
