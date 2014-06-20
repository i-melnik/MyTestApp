package com.melnik.odesktest.adapter;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.melnik.odesktest.R;
import com.melnik.odesktest.entity.MusicItem;

public class MusicFragmentAlbumAdapter implements ListAdapter
{

	private List<Map<String, List<MusicItem>>> data;

	private Context context;
	private LayoutInflater inflater;

	public MusicFragmentAlbumAdapter(Context context, List<Map<String, List<MusicItem>>> data)
	{
		this.context = context;
		this.data = data;
		inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public int getCount()
	{
		return data.size();
	}

	@Override
	public Object getItem(int position)
	{
		return data.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public boolean hasStableIds()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;

		if (convertView == null)
		{
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.music_album_fragment_item, null);

			holder.title = (TextView) convertView.findViewById(R.id.album_name);
			holder.count = (TextView) convertView.findViewById(R.id.songs_count);

			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		Map<String, List<MusicItem>> item = data.get(position);
		Entry<String, List<MusicItem>> entry = item.entrySet().iterator().next();
		if (entry == null)
		{
			holder.title.setText("");
			holder.count.setText("");
		}
		else
		{

			holder.title.setText(entry.getKey() == null ? "" : entry.getKey());
			holder.count.setText(entry.getValue() == null ? "" : entry.getValue().size() + " Композиции(й)");
		}
		return convertView;
	}

	@Override
	public int getItemViewType(int position)
	{
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int getViewTypeCount()
	{
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isEmpty()
	{
		return data.isEmpty();
	}

	@Override
	public boolean areAllItemsEnabled()
	{
		return true;
	}

	@Override
	public boolean isEnabled(int position)
	{
		return true;
	}

	static class ViewHolder
	{
		TextView title;
		TextView count;
	}
}