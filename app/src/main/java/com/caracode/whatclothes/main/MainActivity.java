package com.caracode.whatclothes.main;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.caracode.whatclothes.R;

import butterknife.BindView;
import common.BaseActivity;

public class MainActivity extends BaseActivity<MainPresenter, MainView> implements MainView {

    @BindView(R.id.text_view)
    TextView tvHelloWorld;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @NonNull
    @Override
    public MainPresenter providePresenter() {
        return new MainPresenter();
    }

    @Override
    public void showText(String text) {
        tvHelloWorld.setText(text);
    }
}
