package user.com.stopthefakes.adaper;


import java.util.List;

public interface RecyclerViewAdapter<T> {

    void addAll(List<T> items);

    void replaceAll(List<T> items);

    T getItem(int position);

    List<T> getItems();
}
