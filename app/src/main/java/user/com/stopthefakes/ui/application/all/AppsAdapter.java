package user.com.stopthefakes.ui.application.all;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import user.com.stopthefakes.App;
import user.com.stopthefakes.R;
import user.com.stopthefakes.adaper.BaseRecyclerAdapter;
import user.com.stopthefakes.entity.DbApplication;


public class AppsAdapter extends BaseRecyclerAdapter<DbApplication, RecyclerView.ViewHolder> {

	private Context context;


	@Override
	public int getItemViewType(int position) {
		return getItem(position).getAllType();
	}


	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		context = parent.getContext();
		if (viewType == 1) {
			return new AppsAdapter.AcceptedViewHolder(LayoutInflater.from(context)
				.inflate(R.layout.list_item_accepted_app, parent, false));
		}
		if (viewType == 2) {
			return new AppsAdapter.CancelledViewHolder(LayoutInflater.from(context)
				.inflate(R.layout.list_item_cancelled_app, parent, false));
		}
		return new AppsAdapter.OnWatchingViewHolder(LayoutInflater.from(context)
			.inflate(R.layout.list_item_on_watching_app, parent, false));
	}


	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		switch (getItem(position).getAllType()) {
			case 0:
				((AppsAdapter.OnWatchingViewHolder) holder).bindView(getItem(position));
				break;

			case 1:
				((AppsAdapter.AcceptedViewHolder) holder).bindView(getItem(position));
				break;

			case 2:
				((AppsAdapter.CancelledViewHolder) holder).bindView(getItem(position));
				break;
		}
	}


	@Override
	public int getItemCount() {
		return getItems().size();
	}


	public class AcceptedViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.appIdTextView)
		TextView appIdTextView;

		@BindView(R.id.titleItemTextView)
		TextView titleItemTextView;

		@BindView(R.id.descriptionItemTextView)
		TextView descriptionItemTextView;

		@BindView(R.id.dateTextView)
		TextView dateTextView;


		AcceptedViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}


		void bindView(DbApplication dbApp) {
			appIdTextView.setText(String.format(App.getApp().getString(R.string.alert_id), dbApp.getId()));
			titleItemTextView.setText(dbApp.getHeader());
			descriptionItemTextView.setText(dbApp.getShordDescription());
			DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
			try {
				Date dateParsed = sourceFormat.parse(dbApp.getExpiresString());
				dateTextView.setText(dateFormat.format(dateParsed));
			} catch (Exception e) {
				dateTextView.setText("--.--.--");
				Log.e("Parse date", e.getMessage(), e);
			}
		}
	}


	public class CancelledViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.appIdTextView)
		TextView appIdTextView;

		@BindView(R.id.titleItemTextView)
		TextView titleItemTextView;

		@BindView(R.id.descriptionItemTextView)
		TextView descriptionItemTextView;

		@BindView(R.id.dateTextView)
		TextView dateTextView;


		CancelledViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}


		void bindView(DbApplication dbApp) {
			appIdTextView.setText(String.format(App.getApp().getString(R.string.alert_id), dbApp.getId()));
			titleItemTextView.setText(dbApp.getHeader());
			descriptionItemTextView.setText(dbApp.getShordDescription());
			DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
			try {
				Date dateParsed = sourceFormat.parse(dbApp.getExpiresString());
				dateTextView.setText(dateFormat.format(dateParsed));
			} catch (Exception e) {
				dateTextView.setText("--.--.--");
				Log.e("Parse date", e.getMessage(), e);
			}
		}
	}


	public class OnWatchingViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.appIdTextView)
		TextView appIdTextView;

		@BindView(R.id.titleItemTextView)
		TextView titleItemTextView;

		@BindView(R.id.descriptionItemTextView)
		TextView descriptionItemTextView;

		@BindView(R.id.dateTextView)
		TextView dateTextView;

		@BindView(R.id.timerTextView)
		TextView timerTextView;


		OnWatchingViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}


		void bindView(DbApplication dbApp) {
			appIdTextView.setText(String.format(App.getApp().getString(R.string.alert_id), dbApp.getId()));
			titleItemTextView.setText(dbApp.getHeader());
			descriptionItemTextView.setText(dbApp.getShordDescription());
			DateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy", Locale.ENGLISH);
			try {
				Date dateParsed = sourceFormat.parse(dbApp.getExpiresString());
				dateTextView.setText(dateFormat.format(dateParsed));
			} catch (Exception e) {
				dateTextView.setText("--.--.--");
				Log.e("Parse date", e.getMessage(), e);
			}
			timerTextView.setText(dbApp.getTimeLeftFromExpiresString());
		}
	}

}