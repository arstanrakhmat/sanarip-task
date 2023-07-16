package com.example.sdtask.presenter

import com.example.sdtask.application.MyApplication
import com.example.sdtask.model.PhotoDbHelper
import com.example.sdtask.view.MainActivityView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import moxy.InjectViewState
import moxy.MvpPresenter
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(OneExecutionStateStrategy::class)
class MainActivityPresenter : MvpPresenter<MainActivityView>() {
    private val dbHelper: PhotoDbHelper = MyApplication.databaseHelper

    fun loadPhotos() {
        viewState.showProgressbar()
        viewState.hideAddButton()

        GlobalScope.launch(Dispatchers.IO) {
            delay(5000)
            val photos = dbHelper.getAllPhotos()

            withContext(Dispatchers.Main) {
                viewState.hideProgressBar()
                viewState.showAddButton()
                viewState.showPhotos(photos)
            }
        }
    }
}