package com.example.puzzle15.presenter.screen.main

import com.example.puzzle15.presenter.dialog.PauseDialog

interface MainVM {
    var back:(()->Unit)?
    var load:((ArrayList<ArrayList<Int>>)->Unit)?
    var moves:((Int)->Unit)?
    var timeListener:((Long)->Unit)?
    var win:(()->Unit)?
    var loadSound:((Boolean)->Unit)?
    val isPause:Boolean
    var time:Long
    val move:Int
    val isHaveSound:Boolean
    fun onCLickButton(pos:Pair<Int,Int>)
    fun load()
    fun onClickMenu()
    fun onClickSound()
    fun onClickHome()
    fun onClickRefresh()
    fun openDialog()

    fun onClickPlay()
    fun saveInfo()
}