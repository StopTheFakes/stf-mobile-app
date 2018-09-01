package user.com.stopthefakes;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

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
		final App app = this;
		Request req = new Request("user", Request.Method.GET) {
			@Override
			public void onSuccess(JSONObject result) {
				try {
					SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
					SharedPreferences.Editor editor = sharedPref.edit();

					JSONArray data = result.getJSONArray("data");
					JSONObject row = data.getJSONObject(0);

					editor.putInt("user.id", app.getIntFromJSONObject(row,"id", 0));
					editor.putString("user.name", app.getStringFromJSONObject(row, "name", ""));
					editor.putString("user.email", app.getStringFromJSONObject(row, "email", ""));
					editor.putInt("user.country", app.getIntFromJSONObject(row,"country", 0));
					editor.putInt("user.city", app.getIntFromJSONObject(row,"city", 0));
					editor.putString("user.annotation", app.getStringFromJSONObject(row, "annotation", ""));
					editor.putInt("user.notified", app.getIntFromJSONObject(row,"notified", 0));
					editor.putString("user.avatar", app.getStringFromJSONObject(row, "avatar", ""));
					editor.putInt("user.stars", app.getIntFromJSONObject(row,"stars", 0));
					editor.putInt("user.like", app.getIntFromJSONObject(row,"like", 0));
					editor.putInt("user.dislike", app.getIntFromJSONObject(row,"dislike", 0));
					editor.putInt("user.strike", app.getIntFromJSONObject(row,"strike", 0));

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


	public void toast(Integer message) {
		Toast.makeText(getApplicationContext(), getString(message), Toast.LENGTH_LONG).show();
	}


	public void logout() {
		setToken("");
	}


	public int getIntFromJSONObject(JSONObject obj, String key, Integer def) {
		try {
			return obj.has(key) && !obj.isNull(key) ? obj.getInt(key) : def;
		} catch (Exception e) {
			Log.e("getIntFromJSONObject", e.getMessage(), e);
			return def;
		}
	}


	public String getStringFromJSONObject(JSONObject obj, String key, String def) {
		try {
			return obj.has(key) && !obj.isNull(key) ? obj.getString(key) : def;
		} catch (Exception e) {
			Log.e("getIntFromJSONObject", e.getMessage(), e);
			return def;
		}
	}

}