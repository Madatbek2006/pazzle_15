package com.example.puzzle15.presenter.screen.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.puzzle15.R
import com.example.puzzle15.databinding.ScreenInfoBinding

class InfoScreen: Fragment(R.layout.screen_info) {
    private val binding by viewBinding(ScreenInfoBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.home.setOnClickListener {
            findNavController().navigateUp()
        }

    }
}