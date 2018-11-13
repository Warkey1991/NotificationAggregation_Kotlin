package yj.song.notiaggregate.util

import android.content.Context
import yj.song.notiaggregate.App

/**
 * Created by YJ.Song on 2018/11/12.
 */
object PrefUtil {
    const val PREF_NAME: String = "Noti_Pref"

    fun putString(key: String, value: String) {
        val pref = App.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String): String {
        val pref = App.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getString(key, "")
    }

    fun putBoolean(key: String, value: Boolean) {
        val pref = App.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, value: Boolean = false): Boolean {
        val pref = App.instance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return pref.getBoolean(key, value)
    }
}