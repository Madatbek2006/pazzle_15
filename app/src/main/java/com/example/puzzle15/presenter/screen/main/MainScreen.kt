package com.example.puzzle15.presenter.screen.main

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.puzzle15.R
import com.example.puzzle15.databinding.ScreenMainBinding
import com.example.puzzle15.presenter.dialog.PauseDialog
import com.example.puzzle15.presenter.dialog.PauseObj
import com.example.puzzle15.presenter.dialog.WinDialog
import com.example.puzzle15.presenter.dialog.WinDialogOpj
import com.example.uzummarketclient.utils.myLog

class MainScreen : Fragment(R.layout.screen_main) {
    private val viewModel: MainVM by viewModels<MainVMImpl>()
    private val pause=PauseObj
    private val winDialog=WinDialogOpj
    private val buttons = ArrayList<AppCompatButton>()
    private val binding by viewBinding(ScreenMainBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        initButtons()
        initViewModel()
        viewModel.load()
        binding.apply {
            time.base=viewModel.time+SystemClock.elapsedRealtime()
            if (!viewModel.isPause){
                time.start()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initViewModel() =viewModel.apply{
        binding.apply {

            timeListener={

                time.base=it+SystemClock.elapsedRealtime()
                time.start()
            }
            load = { mat ->
                "${mat.joinToString()}".myLog()
                for (i in buttons.indices) {
                    val x = i / 4
                    val y = i % 4
                    if (mat[x][y] == 0) {
                        buttons[i].visibility = View.INVISIBLE
                        buttons[i].text = ""
                    } else {
                        buttons[i].visibility = View.VISIBLE
                        buttons[i].text = "${mat[x][y]}"
                    }

//                    buttons[i].setBackgroundResource(R.color.button_color)
                }
            }
            loadSound = {

                if (it) {
                    sound.setBackgroundResource(R.drawable.zvuk)
                } else {
                    sound.setBackgroundResource(R.drawable.zvuk_net)
                }

            }
            win = {
                time.stop()
                viewModel.time=binding.time.base-SystemClock.elapsedRealtime()
                val dialog = WinDialog()
                dialog.isCancelable = false
                winDialog.onClickHome = {
                    viewModel.onClickHome()
                }
                winDialog.onClickRestart = {
                    viewModel.onClickRefresh()
                }
                dialog.show(requireActivity().supportFragmentManager, "")


//                dialog.setInfo(count.text.substring(6,count.text.length).toInt(),binding.time.base-SystemClock.elapsedRealtime())
            }
            back = {
                findNavController().navigateUp()
            }
            moves = {
                count.text="moves $it"
            }
        }
    }

    private fun initButtons() {
        var index=0
        binding.apply {
            container.forEachIndexed { i, view ->
                (view as ViewGroup).forEachIndexed { j, view ->
                    buttons.add(view as AppCompatButton)
                    view.setOnClickListener {
                        viewModel.onCLickButton(Pair(i, j))
                        "onClick $index".myLog()
                    }
                    index++
                }
            }
//            "size ${puzzle.size}".myLog()
//            for (i in 0 until puzzle.size) {
//                buttons.add(puzzle[i] as AppCompatButton)
//                puzzle[i].setOnClickListener {
//                    viewModel.onCLickButton(Pair(i / 4, i % 4))
//                }
//            }

            sound.setOnClickListener {
                viewModel.onClickSound()
            }
            menu.setOnClickListener {
                viewModel.onClickMenu()
                openPauseDialog()
                time.stop()
                viewModel.time=time.base-SystemClock.elapsedRealtime()
            }
        }
    }
    private fun openPauseDialog(){
           val pauseDialog=PauseDialog()
           pause.onClickRestart={
               viewModel.onClickRefresh()
           }
           pause.onClickHome={
               viewModel.onClickHome()
           }
           pause.onClickPlay={
               "MainScreen pause dialog".myLog()
               viewModel.onClickPlay()
           }
           pauseDialog.isCancelable=false
           pauseDialog.show(childFragmentManager,"")
    }

    override fun onStop() {
        super.onStop()
        if (!viewModel.isPause){
            viewModel.time=binding.time.base-SystemClock.elapsedRealtime()
        }
        viewModel.saveInfo()
    }

    override fun onResume() {
        super.onResume()
        viewModel.load()
    }




}