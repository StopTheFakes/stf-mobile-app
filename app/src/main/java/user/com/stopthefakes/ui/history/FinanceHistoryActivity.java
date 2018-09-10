package user.com.stopthefakes.ui.history;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.App;
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;
import user.com.stopthefakes.SettingsActivity;
import user.com.stopthefakes.api.Request;
import user.com.stopthefakes.ui.application.all.AllSignalsActivity;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;
import user.com.stopthefakes.ui.application.signal.SendSignalPageActivity;


public class FinanceHistoryActivity extends BaseActivity {

	@BindView(R.id.balanceTextView)
	TextView balanceTextView;

	@BindView(R.id.addressEditText)
	EditText addressEditText;

	@BindView(R.id.sumEditText)
	EditText sumEditText;


	public static Intent newInstance(Context context) {
		return new Intent(context, FinanceHistoryActivity.class);
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finance_history);
		setUnbinder(ButterKnife.bind(this));
		App app = App.getApp();
		balanceTextView.setText(String.format(
			app.getApplicationContext().getString(R.string.currency_label),
			app.getStringFromStore("user.balance")
		));
		reloadHistory();
	}


	protected void reloadHistory() {
		final App app         = App.getApp();
		final Context context = app.getApplicationContext();

		Request req = new Request("finance-operations", Request.Method.GET) {
			@Override
			public void onSuccess(JSONObject result) {
				LinearLayout outer = findViewById(R.id.historyItemsLayout);
				outer.removeAllViews();

				String lastDate = "";

				try {
					JSONArray data = result.getJSONArray("data");

					DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
					DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH);
					DateFormat timeFormat = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);

					for (int i = 0; i < data.length(); ++i) {
						JSONObject item = data.getJSONObject(i);
						String created = item.getString("created_at");

						Date dateParsed =  sourceFormat.parse(created);

						String date = dateFormat.format(dateParsed);

						if (!lastDate.equals(date)) {
							lastDate = date;
							TextView dateView = (TextView) View.inflate(context, R.layout.list_item_finance_operation_date, null);
							dateView.setText(lastDate);
							outer.addView(dateView);
						}

						RelativeLayout row = (RelativeLayout) View.inflate(context, R.layout.list_item_finance_operation_row, null);
						TextView child = (TextView) row.getChildAt(0);
						Integer type = item.getInt("code_type");
						if (type == 2) {
							child.setText(app.getString(R.string.fin_op_title_withdraw));
						} else {
							child.setText(String.format(
								app.getApplicationContext().getString(R.string.fin_op_title_enroll),
								item.getString("id_alert"),
								item.getString("id_claim")
							));
						}
						child = (TextView) row.getChildAt(1);
						child.setText(String.format(
							app.getApplicationContext().getString(R.string.currency_label),
							(type == 2 ? "-" : "+") + item.getString("amount")
						));
						child = (TextView) row.getChildAt(2);
						child.setText(timeFormat.format(dateParsed));
						outer.addView(row);
					}
				} catch (Exception e) {
					Log.e("reloadHistory", e.getMessage(), e);
				}
			}
		};

		req.process(app);
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


	@OnClick(R.id.palyoutButton)
	protected void withdraw() {
		String wallet = addressEditText.getText().toString();
		String amount = sumEditText.getText().toString();

		addressEditText.setError(null);

		if (wallet.length() == 0 || amount.length() == 0) {
			if (wallet.length() == 0) {
				addressEditText.setError("Provide your wallet number");
			}
			if (amount.length() == 0) {
				sumEditText.setError("Enter the amount");
			}
			return;
		}

		try {
			if (Integer.parseInt(amount) <= 0) {
				sumEditText.setError("Enter the amount");
				return;
			}
		} catch (Exception e) {
			Log.e("withdraw", e.getMessage(), e);
			sumEditText.setError("Enter the amount");
			return;
		}

		final App app = App.getApp();

		Request req = new Request("withdraw", Request.Method.POST) {
			@Override
			public void onSuccess(JSONObject result) {
				app.toast(R.string.withdraw_request_has_been_sent);
				addressEditText.setText("");
				sumEditText.setText("");
				reloadHistory();
			}
		};

		req.addBodyParam("wallet", wallet);
		req.addBodyParam("amount", amount);

		req.process(app);
	}

}