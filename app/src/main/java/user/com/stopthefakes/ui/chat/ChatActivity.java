package user.com.stopthefakes.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.App;
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;
import user.com.stopthefakes.api.Request;


public class ChatActivity extends BaseActivity {

	@BindView(R.id.chatContainer)
	RelativeLayout chatContainer;

	@BindView(R.id.supportTextView)
	TextView supportTextView;

	@BindView(R.id.botTextView)
	TextView botTextView;

	@BindView(R.id.chatWriteMessageEditText)
	TextView chatWriteMessageEditText;

	@BindView(R.id.writeMessageContainer)
	RelativeLayout writeMessageContainer;

	protected SupportFragment supportInstance;


	public static Intent newInstance(Context context) {
		return new Intent(context, ChatActivity.class);
	}


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);
		setUnbinder(ButterKnife.bind(this));

		setFragment(false);
	}


	private void setFragment(boolean botChat) {
		if (!botChat) {
			supportInstance = SupportFragment.newInstance();
			getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.chatContainer, supportInstance)
				.addToBackStack(null)
				.commit();
			chatWriteMessageEditText.setEnabled(true);
			chatWriteMessageEditText.setHint("Enter your message here");
			writeMessageContainer.setVisibility(View.VISIBLE);
		} else {
			getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.chatContainer, BotFragment.newInstance())
				.addToBackStack(null)
				.commit();
			chatWriteMessageEditText.setEnabled(false);
			chatWriteMessageEditText.setHint("");
			writeMessageContainer.setVisibility(View.GONE);
		}
	}


	@OnClick(R.id.supportTextView)
	protected void clickSupportChat() {
		supportTextView.setBackgroundResource(R.drawable.rounded_text_view_left);
		botTextView.setBackgroundResource(R.color.transparent);
		supportTextView.setTextColor(getResources().getColor(R.color.textColorSecondary));
		botTextView.setTextColor(getResources().getColor(R.color.white));
		setFragment(false);
	}


	@OnClick(R.id.botTextView)
	protected void clickBotChat() {
		supportTextView.setBackgroundResource(R.color.transparent);
		botTextView.setBackgroundResource(R.drawable.rounded_text_view_right);
		supportTextView.setTextColor(getResources().getColor(R.color.white));
		botTextView.setTextColor(getResources().getColor(R.color.textColorSecondary));
		setFragment(true);
	}


	@OnClick(R.id.sendMessageButton)
	protected void sendSupportMessage() {
		String message = chatWriteMessageEditText.getText().toString();

		if (message.length() == 0) {
			return;
		}

		final App app = App.getApp();

		Request req = new Request("messages/support", Request.Method.POST) {
			@Override
			public void onSuccess(JSONObject result) {
				chatWriteMessageEditText.setText("");
				supportInstance.reloadMessages();
			}
		};

		req.addBodyParam("message", message);

		req.process(app);
	}


	@Override
	public void onBackPressed() {
		this.finish();
	}

}