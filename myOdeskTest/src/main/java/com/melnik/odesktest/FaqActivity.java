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

public class FaqActivity extends SherlockFragmentActivity {

	private ExpandablePanel internetLayout;
	private ExpandablePanel voiceLayout;
	private ExpandablePanel mobileLayout;
	
	private ImageView internetIcon;
	private ImageView voiceIcon;
	private ImageView mobilelIcon;
	
	private Button backButton;
	
	private SlidingMenu menu;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		
		setContentView(R.layout.activity_faq);
		
		internetLayout = (ExpandablePanel) findViewById(R.id.faq_internet_layout);
		voiceLayout = (ExpandablePanel) findViewById(R.id.faq_voice_layout);
		mobileLayout = (ExpandablePanel) findViewById(R.id.faq_mobile_layout);
		internetIcon = (ImageView) findViewById(R.id.internet_minus);
		voiceIcon = (ImageView) findViewById(R.id.voice_minus);
		mobilelIcon = (ImageView) findViewById(R.id.mobile_minus);

		internetLayout.setOnExpandListener(new OnExpandListener() {
			
			@Override
			public void onExpand(View handle, View content) {
				internetIcon.setImageDrawable(FaqActivity.this.getResources().getDrawable(R.drawable.minus));
			}
			
			@Override
			public void onCollapse(View handle, View content) {
				internetIcon.setImageDrawable(FaqActivity.this.getResources().getDrawable(R.drawable.plus));
			}
		});
		
		voiceLayout.setOnExpandListener(new OnExpandListener() {
			
			@Override
			public void onExpand(View handle, View content) {
				voiceIcon.setImageDrawable(FaqActivity.this.getResources().getDrawable(R.drawable.minus));
			}
			
			@Override
			public void onCollapse(View handle, View content) {
				voiceIcon.setImageDrawable(FaqActivity.this.getResources().getDrawable(R.drawable.plus));
			}
		});
		
		mobileLayout.setOnExpandListener(new OnExpandListener() {
			
			@Override
			public void onExpand(View handle, View content) {
				mobilelIcon.setImageDrawable(FaqActivity.this.getResources().getDrawable(R.drawable.minus));
			}
			
			@Override
			public void onCollapse(View handle, View content) {
				mobilelIcon.setImageDrawable(FaqActivity.this.getResources().getDrawable(R.drawable.plus));
			}
		});
		
		backButton = (Button) findViewById(R.id.faq_header);
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		menu = MySlidingMenu.getMenu(this, getString(R.string.menu_faqs));
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
