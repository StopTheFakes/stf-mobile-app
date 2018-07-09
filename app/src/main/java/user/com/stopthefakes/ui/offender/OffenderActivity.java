package user.com.stopthefakes.ui.offender;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;
import user.com.stopthefakes.SettingsActivity;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;
import user.com.stopthefakes.ui.application.signal.SendSignalPageActivity;
import user.com.stopthefakes.ui.signal.SignalAcceptedActivity;
import user.com.stopthefakes.utils.FieldsValidator;



public class OffenderActivity extends BaseActivity {

    @BindView(R.id.websiteLayout)
    LinearLayout websiteLayout;
    @BindView(R.id.emailEditText)
    EditText emailEditText;
    @BindView(R.id.additionalInfoEditText)
    EditText additionalInfoEditText;
    @BindView(R.id.sendSignalButton)
    Button sendSignalButton;

    private FieldsValidator validator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offender);
        ButterKnife.bind(this);

        validator = new FieldsValidator(this);
        websiteLayout.setVisibility(View.GONE);
        sendSignalButton.setEnabled(false);
        setEmailTextWatcher();
        setInfoTextWatcher();
    }

    private void setEmailTextWatcher() {

        emailEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                showWebsiteLayout(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                showWebsiteLayout(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0)
                    showWebsiteLayout(s.toString());
            }
        });
    }

    private void setInfoTextWatcher() {

        additionalInfoEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                setButtonColor(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
                setButtonColor(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0)
                    setButtonColor(s.toString());
            }
        });
    }

    private void setButtonColor(String s) {
        if (s.length() > 30) {
            sendSignalButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_send_signal_button));
            sendSignalButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            sendSignalButton.setText(getString(R.string.send_signal_button));
            sendSignalButton.setEnabled(true);
        } else {
            sendSignalButton.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.rounded_send_signal_transparent));
            sendSignalButton.setText(getString(R.string.send_signal_button));
            sendSignalButton.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.border_button));
            sendSignalButton.setEnabled(false);
        }
    }

    private void showWebsiteLayout(String s) {
        if (validator.isEmailValidBool(s)) {
            websiteLayout.setVisibility(View.VISIBLE);
        } else {
            websiteLayout.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.sendSignalButton)
    public void onSendSignalClicked() {
        if (sendSignalButton.isEnabled()) {
            startActivity(SignalAcceptedActivity.newInstance(this));
        }
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


    @OnClick(R.id.returnBackTextView)
    public void returnBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
