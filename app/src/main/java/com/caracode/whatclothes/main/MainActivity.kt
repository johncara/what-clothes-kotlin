package com.caracode.whatclothes.main

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.caracode.whatclothes.R
import com.caracode.whatclothes.common.BaseActivity
import com.jakewharton.rxbinding2.view.RxView
import dagger.android.AndroidInjection
import io.reactivex.Observable
import javax.inject.Inject

class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {

    @BindView(R.id.rv_main)
    lateinit var rvMain: RecyclerView
    @BindView(R.id.fab)
    lateinit var fab: FloatingActionButton

    @Inject
    lateinit var mainPresenter: MainPresenter

    private var mainRecyclerAdapter: MainRecyclerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainRecyclerAdapter = MainRecyclerAdapter()
        rvMain.adapter = mainRecyclerAdapter
    }

    override fun providePresenter(): MainPresenter {
        return mainPresenter
    }

    override fun updateUi(mainViewModel: MainViewModel) {
        mainRecyclerAdapter!!.setDayModels(mainViewModel.days)
    }

    override fun onFabClick(): Observable<Any> {
        return RxView.clicks(fab)
    }

    override fun displayUnimplementedMessage() {
        val snackbar = Snackbar.make(fab, R.string.edit_temperatures_coming_soon, Snackbar.LENGTH_LONG)
        snackbar.setAction(R.string.dismiss) { v -> snackbar.dismiss() }.show()
    }
}
