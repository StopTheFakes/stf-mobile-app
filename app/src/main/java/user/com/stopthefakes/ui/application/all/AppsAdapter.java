package user.com.stopthefakes.ui.application.all;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import user.com.stopthefakes.R;
import user.com.stopthefakes.adaper.BaseRecyclerAdapter;
import user.com.stopthefakes.entity.DbApplication;

public class AppsAdapter extends BaseRecyclerAdapter<DbApplication, RecyclerView.ViewHolder> {

    private Context context;

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getAllType();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        switch (viewType) {
            case 0:
                return new AppsAdapter.OnWatchingViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.list_item_on_watching_app, parent, false));
            case 1:
                return new AppsAdapter.AcceptedViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.list_item_accepted_app, parent, false));
            case 2:
                return new AppsAdapter.CancelledViewHolder(LayoutInflater.from(context)
                        .inflate(R.layout.list_item_cancelled_app, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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
            default:
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

        public AcceptedViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(DbApplication dbApp) {
            titleItemTextView.setText(dbApp.getHeader());
            descriptionItemTextView.setText(dbApp.getShordDescription());
        }
    }

    public class CancelledViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.appIdTextView)
        TextView appIdTextView;
        @BindView(R.id.titleItemTextView)
        TextView titleItemTextView;
        @BindView(R.id.descriptionItemTextView)
        TextView descriptionItemTextView;

        public CancelledViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(DbApplication dbApp) {
            titleItemTextView.setText(dbApp.getHeader());
            descriptionItemTextView.setText(dbApp.getShordDescription());
        }
    }

    public class OnWatchingViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.appIdTextView)
        TextView appIdTextView;
        @BindView(R.id.titleItemTextView)
        TextView titleItemTextView;
        @BindView(R.id.descriptionItemTextView)
        TextView descriptionItemTextView;

        public OnWatchingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindView(DbApplication dbApp) {
            titleItemTextView.setText(dbApp.getHeader());
            descriptionItemTextView.setText(dbApp.getShordDescription());
        }
    }

}
