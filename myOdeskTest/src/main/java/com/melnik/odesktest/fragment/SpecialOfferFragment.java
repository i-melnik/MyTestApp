package com.melnik.odesktest.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragment;
import com.melnik.odesktest.R;

public class SpecialOfferFragment extends SherlockFragment {

	
	private Button backButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.offer_fragment, container);
		
		backButton = (Button) view.findViewById(R.id.offer_fragment_header);
		backButton.setText(R.string.special_offer_header);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SpecialOfferFragment.this.getFragmentManager().popBackStack();
			}
		});
		return view;
	}

}
