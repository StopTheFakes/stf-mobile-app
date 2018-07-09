package user.com.stopthefakes.ui.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;

public class ChatActivity extends BaseActivity {

    @BindView(R.id.chatContainer)
    RelativeLayout chatContainer;
    @BindView(R.id.supportTextView)
    TextView supportTextView;
    @BindView(R.id.botTextView)
    TextView botTextView;
    @BindView(R.id.chatWriteMessageEditText)
    TextView chatWriteMessageEditText;

    boolean botChat = false;

    public static Intent newInstance(Context context) {
        return new Intent(context, ChatActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        setUnbinder(ButterKnife.bind(this));

        setFragment(botChat);
    }

    private void setFragment(boolean botChat) {
        if (!botChat) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.chatContainer, SupportFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
            chatWriteMessageEditText.setEnabled(true);
            chatWriteMessageEditText.setHint("Enter your message here");
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.chatContainer, BotFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
            chatWriteMessageEditText.setEnabled(false);
            chatWriteMessageEditText.setHint("");

        }
    }

    @OnClick(R.id.supportTextView)
    protected void clickSupportChat() {
        supportTextView.setBackgroundResource(R.drawable.rounded_text_view_left);
        botTextView.setBackgroundResource(R.color.transparent);
        supportTextView.setTextColor(getResources().getColor(R.color.textColorSecondary));
        botTextView.setTextColor(getResources().getColor(R.color.white));
        botChat = false;
        setFragment(botChat);
    }

    @OnClick(R.id.botTextView)
    protected void clickBotChat() {
        supportTextView.setBackgroundResource(R.color.transparent);
        botTextView.setBackgroundResource(R.drawable.rounded_text_view_right);
        supportTextView.setTextColor(getResources().getColor(R.color.white));
        botTextView.setTextColor(getResources().getColor(R.color.textColorSecondary));
        botChat = true;
        setFragment(botChat);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }
}
