package com.example.puzzle15.presenter.screen.main

import android.media.MediaPlayer
import androidx.lifecycle.ViewModel
import com.example.puzzle15.R
import com.example.puzzle15.data.sourse.MyShar
import com.example.puzzle15.data.sourse.MySharImpl
import com.example.uzummarketclient.utils.myLog
import kotlin.math.abs

class MainVMImpl : MainVM, ViewModel() {
    private val myShar: MyShar = MySharImpl.getInstance()
    override var back: (() -> Unit)?=null

    override var load: ((mat: ArrayList<ArrayList<Int>>) -> Unit)? = null
    override var moves: ((Int) -> Unit)?=null
    override var timeListener: ((Long) -> Unit)?=null
    override var win: (() -> Unit)?=null
    override var loadSound: ((Boolean) -> Unit)?=null
    override var isPause: Boolean = false
    override var time: Long = myShar.getTime()
    override var move: Int = myShar.getMove()
    override var isHaveSound: Boolean = myShar.isHaveSound()
    private var zeroPos=Pair(0,0)
    private var noteButton:MediaPlayer?=null
    private var winSound:MediaPlayer?=null
    private var musicSound:MediaPlayer?=null
    private lateinit var mat:ArrayList<ArrayList<Int>>

    init {
        if (myShar.getMatrix().isEmpty()){
            newMat()
            move=0
            time=0
        }else{
            mat=myShar.getMatrix()
        }
    }
    override fun onCLickButton(pos: Pair<Int, Int>) {
        val x=zeroPos.first
        val y=zeroPos.second
        val x1=pos.first
        val y1=pos.second
        if ((abs(x-x1)==1&&y==y1)||(abs(y-y1)==1&&x==x1)){
            noteButton?.start()
            mat[x][y]=mat[x1][y1]
            mat[x1][y1]=0
            zeroPos=pos
            load?.invoke(mat)
            move++
            moves?.invoke(move)
            if (pos.first==3&&pos.second==3&&isCheck()){
                winSound=MediaPlayer.create(myShar.getContext(), R.raw.you_win)
                winSound?.start()
                musicSound?.pause()
                win?.invoke()
                isPause=true
                myShar.setCurrentMove(move, time)
                myShar.setMatrix(ArrayList(ArrayList()))
                reyting()
            }
        }
    }

    override fun load() {
        if (musicSound==null){
            musicSound=MediaPlayer.create(myShar.getContext(),R.raw.puzzle_media)
        }
        winSound?.stop()
        winSound=null
        if (!isPause){
            musicSound?.start()
            "salom".myLog()
        }
            load?.invoke(mat)
            zeroPos=findElementInMatrix(matrix = mat,0)
        if (isHaveSound){
            musicSound?.setVolume(0.7f,0.7f)
            noteButton=MediaPlayer.create(myShar.getContext(), R.raw.button_click)
        }else{
            musicSound?.setVolume(0f,0f)
        }
        musicSound?.setOnCompletionListener {
            it.start()
        }
        if (!isHaveSound){
            noteButton=null
            musicSound?.setVolume(0f,0f)
        }else{
            noteButton=MediaPlayer.create(myShar.getContext(), R.raw.button_click)
            musicSound?.setVolume(0.7f,0.7f)
        }
        loadSound?.invoke(isHaveSound)
        moves?.invoke(move)
    }

    override fun onClickMenu() {
        isPause=true
        musicSound?.pause()
    }

    override fun onClickSound() {
        if (isHaveSound){
            isHaveSound=false
            noteButton=null
            musicSound?.setVolume(0f,0f)
        }else{
            noteButton=MediaPlayer.create(myShar.getContext(), R.raw.button_click)
            isHaveSound=true
            musicSound?.setVolume(0.7f,0.7f)
        }
        loadSound?.invoke(isHaveSound)
    }

    override fun onClickHome() {
        back?.invoke()

    }

    override fun onClickRefresh() {
        move=0
        time=0
        timeListener?.invoke(time)
        newMat()
        isPause=false
        load()
    }

    override fun openDialog() {
    }

    override fun onClickPlay() {
        isPause=false
        timeListener?.invoke(time)
        load()
    }

    override fun saveInfo() {
        musicSound?.pause()
        myShar.setMatrix(mat)
        myShar.setMove(move)
        myShar.setTime(time)
        myShar.setSound(isHaveSound)
    }

    private fun newMat(){
        mat=ArrayList()
        var index=0
        var array=ArrayList<Int>()
        for (i in 0 until 16){
            array.add(i)
        }
        array.shuffle()
        for (i in 0 until 4){
            val arr=ArrayList<Int>()
            for (j in 0 until 4){
                arr.add(array[index])
                index++
            }
            mat.add(arr)

        }
        if (!isSolvable()){
            newMat()
            return
        }
    }

    private fun findElementInMatrix(matrix: ArrayList<ArrayList<Int>>, target: Int): Pair<Int, Int> {
        matrix.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, element ->
                if (element == target) {
                    return Pair(rowIndex, columnIndex)
                }
            }
        }
        return Pair(0,0)
    }

    private fun isCheck():Boolean{
        var index=1
        for (i in 0 until 4){
            for (j in 0 until 4){
                if (i==3&&j==3)return true
                if (mat[i][j]!=index)return false
                index++
            }
        }
        return false
    }

    private fun isSolvable(): Boolean {
        val arr = IntArray(16)
        var k = 0
        for (i in 0 until 4){
            for(j in 0 until 4){
                arr[k++]=mat[i][j]
            }
        }
        val invCount = getInvCount(arr)
        return invCount % 2 == 0
    }

    private fun getInvCount(arr: IntArray): Int {
        var inv_count = 0
        for (i in 0..14) {
            for (j in i + 1..15) {
                if (arr[j] != 0 && arr[i] != 0 && arr[i] > arr[j]) {
                    inv_count++
                }
            }
        }
        return inv_count
    }

    private fun reyting() {
        var tops=myShar.getTopMove()
        var topCount1=tops.first
        var topCount2=tops.second
        var topCount3=tops.third

        var topsTime=myShar.getTopTime()
        var topTime1=topsTime.first
        var topTime2=topsTime.second
        var topTime3=topsTime.third
        if (topCount1 == 0) {
            topCount1 = move
        } else if (topCount1 > move) {
            topCount2 = topCount1
            topCount1 = move
        } else if (topCount2 == 0 && topCount1 != move) {
            topCount2 = move
        } else if (topCount2 > move && topCount1 != move) {
            topCount3 = topCount2
            topCount2 = move
        } else if (topCount3 == 0 && topCount1 != move && topCount2 != move) {
            topCount3 = move
        } else if (topCount3 > move && topCount1 != move && topCount2 != move) {
            topCount3 = move
        }
        if (topTime1 == 0L) {
            topTime1 = time
        } else if (topTime1 < time) {
            topTime2 = topTime1
            topTime1 = time
        } else if (topTime2 == 0L && topTime1 != time) {
            topTime2 = time
        } else if (topTime2 < time && topTime1 != time) {
            topTime3 = topTime2
            topTime2 = time
        } else if (topTime3 == 0L && topTime1 != time && topTime2 != time) {
            topTime3 = time
        } else if (topTime3 < time && topTime1 != time && topTime2 != time) {
            topTime3 = time
        }
        myShar.setTopMove(topCount1,topCount2,topCount3)
        myShar.setTopTime(topTime1,topTime2,topTime3)

    }

}