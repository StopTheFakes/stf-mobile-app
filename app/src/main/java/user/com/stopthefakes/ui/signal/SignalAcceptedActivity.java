package user.com.stopthefakes.ui.signal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.App;
import user.com.stopthefakes.AuthorizationActivity;
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;
import user.com.stopthefakes.ui.application.all.AllSignalsActivity;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;
import user.com.stopthefakes.ui.application.signal.SendSignalPageActivity;


public class SignalAcceptedActivity extends BaseActivity {

	@BindView(R.id.signalTextView)
	TextView signalTextView;


	public static Intent newInstance(Context context) {
		return new Intent(context, SignalAcceptedActivity.class);
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signal_accepted);
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

		int id = getIntent().getIntExtra("id", -1);

		if (id == -1) {
			onBackPressed();
			Log.e("SignalAcceptedActivity", "Id not founded");
			return;
		}

		signalTextView.setText(String.format(app.getString(R.string.accepted_signal), id));
	}


	@OnClick(R.id.closeSignalAcceptedTextView)
	public void closeSignalAcceptedActivity() {
		Intent intent = ApplicationsListActivity.newInstance(this);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		finish();
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

}