package com.example.puzzle15.presenter.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.puzzle15.R
import com.example.puzzle15.databinding.DialogPauseBinding

class PauseDialog: DialogFragment(R.layout.dialog_pause) {
    private val binding by viewBinding(DialogPauseBinding::bind)
    private val viewModel=PauseObj
    var count=0
    var onClickPlayLiveData=MutableLiveData<Unit>()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.let { window ->
            window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            window.setDimAmount(0.5f)
        }
        return dialog
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentDialog)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initButtons()
    }

    private fun initButtons() =binding.apply{
        viewModel.apply {
            binding.root.apply {
                home.setOnClickListener {
                    onClickHome?.invoke()
                    dismiss()
                }
                restart.setOnClickListener {
                    onClickRestart?.invoke()
                    dismiss()
                }
                play.setOnClickListener {
                    onClickPlay?.invoke()
                    dismiss()
                    onClickPlayLiveData.value = Unit
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        this.dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
}
object PauseObj{
    var onClickHome:(()->Unit)?=null
    var onClickRestart:(()->Unit)?=null
    var onClickPlay:(()->Unit)?=null
}