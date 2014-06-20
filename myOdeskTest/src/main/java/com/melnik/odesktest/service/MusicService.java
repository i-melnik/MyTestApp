package com.melnik.odesktest.service;

import java.util.List;

import android.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.v4.app.NotificationCompat;

import com.melnik.odesktest.MusicActivity;
import com.melnik.odesktest.entity.MusicItem;

public class MusicService extends Service implements OnPreparedListener, OnCompletionListener, OnErrorListener
{

	private List<MusicItem> musicList;
	private MediaPlayer mediaPlayer;
	private int currentSong;
	private Notification notification;
	private final IBinder musicBind = new MusicBinder();

	@Override
	public void onCreate()
	{
		super.onCreate();
		currentSong = 0;
		mediaPlayer = new MediaPlayer();
		initMediaPlayer();
	}

	public void initMediaPlayer()
	{
		mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		mediaPlayer.setOnPreparedListener(this);
		mediaPlayer.setOnCompletionListener(this);
		mediaPlayer.setOnErrorListener(this);
	}

	public void setMusicList(List<MusicItem> musicList)
	{
		this.musicList = musicList;
	}

	public class MusicBinder extends Binder
	{
		public MusicService getService()
		{
			return MusicService.this;
		}
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		if (intent.getExtras() != null && !intent.getExtras().isEmpty())
		{
			this.currentSong = intent.getExtras().getInt("position");
		}
		return musicBind;
	}

	@Override
	public boolean onUnbind(Intent intent)
	{
		mediaPlayer.stop();
		mediaPlayer.release();
		return false;
	}

	public void pauseSong()
	{
		mediaPlayer.pause();
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(001);
	}

	public void resumeSong()
	{
		mediaPlayer.start();
	}

	public void playSong()
	{
		mediaPlayer.reset();
		MusicItem item = musicList.get(currentSong);
		try
		{
			mediaPlayer.setDataSource(item.getDataUrl());
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.prepareAsync();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public boolean onError(MediaPlayer mp, int what, int extra)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCompletion(MediaPlayer mp)
	{
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nm.cancel(001);
	}

	@Override
	public void onPrepared(MediaPlayer mp)
	{
		mp.start();
		Intent intent = new Intent(this, MusicActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationManager nm = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new NotificationCompat.Builder(this).setContentTitle("Odesk Test App")
				.setContentText("Playing " + musicList.get(currentSong).getName()).setAutoCancel(true)
				.setSmallIcon(R.drawable.ic_media_play).setContentIntent(pIntent).build();
		nm.notify(001, notification);
	}

	public void setSong(int position)
	{
		this.currentSong = position;
	}

	public MediaPlayer getMediaPlayer()
	{
		return mediaPlayer;
	}
}
