package com.melnik.odesktest.fragment;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.melnik.odesktest.R;
import com.melnik.odesktest.entity.MusicItem;
import com.melnik.odesktest.service.MusicService;
import com.melnik.odesktest.service.MusicService.MusicBinder;

public class MusicPlayFragment extends SherlockFragment implements OnSeekBarChangeListener
{

	private int currentPositionPlaying;
	private long currentAlbumId = -1;

	private ImageButton play;
	private ImageButton previous;
	private ImageButton next;
	private SeekBar playBar;

	private TextView songName;
	private TextView songAlbum;
	private TextView playTime;

	private ImageView albumImage;

	private Bitmap artwork;

	private MusicService musicService;
	private boolean musicBound;
	private Intent playIntent;

	private List<MusicItem> music;

	private Handler seekHandler = new Handler();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.music_play_fragment, container);

		play = (ImageButton) view.findViewById(R.id.music_play);
		previous = (ImageButton) view.findViewById(R.id.music_previous);
		next = (ImageButton) view.findViewById(R.id.music_next);
		playBar = (SeekBar) view.findViewById(R.id.play_seek_bar);
		playBar.setOnSeekBarChangeListener(this);
		songName = (TextView) view.findViewById(R.id.play_song_name);
		songAlbum = (TextView) view.findViewById(R.id.play_album_name);
		playTime = (TextView) view.findViewById(R.id.play_time);
		albumImage = (ImageView) view.findViewById(R.id.play_album_image);

		// listView.setOnItemClickListener(new OnItemClickListener()
		// {
		//
		// @Override
		// public void onItemClick(AdapterView<?> parent, View view, int
		// position, long id)
		// {
		// pauseButton();
		// musicService.setSong(position);
		// musicService.playSong();
		// }
		// });

		previous.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (currentPositionPlaying > 0)
				{
					pauseButton();
					currentPositionPlaying--;
					playSongAndUpdateUI();
				}
			}
		});

		next.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				if (currentPositionPlaying < music.size() - 2)
				{
					pauseButton();
					currentPositionPlaying++;
					playSongAndUpdateUI();
				}
			}
		});

		return view;
	}

	@Override
	public void onDestroy()
	{
        if (playIntent != null) {
            getActivity().getApplicationContext().stopService(playIntent);
        }
		musicService = null;
		super.onDestroy();
	}

	private void playSongAndUpdateUI()
	{
		musicService.setSong(currentPositionPlaying);
		setUpSeekBar(currentPositionPlaying);
		updateUI(currentPositionPlaying);
		musicService.playSong();
		seekUpdation();
	}

	private ServiceConnection musicConnection = new ServiceConnection()
	{

		@Override
		public void onServiceConnected(ComponentName name, IBinder service)
		{
			MusicBinder binder = (MusicBinder) service;
			// get service
			musicService = binder.getService();
			// pass list
			musicService.setMusicList(music);
			musicBound = true;
			pauseButton();
			playSongAndUpdateUI();
		}

		@Override
		public void onServiceDisconnected(ComponentName name)
		{
			musicBound = false;
		}
	};

	public void play(int position)
	{
		if (playIntent == null)
		{
			playIntent = new Intent(this.getActivity().getApplicationContext(), MusicService.class);
			playIntent.putExtra("position", position);
			this.getActivity().getApplicationContext().bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
			this.getActivity().getApplicationContext().startService(playIntent);
		}
	}

	private void updateUI(int position)
	{
		MusicItem item = music.get(position);
		songName.setText(item.getName());
		songAlbum.setText(item.getAlbum());
		if (currentAlbumId != item.getAlbumId())
		{
			Uri sArtworkUri = Uri.parse("content://media/external/audio/albumart");
			Uri uri = ContentUris.withAppendedId(sArtworkUri, item.getAlbumId());
			ContentResolver res = getActivity().getContentResolver();
			InputStream in;
			try
			{
				in = res.openInputStream(uri);
				artwork = BitmapFactory.decodeStream(in);
				currentAlbumId = item.getAlbumId();
				albumImage.setImageBitmap(artwork);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}

	}

	public void setMusic(List<MusicItem> music)
	{
		this.music = music;
	}

	public void setCurrentPositionPlaying(int currentPositionPlaying)
	{
		this.currentPositionPlaying = currentPositionPlaying;
	}

	private void pauseButton()
	{
		play.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_pause));
		play.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				musicService.pauseSong();
				play.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_media_play));
				play.setOnClickListener(new OnClickListener()
				{

					@Override
					public void onClick(View v)
					{
						musicService.resumeSong();
						pauseButton();
					}
				});
			}
		});
	}

	private void setUpSeekBar(int position)
	{
		MusicItem item = music.get(position);
		playBar.setMax(item.getDuration());
	}

	private Runnable run = new Runnable()
	{

		@Override
		public void run()
		{
			seekUpdation();
		}
	};

	public void seekUpdation()
	{
		if (musicService != null)
		{
			int position = musicService.getMediaPlayer().getCurrentPosition();
			if (playBar.getMax() != position)
			{
				playBar.setProgress(position);
				int minutes = (position % (1000 * 60 * 60)) / (1000 * 60);
				int sec = ((position % (1000 * 60 * 60)) % (1000 * 60) / 1000);
				if (sec < 10)
					playTime.setText("" + minutes + ":0" + sec);
				else
					playTime.setText("" + minutes + ":" + sec);
				seekHandler.postDelayed(run, 1000);
			}
		}
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
	{
		if (fromUser)
		{
			System.out.println("SEEK TO" + seekBar.getProgress());
			musicService.getMediaPlayer().seekTo(seekBar.getProgress());
		}

	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar)
	{

	}
}
