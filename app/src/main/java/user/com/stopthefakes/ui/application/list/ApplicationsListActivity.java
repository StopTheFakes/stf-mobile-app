package user.com.stopthefakes.ui.application.list;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import user.com.stopthefakes.App;
import user.com.stopthefakes.AuthorizationActivity;
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;
import user.com.stopthefakes.SettingsActivity;
import user.com.stopthefakes.ui.application.applications.ApplicationActivity;
import user.com.stopthefakes.ui.application.applications.InWorkApplicationActivity;
import user.com.stopthefakes.entity.DbApplication;
import user.com.stopthefakes.ui.application.signal.SendSignalPageActivity;
import user.com.stopthefakes.ui.gallery.GalleryMainActivity;


public class ApplicationsListActivity extends BaseActivity implements ApplicationsAdapter.ApplicationActionListener {

	@BindView(R.id.applicationsRecyclerView)
	RecyclerView applicationsRecyclerView;
	@BindView(R.id.newestTextView)
	TextView newestTextView;
	@BindView(R.id.popularTextView)
	TextView popularTextView;

	private ApplicationsAdapter applicationsAdapter;
	private List<DbApplication> dbApplicationList;


	public static Intent newInstance(Context context) {
		return new Intent(context, ApplicationsListActivity.class);
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_applications);
		setUnbinder(ButterKnife.bind(this));

		initRecyclerView();
		initAdapter();
		reloadList();
	}


	protected void reloadList() {
		dbApplicationList = App.getApp().getApplicationList();

		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest stringRequest = new StringRequest(Request.Method.GET, getString(R.string.api_base_url) + "request",
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					try {
						JSONObject result = new JSONObject(response);
						try {
							JSONArray data = result.getJSONArray("data");
							JSONObject item;
							DbApplication app;
							List<String> cities;
							List<DbApplication> appList = new ArrayList<>();

							int expires;
							String created;

							for (int i = 0; i < data.length(); ++i) {
								item = data.getJSONObject(i);
								app = new DbApplication();
								app.setId(Long.parseLong(item.getString("claim_id")));
								app.setShordDescription("short desc");
								app.setHeader(item.getString("title"));
								app.setPhotosQuantity(100L);
								app.setVideosQuantity(103L);
								cities = new ArrayList<>();
								cities.add("New-York");
								cities.add("Los-Angeles");
								cities.add("Detroit");
								cities.add("Portland");
								app.setCitiesList(cities);
								app.setCountry("USA");
								app.setDescription("test desc");

								try {
									created = item.getString("created");
								} catch (Exception e) {
									created = "----/--/--";
								}
								app.setDate(created);

								try {
									expires = item.getInt("expires");
									app.setExpires(expires);
									if (expires > 0) {
										app.setIsTaken();
										app.setAllIsTaken();
									} else {
										app.setIsExpired();
										app.setAllIsExpired();
									}
								} catch (Exception e) {
									app.setIsWaiting();
									app.setAllIsWaiting();
								}

								app.setImages(new int[]{R.drawable.icon1, R.drawable.icon1, R.drawable.icon1});
								appList.add(app);
							}
							applicationsAdapter.replaceAll(appList);
						} catch (JSONException e) {
							Toast.makeText(getApplicationContext(), getString(R.string.api_err_server_err), Toast.LENGTH_LONG).show();
						}
					} catch (JSONException e) {
						Toast.makeText(getApplicationContext(), getString(R.string.api_err_server_err), Toast.LENGTH_LONG).show();
					}
				}
			},
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					if (error instanceof NoConnectionError) {
						Toast.makeText(getApplicationContext(), getString(R.string.api_err_conn_lost), Toast.LENGTH_LONG).show();
					} else if (error instanceof AuthFailureError) {
						logout();
					} else {
						Toast.makeText(getApplicationContext(), getString(R.string.api_err_server_err), Toast.LENGTH_LONG).show();
					}
				}
			}
		) {
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


	protected void logout() {
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString("token", "");
		editor.apply();
		startActivity(new Intent(this, AuthorizationActivity.class));
	}


	@OnClick(R.id.newestTextView)
	protected void clickFilterNewest() {
		newestTextView.setBackgroundResource(R.drawable.rounded_text_view_left);
		popularTextView.setBackgroundResource(R.color.transparent);
		newestTextView.setTextColor(getResources().getColor(R.color.textColorSecondary));
		popularTextView.setTextColor(getResources().getColor(R.color.white));
		applicationsAdapter.sort(true);
	}


	@OnClick(R.id.popularTextView)
	protected void clickFilterPopular() {
		newestTextView.setBackgroundResource(R.color.transparent);
		popularTextView.setBackgroundResource(R.drawable.rounded_text_view_right);
		newestTextView.setTextColor(getResources().getColor(R.color.white));
		popularTextView.setTextColor(getResources().getColor(R.color.textColorSecondary));
		applicationsAdapter.sort(false);
	}


	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		applicationsAdapter.replaceAll(dbApplicationList);
	}


	private void initRecyclerView() {
		applicationsRecyclerView.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		applicationsRecyclerView.setLayoutManager(llm);
	}


	private void initAdapter() {
		applicationsAdapter = new ApplicationsAdapter();
		applicationsAdapter.setOnApplicationActionListener(this);
		applicationsRecyclerView.setAdapter(applicationsAdapter);
	}


	@Override
	public void onSendSignalClicked(DbApplication app) {
		if (app.isExpired()) {
			Toast.makeText(getApplicationContext(), getString(R.string.application_expired), Toast.LENGTH_LONG).show();
			return;
		}
		int id = app.getId().intValue();
		Intent intent = new Intent(this, GalleryMainActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}


	@Override
	public void onApplicationClicked(DbApplication app, int pos) {
		if (app.isWaiting()) {
			startActivity(ApplicationActivity.newInstance(this).putExtra("id", pos));
		} else {
			startActivity(InWorkApplicationActivity.newInstance(this).putExtra("id", pos));
		}
	}


	@Override
	public void onTakingInWorkClicked(DbApplication app) {
		if (app.isTaken()) {
			Toast.makeText(getApplicationContext(), getString(R.string.application_already_in_progress), Toast.LENGTH_LONG).show();
			return;
		}
		if (app.isExpired()) {
			Toast.makeText(getApplicationContext(), getString(R.string.application_expired), Toast.LENGTH_LONG).show();
			return;
		}
		RequestQueue queue = Volley.newRequestQueue(this);
		String url = getString(R.string.api_base_url) + "request/" + String.valueOf(app.getId()) + "/take";
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					Toast.makeText(getApplicationContext(), getString(R.string.application_taken), Toast.LENGTH_LONG).show();
					reloadList();
				}
			},
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					if (error instanceof NoConnectionError) {
						Toast.makeText(getApplicationContext(), getString(R.string.api_err_conn_lost), Toast.LENGTH_LONG).show();
					} else if (error instanceof AuthFailureError) {
						logout();
					} else {
						Toast.makeText(getApplicationContext(), getString(R.string.api_err_server_err), Toast.LENGTH_LONG).show();
					}
				}
			}
		) {
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
	public void openSignals() {
		startActivity(SendSignalPageActivity.newInstance(this));
	}


	@OnClick(R.id.goToMenuPageButton)
	protected void openSettings() {
		startActivity(new Intent(this, SettingsActivity.class));
	}

}