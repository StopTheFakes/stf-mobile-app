package user.com.stopthefakes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.api.City;
import user.com.stopthefakes.api.Country;
import user.com.stopthefakes.api.Request;
import user.com.stopthefakes.api.RequestCallback;


public class ProfileActivity extends BaseActivity {

	@BindView(R.id.spinnerCountry)
	Spinner spinnerCountry;

	@BindView(R.id.spinnerCity)
	Spinner spinnerCity;

	@BindView(R.id.starsRateView)
	TextView starsRateView;

	@BindView(R.id.positiveRateView)
	TextView positiveRateView;

	@BindView(R.id.negativeRateView)
	TextView negativeRateView;

	@BindView(R.id.attentionRateView)
	TextView attentionRateView;

	@BindView(R.id.bonusCoefficientView)
	TextView bonusCoefficientView;

	@BindView(R.id.nameTextView)
	TextView nameTextView;

	@BindView(R.id.emailTextView)
	TextView emailTextView;

	@BindView(R.id.annotationTextView)
	TextView annotationTextView;

	@BindView(R.id.countryImageView)
	RoundedImageView countryImageView;

	@BindView(R.id.notifyInstantlyRadioButtonView)
	RadioButton notifyInstantlyRadioButtonView;

	@BindView(R.id.notifySixRadioButtonView)
	RadioButton notifySixRadioButtonView;

	@BindView(R.id.notifyTwentyRadioButtonView)
	RadioButton notifyTwentyRadioButtonView;

	@BindView(R.id.notifyDailyRadioButtonView)
	RadioButton notifyDailyRadioButtonView;

	@BindView(R.id.notifyOffRadioButtonView)
	RadioButton notifyOffRadioButtonView;

	@BindView(R.id.passwordCurrentView)
	EditText passwordCurrentView;

	@BindView(R.id.passwordNewView)
	EditText passwordNewView;

	@BindView(R.id.paymentTextView)
	EditText paymentTextView;

	private ArrayAdapter<City> cityAdapter;
	private String currentCountry;
	private String currentCity;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		setUnbinder(ButterKnife.bind(this));

		final App app = App.getApp();

		ArrayList<Country> countryList = new ArrayList<>();
		ArrayList<City> citiesList  = new ArrayList<>();

		countryList.add(new Country("0", "County"));
		citiesList.add(new City("0", "City"));

		currentCountry = String.format(Locale.getDefault(), "%d", app.getIntFromStore("user.country"));
		currentCity = String.format(Locale.getDefault(), "%d", app.getIntFromStore("user.city"));

