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


public class BotFragment extends Fragment {

	private Unbinder unbinder;

	private View view;


	public static BotFragment newInstance() {
		return new BotFragment();
	}


	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_bot_chat, container, false);
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


	private void reloadMessages() {
		final App app = App.getApp();

		Request req = new Request("messages/bot", Request.Method.GET) {
			@Override
			public void onSuccess(JSONObject result) {
				try {
					LinearLayout outer = view.findViewById(R.id.botMessagesOuterView);
					outer.removeAllViews();

					JSONArray data = result.getJSONArray("data");

					for (int i = 0; i < data.length(); ++i) {
						TextView row = (TextView) getLayoutInflater().inflate(R.layout.list_item_message_bot_row, outer, false);
						row.setText(data.getJSONObject(i).getString("message"));
						outer.addView(row);
					}

				} catch (Exception e) {
					Log.e("profile.cities", e.getMessage(), e);
				}
			}
		};

		req.process(app);
	}

}