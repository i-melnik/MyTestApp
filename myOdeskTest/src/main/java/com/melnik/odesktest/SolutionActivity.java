package com.melnik.odesktest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.melnik.odesktest.layout.ExpandablePanel;
import com.melnik.odesktest.layout.ExpandablePanel.OnExpandListener;
import com.melnik.odesktest.menu.MySlidingMenu;

public class SolutionActivity extends SherlockFragmentActivity{

	
	private ExpandablePanel businessLayout;
	private ExpandablePanel individualLayout;
	
	private ImageView businessIcon;
	private ImageView individualIcon;
	
	private Button backButton;
	
	private SlidingMenu menu;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_solution);
		
		businessLayout = (ExpandablePanel) findViewById(R.id.foo);
		businessIcon = (ImageView) findViewById(R.id.business_minus);
		individualIcon = (ImageView) findViewById(R.id.individual_minus);
		individualLayout = (ExpandablePanel) findViewById(R.id.foo1);

		businessLayout.setOnExpandListener(new OnExpandListener() {
			
			@Override
			public void onExpand(View handle, View content) {
				businessIcon.setImageDrawable(SolutionActivity.this.getResources().getDrawable(R.drawable.minus));
			}
			
			@Override
			public void onCollapse(View handle, View content) {
				businessIcon.setImageDrawable(SolutionActivity.this.getResources().getDrawable(R.drawable.plus));
			}
		});
		
		individualLayout.setOnExpandListener(new OnExpandListener() {
			
			@Override
			public void onExpand(View handle, View content) {
				individualIcon.setImageDrawable(SolutionActivity.this.getResources().getDrawable(R.drawable.minus));
			}
			
			@Override
			public void onCollapse(View handle, View content) {
				individualIcon.setImageDrawable(SolutionActivity.this.getResources().getDrawable(R.drawable.plus));
			}
		});
		
		backButton = (Button) findViewById(R.id.solution_header);
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		menu = MySlidingMenu.getMenu(this, getString(R.string.menu_solutions));
		showActionBar();
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
}
