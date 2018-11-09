package yj.song.notiaggregate.util

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import yj.song.notiaggregate.service.NotificationCollectService

/**
 * Created by YJ.Song on 2018/11/8.
 */
object NotiAggregate {

    /**
     * 被杀后再次启动时，监听不生效的问题,进程重启调用
     */
    fun toggleNotificationListenerService(context: Context) {
        try {
            if (AndroidUtil.checkPermission(context)) {
                val pm = context.packageManager
                pm.setComponentEnabledSetting(ComponentName(context, NotificationCollectService::class.java!!),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
                pm.setComponentEnabledSetting(ComponentName(context, NotificationCollectService::class.java!!),
                        PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP)
            }
        } catch (e: Exception) {

        }
    }
}