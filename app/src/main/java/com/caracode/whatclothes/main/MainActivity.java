package com.caracode.whatclothes.main;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.caracode.whatclothes.R;
import com.caracode.whatclothes.common.BaseActivity;
import com.jakewharton.rxbinding2.view.RxView;

import butterknife.BindView;
import io.reactivex.Observable;

public class MainActivity extends BaseActivity<MainPresenter, MainView> implements MainView {

    @BindView(R.id.text_view)
    TextView tvHelloWorld;
    @BindView(R.id.button)
    Button bChange;

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
    public Observable<Object> onButtonPress() {
        return RxView.clicks(bChange);
    }

    @Override
    public void showText(String text) {
        tvHelloWorld.setText(text);
    }
}
