package com.example.puzzle15.data.sourse

import android.content.Context

interface MyShar {
    fun setMatrix(mat:ArrayList<ArrayList<Int>>)
    fun getMatrix():ArrayList<ArrayList<Int>>
    fun setMove(move:Int)
    fun getMove():Int
    fun setTime(time:Long)
    fun getTime():Long
    fun getContext():Context

    fun isHaveSound():Boolean
    fun setSound(bool:Boolean)
    fun setTopMove(t1: Int, t2: Int, t3: Int)
    fun getTopMove():Triple<Int,Int,Int>
    fun setTopTime(t1: Long, t2: Long, t3: Long)
    fun getTopTime():Triple<Long,Long,Long>

    fun setCurrentMove(move:Int,time:Long)
    fun getCurrentMove():Pair<Int,Long>
    fun clearMat()
}