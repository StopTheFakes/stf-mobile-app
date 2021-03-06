package user.com.stopthefakes.ui.application.applications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.App;
import user.com.stopthefakes.AuthorizationActivity;
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;
import user.com.stopthefakes.SettingsActivity;
import user.com.stopthefakes.api.Request;
import user.com.stopthefakes.entity.DbApplication;
import user.com.stopthefakes.entity.Value;
import user.com.stopthefakes.ui.application.all.AllSignalsActivity;
import user.com.stopthefakes.ui.application.list.ApplicationsAdapter;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;
import user.com.stopthefakes.ui.application.signal.SendSignalPageActivity;
import user.com.stopthefakes.ui.gallery.CustomImageContainer;


public class ApplicationActivity extends BaseActivity {

	@BindView(R.id.imageContainer)
	CustomImageContainer mImageContainer;

	@BindView(R.id.photoInfoDetails)
	TextView photoInfoDetails;

	@BindView(R.id.detailsHeader)
	TextView detailsHeader;

	@BindView(R.id.countryTextView)
	TextView countryTextView;

	@BindView(R.id.citiesListTextView)
	TextView citiesListTextView;

	@BindView(R.id.descriptionDetails)
	TextView descriptionDetails;

	@BindView(R.id.rightsInfoTextView)
	TextView rightsInfoTextView;

	@BindView(R.id.tipsInfoTextView)
	TextView tipsInfoTextView;

	@BindView(R.id.photosQuantityTextView)
	TextView photosQuantityTextView;

	@BindView(R.id.videosQuantityTextView)
	TextView videosQuantityTextView;

	@BindView(R.id.pictureQuantityTextView)
	TextView pictureQuantityTextView;

	@BindView(R.id.automobileCheckBox)
	TextView automobileCheckBox;

	@BindView(R.id.logoCheckBox)
	TextView logoCheckBox;

	@BindView(R.id.container_for_checkbox)
	LinearLayout container_for_checkbox;

	@BindView(R.id.countryImageView)
	RoundedImageView countryImageView;

	int id;
	DbApplication mDbApplication;


	public static Intent newInstance(Context context) {
		return new Intent(context, ApplicationActivity.class);
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_application_details);
		setUnbinder(ButterKnife.bind(this));

		App app = App.getApp();
		String token = app.getToken();

		if (token.equals("")) {
			Intent intent = new Intent(this, AuthorizationActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
			return;
		}

		id = getIntent().getIntExtra("id", -1);

		if (id == -1) {
			openStartPage();
			return;
		}

		ApplicationsAdapter apps = app.getApplicationsAdapter();

		if (apps.getItemCount() < id) {
			Log.e("ExpiredApplication", "Application with id " + id + " not founded");
			openStartPage();
			return;
		}

		mDbApplication = apps.getItem(id);

		descriptionDetails.setText(mDbApplication.getShordDescription());
		detailsHeader.setText(mDbApplication.getHeader());
		countryTextView.setText(mDbApplication.getCountry());
		rightsInfoTextView.setText(mDbApplication.getRightToUser());
		tipsInfoTextView.setText(mDbApplication.getTips());
		citiesListTextView.setText(TextUtils.join(", ", mDbApplication.getCitiesList()));

		String[] alerts = mDbApplication.getAlerts();
		if (alerts.length > 0) {
			photoInfoDetails.setText(alerts[0]);
		}

		mImageContainer.setClickListener(new CustomImageContainer.IClickListener() {
			@Override
			public void onClick(int position) {
				String[] alerts = mDbApplication.getAlerts();
				photoInfoDetails.setText(alerts.length > position ? alerts[position] : "");
			}

			@Override
			public void hideImage() { }
		});

		for (Value value : mDbApplication.getPhotoVideo()) {
			switch (value.type) {
				case 1:
					photosQuantityTextView.setVisibility(View.VISIBLE);
					photosQuantityTextView.setText(getString(R.string.currency_label, value.value));
					break;
				case 2:
					videosQuantityTextView.setVisibility(View.VISIBLE);
					videosQuantityTextView.setText(getString(R.string.currency_label, value.value));
					break;
				case 3:
					pictureQuantityTextView.setVisibility(View.VISIBLE);
					pictureQuantityTextView.setText(getString(R.string.currency_label, value.value));
					break;
			}
		}

		logoCheckBox.setText(mDbApplication.getSearchOpject());
		automobileCheckBox.setText(mDbApplication.getTopik());

		for (String value : mDbApplication.getAccepted()) {
			CheckBox checkBox = new CheckBox(this);
			checkBox.setChecked(true);
			checkBox.setText(value);
			container_for_checkbox.addView(checkBox);
		}

		switch (mDbApplication.getCountry()) {
			case "England":
				countryImageView.setImageResource(R.drawable.en_flag);
				break;
			case "USA":
				countryImageView.setImageResource(R.drawable.us_flag);
				break;
			case "Poland":
				countryImageView.setImageResource(R.drawable.po_flag);
				break;
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		String token = App.getApp().getToken();
		if (token.equals("")) {
			Intent intent = new Intent(this, AuthorizationActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
		}
	}


	@OnClick(R.id.returnBackTextView)
	public void returnBack() {
		openStartPage();
	}


	@OnClick(R.id.takeInWorkButton)
	public void clickTakeInWork() {
		if (mDbApplication.isTaken()) {
			Toast.makeText(getApplicationContext(), getString(R.string.application_already_in_progress), Toast.LENGTH_LONG).show();
			return;
		}
		if (mDbApplication.isExpired()) {
			Toast.makeText(getApplicationContext(), getString(R.string.application_expired), Toast.LENGTH_LONG).show();
			return;
		}
		Request req = new Request("request/" + String.valueOf(mDbApplication.getId()) + "/take", Request.Method.POST) {
			@Override
			public void onSuccess(JSONObject r) {
				try {
					JSONObject data = r.getJSONObject("data");
					int expires = data.getInt("expires");
					mDbApplication.setExpires(expires);
					mDbApplication.setIsTaken();
					mDbApplication.setAllIsTaken();
					Toast.makeText(getApplicationContext(), getString(R.string.application_taken), Toast.LENGTH_LONG).show();
					Intent intent = InWorkApplicationActivity.newInstance(App.getApp().getApplicationContext()).putExtra("id", id);
					intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
					finish();
				} catch (Exception e) {
					Log.e("appActivity", e.getMessage(), e);
					Toast.makeText(getApplicationContext(), getString(R.string.api_err_server_err), Toast.LENGTH_LONG).show();
				}
			}

			@Override
			public void onError(Exception e) {
				super.onError(e);
				Toast.makeText(getApplicationContext(), getString(R.string.api_err_server_err), Toast.LENGTH_LONG).show();
			}
		};
		req.process(App.getApp());
	}


	@OnClick(R.id.goToMainScreenButton)
	public void openStartPage() {
		Intent intent = ApplicationsListActivity.newInstance(this);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}


	@OnClick(R.id.sendSignalNavigationButton)
	public void openSignals() {
		Intent intent = AllSignalsActivity.newInstance(this);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}


	@OnClick(R.id.goToMenuPageButton)
	protected void openSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}

}