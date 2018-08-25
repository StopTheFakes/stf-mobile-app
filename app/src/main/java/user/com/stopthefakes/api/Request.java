package user.com.stopthefakes.api;


import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import user.com.stopthefakes.App;
import user.com.stopthefakes.R;


public class Request {

	public interface Method {
		int GET = 0;
		int POST = 1;
	}

	private Boolean useAuth;
	private String url;
	private int method;
	private App app;
	private Map<String, String> bodyParams = new HashMap<>();


	protected Request(String url, int method, boolean useAuth) {
		this.url = url;
		this.method = method;
		this.useAuth = useAuth;
	}


	protected Request(String url, int method) {
		this.url = url;
		this.method = method;
		this.useAuth = true;
	}


	public void addBodyParam(String key, String value) {
		bodyParams.put(key, value);
	}


	public void process(final App app) {
		this.app = app;
		RequestQueue queue = Volley.newRequestQueue(app);
		StringRequest req = new StringRequest(method, app.getString(R.string.api_base_url) + url,
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					try {
						JSONObject result = new JSONObject(response);
						onSuccess(result);
					} catch (JSONException e) {
						onError(e);
					}
				}
			},
			new Response.ErrorListener() {
				@Override
				public void onErrorResponse(VolleyError e) {
					onConnectionError(e);
				}
			}
		) {
			@Override
			public Map<String, String> getHeaders() {
				Map<String, String> headers = new HashMap<>();
				if (useAuth) {
					headers.put("Authorization","Bearer " + app.getToken());
				}
				return headers;
			}

			@Override
			protected Map<String, String> getParams() {
				return bodyParams;
			}
		};
		queue.add(req);
	}


	public void onSuccess(JSONObject result) {}


	public void onError(Exception e) {
		Log.e("Request", e.getMessage(), e);
	}


	public void onConnectionError(Exception e) {
		Log.e("onConnectionError", e.getMessage(), e);
		if (e instanceof NoConnectionError) {
			Toast.makeText(app.getApplicationContext(), app.getString(R.string.api_err_conn_lost), Toast.LENGTH_LONG).show();
		} else if (e instanceof AuthFailureError) {
			app.logout();
		} else {
			Toast.makeText(app.getApplicationContext(), app.getString(R.string.api_err_server_err), Toast.LENGTH_LONG).show();
		}
	}

}
