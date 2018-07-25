package user.com.stopthefakes.adaper;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseRecyclerAdapter<T, V extends RecyclerView.ViewHolder>
	extends RecyclerView.Adapter<V> implements RecyclerViewAdapter<T> {

	protected List<T> items = new ArrayList();


	public void addAll(List<T> items) {
		this.items.addAll(items);
		notifyDataSetChanged();
	}


	@Override
	public void replaceAll(List<T> items) {
		if (items == null) {
			return;
		}
		this.items = items;
		notifyDataSetChanged();
	}


	@Override
	public T getItem(int position) {
		return items.get(position);
	}


	@Override
	public List<T> getItems() {
		return items;
	}

}