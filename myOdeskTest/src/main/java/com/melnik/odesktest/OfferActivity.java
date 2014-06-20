package com.melnik.odesktest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.actionbarsherlock.app.ActionBar;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.melnik.odesktest.fragment.LaptopFragment;
import com.melnik.odesktest.fragment.MobileFragment;
import com.melnik.odesktest.fragment.MusicFragment;
import com.melnik.odesktest.fragment.OfferFragment;
import com.melnik.odesktest.fragment.SpecialOfferFragment;
import com.melnik.odesktest.menu.MySlidingMenu;

public class OfferActivity extends BaseFragmentActivity
{

	private ImageView laptop;
	private ImageView phone;
	private ImageView specialOffer;
	private ImageView music;

	private LaptopFragment laptopFragment;
	private MusicFragment musicFragment;
	private MobileFragment mobileFragment;
	private SpecialOfferFragment spFragment;
	private OfferFragment offerFragment;

	private SlidingMenu menu;

	@Override
	protected void onCreate(Bundle arg0)
	{

		super.onCreate(arg0);

		setContentView(R.layout.activity_offer);

		final FragmentManager fm = getSupportFragmentManager();
		offerFragment = (OfferFragment) fm.findFragmentById(R.id.fragment_offer);
		laptopFragment = (LaptopFragment) fm.findFragmentById(R.id.fragment_laptop);
		musicFragment = (MusicFragment) fm.findFragmentById(R.id.fragment_music);
		mobileFragment = (MobileFragment) fm.findFragmentById(R.id.fragment_mobile);
		spFragment = (SpecialOfferFragment) fm.findFragmentById(R.id.fragment_special_offer);

		laptop = (ImageView) offerFragment.getView().findViewById(R.id.imageView1);
		phone = (ImageView) offerFragment.getView().findViewById(R.id.imageView2);
		specialOffer = (ImageView) offerFragment.getView().findViewById(R.id.imageView3);
		music = (ImageView) offerFragment.getView().findViewById(R.id.imageView4);

		fragments = new Fragment[] { offerFragment, laptopFragment, musicFragment, mobileFragment, spFragment };

		final FragmentTransaction transaction = fm.beginTransaction();
		this.restoreFragment(transaction, laptopFragment, arg0);

		replaceFragment(offerFragment, true);

		laptop.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				replaceFragment(laptopFragment, true);
			}
		});
		music.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				replaceFragment(musicFragment, true);
			}
		});
		phone.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				replaceFragment(mobileFragment, true);
			}
		});
		specialOffer.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v)
			{
				replaceFragment(spFragment, true);
			}
		});

		fm.addOnBackStackChangedListener(new OnBackStackChangedListener()
		{

			@SuppressLint("NewApi")
			@Override
			public void onBackStackChanged()
			{
				if (fm.getBackStackEntryCount() == 0)
				{
					finish();
				}
			}

		});

		menu = MySlidingMenu.getMenu(this, getString(R.string.menu_offers));
		showActionBar();
	}

	@Override
	protected void onSaveInstanceState(Bundle bundle)
	{
		// TODO Auto-generated method stub
		super.onSaveInstanceState(bundle);

		saveFragment(this.offerFragment, bundle);
		saveFragment(this.laptopFragment, bundle);
		saveFragment(this.musicFragment, bundle);
		saveFragment(this.spFragment, bundle);
		saveFragment(this.mobileFragment, bundle);
	}

	private void showActionBar()
	{
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
}
