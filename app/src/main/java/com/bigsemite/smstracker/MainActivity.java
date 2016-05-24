package com.bigsemite.smstracker;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends AppCompatActivity {

	EditText t1;
	Button b1;
	TextView tv1;
	SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test1);

		t1 = (EditText) findViewById (R.id.editText1);
		b1 = (Button)findViewById(R.id.button1);
		tv1 = (TextView) findViewById(R.id.textView1);
		
		
		
		sp = getSharedPreferences("bigsemiteTracker", MODE_PRIVATE);
		
		String pd = sp.getString("passwd",".");
		if (pd.equals(".")){
			Toast.makeText(getApplicationContext(), "You are running this app the first time", Toast.LENGTH_LONG).show();
			t1.setVisibility(View.VISIBLE);
			b1.setVisibility(View.VISIBLE);
		}
		
		/*
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		*/
	}

	public void vv(View v){
		
		SharedPreferences.Editor editor = sp.edit();
		t1.setVisibility(View.INVISIBLE);
		editor.putString("passwd", t1.getText().toString());
		
		editor.commit();
		Toast.makeText(getApplicationContext(), "Search Text changed to:" + t1.getText().toString(), Toast.LENGTH_LONG).show();
		b1.setVisibility(View.INVISIBLE);
		tv1.setText("Search text has been changed from default");
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
