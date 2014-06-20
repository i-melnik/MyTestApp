package com.melnik.odesktest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class BaseFragmentActivity extends SherlockFragmentActivity
{

	protected Fragment[] fragments;

	protected void restoreFragment(final FragmentTransaction transaction, final Fragment fragment, final Bundle state)
	{
		if (null == state || !state.getBoolean(fragment.getClass().getSimpleName()))
			transaction.hide(fragment);
		else
			transaction.show(fragment);
	}

	protected void saveFragment(final Fragment fragment, final Bundle bundle)
	{
		bundle.putBoolean(fragment.getClass().getSimpleName(), fragment.isVisible());
	}

	protected void replaceFragment(final Fragment show, boolean addToBackStack)
	{
		final FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
		for (final Fragment fragment : this.fragments)
		{
			if (null != fragment && fragment != show)
			{
				transaction.hide(fragment);
			}
		}

		if (null != show)
		{
			transaction.show(show);
			if (addToBackStack)
				transaction.addToBackStack(show.getClass().getSimpleName());
		}
		transaction.commit();

	}
}
