package com.example.puzzle15.presenter.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.puzzle15.R
import com.example.uzummarketclient.utils.statusBarTRANSPARENT

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        statusBarTRANSPARENT()
    }
}