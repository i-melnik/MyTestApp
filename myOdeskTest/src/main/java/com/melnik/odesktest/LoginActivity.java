package com.melnik.odesktest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.melnik.odesktest.menu.MySlidingMenu;
import com.melnik.odesktest.util.Constants;

public class LoginActivity extends SherlockFragmentActivity {

	private Button backButton;
	private SlidingMenu menu;
    private Button submit;
    private TextView name;
    private TextView email;
    private TextView phoneNumber;
    private TextView message;
	
	@Override
	protected void onCreate(Bundle arg0) {

		super.onCreate(arg0);
		
		setContentView(R.layout.activity_login);
		
		backButton = (Button) findViewById(R.id.login_header);
        submit = (Button) findViewById(R.id.login_submit);
        name= (TextView) findViewById(R.id.login_name);
        email = (TextView) findViewById(R.id.login_email);
        phoneNumber = (TextView) findViewById(R.id.login_phone);
        message = (TextView) findViewById(R.id.loginMessage);

        SharedPreferences prefs = getSharedPreferences(Constants.LOGIN_PREFS, Context.MODE_PRIVATE);
        if (prefs != null)
        {
            if (prefs.contains(Constants.LOGIN_NAME_KEY))
            {
                name.setText(prefs.getString(Constants.LOGIN_NAME_KEY, ""));
            }
            if (prefs.contains(Constants.LOGIN_EMAIL_KEY))
            {
                email.setText(prefs.getString(Constants.LOGIN_EMAIL_KEY, ""));
            }
            if (prefs.contains(Constants.LOGIN_PHONE_KEY))
            {
                phoneNumber.setText(prefs.getString(Constants.LOGIN_PHONE_KEY, ""));
            }
            if (prefs.contains(Constants.LOGIN_MESSAGE_KEY))
            {
                message.setText(prefs.getString(Constants.LOGIN_MESSAGE_KEY, ""));
            }
        }
        submit.setOnClickListener(submitListener);
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});
		
		menu = MySlidingMenu.getMenu(this, getString(R.string.menu_login));
		
		showActionBar();
	}
	
	private void showActionBar() {
		//getSupportActionBar().setCustomView(R.layout.profile_action_bar);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    View v = inflater.inflate(R.layout.profile_action_bar, null);
	    ActionBar actionBar = getSupportActionBar();
	    actionBar.setDisplayHomeAsUpEnabled(false);
	    actionBar.setDisplayShowHomeEnabled (false);
	    actionBar.setDisplayShowCustomEnabled(true);
	    actionBar.setDisplayShowTitleEnabled(false);
	    actionBar.setCustomView(v);
	    
	    ImageButton menuButton = (ImageButton) v.findViewById(R.id.menuButton);
	    menuButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				menu.toggle();
			}
		});
	}

    private final OnClickListener submitListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences prefs = getSharedPreferences(Constants.LOGIN_PREFS, Context.MODE_PRIVATE);
            prefs.edit()
            .putString(Constants.LOGIN_NAME_KEY, name.getText().toString())
            .putString(Constants.LOGIN_EMAIL_KEY, email.getText().toString())
            .putString(Constants.LOGIN_PHONE_KEY, phoneNumber.getText().toString())
            .putString(Constants.LOGIN_MESSAGE_KEY, message.getText().toString())
            .commit();
        }
    };
}
