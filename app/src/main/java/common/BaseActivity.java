package common;
import android.support.annotation.LayoutRes;

import net.grandcentrix.thirtyinch.TiActivity;
import net.grandcentrix.thirtyinch.TiPresenter;
import net.grandcentrix.thirtyinch.TiView;

import butterknife.ButterKnife;

public abstract class BaseActivity<P extends TiPresenter<V>, V extends TiView> extends TiActivity<P, V> {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}