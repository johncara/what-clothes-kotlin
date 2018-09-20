package com.caracode.whatclothes.main

import android.os.Bundle
import android.support.design.widget.Snackbar
import com.caracode.whatclothes.R
import com.caracode.whatclothes.common.BaseActivity
import com.caracode.whatclothes.common.recycler.GenericRecyclerAdapter
import com.jakewharton.rxbinding2.view.RxView
import dagger.android.AndroidInjection
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity<MainPresenter, MainView>(), MainView {

    @Inject
    lateinit var mainPresenter: MainPresenter

    @Inject
    lateinit var mainCustomAdapter: MainCustomAdapter

    lateinit var mainRecyclerAdapter: GenericRecyclerAdapter<DayModel, DayModel, MainRecyclerHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainRecyclerAdapter = GenericRecyclerAdapter(mainCustomAdapter, ::MainRecyclerHolder, R.layout.item_day)
        rv_main.adapter = mainRecyclerAdapter
    }

    override fun providePresenter(): MainPresenter {
        return mainPresenter
    }

    override fun updateUi(mainViewModel: MainViewModel) {
        mainRecyclerAdapter.setInputModels(mainViewModel.days)
    }

    override fun onFabClick(): Observable<Any> {
        return RxView.clicks(fab)
    }

    override fun displayUnimplementedMessage() {
        val snackbar = Snackbar.make(fab, R.string.edit_temperatures_coming_soon, Snackbar.LENGTH_LONG)
        snackbar.setAction(R.string.dismiss) { v -> snackbar.dismiss() }.show()
    }
}
