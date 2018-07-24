package user.com.stopthefakes.ui.application.list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.App;
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
		Intent intent = new Intent(context, ApplicationsListActivity.class);
		return intent;
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_applications);
		setUnbinder(ButterKnife.bind(this));

		initRecyclerView();
		initAdapter();
		dbApplicationList = App.getApp().getApplicationList();
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
	public void onSendSignalClicked(DbApplication dbApplication) {
		int id = dbApplication.getId().intValue();
		Intent intent = new Intent(this, GalleryMainActivity.class);
		intent.putExtra("id", id);
		startActivity(intent);
	}


	@Override
	public void onApplicationClicked(DbApplication dbApplication, int pos) {
		if (dbApplication.getType() == 1) {
			startActivity(InWorkApplicationActivity.newInstance(this).putExtra("id", pos));
		} else {
			startActivity(ApplicationActivity.newInstance(this).putExtra("id", pos));
		}
	}


	@Override
	public void onTakingInWorkClicked(DbApplication dbApplication) { }


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