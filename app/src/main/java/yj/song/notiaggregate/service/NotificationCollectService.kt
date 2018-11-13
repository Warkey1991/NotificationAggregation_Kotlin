package yj.song.notiaggregate.service

import android.annotation.TargetApi
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import yj.song.notiaggregate.manager.GlobalSingleManager
import yj.song.notiaggregate.manager.NotificationFuncManager
import yj.song.notiaggregate.model.DBEntityWrapper
import yj.song.notiaggregate.util.AppUtil
import yj.song.notiaggregate.util.NLog

/**
 * Created by YJ.Song on 2018/11/8.
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
class NotificationCollectService : NotificationListenerService() {

    val TAG = "NotificationCollect"
    override fun onCreate() {
        super.onCreate()
        NLog.e(TAG, "onCreate")
    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        if (AppUtil.isOpenNotiAggregate()) {
            // 监听到通知，如果包含在截取列表中的应用，就截取通知，否则，不操作
            if (GlobalSingleManager.instance.checkIfNeedInterrupt(sbn?.packageName)) {
                interceptNotification(sbn)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    cancelNotification(sbn?.key)
                } else {
                    cancelNotification(sbn?.packageName, sbn?.tag, if (sbn?.id == null) 0 else sbn?.id)
                }

                NotificationFuncManager.instance.showPermanentNotification(GlobalSingleManager.instance.notificationBeans!!)
            }
        }
        NLog.e(TAG, "onNotificationPosted")
    }


    override fun onNotificationRemoved(sbn: StatusBarNotification?) {
        super.onNotificationRemoved(sbn)
        NLog.e(TAG, "onNotificationRemoved")
    }

    override fun onListenerConnected() {
        super.onListenerConnected()
        NLog.e(TAG, "onListenerConnected")

        doConnected()
    }

    override fun onListenerDisconnected() {
        super.onListenerDisconnected()
        NLog.e(TAG, "onListenerDisconnected")
    }

    override fun onDestroy() {
        super.onDestroy()
        NLog.e(TAG, "onDestroy")
    }

    private fun doConnected() {
        val notifications = activeNotifications
        for (n in notifications) {
            onNotificationPosted(n)
            NLog.e(TAG, n.packageName)
        }
    }

    /**
     * 收集通知，处理通知的数据对象
     */
    private fun interceptNotification(sbn: StatusBarNotification?) {
        val bean = DBEntityWrapper.statusBarNotificationConvertBean(sbn)
        GlobalSingleManager.instance.addNotificationBean(bean)  //加到缓存列表中，用于显示
    }


}