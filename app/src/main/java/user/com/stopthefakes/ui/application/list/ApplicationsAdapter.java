package user.com.stopthefakes.ui.application.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.R;
import user.com.stopthefakes.adaper.BaseRecyclerAdapter;
import user.com.stopthefakes.entity.DbApplication;


public class ApplicationsAdapter extends BaseRecyclerAdapter<DbApplication, RecyclerView.ViewHolder> {

	private ApplicationActionListener applicationActionListener;
	private Context context;


	public interface ApplicationActionListener {
		void onSendSignalClicked(DbApplication dbApplication);

		void onApplicationClicked(DbApplication dbApplication, int pos);

		void onTakingInWorkClicked(DbApplication dbApplication);
	}


	public void setOnApplicationActionListener(ApplicationActionListener applicationActionListener) {
		this.applicationActionListener = applicationActionListener;
	}


	@Override
	public int getItemViewType(int position) {
		return getItem(position).getType();
	}


	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		context = parent.getContext();
		if (DbApplication.isTypeTaken(viewType) || DbApplication.isTypeExpired(viewType)) {
			return new InWorkApplicationViewHolder(LayoutInflater.from(context)
				.inflate(R.layout.list_item_application_in_work, parent, false));
		}
		return new ApplicationViewHolder(LayoutInflater.from(context)
			.inflate(R.layout.list_item_application, parent, false));
	}


	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
		DbApplication app = getItem(position);

		if (app.isWaiting()) {
			((ApplicationViewHolder) holder).bindView(getItem(position));
		} else if (app.isTaken() || app.isExpired()) {
			((InWorkApplicationViewHolder) holder).bindView(getItem(position));
		}
	}


	@Override
	public int getItemCount() {
		return getItems().size();
	}


	public class InWorkApplicationViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.inWorkApplicationItemCityCountryTextView)
		TextView inWorkApplicationItemCityCountryTextView;
		@BindView(R.id.inWorkApplicationItemDateTextView)
		TextView inWorkApplicationItemDateTextView;
		@BindView(R.id.inWorkHeaderItemTextView)
		TextView inWorkTitleItemTextView;
		@BindView(R.id.inWorkTimerItemTextView)
		TextView inWorkTimerItemTextView;


		InWorkApplicationViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}


		void bindView(DbApplication app) {
			inWorkApplicationItemCityCountryTextView
				.setText(String.format(context.getString(R.string.format_city_country), app.getCountry(), getCitiesInString(app)));
			inWorkApplicationItemDateTextView.setText(app.getDate());
			inWorkTimerItemTextView.setText(app.getTimeLeft());
			inWorkTitleItemTextView.setText(app.getHeader());
		}


		@OnClick(R.id.inWorkLookAtApplicationButton)
		public void showWorkingDetails() {
			int pos = getAdapterPosition();
			if (pos != RecyclerView.NO_POSITION && applicationActionListener != null) {
				applicationActionListener.onApplicationClicked(items.get(pos), pos);
			}
		}


		@OnClick(R.id.sendSignalButton)
		public void sendSignal() {
			int pos = getAdapterPosition();
			if (pos != RecyclerView.NO_POSITION && applicationActionListener != null) {
				applicationActionListener.onSendSignalClicked(getItem(pos));
			}
		}
	}


	public class ApplicationViewHolder extends RecyclerView.ViewHolder {

		@BindView(R.id.applicationItemCityTextView)
		TextView applicationItemCityTextView;
		@BindView(R.id.applicationItemDateTextView)
		TextView applicationItemDateTextView;
		@BindView(R.id.titleItemTextView)
		TextView titleItemTextView;
		@BindView(R.id.descriptionItemTextView)
		TextView descriptionItemTextView;


		ApplicationViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);
		}


		void bindView(DbApplication dbApplication) {
			applicationItemCityTextView.setText(String.format(context.getString(R.string.format_city_country),
				dbApplication.getCountry(), getCitiesInString(dbApplication)));
			titleItemTextView.setText(dbApplication.getHeader());
			applicationItemDateTextView.setText(dbApplication.getDate());
			descriptionItemTextView.setText(dbApplication.getShordDescription());
		}


		@OnClick(R.id.lookAtApplicationButton)
		public void showDetails() {
			int pos = getAdapterPosition();
			if (pos != RecyclerView.NO_POSITION && applicationActionListener != null) {
				applicationActionListener.onApplicationClicked(getItem(pos), pos);
			}
		}


		@OnClick(R.id.takeInWorkButton)
		public void takeInWork() {
			int pos = getAdapterPosition();
			if (pos != RecyclerView.NO_POSITION && applicationActionListener != null) {
				DbApplication dbApplication = getItem(pos);
				//dbApplication.setType(1);
				applicationActionListener.onTakingInWorkClicked(dbApplication);
				//notifyDataSetChanged();
			}
		}
	}


	private String getCitiesInString(DbApplication dbApplication) {
		String s;
		if(dbApplication.getCitiesList().size() > 1){
			s = dbApplication.getCitiesList().size() + " cities";
		} else {
			s = dbApplication.getCitiesList().get(0);
		}
		return s;
	}


	public void sort(boolean isNewest) {
		if (isNewest) {
			Collections.sort(items, new Comparator<DbApplication>() {
				@Override
				public int compare(DbApplication dbApplication, DbApplication t1) {
					return t1.getDate().compareTo(dbApplication.getDate());
				}
			});
		} else {
			Collections.sort(items, new Comparator<DbApplication>() {
				@Override
				public int compare(DbApplication dbApplication, DbApplication t1) {
					return dbApplication.getDate().compareTo(t1.getDate());
				}
			});
		}
		notifyDataSetChanged();
	}

}