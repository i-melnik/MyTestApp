package com.melnik.odesktest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.melnik.odesktest.menu.MySlidingMenu;

public class ProfileActivity extends SherlockFragmentActivity{

	private Button backButton;
	private SlidingMenu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_profile);
		
		backButton = (Button) findViewById(R.id.profile_header);
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		menu = MySlidingMenu.getMenu(this, getString(R.string.menu_profile));
		
		showActionBar();
	}
	
//	@Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//		getSupportActionBar().setBackgroundDrawable(new ColorDrawable(R.color.white));
//		menu.add("").setIcon(R.drawable.callsat_header_logo).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//    	menu.add("").setIcon(android.R.drawable.ic_menu_view).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
//        // Inflate the menu; this adds items to the action bar if it is present.
//        //getSupportMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	private void showActionBar() {
		//getSupportActionBar().setCustomView(R.layout.profile_action_bar);
        LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View v = inflator.inflate(R.layout.profile_action_bar, null);
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(false);
	    actionBar.setDisplayShowHomeEnabled (false);
	    actionBar.setDisplayShowCustomEnabled(true);
	    actionBar.setDisplayShowTitleEnabled(false);
	    actionBar.setCustomView(v);
	    
	    ImageButton menuButton = (ImageButton) v.findViewById(R.id.menuButton);
	    menuButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				menu.toggle();
			}
		});
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
	        case R.id.menuButton:
	        {
	        	menu.toggle();
	        	return true;
	        }
	        case R.id.action_settings:
	        	return true;
	        default:
	        	return super.onOptionsItemSelected(item);
	    }
    }

	@Override
	public void onBackPressed() {
		if (menu.isMenuShowing())
    	{
    		menu.toggle();
    	}
    	else
    	{
    		super.onBackPressed();
    	}
	}
}
