package user.com.stopthefakes;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import user.com.stopthefakes.api.Request;
import user.com.stopthefakes.api.RequestCallback;
import user.com.stopthefakes.entity.DbApplication;
import user.com.stopthefakes.ui.application.list.ApplicationsAdapter;


public class App extends Application {

	private static App mApp;

	private ApplicationsAdapter applicationsAdapter;
	private List<DbApplication> applicationList;


	@Override
	public void onCreate() {
		super.onCreate();
		mApp = this;

		applicationList = new ArrayList<>();
		applicationsAdapter = new ApplicationsAdapter();
	}


	public static App getApp() {
		return mApp;
	}


	public void setApp(App app) {
		mApp = app;
	}


	public List<DbApplication> getApplicationList() {
		return applicationList;
	}


	public ApplicationsAdapter getApplicationsAdapter() {
		return applicationsAdapter;
	}


	public void reloadCurrentUser(final RequestCallback cb) {
		Request req = new Request("user", Request.Method.GET) {
			@Override
			public void onSuccess(JSONObject result) {
				try {
					SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					SharedPreferences.Editor editor = sharedPref.edit();

					JSONArray data = result.getJSONArray("data");
					JSONObject row = data.getJSONObject(0);

					editor.putInt(   "user.id",         row.getInt("id"));
					editor.putString("user.name",       row.getString("name"));
					editor.putString("user.email",      row.getString("email"));
					editor.putInt(   "user.country",    row.getInt("country"));
					editor.putInt(   "user.city",       row.getInt("city"));
					editor.putString("user.annotation", row.getString("annotation"));
					editor.putInt(   "user.notified",   row.getInt("notified"));
					editor.putString("user.avatar",     row.getString("avatar"));
					editor.putInt(   "user.stars",      row.getInt("stars"));
					editor.putInt(   "user.like",       row.getInt("like"));
					editor.putInt(   "user.dislike",    row.getInt("dislike"));
					editor.putInt(   "user.strike",     row.getInt("strike"));

					editor.apply();

					cb.onSuccess();
				} catch (Exception e) {
					cb.onError(e);
				}
			}

			@Override
			public void onError(Exception e) {
				cb.onError(e);
			}

			@Override
			public void onConnectionError(Exception e) {
				cb.onError(e);
			}
		};
		req.process(mApp);
	}


	public void setToken(String token) {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString("token", token);
		editor.apply();
	}


	public String getToken() {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		return sharedPref.getString("token", "");
	}


	public void logout() {
		setToken("");
	}

}