package com.example.puzzle15.data.sourse

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log

@SuppressLint("StaticFieldLeak")
class MySharImpl private constructor():MyShar {

    companion object{
        lateinit var sharedPreferences: SharedPreferences
        lateinit var myShar: MySharImpl
        lateinit var context: Context
        fun init(context: Context){
            sharedPreferences=context.getSharedPreferences("Puzzle",Context.MODE_PRIVATE)
            myShar=MySharImpl()
            this.context=context

        }


        fun getInstance():MySharImpl{
            return myShar
        }
    }

    override fun setMatrix(mat: ArrayList<ArrayList<Int>>) {
        val sizeI=mat.size
        val sizeJ=mat[0].size
        sharedPreferences.edit().putInt("sizeI",sizeI).apply()
        sharedPreferences.edit().putInt("sizeJ",sizeJ).apply()
        for ( i in 0 until sizeI){
            for (j in 0 until sizeJ){
                sharedPreferences.edit().putInt("mat[$i][$j]",mat[i][j]).apply()
            }
        }
    }

    override fun getMatrix(): ArrayList<ArrayList<Int>> {
        val mat=ArrayList<ArrayList<Int>>()
        val sizeI= sharedPreferences.getInt("sizeI",0)
        val sizeJ= sharedPreferences.getInt("sizeJ",0)

        for ( i in 0 until sizeI){
            val arr=ArrayList<Int>()
            for (j in 0 until sizeJ){
                arr.add(sharedPreferences.getInt("mat[$i][$j]",0))
            }
            mat.add(arr)
        }
        return mat
    }

    override fun setMove(move: Int) {
        sharedPreferences.edit().putInt("move",move).apply()
    }

    override fun getMove(): Int = sharedPreferences.getInt("move",0)

    override fun setTime(time: Long) {
        sharedPreferences.edit().putLong("time",time).apply()
    }

    override fun getTime(): Long = sharedPreferences.getLong("time",0)
    override fun getContext(): Context = context
    override fun isHaveSound(): Boolean= sharedPreferences.getBoolean("sound",true)
    override fun setSound(bool: Boolean) {
        sharedPreferences.edit().putBoolean("sound",bool).apply()
    }

    override fun setTopMove(t1: Int, t2: Int, t3: Int) {
        sharedPreferences.edit().putInt("top_move1", t1).apply()
        sharedPreferences.edit().putInt("top_move2", t2).apply()
        sharedPreferences.edit().putInt("top_move3", t3).apply()
    }

    override fun getTopMove(): Triple<Int, Int, Int> = Triple(
        sharedPreferences.getInt("top_move1",0),
        sharedPreferences.getInt("top_move2",0),
        sharedPreferences.getInt("top_move3",0)
    )

    override fun setTopTime(t1:Long, t2:Long, t3:Long) {
        sharedPreferences.edit().putLong("top_time1", t1).apply()
        sharedPreferences.edit().putLong("top_time2", t2).apply()
        sharedPreferences.edit().putLong("top_time3", t3).apply()
    }

    override fun getTopTime(): Triple<Long,Long,Long> = Triple(
        sharedPreferences.getLong("top_time1",-1),
        sharedPreferences.getLong("top_time2",-1),
        sharedPreferences.getLong("top_time3",-1)
    )

    override fun setCurrentMove(move: Int, time: Long) {
        sharedPreferences.edit().putLong("current_time",time).apply()
        sharedPreferences.edit().putInt("current_move",move).apply()
    }

    override fun getCurrentMove(): Pair<Int, Long> = Pair(
        sharedPreferences.getInt("current_move",0),
        sharedPreferences.getLong("current_time",0),
    )

    override fun clearMat() {
        sharedPreferences.edit().putInt("sizeI",0).apply()
        sharedPreferences.edit().putInt("sizeJ",0).apply()
    }
}