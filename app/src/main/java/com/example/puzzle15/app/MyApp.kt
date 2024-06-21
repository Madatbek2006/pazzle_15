package com.example.puzzle15.app

import android.app.Application
import com.example.puzzle15.data.sourse.MySharImpl

class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        MySharImpl.init(this)

    }
}