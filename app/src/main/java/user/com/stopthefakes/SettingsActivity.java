package user.com.stopthefakes;

import android.content.Intent;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.ui.application.all.AllSignalsActivity;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;
import user.com.stopthefakes.ui.application.signal.SendSignalPageActivity;
import user.com.stopthefakes.ui.chat.ChatActivity;
import user.com.stopthefakes.ui.history.FinanceHistoryActivity;

public class SettingsActivity extends BaseActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setUnbinder(ButterKnife.bind(this));
    }


    @OnClick(R.id.settings)
    protected void onClickSetting(){
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @OnClick(R.id.balance)
    protected void onClickBalance(){
        startActivity(new Intent(this, FinanceHistoryActivity.class));
    }

    @OnClick(R.id.mySignal)
    protected void onClickSignal(){
        startActivity(new Intent(this, AllSignalsActivity.class));
    }

    @OnClick(R.id.messages)
    protected void onClickMessages(){
        startActivity(new Intent(this, ChatActivity.class));

    }

    @OnClick(R.id.exit)
    protected void onClickExit(){
        startActivity(new Intent(this, AuthorizationActivity.class));    }

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
}
