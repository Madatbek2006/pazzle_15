package com.example.puzzle15.presenter.screen.play

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.puzzle15.R
import com.example.puzzle15.databinding.ScreenPlayBinding

class PlayScreen:Fragment(R.layout.screen_play) {
    private val binding by viewBinding(ScreenPlayBinding::bind)
    private var exitCount=0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initButton()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onStart() {
        super.onStart()
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
    private fun initButton() =binding.apply{
        play.setOnClickListener {
            findNavController().navigate(R.id.action_playScreen_to_mainScreen)
        }
        info.setOnClickListener {
            findNavController().navigate(R.id.action_playScreen_to_infoScreen)
        }
        statistic.setOnClickListener {
            findNavController().navigate(R.id.action_playScreen_to_statisticsScreen)
        }
        exit.setOnClickListener {
     AlertDialog.Builder(requireContext())
                 .setTitle("Chiqish")
                 .setMessage("Siz aniq chiqmoqchimisiz?")
             .setPositiveButton("yo'q", { dialog, which ->

             })
             .setNegativeButton("ha", { dialog, which ->
                 requireActivity().finish()
             })
             .create()
             .show()


        }
    }
    private fun finish() {
        exitCount++
        Exit().start()
        if (exitCount > 1) {
            requireActivity().finish()
        } else Toast.makeText(requireContext(), "Chiqish uchun ikki marotaba bosing!", Toast.LENGTH_SHORT)
            .show()
    }

    inner class Exit : Thread() {
        override fun run() {
            try {
                sleep(1000)
                exitCount = 0
            } catch (ignored: InterruptedException) {
            }
        }
    }

}