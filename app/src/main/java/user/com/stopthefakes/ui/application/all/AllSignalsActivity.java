package user.com.stopthefakes.ui.application.all;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

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
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;
import user.com.stopthefakes.SettingsActivity;
import user.com.stopthefakes.api.Request;
import user.com.stopthefakes.entity.DbApplication;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;


public class AllSignalsActivity extends BaseActivity {

	@BindView(R.id.appsRecyclerView)
	RecyclerView appsRecyclerView;

	private AppsAdapter appsAdapter;


	public static Intent newInstance(Context context) {
		return new Intent(context, AllSignalsActivity.class);
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_signals);
		setUnbinder(ButterKnife.bind(this));

		initRecyclerView();
		initAdapter();
	}


	@Override
	protected void onPostCreate(@Nullable Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		onAllClicked();
	}


	private void initRecyclerView() {
		appsRecyclerView.setHasFixedSize(true);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		appsRecyclerView.setLayoutManager(llm);
	}


	private void initAdapter() {
		appsAdapter = new AppsAdapter();
		appsRecyclerView.setAdapter(appsAdapter);
	}


	@OnClick(R.id.acceptedTextView)
	public void onAcceptedClicked() {
		reloadList(1);
	}


	@OnClick(R.id.cancelledTextView)
	public void onCancelledClicked() {
		reloadList(2);
	}


	@OnClick(R.id.onWatchingTextView)
	public void onWatchingClicked() {
		reloadList(3);
	}


	@OnClick(R.id.allTextView)
	public void onAllClicked() {
		reloadList(0);
	}


	protected void reloadList(int type) {
		if (appsAdapter == null) {
			appsAdapter = new AppsAdapter();
		}
		String url = "";
		switch (type) {
			case 0: url = "alerts"; break;
			case 1: url = "alerts-processed"; break;
			case 2: url = "alerts-declined"; break;
			case 3: url = "alerts-accepted"; break;
		}
		Request req = new Request(url, Request.Method.GET) {
			@Override
			public void onSuccess(JSONObject result) {
				try {
					JSONArray data = result.getJSONArray("data");
					List<DbApplication> appList = new ArrayList<>();

					for (int i = 0; i < data.length(); ++i) {
						JSONObject item = data.getJSONObject(i);
						DbApplication app = new DbApplication();

						app.setHeader(item.getString("title"));
						app.setShordDescription(item.getString("description"));
						app.setId((long) item.getInt("alert_id"));
						app.setExpiresString(item.getString("expires_at"));

						switch (item.getString("status")) {
							case "1":
								app.setType(0);
								app.setAllType(0);
								break;
							case "2":
								app.setType(1);
								app.setAllType(1);
								break;
							case "3":
								app.setType(2);
								app.setAllType(2);
								break;
						}

						appList.add(app);
					}
					appsAdapter.replaceAll(appList);
				} catch (JSONException e) {
					Log.e("reloadList", e.getMessage(), e);
				}
			}
		};

		req.process(App.getApp());
	}


	@OnClick(R.id.goToMainScreenButton)
	public void openStartPage() {
		startActivity(ApplicationsListActivity.newInstance(this));
	}


	@OnClick(R.id.sendSignalNavigationButton)
	public void openSignals(){
		startActivity(AllSignalsActivity.newInstance(this));
	}

	@OnClick(R.id.goToMenuPageButton)
	protected void openSettings() {
		startActivity(new Intent(this, SettingsActivity.class));
	}

}