		final ArrayAdapter<Country> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countryList);
		countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCountry.setAdapter(countryAdapter);

		cityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, citiesList);
		cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCity.setAdapter(cityAdapter);

		Request req = new Request("countries", Request.Method.GET) {
			@Override
			public void onSuccess(JSONObject result) {
				try {
					JSONArray list = result.getJSONArray("countries");
					for (Integer i = 0; i < list.length(); ++i) {
						JSONObject ctr = list.getJSONObject(i);
						countryAdapter.add(new Country(ctr.getString("id"), ctr.getString("name")));
					}
					spinnerCountry.setSelection(countryAdapter.getPosition(new Country(currentCountry)));
					countryAdapter.notifyDataSetChanged();
					loadCitiesForCountry(currentCountry);
				} catch (Exception e) {
					Log.e("profile.countries", e.getMessage(), e);
				}
			}
		};
		req.process(app);

		spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
				Country country = (Country) adapterView.getSelectedItem();
				loadCitiesForCountry(country.getId());
			}

			@Override
			public void onNothingSelected(AdapterView<?> adapterView) { }
		});

		starsRateView.setText(String.format(Locale.getDefault(), "%d", app.getIntFromStore("user.stars")));
		positiveRateView.setText(String.format(Locale.getDefault(), "%d", app.getIntFromStore("user.like")));
		negativeRateView.setText(String.format(Locale.getDefault(), "%d", app.getIntFromStore("user.dislike")));
		attentionRateView.setText(String.format(Locale.getDefault(), "%d", app.getIntFromStore("user.notified")));
		bonusCoefficientView.setText("5%");
		nameTextView.setText(app.getStringFromStore("user.name"));
		emailTextView.setText(app.getStringFromStore("user.email"));
		annotationTextView.setText(app.getStringFromStore("user.annotation"));

		String avatar = app.getStringFromStore("user.avatar");
		if (avatar.equals("")) {
			countryImageView.setVisibility(View.GONE);
		} else {
			countryImageView.setVisibility(View.GONE);
		}

		notifyInstantlyRadioButtonView.setChecked(false);
		notifySixRadioButtonView.setChecked(false);
		notifyTwentyRadioButtonView.setChecked(false);
		notifyDailyRadioButtonView.setChecked(false);
		notifyOffRadioButtonView.setChecked(false);

		switch (app.getIntFromStore("user.notified")) {
			case 1: // Instantly
				notifyInstantlyRadioButtonView.setChecked(true);
				break;
			case 2: // Every 6 hours
				notifySixRadioButtonView.setChecked(true);
				break;
			case 3: // Every 12 hours
				notifyTwentyRadioButtonView.setChecked(true);
				break;
			case 4: // Every 24 hours
				notifyDailyRadioButtonView.setChecked(true);
				break;
			case 5: // Turn off notifications
				notifyOffRadioButtonView.setChecked(true);
				break;
		}
	}


	@OnClick(R.id.cancelButtonView)
	protected void openSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}


	@OnClick(R.id.saveButtonView)
	protected void saveProfile() {
		String idCountry  = ((Country) spinnerCountry.getSelectedItem()).getId();
		String idCity     = ((City) spinnerCity.getSelectedItem()).getId();
		String name       = nameTextView.getText().toString();
		String email      = emailTextView.getText().toString();
		String annotation = annotationTextView.getText().toString();
		String notify     = "0";
		String payment    = paymentTextView.getText().toString();

		Integer paymentAmount = 0;

		String oldPassword = passwordCurrentView.getText().toString();
		String newPassword = passwordNewView.getText().toString();

		passwordCurrentView.setError(null);
		passwordNewView.setError(null);

		if (oldPassword.length() > 0 || newPassword.length() > 0) {
			if (oldPassword.length() == 0) {
				passwordCurrentView.setError("Enter current password");
				return;
			}
			if (newPassword.length() == 0) {
				passwordNewView.setError("Enter new password");
				return;
			}
		}

		if (payment.length() > 0) {
			try {
				paymentAmount = Integer.parseInt(payment);
				if (paymentAmount <= 0) {
					paymentTextView.setError("Enter payment amount");
					return;
				}
			} catch (Exception e) {
				Log.e("profile.save", e.getMessage(), e);
				paymentTextView.setError("Enter payment amount");
				return;
			}
		}

		final App app = App.getApp();

		if (notifyInstantlyRadioButtonView.isChecked()) {
			notify = "1";
		} else if (notifySixRadioButtonView.isChecked()) {
			notify = "2";
		} else if (notifyTwentyRadioButtonView.isChecked()) {
			notify = "3";
		} else if (notifyDailyRadioButtonView.isChecked()) {
			notify = "4";
		} else if (notifyOffRadioButtonView.isChecked()) {
			notify = "5";
		}

		Request req = new Request("user", Request.Method.POST) {
			@Override
			public void onSuccess(JSONObject result) {
				try {
					if (result.getBoolean("success")) {
						app.toast(R.string.profile_saved);
						passwordCurrentView.setText("");
						passwordNewView.setText("");
						paymentTextView.setText("");
						app.reloadCurrentUser(new RequestCallback() {
							public void onSuccess() {}
							public void onError(Exception e) {}
						});
						return;
					}
				} catch (Exception e) {
					Log.e("profile.countries", e.getMessage(), e);
				}
				try {
					JSONObject errors = result.getJSONObject("errors");
					passwordCurrentView.setError(errors.getString("password"));
				} catch (Exception e) {
					Log.e("profile.countries", e.getMessage(), e);
				}
			}
		};

		req.addBodyParam("country_id",   idCountry);
		req.addBodyParam("city_id",      idCity);
		req.addBodyParam("annotation",   annotation);
		req.addBodyParam("notified",     notify);
		req.addBodyParam("name",         name);
		req.addBodyParam("email",        email);
		req.addBodyParam("password_old", oldPassword);
		req.addBodyParam("password_new", newPassword);
		req.addBodyParam("payment",      paymentAmount > 0 ? Integer.toString(paymentAmount) : "");

		req.process(app);
	}


	protected void loadCitiesForCountry(String idCountry) {
		cityAdapter.clear();
		cityAdapter.add(new City("0", "City"));
		cityAdapter.notifyDataSetChanged();
		Request req = new Request("cities/" + idCountry, Request.Method.GET) {
			@Override
			public void onSuccess(JSONObject result) {
				try {
					JSONArray list = result.getJSONArray("cities");
					for (Integer i = 0; i < list.length(); ++i) {
						JSONObject c = list.getJSONObject(i);
						cityAdapter.add(new City(c.getString("id"), c.getString("name")));
					}
					cityAdapter.notifyDataSetChanged();
					spinnerCity.setSelection(cityAdapter.getPosition(new City(currentCity)));
				} catch (Exception e) {
					Log.e("profile.cities", e.getMessage(), e);
				}
			}
		};
		req.process(App.getApp());
	}

}