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
			startActivity(new Intent(this, AuthorizationActivity.class));
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
			startActivity(new Intent(this, AuthorizationActivity.class));
		}
	}


	@OnClick(R.id.settings)
	protected void onClickSetting() {
		startActivity(new Intent(this, ProfileActivity.class));
	}


	@OnClick(R.id.balance)
	protected void onClickBalance() {
		startActivity(new Intent(this, FinanceHistoryActivity.class));
	}


	@OnClick(R.id.mySignal)
	protected void onClickSignal() {
		startActivity(new Intent(this, AllSignalsActivity.class));
	}


	@OnClick(R.id.messages)
	protected void onClickMessages(){
		startActivity(new Intent(this, ChatActivity.class));
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
		startActivity(ApplicationsListActivity.newInstance(this));
	}


	@OnClick(R.id.sendSignalNavigationButton)
	public void openSignals(){
		startActivity(SendSignalPageActivity.newInstance(this));
	}


	@OnClick(R.id.goToMenuPageButton)
	protected void openSettings() {
		startActivity(new Intent(this, SettingsActivity.class));
	}


	protected void logout() {
		App.getApp().logout();
		startActivity(new Intent(this, AuthorizationActivity.class));
	}

}