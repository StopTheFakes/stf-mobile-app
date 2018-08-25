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

import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.App;
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;
import user.com.stopthefakes.SettingsActivity;
import user.com.stopthefakes.entity.DbApplication;
import user.com.stopthefakes.entity.Value;
import user.com.stopthefakes.ui.application.list.ApplicationsAdapter;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;
import user.com.stopthefakes.ui.application.signal.SendSignalPageActivity;
import user.com.stopthefakes.ui.gallery.CustomImageContainer;
import user.com.stopthefakes.ui.gallery.GalleryMainActivity;

public class InWorkApplicationActivity extends BaseActivity {

	@BindView(R.id.imageContainer)
	CustomImageContainer mImageContainer;

	@BindView(R.id.photoInfoDetails)
	TextView photoInfoDetails;

	@BindView(R.id.detailsHeader)
	TextView detailsHeader;

	@BindView(R.id.timerInWork)
	TextView timerInWork;

	@BindView(R.id.countryTextView)
	TextView countryTextView;

	@BindView(R.id.citiesListTextView)
	TextView citiesListTextView;

	@BindView(R.id.descriptionDetails)
	TextView descriptionDetails;

	@BindView(R.id.rightsInfoTextView)
	TextView rightsInfoTextView;

	@BindView(R.id.tipesInfoTextView)
	TextView tipesInfoTextView;

	@BindView(R.id.pictureQuantityTextView)
	TextView pictureQuantityTextView;

	@BindView(R.id.automobileCheckBox)
	TextView automobileCheckBox;

	@BindView(R.id.logoCheckBox)
	TextView logoCheckBox;

	@BindView(R.id.container_for_checkbox)
	LinearLayout container_for_checkbox;

	@BindView(R.id.videosQuantityTextView)
	TextView videosQuantityTextView;

	@BindView(R.id.countryImageView)
	RoundedImageView countryImageView;

	@BindView(R.id.photosQuantityTextView)
	TextView photosQuantityTextView;

	int id;
	DbApplication mDbApplication;


	public static Intent newInstance(Context context) {
		return new Intent(context, InWorkApplicationActivity.class);
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_application_in_work_details);
		setUnbinder(ButterKnife.bind(this));

		id = getIntent().getIntExtra("id", -1);

		if (id == -1) {
			openStartPage();
			return;
		}

		ApplicationsAdapter apps = App.getApp().getApplicationsAdapter();

		if (apps.getItemCount() < id) {
			Log.e("ExpiredApplication", "Application with id " + id + " not founded");
			openStartPage();
			return;
		}

		mDbApplication = apps.getItem(id);

		for (int i = 0; i < mDbApplication.getImages().length; i++) {
			mImageContainer.addImage(mDbApplication.getImages()[i]);
		}

		descriptionDetails.setText(mDbApplication.getShordDescription());
		detailsHeader.setText(mDbApplication.getHeader());
		countryTextView.setText(mDbApplication.getCountry());
		rightsInfoTextView.setText(mDbApplication.getRightToUser());
		tipesInfoTextView.setText(mDbApplication.getTipes());
		timerInWork.setText(mDbApplication.getTimeLeft());
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

		citiesListTextView.setText(TextUtils.join(", ", mDbApplication.getCitiesList()));

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

	@OnClick(R.id.returnBackTextView)
	public void returnBack() {
		onBackPressed();
	}

	@OnClick(R.id.sendSignalButton)
	public void clickSendSignal() {
		startActivity(GalleryMainActivity.newInstance(this).putExtra("id", id));
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
