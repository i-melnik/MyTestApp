package com.melnik.odesktest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.melnik.odesktest.menu.MySlidingMenu;

public class HomeActivity extends SherlockActivity
{

	private Button viewSiteBtn;
	private SlidingMenu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		viewSiteBtn = (Button) findViewById(R.id.site_button);

		viewSiteBtn.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(HomeActivity.this, MainMenuActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});

		menu = MySlidingMenu.getMenu(this, getString(R.string.menu_home));
	}

	@Override
	public void onBackPressed()
	{
		if (menu.isMenuShowing())
		{
			menu.toggle();
		}
		else
		{
			super.onBackPressed();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
