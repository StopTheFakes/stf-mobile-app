package user.com.stopthefakes.api;


import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Locale;
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
	private String urlPrefix;
	private int method;
	private App app;
	private Map<String, String> bodyParams = new HashMap<>();
	Map<String, String> bodyFiles = new HashMap<>();


	protected Request(String url, int method, boolean useAuth) {
		this.url = url;
		this.method = method;
		this.useAuth = useAuth;
		this.urlPrefix = "v1/";
	}


	protected Request(String url, int method) {
		this.url = url;
		this.method = method;
		this.useAuth = true;
		this.urlPrefix = "v1/";
	}


	public void addBodyParam(String key, String value) {
		bodyParams.put(key, value);
	}


	public void addFile(String key, String path) {
		bodyFiles.put(key, path);
	}


	public void process(final App app) {
		this.app = app;

		RequestQueue queue = Volley.newRequestQueue(app);

		String reqUrl = app.getString(R.string.api_base_url) + urlPrefix + url;

		Log.d("Request/process/url", reqUrl);

		StringRequest req = new StringRequest(method, reqUrl, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				Log.d("Request.resp.len", String.format(Locale.getDefault(), "%d", response.length()));
				Log.d("Request.resp", response);
				try {
					JSONObject result = new JSONObject(response);
					onSuccess(result);
				} catch (JSONException e) {
					Log.e("Request.parseError", e.getMessage(), e);
					onError(e);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError e) {
				onConnectionError(e);
			}
		}) {
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


	public void processMultipart(final App app) {
		this.app = app;

		RequestQueue queue = Volley.newRequestQueue(app);

		String reqUrl = app.getString(R.string.api_base_url) + urlPrefix + url;

		Log.d("Request/process/url", reqUrl);

		VolleyMultipartRequest req = new VolleyMultipartRequest(Request.Method.POST, reqUrl, new Response.Listener<NetworkResponse>() {
			@Override
			public void onResponse(NetworkResponse nResponse) {
				String response = new String(nResponse.data);
				Log.d("Request.resp.len", String.format(Locale.getDefault(), "%d", response.length()));
				Log.d("Request.resp", response);
				try {
					JSONObject result = new JSONObject(response);
					onSuccess(result);
				} catch (JSONException e) {
					Log.e("Request.parseError", e.getMessage(), e);
					onError(e);
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError e) {
				onConnectionError(e);
			}
		}) {
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

			@Override
			protected Map<String, DataPart> getByteData() {
				Map<String, DataPart> params = new HashMap<>();
				for (Map.Entry<String, String> entry : bodyFiles.entrySet()) {
					File file = new File(entry.getValue());
					if (file.exists()) {
						byte[] bytes = new byte[(int) file.length()];
						try {
							BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
							if (buf.read(bytes, 0, bytes.length) > 0) {
								params.put(entry.getKey(), new DataPart(file.getName(), bytes, "image/jpeg"));
							}
							buf.close();
						} catch (Exception e) {
							Log.e("Request.addFile", e.getMessage(), e);
						}
					}
				}

				return params;
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
			app.toast(R.string.api_err_conn_lost);
		} else if (e instanceof AuthFailureError) {
			app.logout();
		} else {
			app.toast(R.string.api_err_server_err);
		}
	}

}
