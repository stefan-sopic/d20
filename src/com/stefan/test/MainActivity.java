package com.stefan.test;


import java.util.Locale;

import com.stefan.test.ShakeDetector.OnShakeListener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnSharedPreferenceChangeListener {

	SectionsPagerAdapter mSectionsPagerAdapter;
	 // The following are used for the shake detection
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	public static boolean vibrate;
	public static boolean shake;

	//listener for change in preference
	OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
		public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
			vibrate = prefs.getBoolean("vibratePreference", true);
			shake = prefs.getBoolean("shakePreference", true);
		}
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		//PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

		prefs.registerOnSharedPreferenceChangeListener(listener);
		
		// ShakeDetector initialization
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();
        mShakeDetector.setOnShakeListener(new OnShakeListener() {
 
            @Override
            public void onShake(int count) {
            	if(shake) {
            		Button b1 = (Button) findViewById(R.id.button_d08); 
                	b1.performClick();
                	Button b2 = (Button) findViewById(R.id.button_d10); 
                	b2.performClick();
                	Button b3 = (Button) findViewById(R.id.button_d12); 
                	b3.performClick();
                	Button b4 = (Button) findViewById(R.id.button_d20); 
                	b4.performClick();
            	}
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void rollDice(View view) {
		Button b = (Button)view;
		String[] dice = ((String) b.getText()).split("d");
		int Min = 1;
		int Max = Integer.parseInt(dice[1]);
		int num = Min + (int)(Math.random() * ((Max - Min) + 1));
		if (num == Max  && vibrate) {
			Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
			// Vibrate for 500 milliseconds
			v.vibrate(500);
		}
		TextView tv;
		switch(Max) {
			case 20: tv =  (TextView)findViewById(R.id.text_d20);
					 tv.setText(Integer.toString(num));
					 break;
			case 12: tv =  (TextView)findViewById(R.id.text_d12);
		 			 tv.setText(Integer.toString(num));
		 			 break;
			case 10: tv =  (TextView)findViewById(R.id.text_d10);
			 		 tv.setText(Integer.toString(num));
			 		 break;
			case 8:  tv =  (TextView)findViewById(R.id.text_d08);
			 		 tv.setText(Integer.toString(num));
			 		 break;
			    default:  break;
		}
	}
	
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new Fragment();
			switch (position) {
			case 0:
				return fragment = new DiceRollerFragment();
			case 1:
				return fragment = new RaceFragment();
			case 2:
				return fragment = new PlayerFragment();
			default:
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}
	
	@Override
    public void onResume() {
        super.onResume();
        // Add the following line to register the Session Manager Listener onResume
        mSensorManager.registerListener(mShakeDetector, mAccelerometer,    SensorManager.SENSOR_DELAY_UI);
    }
 
    @Override
    public void onPause() {
        // Add the following line to unregister the Sensor Manager onPause
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }
    
	@Override
	public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                return true;
            case R.id.action_help:
                startActivity(new Intent(this, HelpActivity.class));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

