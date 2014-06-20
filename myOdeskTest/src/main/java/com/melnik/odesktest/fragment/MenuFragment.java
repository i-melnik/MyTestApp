package com.melnik.odesktest.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockListFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.melnik.odesktest.HomeActivity;
import com.melnik.odesktest.R;
import com.melnik.odesktest.adapter.MenuAdapter;

public class MenuFragment extends SherlockFragment{

	private MenuAdapter adapter;
	private SlidingMenu menu;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.slide_menu, container);
		
		ListView listView = (ListView) view.findViewById(R.id.menuList);
        String[] menuItems = getResources().getStringArray(R.array.menu_array);

        adapter = new MenuAdapter(this.getActivity(), menuItems);
        listView.setAdapter(adapter);
        
//        menu = new SlidingMenu(getActivity());
//        menu.setMode(SlidingMenu.RIGHT);
//        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
//        menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
//        menu.setFadeDegree(0.35f);
//        menu.attachToActivity(getActivity(), SlidingMenu.SLIDING_CONTENT);
//        menu.setMenu(view);
        
		return view;
	}
	
//	@Override
//	public void onListItemClick(ListView l, View v, int position, long id) {
//		Object item = adapter.getItem(position);
//        if (item != null)
//        {
//        	if (item.toString().equals(getActivity().getString(R.string.menu_home)))
//            {
//            	if (!needToStay(HomeActivity.class.getSimpleName()))
//            	{
//            		Intent intent = new Intent(getActivity(), HomeActivity.class);
//                    getActivity().startActivity(intent);
//            	}           
//                menu.toggle();
//            }
//
//        }
//        else {
//            super.onListItemClick(l, v, position, id);
//        }
//	}
	
	private boolean needToStay(String activityClassName)
	{
		if (getActivity().getClass().getSimpleName().equals(activityClassName))
		{
			return true;
		}
		return false;
	}
}
