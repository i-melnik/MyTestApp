package com.melnik.odesktest.menu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.melnik.odesktest.ContactUsActivity;
import com.melnik.odesktest.FaqActivity;
import com.melnik.odesktest.HomeActivity;
import com.melnik.odesktest.LoginActivity;
import com.melnik.odesktest.MusicActivity;
import com.melnik.odesktest.OfferActivity;
import com.melnik.odesktest.ProfileActivity;
import com.melnik.odesktest.R;
import com.melnik.odesktest.SolutionActivity;
import com.melnik.odesktest.StoreFinderActivity;
import com.melnik.odesktest.adapter.MenuAdapter;

public class MySlidingMenu
{

	private static MenuAdapter adapter;

	public static SlidingMenu getMenu(final Activity activity, final String current)
	{
		final SlidingMenu menu = new SlidingMenu(activity);
		menu.setMode(SlidingMenu.RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(activity, SlidingMenu.SLIDING_WINDOW);

		LayoutInflater inflater = ((LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
		View view = inflater.inflate(R.layout.slide_menu, null);

		ListView lv = (ListView) view.findViewById(R.id.menuList);
		lv.setCacheColorHint(0);
		String[] menuItems = activity.getResources().getStringArray(R.array.menu_array);
		adapter = new MenuAdapter(activity, menuItems);
		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new ListView.OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Object item = adapter.getItem(position);
				if (item != null)
				{
					if (item.toString().equals(current))
					{
						if (menu.isMenuShowing())
						{
							menu.toggle();
						}
					}
					else if (item.toString().equals(activity.getString(R.string.menu_home)))
					{

						Intent intent = new Intent(activity, HomeActivity.class);
						activity.startActivity(intent);
						menu.toggle();
					}
					else if (item.toString().equals(activity.getString(R.string.menu_profile)))
					{

						Intent intent = new Intent(activity, ProfileActivity.class);
						activity.startActivity(intent);
						menu.toggle();
					}
					else if (item.toString().equals(activity.getString(R.string.menu_login)))
					{

						Intent intent = new Intent(activity, LoginActivity.class);
						activity.startActivity(intent);
						menu.toggle();
					}
					else if (item.toString().equals(activity.getString(R.string.menu_solutions)))
					{

						Intent intent = new Intent(activity, SolutionActivity.class);
						activity.startActivity(intent);
						menu.toggle();
					}
					else if (item.toString().equals(activity.getString(R.string.menu_offers)))
					{

						Intent intent = new Intent(activity, OfferActivity.class);
						activity.startActivity(intent);
						menu.toggle();
					}
					else if (item.toString().equals(activity.getString(R.string.menu_faqs)))
					{

						Intent intent = new Intent(activity, FaqActivity.class);
						activity.startActivity(intent);
						menu.toggle();
					}
					else if (item.toString().equals(activity.getString(R.string.menu_contact_us)))
					{

						Intent intent = new Intent(activity, ContactUsActivity.class);
						activity.startActivity(intent);
						menu.toggle();
					}
					else if (item.toString().equals(activity.getString(R.string.menu_store_finder)))
					{

						Intent intent = new Intent(activity, StoreFinderActivity.class);
						activity.startActivity(intent);
						menu.toggle();
					}
					else if (item.toString().equals(activity.getString(R.string.menu_music)))
					{

						Intent intent = new Intent(activity, MusicActivity.class);
						activity.startActivity(intent);
						menu.toggle();
					}
				}

			}
		});

		menu.setMenu(view);

		return menu;
	}

}
