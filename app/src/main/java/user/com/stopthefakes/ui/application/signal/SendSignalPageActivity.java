package user.com.stopthefakes.ui.application.signal;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import user.com.stopthefakes.App;
import user.com.stopthefakes.BaseActivity;
import user.com.stopthefakes.R;
import user.com.stopthefakes.SettingsActivity;
import user.com.stopthefakes.entity.DbApplication;
import user.com.stopthefakes.ui.application.applications.InWorkApplicationActivity;
import user.com.stopthefakes.ui.application.list.ApplicationsAdapter;
import user.com.stopthefakes.ui.application.list.ApplicationsListActivity;
import user.com.stopthefakes.ui.gallery.GalleryMainActivity;


public class SendSignalPageActivity extends BaseActivity implements ApplicationsAdapter.ApplicationActionListener {

    @BindView(R.id.recyclerAppsSendSignal)
    RecyclerView recyclerAppsSendSignal;

    private ApplicationsAdapter applicationsAdapter;

    public static Intent newInstance(Context context) {
        return new Intent(context, SendSignalPageActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_signal_page);
        setUnbinder(ButterKnife.bind(this));

        initRecycler();
        initAdapter();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        applicationsAdapter.replaceAll(App.getApp().getApplicationList());
    }

    private void initRecycler() {
        recyclerAppsSendSignal.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerAppsSendSignal.setLayoutManager(llm);
    }

    private void initAdapter() {
        applicationsAdapter = new ApplicationsAdapter();
        applicationsAdapter.setOnApplicationActionListener(this);
        recyclerAppsSendSignal.setAdapter(applicationsAdapter);
    }

    @Override
    public void onSendSignalClicked(DbApplication dbApplication) {
        int id = dbApplication.getId().intValue();
        Intent intent = new Intent(this, GalleryMainActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

    @Override
    public void onApplicationClicked(DbApplication dbApplication, int pos) {
        startActivity(InWorkApplicationActivity.newInstance(this).putExtra("id", pos));

    }

    @Override
    public void onTakingInWorkClicked(DbApplication dbApplication) {

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
