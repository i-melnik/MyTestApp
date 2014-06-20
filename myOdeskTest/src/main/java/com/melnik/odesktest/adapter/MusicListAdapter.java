package com.melnik.odesktest.adapter;

import java.util.List;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.melnik.odesktest.R;
import com.melnik.odesktest.entity.MusicItem;

public class MusicListAdapter implements ListAdapter
{

	private List<MusicItem> data;

	private Context context;
	private LayoutInflater inflater;

	public MusicListAdapter(Context context, List<MusicItem> data)
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
			convertView = inflater.inflate(R.layout.music_item, null);

			holder.title = (TextView) convertView.findViewById(R.id.music_item_title);
			holder.name = (TextView) convertView.findViewById(R.id.music_item_name);
			holder.album = (TextView) convertView.findViewById(R.id.music_item_album);
			holder.artist = (TextView) convertView.findViewById(R.id.music_item_artist);

			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		MusicItem item = data.get(position);
		if (item == null)
		{
			holder.title.setText("");
			holder.name.setText("");
			holder.album.setText("");
			holder.artist.setText("");
		}
		else
		{
			holder.title.setText(item.getTitle() == null ? "" : item.getTitle());
			holder.name.setText(item.getName() == null ? "" : item.getName());
			holder.album.setText(item.getAlbum() == null ? "" : item.getAlbum());
			holder.artist.setText(item.getArtist() == null ? "" : item.getArtist());
		}
		return convertView;
	}

	@Override
	public int getItemViewType(int position)
	{
		// TODO Auto-generated method stub
		return 0;
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
		TextView name;
		TextView artist;
		TextView album;
	}
}
