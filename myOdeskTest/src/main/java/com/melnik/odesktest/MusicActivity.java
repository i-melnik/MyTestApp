package com.melnik.odesktest;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.melnik.odesktest.entity.MusicItem;
import com.melnik.odesktest.fragment.MusicAlbumFragment;
import com.melnik.odesktest.fragment.MusicAlbumFragment.MusicAlbumClickListener;
import com.melnik.odesktest.fragment.MusicCompositionFragment;
import com.melnik.odesktest.fragment.MusicCompositionFragment.MusicCompositionClickListener;
import com.melnik.odesktest.fragment.MusicFolderFragment;
import com.melnik.odesktest.fragment.MusicFolderFragment.MusicFolderClickListener;
import com.melnik.odesktest.fragment.MusicPlayFragment;
import com.melnik.odesktest.menu.MySlidingMenu;

public class MusicActivity extends BaseFragmentActivity implements MusicAlbumClickListener, MusicFolderClickListener,
		MusicCompositionClickListener
{

	private ImageButton btnAlbum;
	private ImageButton btnComposition;
	private ImageButton btnFolder;
	private ImageButton btnCompositionList;

	private MusicAlbumFragment musicAlbumFragment;
	private MusicCompositionFragment musicItemFragment;
	private MusicFolderFragment musicFolderFragment;
	private MusicPlayFragment musicPlayFragment;

	private ProgressDialog progressDialog;

	private List<Map<String, List<MusicItem>>> data;
	private List<Map<String, List<MusicItem>>> musicByFolderList;
	private List<MusicItem> musicServiceList;

	int currentPositionPlaying;

	private ListView listView;

	private Map<String, List<MusicItem>> musicByArtistMap;
	private Map<String, List<MusicItem>> musicByFolderMap;
	private SlidingMenu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.music_activity);
		// listView = (ListView) findViewById(R.id.music_list);
		btnAlbum = (ImageButton) findViewById(R.id.btn_music_album);
		btnComposition = (ImageButton) findViewById(R.id.btn_music_composition);
		btnFolder = (ImageButton) findViewById(R.id.btn_music_folder);
		btnCompositionList = (ImageButton) findViewById(R.id.btn_music_composition_list);

		final FragmentManager fm = getSupportFragmentManager();
		musicAlbumFragment = (MusicAlbumFragment) fm.findFragmentById(R.id.fragment_music_album);
		musicItemFragment = (MusicCompositionFragment) fm.findFragmentById(R.id.fragment_music_composition);
		musicFolderFragment = (MusicFolderFragment) fm.findFragmentById(R.id.fragment_music_folder);
		musicPlayFragment = (MusicPlayFragment) fm.findFragmentById(R.id.fragment_music_play);

		musicPlayFragment.setRetainInstance(true);
		btnAlbum.setOnClickListener(btnAlbumListener);
		btnFolder.setOnClickListener(btnFolderListener);
		btnComposition.setOnClickListener(btnCompositionListener);

		this.fragments = new Fragment[] { musicAlbumFragment, musicItemFragment, musicFolderFragment, musicPlayFragment };
		FragmentTransaction transaction = fm.beginTransaction();
		this.restoreFragment(transaction, musicPlayFragment, savedInstanceState);
		transaction.commit();
		this.replaceFragment(musicAlbumFragment, false);

		menu = MySlidingMenu.getMenu(this, getString(R.string.menu_solutions));
		showActionBar();
		new MusicLoaderAsyncTask().execute((Void) null);
	}

    @Override
	protected void onPause()
	{
		super.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		saveFragment(musicAlbumFragment, outState);
		saveFragment(musicFolderFragment, outState);
		saveFragment(musicItemFragment, outState);
		saveFragment(musicPlayFragment, outState);
	}

	@Override
	protected void onResume()
	{

		super.onResume();
	}

	private View.OnClickListener btnAlbumListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			replaceFragment(musicAlbumFragment, false);
		}
	};

	private View.OnClickListener btnFolderListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			replaceFragment(musicFolderFragment, false);
		}
	};

	private View.OnClickListener btnCompositionListener = new OnClickListener()
	{

		@Override
		public void onClick(View v)
		{
			musicItemFragment.setList(musicServiceList);
			replaceFragment(musicItemFragment, false);
		}
	};

	private void showActionBar()
	{
		// getSupportActionBar().setCustomView(R.layout.profile_action_bar);
		LayoutInflater inflator = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflator.inflate(R.layout.profile_action_bar, null);
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
	protected void onDestroy()
	{
		super.onDestroy();
	}

	class MusicLoaderAsyncTask extends AsyncTask<Void, Void, List<Map<String, List<MusicItem>>>>
	{

		private List<Map<String, List<MusicItem>>> musicList;

		@Override
		protected void onPreExecute()
		{
			progressDialog = new ProgressDialog(MusicActivity.this);
			// Set the progress dialog to display a horizontal progress bar
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			// Set the dialog title to 'Loading...'
			progressDialog.setTitle("Loading...");
			// Set the dialog message to 'Loading application View, please
			// wait...'
			progressDialog.setMessage("Loading music from device, please wait...");
			// This dialog can't be canceled by pressing the back key
			progressDialog.setCancelable(false);
			// This dialog isn't indeterminate
			progressDialog.setIndeterminate(false);
			// Display the progress dialog
			progressDialog.show();
		}

		@Override
		protected List<Map<String, List<MusicItem>>> doInBackground(Void... params)
		{
			musicList = new ArrayList<Map<String, List<MusicItem>>>();
			musicByFolderList = new ArrayList<Map<String, List<MusicItem>>>();
			ContentResolver contentResolver = getContentResolver();
			Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
			Cursor cursor = contentResolver.query(uri, null, null, null, null);
			if (cursor == null)
			{
				// query failed, handle error.
			}
			else if (!cursor.moveToFirst())
			{
				// no media on the device
			}
			else
			{
				int titleColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
				int idColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
				int artistColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
				int albumColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM);
				int idAlbumColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ALBUM_ID);
				int displayNameColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DISPLAY_NAME);
				int dataColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DATA);
				int durationColumn = cursor.getColumnIndex(android.provider.MediaStore.Audio.Media.DURATION);
				musicByArtistMap = new HashMap<String, List<MusicItem>>();
				musicByFolderMap = new HashMap<String, List<MusicItem>>();
				do
				{
					MusicItem item = new MusicItem();
					Long albumId = cursor.getLong(idAlbumColumn);
					String thisTitle = cursor.getString(titleColumn);
					String displayName = cursor.getString(displayNameColumn);
					String artist = cursor.getString(artistColumn);
					String album = cursor.getString(albumColumn);
					String dataString = cursor.getString(dataColumn);
					int duration = cursor.getInt(durationColumn);

					String[] paths = dataString.split("\\/");
					if (paths != null)
					{
						// System.out.println(paths[paths.length - 2]);
						item.setFolderName(paths[paths.length - 2]);
					}

					item.setTitle(thisTitle);
					item.setName(displayName);
					item.setAlbum(album);
					item.setArtist(artist);
					item.setDataUrl(dataString);
					item.setDuration(duration);
					item.setAlbumId(albumId);

					if (musicByArtistMap.containsKey(artist))
					{
						musicByArtistMap.get(artist).add(item);
					}
					else
					{
						List<MusicItem> mList = new ArrayList<MusicItem>();
						mList.add(item);
						musicByArtistMap.put(artist, mList);
					}

					if (musicByFolderMap.containsKey(item.getFolderName()))
					{
						musicByFolderMap.get(item.getFolderName()).add(item);
					}
					else
					{
						List<MusicItem> mList = new ArrayList<MusicItem>();
						mList.add(item);
						musicByFolderMap.put(item.getFolderName(), mList);
					}

				} while (cursor.moveToNext());

				musicServiceList = new ArrayList<MusicItem>();
				for (Entry<String, List<MusicItem>> entry : musicByArtistMap.entrySet())
				{
					Map<String, List<MusicItem>> oneArtistMap = new HashMap<String, List<MusicItem>>();
					oneArtistMap.put(entry.getKey(), entry.getValue());
					musicServiceList.addAll(entry.getValue());
					musicList.add(oneArtistMap);
				}
				// Setup list for MusicFolderFragment
				for (Entry<String, List<MusicItem>> entry : musicByFolderMap.entrySet())
				{
					Map<String, List<MusicItem>> oneArtistMap = new HashMap<String, List<MusicItem>>();
					oneArtistMap.put(entry.getKey(), entry.getValue());
					musicByFolderList.add(oneArtistMap);
				}
			}
			return musicList;
		}

		@Override
		protected void onPostExecute(List<Map<String, List<MusicItem>>> result)
		{
			progressDialog.dismiss();
			data = result;
			musicAlbumFragment.setList(data);
			musicItemFragment.setList(musicServiceList);
			musicFolderFragment.setList(musicByFolderList);
			// listView.setAdapter(new MusicListAdapter(MusicActivity.this,
			// data));
			// play(0);
		}
	}

	@Override
	public void onFolderSelected(List<MusicItem> data)
	{
		musicItemFragment.setList(data);
		replaceFragment(musicItemFragment, false);
	}

	@Override
	public void onAlbumSelected(List<MusicItem> data)
	{
		musicItemFragment.setList(data);
		replaceFragment(musicItemFragment, false);
	}

	@Override
	public void onSongSelected(List<MusicItem> data, int currentPosition)
	{
		musicPlayFragment.setCurrentPositionPlaying(currentPosition);
		musicPlayFragment.setMusic(data);
		musicPlayFragment.play(currentPosition);
		replaceFragment(musicPlayFragment, true);
	}
}
