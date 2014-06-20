package com.melnik.odesktest.fragment;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.melnik.odesktest.R;
import com.melnik.odesktest.adapter.MusicFragmentFolderAdapter;
import com.melnik.odesktest.entity.MusicItem;

public class MusicFolderFragment extends SherlockFragment
{
	private MusicFolderClickListener mListener;

	private ListView listView;

	private MusicFragmentFolderAdapter adapter;

	public interface MusicFolderClickListener
	{
		public void onFolderSelected(List<MusicItem> data);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.music_folder_fragment, container);

		listView = (ListView) view.findViewById(R.id.music_folder_list_view);
		listView.setCacheColorHint(0);
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Map<String, List<MusicItem>> item = (Map<String, List<MusicItem>>) adapter.getItem(position);
				for (Entry<String, List<MusicItem>> entry : item.entrySet())
				{
					mListener.onFolderSelected(entry.getValue());
					break;
				}
			}
		});
		return view;
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);
		try
		{
			mListener = (MusicFolderClickListener) activity;
		}
		catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + " must implement MusicFolderClickListener");
		}
	}

	public void setList(List<Map<String, List<MusicItem>>> data)
	{
		adapter = new MusicFragmentFolderAdapter(getActivity(), data);
		listView.setAdapter(adapter);
	}

	public ListView getListView()
	{
		return listView;
	}
}
