package com.example.puzzle15.presenter.dialog

import android.app.Dialog
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.puzzle15.R
import com.example.puzzle15.data.sourse.MyShar
import com.example.puzzle15.data.sourse.MySharImpl
import com.example.puzzle15.databinding.DialogWinBinding
import com.example.uzummarketclient.utils.durationConverter
import com.example.uzummarketclient.utils.myLog
import kotlin.math.abs

class WinDialog:DialogFragment(R.layout.dialog_win) {
    private val myShar:MyShar=MySharImpl.getInstance()
    private val binding by viewBinding(DialogWinBinding::bind)
    private val obj=WinDialogOpj
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
        setInfo()
    }

    private fun initButtons() =binding.apply{
        winHome.setOnClickListener {
            obj.onClickHome?.invoke()
            dismiss()
        }
        winRestart.setOnClickListener {
            obj.onClickRestart?.invoke()
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        this.dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    fun setInfo(){
        myShar.getCurrentMove().toString().myLog()
        binding.current.text="moves: ${myShar.getCurrentMove().first}   time: ${abs(myShar.getCurrentMove().second).durationConverter()}"
        binding.best.text="moves: ${myShar.getTopMove().first}   time: ${abs(myShar.getTopTime().first).durationConverter()}"
    }
}
object WinDialogOpj{
    var onClickHome:(()->Unit)?=null
    var onClickRestart:(()->Unit)?=null
}