package user.com.stopthefakes;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;

import org.json.JSONObject;
import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.api.Request;
import user.com.stopthefakes.api.RequestCallback;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;
import user.com.stopthefakes.utils.FieldsValidator;

public class AuthorizationActivity extends BaseActivity {

	@BindView(R.id.emailEditText)
	EditText emailEditText;
	@BindView(R.id.passwordEditText)
	EditText passwordEditText;

	private FieldsValidator fieldsValidator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_authorization);
		setUnbinder(ButterKnife.bind(this));
		fieldsValidator = new FieldsValidator(this);
		if (ActivityCompat.checkSelfPermission(AuthorizationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(AuthorizationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(AuthorizationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
				|| ActivityCompat.checkSelfPermission(AuthorizationActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(AuthorizationActivity.this, new String[]{
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				Manifest.permission.READ_EXTERNAL_STORAGE,
				Manifest.permission.CAMERA,
				Manifest.permission.RECORD_AUDIO,
				Manifest.permission.INTERNET
			}, 100);
		}
	}


	@OnClick({R.id.signUpButton, R.id.signInButton})
	public void signIn() {
		FieldsValidator.errorWatcher(emailEditText, fieldsValidator.isEmailValid(emailEditText.getText().toString()));
		FieldsValidator.errorWatcher(passwordEditText, fieldsValidator.isPasswordValid(passwordEditText.getText().toString()));

		if (!hasErrors()) {
			final App app = App.getApp();
			Request req = new Request("login", Request.Method.POST, false) {
				@Override
				public void onSuccess(JSONObject result) {
					try {
						String token = result.getString("token");
						app.setToken(token);
						app.reloadCurrentUser(new RequestCallback() {
							@Override
							public void onSuccess() {
								goToList();
							}

							@Override
							public void onError(Exception e) {
								Log.e("reloadCurrentUser", e.getMessage(), e);
								Toast.makeText(app.getApplicationContext(), app.getString(R.string.api_err_server_err), Toast.LENGTH_LONG).show();
								app.logout();
							}
						});
					} catch (JSONException e) {
						Log.e("signIn", e.getMessage(), e);
						Toast.makeText(getApplicationContext(), getString(R.string.api_err_server_err), Toast.LENGTH_LONG).show();
					}
				}

				@Override
				public void onConnectionError(Exception e) {
					if (e instanceof AuthFailureError) {
						Log.e("signIn", e.getMessage(), e);
						Toast.makeText(getApplicationContext(), getString(R.string.api_err_incorrect_cred), Toast.LENGTH_LONG).show();
					} else {
						super.onConnectionError(e);
					}
				}
			};
			req.addBodyParam("email", emailEditText.getText().toString());
			req.addBodyParam("password", passwordEditText.getText().toString());
			req.process(app);
		}
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		for (int grantResult : grantResults) {
			if (grantResult != PackageManager.PERMISSION_GRANTED) {
				if (ActivityCompat.checkSelfPermission(AuthorizationActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
					|| ActivityCompat.checkSelfPermission(AuthorizationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
					|| ActivityCompat.checkSelfPermission(AuthorizationActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
					|| ActivityCompat.checkSelfPermission(AuthorizationActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(AuthorizationActivity.this, new String[]{
						Manifest.permission.WRITE_EXTERNAL_STORAGE,
						Manifest.permission.READ_EXTERNAL_STORAGE,
						Manifest.permission.CAMERA,
						Manifest.permission.RECORD_AUDIO,
						Manifest.permission.INTERNET
					}, 100);
				}
				return;
			}
		}
	}


	private boolean hasErrors() {
		return emailEditText.getError() != null || passwordEditText.getError() != null;
	}


	private void goToList() {
		startActivity(ApplicationsListActivity.newInstance(this));
	}

}