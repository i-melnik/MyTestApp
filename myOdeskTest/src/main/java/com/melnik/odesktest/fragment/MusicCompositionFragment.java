package com.melnik.odesktest.fragment;

import java.util.List;

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
import com.melnik.odesktest.adapter.MusicCompositionAdapter;
import com.melnik.odesktest.entity.MusicItem;

public class MusicCompositionFragment extends SherlockFragment
{

	private MusicCompositionClickListener mListener;

	private ListView listView;

	private MusicCompositionAdapter adapter;

	public interface MusicCompositionClickListener
	{
		public void onSongSelected(List<MusicItem> data, int currentPosition);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.music_composition_fragment, container);

		listView = (ListView) view.findViewById(R.id.composition_list_view);
		listView.setCacheColorHint(0);
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				mListener.onSongSelected(adapter.getData(), position);
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
			mListener = (MusicCompositionClickListener) activity;
		}
		catch (ClassCastException e)
		{
			throw new ClassCastException(activity.toString() + " must implement MusicCompositionClickListener");
		}
	}

	public void setList(List<MusicItem> data)
	{
		adapter = new MusicCompositionAdapter(getActivity(), data);
		listView.setAdapter(adapter);
	}
}
