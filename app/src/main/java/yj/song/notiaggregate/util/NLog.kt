package yj.song.notiaggregate.util

import android.util.Log

/**
 * Created by YJ.Song on 2018/11/8.
 */
object NLog {
    const val TAG: String = "Noti_"
    var DEBUG: Boolean = true

    fun d(tag: String, msg: String) {
        if (DEBUG) {
            Log.d(tag, msg)
        }
    }

    fun d(msg: String) {
        d(TAG, msg)
    }

    fun i(tag: String, msg: String) {
        if (DEBUG) {
            Log.i(tag, msg)
        }
    }

    fun i(msg: String) {
        i(TAG, msg)
    }

    fun e(tag: String, msg: String) {
        if (DEBUG) {
            Log.e(tag, msg)
        }
    }

    fun e(msg: String) {
        e(TAG, msg)
    }


}