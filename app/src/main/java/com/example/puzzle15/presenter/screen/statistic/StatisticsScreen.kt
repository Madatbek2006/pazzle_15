package com.example.puzzle15.presenter.screen.statistic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.puzzle15.R
import com.example.puzzle15.data.sourse.MyShar
import com.example.puzzle15.data.sourse.MySharImpl
import com.example.puzzle15.databinding.ScreenStatisticBinding
import com.example.uzummarketclient.utils.durationConverter
import kotlin.math.abs

class StatisticsScreen:Fragment(R.layout.screen_statistic) {
    private val binding by viewBinding(ScreenStatisticBinding::bind)
    private val myShar:MyShar=MySharImpl.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            home.setOnClickListener {
                findNavController().navigateUp()
            }
           val topMove1=if (myShar.getTopMove().first==0)"-  " else myShar.getTopMove().first.toString()
           val topMove2=if (myShar.getTopMove().second==0)"-  " else myShar.getTopMove().second.toString()
           val topMove3=if (myShar.getTopMove().third==0)"-  " else myShar.getTopMove().third.toString()
            val topTime1=if (myShar.getTopTime().first==-1L)"-    " else abs(myShar.getTopTime().first).durationConverter()
            val topTime2=if (myShar.getTopTime().second==-1L)"-    " else abs(myShar.getTopTime().second).durationConverter()
            val topTime3=if (myShar.getTopTime().third==-1L)"-    " else abs(myShar.getTopTime().third).durationConverter()
            top1.text="moves: ${topMove1}   time: ${topTime1}"
            top2.text="moves: ${topMove2}   time: ${topTime2}"
            top3.text="moves: ${topMove3}   time: ${topTime3}"
        }

    }
}