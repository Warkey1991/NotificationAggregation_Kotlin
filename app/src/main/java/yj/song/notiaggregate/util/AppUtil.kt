package yj.song.notiaggregate.util

import yj.song.notiaggregate.Constant

/**
 * Created by YJ.Song on 2018/11/12.
 */
object AppUtil {
    fun isOpenNotiAggregate(): Boolean {
        return PrefUtil.getBoolean(Constant.NOTI_OPEN, true)
    }
}