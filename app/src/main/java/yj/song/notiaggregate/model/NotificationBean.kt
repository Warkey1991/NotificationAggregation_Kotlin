package yj.song.notiaggregate.model

import android.app.PendingIntent
import android.graphics.Bitmap

/**
 * Created by YJ.Song on 2018/11/8.
 */
data class NotificationBean(val pkgName: String?, val title: String?,
                            val content: String?, val icon: Bitmap?,
                            val sendTime: Long?, val pendingIntent: PendingIntent?)