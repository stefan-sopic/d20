package com.stefan.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class RaceFragment extends Fragment implements OnItemSelectedListener {
	
	

	public RaceFragment() {
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_race, container, false);
		
		Spinner spinner = (Spinner)rootView.findViewById(R.id.race_spinner);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.races_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		
		return rootView;
	}
	
	
	    
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // parent.getItemAtPosition(pos)
    	System.out.println(parent.getItemAtPosition(pos));
    	switch(pos) {
			case 0: 
				((TextView)getView().findViewById (R.id.race_description)).setText(R.string.race_human);
				break;
			case 1: 
				((TextView)getView().findViewById (R.id.race_description)).setText(R.string.race_elf);
				break;
			case 2: 
				((TextView)getView().findViewById (R.id.race_description)).setText(R.string.race_halflig);
				break;
			case 3:  
				((TextView)getView().findViewById (R.id.race_description)).setText(R.string.race_dwarf);
				break;
			case 4:  
				((TextView)getView().findViewById (R.id.race_description)).setText(R.string.race_gnome);
				break;
			case 5:  
				((TextView)getView().findViewById (R.id.race_description)).setText(R.string.race_halforc);
				break;
			default:  break;
    	}
    }
    
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
}
