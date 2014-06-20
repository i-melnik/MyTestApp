package com.melnik.odesktest;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.melnik.odesktest.menu.MySlidingMenu;
import com.melnik.odesktest.util.Route;

public class StoreFinderActivity extends SherlockFragmentActivity
{

	private Button backButton;

	private SlidingMenu menu;

	private SupportMapFragment mapFragment;
	private GoogleMap map;

	private LatLng firstPoint;
	private LatLng secondPoint;

    private AlertDialog dialog;

    private View mapDialog;
    private Button firstPointBtn;
    private Button secondPointBtn;
    private Button routeBtn;

	@Override
	protected void onCreate(Bundle arg0)
	{
		super.onCreate(arg0);

		setContentView(R.layout.activity_store_finder);

		backButton = (Button) findViewById(R.id.store_finder_header);
		backButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				onBackPressed();
			}
		});

		mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
		map = mapFragment.getMap();
		map.setMyLocationEnabled(true);

        map.setOnMapLongClickListener(mapLongClickListener);

		menu = MySlidingMenu.getMenu(this, getString(R.string.menu_store_finder));
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		showActionBar();

        mapDialog = getLayoutInflater().inflate(R.layout.map_alert_dialog, null);
        firstPointBtn = (Button) mapDialog.findViewById(R.id.first_point);
        secondPointBtn = (Button) mapDialog.findViewById(R.id.second_point);
        routeBtn = (Button) mapDialog.findViewById(R.id.btn_route);
		//registerForContextMenu(map);
	}

	private void showActionBar()
	{
		// getSupportActionBar().setCustomView(R.layout.profile_action_bar);
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflater.inflate(R.layout.profile_action_bar, null);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setCustomView(v);

		ImageButton menuButton = (ImageButton) v.findViewById(R.id.menuButton);
		menuButton.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				menu.toggle();
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
		menu.add("Select first point");
		menu.add("Select second point");
	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		if (item.toString().equals("Select first point"))
		{
			map.setOnMapClickListener(new OnMapClickListener()
			{

				@Override
				public void onMapClick(LatLng arg0)
				{
					firstPoint = arg0;
				}
			});
		}
		else if (item.toString().equals("Select second point"))
		{
			map.setOnMapClickListener(new OnMapClickListener()
			{

				@Override
				public void onMapClick(LatLng arg0)
				{
					secondPoint = arg0;
					if (firstPoint != null)
					{
						Route route = new Route();
						route.drawRoute(map, StoreFinderActivity.this, firstPoint, secondPoint, "en");
						map.setOnMapClickListener(null);
					}
				}
			});
		}
		return true;
	}

    GoogleMap.OnMapLongClickListener mapLongClickListener = new GoogleMap.OnMapLongClickListener() {
        @Override
        public void onMapLongClick(final LatLng latLng) {
            dialog = new AlertDialog.Builder(StoreFinderActivity.this).setView(mapDialog).show();
            firstPointBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    firstPoint = latLng;
                }
            });
            secondPointBtn.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    secondPoint = latLng;
                }
            });
        }
    };

    private final OnClickListener routeListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (firstPoint != null && secondPoint != null)
            {
                Route route = new Route();
                route.drawRoute(map, StoreFinderActivity.this, firstPoint, secondPoint, "en");
            }
        }
    };
}
