package user.com.stopthefakes.ui.application.all;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.App;
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;
import user.com.stopthefakes.SettingsActivity;
import user.com.stopthefakes.entity.DbApplication;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;
import user.com.stopthefakes.ui.application.signal.SendSignalPageActivity;

public class AllSignalsActivity extends BaseActivity {

    @BindView(R.id.appsRecyclerView)
    RecyclerView appsRecyclerView;

    private AppsAdapter appsAdapter;

    public static Intent newInstance(Context context) {
        return new Intent(context, AllSignalsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_signals);
        setUnbinder(ButterKnife.bind(this));

        initRecyclerView();
        initAdapter();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        onAllClicked();
    }

    private void initRecyclerView() {
        appsRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        appsRecyclerView.setLayoutManager(llm);
    }

    private void initAdapter() {
        appsAdapter = new AppsAdapter();
        appsRecyclerView.setAdapter(appsAdapter);
    }

    private List<DbApplication> getDbApps() {


        return App.getApp().getApplicationList();
    }

    // type: 0 - onWatching, 1 - accepted, 2 - cancelled

    private List<DbApplication> getAppsByType(int type) {
        List<DbApplication> allAppsList = getDbApps();
        List<DbApplication> sortedDbAppList = new ArrayList<>();
        for (DbApplication dbApp : allAppsList) {
            if (dbApp.getAllType() == type) {
                sortedDbAppList.add(dbApp);
            }
        }
        return sortedDbAppList;
    }

    @OnClick(R.id.acceptedTextView)
    public void onAcceptedClicked() {
        if (appsAdapter == null) {
            appsAdapter = new AppsAdapter();
        }
        appsAdapter.replaceAll(getAppsByType(1));
    }

    @OnClick(R.id.cancelledTextView)
    public void onCancelledClicked() {
        if (appsAdapter == null) {
            appsAdapter = new AppsAdapter();
        }
        appsAdapter.replaceAll(getAppsByType(2));
    }

    @OnClick(R.id.onWatchingTextView)
    public void onWatchingClicked() {
        if (appsAdapter == null) {
            appsAdapter = new AppsAdapter();
        }
        appsAdapter.replaceAll(getAppsByType(0));
    }

    @OnClick(R.id.allTextView)
    public void onAllClicked() {
        if (appsAdapter == null) {
            appsAdapter = new AppsAdapter();
        }
        appsAdapter.replaceAll(getDbApps());
    }

    @OnClick(R.id.goToMainScreenButton)
    public void openStartPage() {
        startActivity(ApplicationsListActivity.newInstance(this));
    }

    @OnClick(R.id.sendSignalNavigationButton)
    public void openSignals(){
        startActivity(SendSignalPageActivity.newInstance(this));
    }

    @OnClick(R.id.goToMenuPageButton)
    protected void openSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }
}
