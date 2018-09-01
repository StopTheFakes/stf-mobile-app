package user.com.stopthefakes.ui.application.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import user.com.stopthefakes.App;
import user.com.stopthefakes.AuthorizationActivity;
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;
import user.com.stopthefakes.SettingsActivity;
import user.com.stopthefakes.api.Request;
import user.com.stopthefakes.ui.application.applications.ApplicationActivity;
import user.com.stopthefakes.ui.application.applications.ExpiredApplicationActivity;
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


	public static Intent newInstance(Context context) {
		return new Intent(context, ApplicationsListActivity.class);
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_applications);
		setUnbinder(ButterKnife.bind(this));

		App app = App.getApp();
		String token = app.getToken();

		if (token.equals("")) {
			startActivity(new Intent(this, AuthorizationActivity.class));
		} else {
			initRecyclerView();
			initAdapter();
			reloadList();
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		String token = App.getApp().getToken();
		if (token.equals("")) {
			startActivity(new Intent(this, AuthorizationActivity.class));
		}
	}


	protected void reloadList() {
		Request req = new Request("request", Request.Method.GET) {
			@Override
			public void onSuccess(JSONObject result) {
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
						JSONArray citiesRaw = item.getJSONArray("cities");
						for (int l = 0; l < citiesRaw.length(); ++l) {
							cities.add(citiesRaw.getString(l));
						}
						app.setCitiesList(cities);
						app.setCountry(item.getString("country"));

						try {
							created = item.getString("created_at");
							DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);
							Date parsedDate = df.parse(created);
							df = new SimpleDateFormat("yyyy/MM/dd", Locale.ENGLISH);
							created = df.format(parsedDate);
						} catch (Exception e) {
							created = "----/--/--";
							Log.e("reloadList", e.getMessage(), e);
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

						appList.add(app);
					}
					applicationsAdapter.replaceAll(appList);
					applicationsAdapter.sort(true);
				} catch (JSONException e) {
					Log.e("reloadList", e.getMessage(), e);
					App.getApp().toast(R.string.api_err_server_err);
				}
			}

			@Override
			public void onError(Exception e) {
				super.onError(e);
				App.getApp().toast(R.string.api_err_server_err);
			}
		};

		req.process(App.getApp());
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


	private void initRecyclerView() {
		applicationsRecyclerView.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		applicationsRecyclerView.setLayoutManager(llm);
	}


	private void initAdapter() {
		applicationsAdapter = App.getApp().getApplicationsAdapter();
		applicationsAdapter.setOnApplicationActionListener(this);
		applicationsRecyclerView.setAdapter(applicationsAdapter);
	}


	@Override
	public void onSendSignalClicked(DbApplication app) {
		if (app.isExpired()) {
			App.getApp().toast(R.string.application_expired);
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
		} else if (app.isTaken()) {
			startActivity(InWorkApplicationActivity.newInstance(this).putExtra("id", pos));
		} else if (app.isExpired()) {
			startActivity(ExpiredApplicationActivity.newInstance(this).putExtra("id", pos));
		}
	}


	@Override
	public void onTakingInWorkClicked(DbApplication app) {
		if (app.isTaken()) {
			App.getApp().toast(R.string.application_already_in_progress);
			return;
		}
		if (app.isExpired()) {
			App.getApp().toast(R.string.application_expired);
			return;
		}
		Request req = new Request("request/" + String.valueOf(app.getId()) + "/take", Request.Method.POST) {
			@Override
			public void onSuccess(JSONObject r) {
				App.getApp().toast(R.string.application_taken);
				reloadList();
			}

			@Override
			public void onError(Exception e) {
				super.onError(e);
				reloadList();
			}
		};
		req.process(App.getApp());
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