package user.com.stopthefakes.ui.signal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;
import user.com.stopthefakes.ui.application.signal.SendSignalPageActivity;

public class SignalAcceptedActivity extends BaseActivity {


    public static Intent newInstance(Context context) {
        return new Intent(context, SignalAcceptedActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal_accepted);
        setUnbinder(ButterKnife.bind(this));

    }

    @OnClick(R.id.closeSignalAcceptedTextView)
    public void closeSignalAcceptedActivity() {
        startActivity(ApplicationsListActivity.newInstance(this));
    }

    @OnClick(R.id.goToMainScreenButton)
    public void openStartPage() {
        startActivity(ApplicationsListActivity.newInstance(this));
    }

    @OnClick(R.id.sendSignalNavigationButton)
    public void openSignals(){
        startActivity(SendSignalPageActivity.newInstance(this));
    }

}
