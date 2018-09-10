package user.com.stopthefakes.ui.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import user.com.stopthefakes.App;
import user.com.stopthefakes.R;
import user.com.stopthefakes.api.Request;


public class SupportFragment extends Fragment {

	private Unbinder unbinder;
	private View view;


	public static SupportFragment newInstance(){
		return new SupportFragment();
	}


	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_support_chat, container, false);
		setUnbinder(ButterKnife.bind(this, view));
		reloadMessages();
		return view;
	}


	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}


	@Override
	public void onDestroyView() {
		super.onDestroyView();
		if (unbinder != null) {
			unbinder.unbind();
			unbinder = null;
		}
	}


	private void setUnbinder(Unbinder unbinder) {
		this.unbinder = unbinder;
	}


	public void reloadMessages() {
		final App app = App.getApp();

		Request req = new Request("messages/support", Request.Method.GET) {
			@Override
			public void onSuccess(JSONObject result) {
				try {
					LinearLayout outer = view.findViewById(R.id.supportMessagesView);
					outer.removeAllViews();

					JSONArray data = result.getJSONArray("data");

					for (int i = 0; i < data.length(); ++i) {
						JSONObject item = data.getJSONObject(i);
						if (item.getInt("code_sender") == 1) {
							LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.list_item_message_support_doer_row, outer, false);
							((TextView) row.getChildAt(0)).setText(item.getString("message"));
							outer.addView(row);
							Log.i("Support messages", "Doer inserted");
						} else {
							LinearLayout row = (LinearLayout) getLayoutInflater().inflate(R.layout.list_item_message_support_support_row, outer, false);
							((TextView) row.getChildAt(1)).setText(item.getString("message"));
							outer.addView(row);
							Log.i("Support messages", "Support inserted");
						}
					}

				} catch (Exception e) {
					Log.e("profile.cities", e.getMessage(), e);
				}
			}
		};

		req.process(app);
	}

}