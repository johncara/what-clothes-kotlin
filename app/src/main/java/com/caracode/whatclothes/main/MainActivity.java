package com.caracode.whatclothes.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;

import com.caracode.whatclothes.R;
import com.caracode.whatclothes.common.BaseActivity;
import com.jakewharton.rxbinding2.view.RxView;

import javax.inject.Inject;

import butterknife.BindView;
import dagger.android.AndroidInjection;
import io.reactivex.Observable;

public class MainActivity extends BaseActivity<MainPresenter, MainView> implements MainView {

    @BindView(R.id.rv_main)
    RecyclerView rvMain;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Inject
    MainPresenter mainPresenter;

    private MainRecyclerAdapter mainRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainRecyclerAdapter = new MainRecyclerAdapter();
        rvMain.setAdapter(mainRecyclerAdapter);
    }

    @NonNull
    @Override
    public MainPresenter providePresenter() {
        return mainPresenter;
    }

    @Override
    public void updateUi(MainViewModel mainViewModel) {
        mainRecyclerAdapter.setDayModels(mainViewModel.getDays());
    }

    @Override
    public Observable<Object> onFabClick() {
        return RxView.clicks(fab);
    }

    @Override
    public void displayUnimplementedMessage() {
        Snackbar snackbar = Snackbar.make(fab, R.string.edit_temperatures_coming_soon, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.dismiss, v -> snackbar.dismiss()).show();
    }
}
