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


	protected final void setUnbinder(@Nullable Unbinder unbinder) {
		this.unbinder = unbinder;
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String token = sharedPref.getString("token", "");
		if (token.length() > 0) {
			if (this instanceof AuthorizationActivity) {
				Intent intent = new Intent(this, ApplicationsListActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			}
		} else {
			if (!(this instanceof AuthorizationActivity)) {
				Intent intent = new Intent(this, AuthorizationActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
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