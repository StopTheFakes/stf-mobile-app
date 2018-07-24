package user.com.stopthefakes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.Unbinder;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;


public abstract class BaseActivity extends AppCompatActivity {

	@Nullable
	private Unbinder unbinder;


	protected final void setUnbinder(Unbinder unbinder) {
		this.unbinder = unbinder;
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String token = sharedPref.getString("token", "");
		if (token.length() > 0) {
			if (this instanceof AuthorizationActivity) {
				startActivity(new Intent(this, ApplicationsListActivity.class));
			}
		} else {
			if (!(this instanceof AuthorizationActivity)) {
				startActivity(new Intent(this, AuthorizationActivity.class));
			}
		}
	}


	@CallSuper
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (unbinder != null) {
			unbinder.unbind();
			unbinder = null;
		}
	}

}