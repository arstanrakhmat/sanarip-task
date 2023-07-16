package com.example.sdtask.application

import android.app.Application
import com.example.sdtask.model.PhotoDbHelper

class MyApplication : Application() {
    companion object {
        lateinit var databaseHelper: PhotoDbHelper
    }

    override fun onCreate() {
        super.onCreate()
            databaseHelper = PhotoDbHelper(this)
    }
}