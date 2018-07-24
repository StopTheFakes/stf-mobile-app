package user.com.stopthefakes;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
			RequestQueue queue = Volley.newRequestQueue(this);

			StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.api_base_url) + "login",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						try {
							JSONObject result = new JSONObject(response);
							try {
								String token = result.getString("token");
								SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
								SharedPreferences.Editor editor = sharedPref.edit();
								editor.putString("token", token);
								editor.apply();
								goToList();
							} catch (JSONException e) {
								Toast.makeText(getApplicationContext(), getString(R.string.api_err_server_err), Toast.LENGTH_LONG).show();
							}
						} catch (JSONException e) {
							Toast.makeText(getApplicationContext(), getString(R.string.api_err_server_err), Toast.LENGTH_LONG).show();
						}
					}
				}, new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError error) {
					if (error instanceof AuthFailureError) {
						Toast.makeText(getApplicationContext(), getString(R.string.api_err_incorrect_cred), Toast.LENGTH_LONG).show();
					} else if (error instanceof NoConnectionError) {
						Toast.makeText(getApplicationContext(), getString(R.string.api_err_conn_lost), Toast.LENGTH_LONG).show();
					} else {
						Toast.makeText(getApplicationContext(), getString(R.string.api_err_server_err), Toast.LENGTH_LONG).show();
					}
				}
			}) {
				@Override
				protected Map<String, String> getParams() {
					Map<String, String> params = new HashMap<>();
					params.put("email", emailEditText.getText().toString());
					params.put("password", passwordEditText.getText().toString());
					return params;
				}
			};

			queue.add(stringRequest);
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