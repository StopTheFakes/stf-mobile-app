package user.com.stopthefakes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.ui.application.all.AllSignalsActivity;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;
import user.com.stopthefakes.ui.application.signal.SendSignalPageActivity;
import user.com.stopthefakes.ui.chat.ChatActivity;
import user.com.stopthefakes.ui.history.FinanceHistoryActivity;


public class SettingsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setUnbinder(ButterKnife.bind(this));
	}


	@OnClick(R.id.settings)
	protected void onClickSetting() {
		startActivity(new Intent(this, ProfileActivity.class));
	}


	@OnClick(R.id.balance)
	protected void onClickBalance() {
		startActivity(new Intent(this, FinanceHistoryActivity.class));
	}


	@OnClick(R.id.mySignal)
	protected void onClickSignal() {
		startActivity(new Intent(this, AllSignalsActivity.class));
	}


	@OnClick(R.id.messages)
	protected void onClickMessages(){
		startActivity(new Intent(this, ChatActivity.class));
	}


	@OnClick(R.id.exit)
	protected void onClickExit() {
		RequestQueue queue = Volley.newRequestQueue(this);

		StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.api_base_url) + "logout",
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Toast.makeText(getApplicationContext(), getString(R.string.api_mess_logout_finished), Toast.LENGTH_LONG).show();
					SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					SharedPreferences.Editor editor = sharedPref.edit();
					editor.putString("token", "");
					editor.apply();
					toLoginActivity();
				}
			}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (error instanceof NoConnectionError) {
					Toast.makeText(getApplicationContext(), getString(R.string.api_err_conn_lost), Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(), getString(R.string.api_err_server_err), Toast.LENGTH_LONG).show();
				}
			}
		}) {
			@Override
			public Map<String, String> getHeaders() {
				Map<String, String> headers = new HashMap<>();
				SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				String token = sharedPref.getString("token", "");
				headers.put("Authorization","Bearer " + token);
				return headers;
			}
		};
		queue.add(stringRequest);
	}


	@OnClick(R.id.goToMainScreenButton)
	public void openStartPage() {
		startActivity(ApplicationsListActivity.newInstance(this));
	}


	@OnClick(R.id.sendSignalNavigationButton)
	public void openSignals(){
		startActivity(SendSignalPageActivity.newInstance(this));
	}


	@OnClick(R.id.goToMenuPageButton)
	protected void openSettings() {
		startActivity(new Intent(this, SettingsActivity.class));
	}


	protected void toLoginActivity() {
		startActivity(new Intent(this, AuthorizationActivity.class));
	}

}