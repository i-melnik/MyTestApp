package com.melnik.odesktest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.melnik.odesktest.adapter.MenuAdapter;
import com.melnik.odesktest.menu.MySlidingMenu;

public class MainMenuActivity extends SherlockActivity
{

	private ListView listView;
	private MenuAdapter adapter;
	private SlidingMenu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setTheme(R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		listView = (ListView) findViewById(android.R.id.list);
		String[] menuItems = getResources().getStringArray(R.array.menu_array);
		listView.setCacheColorHint(0);
		adapter = new MenuAdapter(this, menuItems);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Object item = adapter.getItem(position);
				if (item != null)
				{
					if (item.toString().equals(MainMenuActivity.this.getString(R.string.menu_home)))
					{
						Intent intent = new Intent(MainMenuActivity.this, HomeActivity.class);
						startActivity(intent);
					}
					else if (item.toString().equals(MainMenuActivity.this.getString(R.string.menu_profile)))
					{

						Intent intent = new Intent(MainMenuActivity.this, ProfileActivity.class);
						startActivity(intent);
					}
					else if (item.toString().equals(MainMenuActivity.this.getString(R.string.menu_login)))
					{

						Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
						startActivity(intent);
					}
					else if (item.toString().equals(MainMenuActivity.this.getString(R.string.menu_solutions)))
					{

						Intent intent = new Intent(MainMenuActivity.this, SolutionActivity.class);
						startActivity(intent);
					}
					else if (item.toString().equals(MainMenuActivity.this.getString(R.string.menu_offers)))
					{

						Intent intent = new Intent(MainMenuActivity.this, OfferActivity.class);
						startActivity(intent);
					}
					else if (item.toString().equals(MainMenuActivity.this.getString(R.string.menu_faqs)))
					{

						Intent intent = new Intent(MainMenuActivity.this, FaqActivity.class);
						startActivity(intent);
					}
					else if (item.toString().equals(MainMenuActivity.this.getString(R.string.menu_contact_us)))
					{

						Intent intent = new Intent(MainMenuActivity.this, ContactUsActivity.class);
						startActivity(intent);
					}
					else if (item.toString().equals(MainMenuActivity.this.getString(R.string.menu_store_finder)))
					{

						Intent intent = new Intent(MainMenuActivity.this, StoreFinderActivity.class);
						startActivity(intent);
					}
					else if (item.toString().equals(MainMenuActivity.this.getString(R.string.menu_music)))
					{

						Intent intent = new Intent(MainMenuActivity.this, MusicActivity.class);
						startActivity(intent);
					}
				}
			}
		});

		menu = MySlidingMenu.getMenu(this, "");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{

		menu.add("").setIcon(R.drawable.abs__ic_menu_moreoverflow_normal_holo_light)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		// Inflate the menu; this adds items to the action bar if it is present.
		// getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == 0)
		{
			menu.toggle();
			return true;
		}
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
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

	// @Override
	// protected void onListItemClick(ListView l, View v, int position, long id)
	// {
	// Object item = adapter.getItem(position);
	// if (item != null)
	// {
	// if (item.toString().equals(this.getString(R.string.menu_home)))
	// {
	// Intent intent = new Intent(this, HomeActivity.class);
	// startActivity(intent);
	// }
	//
	// }
	// else {
	// super.onListItemClick(l, v, position, id);
	// }
	// }
}
