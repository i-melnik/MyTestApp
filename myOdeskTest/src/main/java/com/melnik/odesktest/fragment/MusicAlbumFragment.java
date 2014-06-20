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
import com.melnik.odesktest.adapter.MusicFragmentAlbumAdapter;
import com.melnik.odesktest.entity.MusicItem;

public class MusicAlbumFragment extends SherlockFragment
{

	private MusicAlbumClickListener mListener;

	private ListView listView;

	private MusicFragmentAlbumAdapter adapter;

	public interface MusicAlbumClickListener
	{
		public void onAlbumSelected(List<MusicItem> data);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.music_album_fragment, container);

		listView = (ListView) view.findViewById(R.id.music_album_list_view);
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
					mListener.onAlbumSelected(entry.getValue());
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
			mListener = (MusicAlbumClickListener) activity;
		}
		catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + " must implement MusicAlbumClickListener");
		}
	}

	public void setList(List<Map<String, List<MusicItem>>> data)
	{
		adapter = new MusicFragmentAlbumAdapter(getActivity(), data);
		listView.setAdapter(adapter);
	}

	public ListView getListView()
	{
		return listView;
	}

}
