package user.com.stopthefakes.ui.application.applications;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
    @BindView(R.id.tipesInfoTextView)
    TextView tipesInfoTextView;
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

        id = (int) getIntent().getExtras().get("id");
        mDbApplication = App.getApp().getApplicationList().get(id);

        for (int i = 0; i < mDbApplication.getImages().length; i++) {
            mImageContainer.addImage(mDbApplication.getImages()[i]);
        }


        descriptionDetails.setText(mDbApplication.getShordDescription());
        detailsHeader.setText(mDbApplication.getHeader());
        countryTextView.setText(mDbApplication.getCountry());


        photoInfoDetails.setText(mDbApplication.getAlerts()[0]);
        rightsInfoTextView.setText(mDbApplication.getRightToUser());
        tipesInfoTextView.setText(mDbApplication.getTipes());

        mImageContainer.setClickListener(new CustomImageContainer.IClickListener() {
            @Override
            public void onClick(int position) {

                photoInfoDetails.setText(mDbApplication.getAlerts()[position]);
            }

            @Override
            public void hideImage() {
            }
        });

        String cities = mDbApplication.getCitiesList().get(0);
        for (int i = 1; i < mDbApplication.getCitiesList().size(); i++) {
            cities = cities + ", " + mDbApplication.getCitiesList().get(i);
        }
        citiesListTextView.setText(cities);

        for (Value value : mDbApplication.getPhotoVideo()) {
            if (value.type == 1) {
                photosQuantityTextView.setVisibility(View.VISIBLE);
                photosQuantityTextView.setText(value.value + " STF");
            }
            if (value.type == 2) {
                videosQuantityTextView.setVisibility(View.VISIBLE);
                videosQuantityTextView.setText(value.value + " STF");
            }
            if (value.type == 3) {
                pictureQuantityTextView.setVisibility(View.VISIBLE);
                pictureQuantityTextView.setText(value.value + " STF");
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

        if (mDbApplication.getCountry().equals("England")) {
            countryImageView.setImageResource(R.drawable.en_flag);
        } else if (mDbApplication.getCountry().equals("USA")) {
            countryImageView.setImageResource(R.drawable.us_flag);
        } else if (mDbApplication.getCountry().equals("Poland")) {
            countryImageView.setImageResource(R.drawable.po_flag);
        }
    }

    @OnClick(R.id.returnBackTextView)
    public void returnBack() {
        onBackPressed();
    }

    @OnClick(R.id.takeInWorkButton)
    public void clickTakeInWork() {
        mDbApplication.setType(1);
        startActivity(InWorkApplicationActivity.newInstance(this).putExtra("id", id));
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
