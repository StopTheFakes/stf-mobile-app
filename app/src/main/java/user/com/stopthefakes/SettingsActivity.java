package user.com.stopthefakes;

import android.content.Intent;
import android.os.Bundle;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.api.Request;
import user.com.stopthefakes.ui.application.all.AllSignalsActivity;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;
import user.com.stopthefakes.ui.application.signal.SendSignalPageActivity;
import user.com.stopthefakes.ui.chat.ChatActivity;
import user.com.stopthefakes.ui.history.FinanceHistoryActivity;


public class SettingsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		App app = App.getApp();
		String token = app.getToken();

		if (token.equals("")) {
			Intent intent = new Intent(this, AuthorizationActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
			return;
		}

		setContentView(R.layout.activity_settings);
		setUnbinder(ButterKnife.bind(this));
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


	@OnClick(R.id.settings)
	protected void onClickSetting() {
		Intent intent = new Intent(this, ProfileActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}


	@OnClick(R.id.balance)
	protected void onClickBalance() {
		Intent intent = new Intent(this, FinanceHistoryActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}


	@OnClick(R.id.mySignal)
	protected void onClickSignal() {
		Intent intent = new Intent(this, AllSignalsActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}


	@OnClick(R.id.messages)
	protected void onClickMessages() {
		Intent intent = new Intent(this, ChatActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}


	@OnClick(R.id.exit)
	protected void onClickExit() {
		Request req = new Request("logout", Request.Method.POST) {
			@Override
			public void onSuccess(JSONObject result) {
				logout();
			}

			@Override
			public void onError(Exception e) {
				super.onError(e);
				logout();
			}

			@Override
			public void onConnectionError(Exception e) {
				super.onConnectionError(e);
				logout();
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


	protected void logout() {
		App.getApp().logout();
		Intent intent = new Intent(this, AuthorizationActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
	}

}